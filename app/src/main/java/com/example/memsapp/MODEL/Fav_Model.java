package com.example.memsapp.MODEL;

public class Fav_Model {
    String joke,ans;

    public Fav_Model() {
    }

    public Fav_Model(String joke, String ans) {
        this.joke = joke;
        this.ans = ans;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
