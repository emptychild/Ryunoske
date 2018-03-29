package ru.nickant.ryunoske;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RAdapter extends RecyclerView.Adapter<RAdapter.ViewHolder> {
    private List<AppInfo> appsList;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView textView;
        public ImageView img;

        //This is the subclass ViewHolder which simply
        //'holds the views' for us to show on each row
        public ViewHolder(View itemView) {
            super(itemView);

            //Finds the views from our row.xml
            textView = (TextView) itemView.findViewById(R.id.iconName);
            img = (ImageView) itemView.findViewById(R.id.iconLogo);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick (View v) {
            int pos = getAdapterPosition();
            Context context = v.getContext();

            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appsList.get(pos).packageName.toString());
            context.startActivity(launchIntent);
            //Toast.makeText(v.getContext(), appsList.get(pos).label.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean onLongClick(final View view) {
            final int pos = getAdapterPosition();
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.inflate(R.menu.menu_app);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();

                    //noinspection SimplifiableIfStatement
                    if (id == R.id.action_app_delete) {
                        Uri packageURI = Uri.parse("package:"+appsList.get(pos).packageName.toString());
                        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                        view.getContext().startActivity(uninstallIntent);
                        return true;
                    }
                    return true;
                }
            });
            popup.show();
            return false;
        }
    }



    public RAdapter(Context c) {

        //This is where we build our list of app details, using the app
        //object we created to store the label, package name and icon

        PackageManager pm = c.getPackageManager();
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

    }

    @Override
    public void onBindViewHolder(RAdapter.ViewHolder viewHolder, int i) {

        //Here we use the information in the list we created to define the views

        String appLabel = appsList.get(i).label.toString();
        String appPackage = appsList.get(i).packageName.toString();
        Drawable appIcon = appsList.get(i).icon;

        TextView textView = viewHolder.textView;
        textView.setText(appLabel);
        ImageView imageView = viewHolder.img;
        imageView.setImageDrawable(appIcon);
    }


    @Override
    public int getItemCount() {

        //This method needs to be overridden so that Androids knows how many items
        //will be making it into the list

        return appsList.size();
    }


    @Override
    public RAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.icon_cell, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}
