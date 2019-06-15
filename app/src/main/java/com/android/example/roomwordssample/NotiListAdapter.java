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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.roomwordssample.R;
import com.android.example.roomwordssample.Word;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This is the adapter for the RecyclerView that displays
 * a list of words.
 */

public class NotiListAdapter extends RecyclerView.Adapter<NotiListAdapter.NotiViewHolder> {

    private final LayoutInflater mInflater;
    private List<Noti> mNoti=new ArrayList<>(); // Cached copy of words
    private static ClickListener clickListener;

    NotiListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public NotiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new NotiViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotiViewHolder holder, int position) {
        //Calendar mCal=Calendar.getInstance();
        //CharSequence s= DateFormat.format("yyyy-MM-dd kk:mm:ss",mCal.getTime());
        Noti current = mNoti.get(position);
        holder.weightItemView.setText(current.getHour() +":"+ current.getMinute());
        //   holder.pressureItemView.setText(current.getPressure());
        //  holder.bloodpressureItemView.setText(current.getBloSugar());
         /*else {
            // Covers the case of data not being ready yet.
            // holder.wordItemView.setText("No Word");
            holder.wordItemView.setText(R.string.no_word);
        }*/
    }

    /**
     *     Associate a list of words with this adapter
     */

    void setNoti(List<Noti> noti) {
        mNoti = noti;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mNoti != null)
            return mNoti.size();
        else return 0;
    }

    /**
     * Get the word at a given position.
     * This method is useful for identifying which word
     * was clicked or swiped in methods that handle user events.
     *
     * @param position
     * @return The word at the given position
     */
    public Noti getNotiAtPosition(int position) {
        return mNoti.get(position);
    }

    class NotiViewHolder extends RecyclerView.ViewHolder {
        private  TextView weightItemView;
        //  private  TextView pressureItemView;
        // private TextView bloodpressureItemView;

        private NotiViewHolder(View itemView) {
            super(itemView);
            weightItemView = itemView.findViewById(R.id.textView);
            //  pressureItemView = itemView.findViewById(R.id.textView1);
            //  bloodpressureItemView= itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        NotiListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
