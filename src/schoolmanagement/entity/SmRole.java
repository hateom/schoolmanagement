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
@Table(name = "SM_ROLE")
@NamedQueries({@NamedQuery(name = "SmRole.findByRolId", query = "SELECT s FROM SmRole s WHERE s.rolId = :rolId"), @NamedQuery(name = "SmRole.findByRolName", query = "SELECT s FROM SmRole s WHERE s.rolName = :rolName")})
public class SmRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ROL_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rolId;
    @Column(name = "ROL_NAME", nullable = false)
    private String rolName;
    @OneToMany(mappedBy = "usrRolId")
    private Collection<SmUser> smUserCollection;

    public SmRole() {
    }

    public SmRole(Integer rolId) {
        this.rolId = rolId;
    }

    public SmRole(Integer rolId, String rolName) {
        this.rolId = rolId;
        this.rolName = rolName;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public Collection<SmUser> getSmUserCollection() {
        return smUserCollection;
    }

    public void setSmUserCollection(Collection<SmUser> smUserCollection) {
        this.smUserCollection = smUserCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolId != null ? rolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmRole)) {
            return false;
        }
        SmRole other = (SmRole) object;
        if ((this.rolId == null && other.rolId != null) || (this.rolId != null && !this.rolId.equals(other.rolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return rolName;
    }

}
