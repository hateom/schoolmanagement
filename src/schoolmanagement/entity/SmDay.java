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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author jasiu
 */
@Entity
@Table(name = "SM_DAY")
@NamedQueries({@NamedQuery(name = "SmDay.findByDayId", query = "SELECT s FROM SmDay s WHERE s.dayId = :dayId"), @NamedQuery(name = "SmDay.findByDayName", query = "SELECT s FROM SmDay s WHERE s.dayName = :dayName")})
public class SmDay implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "DAY_ID", nullable = false)
    private Integer dayId;
    @Column(name = "DAY_NAME")
    private String dayName;
    @OneToMany(mappedBy = "schDayId")
    private Collection<SmSchedule> smScheduleCollection;

    public SmDay() {
    }

    public SmDay(Integer dayId) {
        this.dayId = dayId;
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
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
        hash += (dayId != null ? dayId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmDay)) {
            return false;
        }
        SmDay other = (SmDay) object;
        if ((this.dayId == null && other.dayId != null) || (this.dayId != null && !this.dayId.equals(other.dayId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "schoolmanagement.entity.SmDay[dayId=" + dayId + "]";
    }

}
