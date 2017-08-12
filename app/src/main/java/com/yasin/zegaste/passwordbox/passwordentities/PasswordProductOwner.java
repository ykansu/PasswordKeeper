package com.yasin.zegaste.passwordbox.passwordentities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class stands for the model of password's creation place.
 * For Example (Category-PasswordOwner): (Bank-Ziraat Bank), (Game-League of Legends)
 * Created by Yasin on 12.11.2016.
 */

public class PasswordProductOwner implements Serializable {

    private String Eid;
    private String name;
    private List<PasswordProduct> passwordProducts;
    private EntityType entityType = EntityType.passWordProductOwner;
    private String passWordCategoryEid;

    public PasswordProductOwner(String name, String passWordCategoryEid) {
        setName(name);
        this.passWordCategoryEid = passWordCategoryEid;
        Eid = getEid();
        passwordProducts = new ArrayList<>();
    }

    public String getPassWordCategoryEid() {
        return passWordCategoryEid;
    }

    public void setPassWordCategoryEid(String passWordCategoryEid) {
        this.passWordCategoryEid = passWordCategoryEid;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getEid() {
        if (Eid == null)
            if (name != null)
                Eid = name.toLowerCase()+":" + entityType;
            else
                throw new NullPointerException("PasswordProductOwnerName is null");
        return Eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase(Locale.getDefault());
    }

    public int getProductNumber() {
        return passwordProducts.size();
    }

    public List<PasswordProduct> getPasswordProducts() {
        return passwordProducts;
    }

    public void setPasswordProducts(List<PasswordProduct> passwordProducts) {
        this.passwordProducts = passwordProducts;
    }

}
