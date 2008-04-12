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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author jasiu
 */
@Entity
@Table(name = "SM_PERSONS")
@NamedQueries({@NamedQuery(name = "SmPersons.findByPerId", query = "SELECT s FROM SmPersons s WHERE s.perId = :perId"), @NamedQuery(name = "SmPersons.findByPerName", query = "SELECT s FROM SmPersons s WHERE s.perName = :perName"), @NamedQuery(name = "SmPersons.findByPerSurname", query = "SELECT s FROM SmPersons s WHERE s.perSurname = :perSurname")})
public class SmPersons implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PER_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer perId;
    @Column(name = "PER_NAME", nullable = false)
    private String perName;
    @Column(name = "PER_SURNAME", nullable = false)
    private String perSurname;

    public SmPersons() {
    }

    public SmPersons(Integer perId) {
        this.perId = perId;
    }

    public SmPersons(Integer perId, String perName, String perSurname) {
        this.perId = perId;
        this.perName = perName;
        this.perSurname = perSurname;
    }

    public Integer getPerId() {
        return perId;
    }

    public void setPerId(Integer perId) {
        this.perId = perId;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public String getPerSurname() {
        return perSurname;
    }

    public void setPerSurname(String perSurname) {
        this.perSurname = perSurname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perId != null ? perId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmPersons)) {
            return false;
        }
        SmPersons other = (SmPersons) object;
        if ((this.perId == null && other.perId != null) || (this.perId != null && !this.perId.equals(other.perId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "schoolmanagement.dataaccess.SmPersons[perId=" + perId + "]";
    }

}
