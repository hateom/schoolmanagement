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
@Table(name = "SM_NOTE")
@NamedQueries({@NamedQuery(name = "SmNote.findByNotId", query = "SELECT s FROM SmNote s WHERE s.notId = :notId"), @NamedQuery(name = "SmNote.findByNotNote", query = "SELECT s FROM SmNote s WHERE s.notNote = :notNote")})
public class SmNote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "NOT_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notId;
    @Column(name = "NOT_NOTE")
    private String notNote;
    @Lob
    @Column(name = "NOT_COMMENT")
    private String notComment;
    @Column(name = "NOT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notDate;
    @JoinColumn(name = "NOT_P2C_ID", referencedColumnName = "P2C_ID")
    @ManyToOne
    private SmPerson2class notP2cId;
    @JoinColumn(name = "NOT_TCH_PER_ID", referencedColumnName = "PER_ID")
    @ManyToOne
    private SmPerson notTchPerId;
    @JoinColumn(name = "NOT_SUB_ID", referencedColumnName = "SUB_ID")
    @ManyToOne
    private SmSubject notSubId;

    public SmNote() {
    }

    public SmNote(Integer notId) {
        this.notId = notId;
    }

    public Integer getNotId() {
        return notId;
    }

    public void setNotId(Integer notId) {
        this.notId = notId;
    }

    public String getNotNote() {
        return notNote;
    }

    public void setNotNote(String notNote) {
        this.notNote = notNote;
    }
    public String getNotComment() {
        return notComment;
    }

    public void setNotComment(String notComment) {
        this.notComment = notComment;
    }

    public Date getNotDate() {
        return notDate;
    }

    public void setNotDate(Date notDate) {
        this.notDate = notDate;
    }
    
    public SmPerson2class getNotP2cId() {
        return notP2cId;
    }

    public void setNotP2cId(SmPerson2class notP2cId) {
        this.notP2cId = notP2cId;
    }
    
        public SmPerson getNotTchPerId() {
        return notTchPerId;
    }

    public void setNotTchPerId(SmPerson notTchPerId) {
        this.notTchPerId = notTchPerId;
    }

    public SmSubject getNotSubId() {
        return notSubId;
    }

    public void setNotSubId(SmSubject notSubId) {
        this.notSubId = notSubId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notId != null ? notId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmNote)) {
            return false;
        }
        SmNote other = (SmNote) object;
        if ((this.notId == null && other.notId != null) || (this.notId != null && !this.notId.equals(other.notId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return notNote;
    }

}
