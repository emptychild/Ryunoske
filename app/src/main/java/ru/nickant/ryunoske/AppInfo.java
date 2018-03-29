package ru.nickant.ryunoske;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

public class AppInfo {
    public CharSequence getLabel() {
        return label;
    }

    public void setLabel(CharSequence label) {
        this.label = label;
    }

    public CharSequence getPackageName() {
        return packageName;
    }

    public void setPackageName(CharSequence packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    CharSequence label;
    CharSequence packageName;
    Drawable icon;
}
