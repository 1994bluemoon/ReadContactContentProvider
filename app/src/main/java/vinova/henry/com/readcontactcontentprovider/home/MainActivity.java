package vinova.henry.com.readcontactcontentprovider.home;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vinova.henry.com.readcontactcontentprovider.R;
import vinova.henry.com.readcontactcontentprovider.models.Contact;

public class MainActivity extends AppCompatActivity implements IHomeContract.IHomeView {

    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;
    ContactAdapter contactAdapter;
    HomePresenterImp presenterImp;
    List<Contact> contacts;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.bt_showcontact)
    Button btShowcontact;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Contact");

        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));


        getPermissionToReadUserContacts();
        presenterImp = new HomePresenterImp(this);

        contactAdapter = new ContactAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvHome.setLayoutManager(manager);
        rvHome.setAdapter(contactAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        //MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToReadUserContacts() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            } else {
                // Show our own UI to explain to the user why we need to read the contacts
                // before actually requesting the permission and showing the default UI
            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSIONS_REQUEST);
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("permiss", "request OK");
            } else {
                // showRationale = false if user clicks Never Ask Again, otherwise true
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS);

                if (showRationale) {
                    // do something here to handle degraded mode
                } else {
                    Log.d("permiss", "request fail");
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void showGetContactOk(List<Contact> contacts) {
        Log.d("asyntask", "ok");
        contacts = contacts;
        contactAdapter.setContacts(contacts);
        contactAdapter.notifyDataSetChanged();
        btShowcontact.setVisibility(View.GONE);
        rvHome.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bt_showcontact)
    public void onViewClicked() {
        new ReadContactAsync(this, this, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        btShowcontact.setText("Please wait...");
    }
}
