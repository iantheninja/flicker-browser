package com.iantheninja.flickerbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by ian on 30/03/16.
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener{

    public static interface onItemClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }
    private AdapterView.OnItemClickListener mListener;
    private GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, AdapterView.OnItemClickListener listener){
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()){
            public boolean onSingleTapUp(MotionEvent e){
                return true;
            }

            public void onLongPress(MotionEvent e){
                //gets position in recycler view
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)){
                    //mListener.onItemClick(childView, );
                }
            }
        };
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }



}
