package com.harsha.harshaapp;

/**
 * Created by Jeevani on 12/7/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.harsha.harshaapp.bean.User;
import com.harsha.harshaapp.database.DBHandler;

public class UpdateBaselineInformation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    DBHandler dbHandler = new DBHandler(UpdateBaselineInformation.this, null, null, 1);
    Bundle bundle;
    User user = new User();

    TextView nav_username,nav_email;

    EditText familyHeadName,date,income,personalSavings;
    Spinner state,district,block,familyMembers,village;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseline_update_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        familyHeadName = (EditText) findViewById(R.id.familyHeadName);
        date = (EditText) findViewById(R.id.date);
        income = (EditText) findViewById(R.id.income);
        personalSavings = (EditText) findViewById(R.id.personalSavings);
        state = (Spinner) findViewById(R.id.state);
        district = (Spinner) findViewById(R.id.district);
        block = (Spinner) findViewById(R.id.block);
        village = (Spinner) findViewById(R.id.village);
        familyMembers = (Spinner) findViewById(R.id.familyMembers);
        update = (Button) findViewById(R.id.update);

        ArrayAdapter<CharSequence> stateListAdapter = ArrayAdapter
                .createFromResource(this, R.array.state_list,
                        android.R.layout.simple_spinner_item);
        stateListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateListAdapter);

        ArrayAdapter<CharSequence> districtListAdapter = ArrayAdapter
                .createFromResource(this, R.array.district_list,
                        android.R.layout.simple_spinner_item);
        districtListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        district.setAdapter(districtListAdapter);

        ArrayAdapter<CharSequence> blockListAdapter = ArrayAdapter
                .createFromResource(this, R.array.block_list,
                        android.R.layout.simple_spinner_item);
        blockListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        block.setAdapter(blockListAdapter);

        ArrayAdapter<CharSequence> villageListAdapter = ArrayAdapter
                .createFromResource(this, R.array.village_list,
                        android.R.layout.simple_spinner_item);
        villageListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        village.setAdapter(villageListAdapter);

        ArrayAdapter<CharSequence> familyMemberListAdapter = ArrayAdapter
                .createFromResource(this, R.array.familyMember_list,
                        android.R.layout.simple_spinner_item);
        familyMemberListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familyMembers.setAdapter(familyMemberListAdapter);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Intent receive = getIntent();
        bundle = receive.getExtras();
        user = dbHandler.getUserDetail();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);
        nav_username = (TextView) headerView.findViewById(R.id.nav_username);
        nav_username.setText(user.getUserName());
        nav_email = (TextView) headerView.findViewById(R.id.nav_email);
        nav_email.setText(user.getEmail());
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            //intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.b_info) {
            Intent intent = new Intent(getApplicationContext(), BaselineInformation.class);
            //intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.create_info) {
            Intent intent = new Intent(getApplicationContext(), CreateBaselineInformation.class);
            //intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.impact_area) {
            Intent intent = new Intent(getApplicationContext(), ImpactArea.class);
            //intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.uploads) {
            Intent intent = new Intent(getApplicationContext(), ProjectDetails.class);
            //intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.profile) {
            Intent intent = new Intent(getApplicationContext(), MyProfile.class);
            //intent.putExtras(bundle);
            startActivity(intent);
        }else if (id == R.id.change_password) {
            Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
            //intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.serverDownload) {
            Intent intent = new Intent(getApplicationContext(), ServerDownload.class);
            //intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.serverUpload) {
            Intent intent = new Intent(getApplicationContext(), ServerUpload.class);
            //intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.address) {
            Intent intent = new Intent(getApplicationContext(), Address.class);
            //intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.logout) {
            dbHandler.deleteUser(user);
            Intent intent = new Intent(getApplicationContext(), Login.class);
            //intent.putExtras(bundle);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
