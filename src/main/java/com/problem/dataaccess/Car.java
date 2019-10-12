package com.problem.dataaccess;

public class Car {
    private String color;
    private String registrationNo;

    public Car(String color, String registrationNo) {
        this.color = color;
        this.registrationNo = registrationNo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }
}
