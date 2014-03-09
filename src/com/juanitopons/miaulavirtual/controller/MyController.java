package com.juanitopons.miaulavirtual.controller;

import org.jsoup.Jsoup;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.juanitopons.miaulavirtual.model.Carpetas;
import com.juanitopons.miaulavirtual.model.ConnectionTask;
import com.juanitopons.miaulavirtual.model.MainAdapter;
import com.juanitopons.miaulavirtual.model.MyModel;
import com.juanitopons.miaulavirtual.view.MainActivity;

public class MyController {
    private static MyController controllerInstance = null;
    private MyModel modelInstance;
    private MainActivity mainActivity;
    private AulaVirtualController aulaController;
    private CalendarController calendarController;
    private BackController backController;
    private FragmentChangeController fragmentController;
    
    public MyController(MyModel model, MainActivity mainActivity) {
        controllerInstance = this;
        modelInstance = model;
        this.mainActivity = mainActivity;
        
        aulaController = new AulaVirtualController();
        calendarController = new CalendarController();
        backController = new BackController();
        fragmentController = new FragmentChangeController();
    }
    
    public static MyController getInstance(MyModel model, MainActivity mainActivity) {
        if(controllerInstance == null) {
            controllerInstance = new MyController(model, mainActivity);
        }
        return controllerInstance;
    }

    /**
     * @return the fragmentController
     */
    public FragmentChangeController getFragmentController() {
        return fragmentController;
    }

    /**
     * @param fragmentController the fragmentController to set
     */
    public void setFragmentController(FragmentChangeController fragmentController) {
        this.fragmentController = fragmentController;
    }

    /**
     * @return the backController
     */
    public BackController getBackController() {
        return backController;
    }

    /**
     * @param backController the backController to set
     */
    public void setBackController(BackController backController) {
        this.backController = backController;
    }
    
    public void setBackControllerActivity(FragmentActivity activity) {
        backController.setActivity(activity);
    }
    
    public void setBackControllerFrag(int mode) {
        backController.setMode(mode);
    }
    
    public void doBackController() {
        backController.doBack();
    }

    /**
     * @return the aulaController
     */
    public AulaVirtualController getAulaController() {
        return aulaController;
    }

    /**
     * @return the calendarController
     */
    public CalendarController getCalendarController() {
        return calendarController;
    }

    /**
     * @param aulaController the aulaController to set
     */
    public void setAulaController(AulaVirtualController aulaController) {
        this.aulaController = aulaController;
    }

    /**
     * @param calendarController the calendarController to set
     */
    public void setCalendarController(CalendarController calendarController) {
        this.calendarController = calendarController;
    }

    class AulaVirtualController implements OnItemClickListener {

        public AulaVirtualController() {}
        
        @Override
        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            mainActivity.setBackActive(false);
            Carpetas item = (Carpetas) MyModel.getDataOn(MyModel.AULAVIRTUAL)[position];
            MainAdapter adapter = MyModel.getAdaptersOn(MyModel.AULAVIRTUAL);
            adapter.clearData();
            adapter.setStatus(MyModel.LOAD);
            adapter.notifyDataSetChanged();
            new ConnectionTask(mainActivity, Integer.valueOf(MyModel.AULAVIRTUAL)).execute(MyModel.url3+item.getUrl());
            
        }
        
    }
    
    class CalendarController implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            
        }
        
    }
    
    class FragmentChangeController implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onPageSelected(int fragment) {
            controllerInstance.setBackControllerFrag(fragment);
        }
        
    }
    
    class BackController {
        
        private FragmentActivity activity = null;
        private int mode;
        
        public BackController() {}

        public BackController(FragmentActivity activity, int mode) {
            this.activity = activity;
            this.mode = mode;
        }

        /**
         * @param activity the activity to set
         */
        public void setActivity(FragmentActivity activity) {
            this.activity = activity;
        }

        /**
         * @param mode the mode to set
         */
        public void setMode(int mode) {
            this.mode = mode;
        }

        public void doBack() {
            activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Log.d("model", String.valueOf(mode));
            switch(mode) {
                case 0:
                    mainActivity.setBackActive(false);
                    MainAdapter adapter = MyModel.getAdaptersOn(MyModel.AULAVIRTUAL);
                    adapter.clearData();
                    adapter.setStatus(MyModel.LOAD);
                    adapter.notifyDataSetChanged();
                    new ConnectionTask(mainActivity, Integer.valueOf(MyModel.AULAVIRTUAL), true).execute(Carpetas.getInVector(Carpetas.vectorSize()-2));
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
        
    }
}
