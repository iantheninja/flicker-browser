package com.iantheninja.flickerbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewPhotoDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_photo_details);
        activateToolbarWithHomeEnabled();

        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);

        //populate the views in the layout
        TextView photoTitle = (TextView) findViewById(R.id.photo_title);
        photoTitle.setText("Title: "+photo.getmTitle());

        TextView photoTags = (TextView) findViewById(R.id.photo_title);
        photoTags.setText("Tags: "+photo.getmTags());

        TextView photoAuthor = (TextView) findViewById(R.id.photo_title);
        photoAuthor.setText(photo.getmAuthor());

        ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
        Picasso.with(this).load(photo.getmLink())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(photoImage);
    }

}
