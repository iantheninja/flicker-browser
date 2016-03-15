package com.iantheninja.flickerbrowser;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by ian on 14/03/16.
 */
public class BaseActivity extends AppCompatActivity{
    private Toolbar mToolbar;

    protected Toolbar activateToolbar(){
        if (mToolbar == null){
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            if (mToolbar != null){
                setSupportActionBar(mToolbar);
            }
        }
        return mToolbar;
    }
}
