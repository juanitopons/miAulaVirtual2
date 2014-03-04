package com.juanitopons.miaulavirtual.model;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class Parser {
    public Parser() {}
    
    public Elements parseDocuments(Document mdoc) throws IOException {
        Elements elem = mdoc.select("table[summary] tbody tr[class=odd], table[summary] tbody tr[class=even], table[summary] tbody tr[class=even last], table[summary] tbody tr[class=odd last]"); //Filas de Documentos
        return elem;
    }
    
    public String[] carpetasToArray(Elements elem /*, Boolean isHome, Boolean comun*/) throws IndexOutOfBoundsException {
        int i = 1;
        String[] carpetas;
        
        elem = elem.select("td[headers=contents_name] a, td[headers=folders_name] a").not("[href*=/clubs/]");
        carpetas = new String[(elem.size())+1]; //todo-comunidades + 1(carpeta comunidades)
        carpetas[0] = "Comunidades y otros";

        for(Element el : elem){
            carpetas[i] = el.text();
            i++;
        }
        
        return carpetas;
    }
    
    public String[] enlacesToArray(Elements elem /*, Boolean isHome, Boolean comun*/) throws IndexOutOfBoundsException {
        int i = 1;
        String[] enlaces;
        
        elem = elem.select("td[headers=contents_name] a, td[headers=folders_name] a").not("[href*=/clubs/]"); //Nombre Asignaturas String !"Comunuidades"
        enlaces = new String[(elem.size())+1];
        enlaces[0] = "/dotlrn/?page_num=2";
        for(Element el : elem){
            enlaces[i] = el.select("a").attr("href");
            i++;
        }
        
        return enlaces;
    }
    
    public String[] tiposToArray(Elements elem /*, Boolean isHome, Boolean comun*/, int size) throws IndexOutOfBoundsException {
        int i = 1;
        String[] mtypes = {"carpeta", "Carpeta", "PDF", "Microsoft Excel", "Microsoft PowerPoint", "Microsoft Word"};
        String[] tipos = new String[size];
        tipos[0] = "6";
        
        while(i<size) {
            tipos[i] = "1";
            i++;
        }
        
        return tipos;
    }
}
