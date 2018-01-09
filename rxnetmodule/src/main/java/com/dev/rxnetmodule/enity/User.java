package com.dev.rxnetmodule.enity;

import java.io.Serializable;

/**
 * model
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1589896482;

    private Integer id;
    private String loginId;
    private String telephone;
    private String pwd;
    private String name;
    private String sex;
    private String birthday;
    private String pic;
    private String eMail;
    private Boolean isActivate;
    private String activateTime;
    private Integer roleId;
    private Integer role;
    private Integer sourceType;
    private String createTime;
    private Boolean isDelete;

    private String sexName;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLoginId() {
        return loginId;
    }

    public User setLoginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public User setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public String getPwd() {
        return pwd;
    }

    public User setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public User setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getPic() {
        return pic;
    }

    public User setPic(String pic) {
        this.pic = pic;
        return this;
    }

    public String geteMail() {
        return eMail;
    }

    public User seteMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public Boolean getActivate() {
        return isActivate;
    }

    public User setActivate(Boolean activate) {
        isActivate = activate;
        return this;
    }

    public String getActivateTime() {
        return activateTime;
    }

    public User setActivateTime(String activateTime) {
        this.activateTime = activateTime;
        return this;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public User setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public Integer getRole() {
        return role;
    }

    public User setRole(Integer role) {
        this.role = role;
        return this;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public User setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public User setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public User setDelete(Boolean delete) {
        isDelete = delete;
        return this;
    }

    public String getSexName() {
        return sexName;
    }

    public User setSexName(String sexName) {
        this.sexName = sexName;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", telephone='" + telephone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", pic='" + pic + '\'' +
                ", eMail='" + eMail + '\'' +
                ", isActivate=" + isActivate +
                ", activateTime='" + activateTime + '\'' +
                ", roleId=" + roleId +
                ", role=" + role +
                ", sourceType=" + sourceType +
                ", createTime='" + createTime + '\'' +
                ", isDelete=" + isDelete +
                ", sexName='" + sexName + '\'' +
                '}';
    }
}
