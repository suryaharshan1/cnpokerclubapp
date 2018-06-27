package com.nunnaguppala.suryaharsha.cnpokerclub.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user")
public class UserEntity {
    @PrimaryKey
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String registrationStatus;

    private String smallPicture;

    private String mediumPicture;

    private String largePicture;

    public UserEntity(int id, String firstName, String lastName, String email, String registrationStatus, String smallPicture, String mediumPicture, String largePicture) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.registrationStatus = registrationStatus;
        this.smallPicture = smallPicture;
        this.mediumPicture = mediumPicture;
        this.largePicture = largePicture;
    }

    public int getId() {
        return id;
    }

    public UserEntity setId(int id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public UserEntity setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
        return this;
    }

    public String getSmallPicture() {
        return smallPicture;
    }

    public UserEntity setSmallPicture(String smallPicture) {
        this.smallPicture = smallPicture;
        return this;
    }

    public String getMediumPicture() {
        return mediumPicture;
    }

    public UserEntity setMediumPicture(String mediumPicture) {
        this.mediumPicture = mediumPicture;
        return this;
    }

    public String getLargePicture() {
        return largePicture;
    }

    public UserEntity setLargePicture(String largePicture) {
        this.largePicture = largePicture;
        return this;
    }
}
