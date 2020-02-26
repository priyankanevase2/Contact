package com.itsyuj.user.healthcare.db.db_tables;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by user on 4/5/18.
 */

@Table(name = "Cart")
public class Cart extends Model {
    @Column(name = "pl_Id")
    public String pI_Id;

    @Column(name = "panel_label")
    public String  panel_label;

    @Column(name = "panel_price")
    public double price;

    @Column(name = "qty")
    public int qty;

    @Column(name = "total")
    public double total;

}
