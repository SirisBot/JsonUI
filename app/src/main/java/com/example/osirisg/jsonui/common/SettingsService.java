package com.example.osirisg.jsonui.common;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SettingsService {

    private Flowable<Boolean> livePredictiveCardSettingFlowable = new Flowable<Boolean>() {
        @Override
        protected void subscribeActual(Subscriber<? super Boolean> s) {
            livePredictiveCardSettingSubscribers.add(s);
        }
    };

    private Flowable<String> mapDataPathSettingFlowable = new Flowable<String>() {
        @Override
        protected void subscribeActual(Subscriber<? super String> s) {
            mapDataPathSettingSubscribers.add(s);
        }
    };

    private Flowable<String> scanDataPathSettingFlowable = new Flowable<String>() {
        @Override
        protected void subscribeActual(Subscriber<? super String> s) {
            scanDataPathSettingSubscribers.add(s);
        }
    };

    private Flowable<Integer> gpsConfigSettingFlowable = new Flowable<Integer>() {
        @Override
        protected void subscribeActual(Subscriber<? super Integer> s) {
            gpsConfigSettingSubscribers.add(s);
        }
    };

    private List<Subscriber<? super Boolean>> livePredictiveCardSettingSubscribers = new ArrayList<>();
    private List<Subscriber<? super String>> mapDataPathSettingSubscribers = new ArrayList<>();
    private List<Subscriber<? super String>> scanDataPathSettingSubscribers = new ArrayList<>();
    private List<Subscriber<? super Integer>> gpsConfigSettingSubscribers = new ArrayList<>();

    public SettingsService() {
    }

    public void observeLivePredictiveCardSetting(Boolean toggled) {
        for (Subscriber<? super Boolean> s : livePredictiveCardSettingSubscribers) {
            s.onNext(toggled);
        }
    }

    public void subscribeToLivePredictiveCardSetting(Subscriber<Boolean> subscriber) {
        livePredictiveCardSettingFlowable.subscribe(subscriber);
    }

    public void observeMapDataPathSetting(String mapDataPath) {
        for (Subscriber<? super String> s : mapDataPathSettingSubscribers) {
            s.onNext(mapDataPath);
        }
    }

    public void subscribeToMapDataPathSetting(Subscriber<String> subscriber) {
        mapDataPathSettingFlowable.subscribe(subscriber);
    }

    public void observeScanDataPathSetting(String scanDataPath) {
        for (Subscriber<? super String> s : scanDataPathSettingSubscribers) {
            s.onNext(scanDataPath);
        }
    }

    public void subscribeScanDataPathSetting(Subscriber<String> subscriber) {
        scanDataPathSettingFlowable.subscribe(subscriber);
    }

    public void observeGPSConfigSetting(Integer gpsIndex) {
        String thread = Thread.currentThread().getName();
        for (Subscriber<? super Integer> s : gpsConfigSettingSubscribers) {
            s.onNext(gpsIndex);
        }
    }

    public void subscribeGPSConfigSetting(Subscriber<Integer> subscriber) {
        gpsConfigSettingFlowable.subscribe(subscriber);
    }
}
