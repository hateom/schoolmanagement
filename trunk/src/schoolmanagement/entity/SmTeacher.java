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
@Table(name = "SM_TEACHER")
@NamedQueries({@NamedQuery(name = "SmTeacher.findByTchId", query = "SELECT s FROM SmTeacher s WHERE s.tchId = :tchId")})
public class SmTeacher implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "TCH_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tchId;
    @OneToMany(mappedBy = "schTchId")
    private Collection<SmSchedule> smScheduleCollection;
    @OneToMany(mappedBy = "notTchId")
    private Collection<SmNote> smNoteCollection;
    @JoinColumn(name = "TCH_PER_ID", referencedColumnName = "PER_ID")
    @ManyToOne
    private SmPerson tchPerId;
    @JoinColumn(name = "TCH_SUB_ID", referencedColumnName = "SUB_ID")
    @ManyToOne
    private SmSubject tchSubId;

    public SmTeacher() {
    }

    public SmTeacher(Integer tchId) {
        this.tchId = tchId;
    }

    public Integer getTchId() {
        return tchId;
    }

    public void setTchId(Integer tchId) {
        this.tchId = tchId;
    }

    public Collection<SmSchedule> getSmScheduleCollection() {
        return smScheduleCollection;
    }

    public void setSmScheduleCollection(Collection<SmSchedule> smScheduleCollection) {
        this.smScheduleCollection = smScheduleCollection;
    }

    public Collection<SmNote> getSmNoteCollection() {
        return smNoteCollection;
    }

    public void setSmNoteCollection(Collection<SmNote> smNoteCollection) {
        this.smNoteCollection = smNoteCollection;
    }

    public SmPerson getTchPerId() {
        return tchPerId;
    }

    public void setTchPerId(SmPerson tchPerId) {
        this.tchPerId = tchPerId;
    }

    public SmSubject getTchSubId() {
        return tchSubId;
    }

    public void setTchSubId(SmSubject tchSubId) {
        this.tchSubId = tchSubId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tchId != null ? tchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmTeacher)) {
            return false;
        }
        SmTeacher other = (SmTeacher) object;
        if ((this.tchId == null && other.tchId != null) || (this.tchId != null && !this.tchId.equals(other.tchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "schoolmanagement.entity.SmTeacher[tchId=" + tchId + "]";
    }

}
