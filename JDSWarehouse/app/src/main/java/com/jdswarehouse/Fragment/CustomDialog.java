package com.jdswarehouse.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jdswarehouse.R;

/**
 * Created by dikhong on 04-07-2018.
 */

public class CustomDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    RadioGroup rbSelect;
    RadioButton radioButton;
    public Button btSubmit;
    ImageView ivClose;
    String selectedRadio="Vehicle";

    public CustomDialog(Activity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        setCancelable(false);
        btSubmit = (Button) findViewById(R.id.bt_submit);
        ivClose = (ImageView) findViewById(R.id.iv_close);
        rbSelect= (RadioGroup) findViewById(R.id.rb_select);
        btSubmit.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        rbSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                  @Override
                                                  public void onCheckedChanged(RadioGroup group, int checkedId)
                                                  {
                                                      radioButton = (RadioButton) findViewById(checkedId);
                                                      selectedRadio=radioButton.getText().toString();
                                                      Toast.makeText(getContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                                                  }
                                              }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                MyDialogFragmentListener myDialogFragmentListener = (MyDialogFragmentListener)activity;
                myDialogFragmentListener.onReturnValue(selectedRadio);
                dismiss();
                break;
            case R.id.iv_close:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }


    public interface MyDialogFragmentListener {
        public void onReturnValue(String selectedValue);
    }
}
