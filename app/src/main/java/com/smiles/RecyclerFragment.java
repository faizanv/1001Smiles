package com.smiles;


import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    Map<Date, SingleRow> retrieved;
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
            Snackbar.make(getActivity().findViewById(R.id.main_linear_layout), "Refreshed", Snackbar.LENGTH_LONG).show();
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
                getFeed();
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        notifList = new LinkedList<>();
        retrieved = new HashMap<>();
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

        getFeed();

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
        setListData();
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

    private void getFeed() {
        swipeRefreshLayout.setRefreshing(true);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Feed");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (ParseObject obj: list) {
                    if (!retrieved.containsKey(obj.getCreatedAt())) {
                        SingleRow newRow = new SingleRow();
                        newRow.setMessage(obj.get("Post").toString());
//                        newRow.setDetail(obj.get("Type").toString());
                        newRow.setDate(obj.getCreatedAt().toString());
                        newRow.setType(obj.get("Type").toString());
                        notifList.add(newRow);
                        retrieved.put(obj.getCreatedAt(), newRow);
                    }
                }
                setListData();
            }
        });
    }
}
