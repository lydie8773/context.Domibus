package eu.domibus.api.message;

import java.util.Date;
import java.util.List;

/**
 * @author Cosmin Baciu
 * @since 3.3
 */
//TODO create a model agnostic of peristence/JAXB annotations for exposing the UserMessage details
public interface UserMessageService {

    String getFinalRecipient(final String messageId);

    List<String> getFailedMessages();

    List<String> getFailedMessages(String finalRecipient);

    Long getFailedMessageElapsedTime(String messageId);

    void restoreFailedMessage(String messageId);

    List<String> restoreFailedMessagesDuringPeriod(Date begin, Date end);

    List<String> restoreFailedMessagesDuringPeriod(Date begin, Date end, String finalRecipient);

    void deleteFailedMessage(String messageId);
}
