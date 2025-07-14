package modelo.usuario;

import modelo.vehiculo.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Usuario {

    private String nombre;
    private String apellido;
    private LocalDate fechaArriendo;
    private ArrayList<Vehiculo> vehiculosArrendados = new ArrayList<>();



    public Usuario() {
    }

    public Usuario(String nombre, String apellido, LocalDate fechaArriendo, ArrayList<Vehiculo> vehiculosArrendados) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaArriendo = fechaArriendo;
        this.vehiculosArrendados = vehiculosArrendados;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public ArrayList<Vehiculo> getVehiculosArrendados() {
        return vehiculosArrendados;
    }
    public void setVehiculosArrendados(ArrayList<Vehiculo> vehiculosArrendados) {
        this.vehiculosArrendados = vehiculosArrendados;
    }
    public LocalDate getFechaArriendo() {
        return fechaArriendo;
    }
    public void setFechaArriendo(LocalDate fechaArriendo) {
        this.fechaArriendo = fechaArriendo;
    }

    public void agregarVehiculo(Vehiculo vehiculo){
        vehiculosArrendados.add(vehiculo);
    }

    public void mostrarListaVehiculosArrendados(){
        for(Vehiculo vehiculo: vehiculosArrendados){
            System.out.println(vehiculo.toString());
        }
    }

    public Usuario inicializarUsuario(){
        Usuario usuario = new Usuario();
        ArrayList<Vehiculo> vehiculosUsuario = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresa tu nombre: ");
        usuario.setNombre(scanner.nextLine());
        System.out.println("Ingresa tu apellido: ");
        usuario.setApellido(scanner.nextLine());
        usuario.setVehiculosArrendados(vehiculosUsuario);

     return usuario;
    }

    @Override
    public String toString() {
        fechaArriendo = LocalDate.now();
        return "Arrendatario Vehiculo " + '\n' +
                "Nombre: " + nombre + " " + apellido + '\n' +
                "VehiculosArrendados: " + vehiculosArrendados.toString() + '\n' +
                "Fecha Arriendo: " + fechaArriendo;
    }
}
