package com.tapestry5book.entities;

import java.net.URL;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.ioc.annotations.Inject;
//import org.apache.tapestry5.beaneditor.DataType;

@Entity
public class User {
    @Id
    @GeneratedValue
    @NonVisual
    private Long id;

    @Column
    @Validate("required")
    private String name;

    @Column(name = "PASSWD")
    @Validate("required")
    //@DataType("password")
    //@Validate("required,minlength=5,regexp=[a-zA-Z]+")
    private String password;

    @Column
    private boolean rememberMe;

    @Column
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Enumerated (EnumType.STRING)
    private Gender gender;

    @Column
    private URL website;

    @Column
    @Temporal(TemporalType.DATE)
    private Date passwordChanged;


    @Inject
    public User(){
        super();
        passwordChanged = new Date();
    }

    public User(String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public URL getWebsite() {
        return website;
    }

    public void setWebsite(URL website) {
        this.website = website;
    }

    public Date getPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(Date passwordChanged) {
        this.passwordChanged = passwordChanged;
    }
}
