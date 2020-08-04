package com.example.geometryapp.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.geometryapp.R;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class TestFragment extends Fragment {

    //THIS IS THE FRAGMENT FOR TESTING

    View view;
    private Switch SwitchDayNightMode;
    private Switch SwitchNumerals;
    private Switch SwitchColor;
    private RadioGroup RGCategory;
    private RadioGroup RGLevel;
    private Button BTNOpenLevel;

    public Boolean wrongAnswerSelected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_test, container, false);
        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("Modes", MODE_PRIVATE).edit();
        final SharedPreferences shared = getActivity().getSharedPreferences("Modes", MODE_PRIVATE);
        SwitchDayNightMode = view.findViewById(R.id.switchDayNight);
        SwitchNumerals = view.findViewById(R.id.switchNumerals);
        SwitchColor = view.findViewById(R.id.switchSelectColor);
        RGCategory = view.findViewById(R.id.RGActivity);
        RGLevel = view.findViewById(R.id.RGLevel);
        BTNOpenLevel = view.findViewById(R.id.BTNOpenLevel);
        SwitchDayNightMode.setChecked(shared.getBoolean("nightModeOn", false));
        SwitchNumerals.setChecked(shared.getBoolean("englishNumerals", false));
        SwitchColor.setChecked(shared.getBoolean("purpleColorOn", false));
        BTNOpenLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RGLevel.indexOfChild(RGLevel.findViewById(RGLevel.getCheckedRadioButtonId())) == -1 ||
                        RGCategory.indexOfChild(RGCategory.findViewById(RGCategory.getCheckedRadioButtonId())) == -1) {
                    Toast.makeText(getContext(), "Select a category and a level", Toast.LENGTH_SHORT).show();
                } else {
                    boolean nightModeOn = SwitchDayNightMode.isChecked();
                    boolean englishNumerals = SwitchNumerals.isChecked();
                    boolean purpleColorOn = SwitchColor.isChecked();
                    int categoryIndex = RGCategory.indexOfChild(RGCategory.findViewById(RGCategory.getCheckedRadioButtonId())) + 1;
                    int levelIndex = RGLevel.indexOfChild(RGLevel.findViewById(RGLevel.getCheckedRadioButtonId())) + 1;
                    editor.putBoolean("nightModeOn", nightModeOn);
                    editor.putBoolean("englishNumerals", englishNumerals);
                    editor.putBoolean("purpleColorOn", purpleColorOn);
                    editor.commit();
                    Bundle bundle = new Bundle();
                    bundle.putInt("categoryIndex", categoryIndex);
                    bundle.putInt("levelIndex", levelIndex);
                    bundle.putBoolean("englishNumerals", englishNumerals);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    LevelFragment fragment = new LevelFragment();
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.LLFragmentHolder, fragment);
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                }
            }
        });
        SwitchDayNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Todo check for better solution. Activity is recreated so that night mode changes. This migth be the only solution
                boolean nightModeOn = SwitchDayNightMode.isChecked();
                boolean englishNumerals = SwitchNumerals.isChecked();
                boolean purpleColorOn = SwitchColor.isChecked();
                editor.putBoolean("nightModeOn", nightModeOn);
                editor.putBoolean("englishNumerals", englishNumerals);
                editor.putBoolean("purpleColorOn", purpleColorOn);
                editor.apply();
                if (nightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                getActivity().recreate();
            }
        });
        SwitchNumerals.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean nightModeOn = SwitchDayNightMode.isChecked();
                boolean englishNumerals = SwitchNumerals.isChecked();
                boolean purpleColorOn = SwitchColor.isChecked();
                editor.putBoolean("nightModeOn", nightModeOn);
                editor.putBoolean("englishNumerals", englishNumerals);
                editor.putBoolean("purpleColorOn", purpleColorOn);
                editor.apply();
            }
        });
        SwitchColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean nightModeOn = SwitchDayNightMode.isChecked();
                boolean englishNumerals = SwitchNumerals.isChecked();
                boolean purpleColorOn = SwitchColor.isChecked();
                editor.putBoolean("nightModeOn", nightModeOn);
                editor.putBoolean("englishNumerals", englishNumerals);
                editor.putBoolean("purpleColorOn", purpleColorOn);
                editor.apply();
            }
        });
        RGCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    createRadioButtons(RGCategory.indexOfChild(RGCategory.findViewById(checkedId)) + 1);
                }
            }
        });
        createRadioButtons(1);
        return view;
    }

    // ALL CATEGORIES
    // 1 - Point from coordinates: 6 Levels
    // 2 - Coordinates from points: 6 Levels
    // 3 - line symmetry - point - find point
    // 4 - line symmetry - point - find coordinates
    // 5 - point symmetry - point - find point
    // 6 - point symmetry - point - find coordinates
    // 7 - point symmetry- figure - find coordinates
    // 8 - Find the perimeter of a figure
    // 9 - Draw figure from perimeter
    // 10 - Find the area of a figure
    // 11 - Draw figure from area

    private void createRadioButtons(int categoryIndex) {
        RGLevel.removeAllViews();
        if (categoryIndex == 1) {
            //Amount of levels in this category
            addRadioButtons(7);
        } else if (categoryIndex == 2) {
            addRadioButtons(6);
        } else if (categoryIndex == 3) {
            addRadioButtons(2);
        } else if (categoryIndex == 4) {
            addRadioButtons(8);
        } else if (categoryIndex == 5) {
            addRadioButtons(2);
        } else if (categoryIndex == 6) {
            addRadioButtons(5);
        } else if (categoryIndex == 7) {
            addRadioButtons(1);
        } else if (categoryIndex == 8) {
            addRadioButtons(9);
        } else if (categoryIndex == 9) {
            addRadioButtons(9);
        } else if (categoryIndex == 10) {
            addRadioButtons(11);
        } else if (categoryIndex == 11) {
            addRadioButtons(11);
        }
    }

    private void addRadioButtons(int amount){
        //Adds radio buttons to radio group according to amount of levels in each category
        for (int i = 0; i < amount; i++) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText("" + (i + 1));
            radioButton.setSelected(true);
            RGLevel.addView(radioButton);
        }
    }

    public boolean getWrongAnswer(){
        return wrongAnswerSelected;
    }
}
