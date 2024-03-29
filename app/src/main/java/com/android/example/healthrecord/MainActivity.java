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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.healthrecord.R;

import java.util.List;

/**
 * This class displays a list of words in a RecyclerView.
 * The words are saved in a Room database.
 * The layout for this activity also displays an FAB that
 * allows users to start the NewWordActivity to add new words.
 * Users can delete a word by swiping it away, or delete all words
 * through the Options menu.
 * Whenever a new word is added, deleted, or updated, the RecyclerView
 * showing the list of words automatically updates.
 */
public class MainActivity extends AppCompatActivity {

    private WordViewModel mWordViewModel;
    private TextView textView1;
    private TextView textView2;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_UPDATE_WORD = "extra_word_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_WORD1 = "1";
    public static final String EXTRA_DATA_UPDATE_WORD2 = "2";
    public static final String EXTRA_DATA_UPDATE_WORD3 = "3";
    public static final String EXTRA_DATA_UPDATE_WORD4 = "4";
    public static final String EXTRA_DATA_UPDATE_WORD5 = "5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView1=findViewById(R.id.textView);
        //textView2=findViewById(R.id.textView2);

        // Setup the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup the WordViewModel
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        // Get all the words from the database
        // and associate them to the adapter
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });

        // Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // We are not implementing onMove() in this app
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the use swipes a word,
                    // delete that word from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Word myWord = adapter.getWordAtPosition(position);
                        Toast.makeText(MainActivity.this,
                                getString(R.string.delete_word_preamble) + " " +
                                myWord.getTime(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        mWordViewModel.deleteWord(myWord);
                    }
                });
        // Attach the item touch helper to the recycler view
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new WordListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Word word = adapter.getWordAtPosition(position);
                launchUpdateWordActivity(word);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // The options menu has a single item "Clear all data now"
    // that deletes all the entries in the database
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();

            // Delete the existing data
            mWordViewModel.deleteAll();
            return true;
        } else if(id == R.id.set_noti){
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /** When the user enters a new word in the NewWordActivity,
     * that activity returns the result to this activity.
     * If the user entered a new word, save it in the database.

     * @param requestCode -- ID for the request
     * @param resultCode -- indicates success or failure
     * @param data -- The Intent sent back from the NewWordActivity,
     *             which includes the word that the user entered
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY),data.getStringExtra(NewWordActivity.EXTRA_REPLY1),data.getStringExtra(NewWordActivity.EXTRA_REPLY2)
            ,data.getStringExtra(NewWordActivity.EXTRA_REPLY3),data.getStringExtra(NewWordActivity.EXTRA_REPLY4),data.getStringExtra(NewWordActivity.EXTRA_REPLY5));
            // Save the data
            mWordViewModel.insert(word);
        } else if (requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            // TODO: implement "UPDATE"
            int id =data.getIntExtra(NewWordActivity.EXTRA_ID,-1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY),data.getStringExtra(NewWordActivity.EXTRA_REPLY1),data.getStringExtra(NewWordActivity.EXTRA_REPLY2)
            ,data.getStringExtra(NewWordActivity.EXTRA_REPLY3),data.getStringExtra(NewWordActivity.EXTRA_REPLY4),data.getStringExtra(NewWordActivity.EXTRA_REPLY5));
            word.setId(id);
            mWordViewModel.update(word);

            Toast.makeText(this,word.getTime()+"今日記錄完成",Toast.LENGTH_SHORT).show();


        }else {
            Toast.makeText(
                    this, "欄位不可為空", Toast.LENGTH_LONG).show();
        }
    }
    public void launchUpdateWordActivity( Word word) {
        Intent intent = new Intent(this, NewWordActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_WORD, word.getWeight());
        intent.putExtra(EXTRA_DATA_UPDATE_WORD1,word.getPressure());
        intent.putExtra(EXTRA_DATA_UPDATE_WORD2,word.getPressuredown());
        intent.putExtra(EXTRA_DATA_UPDATE_WORD3,word.getBloSugar());
        intent.putExtra(EXTRA_DATA_UPDATE_WORD4,word.getTime());
        intent.putExtra(EXTRA_DATA_UPDATE_WORD5,word.getTimer());
        intent.putExtra(NewWordActivity.EXTRA_ID, word.getId());
        startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }
}