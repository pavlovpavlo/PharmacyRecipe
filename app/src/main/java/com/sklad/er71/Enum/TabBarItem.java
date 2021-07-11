package com.sklad.er71.Enum;

public class TabBarItem {
    private final int imageViewID;
    private final int textViewID;
    private final int imageActiveCode;
    private final int imageNotActiveCode;

    public TabBarItem(int imageViewID, int textViewID, int imageActiveCode, int imageNotActiveCode) {
        this.imageViewID = imageViewID;
        this.textViewID = textViewID;
        this.imageActiveCode = imageActiveCode;
        this.imageNotActiveCode = imageNotActiveCode;
    }

    public int getImageViewID() {
        return imageViewID;
    }

    public int getTextViewID() {
        return textViewID;
    }

    public int getImageActiveCode() {
        return imageActiveCode;
    }

    public int getImageNotActiveCode() {
        return imageNotActiveCode;
    }
}
