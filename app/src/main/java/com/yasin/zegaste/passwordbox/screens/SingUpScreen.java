package com.yasin.zegaste.passwordbox.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.yasin.zegaste.passwordbox.common.SaveAndRestoreUtil;
import com.yasin.zegaste.passwordbox.passwordbox.R;
import com.yasin.zegaste.passwordbox.passwordentities.IdentitiyCard;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordCategory;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordDataStructure;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordFirstDataType;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordProduct;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordProductOwner;

import java.io.IOException;

public class SingUpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_screen);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Şifrenizi Belirleyin");
        }
        Intent ıntent = getIntent();
        final PasswordDataStructure PasswordDataStructure = (PasswordDataStructure) ıntent.getSerializableExtra("PasswordDataStructure");
        final EditText password = (EditText) findViewById(R.id.editText);
        final EditText password_Check = (EditText) findViewById(R.id.editText2);
        Button createAppPassword  = (Button)findViewById(R.id.btnCreateAppPassword);
        createAppPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPassword = password.getText().toString();
                if(!userPassword.equals(password_Check.getText().toString())){
                    showPasswordsDontMatchWarning(view);
                    return;
                }
               showEULAMessage(userPassword);
            }

            private void startNextActivity(Class<?> cls) {
                Intent ıntent1 = new Intent(SingUpScreen.this,cls);
                ıntent1.putExtra("PasswordDataStructure", PasswordDataStructure);
                startActivity(ıntent1);
                finish();
            }

            private void saveController() {
                try {
                    SaveAndRestoreUtil.saveObjectToLocal(PasswordDataStructure,SingUpScreen.this,"PasswordDataStructure");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private IdentitiyCard initUserIdentityCard(String userPassword) {
                IdentitiyCard ıdentitiyCard = new IdentitiyCard(userPassword);
                return ıdentitiyCard;
            }

            private void showEULAMessage(final String userPassword) {
               String title="Kullanıcı Sözleşmesi";
                String message="Bu uygulamayı kullanmayı kabul ediyorum. Kullanımından doğabilecek her türlü sorumluluğu alıyorum.";
                AlertDialog.Builder builder = new AlertDialog.Builder(SingUpScreen.this)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PasswordDataStructure.setIdIdentitiyCard(initUserIdentityCard(userPassword));
                                if(addExamplesSelected())
                                    addExamples(PasswordDataStructure);
                                saveController();
                                dialogInterface.dismiss();
                                startNextActivity(HomeScreen.class);
                            }

                            private void addExamples(PasswordDataStructure PasswordDataStructure) {
                                PasswordCategory epostaPasswordCategory = new PasswordCategory("e-posta");
                                PasswordCategory bankaPasswordCategory = new PasswordCategory("banka");
                                PasswordCategory oyunPasswordCategory = new PasswordCategory("oyun");

                                PasswordProductOwner gmailPasswordProductOwner = new PasswordProductOwner("GMail", epostaPasswordCategory.getEid());
                                PasswordProductOwner outlookPasswordProductOwner = new PasswordProductOwner("Outlook", epostaPasswordCategory.getEid());

                                PasswordProduct gmailPasswordProduct = new PasswordProduct("Gmail Hesabım", gmailPasswordProductOwner.getEid());
                                gmailPasswordProduct.setData1("yasinkansu3@gmail.com", PasswordFirstDataType.E_MAIL);
                                gmailPasswordProduct.setData2("1234567");

                                PasswordProduct outlookPasswordProduct = new PasswordProduct("Outlook Hesabım", outlookPasswordProductOwner.getEid());
                                outlookPasswordProduct.setData1("yasinkansu3@hotmail.com", PasswordFirstDataType.E_MAIL);
                                outlookPasswordProduct.setData2("1234567");

                                PasswordProductOwner finansPasswordProductOwner = new PasswordProductOwner("Ziraat Bankası", bankaPasswordCategory.getEid());
                                PasswordProductOwner yapıkrediPasswordProductOwner = new PasswordProductOwner("Halk bankası", bankaPasswordCategory.getEid());

                                PasswordProduct finansbakPasswordProduct = new PasswordProduct("internet bankacılığı", finansPasswordProductOwner.getEid());
                                finansbakPasswordProduct.setData1("765432", PasswordFirstDataType.USERNAME);
                                finansbakPasswordProduct.setData2("1234567");

                                PasswordProduct finansbakPasswordProduct2 = new PasswordProduct("nakit kartı", finansPasswordProductOwner.getEid());
                                finansbakPasswordProduct2.setPasswordFirstDataType(PasswordFirstDataType.NOTNECESSARY);
                                finansbakPasswordProduct2.setData2("1234567");

                                PasswordProduct finansbakPasswordProduct3 = new PasswordProduct("kredi kartı", finansPasswordProductOwner.getEid());
                                finansbakPasswordProduct3.setPasswordFirstDataType(PasswordFirstDataType.NOTNECESSARY);
                                finansbakPasswordProduct3.setData2("1234567");

                                PasswordProductOwner lolPasswordProductOwner = new PasswordProductOwner("League of Legends", oyunPasswordCategory.getEid());
                                PasswordProduct lolPasswordProduct = new PasswordProduct("1. Hesabım", lolPasswordProductOwner.getEid());
                                lolPasswordProduct.setData1("gamecholic38", PasswordFirstDataType.USERNAME);
                                lolPasswordProduct.setData2("1234567");

                                PasswordProduct lolPasswordProduct2 = new PasswordProduct("2. Hesabım", lolPasswordProductOwner.getEid());
                                lolPasswordProduct2.setData1("2.gamecholic38", PasswordFirstDataType.USERNAME);
                                lolPasswordProduct2.setData2("1234567");

                                PasswordDataStructure.add(oyunPasswordCategory, lolPasswordProductOwner, lolPasswordProduct);
                                PasswordDataStructure.addPassWordProduct(lolPasswordProduct2);

                                PasswordDataStructure.addPasswordCategory(epostaPasswordCategory);
                                PasswordDataStructure.addPasswordCategory(bankaPasswordCategory);

                                PasswordDataStructure.addPassWordProductOwner(gmailPasswordProductOwner);
                                PasswordDataStructure.addPassWordProductOwner(outlookPasswordProductOwner);
                                PasswordDataStructure.addPassWordProductOwner(finansPasswordProductOwner);
                                PasswordDataStructure.addPassWordProductOwner(yapıkrediPasswordProductOwner);

                                PasswordDataStructure.addPassWordProduct(gmailPasswordProduct);
                                PasswordDataStructure.addPassWordProduct(outlookPasswordProduct);
                                PasswordDataStructure.addPassWordProduct(finansbakPasswordProduct);
                                PasswordDataStructure.addPassWordProduct(finansbakPasswordProduct2);
                                PasswordDataStructure.addPassWordProduct(finansbakPasswordProduct3);
                            }

                            private boolean addExamplesSelected() {
                                CheckBox cbAddExamples = (CheckBox) findViewById(R.id.cbAddExamplePasswords);
                                return cbAddExamples.isChecked();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                builder.create().show();

            }

            private void showPasswordsDontMatchWarning(View view) {
                Snackbar.make(view, "Girilen Şifreler Eşleşmiyor !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




    }
}
