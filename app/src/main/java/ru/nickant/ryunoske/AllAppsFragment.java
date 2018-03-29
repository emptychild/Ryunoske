package ru.nickant.ryunoske;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllAppsFragment extends Fragment {

    ArrayList appsList;
    RAdapter adapter;
    RecyclerView recyclerView;
    int numberOfColumns;
    boolean init = false;
    Context context = getActivity();
    PackageManager pm;


    public AllAppsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_apps, container, false);
        pm = getActivity().getPackageManager();
        appsList = new ArrayList<AppInfo>();

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);


        List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
        for(ResolveInfo ri:allApps) {
            AppInfo app = new AppInfo();
            app.label = ri.loadLabel(pm);
            app.packageName = ri.activityInfo.packageName;
            app.icon = ri.activityInfo.loadIcon(pm);
            appsList.add(app);
        }

        Collections.sort(appsList, new Comparator<AppInfo>()
        {
            @Override
            public int compare(AppInfo text1, AppInfo text2)
            {
                return text1.getLabel().toString().compareToIgnoreCase(text2.getLabel().toString());
            }
        });

        numberOfColumns = 4;
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));
        adapter = new RAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        init = true;

        return view;
    }

    public void update() {
        pm = getActivity().getPackageManager();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
        for(ResolveInfo ri:allApps) {
            AppInfo app = new AppInfo();
            app.label = ri.loadLabel(pm);
            app.packageName = ri.activityInfo.packageName;
            app.icon = ri.activityInfo.loadIcon(pm);
            appsList.add(app);

        }
        recyclerView.getAdapter().notifyDataSetChanged();
        Log.i("Refresh", "RecyclerView");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && init) {
            update();
        }
    }

    public void swap(Object a, Object b) {
        Object temp;
        temp = a;
        a = b;
        b = temp;
    }

    public void sort() {
        Collections.sort(appsList, new Comparator<AppInfo>()
        {
            @Override
            public int compare(AppInfo text1, AppInfo text2)
            {
                return text1.getLabel().toString().compareToIgnoreCase(text2.getLabel().toString());
            }
        });
    }



}
