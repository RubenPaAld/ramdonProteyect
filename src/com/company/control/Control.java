package com.company.control;

import com.company.entity.Alumno;
import com.company.utilities.In;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Control {

    private RandomAccessFile raf;
    private String url;

    public Control(String url){
        this.url = url;
        try {
            File f = new File(url);

            if (!f.isFile()){
                this.raf = new RandomAccessFile(url,"rw");
                guardarRegistro(Alumno.ALUMNO_VOID());
                cerrarArchivo();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void cerrarArchivo(){
        try {
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirArchivo(String mode){

        try {
            raf = new RandomAccessFile(url,mode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int numRegistros() {

        try {
            return (int) raf.length()/Alumno.size();

        } catch (IOException e) {
            return -1;
        }
    }

    private void posicionarPunteroAlFinal(){
        try {
            raf.seek(raf.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void posicionarPunteroAlInicio(){

        try {
            raf.seek(Alumno.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void posicionarPuntero(long pos){

        try {
            raf.seek(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Alumno getRegistro(int pos){

        posicionarPuntero(Alumno.size()*pos);
        Alumno alumno = new Alumno();
        alumno.read(raf);
        return alumno;
    }

    private void guardarRegistro(Alumno alumno){
        alumno.write(raf);
    }

    private void borrarAlumno(int codigo){
        posicionarPuntero(codigo*Alumno.size());

        Alumno.ALUMNO_VOID().write(raf);
    }

    private void actualizarAlumno(Alumno alumno){
        posicionarPuntero(alumno.getCodigo()*Alumno.size());
        alumno.write(raf);
    }

    public void menu() {

        exit:
        while(true){

            System.out.println("!======MENU======!");
            System.out.println("\t1 - ALTAS");
            System.out.println("\t2 - BAJAS");
            System.out.println("\t3 - VER REGISTRO");
            System.out.println("\t4 - LISTADO");
            System.out.println("\t5 - MODIFICAR REGISTRO");
            System.out.println("\t6 - SALIR");
            System.out.print("\t\tOpcion: ");

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

    private void bajas() {

        abrirArchivo("rw");
        Alumno alumno = solicitarAlumno();

        borrarAlumno(alumno.getCodigo());
    }

    private void altas() {

        exit:
        while(true){

            System.out.println("======MENU ALTAS======");
            System.out.println("\t1 - REGISTRAR ALUMNO");
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

        abrirArchivo("rw");

        int codigo;

        do{
            System.out.print("inserte el codigo: ");
            codigo = In.getInt(1,Integer.MAX_VALUE);

            if (codigo < numRegistros()){
                if (Alumno.isVoid(getRegistro(codigo)))
                    break;
                else
                    System.out.println("El registro ya esta en uso.");
            }else{
                break;
            }
        }while(true);

        System.out.print("inserte el nombre (maximo 25 caracteres): ");
        String nombre = In.getString(25);

        System.out.print("inserte la nota 1: ");
        int nota1 = In.getInt(1,10);
        System.out.print("inserte la nota 2: ");
        int nota2 = In.getInt(1,10);

        Alumno alumno = new Alumno(codigo,nombre,nota1,nota2);

        Alumno av = Alumno.ALUMNO_VOID();
        posicionarPuntero(numRegistros()*Alumno.size());
        while (numRegistros() < codigo){
            guardarRegistro(av);
        }
        posicionarPuntero(codigo*Alumno.size());
        guardarRegistro(alumno);
        cerrarArchivo();
    }

    private void listado(){

        abrirArchivo("r");
        posicionarPunteroAlInicio();

        int n = numRegistros();
        System.out.println("numero de registros: " + n);

        for (int i = 1; i < n; i++){

            Alumno alumno = getRegistro(i);

            System.out.println(i+ "----------------");
            System.out.println(alumno.toString());
            System.out.println("-------------------");
        }
    }

    private Alumno solicitarAlumno(){
        int n = numRegistros();
        do {
            System.out.print("inserte el codigo del alumno  (1, " + (n-1) + "): ");
            int codigo = In.getInt(1,n-1);
            Alumno alumno = getRegistro(codigo);

            if (Alumno.isVoid(alumno))
                System.out.println("no existe el alumno selecionado");
            else
                return alumno;

        }while (true);
    }

    private void verRegistro() {

        abrirArchivo("r");

        Alumno alumno = solicitarAlumno();

        System.out.println(alumno);
    }

    private void modificarRegistro() {

        abrirArchivo("rw");

        Alumno alumno = solicitarAlumno();

        exit:
        while (true){

            System.out.println("\t1 - nombre: "+alumno.getNombre());
            System.out.println("\t2 - nota 1: "+alumno.getNota1());
            System.out.println("\t3 - nota 2: "+alumno.getNota2());
            System.out.println("\t4 - salir");
            System.out.print("\t\t opcion: ");

            int op = In.getInt(1,4);

            switch (op) {
                case 1:
                    System.out.print("nuevo nombre: ");
                    alumno.setNombre(In.getString(25));
                    break;
                case 2:
                    System.out.print("nueva nota 1: ");
                    alumno.setNota1(In.getFloat(1,10));
                    break;
                case 3:
                    System.out.print("nueva nota 2: ");
                    alumno.setNota2(In.getFloat(1,10));
                    break;
                case 4: break exit;
            }
        }
        actualizarAlumno(alumno);
    }

}
