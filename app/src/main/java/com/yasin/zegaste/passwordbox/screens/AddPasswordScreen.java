package com.yasin.zegaste.passwordbox.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.yasin.zegaste.passwordbox.common.SaveAndRestoreUtil;
import com.yasin.zegaste.passwordbox.passwordbox.R;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordCategory;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordDataStructure;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordFirstDataType;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordProduct;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordProductOwner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPasswordScreen extends AppCompatActivity {

    PasswordProduct passwordProduct;
    AutoCompleteTextView passwordCategory;
    AutoCompleteTextView productOwner;
    AutoCompleteTextView passwordName;
    RadioButton rb_Email;
    RadioButton rb_Username;
    RadioButton rb_OnlyPassword;
    AutoCompleteTextView passwordData1;
    EditText passwordData2;
    private PasswordDataStructure PasswordDataStructure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password_screen);
        PasswordDataStructure = (PasswordDataStructure) getIntent().getSerializableExtra("PasswordDataStructure");

        rb_Email = (RadioButton) findViewById(R.id.addPasswordRb_Email);
        rb_Username = (RadioButton) findViewById(R.id.addPasswordRb_Username);
        rb_OnlyPassword = (RadioButton) findViewById(R.id.addPasswordRb_OnlyPassword);

        passwordCategory = (AutoCompleteTextView) findViewById(R.id.addPasswordTv_Category);
        final ArrayList<String> categoryNames = new ArrayList<>();
        for (PasswordCategory passwordCategory : PasswordDataStructure.getCategories()) {
            categoryNames.add(passwordCategory.getName());
        }
        ArrayAdapter<String> adapterForCategories = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryNames);
        passwordCategory.setAdapter(adapterForCategories);

        final ArrayList<String> listOwners = new ArrayList<>();
        productOwner = (AutoCompleteTextView) findViewById(R.id.addPasswordTv_ProductOwner);
        productOwner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) return;
                if (passwordCategory.getText().equals("")) return;
                PasswordCategory category = null;
                String categoryName = passwordCategory.getText().toString().toUpperCase();
                if (categoryNames.contains(categoryName)) {
                    for (PasswordCategory category1 : PasswordDataStructure.getCategories())
                        if (category1.getName().equalsIgnoreCase(categoryName))
                            category = category1;
                    for (PasswordProductOwner owner : category.getPasswordProductOwners()) {
                        if (!listOwners.contains(owner.getName()))
                            listOwners.add(owner.getName());
                    }
                    productOwner.setAdapter(new ArrayAdapter<String>(AddPasswordScreen.this, android.R.layout.simple_list_item_1, listOwners));
                } else {
                    for (PasswordCategory category1 : PasswordDataStructure.getCategories()) {
                        for (PasswordProductOwner owner : category1.getPasswordProductOwners())
                            if (!listOwners.contains(owner.getName()))
                                listOwners.add(owner.getName());
                    }
                    productOwner.setAdapter(new ArrayAdapter<String>(AddPasswordScreen.this, android.R.layout.simple_list_item_1, listOwners));
                }

            }
        });
        passwordName = (AutoCompleteTextView) findViewById(R.id.addPasswordTv_PasswordProduct);

        passwordData1 = (AutoCompleteTextView) findViewById(R.id.addPasswordTv_Data1);
        passwordData2 = (EditText) findViewById(R.id.addPasswordEt_Pass1);

        rb_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordData1.setEnabled(true);
                setPasswordData1AdapterAsUserNames(PasswordFirstDataType.E_MAIL);
                passwordData1.setHint("E-Posta");
            }
        });
        rb_Username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordData1.setEnabled(true);
                setPasswordData1AdapterAsUserNames(PasswordFirstDataType.USERNAME);
                passwordData1.setHint("Kullanıcı Adı");
            }
        });
        rb_OnlyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordData1.setEnabled(false);
                passwordData1.setHint("Sadece Şifre");
            }
        });

        setPasswordData1AdapterAsUserNames(PasswordFirstDataType.E_MAIL);
    }

    private void setPasswordData1AdapterAsUserNames(PasswordFirstDataType type) {
        List<String> list = new ArrayList<>();
        for (PasswordCategory category : PasswordDataStructure.getCategories()) {
            for (PasswordProductOwner owner : category.getPasswordProductOwners()) {
                for (PasswordProduct product : owner.getPasswordProducts()) {
                    if (product.getPasswordFirstDataType().equals(type))
                        list.add(product.getData1());
                }
            }
        }
        passwordData1.setAdapter(new ArrayAdapter<String>(AddPasswordScreen.this, android.R.layout.simple_list_item_1, list));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater ınflater = getMenuInflater();
        ınflater.inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveMenuitem) {
            addPasswordProduct();
        } else {
            onBackPressed();
        }
        return true;
    }

    private void addPasswordProduct() {
        if (!checkFields())
            return;
        PasswordCategory category = new PasswordCategory(passwordCategory.getText().toString().toUpperCase());
        PasswordProductOwner owner = new PasswordProductOwner(productOwner.getText().toString().toUpperCase(), category.getEid());
        passwordProduct = new PasswordProduct(passwordName.getText().toString().toUpperCase(), owner.getEid());
        setPasswordDataTypeFromRadioButtons();
        if (!passwordProduct.getPasswordFirstDataType().equals(PasswordFirstDataType.NOTNECESSARY))
            passwordProduct.setData1(passwordData1.getText().toString(), passwordProduct.getPasswordFirstDataType());
        passwordProduct.setData2(passwordData2.getText().toString());
        PasswordDataStructure.add(category, owner, passwordProduct);
        saveController();
        createToastMessage("Yeni Şifre Başarılı Olarak Kaydedildi !");
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddPasswordScreen.this, HomeScreen.class);
        intent.putExtra("PasswordDataStructure", PasswordDataStructure);
        startActivity(intent);
        finish();
    }

    private boolean checkFields() {
        if (passwordCategory.getText().equals("")) {
            createToastMessage("Şifre Kategorisi boş bırakılamaz !");
            return false;
        }
        if (productOwner.getText().toString().equals("")) {
            createToastMessage("Marka/Firma alanı boş bırakılamaz !");
            return false;
        }
        if (passwordName.getText().equals("")) {
            createToastMessage("Şifre Adı boş bırakılamaz !");
            return false;
        }
        if (passwordData1.getText().equals("") && !passwordProduct.getPasswordFirstDataType().equals(PasswordFirstDataType.NOTNECESSARY)) {
            createToastMessage(passwordProduct.getPasswordFirstDataType().equals(PasswordFirstDataType.E_MAIL) ? "E-Mail" : "Kullanıcı Adı" +
                    " alanı boş bırakılamaz !");
            return false;
        }
        if (passwordData2.getText().equals("")) {
            createToastMessage("Şifre alanı boş bırakılamaz !");
            return false;
        }
        return true;
    }

    private void setPasswordDataTypeFromRadioButtons() {
        if (rb_Email.isChecked())
            passwordProduct.setPasswordFirstDataType(PasswordFirstDataType.E_MAIL);
        if (rb_Username.isChecked())
            passwordProduct.setPasswordFirstDataType(PasswordFirstDataType.USERNAME);
        if (rb_OnlyPassword.isChecked())
            passwordProduct.setPasswordFirstDataType(PasswordFirstDataType.NOTNECESSARY);
    }

    private void saveController() {
        try {
            SaveAndRestoreUtil.saveObjectToLocal(PasswordDataStructure, AddPasswordScreen.this, SaveAndRestoreUtil.savePasswordDataStructureKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createToastMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
