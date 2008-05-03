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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author jasiu
 */
@Entity
@Table(name = "SM_SUBJECT")
@NamedQueries({@NamedQuery(name = "SmSubject.findBySubId", query = "SELECT s FROM SmSubject s WHERE s.subId = :subId"), @NamedQuery(name = "SmSubject.findBySubName", query = "SELECT s FROM SmSubject s WHERE s.subName = :subName")})
public class SmSubject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "SUB_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subId;
    @Column(name = "SUB_NAME", nullable = false)
    private String subName;
    @OneToMany(mappedBy = "tchSubId")
    private Collection<SmTeacher> smTeacherCollection;

    public SmSubject() {
    }

    public SmSubject(Integer subId) {
        this.subId = subId;
    }

    public SmSubject(Integer subId, String subName) {
        this.subId = subId;
        this.subName = subName;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Collection<SmTeacher> getSmTeacherCollection() {
        return smTeacherCollection;
    }

    public void setSmTeacherCollection(Collection<SmTeacher> smTeacherCollection) {
        this.smTeacherCollection = smTeacherCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subId != null ? subId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmSubject)) {
            return false;
        }
        SmSubject other = (SmSubject) object;
        if ((this.subId == null && other.subId != null) || (this.subId != null && !this.subId.equals(other.subId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return subName;
    }

}
