package com.example.moham.hatledawa.Model;

/**
 * Created by moham on 22/02/2017.
 */

public class Doctor extends User {
    private String pharmacyPhone;
    private String pharmacyLisence;

    public String getPharmacyPhone() {
        return pharmacyPhone;
    }

    public void setPharmacyPhone(String pharmacyPhone) {
        this.pharmacyPhone = pharmacyPhone;
    }

    public String getPharmacyLisence() {
        return pharmacyLisence;
    }

    public void setPharmacyLisence(String pharmacyLisence) {
        this.pharmacyLisence = pharmacyLisence;
    }
}
