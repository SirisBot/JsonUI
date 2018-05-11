package com.example.osirisg.jsonui;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.osirisg.jsonui.common.SettingsService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertTrue;

public class SettingsTests {

    private SettingsService settingsService;

    @Before
    public void setUp() {
        settingsService = new SettingsService();
    }

    @Test
    public void livePredictiveCardEnabledSettingTest() {

        Switch uiSwitch = Mockito.mock(Switch.class);
        final boolean initialValue = uiSwitch.isChecked();

        final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();

        settingsService.subscribeToLivePredictiveCardSetting(testSubscriber);

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingsService.observeLivePredictiveCardSetting(isChecked);
                boolean newValue = testSubscriber.values().get(0);

                if (initialValue) {
                    assertTrue(!newValue);
                } else {
                    assertTrue(newValue);
                }
            }
        };

        checkedChangeListener.onCheckedChanged(uiSwitch, !uiSwitch.isChecked());
    }

    @Test
    public void scanDataButtonClickedTest() {

        final String clickString = "ClickString";

        final TestSubscriber<String> testSubscriber = new TestSubscriber<>();

        settingsService.subscribeScanDataPathSetting(testSubscriber);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsService.observeScanDataPathSetting(clickString);
                String test = testSubscriber.values().get(0);
                assertTrue(clickString.equals(test));
            }
        };

        onClickListener.onClick(Mockito.mock(Button.class));
    }

    @Test
    public void mapDataPathChangedTest() {

        final String mapDataPath = "/asdas/asdasd";

        final TestSubscriber<String> testSubscriber = new TestSubscriber<>();

        settingsService.subscribeToMapDataPathSetting(testSubscriber);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                settingsService.observeMapDataPathSetting(s.toString());
                String test = testSubscriber.values().get(0);
                assertTrue(mapDataPath.equals(test));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        textWatcher.onTextChanged(mapDataPath, 0, 0, 0);
    }

    @Test
    public void gpsConfigChangedTest() {

        final int gpsIndex = 2;

        final TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        settingsService.subscribeGPSConfigSetting(testSubscriber);

        RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                settingsService.observeGPSConfigSetting(checkedId);
                int change = testSubscriber.values().get(0);
                assertTrue(gpsIndex == change);
            }
        };

        onCheckedChangeListener.onCheckedChanged(Mockito.mock(RadioGroup.class), gpsIndex);
    }
}
