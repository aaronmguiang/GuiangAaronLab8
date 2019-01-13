package com.guiang.aaron;

public class Record {
    String age;
    String gender;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Record(String age, String gender) {
        this.age = age;
        this.gender = gender;
    }
}

