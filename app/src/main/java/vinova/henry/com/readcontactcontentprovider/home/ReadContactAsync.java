package vinova.henry.com.readcontactcontentprovider.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import vinova.henry.com.readcontactcontentprovider.models.Contact;

public class ReadContactAsync extends AsyncTask<Void, Void, List<Contact>>{

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    private HomePresenterImp homePresenterImp;
    private IHomeContract.IHomeView iHomeView;

    ReadContactAsync(Context context, IHomeContract.IHomeView homeView, Activity activity) {
        this.activity = activity;
        this.iHomeView = homeView;
        homePresenterImp = new HomePresenterImp(context);
    }

    @Override
    protected List<Contact> doInBackground(Void... voids) {
        return homePresenterImp.refreshContactData();
    }

    @Override
    protected void onPostExecute(List<Contact> contacts) {
        super.onPostExecute(contacts);
        iHomeView.showGetContactOk(contacts);
    }
}
