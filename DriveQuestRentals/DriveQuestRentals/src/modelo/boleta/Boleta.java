package modelo.boleta;

import modelo.usuario.Usuario;
import modelo.vehiculo.Vehiculo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class Boleta implements CalculoBoleta{

    private double costoArriendoDia;
    private double descuento;
    private int numeroDiasArriendo;
    private double costoNeto;
    private double valorConDescuento;
    private double valorTotal;
    private final int IVA = 19;
    private double valorIva;
    private double totalPago;
    private LocalDate fecha;
    public Usuario usuario;


    public Boleta() {}

    public Boleta(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getCostoArriendoDia() {
        return costoArriendoDia;
    }
    public void setCostoArriendoDia(double costoArriendoDia) {
        this.costoArriendoDia = costoArriendoDia;
    }
    public double getDescuento() {
        return descuento;
    }
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    public int getNumeroDiasArriendo() {
        return numeroDiasArriendo;
    }
    public void setNumeroDiasArriendo(int numeroDiasArriendo) {
        this.numeroDiasArriendo = numeroDiasArriendo;
    }
    public double getCostoNeto() {
        return costoNeto;
    }
    public void setCostoNeto(double costoNeto) {
        this.costoNeto = costoNeto;
    }
    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    public int getIVA() {
        return IVA;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public double getValorConDescuento() {
        return valorConDescuento;
    }
    public void setValorConDescuento(double valorConDescuento) {
        this.valorConDescuento = valorConDescuento;
    }

    @Override
    public void calculoCostoArriendo(Boleta boleta) {
        System.out.println("============================\n-          Boleta          -\n============================");

        System.out.println("Arrendatario: " + boleta.usuario.getNombre() + " " + boleta.usuario.getApellido());
        System.out.println("Fecha Arriendo: " + usuario.getFechaArriendo());
        System.out.println();

        for(Vehiculo vehiculo: boleta.usuario.getVehiculosArrendados()){

            boleta.costoArriendoDia = vehiculo.getCostoArriendoDia();
            boleta.descuento = vehiculo.getDescuentoArriendo();
            boleta.numeroDiasArriendo = vehiculo.getDiasArriendo();
            boleta.descuento = vehiculo.getDescuentoArriendo();

            boleta.costoNeto = boleta.costoArriendoDia * numeroDiasArriendo;
            valorConDescuento = boleta.costoNeto*((100 - vehiculo.getDescuentoArriendo())/100);

            valorIva =(valorConDescuento* CalculoBoleta.IVA);
            boleta.valorTotal = valorConDescuento + valorIva;

            System.out.println("----   Boleta Arriendo   ----"+ '\n' +
                    "Vehiculo " + vehiculo.getTipoVehiculo() + ", Marca: " +
                    vehiculo.getMarca() + ", Modelo: " + vehiculo.getModelo() + ", Patente: " + vehiculo.getPatente() + '\n' +
                    "Costo Arriendo Dia: $" + costoArriendoDia + '\n' +
                    "Descuento: " + descuento + "%" + '\n' +
                    "Numero dias Arriendo: " + numeroDiasArriendo + '\n' +
                    "Valor Neto: $" + costoNeto + '\n' +
                    "Valor con Descuento: $" + valorConDescuento + '\n' +
                    "IVA: " + IVA + "%" + '\n' +
                    "Valor IVA: $" + valorIva + '\n' +
                    "Valor Arriendo: $" + valorTotal);
            System.out.println();
            totalPago = totalPago + valorTotal;
        }

        System.out.println("Total Vehiculos Arrendados: " + boleta.usuario.getVehiculosArrendados().size());
        System.out.println("Total a Pagar: $" + totalPago);
    }

    public double boletaUnica(Vehiculo vehiculo){
        double valorNeto = vehiculo.getCostoArriendoDia() * (double) vehiculo.getDiasArriendo();
        double descuentoAplicado = (double) (100 - vehiculo.getDescuentoArriendo())/100;
        double valorConDescuento = valorNeto * descuentoAplicado;
        double total = valorConDescuento + (valorConDescuento * CalculoBoleta.IVA);
    return total;
    }

    public void mostrarArchivoBoletas() throws InterruptedException {

        Thread hiloArchivoBoletas = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("\n-------------------------\n-     LISTA BOLETAS     -\n-------------------------");
                System.out.println();
                System.out.println("Arrendatario, Tipo Vehiculo, Marca, Modelo, Patente, Valor Día, Descuento, Días Arriendo, Total Boleta, Fecha");
                System.out.println();

                try(BufferedReader reader = new BufferedReader(new FileReader("boletas_arriendo.txt"))){
                    String linea;
                    while((linea=reader.readLine()) != null){
                        System.out.println(linea);
                    }
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }

            }
        });
        hiloArchivoBoletas.start();
        hiloArchivoBoletas.join();
    }

    @Override
    public String toString() {
        return "----   Boleta Arriendo   ----"+ '\n' +
                "Costo Arriendo Dia: $" + costoArriendoDia + '\n' +
                "Descuento: " + descuento + "%" + '\n' +
                "Numero dias Arriendo: " + numeroDiasArriendo + '\n' +
                "Valor Neto: $" + costoNeto + '\n' +
                "IVA: " + IVA + "%" + '\n' +
                "Valor IVA: $" + valorIva + '\n' +
                "Valor Total: $" + valorTotal;

    }

}
