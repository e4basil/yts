
package com.example.basi.test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Torrent {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("quality")
    @Expose
    private String quality;
    @SerializedName("seeds")
    @Expose
    private int seeds;
    @SerializedName("peers")
    @Expose
    private int peers;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("size_bytes")
    @Expose
    private Long sizeBytes;
    @SerializedName("date_uploaded")
    @Expose
    private String dateUploaded;
    @SerializedName("date_uploaded_unix")
    @Expose
    private int dateUploadedUnix;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getSeeds() {
        return seeds;
    }

    public void setSeeds(int seeds) {
        this.seeds = seeds;
    }

    public int getPeers() {
        return peers;
    }

    public void setPeers(int peers) {
        this.peers = peers;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public int getDateUploadedUnix() {
        return dateUploadedUnix;
    }

    public void setDateUploadedUnix(int dateUploadedUnix) {
        this.dateUploadedUnix = dateUploadedUnix;
    }
}
