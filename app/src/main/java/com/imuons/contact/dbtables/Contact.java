package com.imuons.contact.dbtables;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Contact")
public class Contact extends Model {
    @Column(name = "contactId",unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long contactId;

    @Column(name = "contactName")
    public String  contactName;

    @Column(name = "contactNumber")
    public String contactNumber;

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    @Column(name = "contactPhoto")
    public String contactPhoto;

    @Column (name = "isFav")
    public  boolean isFav;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Column (name = "isDeleted")
    public  boolean isDeleted;
}
