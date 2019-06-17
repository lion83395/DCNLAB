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

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;


import java.util.List;

/**
 * This class holds the implementation code for the
 * methods that interact with the database.
 * Using a repository allows us to group the implementation
 * methods together, and allows the WordViewModel to be
 * a clean interface between the rest of the app and the database.
 *
 * For insert, update and delete, and longer-running queries,
 * you must run the database interaction methods in the background.
 * Typically, all you need to do to implement a database method
 * is to call it on the data access object (DAO),
 * in the background if applicable.
 */

public class NotiRepository {

    private NotiDao mNotiDao;
    private LiveData<List<Noti>> mAllNoti;

    NotiRepository(Application application) {
        NotiRoomDatabase db = NotiRoomDatabase.getDatabase(application);
        mNotiDao = db.notiDao();
        mAllNoti = mNotiDao.getAllNoti();
    }

    LiveData<List<Noti>> getAllNoti() {
        return mAllNoti;
    }

    public void insert(Noti noti) {
        new insertAsyncTask(mNotiDao).execute(noti);
    }

    public void deleteAll() {
        new deleteAllNotiAsyncTask(mNotiDao).execute();
    }

    // Need to run off main thread
    public void deleteNoti(Noti noti) {
        new deleteNotiAsyncTask(mNotiDao).execute(noti);
    }

    public void update(Noti noti){new updateAsyncTask(mNotiDao).execute(noti);}

    // Static inner classes below here to run database interactions
    // in the background.

    /**
     * Insert a notification into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Noti, Void, Void> {

        private NotiDao mAsyncTaskDao;

        insertAsyncTask(NotiDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Noti... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<Noti, Void, Void> {

        private NotiDao mAsyncTaskDao;

        updateAsyncTask(NotiDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Noti... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    /**
     * Delete all notifications from the database (does not delete the table)
     */
    private static class deleteAllNotiAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotiDao mAsyncTaskDao;

        deleteAllNotiAsyncTask(NotiDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     *  Delete a single notification from the database.
     */
    private static class deleteNotiAsyncTask extends AsyncTask<Noti, Void, Void> {
        private NotiDao mAsyncTaskDao;

        deleteNotiAsyncTask(NotiDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Noti... params) {
            mAsyncTaskDao.deleteNoti(params[0]);
            return null;
        }
    }
}
