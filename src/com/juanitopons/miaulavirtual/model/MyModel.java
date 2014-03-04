package com.juanitopons.miaulavirtual.model;

import java.io.Serializable;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class MyModel implements Serializable {
    public static final int NOINTERNET = 0, RANDOM = 1, BADDATA = 2;
    public static MyModel modelInstance;
    private MyRequest request;
    private Context context;
    private final SharedPreferences prefs;
    private final Editor editor;
    private final String url1 = "https://as.uv.es/cgi-bin/AuthServer", url2 = "http://aulavirtual.uv.es/cas", url3 = "http://aulavirtual.uv.es/dotlrn/?page_num=";
    private String user, pass, panel;
    private Map<String, String> cookies1;
    
    public MyModel(Context context) {
        modelInstance = this;
        this.context = context;
        prefs = context.getSharedPreferences("apppreferences", 0);
        user = prefs.getString("myuser", "0");
        pass = prefs.getString("mypass", "0");
        panel = prefs.getString("panel", "2");
        editor = prefs.edit();
    }
    
    /**
     * @return the context
     */
    public Context getContext() {
        return context;
    }

    /**
     * @return the request
     */
    public MyRequest getRequest() {
        return request;
    }

    /**
     * @return the url1
     */
    public String getUrl1() {
        return url1;
    }

    /**
     * @return the url2
     */
    public String getUrl2() {
        return url2;
    }

    /**
     * @return the url3
     */
    public String getUrl3() {
        return url3;
    }

    /**
     * @return the panel
     */
    public String getPanel() {
        return panel;
    }

    /**
     * @param panel the panel to set
     */
    public void setPanel(String panel) {
        this.panel = panel;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
        editor.putString("myuser", user);
        editor.commit();
        
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
        editor.putString("mypass", pass);
        editor.commit();
    }
    
    public void setCookies1(Map<String, String> myCookies) {
        this.cookies1 = myCookies;
    }
    
    public Map<String, String> getCookies1() {
        return cookies1;
    }

}
