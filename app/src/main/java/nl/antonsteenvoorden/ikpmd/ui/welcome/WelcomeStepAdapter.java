package nl.antonsteenvoorden.ikpmd.ui.welcome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daan on 29-Dec-15.
 */
public class WelcomeStepAdapter extends FragmentPagerAdapter {

    private List<Fragment> stepFragments;
    private WelcomeSliderFragment.OnFragmentInteractionListener welcomeSliderFragment;

    public WelcomeStepAdapter(FragmentManager fm,
                              WelcomeSliderFragment.OnFragmentInteractionListener
                                      welcomeSliderFragment) {
        super(fm);
        this.welcomeSliderFragment = welcomeSliderFragment;
        stepFragments = new ArrayList<>();
        stepFragments.add(WelcomeStep1Fragment.newInstance());
        stepFragments.add(WelcomeStep2Fragment.newInstance());
        stepFragments.add(WelcomeStep3Fragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        // welcomeSliderFragment.updateSliderIcon(position);
        return stepFragments.get(position);
    }

    @Override
    public int getCount() {
        return stepFragments.size();
    }

    public String getFragmentTag(int viewPagerId, int fragmentPosition)
    {
        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
    }

}
