package com.company.control;

import com.company.entity.Mueble;
import com.company.utilities.In;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Control {

    private RandomAccessFile raf;


    private int numRegistros() {

        try {
            return (int) raf.length()/Mueble.size();
        } catch (IOException e) {
            return -1;
        }
    }

    public Control(RandomAccessFile raf) {
        this.raf = raf;
    }

    public void menu() {

        exit:
        while(true){

            System.out.println("======MENU======");
            System.out.println("\t1 - ALTAS");
            System.out.println("\t2 - BAJAS");
            System.out.println("\t3 - VER REGISTRO");
            System.out.println("\t4 - LISTADO");
            System.out.println("\t5 - MODIFICAR REGISTRO");
            System.out.println("\t6 - SALIR");
            System.out.print("\t\t Opcion: ");

            switch (In.getInt(1,6)){

                case 1: altas();
                        break;
                case 2: bajas();
                        break;
                case 3: verRegistro();
                        break;
                case 4: listado();
                        break;
                case 5: modificarRegistro();
                        break;
                case 6: break exit;
            }
        }
        System.out.println("Fin del programa");
    }

    private void modificarRegistro() {

        System.out.print("inserte el numero del registro a modificar: ");
        int pos = In.getInt(0,numRegistros());

        Mueble e = getRegistro(pos);

        exit:
        while (true){

            System.out.println("\t1 - codigo: "+e.getCodigo());
            System.out.println("\t2 - nombre: "+e.getNombre());
            System.out.println("\t3 - stock minimo: "+e.getStockMin());
            System.out.println("\t4 - stock maximo: "+e.getStockMax());
            System.out.println("\t5 - stock activo: "+e.getStockAct());
            System.out.println("\t6 - precio: "+e.getPrecio());
            System.out.println("\t7 - salir");
            System.out.print("\t\t opcion: ");

            int op = In.getInt(1,7);

            switch (op) {
                case 1:
                    System.out.print("nuevo codigo:");
                    e.setCodigo(In.getInt());
                    break;
                case 2:
                    System.out.print("nuevo nombre: ");
                    StringBuilder sb = new StringBuilder(In.getString(25));
                    sb.setLength(25);
                    e.setNombre(sb.toString());
                    break;
                case 3:
                    System.out.print("nuevo stock minimo: ");
                    e.setStockMin(In.getInt(0,e.getStockMax()));
                    break;
                case 4:
                    System.out.print("nuevo stock maximo: ");
                    e.setStockMax(In.getInt(e.getStockMin(),Integer.MAX_VALUE));
                    break;
                case 5:
                    System.out.print("nuevo stock activo: ");
                    e.setStockMax(In.getInt(e.getStockMin(),e.getStockMax()));
                    break;
                case 6:
                    System.out.print("nuevo precio: ");
                    e.setPrecio(In.getDouble(0,Double.MAX_VALUE));
                    break;
                case 7: break exit;
            }
        }
        try {
            raf.seek(Mueble.size()* (pos-1));
            e.write(raf);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void listado() {

        System.out.println("===LISTADO DE MUEBLES===");
        int registros = numRegistros();
        Mueble mueble = new Mueble();

        try {
            raf.seek(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < registros; i++){

            mueble.read(raf);

            System.out.println("Mueble - " + (i+1));
            System.out.println("\tcodigo: "+mueble.getCodigo());
            System.out.println("\tnombre: "+mueble.getNombre());
            System.out.println("\tstock minimo: "+mueble.getStockMin());
            System.out.println("\tstock maximo: "+mueble.getStockMax());
            System.out.println("\tstock activo: "+mueble.getStockAct());
            System.out.println("\tprecio: "+mueble.getPrecio());
        }
        System.out.println();
    }

    private void verRegistro() {

        System.out.println("inserte el numero del registro a consultar: ");
        int pos = In.getInt(0,numRegistros());

        Mueble e = getRegistro(pos);

        System.out.println("\tcodigo: "+e.getCodigo());
        System.out.println("\tnombre: "+e.getNombre());
        System.out.println("\tstock minimo: "+e.getStockMin());
        System.out.println("\tstock maximo: "+e.getStockMax());
        System.out.println("\tstock activo: "+e.getStockAct());
        System.out.println("\tprecio: "+e.getPrecio());

    }

    private Mueble getRegistro(int pos){

        try {
            raf.seek(Mueble.size()*(pos-1));

            Mueble mueble = new Mueble();
            mueble.read(raf);
            return mueble;

        } catch (IOException e) {
            return null;
        }
    }

    private void cambiarRegistro(int pos, Mueble mueble){

        try {
            raf.seek(Mueble.size()*(pos-1));
            mueble.write(raf);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bajas() {

        System.out.print("inserte el numero del registro a borrar: ");
        int pos = In.getInt(0,numRegistros());
        int registros = numRegistros()-1;
        for (int i = pos; i <= registros; i++){

            cambiarRegistro(i,getRegistro(i+1));
        }
        try {
            raf.setLength(raf.length()-Mueble.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void altas() {

        exit:
        while(true){

            System.out.println("======MENU ALTAS======");
            System.out.println("\t1 - REGISTRAR MUEBLE");
            System.out.println("\t2 - SALIR");
            System.out.print("\t\t Opcion: ");

            switch (In.getInt(1,2)){

                case 1: registrarMueble();
                        break;
                case 2: break exit;
            }
            System.out.println();
        }
    }

    private void registrarMueble() {

        try {
            raf.seek(raf.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("inserte el codigo: ");
        int codigo = In.getInt();
        System.out.print("inserte el nombre (maximo 25 caracteres): ");
        StringBuilder nombre = new StringBuilder(In.getString(25));
        nombre.setLength(25);
        System.out.print("inserte el stock minimo: ");
        int stockMin = In.getInt(0,Integer.MAX_VALUE);
        System.out.print("inserte el stock maximo: ");
        int stockMax = In.getInt(stockMin,Integer.MAX_VALUE);
        System.out.print("inserte el stock activo: ");
        int stockAct = In.getInt(stockMin,stockMax);
        System.out.print("inserte el precio: ");
        double precio = In.getDouble(0,Double.MAX_VALUE);
        System.out.println();
        Mueble mueble = new Mueble(codigo,nombre.toString(),stockMin,stockMax,stockAct,precio);
        mueble.write(raf);
    }
}
