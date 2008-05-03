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
@Table(name = "SM_CLASS")
@NamedQueries({@NamedQuery(name = "SmClass.findByClsId", query = "SELECT s FROM SmClass s WHERE s.clsId = :clsId"), @NamedQuery(name = "SmClass.findByClsNumber", query = "SELECT s FROM SmClass s WHERE s.clsNumber = :clsNumber"), @NamedQuery(name = "SmClass.findByClsNumberAlph", query = "SELECT s FROM SmClass s WHERE s.clsNumberAlph = :clsNumberAlph")})
public class SmClass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CLS_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clsId;
    @Column(name = "CLS_NUMBER")
    private Integer clsNumber;
    @Column(name = "CLS_NUMBER_ALPH")
    private Character clsNumberAlph;
    @OneToMany(mappedBy = "schClsId")
    private Collection<SmSchedule> smScheduleCollection;
    @JoinColumn(name = "CLS_PER_ID", referencedColumnName = "PER_ID")
    @ManyToOne
    private SmPerson clsPerId;
    @OneToMany(mappedBy = "p2cClsId")
    private Collection<SmPerson2class> smPerson2classCollection;

    public SmClass() {
    }

    public SmClass(Integer clsId) {
        this.clsId = clsId;
    }

    public Integer getClsId() {
        return clsId;
    }

    public void setClsId(Integer clsId) {
        this.clsId = clsId;
    }

    public Integer getClsNumber() {
        return clsNumber;
    }

    public void setClsNumber(Integer clsNumber) {
        this.clsNumber = clsNumber;
    }

    public Character getClsNumberAlph() {
        return clsNumberAlph;
    }

    public void setClsNumberAlph(Character clsNumberAlph) {
        this.clsNumberAlph = clsNumberAlph;
    }

    public Collection<SmSchedule> getSmScheduleCollection() {
        return smScheduleCollection;
    }

    public void setSmScheduleCollection(Collection<SmSchedule> smScheduleCollection) {
        this.smScheduleCollection = smScheduleCollection;
    }

    public SmPerson getClsPerId() {
        return clsPerId;
    }

    public void setClsPerId(SmPerson clsPerId) {
        this.clsPerId = clsPerId;
    }

    public Collection<SmPerson2class> getSmPerson2classCollection() {
        return smPerson2classCollection;
    }

    public void setSmPerson2classCollection(Collection<SmPerson2class> smPerson2classCollection) {
        this.smPerson2classCollection = smPerson2classCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clsId != null ? clsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmClass)) {
            return false;
        }
        SmClass other = (SmClass) object;
        if ((this.clsId == null && other.clsId != null) || (this.clsId != null && !this.clsId.equals(other.clsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return clsNumber +" "+clsNumberAlph;
    }

}
