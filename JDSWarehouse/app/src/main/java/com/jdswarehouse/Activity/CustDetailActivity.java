package com.jdswarehouse.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.jdswarehouse.Adapter.QutationAdapter;
import com.jdswarehouse.Fragment.CustomDialog;
import com.jdswarehouse.Fragment.QuatityDialog;
import com.jdswarehouse.R;
import com.jdswarehouse.Response.DataResponse;
import com.jdswarehouse.Response.QuotationResponse;
import com.jdswarehouse.Retrofit.JDSWarehouseService;
import com.jdswarehouse.Retrofit.RestClient;
import com.jdswarehouse.Utilites.BaseActivity;
import com.jdswarehouse.Utilites.GPSTracker;
import com.jdswarehouse.Utilites.JDSWarehouse;
import com.jdswarehouse.Utilites.Preferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustDetailActivity extends BaseActivity implements CustomDialog.MyDialogFragmentListener,QuatityDialog.QuatityFragmentListener {
    String quoteId,customerId,quantity,quoteId1,selectedQuantity,totalQuantity,total,selectValue;
    GPSTracker gpsTracker;
    double latitude,longitude;
    boolean shouldExecuteOnResume;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_detail);
        ButterKnife.bind(this);
        shouldExecuteOnResume = false;
        gpsTracker = new GPSTracker(CustDetailActivity.this);
        if(gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        }
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("CLIENT INFO");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
        setStatusBar();
        quoteId=getIntent().getStringExtra("quotation_id");
        customerId=getIntent().getStringExtra("customer_id");
        getQuoteData(quoteId,customerId);
        SharedPreferences settings = getSharedPreferences("Scan", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void submitOrder(String selectValue){
        JDSWarehouseService jdsWarehouseService = RestClient.getClient().create(JDSWarehouseService.class);
        showProgressbar("Loading", "Sending request...");
        jdsWarehouseService.submitOrder(quoteId, selectValue,Preferences.getInstance().getAuthKey(),String.valueOf(latitude),String.valueOf(longitude)).
                enqueue(new Callback<DataResponse>() {
                    @Override
                    public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                        if (response.body().getFlag()==1){
                            Toast.makeText(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
    public void getQuoteData(String quoteId,String customerId) {
        JDSWarehouseService jdsWarehouseService = RestClient.getClient().create(JDSWarehouseService.class);
        showProgressbar("Loading", "Sending request...");
        jdsWarehouseService.quatationData(quoteId,customerId, Preferences.getInstance().getAuthKey(),String.valueOf(latitude),String.valueOf(longitude)).
                enqueue(new Callback<QuotationResponse>() {
                    @Override
                    public void onResponse(Call<QuotationResponse> call, Response<QuotationResponse> response) {
                        if (response.body().getFlag()==1){
                          int totaltems=response.body().getQuoteItemList().size();
                          setUser(response.body().getCustInfoList(),totaltems);
                          setQuoteList(response.body().getQuoteItemList());

                        }
                        else
                        {
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<QuotationResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSWarehouse.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setQuoteList(List<QuotationResponse.QuoteItem> quoteItemList) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(mLayoutManager);
        QutationAdapter qutationAdapter = new QutationAdapter(this, quoteItemList);
        rvItems.setAdapter(qutationAdapter);
        for (int i=0;i<quoteItemList.size();i++){
            if (quoteItemList.get(i).getIsScan()==null||quoteItemList.get(i).getIsScan().equalsIgnoreCase("")){
                btSumit.setEnabled(false);
                btSumit.setClickable(false);
                btSumit.setAlpha(0.6f);
            }
            else if (quoteItemList.get(i).getIsScan().equalsIgnoreCase("1")){
                btSumit.setEnabled(true);
                btSumit.setClickable(true);
                btSumit.setAlpha(1);
            }
        }
    }

    public void setUser(List<QuotationResponse.CustInfo> custInfoList, int totaltems) {
        tvLocate.setText(custInfoList.get(0).getAddress()+","+custInfoList.get(0).getCity()+","+custInfoList.get(0).getState()+","+custInfoList.get(0).getPostalCode());
        tvName.setText(custInfoList.get(0).getName());
        if (custInfoList.get(0).getPhone()==null|| custInfoList.get(0).getPhone().equalsIgnoreCase("")){
            tvPhone.setText("N/A");
        }
        else {
            tvPhone.setText(custInfoList.get(0).getPhone());
        }
        if (custInfoList.get(0).getEmail()==null|| custInfoList.get(0).getEmail().equalsIgnoreCase("")){
            tvEmail.setText("N/A");
        }
        else {
            tvEmail.setText(custInfoList.get(0).getEmail());
        }
        tvItems.setText("TOTAL ITEMS ("+String.valueOf(totaltems)+")");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReturnValue(String selectedValue) {
        selectValue=selectedValue;
        submitOrder(selectValue);
        Log.d("selectValue","selectValue"+selectValue);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        if(shouldExecuteOnResume){
            getQuoteData(quoteId,customerId);
            SharedPreferences prefs = getSharedPreferences("Scan", MODE_PRIVATE);
            quoteId1 = prefs.getString("quoteId", null);
            quantity = prefs.getString("quantity", null);
            totalQuantity = prefs.getString("quantity", null);
            total= prefs.getString("total", null);
            if (quoteId1 == null && quantity == null) {

            }
            else {
                QuatityDialog quatityDialog=new QuatityDialog(CustDetailActivity.this,quoteId1,quantity,totalQuantity);
                quatityDialog.show();
            }
        } else{
            shouldExecuteOnResume = true;
        }

    }

    @Override
    public void openSelector(String selectedValue,String quantity1) {
        if (selectedValue!=null){
            totalQuantity=quantity1;
            wheel.setVisibility(View.VISIBLE);
            btSumit.setVisibility(View.GONE);
            List<String> datalist1=new ArrayList<String>();
            int totalQuant=Integer.parseInt(total);
            int number=Integer.parseInt(quantity1);
            if (totalQuant>number|| totalQuant==number) {
                for (int i = 0; i <= totalQuant; i++) {
                    datalist1.add(String.valueOf(i));
                }
            }
            else {
                for (int i = 0; i <= number; i++) {
                    datalist1.add(String.valueOf(i));
                }
            }
            wheelPicker.setData(datalist1);
            wheelPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
                @Override
                public void onItemSelected(WheelPicker picker, Object data, int position) {
                    selectedQuantity = (String.valueOf(data));
                    Log.d("hour","hour"+quantity);
                }


            });
        }
        else {

        }
    }


    @BindView(R.id.tv_locate)
    TextView tvLocate;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.tv_items)
    TextView tvItems;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_items)
    RecyclerView rvItems;

    @BindView(R.id.wheel)
    LinearLayout wheel;

    @BindView(R.id.wheel_picker)
    WheelPicker wheelPicker;

    @BindView(R.id.bt_sumit)
    Button btSumit;

    @OnClick(R.id.bt_sumit)
    void btsumit(){
        CustomDialog customDialog=new CustomDialog(CustDetailActivity.this);
        customDialog.show();
    }

    @OnClick(R.id.done)
    void done(){
        wheel.setVisibility(View.GONE);
        btSumit.setVisibility(View.VISIBLE);
        if (selectedQuantity!=null) {
            QuatityDialog quatityDialog=new QuatityDialog(CustDetailActivity.this,quoteId1,selectedQuantity,totalQuantity);
            quatityDialog.show();
        }
    }


    @OnClick(R.id.cancel)
    void cancel(){
        wheel.setVisibility(View.GONE);
        btSumit.setVisibility(View.VISIBLE);
        QuatityDialog quatityDialog=new QuatityDialog(CustDetailActivity.this,quoteId1,quantity,totalQuantity);
        quatityDialog.show();
    }

    @OnClick(R.id.rl_phone)
    void call(){
        if (!tvPhone.getText().toString().equalsIgnoreCase("N/A")) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(CustDetailActivity.this);
            builder1.setMessage(tvPhone.getText().toString());
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Call",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                            dialIntent.setData(Uri.parse("tel:" + tvPhone.getText().toString()));
                            startActivity(dialIntent);
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            Button pbutton = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
            Button nbutton = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
            pbutton.setTextColor(Color.parseColor("#000000"));
            nbutton.setTextColor(Color.parseColor("#000000"));
        }
    }

    @OnClick(R.id.rl_email)
    void setTvEmail(){
        if (!tvEmail.getText().toString().equalsIgnoreCase("N/A")) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto: "+tvEmail.getText().toString()));
            startActivity(Intent.createChooser(emailIntent, "Email via.."));
        }
    }
}
