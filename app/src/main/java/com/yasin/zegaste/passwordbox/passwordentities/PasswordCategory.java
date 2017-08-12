package com.yasin.zegaste.passwordbox.passwordentities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class stands for the model of password categories user could add to app.
 * For Example:Bank,Game,Application, Web Page vs.
 * Created by Yasin on 12.11.2016.
 */

public class PasswordCategory implements Serializable {

    private String name;
    private String Eid;
    private List<PasswordProductOwner> passwordProductOwners;

    public PasswordCategory(String name) {
        setName(name);
        Eid = getEid();
        passwordProductOwners = new ArrayList<>();
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    private EntityType entityType = EntityType.PassWordCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase(Locale.getDefault());
    }

    public String getEid() {
        if (Eid == null)
            if (name != null)
                Eid = name.toLowerCase()+":" + entityType;
            else
                throw new NullPointerException("PasswordCategoryName field is null");
        return Eid;
    }

    public List<PasswordProductOwner> getPasswordProductOwners() {
        return passwordProductOwners;
    }

    public void setPasswordProductOwners(List<PasswordProductOwner> passwordProductOwners) {
        this.passwordProductOwners = passwordProductOwners;
    }

}
