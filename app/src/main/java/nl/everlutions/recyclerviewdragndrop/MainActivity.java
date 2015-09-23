package nl.everlutions.recyclerviewdragndrop;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import nl.everlutions.recyclerviewdragndrop.dialogs.FDialogDelete;


public class MainActivity extends AppCompatActivity implements MyClickListener, View.OnClickListener{

    public final String TAG = this.getClass().getSimpleName();
    private RecyclerView mRecyclerView;
    private MyHoverAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager;
    private ArrayList<MyObject> mData;
    private int mDraggedViewPos;
    private int addItem=0;
    private boolean flag;
    private EditText cantHab;
    private Button confirmation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cantHab = (EditText) findViewById(R.id.cantHabit);
        findViewById(R.id.okCantHabit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoom(Integer.parseInt(cantHab.getText().toString()));
                findViewById(R.id.containerInputIni).setVisibility(View.GONE);
            }
        });



        findViewById(R.id.container).setTag("container-fuera");
        findViewById(R.id.containerInside).setTag("container-dentro");
        findViewById(R.id.containerInside).setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        flag=false;
                        Log.e("drop-dentro", v.getTag().toString());
                        break;
                }
                return true;
            }
        });
        findViewById(R.id.container).setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        Log.e("drop-fuera", v.getTag().toString());
                        if (flag) {
                            View view = (View) event.getLocalState();


                            ViewGroup owner = (ViewGroup) view.getParent();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            new FDialogDelete(owner).show(fragmentManager, "FDialogDelete");
                        }
                        flag = false;


                        break;
                }
                return true;
            }
        });
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setTag("recycler");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setHorizontalScrollBarEnabled(true);
        mGridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);

        mData = new ArrayList<MyObject>();
       /* for (int i = 0; i < 3; i++) {
            mData.add(new MyObject("pos " + i, i % 2 == 1 ? Color.YELLOW : Color.WHITE));
            this.addItem=3;
        }*/

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new MyHoverAdapter(this, this, mData, mItemDragListener);
        mRecyclerView.setAdapter(mAdapter);

    }

    public View.OnDragListener mItemDragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

                int pos = mRecyclerView.getChildPosition(v);
                if (pos == mDraggedViewPos) {
                    return false;
                }

            switch (event.getAction()) {
                case  DragEvent.ACTION_DRAG_STARTED:
                    Log.e("started",v.getTag().toString());
                    flag=true;
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                        flag=false;
                        Log.e("entered",v.getTag().toString());
                        //Log.e(TAG, "ENTERED: " + pos);
                        if (pos > mDraggedViewPos) {
                            flag=false;
                            Collections.rotate(mData.subList(mDraggedViewPos, pos+1), -1);
                        } else {
                            flag=false;
                            Collections.rotate(mData.subList(0, mDraggedViewPos+1), 1);
                        }
                        int oldpos = mDraggedViewPos;
                        mDraggedViewPos = pos;
                        mAdapter.notifyItemMoved(oldpos, mDraggedViewPos);break;

            }

            return true;
        }
    };

    public void showMsg(String string) {
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v, int pos, boolean longClick) {
        if(longClick==false){
            Log.e("activity","entro a 1 clic");
          /*  FragmentManager fragmentManager = getSupportFragmentManager();
            DialogAlert dialogo = new DialogAlert();
            dialogo.show(fragmentManager, "tagAlerta");*/
            //showMsg(String.valueOf(longClick));

                startActivity(new Intent(this, DetailActivity.class));


        }else {
            showMsg(String.valueOf(longClick));
            mDraggedViewPos = pos;
            ClipData data = ClipData.newPlainText("myLabel","what");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.setTag(pos);
            v.startDrag(data, shadowBuilder, v, 0);
//        v.setVisibility(View.INVISIBLE);
//        mAdapter.notifyItemMoved(0, 3);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                addRoom();
                break;
        }
    }
    public void addRoom(){
        mData.add(new MyObject("pos " + addItem, addItem % 2 == 1 ? Color.YELLOW : Color.WHITE));
        mAdapter.notifyDataSetChanged();
        addItem++;
    }
    public void addRoom(int cant){
        for (int i=addItem;i<(addItem+cant);i++){
            mData.add(new MyObject("pos " + i, i % 2 == 1 ? Color.YELLOW : Color.WHITE));
        }
        mAdapter.notifyDataSetChanged();
        addItem+=cant;
    }

    public class MyObject {
        public String name;
        public int color;

        public MyObject(String name, int color) {
            this.name = name;
            this.color = color;
        }
    }
}
