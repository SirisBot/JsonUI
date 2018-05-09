package com.example.osirisg.jsonui;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class SettingsService {

    private Flowable<Boolean> switchToggleFlowable = new Flowable<Boolean>() {
        @Override
        protected void subscribeActual(Subscriber<? super Boolean> s) {
            switchToggleSubscribers.add(s);
        }
    };

    private List<Subscriber<? super Boolean>> switchToggleSubscribers = new ArrayList<>();

    public SettingsService() {
    }

    void observeSwitchToggledSetting(Boolean toggled) {
        for (Subscriber<? super Boolean> s : switchToggleSubscribers) {
            s.onNext(toggled);
        }
    }

    void subscribeToSwitchToggleSetting(Subscriber<Boolean> subscriber) {
        switchToggleFlowable.subscribe(subscriber);
    }
}
