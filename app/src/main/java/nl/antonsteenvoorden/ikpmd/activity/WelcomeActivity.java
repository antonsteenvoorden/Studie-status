package nl.antonsteenvoorden.ikpmd.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.ui.welcome.SliderFragment;
import nl.antonsteenvoorden.ikpmd.ui.welcome.WelcomeStepAdapter;

public class WelcomeActivity extends AppCompatActivity implements
        SliderFragment.Slideable, SliderFragment.Saveable {

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
    public int nextStep() {
        if (mViewPager.getCurrentItem() == welcomeStepAdapter.getCount()-1) {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
            finish();
        } else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
        }
        return mViewPager.getCurrentItem();
    }

    @Override
    public void save() {
        SliderFragment.Saveable fragment;
        try {
            fragment = (SliderFragment.Saveable) welcomeStepAdapter.getItem(mViewPager.
                    getCurrentItem());
            fragment.save();
            SharedPreferences settings = getSharedPreferences(SplashScreen.PREFS_NAME, 0);
            settings.edit().putBoolean("first_run", false).commit();
        } catch (ClassCastException ce) {
            Log.d("SAVE", "Save gone wrong! ");
            ce.printStackTrace();
        }
    }
}
