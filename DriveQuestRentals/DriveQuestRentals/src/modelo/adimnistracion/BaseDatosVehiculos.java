package modelo.adimnistracion;

import modelo.vehiculo.Vehiculo;
import modelo.vehiculo.VehiculoCarga;
import modelo.vehiculo.VehiculoPasajeros;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class BaseDatosVehiculos implements Runnable{

    public BaseDatosVehiculos() {
    }

    private HashMap listaVehiculos;

    private Map listaMapVehiculos;

    public BaseDatosVehiculos(Map listaMapVehiculos) {
        this.listaMapVehiculos = listaMapVehiculos;
    }

    public BaseDatosVehiculos(HashMap listaVehiculos) {
        this.listaVehiculos = listaVehiculos;
    }

    public HashMap listaVehiculos() throws InterruptedException {
        HashMap<String, Vehiculo> listaTotalVehiculos = new HashMap<>();
        Thread hiloListaVehiculos = new Thread(() -> {
            try(BufferedReader reader = new BufferedReader(new FileReader("vehiculos.csv"))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] campos = linea.split(";");

                    if (campos.length >= 9 && campos[0].toLowerCase().strip().equals("carga")) {
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

                        listaTotalVehiculos.put(campos[3], vehiculoCarga);

                    }else if (campos.length >=9 && campos[0].toLowerCase().strip().equals("pasajeros")) {
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

                        listaTotalVehiculos.put(campos[3], vehiculoPasajeros);
                    }
                }
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        });
        hiloListaVehiculos.start();
        hiloListaVehiculos.join();
        return listaTotalVehiculos;
    }

    public void mostrarVehiculosArrendados() throws InterruptedException {

        System.out.println("\n--------------------------------------\n-     LISTA VEHICULOS ARRENDADOS     -\n--------------------------------------");
        System.out.println();
        AtomicBoolean hayArrendados = new AtomicBoolean(false);

        Thread hiloListaVehiculos = new Thread(() -> {
            try(BufferedReader reader = new BufferedReader(new FileReader("vehiculos.csv"))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] campos = linea.split(";");

                    if (campos.length >= 9 && campos[8].strip().equalsIgnoreCase("arrendado")) {
                        System.out.println(linea);
                        hayArrendados.set(true);
                    }
                }
                if(!hayArrendados.get()){
                    System.out.println("No hay vehiculos arrendados en este momento");
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
    });
    hiloListaVehiculos.start();
    hiloListaVehiculos.join();
}



@Override
public void run() {
    System.out.println("\n---------------------------------\n-     VEHICULOS DISPONIBLES     -\n---------------------------------");
    System.out.println();

    try(BufferedReader reader = new BufferedReader(new FileReader("vehiculos.csv"))){
        String linea;
        while((linea= reader.readLine()) != null){
            String[] campos = linea.split(";");

            if(campos.length >=9 && campos[0].toLowerCase().strip().equals("carga")){
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

                listaMapVehiculos.put(campos[3], vehiculoCarga);

                if (vehiculoCarga.getEstado().equalsIgnoreCase("disponible")){
                    System.out.println("Tipo: " + vehiculoCarga.getTipoVehiculo() + ", Marca: " + vehiculoCarga.getMarca() + ", Modelo: " + vehiculoCarga.getModelo() +
                            ", Patente: " + vehiculoCarga.getPatente() + ", Capacidad Carga: " + vehiculoCarga.getCapacidadCarga() + "Kg" + ", Costo Arriendo Día: $" +
                    vehiculoCarga.getCostoArriendoDia() + ", Descuento Arriendo: " + vehiculoCarga.getDescuentoArriendo() +"%" + ", Estado: " + vehiculoCarga.getEstado());
                }

            } else if (campos.length >=9 && campos[0].toLowerCase().strip().equals("pasajeros")) {
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

                listaMapVehiculos.put(campos[3], vehiculoPasajeros);

                if (vehiculoPasajeros.getEstado().equalsIgnoreCase("disponible")){
                    System.out.println("Tipo: " + vehiculoPasajeros.getTipoVehiculo() +
                            ", Marca: " + vehiculoPasajeros.getMarca() +
                            ", Modelo: " + vehiculoPasajeros.getModelo() +
                            ", Patente: " + vehiculoPasajeros.getPatente() +
                            ", Asientos Disponibles: " + vehiculoPasajeros.getNumeroPasajeros() +
                            ", Costo Arriendo Día: $" + vehiculoPasajeros.getCostoArriendoDia() +
                            ", Descuento Arriendo: " + vehiculoPasajeros.getDescuentoArriendo() +"%" +
                            ", Estado: " + vehiculoPasajeros.getEstado());
                }
            }
        }
    }catch (IOException e){
        System.out.println("Archivo no disponible: " + e.getMessage());
    }

}
}
