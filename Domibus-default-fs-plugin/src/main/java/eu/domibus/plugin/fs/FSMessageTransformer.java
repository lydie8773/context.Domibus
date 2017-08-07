package eu.domibus.plugin.fs;

import eu.domibus.logging.DomibusLogger;
import eu.domibus.logging.DomibusLoggerFactory;
import eu.domibus.plugin.Submission;
import eu.domibus.plugin.fs.ebms3.*;
import eu.domibus.plugin.fs.exception.FSPayloadException;
import eu.domibus.plugin.transformer.MessageRetrievalTransformer;
import eu.domibus.plugin.transformer.MessageSubmissionTransformer;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * This class is responsible for transformations from {@link FSMessage} to
 * {@link eu.domibus.plugin.Submission} and vice versa
 *
 * @author @author FERNANDES Henrique, GONCALVES Bruno
 */
@Component
public class FSMessageTransformer
        implements MessageRetrievalTransformer<FSMessage>, MessageSubmissionTransformer<FSMessage> {

    private static final DomibusLogger LOG = DomibusLoggerFactory.getLogger(FSMessageTransformer.class);
    
    private static final String DEFAULT_CONTENT_ID = "cid:message";
    private static final String DEFAULT_MIME_TYPE =  "text/xml";
    public static final String MIME_TYPE = "MimeType";
    public static final String CHARSET = "CharacterSet";

    /**
     * Transforms {@link eu.domibus.plugin.Submission} to {@link FSMessage}
     *
     * @param submission the message to be transformed
     * @param messageOut output target
     *
     * @return result of the transformation as {@link FSMessage}
     */
    @Override
    public FSMessage transformFromSubmission(final Submission submission, final FSMessage messageOut) {
        UserMessage metadata = new UserMessage();
        metadata.setPartyInfo(getPartyInfoFromSubmission(submission));
        metadata.setCollaborationInfo(getCollaborationInfoFromSubmission(submission));
        metadata.setMessageInfo(getMessageInfoFromSubmission(submission));
        metadata.setMessageProperties(getMessagePropertiesFromSubmission(submission));

        try {
            DataHandler dataHandler = getPayloadFromSubmission(submission);
            return new FSMessage(dataHandler, metadata);
        } catch (FSPayloadException ex) {
            LOG.error("Could not get the file from submission " + submission.getMessageId(), ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Transforms {@link FSMessage} to {@link eu.domibus.plugin.Submission}
     *
     * @param messageIn the message ({@link FSMessage}) to be tranformed
     * @return the result of the transformation as
     * {@link eu.domibus.plugin.Submission}
     */
    @Override
    public Submission transformToSubmission(final FSMessage messageIn) {
        UserMessage metadata = messageIn.getMetadata();
        Submission submission = new Submission();
        
        setPartyInfo(submission, metadata.getPartyInfo());
        setCollaborationInfo(submission, metadata.getCollaborationInfo());
        setMessageProperties(submission, metadata.getMessageProperties());
        setPayload(submission, messageIn.getDataHandler());
        
        return submission;
    }

    private void setPayload(Submission submission, final DataHandler dataHandler) {
        ArrayList<Submission.TypedProperty> payloadProperties = new ArrayList<>(1);
        payloadProperties.add(new Submission.TypedProperty(MIME_TYPE, DEFAULT_MIME_TYPE));
        
        submission.addPayload(DEFAULT_CONTENT_ID, dataHandler, payloadProperties);
    }

    private DataHandler getPayloadFromSubmission(Submission submission) throws FSPayloadException {
        Set<Submission.Payload> payloads = submission.getPayloads();
        if (payloads.size() == 1) {
            Submission.Payload payload = payloads.iterator().next();
            Collection<Submission.TypedProperty> payloadProperties = payload.getPayloadProperties();

            return payload.getPayloadDatahandler();
        } else {
            throw new FSPayloadException("Payloads size should be 1");
        }
    }

    private void setMessageProperties(Submission submission, MessageProperties messageProperties) {
        for (Property messageProperty : messageProperties.getProperty()) {
            String name = messageProperty.getName();
            String value = messageProperty.getValue();
            String type = messageProperty.getType();
            
            if (type != null) {
                submission.addMessageProperty(name, value, type);
            } else {
                submission.addMessageProperty(name, value);
            }
        }
    }

    private MessageProperties getMessagePropertiesFromSubmission(Submission submission) {
        MessageProperties messageProperties = new MessageProperties();

        for (Submission.TypedProperty typedProperty: submission.getMessageProperties()) {
            Property messageProperty = new Property();
            messageProperty.setType(typedProperty.getType());
            messageProperty.setName(typedProperty.getKey());
            messageProperty.setValue(typedProperty.getValue());
            messageProperties.getProperty().add(messageProperty);
        }
        return messageProperties;
    }

    private void setCollaborationInfo(Submission submission, CollaborationInfo collaborationInfo) {
        AgreementRef agreementRef = collaborationInfo.getAgreementRef();
        Service service = collaborationInfo.getService();
        
        if (agreementRef != null) {
            submission.setAgreementRef(agreementRef.getValue());
            submission.setAgreementRefType(agreementRef.getType());
        }
        submission.setService(service.getValue());
        submission.setServiceType(service.getType());
        submission.setAction(collaborationInfo.getAction());
        
        // TODO: is this bit needed?
        if (collaborationInfo.getConversationId() != null) {
            submission.setConversationId(collaborationInfo.getConversationId());
        }
    }

    private CollaborationInfo getCollaborationInfoFromSubmission(Submission submission) {
        AgreementRef agreementRef = new AgreementRef();
        agreementRef.setType(submission.getAgreementRefType());
        agreementRef.setValue(submission.getAgreementRef());

        Service service = new Service();
        service.setType(submission.getServiceType());
        service.setValue(submission.getService());

        CollaborationInfo collaborationInfo = new CollaborationInfo();
        collaborationInfo.setAgreementRef(agreementRef);
        collaborationInfo.setService(service);
        collaborationInfo.setAction(submission.getAction());
        collaborationInfo.setConversationId(submission.getConversationId());

        return collaborationInfo;
    }

    private MessageInfo getMessageInfoFromSubmission(Submission submission) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessageId(submission.getMessageId());
        return messageInfo;
    }

    private void setPartyInfo(Submission submission, PartyInfo partyInfo) {
        From from = partyInfo.getFrom();
        To to = partyInfo.getTo();
        
        submission.addFromParty(from.getPartyId().getValue(), from.getPartyId().getType());
        submission.setFromRole(from.getRole());
        if (to != null) {
            submission.addToParty(to.getPartyId().getValue(), to.getPartyId().getType());
            submission.setToRole(to.getRole());
        }
    }

    private PartyInfo getPartyInfoFromSubmission(Submission submission) {
        // From
        Submission.Party fromParty = submission.getFromParties().iterator().next();
        String fromRole = submission.getFromRole();

        PartyId fromPartyId = new PartyId();
        fromPartyId.setType(fromParty.getPartyIdType());
        fromPartyId.setValue(fromParty.getPartyId());

        From from = new From();
        from.setPartyId(fromPartyId);
        from.setRole(fromRole);

        // To
        Submission.Party toParty = submission.getToParties().iterator().next();
        String toRole = submission.getToRole();

        PartyId toPartyId = new PartyId();
        toPartyId.setType(toParty.getPartyIdType());
        toPartyId.setValue(toParty.getPartyId());

        To to = new To();
        to.setPartyId(toPartyId);
        to.setRole(toRole);

        // PartyInfo
        PartyInfo partyInfo = new PartyInfo();
        partyInfo.setFrom(from);
        partyInfo.setTo(to);

        return partyInfo;
    }
}