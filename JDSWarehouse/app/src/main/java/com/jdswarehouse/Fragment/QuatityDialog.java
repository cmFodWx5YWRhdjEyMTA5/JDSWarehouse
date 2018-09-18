package com.jdswarehouse.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdswarehouse.Activity.CustDetailActivity;
import com.jdswarehouse.R;
import com.jdswarehouse.Response.DataResponse;
import com.jdswarehouse.Retrofit.JDSWarehouseService;
import com.jdswarehouse.Retrofit.RestClient;
import com.jdswarehouse.Utilites.BaseActivity;
import com.jdswarehouse.Utilites.GPSTracker;
import com.jdswarehouse.Utilites.JDSWarehouse;
import com.jdswarehouse.Utilites.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dikhong on 05-07-2018.
 */

public class QuatityDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    String quantity,quoteId,totalQuantity;
    public Button btSubmit;
    ImageView ivClose;
    TextView tvQuatity;
    EditText etNote;
    GPSTracker gpsTracker;
    double latitude,longitude;


    public QuatityDialog(Activity a,String quoteId,String quantity,String totalQuantity) {
        super(a);
        // TODO Auto-generated constructor stub
        this.quoteId=quoteId;
        this.quantity=quantity;
        this.totalQuantity=totalQuantity;
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.quantity_dialog);
        gpsTracker = new GPSTracker(getContext());
        if(gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        }
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        SharedPreferences settings = activity.getSharedPreferences("Scan", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        btSubmit = (Button) findViewById(R.id.bt_submit);
        tvQuatity = (TextView) findViewById(R.id.tv_quatity);
        tvQuatity.setText(quantity);
        tvQuatity.setFocusable(false);
        tvQuatity.setFocusableInTouchMode(false);
        tvQuatity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuatityFragmentListener quatityFragmentListener = (QuatityFragmentListener)activity;
                quatityFragmentListener.openSelector(tvQuatity.getText().toString(),totalQuantity);
                dismiss();
            }
        });
        etNote = (EditText) findViewById(R.id.et_note);
        ivClose = (ImageView) findViewById(R.id.iv_close);
        btSubmit.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                if (etNote.getText().toString().length()!=0) {
                    submitQuantity();
                    dismiss();
                }
                else {
                    Toast.makeText(activity,"Please enter notes", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_close:
                dismiss();
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void submitQuantity() {
        JDSWarehouseService jdsWarehouseService = RestClient.getClient().create(JDSWarehouseService.class);
        ((BaseActivity) activity).showProgressbar("Loading", "Please wait...");
        jdsWarehouseService.quantityStatus(quoteId, quantity,etNote.getText().toString(),"done", Preferences.getInstance().getAuthKey(),String.valueOf(latitude),String.valueOf(longitude)).
                enqueue(new Callback<DataResponse>() {
                    @Override
                    public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                        if (response.body().getFlag()==1){
                            Toast.makeText(activity,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            ((CustDetailActivity) activity).onResume();
                        }
                        else
                        {
                            Toast.makeText(activity,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        ((BaseActivity) activity).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<DataResponse> call, Throwable t) {
                        ((BaseActivity) activity).hideProgressBar();
                        Toast.makeText(activity, JDSWarehouse.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public interface QuatityFragmentListener {
        public void openSelector(String selectedValue,String quantity);
    }

}

