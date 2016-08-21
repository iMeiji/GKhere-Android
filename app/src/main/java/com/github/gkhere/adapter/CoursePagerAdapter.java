package com.github.gkhere.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.gkhere.ui.CourseSingleFragment;

/**
 * Created by Meiji on 2016/8/17.
 */
public class CoursePagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[]{"周一", "周二", "周三", "周四", "周五"};
    private Context mContext;

    public CoursePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return CourseSingleFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitles[position];
    }
}
