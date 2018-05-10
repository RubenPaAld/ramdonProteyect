package com.company.utilities;

import java.util.Scanner;

public class In {

    private static Scanner escaner = new Scanner(System.in);

    /**
     * Solicita por teclado un numero de tipo int comprendido entre min y max, ambos incluidos. En caso de introducir un valor incorrecto
     * o un numero que desborde el tamaño del tipo int se le volvera a solicitar uno nuevo hasta introducir uno valido.
     * @param min el minimo valor incluido en el rango.
     * @param max el maximo valor incluido en el ranfo.
     * @return un numero de tipo int entre el minimo y el maximo ambos incluidos que fue introducido por teclado.
     */
    public static int getInt (int min, int max) {

        while (true){

            try{
                String s = escaner.nextLine();
                int n = Integer.parseInt(s);
                if (n >= min && n <= max)
                    return n;
                else throw new NumberFormatException();

            }catch (NumberFormatException e){
                System.out.println("Inserte un numero entre " + min + " y " + max + " : ");
            }
        }
    }

    /**
     * retorna un numero de tipo int. En caso de introducir un valor incorrecto o un numero que desborde el tamaño del tipo int
     * se volveria a solicitar uno nieivo hasta introducr uno valido.
     * @return un numero de tipo int que fue introducido por teclado.
     */
    public static int getInt(){
        while (true){

            try{
                String s = escaner.nextLine();
                int n = Integer.parseInt(s);
                return n;

            }catch (NumberFormatException e){
                System.out.print("Inserte un numero entero entre " + Integer.MIN_VALUE + " y " + Integer.MAX_VALUE+ " : ");
            }
        }
    }

    /**
     * Solicita por teclado un numero de tipo double comprendido entre min y max, ambos incluidos. En caso de introducir un valor incorrecto
     * o un numero que desborde el tamaño del tipo int se le volvera a solicitar uno nuevo hasta introducir uno valido.
     * @param min el minimo valor incluido en el rango.
     * @param max el maximo valor incluido en el ranfo.
     * @return un numero de tipo double entre el minimo y el maximo ambos incluidos que fue introducido por teclado.
     */
    public static double getDouble (double min, double max) {

        while (true){

            try{
                String s = escaner.nextLine();
                s = s.replace(',','.');
                double n = Double.parseDouble(s);

                if (n >= min && n <= max)
                    return n;
                else throw new NumberFormatException();

            }catch (NumberFormatException e){
                System.out.println("Inserte un numero entre " + min + " y " + max);
            }
        }
    }

    /**
     * retorna un numero de tipo double. En caso de introducir un valor incorrecto o un numero que desborde el tamaño del tipo int
     * se volveria a solicitar uno nieivo hasta introducr uno valido.
     * @return un numero de tipo double que fue introducido por teclado.
     */
    public static double getDouble(){
        while (true){

            try{
                String s = escaner.nextLine();
                s = s.replace(',','.');
                System.out.println(s);
                double n = Double.parseDouble(s);
                return n;

            }catch (NumberFormatException e){
                System.out.print("Inserte un numero entero entre " + Double.MIN_VALUE + " y " + Double.MAX_VALUE+ " : ");
            }
        }
    }


    /**
     * solicita por teclado un string.
     * @return un string con el texto introducido por teclado
     */
    public static String getString(){
        return escaner.nextLine();
    }

    /**
     * solicita por teclado una cadena de una longitud no superior al numero pasado como parametro
     * @param maxLength la longitud maxima de la cadena
     * @return una cadena de longitud no superior a la pasada por parametro
     */
    public static String getString(int maxLength){

        while (true){

            String s = escaner.nextLine();

            if (s.length() <= maxLength)
                return s;
            else
                System.out.print("inserte una cadena de " + maxLength + " o menos caracteres: ");
        }
    }

    /**
     * solicita por teclado un string que valide la expresion regular pasada por parametro, en caso de no ser correcta
     * imprimira el texto
     * @param pattern la expresion regular a validar
     * @param text el texto mostrado por pantalla en caso de no ser valido el texto introducido por teclado
     * @return un string que cumple la expresion regular.
     */
    public static String getString(String pattern, String text){

        while (true){

            String s = escaner.nextLine();

            if (s.matches(pattern))
                return s;
            else
                System.out.print(text+": ");
        }
    }
}
