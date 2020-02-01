package com.project.shadowing;

public class Sub {

    int key;
    int start;
    int num;
    int time;
    String sub;


    public Sub(int key, int start, int num, int time, String sub) {

        this.key = key;
        this.start = start;
        this.num = num;
        this.time = time;
        this.sub = sub;
    }


    public int time() {
        return this.time;
    }

    public String sub() {
        return this.sub;
    }
}
