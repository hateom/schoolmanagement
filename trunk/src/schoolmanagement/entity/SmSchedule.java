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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author jasiu
 */
@Entity
@Table(name = "SM_SCHEDULE")
@NamedQueries({@NamedQuery(name = "SmSchedule.findBySchId", query = "SELECT s FROM SmSchedule s WHERE s.schId = :schId")})
public class SmSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "SCH_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schId;
    @JoinColumn(name = "SCH_TCH_ID", referencedColumnName = "TCH_ID")
    @ManyToOne
    private SmTeacher schTchId;
    @JoinColumn(name = "SCH_DAY_ID", referencedColumnName = "DAY_ID")
    @ManyToOne
    private SmDay schDayId;
    @JoinColumn(name = "SCH_RNG_ID", referencedColumnName = "RNG_ID")
    @ManyToOne
    private SmRing schRngId;
    @JoinColumn(name = "SCH_CLS_ID", referencedColumnName = "CLS_ID")
    @ManyToOne
    private SmClass schClsId;
    @JoinColumn(name = "SCH_CLR_ID", referencedColumnName = "CLR_ID")
    @ManyToOne
    private SmClassroom schClrId;

    public SmSchedule() {
    }

    public SmSchedule(Integer schId) {
        this.schId = schId;
    }

    public Integer getSchId() {
        return schId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }

    public SmTeacher getSchTchId() {
        return schTchId;
    }

    public void setSchTchId(SmTeacher schTchId) {
        this.schTchId = schTchId;
    }

    public SmDay getSchDayId() {
        return schDayId;
    }

    public void setSchDayId(SmDay schDayId) {
        this.schDayId = schDayId;
    }

    public SmRing getSchRngId() {
        return schRngId;
    }

    public void setSchRngId(SmRing schRngId) {
        this.schRngId = schRngId;
    }

    public SmClass getSchClsId() {
        return schClsId;
    }

    public void setSchClsId(SmClass schClsId) {
        this.schClsId = schClsId;
    }

    public SmClassroom getSchClrId() {
        return schClrId;
    }

    public void setSchClrId(SmClassroom schClrId) {
        this.schClrId = schClrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schId != null ? schId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmSchedule)) {
            return false;
        }
        SmSchedule other = (SmSchedule) object;
        if ((this.schId == null && other.schId != null) || (this.schId != null && !this.schId.equals(other.schId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return schTchId.getTchSubId().getSubName()+" / "+schTchId.getTchPerId().getPerSurname();
    }

}
