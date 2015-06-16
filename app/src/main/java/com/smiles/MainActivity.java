package com.smiles;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private Firebase ref;
    private ArrayList<SingleRow> notifList;
    private Menu optionsMenu;
    FirebaseAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setRefreshActionButtonState(false);

        Parse.initialize(this, "PkhBXQHKFDqNVNgsnYrNYVLOiFfdWkPpVCx1173K", "8iVNR9wZ51g0iun8uuoOhuJxWOCOgTik1oIAwfaO");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.header, null);
        listView.addHeaderView(header, null, false);
        notifList = new ArrayList<>();

        Firebase.setAndroidContext(this);
        ref = new Firebase("https://camp-mosaic.firebaseio.com/");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                notifList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //Log.d("result", (String) data.child("msg").getValue());
                    SingleRow foo = new SingleRow();
                    foo.setMessage((String) data.child("msg").getValue());
                    foo.setDetail((String) data.child("detail").getValue());
                    if (data.child("date").getValue() != null) {
                        foo.setDate((String) data.child("date").getValue());
                    }
                    //Log.d("date", foo.getDate());
                    notifList.add(foo);
                }
                Log.d("result", notifList.get(0).getMessage());
                Collections.reverse(notifList);
                setListData();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        listView.setOnItemClickListener(this);
    }

    void setListData() {
        listAdapter = new FirebaseAdapter(notifList, this);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("position", position + "");
        listAdapter.changeFlag(position-1);
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        ImageView read = (ImageView) view.findViewById(R.id.read_icon);
//        int visible= read.getVisibility();
//        if (visible == View.VISIBLE) {
//            read.setVisibility(View.GONE);
//        } else {
//            read.setVisibility(View.VISIBLE);
//        }
//        Log.d("position", position + "");
//        SingleRow temp = notifList.get(position-1);
//        if (!temp.getFlag()) {
//            temp.setFlag(true);
//        }
//    }
//
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_refresh:
//                setListData();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void setRefreshActionButtonState(final boolean refreshing) {
//        if (optionsMenu != null) {
//            final MenuItem refreshItem = optionsMenu
//                    .findItem(R.id.action_refresh);
//            if (refreshItem != null) {
//                if (refreshing) {
//                    refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
//                } else {
//                    refreshItem.setActionView(null);
//                }
//            }
//        }
//    }
}



