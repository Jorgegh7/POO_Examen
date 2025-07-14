package servicio;

import modelo.adimnistracion.BaseDatosVehiculos;
import modelo.boleta.Boleta;
import modelo.usuario.Usuario;
import modelo.vehiculo.Vehiculo;
import modelo.vehiculo.VehiculoCarga;
import modelo.vehiculo.VehiculoPasajeros;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ArrendarVehiculo {

    public void arrendarVehiculo(List<Vehiculo> listaVehiculo, Usuario usuario){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa la Patente del Vehiculo");
        String patente = scanner.nextLine();
        boolean patenteEncontrada = false;
        for(Vehiculo vehiculo: listaVehiculo){
            if(vehiculo.getPatente().equalsIgnoreCase(patente.strip())){
                patenteEncontrada = true;
                System.out.println("Ingresa el numero de dias de arriendo: ");
                try{
                    vehiculo.setDiasArriendo(scanner.nextInt());
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Ingresa un numero entero: " + e.getMessage());
                }
                usuario.agregarVehiculo(vehiculo);
                usuario.setFechaArriendo(LocalDate.now());
                System.out.println(vehiculo.toString());

                Boleta boleta = new Boleta();
                double total = boleta.boletaUnica(vehiculo);

                System.out.println("Valor arriendo: " + total);

                try{
                    cambiarEstadoVehiculo(patente);
                    archivarBoleta(usuario,vehiculo, total);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if(!patenteEncontrada){
        System.out.println("La patente ingresada No es válida.");
        }

    }

    private void archivarBoleta(Usuario usuario, Vehiculo vehiculo, double total) throws InterruptedException {

        Thread hiloArchivarBoleta = new Thread(() -> {
            try(FileWriter writer = new FileWriter("boletas_arriendo.txt", true)){
                String linea = usuario.getNombre() + "," +
                        usuario.getApellido() + "," +
                        vehiculo.getTipoVehiculo() + "," +
                        vehiculo.getMarca() + "," +
                        vehiculo.getModelo() + "," +
                        vehiculo.getPatente() + "," +
                        String.valueOf(vehiculo.getCostoArriendoDia()) + "," +
                        String.valueOf(vehiculo.getDescuentoArriendo()) + "," +
                        String.valueOf(vehiculo.getDiasArriendo()) + "," +
                        String.valueOf(total) + ',' +
                        usuario.getFechaArriendo();

                writer.write('\n' + linea);
                writer.flush();

            }catch (IOException e){
                System.out.println("El archivo no ha sido encontrado: " + e.getMessage());
            }
        });
        hiloArchivarBoleta.start();
        hiloArchivarBoleta.join();
    }

    private void cambiarEstadoVehiculo(String patente) throws InterruptedException {

        HashMap<String, Vehiculo> vehiculos = new HashMap<>();
        BaseDatosVehiculos baseDatosVehiculos = new BaseDatosVehiculos(vehiculos);
        vehiculos= baseDatosVehiculos.listaVehiculos();

        vehiculos.get(patente).setEstado("Arrendado");

        try(FileWriter writer = new FileWriter("vehiculos.csv")){

            writer.write("Tipo;Marca;Modelo;Patente;2200;65000;7;0;Estado");
            String linea="";
            for(Vehiculo vehiculo: vehiculos.values()){
                if(vehiculo.getTipoVehiculo().equalsIgnoreCase("carga")){
                    VehiculoCarga vehiculoCarga =new VehiculoCarga();
                    vehiculoCarga = (VehiculoCarga) vehiculo;
                    linea = vehiculoCarga.getTipoVehiculo()+ ";" +
                            vehiculoCarga.getMarca()  + ";" +
                            vehiculoCarga.getModelo() + ";" +
                            vehiculoCarga.getPatente() + ";" +
                            vehiculoCarga.getCapacidadCarga() + ";" +
                            String.valueOf(vehiculoCarga.getCostoArriendoDia()) + ";" +
                            String.valueOf(vehiculoCarga.getDescuentoArriendo()) + ";" +
                            vehiculoCarga.getDiasArriendo() + ";" +
                            vehiculoCarga.getEstado();

                    writer.write('\n' +linea);


                }else if (vehiculo.getTipoVehiculo().equalsIgnoreCase("pasajeros")){
                    VehiculoPasajeros vehiculoPasajeros =new VehiculoPasajeros();
                    vehiculoPasajeros = (VehiculoPasajeros) vehiculo;
                    linea = vehiculoPasajeros.getTipoVehiculo()+ ";" +
                            vehiculoPasajeros.getMarca() + ";" +
                            vehiculoPasajeros.getModelo() + ";" +
                            vehiculoPasajeros.getPatente() + ";" +
                            vehiculoPasajeros.getNumeroPasajeros() + ";" +
                            String.valueOf(vehiculoPasajeros.getCostoArriendoDia()) + ";" +
                            String.valueOf(vehiculoPasajeros.getDescuentoArriendo()) + ";" +
                            vehiculoPasajeros.getDiasArriendo() + ";" +
                            vehiculoPasajeros.getEstado();

                    writer.write('\n' + linea);
                }
            }
            writer.flush();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Base de datos actualizada");
    }

    public void devolverVehiculo() throws InterruptedException {
        HashMap<String, Vehiculo> vehiculos = new HashMap<>();
        BaseDatosVehiculos baseDatosVehiculos = new BaseDatosVehiculos(vehiculos);
        vehiculos= baseDatosVehiculos.listaVehiculos();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa la Patente del Vehiculo que deseas devolver formato AA-AA-00: ");
        String patente = scanner.nextLine();
        while(patente.length() !=8){
            System.out.println("Ingresa un nuevo valor de Patente ");
            patente = scanner.nextLine();
        }
        while(!vehiculos.containsKey(patente.toUpperCase())){
            System.out.println("La patente ingresada no se encuentra en el registro de vehiculos");
            System.out.println("Ingresa una nueva patente");
            patente = scanner.nextLine();
        }

        String estado = vehiculos.get(patente).getEstado();
        if(estado.equalsIgnoreCase("arrendado")){

            vehiculos.get(patente).setEstado("Disponible");

            try(FileWriter writer = new FileWriter("vehiculos.csv")){

                writer.write("Tipo;Marca;Modelo;Patente;2200;65000;7;0;Estado");
                String linea="";
                for(Vehiculo vehiculo: vehiculos.values()){
                    if(vehiculo.getTipoVehiculo().equalsIgnoreCase("carga")){
                        VehiculoCarga vehiculoCarga =new VehiculoCarga();
                        vehiculoCarga = (VehiculoCarga) vehiculo;
                        linea = vehiculoCarga.getTipoVehiculo()+ ";" +
                                vehiculoCarga.getMarca()  + ";" +
                                vehiculoCarga.getModelo() + ";" +
                                vehiculoCarga.getPatente() + ";" +
                                vehiculoCarga.getCapacidadCarga() + ";" +
                                String.valueOf(vehiculoCarga.getCostoArriendoDia()) + ";" +
                                String.valueOf(vehiculoCarga.getDescuentoArriendo()) + ";" +
                                vehiculoCarga.getDiasArriendo() + ";" +
                                vehiculoCarga.getEstado();

                        writer.write('\n' +linea);


                    }else if (vehiculo.getTipoVehiculo().equalsIgnoreCase("pasajeros")){
                        VehiculoPasajeros vehiculoPasajeros =new VehiculoPasajeros();
                        vehiculoPasajeros = (VehiculoPasajeros) vehiculo;
                        linea = vehiculoPasajeros.getTipoVehiculo()+ ";" +
                                vehiculoPasajeros.getMarca() + ";" +
                                vehiculoPasajeros.getModelo() + ";" +
                                vehiculoPasajeros.getPatente() + ";" +
                                vehiculoPasajeros.getNumeroPasajeros() + ";" +
                                String.valueOf(vehiculoPasajeros.getCostoArriendoDia()) + ";" +
                                String.valueOf(vehiculoPasajeros.getDescuentoArriendo()) + ";" +
                                vehiculoPasajeros.getDiasArriendo() + ";" +
                                vehiculoPasajeros.getEstado();

                        writer.write('\n' + linea);
                    }
                }
                writer.flush();

            }catch (IOException e){
                System.out.println(e.getMessage());
            }
            System.out.println("Base de datos actualizada");
            System.out.println("Vehiculo devuelto de forma correcta");

        }else{
            System.out.println("El vehiculo no se encuentra Arrendado");
        }
    }
}
