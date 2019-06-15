package com.android.example.roomwordssample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import static com.android.example.roomwordssample.MainActivity.UPDATE_WORD_ACTIVITY_REQUEST_CODE;
import static java.lang.Boolean.TRUE;

//import com.android.example.dcnlab.R;

public class NotificationActivity extends AppCompatActivity {

    private NotiViewModel mNotiViewModel;

    public static final int NEW_NOTI_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTI_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_NOTI_UPDATE_HOUR = "0";
    public static final String EXTRA_NOTI_UPDATE_MINUTE = "1";
    public static final String EXTRA_NOTI_UPDATE_WEIGHT = "2";
    public static final String EXTRA_NOTI_UPDATE_BLOODP = "3";
    public static final String EXTRA_NOTI_UPDATE_BLOODS = "4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNotiViewModel = ViewModelProviders.of(this).get(NotiViewModel.class);


        RecyclerView recyclerView = findViewById(R.id.notirecyclerview);
        final NotiListAdapter adapter = new NotiListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNotiViewModel.getmAllNoti().observe(this, new Observer<List<Noti>>() {
            @Override
            public void onChanged(@Nullable final List<Noti> noti) {
                // Update the cached copy of the words in the adapter.
                adapter.setNoti(noti);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTI_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Noti noti = new Noti(data.getIntExtra(NewNotiActivity.EXTRA_NOTI_HOUR, 0),
                    data.getIntExtra(NewNotiActivity.EXTRA_NOTI_MINUTE, 0),
                    data.getBooleanExtra(NewNotiActivity.EXTRA_NOTI_WEIGHT, TRUE),
                    data.getBooleanExtra(NewNotiActivity.EXTRA_NOTI_BLOODP, TRUE),
                    data.getBooleanExtra(NewNotiActivity.EXTRA_NOTI_BLOODS, TRUE));
            // Save the data
            mNotiViewModel.insert(noti);
        } else if (requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            // TODO: implement "UPDATE"
            int id =data.getIntExtra(NewNotiActivity.EXTRA_ID,-1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            Noti noti = new Noti(data.getIntExtra(NewNotiActivity.EXTRA_NOTI_HOUR, 0),
                    data.getIntExtra(NewNotiActivity.EXTRA_NOTI_MINUTE, 0),
                    data.getBooleanExtra(NewNotiActivity.EXTRA_NOTI_WEIGHT, TRUE),
                    data.getBooleanExtra(NewNotiActivity.EXTRA_NOTI_BLOODP, TRUE),
                    data.getBooleanExtra(NewNotiActivity.EXTRA_NOTI_BLOODS, TRUE));
            noti.setId(id);
            mNotiViewModel.update(noti);

            Toast.makeText(this,"提醒已設定" + noti.getHour() + ":"
                    + noti.getMinute(),Toast.LENGTH_SHORT).show();


        }else {
            Toast.makeText(
                    this, "欄位不可為空", Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdateNotiActivity(Noti noti) {
        Intent intent = new Intent(this, NewNotiActivity.class);
        intent.putExtra(EXTRA_NOTI_UPDATE_HOUR, noti.getHour());
        intent.putExtra(EXTRA_NOTI_UPDATE_MINUTE, noti.getMinute());
        intent.putExtra(EXTRA_NOTI_UPDATE_WEIGHT, noti.getWeight());
        intent.putExtra(EXTRA_NOTI_UPDATE_BLOODP, noti.getPressure());
        intent.putExtra(EXTRA_NOTI_UPDATE_BLOODS, noti.getBloSugar());

        intent.putExtra(NewNotiActivity.EXTRA_ID, noti.getId());
        startActivityForResult(intent, UPDATE_NOTI_ACTIVITY_REQUEST_CODE);
    }

}
