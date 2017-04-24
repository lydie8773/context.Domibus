package eu.domibus.ext.delegate.services.message;

import eu.domibus.api.message.acknowledge.MessageAcknowledgeService;
import eu.domibus.api.message.acknowledge.MessageAcknowledgement;
import eu.domibus.api.message.UserMessageService;
import eu.domibus.api.security.AuthUtils;
import eu.domibus.ext.delegate.converter.DomibusDomainConverter;
import eu.domibus.ext.domain.MessageAcknowledgementDTO;
import eu.domibus.ext.exceptions.AuthenticationException;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.access.AccessDeniedException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  migueti, Cosmin Baciu
 * @since 3.3
 */
@RunWith(JMockit.class)
public class MessageAcknowledgeServiceDelegateTest {

    @Tested
    MessageAcknowledgeServiceDelegate messageAcknowledgeServiceDelegate;

    @Injectable
    MessageAcknowledgeService messageAcknowledgeService;

    @Injectable
    DomibusDomainConverter domainConverter;

    @Injectable
    UserMessageService userMessageService;

    @Injectable
    AuthUtils authUtils;

    @Test
    public void testAcknowledgeMessageDelivered() throws Exception {
        final String messageId = "1";
        final Timestamp acknowledgeTimestamp = new Timestamp(System.currentTimeMillis());
        final Map<String, String> properties = new HashMap<>();
        properties.put("prop1", "value1");

        final MessageAcknowledgement messageAcknowledgement = new MessageAcknowledgement();

        new Expectations(messageAcknowledgeServiceDelegate) {{
            //don't execute method
            messageAcknowledgeServiceDelegate.checkSecurity(messageId);

            messageAcknowledgeService.acknowledgeMessageDelivered(messageId, acknowledgeTimestamp, properties);
            result = messageAcknowledgement;

        }};

        messageAcknowledgeServiceDelegate.acknowledgeMessageDelivered(messageId, acknowledgeTimestamp, properties);

        new Verifications() {{
            messageAcknowledgeService.acknowledgeMessageDelivered(messageId, acknowledgeTimestamp, properties);
            domainConverter.convert(messageAcknowledgement, MessageAcknowledgementDTO.class);
        }};
    }

    @Test
    public void testAcknowledgeMessageDeliveredWithNoProperties() throws Exception {
        final String messageId = "1";
        final Timestamp acknowledgeTimestamp = new Timestamp(System.currentTimeMillis());

        new Expectations(messageAcknowledgeServiceDelegate) {{
            //don't execute method
            messageAcknowledgeServiceDelegate.checkSecurity(messageId);

            messageAcknowledgeService.acknowledgeMessageDelivered(messageId, acknowledgeTimestamp, null);
        }};

        messageAcknowledgeServiceDelegate.acknowledgeMessageDelivered(messageId, acknowledgeTimestamp);
    }

    @Test
    public void testCheckSecurityWithUnsecuredLoginAllowed() throws Exception {
        final String messageId = "1";
        new Expectations(messageAcknowledgeServiceDelegate) {{
            userMessageService.getFinalRecipient(messageId);
            result = messageId;

            //don't execute method
            authUtils.isUnsecureLoginAllowed();
            result = true;
        }};

        messageAcknowledgeServiceDelegate.checkSecurity(messageId);

        new Verifications() {{
            authUtils.isUserAdmin();
            times = 0;
        }};
    }

    @Test
    public void testCheckSecurityWithAdminRole() throws Exception {
        final String messageId = "1";
        new Expectations(messageAcknowledgeServiceDelegate) {{
            userMessageService.getFinalRecipient(messageId);
            result = messageId;

            //don't execute method
            authUtils.isUnsecureLoginAllowed();
            result = false;

            authUtils.isUserAdmin();
            result = true;
        }};

        messageAcknowledgeServiceDelegate.checkSecurity(messageId);

        new Verifications() {{
            authUtils.hasUserRole();
            times = 0;
        }};
    }


    @Test(expected = AuthenticationException.class)
    public void testCheckSecurityWithNoUserRole() throws Exception {
        final String messageId = "1";
        new Expectations(messageAcknowledgeServiceDelegate) {{
            userMessageService.getFinalRecipient(messageId);
            result = messageId;

            //don't execute method
            authUtils.isUnsecureLoginAllowed();
            result = false;

            authUtils.hasUserRole();
            result = new AccessDeniedException("access denied");
        }};

        messageAcknowledgeServiceDelegate.checkSecurity(messageId);
    }

    @Test(expected = AuthenticationException.class)
    public void testCheckSecurityWhenOriginalUserFromSecurityContextIsDifferent() throws Exception {
        final String messageId = "1";
        final String originalUserFromSecurityContext = "C4";

        new Expectations(messageAcknowledgeServiceDelegate) {{
            //don't execute method
            authUtils.isUnsecureLoginAllowed();
            result = false;

            authUtils.isUserAdmin();
            result = false;

            authUtils.getOriginalUserFromSecurityContext();
            result = originalUserFromSecurityContext;


            userMessageService.getFinalRecipient(messageId);
            result = "differentRecipientID";
        }};

        messageAcknowledgeServiceDelegate.checkSecurity(messageId);
    }

    @Test
    public void testGetAcknowledgeMessages() throws Exception {
        final String messageId = "1";
        final List<MessageAcknowledgement> messageAcknowledgements = new ArrayList<>();
        messageAcknowledgements.add(new MessageAcknowledgement());
        messageAcknowledgements.add(new MessageAcknowledgement());


        new Expectations(messageAcknowledgeServiceDelegate) {{
            //don't execute method
            messageAcknowledgeServiceDelegate.checkSecurity(messageId);

            messageAcknowledgeService.getAcknowledgedMessages(messageId);
            result = messageAcknowledgements;

        }};

        messageAcknowledgeServiceDelegate.getAcknowledgedMessages(messageId);

        new Verifications() {{
            domainConverter.convert(messageAcknowledgements, MessageAcknowledgementDTO.class);
        }};
    }
}