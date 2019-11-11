package com.coffeetime.API;

import com.coffeetime.model.Orderan;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderanList {

    @SerializedName("data")

    private ArrayList<Orderan> orderanList;

    public ArrayList<Orderan> getOrderanListArrayList() {
        return orderanList;
    }

    public void setOrderanList(ArrayList<Orderan> orderanArrayList) {
        this.orderanList = orderanArrayList;
    }
}
