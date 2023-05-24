package com.blogspot.fdbozzo;

class Author {
    private String name;
    private String firstSurname;
    private String bio;

    // Getters and setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "com.example.Author{" +
                "name='" + name + '\'' +
                ", firstSurname='" + firstSurname + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}