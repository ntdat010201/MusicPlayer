package com.example.musicmp3java.fragment.home.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SongModel implements Parcelable {
    String title;
    Uri uri;
    Bitmap imageSong;
    int size;
    int duration;

    String path;


    public SongModel(String title, Uri uri, Bitmap imageSong, int size, int duration,String path) {
        this.title = title;
        this.uri = uri;
        this.imageSong = imageSong;
        this.size = size;
        this.duration = duration;
        this.path = path;
    }

    protected SongModel(Parcel in) {
        title = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
        imageSong = in.readParcelable(Bitmap.class.getClassLoader());
        size = in.readInt();
        duration = in.readInt();
        path = in.readString();
    }

    public static final Creator<SongModel> CREATOR = new Creator<SongModel>() {
        @Override
        public SongModel createFromParcel(Parcel in) {
            return new SongModel(in);
        }

        @Override
        public SongModel[] newArray(int size) {
            return new SongModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bitmap getImageSong() {
        return imageSong;
    }

    public void setImageSong(Bitmap imageSong) {
        this.imageSong = imageSong;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

        parcel.writeString(title);
        parcel.writeParcelable(uri, i);
        parcel.writeParcelable(imageSong, i);
        parcel.writeInt(size);
        parcel.writeInt(duration);
        parcel.writeString(path);
    }

}
