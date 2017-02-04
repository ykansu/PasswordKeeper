package com.yasin.zegaste.passwordbox.passwordentities;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Yasin on 12.11.2016.
 */

public class PasswordProduct implements Serializable {

    private Date creationDate;

    private String Eid;
    private String name;
    private PasswordFirstDataType passwordFirstDataType;
    private String data1;
    private String data2;
    private String description;
    private EntityType entityType = EntityType.PassWordProduct;
    private String passWordProductOwnerEid;

    public PasswordProduct(String name, String passWordProductOwnerEid) {
        setName(name);
        setPassWordProductOwnerEid(passWordProductOwnerEid);
        setCreationDate(new Date(System.currentTimeMillis()));
    }

    public String getPassWordProductOwnerEid() {
        return passWordProductOwnerEid;
    }

    public void setPassWordProductOwnerEid(String passWordProductOwnerEid) {
        this.passWordProductOwnerEid = passWordProductOwnerEid;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    private void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getEid() {
        if (Eid == null)
            if (name != null)
                Eid = name.toLowerCase() + ":" + entityType;
            else
                throw new NullPointerException("PasswordProductName is Null");
        return Eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase(Locale.getDefault());
    }

    public PasswordFirstDataType getPasswordFirstDataType() {
        return passwordFirstDataType;
    }

    public void setPasswordFirstDataType(PasswordFirstDataType passwordFirstDataType) {
        this.passwordFirstDataType = passwordFirstDataType;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1, PasswordFirstDataType passwordFirstDataType) {
        this.passwordFirstDataType = passwordFirstDataType;
        if (passwordFirstDataType.equals(PasswordFirstDataType.E_MAIL))
            if (checkDataIfSatisfiesEmailRegex(data1))
                this.data1 = data1;
            else
                throw new NullPointerException("Data1 is not a e-mail adress");
    }

    private boolean checkDataIfSatisfiesEmailRegex(String data1) {
        return data1.contains("@");
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
