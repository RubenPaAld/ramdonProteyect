package com.company;

import com.company.control.Control;
import com.company.utilities.In;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class Main {

    public static void main(String[] args) {

        Control control = new Control("alumnos.dat");
        control.menu();

    }
}
