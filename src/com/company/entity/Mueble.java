package com.company.entity;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Mueble {

    private int codigo;
    private String nombre;
    private int stockMin;
    private int stockMax;
    private int stockAct;
    private double precio;

    public static int size(){
        return Integer.BYTES*4 + 25*2 + Double.BYTES;
    }

    public Mueble (){}

    public Mueble(int codigo, String nombre, int stockMin, int stockMax, int stockAct, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.stockMin = stockMin;
        this.stockMax = stockMax;
        this.stockAct = stockAct;
        this.precio = precio;
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
        this.nombre = nombre;
    }

    public int getStockMin() {
        return stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }

    public int getStockMax() {
        return stockMax;
    }

    public void setStockMax(int stockMax) {
        this.stockMax = stockMax;
    }

    public int getStockAct() {
        return stockAct;
    }

    public void setStockAct(int stockAct) {
        this.stockAct = stockAct;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void write(RandomAccessFile raf){

        try {
            raf.writeInt(codigo);
            raf.writeChars(nombre);
            raf.writeInt(stockMin);
            raf.writeInt(stockMax);
            raf.writeInt(stockAct);
            raf.writeDouble(precio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(RandomAccessFile raf){

        try {
            setCodigo(raf.readInt());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 25; i++){

                sb.append(raf.readChar());
            }
            setNombre(sb.toString());
            setStockMin(raf.readInt());
            setStockMax(raf.readInt());
            setStockAct(raf.readInt());
            setPrecio(raf.readDouble());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Mueble{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", stockMin=" + stockMin +
                ", stockMax=" + stockMax +
                ", stockAct=" + stockAct +
                ", precio=" + precio +
                '}';
    }
}
