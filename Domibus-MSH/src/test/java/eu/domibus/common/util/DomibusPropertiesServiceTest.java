package eu.domibus.common.util;

import mockit.integration.junit4.JMockit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Federico Martini
 */
@RunWith(JMockit.class)
public class DomibusPropertiesServiceTest {

    private static final Log LOG = LogFactory.getLog(DomibusPropertiesServiceTest.class);

    @Test
    public void testDisplayVersion() throws Exception {

        DomibusPropertiesService service = new DomibusPropertiesService();

        assertEquals("domibus-MSH", service.getArtifactName());
        assertNotEquals("", service.getBuiltTime());
        assertNotEquals("", service.getImplVersion());

        LOG.info(service.getDisplayVersion());
    }
}
