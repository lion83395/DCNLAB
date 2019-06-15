package com.android.example.roomwordssample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class NotiViewModel extends AndroidViewModel {
    private NotiRepository mRepository;
    private LiveData<List<Noti>> mAllNoti;

    public NotiViewModel (Application application) {
        super(application);
        mRepository = new NotiRepository(application);
        mAllNoti = mRepository.getAllNoti();
    }

    LiveData<List<Noti>> getmAllNoti() { return mAllNoti;}

    public void insert(Noti noti){ mRepository.insert(noti);}

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteNoti(Noti noti) {
        mRepository.deleteNoti(noti);
    }

    public void update(Noti noti){mRepository.update(noti);}
}
