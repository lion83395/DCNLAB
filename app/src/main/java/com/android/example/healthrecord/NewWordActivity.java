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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import static com.android.example.healthrecord.MainActivity.EXTRA_DATA_UPDATE_WORD;
import static com.android.example.healthrecord.MainActivity.EXTRA_DATA_UPDATE_WORD1;
import static com.android.example.healthrecord.MainActivity.EXTRA_DATA_UPDATE_WORD2;
import static com.android.example.healthrecord.MainActivity.EXTRA_DATA_UPDATE_WORD3;
import static com.android.example.healthrecord.MainActivity.EXTRA_DATA_UPDATE_WORD4;
import static com.android.example.healthrecord.MainActivity.EXTRA_DATA_UPDATE_WORD5;

/**
 * This class displays a screen where the user enters a new word.
 * The NewWordActivity returns the entered word to the calling activity
 * (MainActivity) which then stores the new word and updates the list of
 * displayed words.
 */
public class NewWordActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.codinginflow.architectureexample.EXTRA_ID";
    public static final String EXTRA_REPLY = "com.example.android.roomwordsample.REPLY";
    public static final String EXTRA_REPLY1 = "com.example.android.roomwordsample.REPLY1";
    public static final String EXTRA_REPLY2 = "com.example.android.roomwordsample.REPLY2";
    public static final String EXTRA_REPLY3 = "com.example.android.roomwordsample.REPLY3";
    public static final String EXTRA_REPLY4 = "com.example.android.roomwordsample.REPLY4";
    public static final String EXTRA_REPLY5 = "com.example.android.roomwordsample.REPLY5";

    private EditText mEditWordView;
    private EditText mEditWordView1;
    private EditText mEditWordView2;
    private EditText mEditWordView3;
    private EditText mEditWordView4;
    private EditText mEditWordView5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        mEditWordView = findViewById(R.id.edit_weight);
        mEditWordView1=findViewById(R.id.edit_pressure);
        mEditWordView2=findViewById(R.id.edit_pressuredown);
        mEditWordView3=findViewById(R.id.edit_bloodsugar);
        mEditWordView4=findViewById(R.id.edit_time);
        mEditWordView5=findViewById(R.id.edit_time1);
        setTime();


       final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            String word = extras.getString(EXTRA_DATA_UPDATE_WORD, "");
            String word1= extras.getString(EXTRA_DATA_UPDATE_WORD1,"");
            String word2= extras.getString(EXTRA_DATA_UPDATE_WORD2,"");
            String word3= extras.getString(EXTRA_DATA_UPDATE_WORD3,"");
            String word4= extras.getString(EXTRA_DATA_UPDATE_WORD4,"");
            String word5= extras.getString(EXTRA_DATA_UPDATE_WORD5,"");
            if (!word.isEmpty()) {
                mEditWordView.setText(word);
                mEditWordView1.setText(word1);
                mEditWordView2.setText(word2);
                mEditWordView3.setText(word3);
                mEditWordView4.setText(word4);
                mEditWordView5.setText(word5);
                mEditWordView.setSelection(word.length());
                mEditWordView1.setSelection(word1.length());
                mEditWordView2.setSelection(word2.length());
                mEditWordView3.setSelection(word3.length());
                mEditWordView4.setSelection(word4.length());
                mEditWordView5.setSelection(word5.length());
                mEditWordView.requestFocus();
                mEditWordView1.requestFocus();
                mEditWordView2.requestFocus();
                mEditWordView3.requestFocus();
                mEditWordView4.requestFocus();
                mEditWordView5.requestFocus();
            }
        } // Otherwise, start with empty fields.

        final Button button = findViewById(R.id.button_save);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, MainActivity)
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Create a new Intent for the reply.
                Intent replyIntent = new Intent();
               if ((TextUtils.isEmpty(mEditWordView4.getText())||TextUtils.isEmpty(mEditWordView5.getText())))
                {
                    // No word was entered, set the result accordingly.
                   setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Get the new word that the user entered.
                    String word = mEditWordView.getText().toString();
                    String word1= mEditWordView1.getText().toString();
                    String word2= mEditWordView2.getText().toString();
                    String word3= mEditWordView3.getText().toString();
                    String word4= mEditWordView4.getText().toString();
                    String word5=mEditWordView5.getText().toString();
                    // Put the new word in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    replyIntent.putExtra(EXTRA_REPLY1,word1);
                    replyIntent.putExtra(EXTRA_REPLY2,word2);
                    replyIntent.putExtra(EXTRA_REPLY3,word3);
                    replyIntent.putExtra(EXTRA_REPLY4,word4);
                    replyIntent.putExtra(EXTRA_REPLY5,word5);
                   int id = getIntent().getIntExtra(EXTRA_ID, -1);
                   if (id != -1) {
                       replyIntent.putExtra(EXTRA_ID, id);
                   }
                    // Set the result status to indicate success.
                    setResult(RESULT_OK, replyIntent);
               }
                finish();
            }
        });
    }
    public void setTime(){
        Calendar c = Calendar.getInstance();
        int y=c.get( Calendar.YEAR );
        int m= c.get( Calendar.MONTH )+1;
        int d= c.get( Calendar.DAY_OF_MONTH );
        int h= c.get( Calendar.HOUR );
        int min= c.get( Calendar.MINUTE );
        String mi = "";
        String ho ="";
        if(min<10){mi="0"+min;}else{mi=min+"";}
        if(h<10){ho="0"+h;}else{ho=h+"";}
        mEditWordView4.setText(y+"/"+m+"/"+d );
        mEditWordView5.setText(ho+":"+mi );
    }
}
