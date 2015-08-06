package com.smiles;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.LinkedList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    public static final String TAG = "ListFragment";
    View rootView;
    private ListView listView;
    private Firebase ref;
    private LinkedList<SingleRow> notifList;
    private Menu optionsMenu;
    FirebaseAdapter listAdapter;

    public ListFragment() {
        // Required empty public constructor
    }

    void setListData() {
        listAdapter = new FirebaseAdapter(notifList, getActivity());
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView);
        LayoutInflater inflater2 = getActivity().getLayoutInflater();
        View header = inflater2.inflate(R.layout.header, null);
        listView.addHeaderView(header, null, false);
        notifList = new LinkedList<>();

        Firebase.setAndroidContext(getActivity());
        ref = new Firebase("https://camp-mosaic.firebaseio.com/");

        listAdapter = new FirebaseAdapter(notifList, getActivity());

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SingleRow foo = new SingleRow();
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                foo.setMessage(newPost.get("msg").toString());
                foo.setDetail(newPost.get("detail").toString());
                if (newPost.get("date") == null) {
                    foo.setDate("Some point in time");
                } else {
                    foo.setDate(newPost.get("date").toString());
                }
                notifList.add(0,foo);
                setListData();
                //Log.d("childAdd","date" + newPost.get("date"));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView read = (ImageView) view.findViewById(R.id.read_icon);
                int visible= read.getVisibility();
                if (visible == View.VISIBLE) {
                    notifList.get(position-1).setFlag(false);
                } else {
                    notifList.get(position-1).setFlag(true);

                }
                listAdapter.notifyDataSetChanged();

            }
        });


        return rootView;
    }
}
