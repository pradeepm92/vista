package com.health.vistacan.forceupdate;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

import com.health.vistacan.R;


public class ForceUpdateDialog extends DialogFragment {

    private onDialogAction mCallback;


    public static ForceUpdateDialog newInstance(String dialogTitle) {

        ForceUpdateDialog picker = new ForceUpdateDialog();
        Bundle bundle = new Bundle();
        bundle.putString("dialogTitle", dialogTitle);
        picker.setArguments(bundle);
        return picker;
    }

    public ForceUpdateDialog() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View    view = inflater.inflate(R.layout.force_alert_dialog, null);


        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        Bundle args = this.getArguments();
        if (args != null) {
            String dialogTitle = args.getString("dialogTitle");
            this.getDialog().setTitle(dialogTitle);

            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        LinearLayout llok = view.findViewById(R.id.llok);

        llok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.doSendPlayStore();
                dismiss();
            }
        });


        return view;
    }


    public interface onDialogAction {
        void doSendPlayStore();


    }

    public void setListener(onDialogAction listener) {
        this.mCallback = listener;
    }

}

