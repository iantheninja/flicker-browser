package com.iantheninja.flickerbrowser;

import android.os.Bundle;

public class ViewPhotoDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_photo_details);
        activateToolbarWithHomeEnabled();

    }

}
