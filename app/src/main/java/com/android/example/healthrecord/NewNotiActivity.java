/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.healthrecord;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.example.healthrecord.R;

import java.util.Calendar;

import static com.android.example.healthrecord.NotificationActivity.EXTRA_NOTI_UPDATE_BLOODP;
import static com.android.example.healthrecord.NotificationActivity.EXTRA_NOTI_UPDATE_BLOODS;
import static com.android.example.healthrecord.NotificationActivity.EXTRA_NOTI_UPDATE_HOUR;
import static com.android.example.healthrecord.NotificationActivity.EXTRA_NOTI_UPDATE_MINUTE;
import static com.android.example.healthrecord.NotificationActivity.EXTRA_NOTI_UPDATE_WEIGHT;

/**
 * This class displays a screen where the user enters a new word.
 * The NewWordActivity returns the entered word to the calling activity
 * (MainActivity) which then stores the new word and updates the list of
 * displayed words.
 */
public class NewNotiActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.codinginflow.architectureexample.EXTRA_IDE";
    public static final String EXTRA_NOTI_HOUR = "com.example.android.roomwordsample.NOTI.HOUR";
    public static final String EXTRA_NOTI_MINUTE = "com.example.android.roomwordsample.NOTI.MIN";
    public static final String EXTRA_NOTI_WEIGHT = "com.example.android.roomwordsample.NOTI.WEI";
    public static final String EXTRA_NOTI_BLOODP = "com.example.android.roomwordsample.NOTI.BP";
    public static final String EXTRA_NOTI_BLOODS = "com.example.android.roomwordsample.NOTI.BS";

    private TextView mTimeView;
    private CheckBox mCheckWeight;
    private CheckBox mCheckBloodP;
    private CheckBox mCheckBloodS;
    private Integer HOUR;
    private Integer MINUTE;
    private Boolean WEIGHT;
    private Boolean BLOODP;
    private Boolean BLOODS;
    private int alarm_id=-1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_noti);

        mTimeView = findViewById(R.id.textView_Time);
        mCheckWeight=findViewById(R.id.checkBox);
        mCheckBloodP=findViewById(R.id.checkBox3);
        mCheckBloodS=findViewById(R.id.checkBox2);


        final Bundle extras = getIntent().getExtras();
        HOUR = 9;
        MINUTE = 0;

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            HOUR = extras.getInt(EXTRA_NOTI_UPDATE_HOUR, 9);
            MINUTE = extras.getInt(EXTRA_NOTI_UPDATE_MINUTE,0);
            WEIGHT = extras.getBoolean(EXTRA_NOTI_UPDATE_WEIGHT);
            BLOODP = extras.getBoolean(EXTRA_NOTI_UPDATE_BLOODP);
            BLOODS = extras.getBoolean(EXTRA_NOTI_UPDATE_BLOODS);

            mTimeView.setText(String.format("%02d:%02d", HOUR, MINUTE));
            mCheckWeight.setChecked(WEIGHT);
            mCheckBloodS.setChecked(BLOODS);
            mCheckBloodP.setChecked(BLOODP);
            alarm_id=HOUR*60+MINUTE;

        } // Otherwise, start with empty fields.

        //Time picker set time
        final Button btn_time = findViewById(R.id.button2);
        btn_time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                // Create a new instance of TimePickerDialog and return it
                new TimePickerDialog(NewNotiActivity.this, new TimePickerDialog.OnTimeSetListener(){

                    @Override
                    public void onTimeSet(TimePicker view, int mHour, int mMinute) {
                        mTimeView.setText(String.format("%02d:%02d", mHour, mMinute));
                        HOUR = mHour;
                        MINUTE = mMinute;
                    }
                }, HOUR, MINUTE, false).show();
            }
        });





        final Button button = findViewById(R.id.btn_save);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, MainActivity)
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Create a new Intent for the reply.
                // Get the checkbox checked
                WEIGHT = mCheckWeight.isChecked();
                BLOODP = mCheckBloodP.isChecked();
                BLOODS = mCheckBloodS.isChecked();
                Intent replyIntent = new Intent();
                if ((!WEIGHT)&&(!BLOODP)&&(!BLOODS)) {
                    // No item checked, set the result accordingly.
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Put the new word in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_NOTI_HOUR, HOUR);
                    replyIntent.putExtra(EXTRA_NOTI_MINUTE, MINUTE);
                    replyIntent.putExtra(EXTRA_NOTI_WEIGHT, WEIGHT);
                    replyIntent.putExtra(EXTRA_NOTI_BLOODP, BLOODP);
                    replyIntent.putExtra(EXTRA_NOTI_BLOODS, BLOODS);
                    int id = getIntent().getIntExtra(EXTRA_ID, -1);
                    if (id != -1) {
                        replyIntent.putExtra(EXTRA_ID, id);
                    }
                    replyIntent.putExtra( "ALARM",alarm_id );
                    // Set the result status to indicate success.
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
