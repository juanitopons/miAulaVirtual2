package com.juanitopons.miaulavirtual.model;

import java.util.Vector;

public class Carpetas {
    private static Vector<String> lead; /** IMPORTANT **/
    private String nombre;
    private String url;
    private int type;
    
    public Carpetas() {}
    
    /**
     * @param nombre
     * @param url
     * @param type
     */
    public Carpetas(String nombre, String url, int type) {
        super();
        this.nombre = nombre;
        this.url = url;
        this.type = type;
    }
    
    public void delLastInVector() {
        lead.remove(lead.size());
    }
    
    public void addToVector(String url) {
        lead.add(url);
    }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @return the type
     */
    public int getType() {
        return type;
    }
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }
    
    
}
