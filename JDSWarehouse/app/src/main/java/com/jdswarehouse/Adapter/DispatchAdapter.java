package com.jdswarehouse.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdswarehouse.Activity.CustDetailActivity;
import com.jdswarehouse.R;
import com.jdswarehouse.Response.DataResponse;
import com.jdswarehouse.Response.DispatchResponse;
import com.jdswarehouse.Retrofit.JDSWarehouseService;
import com.jdswarehouse.Retrofit.RestClient;
import com.jdswarehouse.Utilites.BaseActivity;
import com.jdswarehouse.Utilites.JDSWarehouse;
import com.jdswarehouse.Utilites.Preferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dikhong on 02-07-2018.
 */

public class DispatchAdapter extends RecyclerView.Adapter<DispatchAdapter.ViewHolder> {
    private List<DispatchResponse.DispatchData> dispatchResponses;
    private Context context;
    String latitude,longitude;

    public DispatchAdapter(Context context, List<DispatchResponse.DispatchData> dispatchResponses,String latitude,String longitude) {
        this.dispatchResponses = dispatchResponses;
        this.context = context;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    @Override
    public DispatchAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dispact_list, viewGroup, false);
        return new DispatchAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final DispatchAdapter.ViewHolder viewHolder, final int i) {
        final DispatchResponse.DispatchData dispatchData=dispatchResponses.get(i);
        viewHolder.tvShop.setText(dispatchData.getCustomer());
        viewHolder.tvOrderNo.setText(dispatchData.getReferenceNo());
        viewHolder.tvNumber.setText(String.valueOf(i+1));
        viewHolder.tvName.setText(dispatchData.getUsername());
        if (dispatchData.getStatus().equalsIgnoreCase("completed")){
            viewHolder.tvNumber.setBackgroundColor(context.getColor(R.color.light_green));
        }
        else {
            viewHolder.tvNumber.setBackgroundColor(context.getColor(R.color.colorGray));
        }
        if (dispatchData.getLoadBy()==null|| dispatchData.getLoadBy().equalsIgnoreCase("")){
            viewHolder.llAssign.setVisibility(View.GONE);
          viewHolder.btAssign.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.btAssign.setVisibility(View.GONE);
            viewHolder.llAssign.setVisibility(View.VISIBLE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) context;
                Intent intent = new Intent(activity,CustDetailActivity.class);
                intent.putExtra("quotation_id",dispatchData.getId());
                intent.putExtra("customer_id",dispatchData.getCustomerId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        viewHolder.btAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             assignMySelf(dispatchData);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dispatchResponses.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public  void assignMySelf(final DispatchResponse.DispatchData item) {
        final JDSWarehouseService jdsWarehouseService = RestClient.getClient().create(JDSWarehouseService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsWarehouseService.assignMySelf(item.getId(), item.getCustomerId(),Preferences.getInstance().getAuthKey(),latitude,longitude).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.body().getFlag()==1) {
                    Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, JDSWarehouse.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvShop,tvOrderNo,tvName,tvNumber;
        LinearLayout llAssign;
        private Button btAssign;
        public ViewHolder(View view) {
            super(view);
            tvNumber= (TextView)view.findViewById(R.id.tv_number);
            tvShop= (TextView)view.findViewById(R.id.tv_shop);
            tvOrderNo = (TextView)view.findViewById(R.id.tv_order);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            btAssign=(Button)view.findViewById(R.id.bt_assign);
            llAssign=(LinearLayout)view.findViewById(R.id.ll_assign);

        }
    }

}