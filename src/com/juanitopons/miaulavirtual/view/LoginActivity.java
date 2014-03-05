package com.juanitopons.miaulavirtual.view;


import java.io.IOException;
import java.net.SocketTimeoutException;

import com.juanitopons.miaulavirtual.R;
import com.juanitopons.miaulavirtual.model.BadDataException;
import com.juanitopons.miaulavirtual.model.BaseActivity;
import com.juanitopons.miaulavirtual.model.ConnectionDetector;
import com.juanitopons.miaulavirtual.model.MyModel;
import com.juanitopons.miaulavirtual.model.MyRequest;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {
    private MyModel model;
    
    EditText user;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        PreferenceManager.setDefaultValues(this.getApplicationContext(), R.xml.apppreferences, false);
        model = MyModel.getInstance();
        model.setContext(this.getApplicationContext());
        Log.d("model", model.toString());
        
        if(model.getUser().equals("0")){
            setContentView(R.layout.activity_login);
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        
    }
    
    public void setLoginContentView() {
        setContentView(R.layout.activity_login);
    }
    
    public void sendMessage(View view) {
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        //setContentView(R.layout.load);
        model.setUser(user.getText().toString());
        model.setPass(pass.getText().toString());
        
        startActivity(new Intent(this, MainActivity.class));
        /**
         * AulaVirtualActivity
        try {
            request.doPostUrl1();
        } catch (SocketTimeoutException e) {
            setLoginContentView();
            super.showError(MyModel.NOINTERNET);
        } catch (IOException e) {
            setLoginContentView();
            super.showError(MyModel.RANDOM);
        } catch (BadDataException e) {
            setLoginContentView();
            super.showError(MyModel.BADDATA);
        }
        **/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        return true;
    }

}
