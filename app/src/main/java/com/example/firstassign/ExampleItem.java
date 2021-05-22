package com.example.firstassign;


public class ExampleItem {
    private String mImageUrl;
    private String mAuthorName;

    public ExampleItem(String mImageUrl, String mAuthorName) {
        this.mImageUrl = mImageUrl;
        this.mAuthorName = mAuthorName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public void setmAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }
}
