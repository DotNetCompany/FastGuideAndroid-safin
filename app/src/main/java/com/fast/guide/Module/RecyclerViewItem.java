package com.fast.guide.Module;

public class RecyclerViewItem {
    private int ID;
    private String Name;
    private  String Image;

    public RecyclerViewItem(int id, String name, String image){
        this.ID = id;
        this.Name = name;
        this.Image = image;
    }


    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }
}
