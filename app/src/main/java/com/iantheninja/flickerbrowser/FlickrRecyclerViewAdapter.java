package com.iantheninja.flickerbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;


public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrImageViewHolder>{
    private List<Photo> mPhotosList;
    private Context mContext;

    //constructor
    public FlickrRecyclerViewAdapter(Context context, List<Photo> photosList){
        this.mContext = context;
        this.mPhotosList = photosList;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // view object to inflate with the browse layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);
        //flickrimageviewholder class object
        FlickrImageViewHolder flickrImageViewHolder = new FlickrImageViewHolder(view);

        return flickrImageViewHolder;
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        Photo photoItem = mPhotosList.get(position);
        //using picasso to display the images
        Picasso.with(mContext).load(photoItem.getmImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);

        holder.title.setText(photoItem.getmTitle());

    }

    @Override
    public int getItemCount() {
        //fuck!!!..ternary operators give mind fucks
        return (null != mPhotosList? mPhotosList.size() : 0);
    }
}
