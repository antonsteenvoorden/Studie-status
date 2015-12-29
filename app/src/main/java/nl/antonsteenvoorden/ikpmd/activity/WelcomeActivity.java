package nl.antonsteenvoorden.ikpmd.activity;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.ui.welcome.WelcomeSliderFragment;
import nl.antonsteenvoorden.ikpmd.ui.welcome.WelcomeStepAdapter;

public class WelcomeActivity extends AppCompatActivity implements WelcomeSliderFragment.OnFragmentInteractionListener {

    private WelcomeStepAdapter welcomeStepAdapter;
    @Bind(R.id.container) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        welcomeStepAdapter = new WelcomeStepAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(welcomeStepAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void nextStep() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
    }


}
