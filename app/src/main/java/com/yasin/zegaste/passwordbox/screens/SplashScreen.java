package com.yasin.zegaste.passwordbox.screens;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.yasin.zegaste.passwordbox.common.SaveAndRestoreUtil;
import com.yasin.zegaste.passwordbox.passwordbox.R;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordDataStructure;

import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.FullscreenTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        hideActionBar();

        StartAppAsyncClass startAppAsyncClass = new StartAppAsyncClass();
        startAppAsyncClass.execute();

    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private class StartAppAsyncClass extends AsyncTask<Void, Void, Void> {

        PasswordDataStructure PasswordDataStructure ;
        boolean firstLogin = false;

        @Override
        protected Void doInBackground(Void... voids) {
            firstLogin = getIfFirstLogin();
            //freezeScreenforMillis(1500);
            if (firstLogin) {
                PasswordDataStructure = PasswordDataStructure.getInstance();
                startNextActivity(SingUpScreen.class);
            } else {
                restorePasswordDataStructure();
                startNextActivity(LoginScreen.class);
            }

            return null;
        }

        private void restorePasswordDataStructure() {

            try {
                PasswordDataStructure = (PasswordDataStructure) SaveAndRestoreUtil.restoreObjectFromLocal(SplashScreen.this, SaveAndRestoreUtil.savePasswordDataStructureKey);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private boolean getIfFirstLogin() {
            return !SaveAndRestoreUtil.isObjectSavedWithThisString(SplashScreen.this, SaveAndRestoreUtil.savePasswordDataStructureKey);
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
            ıntent.putExtra("PasswordDataStructure", PasswordDataStructure);
            startActivity(ıntent);
            finish();
        }

    }

}
