package com.trantan.contactappex;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String CALL_URI = "tel:%s";
    private List<Contact> mContacts;

    public RecyclerAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_recycler_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(mContacts.get(i));
    }

    @Override
    public int getItemCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mContactName;
        private TextView mContactNumber;
        private ImageView mFavoriteContact;
        private ImageView mCall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setUpUi();
        }

        private void setUpUi() {
            mContactName = itemView.findViewById(R.id.text_name_contact);
            mContactNumber = itemView.findViewById(R.id.text_number_contact);
            mFavoriteContact = itemView.findViewById(R.id.image_star);
            mCall = itemView.findViewById(R.id.image_call);
        }

        public void setData(Contact contact) {
            mContactName.setText(contact.getNameContact());
            mContactNumber.setText(contact.getNumberContact());
            if (contact.isFavarite()) mFavoriteContact.setImageResource(R.drawable.ic_stared);
            else mFavoriteContact.setImageResource(R.drawable.ic_star);
            mCall.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(String.format(CALL_URI, mContactNumber.getText())));
            itemView.getContext().startActivity(intent);
        }
    }
}
