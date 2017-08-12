package com.yasin.zegaste.passwordbox.passwordentities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class has full control of model classes.
 * This class organizes all structure of data stored from the base and allows view class to
 * show information to user. User changes also handled by this class.
 * This class saved as data model.
 * Created by Yasin on 12.11.2016.
 */
public class PasswordDataStructure implements Serializable {

    private static PasswordDataStructure ourInstance = new PasswordDataStructure();
    Map<String, PasswordProduct> passwordProductMap;
    Map<String, PasswordProductOwner> passwordProductOwnerMap;
    Map<String, PasswordCategory> passwordCategoryMap;
    IdentitiyCard idIdentitiyCard;
    List<PasswordCategory> categories;

    private PasswordDataStructure() {
        passwordCategoryMap = new HashMap<>();
        passwordProductMap = new HashMap<>();
        passwordProductOwnerMap = new HashMap<>();
        categories = new ArrayList<>();
    }

    public static PasswordDataStructure getInstance() {
        return ourInstance;
    }

    public List<PasswordCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<PasswordCategory> categories) {
        this.categories = categories;
    }

    public IdentitiyCard getIdIdentitiyCard() {
        return idIdentitiyCard;
    }

    public void setIdIdentitiyCard(IdentitiyCard idIdentitiyCard) {
        this.idIdentitiyCard = idIdentitiyCard;
    }

    public boolean removePasswordCategory(PasswordCategory passwordCategory) {
        //todo: if threre are password owners under this category, user warning should be shown.
        if (passwordCategoryMap.containsKey(passwordCategory.getEid())) {
            passwordCategoryMap.remove(passwordCategory.getEid());
            if (categories.contains(passwordCategory)) {
                categories.remove(passwordCategory);
                return true;
            } else
                return false;
        } else
            return false;
    }

    public boolean removePassWordProductOwner(PasswordProductOwner passwordProductOwner) {
        //todo: if there are password products under this owner, user warning should be shown.
        if (passwordProductOwnerMap.containsKey(passwordProductOwner.getEid())) {
            passwordProductOwnerMap.remove(passwordProductOwner.getPassWordCategoryEid());
            passwordCategoryMap.get(passwordProductOwner.getPassWordCategoryEid()).getPasswordProductOwners()
                    .remove(passwordProductOwner);
            return true;
        } else
            return false;
    }

    public boolean removePassWordProduct(PasswordProduct passwordProduct) {
        if (passwordProductMap.containsKey(passwordProduct.getEid())) {
            passwordProductMap.remove(passwordProduct.getEid());
            passwordProductOwnerMap.get(passwordProduct.getPassWordProductOwnerEid()).getPasswordProducts()
                    .remove(passwordProduct);
            return true;
        } else
            return false;
    }

    public void add(PasswordCategory passwordCategory, PasswordProductOwner passwordProductOwner, PasswordProduct passwordProduct) {
        addPasswordCategory(passwordCategory);
        addPassWordProductOwner(passwordProductOwner);
        addPassWordProduct(passwordProduct);
    }

    public void addPasswordCategory(PasswordCategory passwordCategory) {
        if (passwordCategoryMap.containsKey(passwordCategory.getEid()))
            return;
        passwordCategoryMap.put(passwordCategory.getEid(), passwordCategory);
        categories.add(passwordCategory);
    }

    public void addPassWordProductOwner(PasswordProductOwner passwordProductOwner) {
        if (passwordCategoryMap.containsKey(passwordProductOwner.getPassWordCategoryEid())) {
            List categoryProductOwners = passwordCategoryMap.get(passwordProductOwner.getPassWordCategoryEid())
                    .getPasswordProductOwners();
            if (categoryProductOwners.contains(passwordProductOwner.getEid()) && passwordProductOwnerMap.containsKey(passwordProductOwner.getEid()))
                return;
            passwordProductOwnerMap.put(passwordProductOwner.getEid(), passwordProductOwner);
            categoryProductOwners.add(passwordProductOwner);
        } else
            throw new NullPointerException("PassWordCategory of PassWordProductOwner not found");
    }

    public void addPassWordProduct(PasswordProduct passwordProduct) {
        if (passwordProductOwnerMap.containsKey(passwordProduct.getPassWordProductOwnerEid())) {
            if (passwordProductOwnerMap.get(passwordProduct.getPassWordProductOwnerEid()).getPasswordProducts()
                    .contains(passwordProduct.getEid()))
                passwordProduct = changePassWordProductName(passwordProduct);
            passwordProductOwnerMap.get(passwordProduct.getPassWordProductOwnerEid()).getPasswordProducts()
                    .add(passwordProduct);
            passwordProductMap.put(passwordProduct.getEid(), passwordProduct);
        } else {
            throw new NullPointerException("PassWordProductOwner of PassWordProducy not Found");
        }
    }

    private PasswordProduct changePassWordProductName(PasswordProduct passwordProduct) {
        String name = passwordProduct.getName();
        String splitter = "--";
        if (!name.contains(splitter)) {
            name = name + splitter + "1";
        } else {
            int number = Integer.valueOf(name.split(splitter)[name.split(splitter).length - 1]);
            name = name.substring(0, name.lastIndexOf(splitter)) + splitter + number;
        }
        PasswordProduct passwordProduct1 = new PasswordProduct(name, passwordProduct.getPassWordProductOwnerEid());
        passwordProduct1.setData1(passwordProduct.getData1(), passwordProduct.getPasswordFirstDataType());
        passwordProduct1.setData2(passwordProduct.getData2());
        passwordProduct1.setDescription(passwordProduct.getDescription());
        return passwordProduct1;
    }

    public PasswordProductOwner getPasswordProductOwner(String eid) {
        if (!passwordProductOwnerMap.containsKey(eid))
            throw new NullPointerException("PasswordProductOwner not found");
        return passwordProductOwnerMap.get(eid);
    }

    public PasswordCategory getPasswordCategory(String eid) {
        if (!passwordCategoryMap.containsKey(eid))
            throw new NullPointerException("PasswordCategor not found");
        return passwordCategoryMap.get(eid);
    }

}
