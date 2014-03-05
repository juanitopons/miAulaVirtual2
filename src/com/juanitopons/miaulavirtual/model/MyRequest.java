package com.juanitopons.miaulavirtual.model;

import java.io.IOException;
import java.net.SocketTimeoutException;


import android.util.Log;

import com.juanitopons.miaulavirtual.model.ConnectionDetector;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class MyRequest {
    private static MyRequest requestInstance = null;
    private MyModel modelInstance;
    private ConnectionDetector connectionInstance;
    private Response[] resp = new Response[3];
    
    protected MyRequest() {
        modelInstance = MyModel.getInstance();
        connectionInstance = ConnectionDetector.getInstance(modelInstance.getContext());
    }
    
    public static MyRequest getInstance() {
        if(requestInstance == null) {
            requestInstance = new MyRequest();
        }
        return requestInstance;
     }
    
    public void doPostUrl1() throws IOException, SocketTimeoutException, BadDataException {
        Log.d("model", "POST");
        checkInternet();
        resp[MyModel.POST] = Jsoup.connect(modelInstance.getUrl1())
        .data("password", modelInstance.getPass(), "submit", "INICIAR SESIÓN", "username", modelInstance.getUser())
        .method(Method.POST)
        .timeout(10*1000)
        .execute();
        
        if(resp[MyModel.POST].hasCookie("PAPIAuthNcook"))
            modelInstance.setCookies1(resp[MyModel.POST].cookies());
        else
            throw new BadDataException();
    }
    
    public void doGetUrl2() throws IOException, SocketTimeoutException, BadDataException {
        checkInternet();
        resp[MyModel.POST] = Jsoup.connect(modelInstance.getUrl2()).cookies(modelInstance.getCookies1()).method(Method.GET).timeout(10*1000).execute();
        
        if(resp[MyModel.POST].hasCookie("tupi_style"))
            modelInstance.setCookies1(resp[MyModel.POST].cookies());
        else
            throw new BadDataException();
    }
    
    public void doGet(String parameters, int mode) throws IOException, SocketTimeoutException {
        checkInternet();
        resp[mode] = Jsoup.connect(modelInstance.getUrl3()+parameters).cookies(modelInstance.getCookies1()).method(Method.GET).timeout(10*1000).execute();
    }
    
    public void checkInternet() throws IOException {
        if(!connectionInstance.isConnectingToInternet()) throw new SocketTimeoutException();
    }

    /**
     * @return the resp
     */
    public Response getResp(int mode) {
        return resp[mode];
    }

    /**
     * @param resp the resp to set
     */
    public void setResp(Response resp, int mode) {
        this.resp[mode] = resp;
    }
    
}
