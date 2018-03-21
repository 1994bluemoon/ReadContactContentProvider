package vinova.henry.com.readcontactcontentprovider.home;

import java.util.List;

import vinova.henry.com.readcontactcontentprovider.models.Contact;

public interface IHomeContract {
    interface IHomePresenter{
        List<Contact> refreshContactData();
    }
    interface IHomeView{
        void showGetContactOk(List<Contact> contacts);
    }

}
