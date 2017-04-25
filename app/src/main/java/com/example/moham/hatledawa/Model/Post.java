package com.example.moham.hatledawa.Model;

import java.util.UUID;

/**
 * Created by moham on 20/02/2017.
 */

public class Post {
    private String uuid;
    private String posterName;
    private String posterImageUrl;
    private String drugName;
    private String drugType;
    private String drugCon;
    private String doctorsNumber;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getDoctorsNumber() {
        return doctorsNumber;
    }

    public void setDoctorsNumber(String doctorsNumber) {
        this.doctorsNumber = doctorsNumber;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDrugCon() {
        return drugCon;
    }

    public void setDrugCon(String drugCon) {
        this.drugCon = drugCon;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    public void setPosterImageUrl(String posterImageUrl) {
        this.posterImageUrl = posterImageUrl;
    }
}
