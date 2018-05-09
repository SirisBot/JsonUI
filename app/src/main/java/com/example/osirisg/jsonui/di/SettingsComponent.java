package com.example.osirisg.jsonui.di;

import com.example.osirisg.jsonui.MainActivity;
import com.example.osirisg.jsonui.SomeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = SettingsModule.class)
public interface SettingsComponent {

    void inject(MainActivity mainActivity);
    void inject(SomeFragment someFragment);

}
