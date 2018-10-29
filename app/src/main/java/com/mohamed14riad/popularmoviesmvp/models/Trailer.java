package com.mohamed14riad.popularmoviesmvp.models;

import com.google.gson.annotations.SerializedName;

import com.mohamed14riad.popularmoviesmvp.utils.AppConstants;

public class Trailer {
    @SerializedName("id")
    private String trailerId;
    @SerializedName("iso_639_1")
    private String iso_639_1;
    @SerializedName("iso_3166_1")
    private String iso_3166_1;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;
    @SerializedName("size")
    private int size;
    @SerializedName("type")
    private String type;

    public Trailer() {
    }

    public static String getUrl(Trailer trailer) {
        if (trailer.getSite().equalsIgnoreCase("YouTube")) {
            return String.format(AppConstants.YOUTUBE_VIDEO_URL, trailer.getKey());
        } else {
            return null;
        }
    }

    public static String getThumbnailUrl(Trailer trailer) {
        if (trailer.getSite().equalsIgnoreCase("YouTube")) {
            return String.format(AppConstants.YOUTUBE_THUMBNAIL_URL, trailer.getKey());
        } else {
            return null;
        }
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
