/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.entity;

import java.io.Serializable;
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

/**
 *
 * @author jasiu
 */
@Entity
@Table(name = "SM_PERSON_NOTES")
@NamedQueries({@NamedQuery(name = "SmPersonNotes.findByPnId", query = "SELECT s FROM SmPersonNotes s WHERE s.pnId = :pnId")})
public class SmPersonNotes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PN_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pnId;
    @Lob
    @Column(name = "PN_NOTE")
    private String pnNote;
    @JoinColumn(name = "PN_PER_ID", referencedColumnName = "PER_ID")
    @ManyToOne
    private SmPerson pnPerId;

    public SmPersonNotes() {
    }

    public SmPersonNotes(Integer pnId) {
        this.pnId = pnId;
    }

    public Integer getPnId() {
        return pnId;
    }

    public void setPnId(Integer pnId) {
        this.pnId = pnId;
    }

    public String getPnNote() {
        return pnNote;
    }

    public void setPnNote(String pnNote) {
        this.pnNote = pnNote;
    }

    public SmPerson getPnPerId() {
        return pnPerId;
    }

    public void setPnPerId(SmPerson pnPerId) {
        this.pnPerId = pnPerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pnId != null ? pnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmPersonNotes)) {
            return false;
        }
        SmPersonNotes other = (SmPersonNotes) object;
        if ((this.pnId == null && other.pnId != null) || (this.pnId != null && !this.pnId.equals(other.pnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "schoolmanagement.entity.SmPersonNotes[pnId=" + pnId + "]";
    }

}
