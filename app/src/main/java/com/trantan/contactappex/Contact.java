package com.trantan.contactappex;

public class Contact {
    private String mNameContact;
    private String mNumberContact;
    private boolean mIsFavarite;

    public Contact(String nameContact, String numberContact, boolean isFavarite) {
        mNameContact = nameContact;
        mNumberContact = numberContact;
        mIsFavarite = isFavarite;
    }

    public String getNameContact() {
        return mNameContact;
    }

    public void setNameContact(String nameContact) {
        mNameContact = nameContact;
    }

    public String getNumberContact() {
        return mNumberContact;
    }

    public void setNumberContact(String numberContact) {
        mNumberContact = numberContact;
    }

    public boolean isFavarite() {
        return mIsFavarite;
    }

    public void setFavarite(boolean favarite) {
        mIsFavarite = favarite;
    }
}
