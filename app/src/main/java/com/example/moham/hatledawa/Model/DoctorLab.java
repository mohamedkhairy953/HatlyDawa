package com.example.moham.hatledawa.Model;

/**
 * Created by moham on 22/02/2017.
 */
public class DoctorLab {
    private static DoctorLab ourInstance = new DoctorLab();

    public static DoctorLab getInstance() {
        return ourInstance;
    }

    private DoctorLab() {
    }
}
