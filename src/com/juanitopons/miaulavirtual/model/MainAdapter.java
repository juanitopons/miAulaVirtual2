package com.juanitopons.miaulavirtual.model;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Display;
import android.view.WindowManager;
import android.widget.BaseAdapter;

public abstract class MainAdapter extends BaseAdapter {
    protected Activity context;
    protected int status;
    protected int mode;
    
    /**
     * 
     */
    public MainAdapter() {
        super();
    }

    /**
     * @param data
     */
    public MainAdapter(Activity context, int status, int mode) {
        super();
        this.context = context;
        this.status = status;
        this.mode = mode;
    }
    

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
    

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the context
     */
    public Activity getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(Activity context) {
        this.context = context;
    }
    
    public int getCount() {
        if(MyModel.getDataOn(mode) != null) {
            return MyModel.getDataOn(mode).length;
        }
        return 1;
    }
    
    public void clearData() {
        // clear the data
        if(MyModel.getDataOn(mode) != null)
            MyModel.setDataOn(null, mode);
    }
    
    public Object getItem(int position) {
        return position;
    }
    
    public long getItemId(int position) {
        return position;
    }
    
    protected void setRestrictedOrientation() {
        /* We don't want change screen orientation */
        //---get the current display info---
        WindowManager wm = context.getWindowManager();
        Display d = wm.getDefaultDisplay();
        if (d.getWidth() > d.getHeight()) {
            //---change to landscape mode---
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            //---change to portrait mode---
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }
    
}
