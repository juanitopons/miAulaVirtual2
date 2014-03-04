package com.juanitopons.miaulavirtual.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.juanitopons.miaulavirtual.R;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
        case R.id.preferencias:
            //startActivity(new Intent(this, SettingsActivity.class));;
            break;
        case R.id.acercade:
            //startActivity(new Intent(this, AboutActivity.class));
            break;
        }
        return true;
    }
    
    public void showError(int error) {
        switch(error) {
            case 0:
                Toast.makeText(getBaseContext(),getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(getBaseContext(),getString(R.string.toast_5), Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getBaseContext(),getString(R.string.bad_data), Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getBaseContext(),getString(R.string.toast_5), Toast.LENGTH_LONG).show();
                break;
        }
    }
}
