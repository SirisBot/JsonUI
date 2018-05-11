package com.example.osirisg.jsonui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.avocarrot.json2view.DynamicView;
import com.example.osirisg.jsonui.common.SettingsService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    SettingsService settingsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDaggerComponent();

        ViewGroup dynamicViewContainer = findViewById(R.id.json_container_view);

        @Nullable
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(readJSONFromAsset());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonObject != null) {
            View dynamicView = DynamicView.createView(this, jsonObject, dynamicViewContainer);
            dynamicViewContainer.addView(dynamicView);
            ViewGroup container = (ViewGroup) dynamicViewContainer.getChildAt(0);
            if (container != null) {
                for (int i = 0; i < container.getChildCount(); i++) {
                    setupChildView((ViewGroup) container.getChildAt(i));
                }
            }
        }

        getSupportFragmentManager().beginTransaction().add(new SomeFragment(), null).commit();
    }

    private void setupDaggerComponent() {
        ((App) getApplication()).getSettingsComponent().inject(this);
    }

    public void setupChildView(ViewGroup viewGroup) {
        View v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            v = viewGroup.getChildAt(i);
            if (v instanceof Switch) {
                ((Switch) v).setOnCheckedChangeListener((buttonView, isChecked) -> settingsService.observeLivePredictiveCardSetting(isChecked));
            } else if (v instanceof EditText) {
                ((EditText) v).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        settingsService.observeMapDataPathSetting(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            } else if (v instanceof Button) {
                String tag = (String) v.getTag();
                v.setOnClickListener(v1 -> {
                    Scan scan = (String s) -> "Path found was: " + s;
                    settingsService.observeScanDataPathSetting(scan.scan("/somepath/somefile/na"));
                });
            } else if (v instanceof RadioGroup) {
                ((RadioGroup) v).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        settingsService.observeGPSConfigSetting(checkedId);
                    }
                });
            }
        }
    }

    interface Scan {
        String scan(String s);
    }

    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("settings.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
