package com.jdswarehouse.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.jdswarehouse.R;
import com.jdswarehouse.Response.DataResponse;
import com.jdswarehouse.Response.QuotationResponse;
import com.jdswarehouse.Retrofit.JDSWarehouseService;
import com.jdswarehouse.Retrofit.RestClient;
import com.jdswarehouse.Scanner.ZXingScannerView;
import com.jdswarehouse.Utilites.BaseActivity;
import com.jdswarehouse.Utilites.GPSTracker;
import com.jdswarehouse.Utilites.JDSWarehouse;
import com.jdswarehouse.Utilites.Preferences;
import com.jdswarehouse.Utilites.ShareListner;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends BaseActivity implements ZXingScannerView.ResultHandler,ShareListner
{
    private ZXingScannerView mScannerView;
    ViewGroup contentFrame;
    public static final String SCAN = "scan";
    QuotationResponse.QuoteItem quoteItem;
    GPSTracker gpsTracker;
    double latitude,longitude;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        gpsTracker = new GPSTracker(ScannerActivity.this);
        if(gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        }
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("SCAN CODE");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.drawable.crose));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
        SharedPreferences settings = getSharedPreferences("Scan", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
         quoteItem = (QuotationResponse.QuoteItem) getIntent().getParcelableExtra(SCAN);
         requestCameraPermission();
         setStatusBar();
         contentFrame = (ViewGroup) findViewById(R.id.content_frame);
         mScannerView = new ZXingScannerView(this);
         contentFrame.addView(mScannerView);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow(); // in Activity's onCreate() for instance
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorBlack));
        }

    }

    private void requestCameraPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void scan(String scanCode){
        JDSWarehouseService jdsWarehouseService = RestClient.getClient().create(JDSWarehouseService.class);
        showProgressbar("Loading", "Sending request...");
        jdsWarehouseService.scanCode(quoteItem.getProductId(),scanCode,quoteItem.getQuoteId(), Preferences.getInstance().getAuthKey(),String.valueOf(latitude),String.valueOf(longitude)).
                enqueue(new Callback<DataResponse>() {
                    @Override
                    public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                        if (response.body().getFlag()==1){
                            SharedPreferences.Editor editor = getSharedPreferences("Scan", MODE_PRIVATE).edit();
                            String newQunt = quoteItem.getQuantity().substring(0, quoteItem.getQuantity().indexOf("."));
                            editor.putString("quoteId", quoteItem.getId());
                            editor.putString("total", newQunt);
                            if (quoteItem.getIsScan()==null|| quoteItem.getIsScan().equalsIgnoreCase("")) {
                                editor.putString("quantity", newQunt);
                            }
                            else {
                                editor.putString("quantity", quoteItem.getItemLoadQuantity());
                            }
                            editor.apply();
                            finish();
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),response.body().getData(), Toast.LENGTH_SHORT).show();
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<DataResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSWarehouse.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void handleResult(Result rawResult) {
        scan(rawResult.getText().toString());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScannerActivity.this);
            }
        }, 2000);
    }


    @Override
    public void onValue(String quoteiId, String quatity) {

    }
}