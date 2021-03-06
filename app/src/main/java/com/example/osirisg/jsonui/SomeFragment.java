package com.example.osirisg.jsonui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.osirisg.jsonui.common.SettingsService;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class SomeFragment extends Fragment {

    @Inject
    SettingsService settingsService;

    public SomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setupDaggerComponent();
        settingsService.subscribeToLivePredictiveCardSetting(new Subscriber<Boolean>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Boolean b) {
                Toast.makeText(getContext(), "Switch toggled: " + b.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
        settingsService.subscribeToMapDataPathSetting(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
        settingsService.subscribeScanDataPathSetting(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
        settingsService.subscribeGPSConfigSetting(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Integer integer) {
                Toast.makeText(getContext(), integer.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
            }
        });

        return inflater.inflate(R.layout.fragment_some, container, false);
    }

    private void setupDaggerComponent() {
        ((App) getActivity().getApplication()).getSettingsComponent().inject(this);
    }
}
