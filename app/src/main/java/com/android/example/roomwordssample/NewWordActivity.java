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

package com.android.example.roomwordssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.example.roomwordssample.R;

import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_UPDATE_WORD;
import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_UPDATE_WORD1;
import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_UPDATE_WORD2;
import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_UPDATE_WORD3;
import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_UPDATE_WORD4;

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

    private EditText mEditWordView;
    private EditText mEditWordView1;
    private EditText mEditWordView2;
    private EditText mEditWordView3;
    private EditText mEditWordView4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        mEditWordView = findViewById(R.id.edit_weight);
        mEditWordView1=findViewById(R.id.edit_pressure);
        mEditWordView2=findViewById(R.id.edit_pressuredown);
        mEditWordView3=findViewById(R.id.edit_bloodsugar);
        mEditWordView4=findViewById(R.id.edit_time);


       final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            String word = extras.getString(EXTRA_DATA_UPDATE_WORD, "");
            String word1= extras.getString(EXTRA_DATA_UPDATE_WORD1,"");
            String word2= extras.getString(EXTRA_DATA_UPDATE_WORD2,"");
            String word3= extras.getString(EXTRA_DATA_UPDATE_WORD3,"");
            String word4= extras.getString(EXTRA_DATA_UPDATE_WORD4,"");
            if (!word.isEmpty()) {
                mEditWordView.setText(word+"KG");
                mEditWordView1.setText(word1+"mmHG");
                mEditWordView2.setText(word2+"mmHG");
                mEditWordView3.setText(word3+"mg/dl");
                mEditWordView4.setText(word4);
                mEditWordView.setSelection(word.length());
                mEditWordView1.setSelection(word1.length());
                mEditWordView2.setSelection(word2.length());
                mEditWordView3.setSelection(word3.length());
                mEditWordView4.setSelection(word4.length());
                mEditWordView.requestFocus();
                mEditWordView1.requestFocus();
                mEditWordView2.requestFocus();
                mEditWordView3.requestFocus();
                mEditWordView4.requestFocus();
            }
        } // Otherwise, start with empty fields.

        final Button button = findViewById(R.id.button_save);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, MainActivity)
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Create a new Intent for the reply.
                Intent replyIntent = new Intent();
               if (TextUtils.isEmpty(mEditWordView.getText())) {
                    // No word was entered, set the result accordingly.
                   setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Get the new word that the user entered.
                    String word = mEditWordView.getText().toString();
                    String word1= mEditWordView1.getText().toString();
                    String word2= mEditWordView2.getText().toString();
                    String word3= mEditWordView3.getText().toString();
                    String word4= mEditWordView4.getText().toString();
                    // Put the new word in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    replyIntent.putExtra(EXTRA_REPLY1,word1);
                    replyIntent.putExtra(EXTRA_REPLY2,word2);
                    replyIntent.putExtra(EXTRA_REPLY3,word3);
                    replyIntent.putExtra(EXTRA_REPLY4,word4);
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
}
