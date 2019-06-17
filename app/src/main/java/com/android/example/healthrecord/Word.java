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

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Entity class that represents a word in the database
 */

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mWeight;
    private String mPressure;
    private String mPressuredown;
    private String mBloSugar;
    private String mTime;
    private String mTimer;

    public void setId(int id){
        this.id=id;
    }

    public Word( String weight ,String pressure,String mPressuredown,String mBloSugar,String mTime,String mTimer) {
        this.mWeight = weight;
        this.mPressure=pressure;
        this.mPressuredown=mPressuredown;
        this.mBloSugar=mBloSugar;
        this.mTime=mTime;
        this.mTimer=mTimer;
    }

    public String getWeight(){return this.mWeight;}

    public String getPressure(){return  this.mPressure;}

    public String getPressuredown(){return this.mPressuredown;}

    public String getBloSugar(){return  this.mBloSugar;}

    public String getTime(){return  this.mTime;}

    public String getTimer(){return this.mTimer;}

    public int getId(){return id;}
}

