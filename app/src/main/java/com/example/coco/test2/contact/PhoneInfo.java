package com.example.coco.test2.contact;

/**
 * Created by Coco on 2018/11/13
 */
public class PhoneInfo {
    private String name;
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String sortKey;
    private int id;
    public PhoneInfo(String name, String number,String sortKey, int id) {
        this.name = name;
        this.number = number;
        this.sortKey = sortKey;
        this.id = id;
    }
}
