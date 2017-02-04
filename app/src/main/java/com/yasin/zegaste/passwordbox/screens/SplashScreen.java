package com.yasin.zegaste.passwordbox.screens;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.yasin.zegaste.passwordbox.Common.SaveAndRestoreUtil;
import com.yasin.zegaste.passwordbox.passwordbox.R;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordCategory;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordController;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordFirstDataType;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordProduct;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordProductOwner;

import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        hide();
        StartAppAsyncClass startAppAsyncClass = new StartAppAsyncClass();
        startAppAsyncClass.execute();

    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private class StartAppAsyncClass extends AsyncTask<Void, Void, Void> {

        PasswordController passwordController ;
        boolean firstLogin = false;

        @Override
        protected Void doInBackground(Void... voids) {
            freezeScreenforMillis(1500);
            firstLogin = getIfFirstLogin();
            if (firstLogin) {
                passwordController = PasswordController.getInstance();
                startNextActivity(SingUpScreen.class);
            } else {
                restorePassWordController();
                startNextActivity(LoginScreen.class);
            }

            return null;
        }

        private void restorePassWordController() {

            try {
                passwordController = (PasswordController) SaveAndRestoreUtil.restoreObjectFromLocal(SplashScreen.this, "passwordController");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private boolean getIfFirstLogin() {
            return !SaveAndRestoreUtil.isObjectSavedWithThisString(SplashScreen.this, "passwordController");
        }

        private void freezeScreenforMillis(int millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void startNextActivity(Class<?> cls) {
            Intent ıntent = new Intent(SplashScreen.this, cls);
            ıntent.putExtra("passwordController", passwordController);
            startActivity(ıntent);
            finish();
        }

    }

}
