package com.example.osirisg.jsonui;

import android.app.Application;

import com.example.osirisg.jsonui.di.DaggerSettingsComponent;
import com.example.osirisg.jsonui.di.SettingsComponent;
import com.example.osirisg.jsonui.di.SettingsModule;

public class App extends Application {

    private SettingsComponent settingsComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        settingsComponent = DaggerSettingsComponent.builder()
                .settingsModule(new SettingsModule())
                .build();
    }

    public SettingsComponent getSettingsComponent() {
        return settingsComponent;
    }
}
