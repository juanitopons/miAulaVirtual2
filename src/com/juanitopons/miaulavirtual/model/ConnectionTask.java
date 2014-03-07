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
    private MainActivity mainActivity; 
    private MyRequest request;
    private Parser parser;
    private MainAdapter adapter;
    private int mode;
    private ConnectionTask taskToAwake = null;
    private Response response = null;
    
    public ConnectionTask(MainActivity mainActivity, MyRequest request, Parser parser, int mode) {
        this.mainActivity = mainActivity;
        this.request = request;
        this.parser = parser;
        this.adapter = MainActivity.getAdapter(mode);
        this.mode = mode;
    }
    
    public ConnectionTask(MainActivity mainActivity, MyRequest request, Parser parser, int mode, ConnectionTask connectionTask2) {
        this.mainActivity = mainActivity;
        this.request = request;
        this.parser = parser;
        this.mode = mode;
        this.taskToAwake = connectionTask2;
        
    }
    
    protected Integer doInBackground(String... parameters) {
        Integer state = -1;
        try {
            if(!parameters[0].isEmpty()) {
                response = request.doGet(parameters[0]);
            } else {
                request.doPostUrl1();
                request.doGetUrl2();
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
                for(MainAdapter adapter: MainActivity.getAdapters()) {
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
                    taskToAwake.execute(String.valueOf(MyModel.getInstance().getPanel()));
                break;
            default:
                adapter.clearData();
                parser.makeAulaVirtual(Jsoup.parse(response.body()));
                adapter.setStatus(MyModel.OK);
                adapter.notifyDataSetChanged();
                break;
        }
    }
}