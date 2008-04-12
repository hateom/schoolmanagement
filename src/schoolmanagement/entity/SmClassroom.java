/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "SM_CLASSROOM")
@NamedQueries({@NamedQuery(name = "SmClassroom.findByClrId", query = "SELECT s FROM SmClassroom s WHERE s.clrId = :clrId"), @NamedQuery(name = "SmClassroom.findByClrDescr", query = "SELECT s FROM SmClassroom s WHERE s.clrDescr = :clrDescr")})
public class SmClassroom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CLR_ID", nullable = false)
    private Integer clrId;
    @Column(name = "CLR_DESCR")
    private String clrDescr;
    @OneToMany(mappedBy = "schClrId")
    private Collection<SmSchedule> smScheduleCollection;
    @JoinColumn(name = "CLR_OWNER_PER_ID", referencedColumnName = "PER_ID")
    @ManyToOne
    private SmPerson clrOwnerPerId;

    public SmClassroom() {
    }

    public SmClassroom(Integer clrId) {
        this.clrId = clrId;
    }

    public Integer getClrId() {
        return clrId;
    }

    public void setClrId(Integer clrId) {
        this.clrId = clrId;
    }

    public String getClrDescr() {
        return clrDescr;
    }

    public void setClrDescr(String clrDescr) {
        this.clrDescr = clrDescr;
    }

    public Collection<SmSchedule> getSmScheduleCollection() {
        return smScheduleCollection;
    }

    public void setSmScheduleCollection(Collection<SmSchedule> smScheduleCollection) {
        this.smScheduleCollection = smScheduleCollection;
    }

    public SmPerson getClrOwnerPerId() {
        return clrOwnerPerId;
    }

    public void setClrOwnerPerId(SmPerson clrOwnerPerId) {
        this.clrOwnerPerId = clrOwnerPerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clrId != null ? clrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmClassroom)) {
            return false;
        }
        SmClassroom other = (SmClassroom) object;
        if ((this.clrId == null && other.clrId != null) || (this.clrId != null && !this.clrId.equals(other.clrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "schoolmanagement.entity.SmClassroom[clrId=" + clrId + "]";
    }

}
