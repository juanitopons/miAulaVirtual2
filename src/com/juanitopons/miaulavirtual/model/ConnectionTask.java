package com.juanitopons.miaulavirtual.model;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.juanitopons.miaulavirtual.view.MainActivity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

public class ConnectionTask extends AsyncTask<String, Integer, Integer> {
    private MyModel modelInstance;
    private MainActivity mainActivity; 
    private MainAdapter adapter;
    private int mode;
    private ConnectionTask taskToAwake = null;
    private Response response = null;
    private String parameters;
    private boolean goBack = false;
    
    public ConnectionTask(MainActivity mainActivity, int mode) {
        this.modelInstance = MyModel.getInstance();
        this.mainActivity = mainActivity;
        this.adapter = MyModel.getAdaptersOn(mode);
        this.mode = mode;
    }
    
    public ConnectionTask(MainActivity mainActivity, int mode, boolean goBack) {
        this.modelInstance = MyModel.getInstance();
        this.mainActivity = mainActivity;
        this.adapter = MyModel.getAdaptersOn(mode);
        this.mode = mode;
        this.goBack = goBack;
    }
    
    public ConnectionTask(MainActivity mainActivity, int mode, ConnectionTask connectionTask2) {
        this.modelInstance = MyModel.getInstance();
        this.mainActivity = mainActivity;
        this.mode = mode;
        this.taskToAwake = connectionTask2;
        
    }
    
    protected Integer doInBackground(String... parameters) {
        Integer state = -1;
        try {
            if(!parameters[0].isEmpty()) {
                this.parameters = parameters[0];
                Log.d("model", parameters[0]);
                response = modelInstance.getRequest().doGet(parameters[0]);
            } else {
                modelInstance.getRequest().doPostUrl1();
                modelInstance.getRequest().doGetUrl2();
            }
            Log.d("model", "doInBackground acabado: "+String.valueOf(mode));
        } catch (SocketTimeoutException e) {
            state = MyModel.NOINTERNET;
            this.cancel(true);
        } catch (IOException e) {
            state = MyModel.RANDOM;
            this.cancel(true);
        } catch (BadDataException e) {
            /** >>>>>>-----------INTENT START ACTIVITY A LOGI PARA QUE VUELVA A INTENTAR EL LOGUEO------------<<<<<<<<<< **/
            state = MyModel.BADDATA;
            this.cancel(true);
        }
        return state;
    }

    protected void onProgressUpdate(Integer... progress) {}
    
    protected void onCancelled(Integer state) {
        Log.d("model", "onCancelled: "+String.valueOf(mode));
        MainActivity.showError(state);
        if(mode != MyModel.POST) {
            adapter.clearData();
            adapter.setStatus(MyModel.ERROR);
            adapter.notifyDataSetChanged();
        } else {
            if(state == MyModel.BADDATA) {
                mainActivity.moveToLogin();
            } else {
                for(MainAdapter adapter: MyModel.getAdapters()) {
                    if(adapter != null) {
                        adapter.clearData();
                        adapter.setStatus(MyModel.ERROR);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    protected void onPostExecute(Integer state) {
        Log.d("model", "onPostExecute: "+String.valueOf(mode));
        switch(mode) {
            case MyModel.POST:
                if(taskToAwake != null)
                    taskToAwake.execute(modelInstance.getUrl3()+MyModel.URL4+modelInstance.getPanel());
                break;
            default:
                adapter.clearData();
                modelInstance.getParser().makeAulaVirtual(Jsoup.parse(response.body()));
                adapter.setStatus(MyModel.OK);
                adapter.notifyDataSetChanged();
                if(mode == MyModel.AULAVIRTUAL)
                    if(!goBack)
                        Carpetas.addToVector(parameters);
                    else
                        Carpetas.delLastInVector();
                mainActivity.setBackActive(true);
                break;
        }
    }
}