package com.juanitopons.miaulavirtual.model;

import java.io.IOException;
import java.net.SocketTimeoutException;


import com.juanitopons.miaulavirtual.model.ConnectionDetector;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class MyRequest {
    private MyModel modelInstance;
    private ConnectionDetector connectionInstance;
    private Response resp = null;
    
    public MyRequest(ConnectionDetector connector, MyModel model) {
        modelInstance = model;
        connectionInstance = connector;
    }
    
    public void doPostUrl1() throws IOException, SocketTimeoutException, BadDataException {
        checkInternet();
        resp = Jsoup.connect(modelInstance.getUrl1())
        .data("password", modelInstance.getPass(), "submit", "INICIAR SESIÓN", "username", modelInstance.getUser())
        .method(Method.POST)
        .timeout(10*1000)
        .execute();
        
        if(resp.hasCookie("PAPIAuthNcook"))
            modelInstance.setCookies1(resp.cookies());
        else
            throw new BadDataException();
    }
    
    public void doGetUrl2() throws IOException, SocketTimeoutException, BadDataException {
        checkInternet();
        resp = Jsoup.connect(modelInstance.getUrl2()).cookies(modelInstance.getCookies1()).method(Method.GET).timeout(10*1000).execute();
        
        if(resp.hasCookie("tupi_style"))
            modelInstance.setCookies1(resp.cookies());
        else
            throw new BadDataException();
    }
    
    public void doGet(String parameters) throws IOException, SocketTimeoutException {
        checkInternet();
        resp = Jsoup.connect(modelInstance.getUrl3()+parameters).cookies(modelInstance.getCookies1()).method(Method.GET).timeout(10*1000).execute();
    }
    
    public void checkInternet() throws IOException {
        if(!connectionInstance.isConnectingToInternet()) throw new IOException();
    }

    /**
     * @return the resp
     */
    public Response getResp() {
        return resp;
    }

    /**
     * @param resp the resp to set
     */
    public void setResp(Response resp) {
        this.resp = resp;
    }
    
}
