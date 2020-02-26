package com.itsyuj.user.healthcare.db.db_tables;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "CheckAnswer")
public class CheckAnswer extends Model {
    @Column(name = "checkans")
    public boolean checkans;
}
