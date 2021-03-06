package eu.domibus.common.dao;

import com.google.common.collect.Lists;
import eu.domibus.api.util.xml.XMLUtil;
import eu.domibus.common.exception.EbMS3Exception;
import eu.domibus.common.model.configuration.Process;
import eu.domibus.ebms3.common.validators.ConfigurationValidator;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsOperations;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import java.util.List;

/**
 * @author Cosmin Baciu
 * @since 3.2
 */
@RunWith(JMockit.class)
public class PModeDaoTest {

    @Injectable
    protected EntityManager entityManager;

    @Injectable
    private JAXBContext jaxbContextConfig;

    @Injectable
    private JmsOperations jmsTemplateCommand;

    @Injectable
    XMLUtil xmlUtil;

    @Injectable
    List<ConfigurationValidator> configurationValidators;

    @Injectable
    protected ProcessDao processDao;

    @Tested(fullyInitialized = true)
    private PModeDao pModeDao;

    @Test
    public void testFindPushLegName() throws EbMS3Exception {

        new Expectations(pModeDao) {{
            pModeDao.findLegNameMepBindingAgnostic("agreementName", "senderParty", "receiverParty", "service", "action");
            result = "pushLeg";
        }};
        final String legName = pModeDao.findLegName("agreementName", "senderParty", "receiverParty", "service", "action");
        Assert.assertEquals("pushLeg", legName);
    }

    @Test
    public void testFindPushNotFoundPullNotFoundLegName() throws EbMS3Exception {
        new Expectations(pModeDao) {{
            pModeDao.findLegNameMepBindingAgnostic("agreementName", "senderParty", "receiverParty", "service", "action");
            result = new EbMS3Exception(null, "", "", null);
            pModeDao.findLegNameMepBindingAgnostic("agreementName", "receiverParty", "senderParty", "service", "action");
            result = new EbMS3Exception(null, "", "", null);
        }};
        EbMS3Exception check = null;
        try {
            pModeDao.findLegName("agreementName", "senderParty", "receiverParty", "service", "action");
            Assert.assertTrue(false);
        } catch (EbMS3Exception e) {
            check = e;
        }
        Assert.assertNotNull(check);
    }

    @Test
    public void testFindPullLegFoundButNoPullProcess() throws EbMS3Exception {
        final String pullLegFound = "pullLegFound";
        new Expectations(pModeDao) {{
            pModeDao.findLegNameMepBindingAgnostic("agreementName", "senderParty", "receiverParty", "service", "action");
            result = new EbMS3Exception(null, "", "", null);
            pModeDao.findLegNameMepBindingAgnostic("agreementName", "receiverParty", "senderParty", "service", "action");
            result = pullLegFound;
            processDao.findPullProcessByLegName(pullLegFound);
            result = Lists.newArrayList();

        }};
        EbMS3Exception check = null;
        try {
            pModeDao.findLegName("agreementName", "senderParty", "receiverParty", "service", "action");
            Assert.assertTrue(false);
        } catch (EbMS3Exception e) {
            check = e;
        }
        Assert.assertNotNull(check);
    }

    @Test
    public void testFindPullLegFoundAndOnePullProcess() throws EbMS3Exception {
        final String pullLegFound = "pullLegFound";
        new Expectations(pModeDao) {{
            pModeDao.findLegNameMepBindingAgnostic("agreementName", "senderParty", "receiverParty", "service", "action");
            result = new EbMS3Exception(null, "", "", null);
            pModeDao.findLegNameMepBindingAgnostic("agreementName", "receiverParty", "senderParty", "service", "action");
            result = pullLegFound;
            processDao.findPullProcessByLegName(pullLegFound);
            result = Lists.newArrayList(new Process());

        }};

        String legName = pModeDao.findLegName("agreementName", "senderParty", "receiverParty", "service", "action");
        Assert.assertEquals(pullLegFound, legName);

    }

}
