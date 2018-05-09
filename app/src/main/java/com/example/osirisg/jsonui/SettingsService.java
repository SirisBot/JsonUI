package com.example.osirisg.jsonui;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;

public class SettingsService {

    private List<Observer> observerList = new ArrayList<>();

    private Subject subject = new Subject() {
        @Override
        public boolean hasObservers() {
            return false;
        }

        @Override
        public boolean hasThrowable() {
            return false;
        }

        @Override
        public boolean hasComplete() {
            return false;
        }

        @Override
        public Throwable getThrowable() {
            return null;
        }

        @Override
        protected void subscribeActual(Observer observer) {
            observerList.add(observer);
        }

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Object o) {
            for (Observer observer : observerList) {
                observer.onNext(o);
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    public SettingsService() {
    }

    void observeSetting(Boolean object) {
        subject.onNext(object);
    }

    void subscribe(Observer<Object> observer) {
        subject.subscribe(observer);
    }
}
