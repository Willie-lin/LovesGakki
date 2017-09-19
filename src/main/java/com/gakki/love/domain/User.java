package com.gakki.love.domain;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.redis.listener.Topic;
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter;
import sun.plugin2.main.client.PrintBandDescriptor;

import javax.management.Notification;
import javax.persistence.*;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.PrivilegedAction;
import java.util.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 林漠
 * \* Date: 2017/9/13
 * \* Time: 9:34
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Entity
@Data
public class User implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * uid
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
    用户头像
     */
    @Column
    private String head;

    /**
    用户签名
     */
    @Column
    private String says;

    /**
     * 用户名
     */
    @Column(unique = true,nullable = false)
    private String username;

    /**
     * 用户别名
     */
    @Column(unique = true)
    private String alias;

    /**
     *用户信息不完整，首页显示注册导航
     */
    @Column(name = "regist_nav",columnDefinition = "BTE DEFAULT 1")
    private boolean registNav;

    /**
     * 密码
     */
    @Column(nullable = false)
    private String password;

    /**
     * QQ号码
     */
    @Column(name = "qq_num")
    private String qqNum;

    /**
     * 微博账号
     */
    @Column(name = "weibo_num")
    private String weiboNum;

    /**
     * 邮箱账号
     */
    @Column(unique = true,nullable = false)
    private String email;
    /**
     * 过期时间
     */
    @Column
    private String expireTime;
    /**
     * 性别
     */
    @Column
    private String sex;
    /**
     * 年龄
     */
    @Column
    private Integer age;
    /**
     * 住址
     */
    @Column
    private String address;
    /**
     * 手机号码
     */
    @Column(length = 11)
    private String telephone;
    /**
     * sessionid
     */
    @Column
    private String sessionid;
    /**
     * 创建时间
     */
    @CreationTimestamp
    private Date createTime;
    /**
     * 更新时间
     */
    @UpdateTimestamp
    private Date updateTime;

    /**
     * 一对多策略
     */
    @Column
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private List<Roles> roles;

    /**
     * 分享
     * 按时间降序排列
     */
    @OrderBy(value = "date desc")
    @OneToMany(cascade = { CascadeType.ALL},mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Topic> topics = new HashSet<>();

    /**
     * 关注
     */
    @OneToMany(cascade = { CascadeType.ALL},mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Follow> follows;

    /**
     * 收藏
     */
    @OneToMany(cascade = { CascadeType.ALL},mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Favourite> favourites;

    /**
     * 通知
     */
    @OrderBy(value = "date desc ")
    @OneToMany(cascade = { CascadeType.ALL},mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Notify> notifies;
    @Transient
    private Set<Notify> readedNotifies = new HashSet<>();

    public Date getCreateTime() {
        return (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = new Date(createTime.getTime());
    }

    public Date getUpdateTime() {
        return (Date) updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = new Date(updateTime.getTime());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", head='" + head + '\'' +
                ", says='" + says + '\'' +
                ", username='" + username + '\'' +
                ", alias='" + alias + '\'' +
                ", registNav=" + registNav +
                ", password='" + password + '\'' +
                ", qqNum='" + qqNum + '\'' +
                ", weiboNum='" + weiboNum + '\'' +
                ", email='" + email + '\'' +
                ", expireTime='" + expireTime + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", sessionid='" + sessionid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (registNav != user.registNav) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (head != null ? !head.equals(user.head) : user.head != null) return false;
        if (says != null ? !says.equals(user.says) : user.says != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (alias != null ? !alias.equals(user.alias) : user.alias != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (qqNum != null ? !qqNum.equals(user.qqNum) : user.qqNum != null) return false;
        if (weiboNum != null ? !weiboNum.equals(user.weiboNum) : user.weiboNum != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (expireTime != null ? !expireTime.equals(user.expireTime) : user.expireTime != null) return false;
        if (sex != null ? !sex.equals(user.sex) : user.sex != null) return false;
        if (age != null ? !age.equals(user.age) : user.age != null) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (telephone != null ? !telephone.equals(user.telephone) : user.telephone != null) return false;
        return sessionid != null ? sessionid.equals(user.sessionid) : user.sessionid == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (head != null ? head.hashCode() : 0);
        result = 31 * result + (says != null ? says.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (registNav ? 1 : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (qqNum != null ? qqNum.hashCode() : 0);
        result = 31 * result + (weiboNum != null ? weiboNum.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (expireTime != null ? expireTime.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (sessionid != null ? sessionid.hashCode() : 0);
        return result;
    }
}