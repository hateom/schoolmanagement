/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jasiu
 */
@Entity
@Table(name = "SM_MESSAGE")
@NamedQueries({@NamedQuery(name = "SmMessage.findByMsgId", query = "SELECT s FROM SmMessage s WHERE s.msgId = :msgId"), @NamedQuery(name = "SmMessage.findByMsgSendDate", query = "SELECT s FROM SmMessage s WHERE s.msgSendDate = :msgSendDate"), @NamedQuery(name = "SmMessage.findByMsgParentId", query = "SELECT s FROM SmMessage s WHERE s.msgParentId = :msgParentId"), @NamedQuery(name = "SmMessage.findByMsgChildId", query = "SELECT s FROM SmMessage s WHERE s.msgChildId = :msgChildId"), @NamedQuery(name = "SmMessage.findByMsgRespReq", query = "SELECT s FROM SmMessage s WHERE s.msgRespReq = :msgRespReq"), @NamedQuery(name = "SmMessage.findByMsgReaded", query = "SELECT s FROM SmMessage s WHERE s.msgReaded = :msgReaded")})
public class SmMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "MSG_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer msgId;
    @Column(name = "MSG_SEND_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date msgSendDate;
    @Lob
    @Column(name = "MSG_BODY")
    private String msgBody;
    @Lob
    @Column(name = "MSG_TOPIC")
    private String msgTopic;
    @Column(name = "MSG_PARENT_ID")
    private Integer msgParentId;
    @Column(name = "MSG_CHILD_ID")
    private Integer msgChildId;
    @Column(name = "MSG_RESP_REQ")
    private Boolean msgRespReq;
    @Column(name = "MSG_READED")
    private Boolean msgReaded;
    @JoinColumn(name = "MSG_RECP_USR_ID", referencedColumnName = "USR_ID")
    @ManyToOne
    private SmUser msgRecpUsrId;
    @JoinColumn(name = "MSG_SENDER_USR_ID", referencedColumnName = "USR_ID")
    @ManyToOne
    private SmUser msgSenderUsrId;

    public SmMessage() {
    }

    public SmMessage(Integer msgId) {
        this.msgId = msgId;
    }

    public SmMessage(Integer msgId, Date msgSendDate) {
        this.msgId = msgId;
        this.msgSendDate = msgSendDate;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Date getMsgSendDate() {
        return msgSendDate;
    }

    public void setMsgSendDate(Date msgSendDate) {
        this.msgSendDate = msgSendDate;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getMsgTopic() {
        return msgTopic;
    }

    public void setMsgTopic(String msgTopic) {
        this.msgTopic = msgTopic;
    }

    public Integer getMsgParentId() {
        return msgParentId;
    }

    public void setMsgParentId(Integer msgParentId) {
        this.msgParentId = msgParentId;
    }

    public Integer getMsgChildId() {
        return msgChildId;
    }

    public void setMsgChildId(Integer msgChildId) {
        this.msgChildId = msgChildId;
    }

    public Boolean getMsgRespReq() {
        return msgRespReq;
    }

    public void setMsgRespReq(Boolean msgRespReq) {
        this.msgRespReq = msgRespReq;
    }

    public Boolean getMsgReaded() {
        return msgReaded;
    }

    public void setMsgReaded(Boolean msgReaded) {
        this.msgReaded = msgReaded;
    }

    public SmUser getMsgRecpUsrId() {
        return msgRecpUsrId;
    }

    public void setMsgRecpUsrId(SmUser msgRecpUsrId) {
        this.msgRecpUsrId = msgRecpUsrId;
    }

    public SmUser getMsgSenderUsrId() {
        return msgSenderUsrId;
    }

    public void setMsgSenderUsrId(SmUser msgSenderUsrId) {
        this.msgSenderUsrId = msgSenderUsrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msgId != null ? msgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmMessage)) {
            return false;
        }
        SmMessage other = (SmMessage) object;
        if ((this.msgId == null && other.msgId != null) || (this.msgId != null && !this.msgId.equals(other.msgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "schoolmanagement.entity.SmMessage[msgId=" + msgId + "]";
    }

}
