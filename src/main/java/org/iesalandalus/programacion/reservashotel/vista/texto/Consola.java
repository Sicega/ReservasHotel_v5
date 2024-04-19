package org.iesalandalus.programacion.reservashotel.vista.texto;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Iterator;

public class Consola {

    private Consola(){

    }

    public static void mostrarMenu() { // M�todo est�tico para mostrar el men� de opciones

        for (Opcion opcion : Opcion.values()) { //con values devuelvo un array que contiene las constantes del enumerado en el orden declarado

            System.out.println(opcion); //Saca por consola las opciones del enumerado
        }
    }

    public static Opcion elegirOpcion() {

        int opcionElegida; //Variable para almacenar el n�mero de la opci�n escogida

        do{
            System.out.print("Elige una opci�n: ");

            opcionElegida = Entrada.entero();

        }while(opcionElegida<0 || opcionElegida > Opcion.values().length); //Verifico que la opci�n escogida est� dentro del enumerado

        return Opcion.values()[opcionElegida];

    }

    public static Huesped leerHuesped() { //M�todo para introducir los datos del hu�sped

        String nombre;

        do{ //Verifico que no sea nulo ni contenga solo espacios
            System.out.print("Introduce el nombre del hu�sped: ");

            nombre = Entrada.cadena();

        }while(nombre == null || nombre.isBlank());

        String dni;
        String ER_DNI = "([0-9]{8})([A-Za-z])";

        do {

            System.out.print("Introduce el DNI del hu�sped: ");

            dni = Entrada.cadena();

        }while(dni == null || dni.isBlank() || !dni.matches(ER_DNI));

        String correo;
        String ER_CORREO = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$";

        do {

            System.out.print("Introduce el correo del hu�sped: ");

            correo = Entrada.cadena();

        }while(correo == null || correo.isBlank() || !correo.matches(ER_CORREO));

        String telefono;
        String ER_TELEFONO = "[0-9]{9}";

        do {
            System.out.print("Introduce el tel�fono del hu�sped: ");

            telefono = Entrada.cadena();

        }while(telefono==null || telefono.isBlank() || !telefono.matches(ER_TELEFONO));


        System.out.print("Introduce la fecha de nacimiento del hu�sped: ");

        LocalDate fechaNacimiento = Consola.leerFecha();

        return new Huesped(nombre, dni, correo, telefono, fechaNacimiento);
    }

    //M�todo para leer al hu�sped por DNI

    public static Huesped leerHuespedPorDni() {

        System.out.print("Introduce el DNI del hu�sped: ");

        String dni = Entrada.cadena();

        return new Huesped("nombre", dni, "correo@gmail.com", "623456789", LocalDate.of(2000,4,4));
    }

    public static LocalDate leerFecha() { //M�todo para validar la fecha introducida

        String fecha = null;

        boolean fechaValida = false;

        while (!fechaValida) {

            System.out.println("Formato dd/MM/yyyy");

            fecha = Entrada.cadena();



            if (fecha.matches("[0-3][0-9]/[0-1][0-9]/[1-2][0-9]{3}"))
                fechaValida = true;

        }

        DateTimeFormatter formato= DateTimeFormatter.ofPattern(Huesped.FORMATO_FECHA);

        LocalDate fechaFormato=LocalDate.parse(fecha, formato);

        return fechaFormato;
    }

    public static Habitacion leerHabitacion() {

        System.out.print("Introduce el n�mero de planta de la habitaci�n: ");

        int planta = Entrada.entero();

        System.out.print("Introduce el n�mero de puerta de la habitaci�n: ");

        int puerta = Entrada.entero();

        System.out.print("Introduce el precio de la habitaci�n: ");

        double precio = Entrada.realDoble();

        System.out.println("Introduce el tipo de habitacion: ");

        TipoHabitacion tipoHabitacion = leerTipoHabitacion();

        if (tipoHabitacion.equals(TipoHabitacion.SIMPLE)) {
            return new Simple(planta, puerta, precio);
        }else if(tipoHabitacion.equals(TipoHabitacion.DOBLE)){
            System.out.println("�Cuantas camas individuales desea? (Escoja entre 0 y 2)");
            int camasIndividuales=Entrada.entero();
            System.out.println("�Cuantas camas dobles desea? (Escoja 0 o 1)");
            int camasDobles=Entrada.entero();
            return new Doble(planta, puerta, precio, camasIndividuales, camasDobles);
        } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
            System.out.println("�Cu�ntos ba�os desea? Escoja entre 1 y 2");
            int numBanos=Entrada.entero();
            System.out.println("�Cu�ntas camas individuales desea? Escoja entre 0 y 3");
            int camasIndividuales=Entrada.entero();
            System.out.println("�Cu�ntas camas dobles desea? Escoja entre 0 y 1");
            int camasDobles=Entrada.entero();
            return new Triple(planta, puerta, precio, numBanos, camasIndividuales,camasDobles);
        } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)){
            String jacuzzi;

            do{
                System.out.println("�Desea Jacuzzi en la habitaci�n? Introduzca si o no");
                jacuzzi=Entrada.cadena();
            }while(!jacuzzi.equalsIgnoreCase("si") && !jacuzzi.equalsIgnoreCase("no"));

            boolean jacuzziSuite=false;
            if(jacuzzi.equalsIgnoreCase("si")) {
                jacuzziSuite=true;
            }
            return new Suite(planta,puerta,precio,2,jacuzziSuite);
        }else{
            return null;
        }
    }

    public static Habitacion leerHabitacionPorIdentificador() {

        System.out.print("Introduce la planta de la habitaci�n: ");

        int planta = Entrada.entero();

        System.out.println("Introduce la puerta de la habitaci�n: ");

        int puerta = Entrada.entero();

       try {

           TipoHabitacion tipoHabitacion = leerTipoHabitacion();

           if (tipoHabitacion.equals(TipoHabitacion.SIMPLE)) {
               return new Simple(planta, puerta, 50);
           }else if(tipoHabitacion.equals(TipoHabitacion.DOBLE)){
               return new Doble(planta, puerta, 50, 2, 0);
           } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
               return new Triple(planta, puerta, 50, 2, 2,1);
           } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)){
               return new Suite(planta,puerta,50,2,true);
           }

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());

        } return null;
    }

    public static TipoHabitacion leerTipoHabitacion() {

        System.out.println("Tipos de habitaci�n:");

        Iterator<TipoHabitacion> iterador = EnumSet.allOf(TipoHabitacion.class).iterator();

        while (iterador.hasNext()) {

            System.out.println(iterador.next());
        }

        System.out.print("Elige un tipo de habitaci�n: ");

        int tipoElegido;

        do {

            tipoElegido = Entrada.entero();

        } while (tipoElegido < 0 || tipoElegido >= TipoHabitacion.values().length);


        return TipoHabitacion.values()[tipoElegido];
    }

    public static Regimen leerRegimen() {

        System.out.println("Tipos de r�gimen:");

        Iterator<Regimen> iterador = EnumSet.allOf(Regimen.class).iterator();

        while (iterador.hasNext()) {

            System.out.println(iterador.next());
        }

        System.out.print("Elige un tipo de r�gimen: ");

        int regimenElegido = Entrada.entero();

        return Regimen.values()[regimenElegido];
    }

    public static int leerNumeroPersonas() {

        int numeroPersonas;

        // Bucle do-while para garantizar un n�mero de personas v�lido

        do {
            System.out.print("Introduce el n�mero de personas: ");

            numeroPersonas = Entrada.entero();

        }while(numeroPersonas<=0);

        return numeroPersonas;
    }



    public static LocalDateTime leerFechaHora(String mensaje) {


        while (!mensaje.matches("[0-3][0-9]/[01][0-9]/[0-9]{4} [0-2][0-9]:[0-5][0-9]:[0-5][0-9]")){
            System.out.print(mensaje + "No es un patr�n v�lido. Int�ntalo de nuevo. (dd/MM/yyyy hh:mm:ss");
            mensaje = Entrada.cadena();
        }

        // Creo un formateador de fecha y hora con el formato de la constante

        DateTimeFormatter formato = DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA);

        return LocalDateTime.parse(mensaje, formato); // Convierto la cadena de fecha y hora a un objeto LocalDateTime utilizando el formateador
    }


}
