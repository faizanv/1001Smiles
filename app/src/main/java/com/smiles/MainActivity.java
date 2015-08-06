package com.smiles;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Firebase ref;
    private LinkedList<SingleRow> notifList;
    private Menu optionsMenu;
    FirebaseAdapter listAdapter;
    Toolbar toolbar;
    Intent intent;
    Intent chooser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setRefreshActionButtonState(false);

        SharedPreferences pref = getSharedPreferences("my_prefs", MODE_PRIVATE);
        String camp = pref.getString("camp", null);
        if (camp == null) {
            startActivity(new Intent(this, CampSelectionActivity.class));
        } else {
            ParsePush.subscribeInBackground(camp);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setIcon(R.drawable.ic_popup_sync_1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Magic!", Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(R.id.main_linear_layout), "Magic!", Snackbar.LENGTH_LONG).show();
            }
        });
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("1001 Smiles");
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.black));

//        getFragmentManager().beginTransaction().replace(R.id.container, new CampSelectionFragment()).commit();
        getFragmentManager().beginTransaction().replace(R.id.container, new RecyclerFragment(), RecyclerFragment.TAG).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            String[] to = {"mosaic@jubileemonuments.org"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Message from app user");
            intent.putExtra(Intent.EXTRA_TEXT, "- Sent from 1001Smiles App");
            intent.setType("message/rfc822");
            chooser = Intent.createChooser(intent, "Send Message");
            startActivity(chooser);
            return true;
        }
//        if (item.getItemId() == R.id.recycle) {
//            getFragmentManager().beginTransaction().replace(R.id.container, new RecyclerFragment(), RecyclerFragment.TAG).addToBackStack(null).commit();
//        }

//        if (item.getItemId() == R.id.new_activity) {
//            intent = new Intent(this, SecondaryActivity.class);
//            startActivity(intent);
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            } else {
            super.onBackPressed();
           }
        }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().show();
    }
}



