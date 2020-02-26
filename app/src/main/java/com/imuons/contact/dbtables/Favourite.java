package com.imuons.contact.dbtables;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Favourite")
public class Favourite extends Model {
    @Column(name = "contactId",unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long contactId;

    @Column(name = "contactName")
    public String  contactName;

    @Column(name = "contactNumber")
    public String contactNumber;

    @Column(name = "contactPhoto")
    public String contactPhoto;

    @Column (name = "isFav")
    public  boolean isFav;

}

