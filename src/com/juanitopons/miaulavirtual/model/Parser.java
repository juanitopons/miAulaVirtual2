package com.juanitopons.miaulavirtual.model;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class Parser {
    private static Parser parserInstance = null;
    private static MyModel modelInstance;
    Carpetas[] aulaVirtual;
    
    protected Parser() {
        modelInstance = MyModel.getInstance();
    }
    
    public static Parser getInstance() {
        if(parserInstance == null) {
            parserInstance = new Parser();
        }
        return parserInstance;
     }
    
    /**
     * @return the aulaVirtual
     */
    public Carpetas[] getAulaVirtual() {
        return aulaVirtual;
    }

    /**
     * @param aulaVirtual the aulaVirtual to set
     */
    public void setAulaVirtual(Carpetas[] aulaVirtual) {
        this.aulaVirtual = aulaVirtual;
    }

    public Elements parseDocuments(Document mdoc) throws IOException {
        Elements elem = mdoc.select("table[summary] tbody tr[class=odd], table[summary] tbody tr[class=even], table[summary] tbody tr[class=even last], table[summary] tbody tr[class=odd last]"); //Filas de Documentos
        return elem;
    }
    
    public void carpetasToArray(Elements elem /*, Boolean isHome, Boolean comun*/) throws IndexOutOfBoundsException {
        int i = 0;
        elem = elem.select("td[headers=contents_name] a, td[headers=folders_name] a").not("[href*=/clubs/]");
        Log.d("model", String.valueOf(elem.size()));
        for(Element el : elem){
            aulaVirtual[i].setNombre(el.text());
            i++;
        }
    }
    
    public void enlacesToArray(Elements elem /*, Boolean isHome, Boolean comun*/) throws IndexOutOfBoundsException {
        int i = 0;
        elem = elem.select("td[headers=contents_name] a, td[headers=folders_name] a").not("[href*=/clubs/]"); //Nombre Asignaturas String !"Comunuidades"
        Log.d("model", String.valueOf(elem.size()));
        for(Element el : elem){
            aulaVirtual[i].setUrl(el.select("a").attr("href"));
            i++;
        }
    }
    
    public void tiposToArray(Elements elem /*, Boolean isHome, Boolean comun*/, int size) throws IndexOutOfBoundsException {
        int i = 0;
        elem = elem.select("td[headers=folders_type], td[headers=contents_type]");
        Log.d("model", String.valueOf(elem.size()));
        while(i<size){
            aulaVirtual[i].setType(modelInstance.getIntType(elem.get(i).text().trim()));
            i++;
        }
    }
    
    public void makeAulaVirtual(Document mdoc) {
        Elements elements = null;
        try {
            elements = parseDocuments(mdoc);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int count = elements.select("td[headers=contents_name] a, td[headers=folders_name] a").not("[href*=/clubs/]").size();
        aulaVirtual = new Carpetas[count];
        for(int i = 0; i<count; i++)
            aulaVirtual[i] = new Carpetas();
        carpetasToArray(elements);
        enlacesToArray(elements);
        tiposToArray(elements, aulaVirtual.length);
    }
}
