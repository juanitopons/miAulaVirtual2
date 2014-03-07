package com.juanitopons.miaulavirtual.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class Parser {
    private static Parser parserInstance = null;
    private static MyModel modelInstance;
    
    protected Parser() {
        modelInstance = MyModel.getInstance();
    }
    
    public static Parser getInstance() {
        if(parserInstance == null) {
            parserInstance = new Parser();
        }
        return parserInstance;
     }

    public Elements parseDocuments(Document mdoc) throws IOException {
        Elements elem = mdoc.select("table[summary] tbody tr[class=odd], table[summary] tbody tr[class=even], table[summary] tbody tr[class=even last], table[summary] tbody tr[class=odd last]"); //Filas de Documentos
        return elem;
    }
    
    public void carpetasToArray(Elements elem, Carpetas[] carpetas) throws IndexOutOfBoundsException {
        
        int i = 0;
        elem = elem.select("td[headers=contents_name] a, td[headers=folders_name] a").not("[href*=/clubs/]");
        Log.d("model", String.valueOf(elem.size()));
        for(Element el : elem){
            carpetas[i].setNombre(el.text());
            i++;
        }
    }
    
    public void enlacesToArray(Elements elem, Carpetas[] carpetas) throws IndexOutOfBoundsException {
        int i = 0;
        elem = elem.select("td[headers=contents_name] a, td[headers=folders_name] a").not("[href*=/clubs/]"); //Nombre Asignaturas String !"Comunuidades"
        Log.d("model", String.valueOf(elem.size()));
        for(Element el : elem){
            carpetas[i].setUrl(el.select("a").attr("href"));
            i++;
        }
    }
    
    public void tiposToArray(Elements elem, Carpetas[] carpetas) throws IndexOutOfBoundsException {
        int i = 0;
        elem = elem.select("td[headers=folders_type], td[headers=contents_type]");
        Log.d("model", String.valueOf(elem.size()));
        while(i<carpetas.length){
            carpetas[i].setType(modelInstance.getIntType(elem.get(i).text().trim()));
            i++;
        }
    }
    
    public void makeAulaVirtual(Document mdoc) {
        Carpetas[] carpetas = (Carpetas[])MyModel.getDataOn(MyModel.AULAVIRTUAL);
        Elements elements = null;
        try {
            elements = parseDocuments(mdoc);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int count = elements.select("td[headers=contents_name] a, td[headers=folders_name] a").not("[href*=/clubs/]").size();
        carpetas = new Carpetas[count];
        for(int i = 0; i<count; i++)
            carpetas[i] = new Carpetas();
        carpetasToArray(elements, carpetas);
        enlacesToArray(elements, carpetas);
        tiposToArray(elements, carpetas);
        MyModel.setDataOn(carpetas, MyModel.AULAVIRTUAL);
    }
}
