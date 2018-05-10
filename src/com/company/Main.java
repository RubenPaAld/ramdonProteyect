package com.company;

import com.company.control.Control;
import com.company.utilities.In;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class Main {

    public static void main(String[] args) {
        try {
            Control control = new Control(new RandomAccessFile("muebles.dat","rw"));
            control.menu();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
