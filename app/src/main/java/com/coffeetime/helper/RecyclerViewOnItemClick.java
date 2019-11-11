package com.coffeetime.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by arief on 25-Apr-16.
 */
public class RecyclerViewOnItemClick implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener listener;

    public interface OnItemClickListener{
        public void OnItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerViewOnItemClick(Context context, OnItemClickListener listener){
        this.listener = listener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if(childView != null && listener != null && mGestureDetector.onTouchEvent(e)){
            listener.OnItemClick(childView, rv.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
