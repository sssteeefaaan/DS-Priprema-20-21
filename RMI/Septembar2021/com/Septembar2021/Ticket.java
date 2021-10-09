package com.Septembar2021;

import java.io.Serializable;
import java.util.Vector;

public class Ticket implements Serializable{
    public int id;
    public Vector<Integer> numbers;
    public BingoCallback callback;

    public Ticket(int id, Vector<Integer> numbers, BingoCallback callback)
    {
        this.id = id;
        this.numbers = new Vector<>(numbers);
        this.callback = callback;
    }
}
