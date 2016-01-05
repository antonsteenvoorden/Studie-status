package nl.antonsteenvoorden.ikpmd.ui.welcome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daan on 29-Dec-15.
 */
public class WelcomeStepAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> stepFragments;

    public WelcomeStepAdapter(FragmentManager fm) {
        super(fm);
        stepFragments = new ArrayList<>();
        stepFragments.add(WelcomeMessage.newInstance());
        stepFragments.add(WhoAreYou.newInstance());
        stepFragments.add(WhatAreYourGrades.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return stepFragments.get(position);
    }

    @Override
    public int getCount() {
        return stepFragments.size();
    }

}
