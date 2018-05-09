package com.example.osirisg.jsonui.di;

import com.example.osirisg.jsonui.SettingsService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {

    @Provides
    @Singleton
    SettingsService providesSettingsService() {
        return new SettingsService();
    }
}
