package modelo.adimnistracion;

import modelo.vehiculo.Vehiculo;
import modelo.vehiculo.VehiculoCarga;
import modelo.vehiculo.VehiculoPasajeros;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class AdministracionVehiculos implements Runnable {


    public ArrayList<Vehiculo> mostarListaCargo() throws InterruptedException {

        ArrayList<Vehiculo> listaCarga = new ArrayList<>();

        System.out.println("\n---------------------------------\n-     VEHICULOS CARGA     -\n---------------------------------");

        Thread hiloCargo = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader("vehiculos.csv"))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] campos = linea.split(";");

                    if (campos.length >= 9 && campos[0].toLowerCase().strip().equals("carga") && (campos[8].toLowerCase().equals("disponible"))) {

                        VehiculoCarga vehiculoCarga = new VehiculoCarga();

                        vehiculoCarga.setTipoVehiculo(campos[0]);
                        vehiculoCarga.setMarca(campos[1]);
                        vehiculoCarga.setModelo(campos[2]);
                        vehiculoCarga.setPatente(campos[3]);
                        vehiculoCarga.setCapacidadCarga(Integer.parseInt(campos[4]));
                        vehiculoCarga.setCostoArriendoDia(Double.parseDouble(campos[5]));
                        vehiculoCarga.setDescuentoArriendo(Double.parseDouble(campos[6]));
                        vehiculoCarga.setDiasArriendo(Integer.parseInt(campos[7]));
                        vehiculoCarga.setEstado(campos[8]);

                        listaCarga.add(vehiculoCarga);

                        System.out.println(vehiculoCarga.toString());
                    }
                }
            } catch (IOException e) {
                System.out.println("Archivo no disponible: " + e.getMessage());
            }
        });
        hiloCargo.start();
        hiloCargo.join();
        return listaCarga;
    }

    public ArrayList<Vehiculo> mostarListaPasajeros() throws InterruptedException {

        ArrayList<Vehiculo> listaPasajeros = new ArrayList<>();

        System.out.println("\n---------------------------------\n-     VEHICULOS PASAJEROS     -\n---------------------------------");

        Thread hiloPasajeros = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader("vehiculos.csv"))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] campos = linea.split(";");

                    if (campos.length >= 9 && campos[0].toLowerCase().strip().equals("pasajeros") && (campos[8].toLowerCase().equals("disponible"))) {

                        VehiculoPasajeros vehiculoPasajeros = new VehiculoPasajeros();

                        vehiculoPasajeros.setTipoVehiculo(campos[0]);
                        vehiculoPasajeros.setMarca(campos[1]);
                        vehiculoPasajeros.setModelo(campos[2]);
                        vehiculoPasajeros.setPatente(campos[3]);
                        vehiculoPasajeros.setNumeroPasajeros(Integer.parseInt(campos[4]));
                        vehiculoPasajeros.setCostoArriendoDia(Double.parseDouble(campos[5]));
                        vehiculoPasajeros.setDescuentoArriendo(Double.parseDouble(campos[6]));
                        vehiculoPasajeros.setDiasArriendo(Integer.parseInt(campos[7]));
                        vehiculoPasajeros.setEstado(campos[8]);

                        listaPasajeros.add(vehiculoPasajeros);

                        System.out.println(vehiculoPasajeros.toString());
                    }
                }
            } catch (IOException e) {
                System.out.println("Archivo no disponible: " + e.getMessage());
            }
        });
        hiloPasajeros.start();
        hiloPasajeros.join();
        return listaPasajeros;
    }

    private String verificarNoDuplicidadPatente() throws InterruptedException {

        BaseDatosVehiculos baseDatosVehiculos = new BaseDatosVehiculos();
        HashMap<String, Vehiculo> vehiculos = new HashMap<>();
        vehiculos = baseDatosVehiculos.listaVehiculos();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa la Patente del Vehiculo formato AA-AA-00: ");
        String patente = scanner.nextLine();
        while(patente.length() !=8){
            System.out.println("Ingresa un nuevo valor de Patente ");
            patente = scanner.nextLine();
        }
        while(vehiculos.containsKey(patente.toUpperCase())){
            System.out.println("La patente ingresada ya se encuentra en el registro de vehiculos");
            System.out.println("Ingresa una nueva patente");
            patente = scanner.nextLine();
        }
    return patente.toUpperCase();
    }

    public void deslistarVehiculo() throws InterruptedException {

        HashMap<String, Vehiculo> vehiculos = new HashMap<>();
        BaseDatosVehiculos baseDatosVehiculos = new BaseDatosVehiculos(vehiculos);
        vehiculos= baseDatosVehiculos.listaVehiculos();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa la Patente del Vehiculo que deseas deslistar formato AA-AA-00: ");
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
        vehiculos.remove(patente);

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
        System.out.println("El vehiculo se ha deslistado de forma correcta");
    }


    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresa el Tipo de Vehiculo que deseas listar: Carga/Pasajeros");
        String opcion = scanner.nextLine();

        if(opcion.strip().equalsIgnoreCase("carga")){
            VehiculoCarga nuevoVehiculoCarga = new VehiculoCarga();

            nuevoVehiculoCarga.setTipoVehiculo("Carga");

            System.out.print("Ingresa la Marca del Vehiculo: ");
            String marca = scanner.nextLine();
            nuevoVehiculoCarga.setMarca(marca);

            System.out.print("Ingresa la Modelo del Vehiculo: ");
            String modelo = scanner.nextLine();
            nuevoVehiculoCarga.setModelo(modelo);

            String patente = "";

            try{
                patente = verificarNoDuplicidadPatente();
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }

            nuevoVehiculoCarga.setPatente(patente);

            System.out.print("Ingresa la Capacidad de Carga del Vehiculo: ");
            String capacidadCarga = scanner.nextLine();
            nuevoVehiculoCarga.setCapacidadCarga(Integer.parseInt(capacidadCarga));

            System.out.print("Ingresa el valor Arriendo Día del Vehiculo: ");
            String valorArriendoDia = scanner.nextLine();
            nuevoVehiculoCarga.setCostoArriendoDia(Double.parseDouble(valorArriendoDia));

            System.out.print("Ingresa el Descuento del Vehiculo: ");
            String descuento = scanner.nextLine();
            nuevoVehiculoCarga.setDescuentoArriendo(Double.parseDouble(descuento));

            nuevoVehiculoCarga.setDiasArriendo(0);
            nuevoVehiculoCarga.setEstado("Disponible");
            System.out.println();
            System.out.println("Vehiculo Listado de forma correcta");

            System.out.println("\n----------------------------------\n-      NUEVO VEHICULOS CARGA     -\n----------------------------------");
            System.out.println(nuevoVehiculoCarga.toString());

            try(FileWriter writer = new FileWriter("vehiculos.csv", true)){
                writer.write("\n" + "Carga;" + marca +";" + modelo + ";"+ patente + ";" + capacidadCarga + ";" +
                        valorArriendoDia + ";" + descuento + ";" + "0;" + "Disponible");
                writer.flush();
            }catch (IOException e){
                System.out.println(e.getMessage());
            }

        } else if(opcion.strip().equalsIgnoreCase("pasajeros")){
            VehiculoPasajeros nuevoVehiculoPasajeros = new VehiculoPasajeros();

            nuevoVehiculoPasajeros.setTipoVehiculo("Pasajeros");

            System.out.print("Ingresa la Marca del Vehiculo: ");
            String marca = scanner.nextLine();
            nuevoVehiculoPasajeros.setMarca(marca);

            System.out.print("Ingresa la Modelo del Vehiculo: ");
            String modelo = scanner.nextLine();
            nuevoVehiculoPasajeros.setModelo(modelo);

            String patente = "";

            try{
                patente = verificarNoDuplicidadPatente();
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }

            nuevoVehiculoPasajeros.setPatente(patente);

            System.out.print("Ingresa el Numero de Asientos del Vehiculo: ");
            String numeroPasajeros = scanner.nextLine();
            nuevoVehiculoPasajeros.setNumeroPasajeros(Integer.parseInt(numeroPasajeros));

            System.out.print("Ingresa el valor Arriendo Día del Vehiculo: ");
            String valorArriendoDia = scanner.nextLine();
            nuevoVehiculoPasajeros.setCostoArriendoDia(Double.parseDouble(valorArriendoDia));

            System.out.print("Ingresa el Descuento del Vehiculo: ");
            String descuento = scanner.nextLine();
            nuevoVehiculoPasajeros.setDescuentoArriendo(Double.parseDouble(descuento));

            nuevoVehiculoPasajeros.setDiasArriendo(0);
            nuevoVehiculoPasajeros.setEstado("Disponible");
            System.out.println();
            System.out.println("Vehiculo Listado de forma correcta");

            System.out.println("\n---------------------------------------\n-      NUEVO VEHICULOS PASAJEROS     -\n---------------------------------------");

            System.out.println(nuevoVehiculoPasajeros.toString());

            try(FileWriter writer = new FileWriter("vehiculos.csv", true)){
                writer.write("\n" + "Pasajeros;" + marca +";" + modelo + ";"+ patente + ";" + numeroPasajeros + ";" +
                        valorArriendoDia + ";" + descuento + ";" + "0;" + "Disponible");
                writer.flush();
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }else{
            System.out.println("Valor ingresado incorrecto");
        }
        System.out.println("El vehiculo ha sido ingresado de forma correcta");

    }
}