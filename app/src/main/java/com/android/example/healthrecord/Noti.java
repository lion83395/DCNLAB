package com.android.example.healthrecord;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
/**
 * Entity class that represents a word in the database
 */

@Entity(tableName = "noti_table")
public class Noti {

    @PrimaryKey(autoGenerate = true)
    private int id;
    // @NonNull
    // @ColumnInfo(name = "word")
    private Integer mHour;
    private Integer mMinute;
    private Boolean mWeight;
    private Boolean mPressure;
    private Boolean mBloSugar;


    public void setId(Integer id){
        this.id = id;
    }

    public Noti(Integer mHour, Integer mMinute, Boolean mWeight, Boolean mPressure, Boolean mBloSugar) {
        this.mHour = mHour;
        this.mMinute = mMinute;
        this.mWeight = mWeight;
        this.mPressure = mPressure;
        this.mBloSugar = mBloSugar;

    }

    public Boolean getWeight(){return this.mWeight;}

    public Boolean getPressure(){return this.mPressure;}

    public Boolean getBloSugar(){return this.mBloSugar;}

    public Integer getHour(){return this.mHour;}

    public Integer getMinute(){return this.mMinute;}

    public Integer getId(){return id;}
}