package org.techtown.logen.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.logen.R;
import org.techtown.logen.SimpleData;
import org.techtown.logen.fragment.LookupFragment;
import org.techtown.logen.fragment.MergeFragment;
import org.techtown.logen.fragment.RegisterFragment;

public class DepositActivity extends AppCompatActivity {
    RegisterFragment fragment1;
    LookupFragment fragment2;
    MergeFragment fragment3;

    int userCode;
    String username;
    int teamCode;
    int grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        fragment1 = new RegisterFragment();
        fragment2 = new LookupFragment();
        fragment3 = new MergeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.subcontainer, fragment1).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.registerTab:
                                getSupportFragmentManager().beginTransaction().replace(R.id.subcontainer, fragment1).commit();
                                return true;
                            case R.id.lookupTab:
                                getSupportFragmentManager().beginTransaction().replace(R.id.subcontainer, fragment2).commit();
                                return true;
                            case R.id.mergeTab:
                                getSupportFragmentManager().beginTransaction().replace(R.id.subcontainer, fragment3).commit();
                                return true;
                        }
                        return false;
                    }
                }
        );

        Intent intent = getIntent();
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            SimpleData data = bundle.getParcelable("data");

            userCode = data.getUserCode();
            username = data.getUsername();
            teamCode = data.getTeamCode();
            grade = data.getGrade();
        }
    }

    public int getUserCode(){
        return userCode;
    }

    public String getUsername(){
        return username;
    }

    public int getTeamCode(){
        return teamCode;
    }

    public int getGrade(){
        return grade;
    }
}

