/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
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
@Table(name = "SM_USER")
@NamedQueries({@NamedQuery(name = "SmUser.findByUsrId", query = "SELECT s FROM SmUser s WHERE s.usrId = :usrId"), @NamedQuery(name = "SmUser.findByUsrLogin", query = "SELECT s FROM SmUser s WHERE s.usrLogin = :usrLogin"), @NamedQuery(name = "SmUser.findByUsrPasswd", query = "SELECT s FROM SmUser s WHERE s.usrPasswd = :usrPasswd"), @NamedQuery(name = "SmUser.findByUsrIslogged", query = "SELECT s FROM SmUser s WHERE s.usrIslogged = :usrIslogged")})
public class SmUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "USR_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usrId;
    @Column(name = "USR_LOGIN", nullable = false)
    private String usrLogin;
    @Column(name = "USR_PASSWD", nullable = false)
    private String usrPasswd;
    @Column(name = "USR_ISLOGGED", nullable = false)
    private boolean usrIslogged;
    @JoinColumn(name = "USR_PER_ID", referencedColumnName = "PER_ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private SmPerson usrPerId;
    @JoinColumn(name = "USR_ROL_ID", referencedColumnName = "ROL_ID")
    @ManyToOne
    private SmRole usrRolId;
    @OneToMany(mappedBy = "msgRecpUsrId")
    private Collection<SmMessage> smMessageCollection;
    @OneToMany(mappedBy = "msgSenderUsrId")
    private Collection<SmMessage> smMessageCollection1;

    public SmUser() {
    }

    public SmUser(Integer usrId) {
        this.usrId = usrId;
    }

    public SmUser(Integer usrId, String usrLogin, String usrPasswd, boolean usrIslogged) {
        this.usrId = usrId;
        this.usrLogin = usrLogin;
        this.usrPasswd = usrPasswd;
        this.usrIslogged = usrIslogged;
    }

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public String getUsrLogin() {
        return usrLogin;
    }

    public void setUsrLogin(String usrLogin) {
        this.usrLogin = usrLogin;
    }

    public String getUsrPasswd() {
        return usrPasswd;
    }

    public void setUsrPasswd(String usrPasswd) {
        this.usrPasswd = usrPasswd;
    }

    public boolean getUsrIslogged() {
        return usrIslogged;
    }

    public void setUsrIslogged(boolean usrIslogged) {
        this.usrIslogged = usrIslogged;
    }

    public SmPerson getUsrPerId() {
        return usrPerId;
    }

    public void setUsrPerId(SmPerson usrPerId) {
        this.usrPerId = usrPerId;
    }

    public SmRole getUsrRolId() {
        return usrRolId;
    }

    public void setUsrRolId(SmRole usrRolId) {
        this.usrRolId = usrRolId;
    }

    public Collection<SmMessage> getSmMessageCollection() {
        return smMessageCollection;
    }

    public void setSmMessageCollection(Collection<SmMessage> smMessageCollection) {
        this.smMessageCollection = smMessageCollection;
    }

    public Collection<SmMessage> getSmMessageCollection1() {
        return smMessageCollection1;
    }

    public void setSmMessageCollection1(Collection<SmMessage> smMessageCollection1) {
        this.smMessageCollection1 = smMessageCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrId != null ? usrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmUser)) {
            return false;
        }
        SmUser other = (SmUser) object;
        if ((this.usrId == null && other.usrId != null) || (this.usrId != null && !this.usrId.equals(other.usrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "schoolmanagement.entity.SmUser[usrId=" + usrId + "]";
    }

}
