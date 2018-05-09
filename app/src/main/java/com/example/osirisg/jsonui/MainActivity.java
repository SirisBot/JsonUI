package com.example.osirisg.jsonui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.avocarrot.json2view.DynamicView;

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
            if (dynamicView != null) {
                dynamicViewContainer.addView(dynamicView);
                ViewGroup views = findChildViews(dynamicViewContainer); //recursively find child views
                for (int i = 0; i < views.getChildCount(); i++) {
                    View v = views.getChildAt(i);
                    if (v instanceof Switch) {
                        ((Switch) v).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                settingsService.observeSwitchToggledSetting(isChecked);
                            }
                        });
                    }
                }
                getSupportFragmentManager().beginTransaction().add(new SomeFragment(), null).commit();
            }
        }
    }

    private void setupDaggerComponent() {
        ((App) getApplication()).getSettingsComponent().inject(this);
    }

    public ViewGroup findChildViews(ViewGroup viewGroup) {
        View v = null;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            v = viewGroup.getChildAt(i);
            if (!(v instanceof LinearLayout)) {
                return viewGroup;
            }
        }
        return findChildViews((ViewGroup) v);
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
