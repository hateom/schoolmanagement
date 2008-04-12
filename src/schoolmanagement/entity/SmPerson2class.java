/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author jasiu
 */
@Entity
@Table(name = "SM_PERSON2CLASS")
@NamedQueries({@NamedQuery(name = "SmPerson2class.findByP2cId", query = "SELECT s FROM SmPerson2class s WHERE s.p2cId = :p2cId")})
public class SmPerson2class implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "P2C_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer p2cId;
    @OneToMany(mappedBy = "notP2cId")
    private Collection<SmNote> smNoteCollection;
    @JoinColumn(name = "P2C_PER_ID", referencedColumnName = "PER_ID")
    @ManyToOne
    private SmPerson p2cPerId;
    @JoinColumn(name = "P2C_CLS_ID", referencedColumnName = "CLS_ID")
    @ManyToOne
    private SmClass p2cClsId;

    public SmPerson2class() {
    }

    public SmPerson2class(Integer p2cId) {
        this.p2cId = p2cId;
    }

    public Integer getP2cId() {
        return p2cId;
    }

    public void setP2cId(Integer p2cId) {
        this.p2cId = p2cId;
    }

    public Collection<SmNote> getSmNoteCollection() {
        return smNoteCollection;
    }

    public void setSmNoteCollection(Collection<SmNote> smNoteCollection) {
        this.smNoteCollection = smNoteCollection;
    }

    public SmPerson getP2cPerId() {
        return p2cPerId;
    }

    public void setP2cPerId(SmPerson p2cPerId) {
        this.p2cPerId = p2cPerId;
    }

    public SmClass getP2cClsId() {
        return p2cClsId;
    }

    public void setP2cClsId(SmClass p2cClsId) {
        this.p2cClsId = p2cClsId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (p2cId != null ? p2cId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmPerson2class)) {
            return false;
        }
        SmPerson2class other = (SmPerson2class) object;
        if ((this.p2cId == null && other.p2cId != null) || (this.p2cId != null && !this.p2cId.equals(other.p2cId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "schoolmanagement.entity.SmPerson2class[p2cId=" + p2cId + "]";
    }

}
