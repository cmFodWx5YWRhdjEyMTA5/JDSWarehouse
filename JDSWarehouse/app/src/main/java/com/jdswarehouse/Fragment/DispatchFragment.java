package com.jdswarehouse.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.jdswarehouse.Adapter.DispatchAdapter;
import com.jdswarehouse.R;
import com.jdswarehouse.Response.DispatchResponse;
import com.jdswarehouse.Retrofit.JDSWarehouseService;
import com.jdswarehouse.Retrofit.RestClient;
import com.jdswarehouse.Utilites.BaseActivity;
import com.jdswarehouse.Utilites.GPSTracker;
import com.jdswarehouse.Utilites.JDSWarehouse;
import com.jdswarehouse.Utilites.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DispatchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public DispatchFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_dispatch)
    RecyclerView rvDispatch;

    @BindView(R.id.iv_no_data)
    ImageView ivNoData;

    GPSTracker gpsTracker;
    double latitude;
    double longitude;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dispatch, container, false);
        ButterKnife.bind(this,view);
        setRecycle();
        getDispatchList();
        gpsTracker = new GPSTracker(getContext());
        if(gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        }
        return view;
    }

    private void setRecycle() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvDispatch.setLayoutManager(mLayoutManager);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getDispatchList() {
        final JDSWarehouseService jdsWarehouseService = RestClient.getClient().create(JDSWarehouseService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsWarehouseService.dispatch(Preferences.getInstance().getAuthKey(),String.valueOf(latitude),String.valueOf(longitude)).enqueue(new Callback<DispatchResponse>() {
            @Override
            public void onResponse(Call<DispatchResponse> call, Response<DispatchResponse> response) {
                if (response.body()==null){
                    ivNoData.setVisibility(View.VISIBLE);
                    rvDispatch.setVisibility(View.GONE);
                }
                else {
                    if (response.body().getFlag() == 1) {
                        ivNoData.setVisibility(View.GONE);
                        rvDispatch.setVisibility(View.VISIBLE);
                        DispatchAdapter dispatchAdapter = new DispatchAdapter(getContext(), response.body().getDispatchDataList(), String.valueOf(latitude), String.valueOf(longitude));
                        rvDispatch.setAdapter(dispatchAdapter);
                    } else {
                        ivNoData.setVisibility(View.VISIBLE);
                        rvDispatch.setVisibility(View.GONE);
                    }
                }
                ((BaseActivity) getActivity()).hideProgressBar();

            }

            @Override
            public void onFailure(Call<DispatchResponse> call, Throwable t) {
                ((BaseActivity) getActivity()).hideProgressBar();
                Toast.makeText(getContext(), JDSWarehouse.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
