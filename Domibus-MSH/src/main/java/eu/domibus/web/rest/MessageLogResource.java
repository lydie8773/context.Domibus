package eu.domibus.web.rest;

import eu.domibus.api.util.DateUtil;
import eu.domibus.common.MSHRole;
import eu.domibus.common.MessageStatus;
import eu.domibus.common.NotificationStatus;
import eu.domibus.common.dao.SignalMessageLogDao;
import eu.domibus.common.dao.UserMessageLogDao;
import eu.domibus.common.model.logging.MessageLog;
import eu.domibus.common.model.logging.MessageLogInfo;
import eu.domibus.common.model.logging.SignalMessageLog;
import eu.domibus.common.model.logging.UserMessageLog;
import eu.domibus.ebms3.common.model.MessageType;
import eu.domibus.web.rest.ro.MessageLogRO;
import eu.domibus.web.rest.ro.MessageLogResultRO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Tiago Miguel
 * @since 3.3
 */
@RestController
@RequestMapping(value = "/rest/messagelog")
public class MessageLogResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageLogResource.class);

    @Autowired
    private UserMessageLogDao userMessageLogDao;

    @Autowired
    private SignalMessageLogDao signalMessageLogDao;

    @Autowired
    DateUtil dateUtil;

    @RequestMapping(method = RequestMethod.GET)
    public MessageLogResultRO getMessageLog(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "orderBy", required = false) String column,
            @RequestParam(value = "asc", defaultValue = "true") boolean asc,
            @RequestParam(value = "messageId", required = false) String messageId,
            @RequestParam(value = "conversationId", required = false) String conversationId,
            @RequestParam(value = "mshRole", required = false) MSHRole mshRole,
            @RequestParam(value = "messageType", defaultValue = "USER_MESSAGE") MessageType messageType,
            @RequestParam(value = "messageStatus", required = false) MessageStatus messageStatus,
            @RequestParam(value = "notificationStatus", required = false) NotificationStatus notificationStatus,
            @RequestParam(value = "fromPartyId", required = false) String fromPartyId,
            @RequestParam(value = "toPartyId", required = false) String toPartyId,
            @RequestParam(value = "refToMessageId", required = false) String refToMessageId,
            @RequestParam(value = "originalSender", required = false) String originalSender,
            @RequestParam(value = "finalRecipient", required = false) String finalRecipient,
            @RequestParam(value = "receivedFrom", required = false) String receivedFrom,
            @RequestParam(value = "receivedTo", required = false) String receivedTo) {

        LOGGER.debug("Getting message log");

        MessageLogResultRO result = new MessageLogResultRO();

        HashMap<String, Object> filters = new HashMap<>();
        filters.put("messageId", messageId);
        filters.put("conversationId", conversationId);
        filters.put("mshRole", mshRole);
        filters.put("messageType", messageType);
        filters.put("messageStatus", messageStatus);
        filters.put("notificationStatus", notificationStatus);
        filters.put("fromPartyId", fromPartyId);
        filters.put("toPartyId", toPartyId);
        filters.put("refToMessageId", refToMessageId);
        filters.put("originalSender", originalSender);
        filters.put("finalRecipient", finalRecipient);
        filters.put("receivedFrom", dateUtil.fromString(receivedFrom));
        filters.put("receivedTo", dateUtil.fromString(receivedTo));

        result.setFilter(filters);
        LOGGER.debug("using filters [{}]", filters);

        List<MessageLogInfo> resultList = new ArrayList<>();
        switch(messageType) {
            case SIGNAL_MESSAGE:
                int numberOfSignalMessageLogs = signalMessageLogDao.countAllInfo(column, asc, filters);
                LOGGER.debug("count Signal Messages Logs [{}]", numberOfSignalMessageLogs);
                result.setCount(numberOfSignalMessageLogs);
                resultList = signalMessageLogDao.findAllInfoPaged(pageSize * page, pageSize, column, asc, filters);
                break;
            case USER_MESSAGE:
                int numberOfUserMessageLogs = userMessageLogDao.countAllInfo(column, asc, filters);
                LOGGER.debug("count User Messages Logs [{}]", numberOfUserMessageLogs);
                result.setCount(numberOfUserMessageLogs);
                resultList = userMessageLogDao.findAllInfoPaged(pageSize * page, pageSize, column, asc, filters);
                break;
        }
        result.setMessageLogEntries(convertMessageLogInfoList(resultList));
        result.setMshRoles(MSHRole.values());
        result.setMsgTypes(MessageType.values());
        result.setMsgStatus(MessageStatus.values());
        result.setNotifStatus(NotificationStatus.values());
        result.setPage(page);
        result.setPageSize(pageSize);

        return result;
    }

    protected List<MessageLogRO> convertMessageLogInfoList(List<MessageLogInfo> objects) {
        List<MessageLogRO> result = new ArrayList<>();
        for(MessageLogInfo object : objects) {
            final MessageLogRO messageLogRO = convertMessageLogInfo(object);
            if(messageLogRO != null) {
                result.add(messageLogRO);
            }
        }
        return result;
    }

    private void setSpecificMessageLogInfo(MessageLogRO messageLogRO, MessageLog messageLog) {
        messageLogRO.setMessageId(messageLog.getMessageId());
        messageLogRO.setMessageStatus(messageLog.getMessageStatus());
        messageLogRO.setNotificationStatus(messageLog.getNotificationStatus());
        messageLogRO.setMshRole(messageLog.getMshRole());
        messageLogRO.setMessageType(messageLog.getMessageType());
        messageLogRO.setDeleted(messageLog.getDeleted());
        messageLogRO.setReceived(messageLog.getReceived());
        messageLogRO.setSendAttempts(messageLog.getSendAttempts());
        messageLogRO.setSendAttemptsMax(messageLog.getSendAttemptsMax());
        messageLogRO.setNextAttempt(messageLog.getNextAttempt());
    }

    private MessageLogRO convertMessageLogInfo(MessageLogInfo messageLogInfo) {
        if(messageLogInfo == null) {
            return null;
        }

        MessageLogRO result = new MessageLogRO();
        result.setConversationId(messageLogInfo.getConversationId());
        result.setFromPartyId(messageLogInfo.getFromPartyId());
        result.setToPartyId(messageLogInfo.getToPartyId());
        result.setOriginalSender(messageLogInfo.getOriginalSender());
        result.setFinalRecipient(messageLogInfo.getFinalRecipient());
        result.setRefToMessageId(messageLogInfo.getRefToMessageId());
        UserMessageLog userMessageLog = messageLogInfo.getUserMessageLog();
        SignalMessageLog signalMessageLog = messageLogInfo.getSignalMessageLog();
        if (userMessageLog != null) {
            setSpecificMessageLogInfo(result, userMessageLog);
        } else if(signalMessageLog != null) {
            setSpecificMessageLogInfo(result, signalMessageLog);
        } else {
            LOGGER.error("Message Log Info doesn't contain neither User nor Signal message.");
        }
        return result;
    }
}