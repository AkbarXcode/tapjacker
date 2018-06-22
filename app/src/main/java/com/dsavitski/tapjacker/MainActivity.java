package com.dsavitski.tapjacker;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import petrov.kristiyan.colorpicker.ColorPicker;

public class MainActivity extends AppCompatActivity {
    private String chosenAppPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadPackages();
    }

    public void runTapJacker(View view) {

    }

    /**
     * Loads available application packages to the dropdown menu
     */
    private void loadPackages() {
        List<ApplicationInfo> packages = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        List<String> filtered = filterPackages(packages);
        String[] packagesArr = new String[filtered.size()];
        packagesArr = filtered.toArray(packagesArr);

        Spinner packagesDropDown = findViewById(R.id.packagesDropDown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, packagesArr);
        packagesDropDown.setAdapter(adapter);
    }

    /**
     * Filter packages
     */
    private List<String> filterPackages(final List<ApplicationInfo> packages) {
        List<String> filtered = new ArrayList<>();

        for (ApplicationInfo packageInfo : packages) {
            final String packageName = packageInfo.packageName;
            if (!packageName.contains("com.android")) {
                filtered.add(packageName);
            }
        }
        return filtered;
    }

    public void pickColor(View view) {
        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(final int position, final int color) {
                Button buttonColorPicker = findViewById(R.id.buttonColorPicker);
                buttonColorPicker.setBackgroundColor(color);
            }

            @Override
            public void onCancel() {/*NOP*/}
        });
    }
}
