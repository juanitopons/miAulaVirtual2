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
    
    public Response doPostUrl1() throws IOException, SocketTimeoutException, BadDataException {
        Log.d("model", "POST");
        checkInternet();
        Response resp = Jsoup.connect(modelInstance.getUrl1())
        .data("password", modelInstance.getPass(), "submit", "INICIAR SESIÓN", "username", modelInstance.getUser())
        .method(Method.POST)
        .timeout(10*1000)
        .execute();
        
        if(resp.hasCookie("PAPIAuthNcook"))
            modelInstance.setCookies1(resp.cookies());
        else
            throw new BadDataException();
        
        return resp;
    }
    
    public Response doGetUrl2() throws IOException, SocketTimeoutException, BadDataException {
        checkInternet();
        Response resp = Jsoup.connect(modelInstance.getUrl2()).cookies(modelInstance.getCookies1()).method(Method.GET).timeout(10*1000).execute();
        
        if(resp.hasCookie("tupi_style"))
            modelInstance.setCookies1(resp.cookies());
        else
            throw new BadDataException();
        
        return resp;
    }
    
    public Response doGet(String parameters) throws IOException, SocketTimeoutException {
        checkInternet();
        Response resp = Jsoup.connect(modelInstance.getUrl3()+parameters).cookies(modelInstance.getCookies1()).method(Method.GET).timeout(10*1000).execute();
        
        return resp;
    }
    
    public void checkInternet() throws IOException {
        if(!connectionInstance.isConnectingToInternet()) throw new SocketTimeoutException();
    }
    
}
