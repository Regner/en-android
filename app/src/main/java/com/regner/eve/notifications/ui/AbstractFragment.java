package com.regner.eve.notifications.ui;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regner.eve.notifications.Application;
import com.regner.eve.notifications.ApplicationComponent;
import com.regner.eve.notifications.util.Log;

import butterknife.ButterKnife;

public abstract class AbstractFragment extends Fragment implements TitleView {

    protected abstract void inject(final ApplicationComponent component);

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ApplicationComponent appComponent =
                ((Application)getActivity().getApplication()).getAppComponent();
        inject(appComponent);

        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void setLoading(boolean show) {
        if (getActivity() instanceof TitleView) {
            ((TitleView)getActivity()).setLoading(show);
        }
    }

    @Override
    public void setTitle(String title) {
        if (getActivity() instanceof TitleView) {
            ((TitleView)getActivity()).setTitle(title);
        }
    }

    @Override
    public void setDescription(String title) {
        if (getActivity() instanceof TitleView) {
            ((TitleView)getActivity()).setDescription(title);
        }
    }

    protected boolean onBackPressed() {
        return false;
    }

    protected final String r(final int rID) {
        try {
            return getResources().getString(rID);
        }
        catch (Resources.NotFoundException e) {
            Log.e(e.getLocalizedMessage());
            return "";
        }
    }

    protected final String r(final int rID, final Object... format) {
        try {
            return getResources().getString(rID, format);
        }
        catch (Resources.NotFoundException e) {
            Log.e(e.getLocalizedMessage());
            return "";
        }
    }
}
