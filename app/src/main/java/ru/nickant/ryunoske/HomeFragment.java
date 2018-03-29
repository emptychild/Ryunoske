package ru.nickant.ryunoske;


import android.app.WallpaperManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    boolean init = false;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int screenWidth;
        int screenHeight;
        // Inflate the layout for this fragment
        ConstraintLayout root = container.findViewById(R.id.container_main);
        DisplayMetrics metrics=new DisplayMetrics();
        // Get screen size (width and height)
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth=metrics.widthPixels;
        screenHeight=metrics.heightPixels;
        if (init) {
            root.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.inflate(R.menu.menu_home);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            //noinspection SimplifiableIfStatement
                            if (id == R.id.action_home_wallpaper) {
                                Toast.makeText(getContext(),"Wallpaper", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                            return false;
                        }
                    });
                    return false;
                }
            });
        }

        return inflater.inflate(R.layout.fragment_home, container, false);
    }



}
