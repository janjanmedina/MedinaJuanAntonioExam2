package com.juanantonio.medina.medinajuanantonioexam2;

public class Student {
    public String firstName;
    public String lastName;
    public long average;

    public Student() {

    }

    public Student(String firstName, String lastName, long average) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.average = average;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getAverage() {
        return average;
    }
}
