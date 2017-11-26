package com.example.rakeshvasal.myapplication.Utilities;

import java.util.Comparator;

/**
 * Created by prabhat.yadav on 4/1/2016.
 */
public class CompareArrayList implements Comparator<Integer> {


    @Override
    public int compare(Integer o1, Integer o2) {
        return (o1>o2 ? 1 : (o1==o2 ? 0 : -1));
    }
}