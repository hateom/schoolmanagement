/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jasiu
 */
@Entity
@Table(name = "SM_RING")
@NamedQueries({@NamedQuery(name = "SmRing.findByRngId", query = "SELECT s FROM SmRing s WHERE s.rngId = :rngId"), @NamedQuery(name = "SmRing.findByRngTime", query = "SELECT s FROM SmRing s WHERE s.rngTime = :rngTime")})
public class SmRing implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "RNG_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rngId;
    @Column(name = "RNG_TIME", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date rngTime;
    @OneToMany(mappedBy = "schRngId")
    private Collection<SmSchedule> smScheduleCollection;

    public SmRing() {
    }

    public SmRing(Integer rngId) {
        this.rngId = rngId;
    }

    public SmRing(Integer rngId, Date rngTime) {
        this.rngId = rngId;
        this.rngTime = rngTime;
    }

    public Integer getRngId() {
        return rngId;
    }

    public void setRngId(Integer rngId) {
        this.rngId = rngId;
    }

    public Date getRngTime() {
        return rngTime;
    }

    public void setRngTime(Date rngTime) {
        this.rngTime = rngTime;
    }

    public Collection<SmSchedule> getSmScheduleCollection() {
        return smScheduleCollection;
    }

    public void setSmScheduleCollection(Collection<SmSchedule> smScheduleCollection) {
        this.smScheduleCollection = smScheduleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rngId != null ? rngId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmRing)) {
            return false;
        }
        SmRing other = (SmRing) object;
        if ((this.rngId == null && other.rngId != null) || (this.rngId != null && !this.rngId.equals(other.rngId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "schoolmanagement.entity.SmRing[rngId=" + rngId + "]";
    }

}
