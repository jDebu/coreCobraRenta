package nl.everlutions.recyclerviewdragndrop;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyHoverAdapter extends RecyclerView.Adapter<MyHoverAdapter.ViewHolder> {
    private final Context mCtx;

    public final String TAG = this.getClass().getSimpleName();
    private MyClickListener mMyClickListener;
    private ArrayList<MainActivity.MyObject> mData;
    private View.OnDragListener mItemDragListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener {
        private final ImageView image;
        private final RelativeLayout root;
        public MyClickListener mClickListener;
        public TextView textfield;

        public ViewHolder(View v, View.OnDragListener mItemDragListener) {
            super(v);
            root = (RelativeLayout)v;
            textfield = (TextView) v.findViewById(R.id.form_fieldlabel);
            image = (ImageView) v.findViewById(R.id.form_image);
            textfield.setOnClickListener(this);
            textfield.setOnLongClickListener(this);
            root.setOnDragListener(mItemDragListener);

        }


        @Override
        public void onClick(View v) {
            Log.e("clic","please");
            mClickListener.onClick(v, getPosition(), false);

        }

        @Override
        public boolean onLongClick(View v) {
            mClickListener.onClick(v, getPosition(), true);
            return true;
        }

        /*
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);

        }
        final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            public void onLongPress(MotionEvent e) {
                Log.e("", "Longpress detected");
                //mClickListener.onClick(v, getPosition(), true);
                //return true;
            }
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
            // event when double tap occurs
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                float x = e.getX();
                float y = e.getY();

                Log.e("Double Tap", "Tapped at: (" + x + "," + y + ")");

                return true;
            }
        });*/
    }

    public MyHoverAdapter(Context ctx, MyClickListener clickListener, ArrayList<MainActivity.MyObject> data, View.OnDragListener itemDragListener) {
        mCtx = ctx;
        mMyClickListener = clickListener;
        mData = data;
        mItemDragListener = itemDragListener;
    }

    @Override
    public MyHoverAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        ViewHolder vh = new ViewHolder(v, mItemDragListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mClickListener = mMyClickListener;
        holder.textfield.setText("DATA POS: " + mData.get(position).name);
        holder.textfield.setBackgroundColor(mData.get(position).color);
        holder.root.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}