
package eu.domibus.plugin.webService;

import eu.domibus.AbstractIT;
import eu.domibus.plugin.webService.generated.BackendInterface;
import eu.domibus.plugin.webService.generated.ErrorResultImplArray;
import eu.domibus.plugin.webService.generated.GetErrorsRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


/**
 * This JUNIT implements the Test cases Get Message Errors-01 and Get Message Errors-02.
 *
 * @author martifp
 */
public class GetMessageErrorsIT extends AbstractIT {

    private static boolean initialized;
    @Autowired
    BackendInterface backendWebService;

    @Before
    public void before() throws IOException {
        if (!initialized) {
            insertDataset("getMessageErrorsDataset.sql");
            initialized = true;
        }
    }

    /**
     * Tests that the list of errors is not empty for a certain message.
     */
    @Test
    public void testGetMessageErrorsOk() {
        String messageId = "9008713e-1912-460c-97b3-40ec12a29f49@domibus.eu";
        GetErrorsRequest errorsRequest = createMessageErrorsRequest(messageId);
        ErrorResultImplArray response = backendWebService.getMessageErrors(errorsRequest);
        Assert.assertFalse(response.getItem().isEmpty());
    }

    /**
     * Tests that the list of errors is empty for a certain message since there were no errors in the transaction.
     */
    @Test
    public void testGetEmptyMessageErrorsList() {

        String messageId = "2809cef6-240f-4792-bec1-7cb300a34679@domibus.eu";
        GetErrorsRequest errorsRequest = createMessageErrorsRequest(messageId);
        ErrorResultImplArray response = backendWebService.getMessageErrors(errorsRequest);
        Assert.assertTrue(response.getItem().isEmpty());
    }

    private GetErrorsRequest createMessageErrorsRequest(final String messageId) {

        GetErrorsRequest errorsRequest = new GetErrorsRequest();
        errorsRequest.setMessageID(messageId);
        return errorsRequest;
    }
}
