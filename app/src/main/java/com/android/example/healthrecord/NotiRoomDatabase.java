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

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * WordRoomDatabase. Includes code to create the database.
 * After the app creates the database, all further interactions
 * with it happen through the WordViewModel.
 */

@Database(entities = {Noti.class}, version = 3, exportSchema = false)
public abstract class NotiRoomDatabase extends RoomDatabase {

    public abstract NotiDao notiDao();

    private static NotiRoomDatabase INSTANCE;

    public static NotiRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotiRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotiRoomDatabase.class, "noti_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    // This callback is called when the database has opened.
    // In this case, use PopulateDbAsync to populate the database
    // with the initial data set if the database has no entries.
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    // Populate the database with the initial data set
    // only if the database has no entries.
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final NotiDao mDao;

        // Initial data set


        PopulateDbAsync(NotiRoomDatabase db) {
            mDao = db.notiDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // If we have no words, then create the initial list of words
        /*    if (mDao.getAnyWord().length < 1) {
                for (int i = 0; i <= words.length - 1; i++) {
                    Word word = new Word(words[i],answers[i]);
                    mDao.insert(word);
                }
            }*/

            // mDao.insert(new Word("Question 1","Answer1"));
            // mDao.insert(new Word("Question 2","Answer2"));
            return null;
        }
    }
    public static void deleteInstance(){
        INSTANCE=null;
    }
}

