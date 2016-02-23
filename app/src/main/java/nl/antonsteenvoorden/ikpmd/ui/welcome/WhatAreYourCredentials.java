package nl.antonsteenvoorden.ikpmd.ui.welcome;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.activity.SplashScreen;
import nl.antonsteenvoorden.ikpmd.model.Module;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WhatAreYourCredentials#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhatAreYourCredentials extends Fragment implements SliderFragment.Saveable {
  @Bind(R.id.usernameField) EditText usernameField;
  @Bind(R.id.passwordField) EditText passwordField;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WhatAreYourCredentials.
     */
    public static WhatAreYourCredentials newInstance() {
        WhatAreYourCredentials fragment = new WhatAreYourCredentials();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_welcome_what_are_your_credentials, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void save() {
        SharedPreferences.Editor settings = getActivity().getSharedPreferences(SplashScreen.PREFS_NAME, 0).edit();
        settings.putString("username", "s"+usernameField.getText().toString());
        settings.putString("password", passwordField.getText().toString());
        settings.apply();
    }
}
