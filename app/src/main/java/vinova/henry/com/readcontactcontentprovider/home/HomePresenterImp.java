package vinova.henry.com.readcontactcontentprovider.home;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vinova.henry.com.readcontactcontentprovider.models.Contact;

public class HomePresenterImp implements IHomeContract.IHomePresenter{

    private Context mContext;

    HomePresenterImp(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public List<Contact> refreshContactData() {
        List<Contact> contacts = new ArrayList<>();

        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        assert cursor != null;
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                String phoneNumber = null;

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}, null);

                    assert pCur != null;
                    while (pCur.moveToNext()) {
                        int phoneType = pCur.getInt(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.TYPE));
                        phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        switch (phoneType) {
                            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                Log.e(name + "(mobile number)", phoneNumber);
                                break;
                            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                Log.e(name + "(home number)", phoneNumber);
                                break;
                            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                Log.e(name + "(work number)", phoneNumber);
                                break;
                            case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                                Log.e(name + "(other number)", phoneNumber);
                                break;
                            default:
                                break;
                        }
                    }
                    pCur.close();
                }
                Contact contact = new Contact(name, phoneNumber);
                contacts.add(contact);
            }
        } else Log.d("contact", "data not found");
        cursor.close();

        return contacts;
    }
}
