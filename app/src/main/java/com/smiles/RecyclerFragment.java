package com.smiles;


import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.LinkedList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerFragment extends Fragment implements RecyclerAdapter.ClickListener {


    public static final String TAG = "RecyclerFragment";
    public View rootView;
    public RecyclerView recyclerView;
    public RecyclerAdapter adapter;
    public LinkedList<SingleRow> notifList;
    private Firebase ref;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainActivity activity;

    public RecyclerFragment() {
        // Required empty public constructor
    }

    void setListData() {
        if (!recyclerView.getAdapter().equals(adapter)) {
            recyclerView.swapAdapter(adapter,false);
        }
        adapter.notifyDataSetChanged();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
//        if (recyclerView.isAttachedToWindow()) {
//            Log.d("animating", "yes");
//        } else if (!recyclerView.isAttachedToWindow()) {
//            Log.d("animating", "no");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        setHasOptionsMenu(true);
        activity = (MainActivity) getActivity();

//        activity.findViewById(R.id.collapsing_toolbar).getLayoutParams().height = 200;
        ImageView collapsingImage = (ImageView) activity.findViewById(R.id.collapsing_image);
        collapsingImage.setImageResource(R.drawable.banner2);

        swipeRefreshLayout =
                (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setListData();
                Snackbar.make(getActivity().findViewById(R.id.main_linear_layout), "Refreshed", Snackbar.LENGTH_LONG).show();
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        notifList = new LinkedList<>();
        LinkedList<SingleRow> load = new LinkedList<>();
        load.add(new SingleRow("Loading...","Loading...","Loading..."));
        RecyclerAdapter dadapter = new RecyclerAdapter(getActivity(), load);
        adapter = new RecyclerAdapter(getActivity(), notifList);
        recyclerView.setAdapter(dadapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }
        adapter.setClickListener(this);

        Firebase.setAndroidContext(getActivity());
        ref = new Firebase("https://camp-mosaic.firebaseio.com/");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                swipeRefreshLayout.setRefreshing(true);
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

        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

     menu.clear();
     inflater.inflate(R.menu.menu_main, menu);
     super.onCreateOptionsMenu(menu, inflater);
      }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void itemClicked(View view, int position) {
        //Snackbar.make(getActivity().findViewById(R.id.main_linear_layout), notifList.get(position).getMessage(), Snackbar.LENGTH_SHORT).show();
        notifList.get(position).setFlag();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setListData();
    }
}
