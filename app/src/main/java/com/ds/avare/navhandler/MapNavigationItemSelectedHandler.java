package com.ds.avare.navhandler;

import android.support.v4.app.Fragment;

import com.ds.avare.fragment.LocationFragment;
import com.ds.avare.fragment.ThreeDFragment;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by arabbani on 7/9/16.
 */
public class MapNavigationItemSelectedHandler extends NavigationItemSelectedHandler {

    @Override
    protected Set<String> getTagsToRemove() {
        Set<String> fragmentTags = new HashSet<>();
        fragmentTags.addAll(super.getTagsToRemove());
        fragmentTags.add(ThreeDFragment.TAG);
        return fragmentTags;
    }

    @Override
    protected String getFragmentTag() {
        return LocationFragment.TAG;
    }

    @Override
    protected Fragment getNewFragment() {
        return new LocationFragment();
    }

}
