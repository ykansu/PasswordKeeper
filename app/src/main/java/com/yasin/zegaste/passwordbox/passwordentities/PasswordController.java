package com.yasin.zegaste.passwordbox.passwordentities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yasin on 12.11.2016.
 */
public class PasswordController implements Serializable {

    private static PasswordController ourInstance = new PasswordController();
    Map<String, PasswordProduct> passwordProductMap;
    Map<String, PasswordProductOwner> passwordProductOwnerMap;
    Map<String, PasswordCategory> passwordCategoryMap;
    IdentitiyCard idIdentitiyCard;
    List<PasswordCategory> categories;

    private PasswordController() {
        passwordCategoryMap = new HashMap<>();
        passwordProductMap = new HashMap<>();
        passwordProductOwnerMap = new HashMap<>();
        categories = new ArrayList<>();
    }

    public static PasswordController getInstance() {
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
            if (categoryProductOwners.contains(passwordProductOwner.getEid()))
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
