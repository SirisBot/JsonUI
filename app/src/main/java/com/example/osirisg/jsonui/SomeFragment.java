package com.example.osirisg.jsonui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.osirisg.jsonui.di.DaggerSettingsComponent;
import com.example.osirisg.jsonui.di.SettingsModule;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


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
        settingsService.subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Boolean b = (Boolean) o;
                Toast.makeText(getContext(), b.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

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
