package eu.domibus.ebms3.common.dao;

import eu.domibus.api.xml.UnmarshallerResult;
import eu.domibus.api.xml.XMLUtil;
import eu.domibus.common.dao.ConfigurationDAO;
import eu.domibus.common.dao.PModeDao;
import eu.domibus.common.model.configuration.Configuration;
import eu.domibus.messaging.XmlProcessingException;
import eu.domibus.xml.XMLUtilImpl;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityManagerFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;

/**
 * Created by Cosmin Baciu on 14-Sep-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class PModeDaoTest {

    private static final Log LOG = LogFactory.getLog(PModeDaoTest.class);


    @org.springframework.context.annotation.Configuration
    static class ContextConfiguration {

        @Bean
        public ConfigurationDAO configurationDAO() {
            return Mockito.mock(ConfigurationDAO.class);
        }

        @Bean
        public EntityManagerFactory entityManagerFactory() {
            return Mockito.mock(EntityManagerFactory.class);
        }

        @Bean
        public JAXBContext jaxbContextConfig() throws JAXBException {
            return JAXBContext.newInstance("eu.domibus.common.model.configuration");
        }

        @Bean
        @Qualifier("jmsTemplateCommand")
        public JmsOperations jmsOperations() throws JAXBException {
            return Mockito.mock(JmsOperations.class);
        }

        @Bean
        public XMLUtil xmlUtil() {
            return new XMLUtilImpl();
        }

        @Bean
        public PModeDao pModeDao() {
            return new PModeDao();
        }
    }

    @Autowired
    PModeDao pModeDao;

    @Autowired
    XMLUtil xmlUtil;

    @Autowired
    ConfigurationDAO configurationDAO;

    @Autowired
    JAXBContext jaxbContext;


    @Test
    public void testUpdatePModeWithPmodeContainingWhiteSpace() throws Exception {
        InputStream xmlStream = getClass().getClassLoader().getResourceAsStream("SamplePModes/domibus-configuration-with-whitespaces.xml");
        byte[] pModeBytes = IOUtils.toByteArray(xmlStream);
        UnmarshallerResult unmarshallerResult = xmlUtil.unmarshal(true, jaxbContext, new ByteArrayInputStream(pModeBytes), null);

        String updatePmodeMessage = pModeDao.updatePModes(pModeBytes);
        assertNotNull(updatePmodeMessage);

        ArgumentCaptor<Configuration> parameter = ArgumentCaptor.forClass(Configuration.class);
        Mockito.verify(configurationDAO).updateConfiguration(parameter.capture());

        //compare the provided PMode with the one that gets saved
        Configuration saved = parameter.getValue();
        assertNotNull(saved);
        Configuration original = unmarshallerResult.getResult();
        assertEquals(saved.getMpcsXml().getMpc().size(), original.getMpcsXml().getMpc().size());
        assertEquals(saved.getBusinessProcesses(), original.getBusinessProcesses());
    }

    @Test
    public void testUpdatePModeWithValidPmode() throws Exception {
        InputStream xmlStream = getClass().getClassLoader().getResourceAsStream("SamplePModes/domibus-configuration-valid.xml");
        byte[] pModeBytes = IOUtils.toByteArray(xmlStream);
        UnmarshallerResult unmarshallerResult = xmlUtil.unmarshal(true, jaxbContext, new ByteArrayInputStream(pModeBytes), null);

        String updatePmodeMessage = pModeDao.updatePModes(pModeBytes);
        //there are no warnings
        assertNull(updatePmodeMessage);

        ArgumentCaptor<Configuration> parameter = ArgumentCaptor.forClass(Configuration.class);
        Mockito.verify(configurationDAO).updateConfiguration(parameter.capture());

        //compare the provided PMode with the one that gets saved
        Configuration saved = parameter.getValue();
        assertNotNull(saved);
        Configuration original = unmarshallerResult.getResult();
        assertEquals(saved.getMpcsXml().getMpc().size(), original.getMpcsXml().getMpc().size());
        assertEquals(saved.getBusinessProcesses(), original.getBusinessProcesses());
    }

    @Test
    public void testUpdatePModeWithXsdNotCompliantPmode() throws Exception {
        InputStream xmlStream = getClass().getClassLoader().getResourceAsStream("SamplePModes/domibus-configuration-xsd-not-compliant.xml");
        byte[] pModeBytes = IOUtils.toByteArray(xmlStream);

        try {
            pModeDao.updatePModes(pModeBytes);
            fail("The Pmode is invalid so it should have thrown an exception");
        } catch (XmlProcessingException e) {
            LOG.info("Exception thrown as expected due to invalid PMode");
        }

        Mockito.verify(configurationDAO, never()).updateConfiguration((Configuration) Mockito.anyObject());
    }
}
