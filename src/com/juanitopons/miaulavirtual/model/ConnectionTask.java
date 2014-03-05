package com.juanitopons.miaulavirtual.model;

import java.io.IOException;
import java.net.SocketTimeoutException;
import org.jsoup.Jsoup;

import com.juanitopons.miaulavirtual.view.MainActivity;

import android.os.AsyncTask;
import android.util.Log;

public class ConnectionTask extends AsyncTask<String, Integer, Integer> {
    private MainActivity mainActivity; 
    private MyRequest request;
    private Parser parser;
    private ListAdapter[] adapters;
    private int mode;
    private ConnectionTask taskToAwake = null;
    
    public ConnectionTask(MainActivity mainActivity, MyRequest request, Parser parser, int mode) {
        this.mainActivity = mainActivity;
        this.request = request;
        this.parser = parser;
        this.adapters = MainActivity.getAdapters();
        this.mode = mode;
    }
    
    public ConnectionTask(MainActivity mainActivity, MyRequest request, Parser parser, int mode, ConnectionTask connectionTask2) {
        this.mainActivity = mainActivity;
        this.request = request;
        this.parser = parser;
        this.adapters = MainActivity.getAdapters();
        this.mode = mode;
        this.taskToAwake = connectionTask2;
        
    }
    
    protected Integer doInBackground(String... parameters) {
        Integer state = -1;
        try {
            if(!parameters[0].isEmpty()) {
                request.doGet(parameters[0], mode);
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
        if(state == MyModel.BADDATA) {
            mainActivity.moveToLogin();
        } else {
            for(ListAdapter adapter : adapters) {
                if(adapter!=null) { /** <<<<<<<<<<<<<<<<--------------BORRAR POST DESARROLLO------------>>>>>>>>>> **/
                    adapter.clearData();
                    adapter.setStatus(MyModel.ERROR);
                    adapter.notifyDataSetChanged();
                }
            } 
        }
    }

    protected void onPostExecute(Integer state) {
        Log.d("model", "onPostExecute: "+String.valueOf(mode));
        if(mode != MyModel.POST) {
            adapters[mode].clearData();
            parser.makeAulaVirtual(Jsoup.parse(request.getResp(MyModel.AULAVIRTUAL).body()));
            adapters[mode].setCarpetas(parser.getAulaVirtual());
            adapters[mode].setStatus(MyModel.OK);
            adapters[mode].notifyDataSetChanged();
        } else if(taskToAwake != null) {
            taskToAwake.execute(String.valueOf(MyModel.getInstance().getPanel()), String.valueOf(taskToAwake.mode));
        }
    }
}