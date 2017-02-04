package com.yasin.zegaste.passwordbox.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.yasin.zegaste.passwordbox.passwordbox.R;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordCategory;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordController;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordProduct;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordProductOwner;
import com.yasin.zegaste.passwordbox.screens.Adapters.PasswordsListViewAdapter;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private PasswordController passwordController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        passwordController = (PasswordController) getIntent().getSerializableExtra("passwordController");
        ListView passwordsListView = (ListView) findViewById(R.id.listviewPasswords);
        setPasswordsListViewAdapter(passwordsListView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundResource(R.drawable.ic_menu_camera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddNewPasswordAction(view);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setPasswordsListViewAdapter(ListView passwordsListView) {
        List pass = new ArrayList<>();
        for (PasswordCategory category : passwordController.getCategories()) {
            for (PasswordProductOwner passwordProductOwner : category.getPasswordProductOwners()) {
                for (PasswordProduct passwordProduct : passwordProductOwner.getPasswordProducts())
                    /*pass.add(passwordProduct.getName()+"/"+passwordProduct.getData1()+"/"+passwordProduct
                    .getData2());*/
                    pass.add(passwordProduct);
            }

        }
        PasswordsListViewAdapter adapter = new PasswordsListViewAdapter(this, R.layout.layout_passwordlistview, pass, passwordController);
        passwordsListView.setAdapter(adapter);
    }

    private void startAddNewPasswordAction(View view) {
        //TODO: AddNewPassWord
        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //      .setAction("Action", null).show();
        showUserAddNewPasswordScreen();
    }

    private void showUserAddNewPasswordScreen() {
        Intent intent = new Intent(HomeScreen.this, AddPasswordScreen.class);
        intent.putExtra("passwordController", passwordController);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
