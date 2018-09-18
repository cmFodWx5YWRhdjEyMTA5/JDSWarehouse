package com.jdswarehouse.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.jdswarehouse.Activity.ScannerActivity;
import com.jdswarehouse.R;
import com.jdswarehouse.Response.QuotationResponse;

import java.util.List;

/**
 * Created by dikhong on 04-07-2018.
 */

public class QutationAdapter extends RecyclerView.Adapter<QutationAdapter.ViewHolder> {
    private List<QuotationResponse.QuoteItem> quoteItemList;
    private Context context;

    public QutationAdapter(Context context, List<QuotationResponse.QuoteItem> quoteItemList) {
        this.quoteItemList = quoteItemList;
        this.context = context;
    }

    @Override
    public QutationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quote_list, viewGroup, false);
        return new QutationAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final QutationAdapter.ViewHolder viewHolder, final int i) {
        final QuotationResponse.QuoteItem quoteItem=quoteItemList.get(i);
        viewHolder.tvNo.setText(String.valueOf(i+1));
        viewHolder.tvProduct.setText(quoteItem.getProductName());
        if(i %2 == 1)
        {
            viewHolder.table.setBackgroundColor(Color.parseColor("#E1E1E1"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            viewHolder.table.setBackgroundColor(Color.parseColor("#A9A9A9"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }

        if (quoteItem.getIsScan()==null||quoteItem.getIsScan().equalsIgnoreCase("")){
            viewHolder.tvScan.setBackgroundResource(R.drawable.bt_fill_red);
            String newStr = quoteItem.getQuantity().substring(0, quoteItem.getQuantity().indexOf("."));
            viewHolder.tvQuatity.setText(newStr);
        }
        else if (quoteItem.getIsScan().equalsIgnoreCase("1")){
            viewHolder.tvScan.setBackgroundResource(R.drawable.bt_fill_green);
            if (quoteItem.getItemLoadQuantity()==null|| quoteItem.getItemLoadQuantity().equalsIgnoreCase("")){
                String newStr = quoteItem.getQuantity().substring(0, quoteItem.getQuantity().indexOf("."));
                viewHolder.tvQuatity.setText(newStr);
            }
            else {
                viewHolder.tvQuatity.setText(quoteItem.getItemLoadQuantity());
            }

        }

        if (quoteItem.getIsScan()==null||quoteItem.getIsScan().equalsIgnoreCase("")){
            viewHolder.ivTick.setImageResource(R.drawable.tick);
        }
        else if (quoteItem.getIsScan().equalsIgnoreCase("1")){
            viewHolder.ivTick.setImageResource(R.drawable.tick_color);
        }

        viewHolder.tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) context;
                QuotationResponse.QuoteItem quoteItem1 = (QuotationResponse.QuoteItem) quoteItemList.get(i);
                Intent intent = new Intent(activity,ScannerActivity.class);
                intent.putExtra(ScannerActivity.SCAN, quoteItem1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


    }

    @Override
    public int getItemCount() {
        return quoteItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNo,tvProduct,tvQuatity,tvScan;
        ImageView ivTick;
        TableLayout table;
        public ViewHolder(View view) {
            super(view);
            tvNo= (TextView)view.findViewById(R.id.tv_no);
            tvProduct= (TextView)view.findViewById(R.id.tv_product);
            tvQuatity = (TextView)view.findViewById(R.id.tv_quatity);
            tvScan = (TextView) view.findViewById(R.id.tv_scan);
            ivTick=(ImageView)view.findViewById(R.id.iv_tick);
            table=(TableLayout)view.findViewById(R.id.table);
        }
    }

}
