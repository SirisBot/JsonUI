package com.example.osirisg.jsonui;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

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

    private List<Subscriber<? super Boolean>> livePredictiveCardSettingSubscribers = new ArrayList<>();
    private List<Subscriber<? super String>> mapDataPathSettingSubscribers = new ArrayList<>();
    private List<Subscriber<? super String>> scanDataPathSettingSubscribers = new ArrayList<>();

    public SettingsService() {
    }

    void observeLivePredictiveCardSetting(Boolean toggled) {
        for (Subscriber<? super Boolean> s : livePredictiveCardSettingSubscribers) {
            s.onNext(toggled);
        }
    }

    void subscribeToLivePredictiveCardSetting(Subscriber<Boolean> subscriber) {
        livePredictiveCardSettingFlowable.subscribe(subscriber);
    }

    void observeMapDataPathSetting(String mapDataPath) {
        for (Subscriber<? super String> s : mapDataPathSettingSubscribers) {
            s.onNext(mapDataPath);
        }
    }

    void subscribeToMapDataPathSetting(Subscriber<String> subscriber) {
        mapDataPathSettingFlowable.subscribe(subscriber);
    }

    void observeScanDataPathSetting(String scanDataPath) {
        for (Subscriber<? super String> s : scanDataPathSettingSubscribers) {
            s.onNext(scanDataPath);
        }
    }

    void subscribeScanDataPathSetting(Subscriber<String> subscriber) {
        scanDataPathSettingFlowable.subscribe(subscriber);
    }
}
