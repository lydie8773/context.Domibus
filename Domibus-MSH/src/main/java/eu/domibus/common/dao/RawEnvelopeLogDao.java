package eu.domibus.common.dao;

import eu.domibus.common.model.logging.RawEnvelopeDto;
import eu.domibus.common.model.logging.RawEnvelopeLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

import java.util.List;

import static org.springframework.util.StringUtils.hasLength;

/**
 * @author idragusa
 * @since 3.2.5
 */
@Repository
public class RawEnvelopeLogDao extends BasicDao<RawEnvelopeLog> {

    private static final Log LOG = LogFactory.getLog(RawEnvelopeLogDao.class);

    public RawEnvelopeLogDao() {
        super(RawEnvelopeLog.class);
    }

    //@thom test this class
    public List<RawEnvelopeDto> findRawXmlByMessageId(final String messageId){
        TypedQuery<RawEnvelopeDto> namedQuery = em.createNamedQuery("find.by.message.id", RawEnvelopeDto.class);
        namedQuery.setParameter("MESSAGE_ID",messageId);
        return namedQuery.getResultList();
    }

    public void deleteRawMessage(final int id){
        RawEnvelopeLog read = read(id);
        delete(read);
    }


}