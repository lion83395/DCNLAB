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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    public static final String EXTRA_NOTI_UPDATE_HOUR = "10";
    public static final String EXTRA_NOTI_UPDATE_MINUTE = "11";
    public static final String EXTRA_NOTI_UPDATE_WEIGHT = "12";
    public static final String EXTRA_NOTI_UPDATE_BLOODP = "13";
    public static final String EXTRA_NOTI_UPDATE_BLOODS = "14";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

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

        //Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationActivity.this, NewNotiActivity.class);
                startActivityForResult(intent, NEW_NOTI_ACTIVITY_REQUEST_CODE);
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
                        Noti myNoti = adapter.getNotiAtPosition(position);
                        Toast.makeText(NotificationActivity.this,
                                getString(R.string.delete_word_preamble) + String.format(" %02d:%02d", myNoti.getHour(),
                                        myNoti.getMinute()), Toast.LENGTH_LONG).show();

                        // Delete the word
                        mNotiViewModel.deleteNoti(myNoti);
                    }
                });
        // Attach the item touch helper to the recycler view
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NotiListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Noti noti = adapter.getNotiAtPosition(position);
                launchUpdateNotiActivity(noti);
            }
        });
    }


/*    @Override
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
        if (id == R.id.clear_noti) {
            // Add a toast just for confirmation
            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();

            // Delete the existing data
            mNotiViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


*/



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
        } else if (requestCode == UPDATE_NOTI_ACTIVITY_REQUEST_CODE
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

            Toast.makeText(this,"提醒已設定" + String.format("%02d:%02d", noti.getHour(),
                    noti.getMinute()), Toast.LENGTH_LONG).show();


        }else {
            Toast.makeText(
                    this, "勾選不可為全空", Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdateNotiActivity(Noti noti) {
        Intent intent = new Intent(this, NewNotiActivity.class);
        intent.putExtra(EXTRA_NOTI_UPDATE_HOUR, noti.getHour());
        intent.putExtra(EXTRA_NOTI_UPDATE_MINUTE, noti.getMinute());
        intent.putExtra(EXTRA_NOTI_UPDATE_WEIGHT, noti.getWeight());
        intent.putExtra(EXTRA_NOTI_UPDATE_BLOODP, noti.getPressure());
        intent.putExtra(EXTRA_NOTI_UPDATE_BLOODS, noti.getBloSugar());

        Log.i("update", noti.getHour().toString());

        intent.putExtra(NewNotiActivity.EXTRA_ID, noti.getId());
        startActivityForResult(intent, UPDATE_NOTI_ACTIVITY_REQUEST_CODE);
    }

}
