package com.harsha.harshaapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.harsha.harshaapp.bean.BaselineHeadInfo2;
import com.harsha.harshaapp.bean.BaselineInfo;
import com.harsha.harshaapp.bean.Block;
import com.harsha.harshaapp.bean.District;
import com.harsha.harshaapp.bean.State;
import com.harsha.harshaapp.bean.User;
import com.harsha.harshaapp.database.DBHandler;

import java.util.ArrayList;

/**
 * Created by Jeevani on 2/15/2017.
 */

public class OnFarm extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBHandler dbHandler = new DBHandler(OnFarm.this, null, null, 1);
    Bundle bundle;
    User user = new User();
    State state = new State();
    District district = new District();
    Block block = new Block();

    String result = "";
    int flag = 1;
    int position = 0;

    BaselineInfo baselineInfo = new BaselineInfo();
    ArrayList<BaselineHeadInfo2> baselineHeadInfo = new ArrayList<BaselineHeadInfo2>();

    BaselineHeadInfo2 baseHead;

    TextView nav_username,nav_email;

    TextView familyHeadname, onFarmState, onFarmDistrict, onFarmBlock, onFarmVillage;
    EditText land,noOfPlants,yearOfPlanting,production,income,onFarmDate,impact;
    Spinner onFarmproject;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_farm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        familyHeadname = (TextView) findViewById(R.id.familyHeadName);
        land = (EditText) findViewById(R.id.land);
        noOfPlants = (EditText) findViewById(R.id.noOfPlants);
        yearOfPlanting = (EditText) findViewById(R.id.yearOfPlanting);
        production = (EditText) findViewById(R.id.production);
        income = (EditText) findViewById(R.id.income);
        onFarmDate = (EditText) findViewById(R.id.onFarmDate);
        impact = (EditText) findViewById(R.id.impact);
        onFarmState = (TextView) findViewById(R.id.state);
        onFarmDistrict = (TextView) findViewById(R.id.district);
        onFarmBlock = (TextView) findViewById(R.id.block);
        onFarmVillage = (TextView) findViewById(R.id.village);
        onFarmproject = (Spinner) findViewById(R.id.project);
        save = (Button) findViewById(R.id.save);

        AddOnFarmAsyncTask obj = new AddOnFarmAsyncTask(this);
        obj.execute();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=2;
                AddOnFarmAsyncTask obj1 = new AddOnFarmAsyncTask(OnFarm.this);
                obj1.execute();
            }
        });

        Intent receive = getIntent();
        bundle = receive.getExtras();
        position = receive.getIntExtra("baselineHeadInfoPosition", 0);
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

    public void readData() {

        /*baselineInfo.setStateId(state.getStateId());
        baselineInfo.setDistrictId(district.getDistrictId());
        baselineInfo.setBlockId(block.getBlockId());
        baselineInfo.setSurveyUserId(user.getUserId());
        Log.d("income=","Income="+income.getText().toString()+"\ngetIncome="+baselineInfo.getIncome());
        Log.d("user=",user + "\nuserId="+user.getUserId()+"\nUsername="+user.getUserName());*/

    }

    public void spinnerList() {

        baselineHeadInfo = dbHandler.getAllBaselineHeadInformation2();
        baseHead = baselineHeadInfo.get(position);

        familyHeadname.setText(familyHeadname.getText().toString() + ": " + baseHead.memberName);
        onFarmState.setText(onFarmState.getText().toString() + ": " + baseHead.stateName);
        onFarmDistrict.setText(onFarmDistrict.getText().toString() + ": " + baseHead.districtName);
        onFarmBlock.setText(onFarmBlock.getText().toString() + ": " + baseHead.blockName);
        onFarmVillage.setText(onFarmVillage.getText().toString() + ": " + baseHead.villageName);
    }

    class AddOnFarmAsyncTask extends AsyncTask<String, String, String> {

        Context mContext;
        ProgressDialog progressDialog;

        public AddOnFarmAsyncTask(Context mContext) {
            this.mContext = mContext;
            progressDialog = new ProgressDialog(mContext);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle(R.string.app_name);
            progressDialog.setMessage("Loading, Please Wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                if (flag==1) {
                    spinnerList();
                }
                else if (flag==2) {
                    readData();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            if(flag==2) {

            }
            progressDialog.dismiss();
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
