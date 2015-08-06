package com.smiles;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;

import java.util.LinkedList;


public class SecondaryActivity extends AppCompatActivity {

    Toolbar toolbar;
    public RecyclerView recyclerView;
    public RecyclerAdapter adapter;
    public LinkedList<SingleRow> notifList;
    private Firebase ref;

//    void setListData() {
//        adapter = new RecyclerAdapter(this, notifList);
//        recyclerView.setAdapter(adapter);
//        adapter.setClickListener(this);
//        adapter.notifyDataSetChanged();
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        } else {
//            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("1001 Smiles");

        getFragmentManager().beginTransaction().replace(R.id.container, new RecyclerFragment(), RecyclerFragment.TAG).commit();

//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//
//        notifList = new LinkedList<>();
//
//        Firebase.setAndroidContext(this);
//        ref = new Firebase("https://camp-mosaic.firebaseio.com/");
//
//        ref.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                SingleRow foo = new SingleRow();
//                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
//                foo.setMessage(newPost.get("msg").toString());
//                foo.setDetail(newPost.get("detail").toString());
//                if (newPost.get("date") == null) {
//                    foo.setDate("Some point in time");
//                } else {
//                    foo.setDate(newPost.get("date").toString());
//                }
//                notifList.add(0, foo);
//                setListData();
//                //Log.d("childAdd","date" + newPost.get("date"));
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_secondary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void itemClicked(View view, int position) {
//        notifList.get(position).setFlag();
//        adapter.notifyDataSetChanged();
//    }
}
