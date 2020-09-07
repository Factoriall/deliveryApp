package org.techtown.logen.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.techtown.logen.R;
import org.techtown.logen.activity.DepositActivity;
import org.techtown.logen.data.DepositSeekRegisterData;
import org.techtown.logen.data.DepositSeekRegisterResponse;
import org.techtown.logen.network.RetrofitClient;
import org.techtown.logen.network.ServiceApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {
    regFragment regFragment;
    EditText editTextDate;
    Button button;
    private ServiceApi service;
    int isSentBy;
    int userCode;
    int years;
    int months;
    int days;

    Calendar cal;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_dep_register, container, false);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        //DepositActivity로부터 유저 정보(유저 코드, 이름, 팀코드, 지위)를 받는다
        DepositActivity activity = (DepositActivity) getActivity();
        userCode = activity.getUserCode();

        final String username = activity.getUsername();
        final int teamCode = activity.getTeamCode();
        final int grade = activity.getGrade();

        Toast.makeText(getContext(), userCode +", "+username+", "+teamCode+","+grade,Toast.LENGTH_LONG).show();

        //isSentBy 통해 입금 방법 알려줌
        RadioGroup rGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.byCash)
                    isSentBy = 0;
                else if(checkedId == R.id.byBank)
                    isSentBy = 1;
                else
                    isSentBy = 2;
            }
        });

        //datepicker 통해 데이터 받기
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextDate.setInputType(InputType.TYPE_NULL);

        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);//어제로 설정

        years = cal.get(cal.YEAR);
        months = cal.get(cal.MONTH) + 1;
        days = cal.get(cal.DAY_OF_MONTH);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        editTextDate.setText(df.format(cal.getTime()));

        editTextDate.setOnClickListener(new View.OnClickListener() {//건들시 데이터 교체
            @Override
            public void onClick(View view) {
                changeDate();
            }
        });

        //등록 및 수정 버튼 누를 시 데이터가 있는지 서버에 확인 이벤트
        button = view.findViewById(R.id.buttonRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lookupDepositTable(new DepositSeekRegisterData(userCode, isSentBy, years, months, days));
                button.setVisibility(View.GONE);
            }
        });

        return view;
    }

    //서버 확인 후 데이터 받기
    private void lookupDepositTable(DepositSeekRegisterData data){
        service.SeekDepositRegister(data).enqueue(new Callback<DepositSeekRegisterResponse>() {
            //통신 성공 시
            @Override
            public void onResponse(Call<DepositSeekRegisterResponse> call, Response<DepositSeekRegisterResponse> response) {
                DepositSeekRegisterResponse result = response.body();

                if (!result.getIsExist()) {//등록
                    regFragment = new regFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pid", userCode);
                    bundle.putBoolean("isNew", true);
                    bundle.putInt("method", isSentBy);
                    bundle.putInt("year", years);
                    bundle.putInt("month", months);
                    bundle.putInt("day", days);

                    regFragment.setArguments(bundle);
                    getChildFragmentManager().beginTransaction().replace(R.id.subcontainer, regFragment).commit();
                }
                else{
                    if(result.getIsDepartmentAssigned() || result.getIsLeaderAssigned()) {//이미 승인 받았으면 안됨
                        Toast.makeText(getContext(), "이미 승인 받았습니다!", Toast.LENGTH_LONG).show();
                    }
                    else {//수정 가능
                        regFragment = new regFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("pid", userCode);
                        bundle.putBoolean("isNew", false);
                        bundle.putInt("method", isSentBy);
                        bundle.putInt("year", years);
                        bundle.putInt("month", months);
                        bundle.putInt("day", days);
                        bundle.putInt("delivery", result.getDelivery());
                        bundle.putInt("prepay", result.getPrepay());
                        bundle.putString("remark", result.getRemark());
                        regFragment.setArguments(bundle);
                        getChildFragmentManager().beginTransaction().replace(R.id.subcontainer, regFragment).commit();
                    }
                }
            }
            //통신 실패 시
            @Override
            public void onFailure(Call<DepositSeekRegisterResponse> call, Throwable t) {
                Toast.makeText(getContext(), "연결 오류", Toast.LENGTH_LONG).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }


    private void changeDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),//현 액티비티
                birthDateSetListener,//onDateSet에 대한 인터페이스
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener birthDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);

            years = year;
            months = month + 1;
            days = day;

            String monthStr = Integer.toString(month);
            String dayStr = Integer.toString(day);
            if(month + 1 < 10)
                monthStr = "0" + (month + 1);
            if(day < 10)
                dayStr = "0" + day;

            String date = year + "-" + monthStr + "-" + dayStr;
            editTextDate.setText(date);
        }
    };
}
