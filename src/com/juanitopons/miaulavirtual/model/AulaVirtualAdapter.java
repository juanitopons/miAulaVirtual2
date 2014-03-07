/*
* Copyright (C) 2013 Juan Pons (see README for details)
* This file is part of miAulaVirtual.
*
* miAulaVirtual is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* miAulaVirtual is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with miAulaVirtual. If not, see <http://www.gnu.org/licenses/agpl.txt>.
*
*/

package com.juanitopons.miaulavirtual.model;

import java.util.ArrayList;

import com.juanitopons.miaulavirtual.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Creamos nuestro propio adaptador para la lista con la siguiente clase: AdaptadorDocs
 */

public class AulaVirtualAdapter extends MainAdapter {

    public AulaVirtualAdapter(Activity context) {
    	 super(context, MyModel.LOAD, MyModel.AULAVIRTUAL);
    }
    
	public View getView(int position, View convertView, ViewGroup parent) {
    	View item = null;
    	LayoutInflater inflater = context.getLayoutInflater();
        Carpetas[] carpetas = (Carpetas[])MyModel.getDataOn(MyModel.AULAVIRTUAL);
    	switch(status) {
    	    case MyModel.OK:
                item = inflater.inflate(R.layout.list_docs, null);
                item.setMinimumHeight(65);  
                item.setPadding(14, 0, 6, 0);
                TextView title;
                ImageView image;
                int ico;
                // Title
        	    title = (TextView)item.findViewById(R.id.list_title);
        	    String rgxTitle;
        	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context); // Obtenemos las preferencias
        	    
        	    // Image
        	    image = (ImageView)item.findViewById(R.id.folderImage);
        	    switch(carpetas[position].getType()) {
        	    case 0: // Folder
        	    	rgxTitle = carpetas[position].getNombre().replaceAll( "\\d{4}-\\d{4}\\s|\\d{4}-\\d{2}\\s|Documentos\\sde\\s?|Gr\\..+?\\s|\\(.+?\\)", "" );
        	    	title.setText(rgxTitle.trim());
        	    	ico = context.getResources().getIdentifier("com.juanitopons.miaulavirtual:drawable/icon_folder", null, null); // Folder ico
        	    	image.setImageResource(ico);
        	    	break;
        	    case 1: // PDF
        	    	rgxTitle = carpetas[position].getNombre();
        	    	title.setText(rgxTitle.trim());
        	    	ico = context.getResources().getIdentifier("com.juanitopons.miaulavirtual:drawable/ic_pdf", null, null); // PDF ico
        	    	image.setImageResource(ico);
        	    	break;	    	
        	    case 2: // Excel
        	    	rgxTitle = carpetas[position].getNombre();
        	    	title.setText(rgxTitle.trim());
        	    	ico = context.getResources().getIdentifier("com.juanitopons.miaulavirtual:drawable/ic_excel", null, null); // Excel ico
        	    	image.setImageResource(ico);
        	    	break;
        	    case 3: // Power Point
        	    	rgxTitle = carpetas[position].getNombre();
        	    	title.setText(rgxTitle.trim());
        	    	ico = context.getResources().getIdentifier("com.juanitopons.miaulavirtual:drawable/ic_ppt", null, null); // PPT ico
        	    	image.setImageResource(ico);
        	    	break;
        	    case 4: // Word
        	    	rgxTitle = carpetas[position].getNombre();
        	    	title.setText(rgxTitle.trim());
        	    	ico = context.getResources().getIdentifier("com.juanitopons.miaulavirtual:drawable/ic_word", null, null); // Word ico
        	    	image.setImageResource(ico);
        	    	break;
        	    default: // Default
        	    	rgxTitle = carpetas[position].getNombre();
        	    	title.setText(rgxTitle);
        	    	ico = context.getResources().getIdentifier("com.juanitopons.miaulavirtual:drawable/ic_def", null, null); // Other ico
        	    	image.setImageResource(ico);
        	    	break;
        	    }
        	    if(!prefs.getBoolean("pattern", true)) title.setText(carpetas[position].getNombre());
        	    break;
    	    case MyModel.LOAD: 		
        		setRestrictedOrientation();
        	    item = inflater.inflate(R.layout.load, null);
        	    item.setMinimumHeight(35);
        	    break;
    	    case MyModel.ERROR:
    	        setRestrictedOrientation();
                item = inflater.inflate(R.layout.error_page, null);
                item.setMinimumHeight(35);
                break;
    	}
        return(item);
    }
}