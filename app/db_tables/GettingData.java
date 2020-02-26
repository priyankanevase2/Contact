package com.itsyuj.user.healthcare.db.db_tables;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "GettingData")
public class GettingData extends Model {
    @Column(name = "ques_id")
    public String ques_id;

    @Column(name = "que_option_id")
    public String que_option_id;



}
