package org.techtown.logen.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.techtown.logen.R;
import org.techtown.logen.data.DepositData;
import org.techtown.logen.data.DepositResponse;
import org.techtown.logen.network.RetrofitClient;
import org.techtown.logen.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class regFragment extends Fragment {
    private ServiceApi service;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_reg_sub, container, false);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        //여러 변수 초기화
        final EditText editTextDelivery = view.findViewById(R.id.editTextDelivery);
        final EditText editTextPrepay = view.findViewById(R.id.editTextPrepay);
        final TextView textViewMoney = view.findViewById(R.id.textViewMoney);
        final EditText editTextRemark = view.findViewById(R.id.editTextRemark);
        Button buttonToLeader = view.findViewById(R.id.buttonToLeader);
        Button buttonToDepartment = view.findViewById(R.id.buttonToDepartment);

        //RegisterFragment로부터 bundle 받기
        Bundle bundle = this.getArguments();
        boolean isNew = false;
        int method = 0;
        int pid = 0;
        int year = 0;
        int month = 0;
        int day = 0;
        if(bundle != null) {
            isNew = bundle.getBoolean("isNew");
            method = bundle.getInt("method");
            pid = bundle.getInt("pid");
            year = bundle.getInt("year");
            month = bundle.getInt("month");
            day = bundle.getInt("day");
        }
        Log.d("fire1", Integer.toString(method));


        //수정할 시 값을 bundle에서 받기
        int deliveryPay = 0;
        int prepay = 0;
        String remark = "";
        if(!isNew){
            deliveryPay = bundle.getInt("delivery");
            prepay = bundle.getInt("prepay");
            remark = bundle.getString("remark");
        }
        editTextDelivery.setText(String.valueOf(deliveryPay));
        editTextPrepay.setText(String.valueOf(prepay));
        editTextRemark.setText(remark);

        textViewMoney.setText(String.valueOf(deliveryPay + prepay));

        //배송착불 바뀔 시 입금액에 변화를 주는 함수
        editTextDelivery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                int deliveryAfter = 0;

                if (!editable.toString().matches("")) {
                    deliveryAfter = Integer.parseInt(editable.toString());
                }
                int prepay = Integer.parseInt(editTextPrepay.getText().toString());
                textViewMoney.setText(String.valueOf(deliveryAfter + prepay));
            }
        });

        //집하선불 바뀔 시 입금액에 변화를 주는 함수
        editTextPrepay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                int delivery = Integer.parseInt(editTextDelivery.getText().toString());
                int prepayAfter = 0;
                if (!editable.toString().matches("")) {
                    prepayAfter = Integer.parseInt(editable.toString());
                    textViewMoney.setText(String.valueOf(delivery + prepayAfter));
                }
            }
        });


        final int finalMethod = method;
        final boolean finalIsNew = isNew;
        final int finalPid = pid;
        final int finalYear = year;
        final int finalMonth = month;
        final int finalDay = day;
        buttonToLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("fire", Integer.toString(finalMethod));
                if(finalMethod == 1){
                    Toast.makeText(getContext(), "팀장님한테 통장으로 보낼 수 없습니다!", Toast.LENGTH_LONG).show();
                }
                else{
                    if(finalIsNew){
                        //pid, method, year, month, day, delivery, prepay, remark
                        sendNewData(new DepositData(finalPid, finalMethod, finalYear,
                                finalMonth, finalDay, Integer.parseInt(editTextDelivery.getText().toString()),
                                Integer.parseInt(editTextPrepay.getText().toString()), editTextRemark.getText().toString()));

                    }
                    else{
                        sendModifiedData(new DepositData(finalPid, finalMethod, finalYear,
                                finalMonth, finalDay, Integer.parseInt(editTextDelivery.getText().toString()),
                                Integer.parseInt(editTextPrepay.getText().toString()), editTextRemark.getText().toString()));
                    }
                }
            }
        });

        buttonToDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalIsNew){
                    sendNewData(new DepositData(finalPid, finalMethod, finalYear,
                            finalMonth, finalDay, Integer.parseInt(editTextDelivery.getText().toString()),
                            Integer.parseInt(editTextPrepay.getText().toString()), editTextRemark.getText().toString()));
                }
                else{
                    sendModifiedData(new DepositData(finalPid, finalMethod, finalYear,
                            finalMonth, finalDay, Integer.parseInt(editTextDelivery.getText().toString()),
                            Integer.parseInt(editTextPrepay.getText().toString()), editTextRemark.getText().toString()));
                }
            }
        });

        return view;
    }

    private void sendNewData(DepositData data){
        service.registerDeposit(data).enqueue(new Callback<DepositResponse>() {
            //통신 성공 시
            @Override
            public void onResponse(Call<DepositResponse> call, Response<DepositResponse> response) {
                DepositResponse result = response.body();

                if(result.getResponseCode() == 404){
                    Toast.makeText(getContext(), "에러 발생", Toast.LENGTH_LONG).show();
                }
                else if(result.getResponseCode() == 200){
                    Toast.makeText(getContext(), "삽입 성공!", Toast.LENGTH_LONG).show();
                    if(getActivity() != null) {
                        getActivity().finish();
                    }
                }
            }
            //통신 실패 시
            @Override
            public void onFailure(Call<DepositResponse> call, Throwable t) {
                Toast.makeText(getContext(), "연결 오류", Toast.LENGTH_LONG).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });

    }

    private void sendModifiedData(DepositData data){
        service.editDeposit(data).enqueue(new Callback<DepositResponse>() {
            //통신 성공 시
            @Override
            public void onResponse(Call<DepositResponse> call, Response<DepositResponse> response) {
                DepositResponse result = response.body();

                if(result.getResponseCode() == 404){
                    Toast.makeText(getContext(), "에러 발생", Toast.LENGTH_LONG).show();
                }
                else if(result.getResponseCode() == 202){
                    Toast.makeText(getContext(), "이미 승인받은 정보, 고칠 수 없음", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "갱신 성공!", Toast.LENGTH_LONG).show();
                    if(getActivity() != null) {
                        getActivity().finish();
                    }
                }
            }
            //통신 실패 시
            @Override
            public void onFailure(Call<DepositResponse> call, Throwable t) {
                Toast.makeText(getContext(), "연결 오류", Toast.LENGTH_LONG).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });

    }
}