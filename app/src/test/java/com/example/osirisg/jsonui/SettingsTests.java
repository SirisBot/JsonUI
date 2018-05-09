package com.example.osirisg.jsonui;

import android.widget.CompoundButton;
import android.widget.Switch;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertTrue;

public class SettingsTests {

    private Switch uiSwitch;
    private SettingsService settingsService;

    @Before
    public void setUp() {
        uiSwitch = Mockito.mock(Switch.class);
        settingsService = new SettingsService();
    }

    @Test
    public void isSwitchToggled() {

        final boolean initialValue = uiSwitch.isChecked();

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestSubscriber testSubscriber = settingsService.observeSetting(isChecked).test();
                boolean newValue = (boolean) testSubscriber.values().get(0);

                if (initialValue) {
                    assertTrue(!newValue);
                } else {
                    assertTrue(newValue);
                }
            }
        };

        checkedChangeListener.onCheckedChanged(uiSwitch, !uiSwitch.isChecked());
    }
}
