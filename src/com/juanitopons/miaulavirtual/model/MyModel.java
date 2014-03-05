package com.juanitopons.miaulavirtual.model;

import java.io.Serializable;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class MyModel implements Serializable {
    private static MyModel modelInstance;
    private static String[] mtypes = {"carpeta", "Carpeta", "PDF", "Microsoft Excel", "Microsoft PowerPoint", "Microsoft Word"};
    private final String url1 = "https://as.uv.es/cgi-bin/AuthServer", url2 = "http://aulavirtual.uv.es/cas", url3 = "http://aulavirtual.uv.es/dotlrn/?page_num=";
    //Map<String, Integer> aMap = new HashMap<String, Integer>({{"carpeta", 0}});
    public static final int CARPETA = 0, PDF = 1, EXCEL = 2, PPT = 3, WORD = 4; 
    public static final int POST = 2, AULAVIRTUAL = 0, CALENDARIO = 1;
    public static final int NOINTERNET = 0, RANDOM = 1, BADDATA = 2;
    public static final int ERROR = 0, LOAD = 1, OK = 2;
    private MyRequest request;
    private Context context;
    private SharedPreferences prefs;
    private Editor editor;
    private String user, pass, panel;
    private Map<String, String> cookies1;
    
    protected MyModel() {
        modelInstance = this;
    }
    
    public static MyModel getInstance() {
        if(modelInstance == null) {
            modelInstance = new MyModel();
        }
        return modelInstance;
     }
    
    public int getIntType(String type) {
        int a = CARPETA;
        if(mtypes[2].equals(type)) a = PDF; // 2 = PDF 
        if(mtypes[3].equals(type)) a = EXCEL; // 3 = Excel
        if(mtypes[4].equals(type)) a = PPT; // 4 = Power Point
        if(mtypes[5].equals(type)) a = WORD;
        
        return a;
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
    
    public void setContext(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("apppreferences", 0);
        user = prefs.getString("myuser", "0");
        pass = prefs.getString("mypass", "0");
        panel = prefs.getString("panel", "2");
        editor = prefs.edit();
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
