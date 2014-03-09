package com.juanitopons.miaulavirtual.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class MyModel implements Serializable {
    private static MyModel modelInstance;
    private static String[] mtypes = {"carpeta", "Carpeta", "PDF", "Microsoft Excel", "Microsoft PowerPoint", "Microsoft Word"};
    public static final String url1 = "https://as.uv.es/cgi-bin/AuthServer", url2 = "http://aulavirtual.uv.es/cas", url3 = "http://aulavirtual.uv.es", URL4 = "/dotlrn/?page_num=";
    //Map<String, Integer> aMap = new HashMap<String, Integer>({{"carpeta", 0}});
    public static final int CARPETA = 0, PDF = 1, EXCEL = 2, PPT = 3, WORD = 4; 
    public static final int POST = 2, AULAVIRTUAL = 0, CALENDARIO = 1;
    public static final int NOINTERNET = 0, RANDOM = 1, BADDATA = 2;
    public static final int ERROR = 0, LOAD = 1, OK = 2;

    private static MainAdapter[] adapters = new MainAdapter[2];
    private static Object[][] data = new Object[2][];
    private ConnectionDetector connector;
    private Parser parser;
    private MyRequest request;
    private Context context;
    private SharedPreferences prefs;
    private Editor editor;
    private String user, pass, panel;
    private Map<String, String> cookies1;
    
    protected MyModel(Context context) {
        modelInstance = this;
        this.context = context;
        this.connector = new ConnectionDetector(this);
        this.parser = new Parser(this);
        this.request = new MyRequest(this);
        
        prefs = context.getSharedPreferences("apppreferences", 0);
        user = prefs.getString("myuser", "0");
        pass = prefs.getString("mypass", "0");
        panel = prefs.getString("panel", "2");
        editor = prefs.edit();
    }
    
    public static MyModel getInstance() {
        return modelInstance;
    }
    
    public static MyModel getInstance(Context context) {
        if(modelInstance == null) {
            modelInstance = new MyModel(context);
        }
        return modelInstance;
    }
    
    /**
     * @return the connector
     */
    public ConnectionDetector getConnector() {
        return connector;
    }

    /**
     * @return the parser
     */
    public Parser getParser() {
        return parser;
    }

    /**
     * @param connector the connector to set
     */
    public void setConnector(ConnectionDetector connector) {
        this.connector = connector;
    }

    /**
     * @param parser the parser to set
     */
    public void setParser(Parser parser) {
        this.parser = parser;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(MyRequest request) {
        this.request = request;
    }

    /**
     * @return the adapters
     */
    public static MainAdapter getAdaptersOn(int mode) {
        return adapters[mode];
    }

    /**
     * @param adapters the adapters to set
     */
    public static void setAdaptersOn(MainAdapter adapters, int mode) {
        MyModel.adapters[mode] = adapters;
    }
    
    /**
     * @return the adapters
     */
    public static MainAdapter[] getAdapters() {
        return adapters;
    }

    /**
     * @param adapters the adapters to set
     */
    public static void setAdapters(MainAdapter[] adapters) {
        MyModel.adapters = adapters;
    }

    /**
     * @return the data
     */
    public static Object[] getDataOn(int mode) {
        return data[mode];
    }

    /**
     * @param data the data to set
     */
    public static void setDataOn(Object[] data, int mode) {
        MyModel.data[mode] = data;
    }
    
    /**
     * @return the data
     */
    public static Object[][] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public static void setData(Object[][] data) {
        MyModel.data = data;
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
