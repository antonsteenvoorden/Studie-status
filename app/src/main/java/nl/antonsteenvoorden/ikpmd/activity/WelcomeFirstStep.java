package nl.antonsteenvoorden.ikpmd.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.ui.WelcomeSliderFragment;

public class WelcomeFirstStep extends AppCompatActivity implements WelcomeSliderFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_first_step);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
