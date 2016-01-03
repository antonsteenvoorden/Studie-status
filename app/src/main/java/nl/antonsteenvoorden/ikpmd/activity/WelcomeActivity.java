package nl.antonsteenvoorden.ikpmd.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.ui.welcome.WelcomeSliderFragment;
import nl.antonsteenvoorden.ikpmd.ui.welcome.WelcomeStep2Fragment;
import nl.antonsteenvoorden.ikpmd.ui.welcome.WelcomeStep3Fragment;
import nl.antonsteenvoorden.ikpmd.ui.welcome.WelcomeStepAdapter;

public class WelcomeActivity extends AppCompatActivity implements
        WelcomeSliderFragment.OnFragmentInteractionListener {

    private WelcomeStepAdapter welcomeStepAdapter;
    private WelcomeSliderFragment fragment;

    @Bind(R.id.container) ViewPager mViewPager;

    WelcomeStep2Fragment step2Fragment;
    WelcomeStep3Fragment step3Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        fragment = (WelcomeSliderFragment) getSupportFragmentManager().findFragmentById(
                R.id.slider_container);

        welcomeStepAdapter = new WelcomeStepAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(welcomeStepAdapter);

        step2Fragment = (WelcomeStep2Fragment) getSupportFragmentManager().findFragmentByTag(
                welcomeStepAdapter.getFragmentTag(R.id.container, 1));
        step3Fragment = (WelcomeStep3Fragment) getSupportFragmentManager().findFragmentByTag(
                welcomeStepAdapter.getFragmentTag(R.id.container, 2));
    }

    @Override
    public int nextStep() {
        if (mViewPager.getCurrentItem() == welcomeStepAdapter.getCount()-1) {
            // TODO: save data
            step2Fragment.saveData();
            step3Fragment.saveData();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

            SharedPreferences settings = getSharedPreferences(SplashScreen.PREFS_NAME, 0);
            settings.edit().putBoolean("first_run", false).commit();
        } else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
        }
        return mViewPager.getCurrentItem();
    }

}
