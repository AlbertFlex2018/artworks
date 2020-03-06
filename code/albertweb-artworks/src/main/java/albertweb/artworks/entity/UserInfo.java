package albertweb.artworks.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@NamedQuery(name="entity.userinfo.selectall",
            query="SELECT e FROM UserInfo e ")
@XmlRootElement(name = "UserInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class UserInfo implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdTime;
    @OneToMany
    @XmlTransient
    private List<WorkInfo> works;
    @OneToMany
    @XmlTransient
    private List<MailInfo> mails;

    public UserInfo() {
        works=new ArrayList<>();
        mails=new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<WorkInfo> getWorks() {
        return works;
    }

    public void setWorks(List<WorkInfo> works) {
        this.works = works;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public List<MailInfo> getMails() {
        return mails;
    }

    public void setMails(List<MailInfo> mails) {
        this.mails = mails;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkInfo)) {
            return false;
        }
        UserInfo other = (UserInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }    
}
