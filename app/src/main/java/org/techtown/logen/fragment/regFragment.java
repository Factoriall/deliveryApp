package org.techtown.logen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.techtown.logen.R;

public class regFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_reg_sub, container, false);

        EditText editTextDelivery = view.findViewById(R.id.editTextDelivery);
        EditText editTextPrepay = view.findViewById(R.id.editTextPrepay);
        EditText editTextRemark = view.findViewById(R.id.editTextRemark);

        Bundle bundle = this.getArguments();
        boolean isNew = false;
        if(bundle != null)
            isNew = bundle.getBoolean("isNew");


        if(isNew){
            editTextDelivery.setText(String.valueOf(0));
            editTextPrepay.setText(String.valueOf(0));
        }
        else{
            editTextDelivery.setText(String.valueOf(bundle.getInt("delivery")));
            editTextPrepay.setText(String.valueOf(bundle.getInt("prepay")));
            editTextRemark.setText(bundle.getString("remark"));
        }


        return view;
    }
}