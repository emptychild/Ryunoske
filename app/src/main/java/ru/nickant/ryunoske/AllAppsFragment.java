package ru.nickant.ryunoske;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import java.util.List;

public class AllAppsFragment extends Fragment {

    ArrayList appsList;
    RAdapter adapter;
    RecyclerView recyclerView;
    int numberOfColumns;
    boolean init = false;
    Context context = getActivity();


    public AllAppsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_apps, container, false);

        PackageManager pm = getActivity().getPackageManager();
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
        numberOfColumns = 5;
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));
        adapter = new RAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        init = true;

        return view;
    }

    public void update() {
        PackageManager pm = getActivity().getPackageManager();
        appsList = new ArrayList<AppInfo>();

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
    }

    @Override
    public void onResume() {
        super.onResume();
        if (init) update();
        Log.d("onResume", "refreshed");
    }



}
