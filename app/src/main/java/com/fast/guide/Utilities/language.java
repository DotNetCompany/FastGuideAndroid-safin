package com.fast.guide.Utilities;

import android.app.Application;
import android.content.Context;

public class language extends Application
{
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "ku"));
    }
}
