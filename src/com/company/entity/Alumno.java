package com.company.entity;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Alumno {

    private int codigo;
    private String nombre;
    private float nota1;
    private float nota2;
    private char apto;


    public Alumno(){}

    public Alumno(int codigo, String nombre, float nota1, float nota2) {
        this.codigo = codigo;

        StringBuilder sb = new StringBuilder(nombre);
        sb.setLength(25);

        this.nombre = sb.toString();
        this.nota1 = nota1;
        this.nota2 = nota2;
        setApto();
    }

    private void setApto(){
        if (nota1 < 4.5 || nota2 < 4.5)
            apto = 'N';
        else
            apto = 'S';
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        StringBuilder sb = new StringBuilder(nombre);
        sb.setLength(25);
        this.nombre = sb.toString();
    }

    public float getNota1() {
        return nota1;
    }

    public void setNota1(float nota1) {
        this.nota1 = nota1;
        setApto();
    }

    public float getNota2() {
        return nota2;
    }

    public void setNota2(float nota2) {
        this.nota2 = nota2;
        setApto();
    }

    public char getApto() {
        return apto;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "codigo=" + codigo +
                ", nombre=" + nombre +
                ", nota1=" + nota1 +
                ", nota2=" + nota2 +
                ", apto=" + apto +
                '}';
    }

    public void write(RandomAccessFile raf){

        try {
            raf.writeInt(codigo);
            raf.writeChars(nombre);
            raf.writeFloat(nota1);
            raf.writeFloat(nota2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(RandomAccessFile raf){

        try {
            setCodigo(raf.readInt());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 25; i++) {
                sb.append(raf.readChar());
            }
            setNombre(sb.toString());
            setNota1(raf.readFloat());
            setNota2(raf.readFloat());

            setApto();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int size(){
       return Integer.BYTES + Float.BYTES*2 + 25*2;
    }

    public static Alumno ALUMNO_VOID(){

        return new Alumno(0,"void",0,0);
    }

    public static boolean isVoid(Alumno alumno){
        return alumno.getCodigo() == 0;
    }
}
