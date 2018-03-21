package vinova.henry.com.readcontactcontentprovider.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vinova.henry.com.readcontactcontentprovider.R;
import vinova.henry.com.readcontactcontentprovider.models.Contact;

class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private Context mContext;
    private List<Contact> contacts;

    ContactAdapter(Context context) {
        this.mContext = context;
        contacts = new ArrayList<>();
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contact_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvContactName.setText(contacts.get(position).getName());
        holder.tvPhoneNumber.setText(contacts.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_contact_name)
        TextView tvContactName;
        @BindView(R.id.tv_phone_number)
        TextView tvPhoneNumber;
        @BindView(R.id.cv_contact)
        CardView cvContact;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
