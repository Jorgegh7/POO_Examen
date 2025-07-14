package app;

import modelo.adimnistracion.AdministracionVehiculos;
import modelo.adimnistracion.BaseDatosVehiculos;
import modelo.boleta.Boleta;
import modelo.usuario.Usuario;
import modelo.vehiculo.Vehiculo;
import servicio.ArrendarVehiculo;

import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        AdministracionVehiculos administracionVehiculos = new AdministracionVehiculos();
        ArrendarVehiculo arrendarVehiculo = new ArrendarVehiculo();

        int opcionMenu;
        System.out.println("\n=====================================\n-     DRIVEQUEST RENTALS    -\n=====================================");

        Usuario usuario = new Usuario();
        usuario= usuario.inicializarUsuario();

        do{
            System.out.println("\n===========================\n-     MENÚ PRINCIPAL     -\n===========================");
            System.out.println("1. Lista Vehiculos Disponibles");
            System.out.println("2. Arrendar Vehiculo de Carga");
            System.out.println("3. Arrendar Vehiculo de Pasajeros");
            System.out.println("4. Carrito de Compras");
            System.out.println("5. Devolver Vehiculo Arrendado");
            System.out.println("6. Administración");
            System.out.println("7. Salir");
            System.out.println("===========================");

            System.out.println("Selecciona tu opción:");

            opcionMenu=0;
            Scanner scanner = new Scanner(System.in);
            try{
                opcionMenu= scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Ingresa un numero correcto");
            }
            scanner.nextLine(); // limpiamos el buffer

            switch (opcionMenu) {

                case 1: //Lista vehiculos Disponibles

                    HashMap<String, Vehiculo> vehiculos = new HashMap<>();
                    Map<String,Vehiculo> listaVehiculosMapSincronizada = Collections.synchronizedMap(vehiculos);
                    BaseDatosVehiculos baseDatosVehiculos = new BaseDatosVehiculos(listaVehiculosMapSincronizada);
                    Thread hiloVehiculos = new Thread(baseDatosVehiculos);
                    hiloVehiculos.start();
                    hiloVehiculos.join();
                    break;

                case 2: //Arrendar Vehiculo Carga

                    ArrayList<Vehiculo> listaCarga = new ArrayList<>();
                    List<Vehiculo> listaCargaSincronizada = Collections.synchronizedList(listaCarga);
                    try{
                        listaCargaSincronizada =administracionVehiculos.mostarListaCargo();
                        arrendarVehiculo.arrendarVehiculo(listaCargaSincronizada, usuario);
                    }catch (InterruptedException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3: //Arrendar Vehiculo Pasajeros

                    ArrayList<Vehiculo> listaPasajeros = new ArrayList<>();
                    List<Vehiculo> listaPasajerosSincronizada = Collections.synchronizedList(listaPasajeros);
                    try{
                        listaPasajerosSincronizada= administracionVehiculos.mostarListaPasajeros();
                        arrendarVehiculo.arrendarVehiculo(listaPasajerosSincronizada, usuario);
                    }catch (InterruptedException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4: //Entregar Boleta Final (Lista completa de Arriendos)
                    Boleta boleta = new Boleta(usuario);
                    boleta.calculoCostoArriendo(boleta);

                    break;

                case 5:
                    arrendarVehiculo.devolverVehiculo();
                    break;

                case 6: // Administración

                    int opcionMenuAdmin=0;

                    do{
                        System.out.println("\n==============================\n-     MENÚ ADMINISTRACION     -\n==============================");
                        System.out.println("1. Lista Boletas");
                        System.out.println("2. Lista Vehiculos Arrendados");
                        System.out.println("3. Listar Vehiculo");
                        System.out.println("4. Deslistar Vehiculo");
                        System.out.println("5. Salir");

                        System.out.println("Selecciona tu opción:");


                        scanner = new Scanner(System.in);
                        try{
                            opcionMenuAdmin= scanner.nextInt();
                        }catch (InputMismatchException e){
                            System.out.println("Ingresa un numero correcto");
                        }
                        scanner.nextLine(); // limpiamos el buffer

                        switch (opcionMenuAdmin){

                            case 1: // Lista Boletas
                                try{
                                    Boleta archivoBoletas = new Boleta();
                                    archivoBoletas.mostrarArchivoBoletas();
                                }catch (InterruptedException e){
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 2:
                                try{
                                    BaseDatosVehiculos mostrarDatos = new BaseDatosVehiculos();
                                    mostrarDatos.mostrarVehiculosArrendados();
                                }catch (InterruptedException e){
                                    System.out.println(e.getMessage());
                                }

                                break;

                            case 3: // Listar Vehiculo
                                Thread hiloListarVehiculo = new Thread(administracionVehiculos);
                                hiloListarVehiculo.start();
                                hiloListarVehiculo.join();
                                break;

                            case 4: //Deslistar Vehiculo
                                try{
                                    administracionVehiculos.deslistarVehiculo();
                                }catch (InterruptedException e){
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 5: //Salir Menu Administrador
                                break;

                            default:
                                System.out.println("Ingresa una opcion correcta");
                        }
                    }while(opcionMenuAdmin !=5);
                    break;

                case 7: //Salir App

                    System.out.println("Gracias por preferir DRIVEQUEST RENTALS");
                    System.out.println("Hasta la proxima!");
                    break;

                default:
                    System.out.println("Ingresa un número valido");
            }

        }while(opcionMenu !=7);
    }
}