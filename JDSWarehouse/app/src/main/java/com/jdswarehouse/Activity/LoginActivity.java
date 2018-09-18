package com.jdswarehouse.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jdswarehouse.R;
import com.jdswarehouse.Response.LoginResponse;
import com.jdswarehouse.Retrofit.JDSWarehouseService;
import com.jdswarehouse.Retrofit.RestClient;
import com.jdswarehouse.Utilites.BaseActivity;
import com.jdswarehouse.Utilites.GPSTracker;
import com.jdswarehouse.Utilites.JDSWarehouse;
import com.jdswarehouse.Utilites.Preferences;
import com.jdswarehouse.Utilites.ValidationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    GPSTracker gpsTracker;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        gpsTracker = new GPSTracker(LoginActivity.this);
        if(gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        }
        setStatusBar();
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public boolean validation() {
        if (etEmail.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Email.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        } else if (!ValidationUtils.isValidEmail(etEmail.getText())) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Valid Email.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (etPwd.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void login(){
        JDSWarehouseService jdsWarehouseService = RestClient.getClient().create(JDSWarehouseService.class);
        showProgressbar("Loading", "Sending request...");
        jdsWarehouseService.login(String.valueOf(etEmail.getText()), String.valueOf(etPwd.getText()),"","android",String.valueOf(latitude),String.valueOf(longitude)).
                enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.body().getFlag()==1){
                            Toast.makeText(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            savePreferences(response.body().getUserData());
                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                      hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSWarehouse.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void savePreferences(LoginResponse.UserData userData) {
        Preferences.getInstance().setLogIn(true);
        Preferences.getInstance().setUserId(userData.getId());
        Preferences.getInstance().setUserName(userData.getUsername());
        Preferences.getInstance().setMobile(userData.getPhone());
        Preferences.getInstance().setEmail(userData.getEmail());
        Preferences.getInstance().setAuthKey(userData.getAuthToken());
        Preferences.getInstance().setCompany(userData.getCompany());
        Preferences.getInstance().setGroupId(userData.getGroupId());
    }


    @BindView(R.id.et_pwd)
    EditText etPwd;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.container)
    LinearLayout container;

    @OnClick(R.id.tv_forgot)
    void tvForgot(){
        Intent intent = new Intent(getApplicationContext(), ForgotActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        this.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_login)
    void btlogin(){
        if (validation()){
            login();
        }
    }

}
