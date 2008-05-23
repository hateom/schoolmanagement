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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author jasiu
 */
@Entity
@Table(name = "SM_PERSON")
@NamedQueries({@NamedQuery(name = "SmPerson.findByPerId", query = "SELECT s FROM SmPerson s WHERE s.perId = :perId"), @NamedQuery(name = "SmPerson.findByPerName", query = "SELECT s FROM SmPerson s WHERE s.perName = :perName"), @NamedQuery(name = "SmPerson.findByPerSurname", query = "SELECT s FROM SmPerson s WHERE s.perSurname = :perSurname"), @NamedQuery(name = "SmPerson.findByPerPesel", query = "SELECT s FROM SmPerson s WHERE s.perPesel = :perPesel"), @NamedQuery(name = "SmPerson.findByPerNip", query = "SELECT s FROM SmPerson s WHERE s.perNip = :perNip"), @NamedQuery(name = "SmPerson.findByPerPhone", query = "SELECT s FROM SmPerson s WHERE s.perPhone = :perPhone"), @NamedQuery(name = "SmPerson.findByPerEmail", query = "SELECT s FROM SmPerson s WHERE s.perEmail = :perEmail")})
public class SmPerson implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PER_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer perId;
    @Column(name = "PER_NAME", nullable = false)
    private String perName;
    @Column(name = "PER_SURNAME", nullable = false)
    private String perSurname;
    @Column(name = "PER_PESEL", nullable = false)
    private int perPesel;
    @Column(name = "PER_NIP")
    private Integer perNip;
    @Column(name = "PER_PHONE")
    private Integer perPhone;
    @Lob
    @Column(name = "PER_ADRESS", nullable = false)
    private String perAdress;
    @Column(name = "PER_EMAIL")
    private String perEmail;
    @OneToMany(mappedBy = "schTchPerId")
    private Collection<SmSchedule> smScheduleCollection;
    @OneToMany(mappedBy = "pnPerId")
    private Collection<SmPersonNotes> smPersonNotesCollection;
    @OneToMany(mappedBy = "usrPerId",cascade = CascadeType.ALL)
    private Collection<SmUser> smUserCollection;
    @OneToMany(mappedBy = "clsPerId")
    private Collection<SmClass> smClassCollection;
    @OneToMany(mappedBy = "notTchPerId")
    private Collection<SmNote> smNoteCollection;
    @OneToMany(mappedBy = "clrOwnerPerId")
    private Collection<SmClassroom> smClassroomCollection;
    @OneToMany(mappedBy = "p2cPerId")
    private Collection<SmPerson2class> smPerson2classCollection;
    @OneToMany(mappedBy = "tchPerId")
    private Collection<SmTeacher> smTeacherCollection;

    public SmPerson() {
    }

    public SmPerson(Integer perId) {
        this.perId = perId;
    }

    public SmPerson(Integer perId, String perName, String perSurname, int perPesel, String perAdress) {
        this.perId = perId;
        this.perName = perName;
        this.perSurname = perSurname;
        this.perPesel = perPesel;
        this.perAdress = perAdress;
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

    public int getPerPesel() {
        return perPesel;
    }

    public void setPerPesel(int perPesel) {
        this.perPesel = perPesel;
    }

    public Integer getPerNip() {
        return perNip;
    }

    public void setPerNip(Integer perNip) {
        this.perNip = perNip;
    }

    public Integer getPerPhone() {
        return perPhone;
    }

    public void setPerPhone(Integer perPhone) {
        this.perPhone = perPhone;
    }

    public String getPerAdress() {
        return perAdress;
    }

    public void setPerAdress(String perAdress) {
        this.perAdress = perAdress;
    }

    public String getPerEmail() {
        return perEmail;
    }

    public void setPerEmail(String perEmail) {
        this.perEmail = perEmail;
    }

    public Collection<SmSchedule> getSmScheduleCollection() {
        return smScheduleCollection;
    }

    public void setSmScheduleCollection(Collection<SmSchedule> smScheduleCollection) {
        this.smScheduleCollection = smScheduleCollection;
    }
    public Collection<SmPersonNotes> getSmPersonNotesCollection() {
        return smPersonNotesCollection;
    }

    public void setSmPersonNotesCollection(Collection<SmPersonNotes> smPersonNotesCollection) {
        this.smPersonNotesCollection = smPersonNotesCollection;
    }

    public Collection<SmUser> getSmUserCollection() {
        return smUserCollection;
    }

    public void setSmUserCollection(Collection<SmUser> smUserCollection) {
        this.smUserCollection = smUserCollection;
    }

    public Collection<SmClass> getSmClassCollection() {
        return smClassCollection;
    }

    public void setSmClassCollection(Collection<SmClass> smClassCollection) {
        this.smClassCollection = smClassCollection;
    }

    public Collection<SmNote> getSmNoteCollection() {
        return smNoteCollection;
    }

    public void setSmNoteCollection(Collection<SmNote> smNoteCollection) {
        this.smNoteCollection = smNoteCollection;
    }
    
    public Collection<SmClassroom> getSmClassroomCollection() {
        return smClassroomCollection;
    }

    public void setSmClassroomCollection(Collection<SmClassroom> smClassroomCollection) {
        this.smClassroomCollection = smClassroomCollection;
    }

    public Collection<SmPerson2class> getSmPerson2classCollection() {
        return smPerson2classCollection;
    }

    public void setSmPerson2classCollection(Collection<SmPerson2class> smPerson2classCollection) {
        this.smPerson2classCollection = smPerson2classCollection;
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
        hash += (perId != null ? perId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmPerson)) {
            return false;
        }
        SmPerson other = (SmPerson) object;
        if ((this.perId == null && other.perId != null) || (this.perId != null && !this.perId.equals(other.perId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return perSurname + " " + perName;
    }

}
