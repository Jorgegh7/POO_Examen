package modelo.vehiculo;

public abstract class Vehiculo {
    private String marca;
    private String modelo;
    private String patente;
    private double costoArriendoDia;
    private double descuentoArriendo;
    private int diasArriendo;
    private String tipoVehiculo;
    private String estado;

    public Vehiculo() {
    }

    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getPatente() {
        return patente;
    }
    public void setPatente(String patente) {
        this.patente = patente;
    }
    public double getCostoArriendoDia() {
        return costoArriendoDia;
    }
    public void setCostoArriendoDia(double costoArriendoDia) {
        this.costoArriendoDia = costoArriendoDia;
    }
    public int getDiasArriendo() {
        return diasArriendo;
    }
    public void setDiasArriendo(int diasArriendo) {
        this.diasArriendo = diasArriendo;
    }
    public double getDescuentoArriendo() {
        return descuentoArriendo;
    }
    public void setDescuentoArriendo(double descuentoArriendo) {
        this.descuentoArriendo = descuentoArriendo;
    }
    public String getTipoVehiculo() {
        return tipoVehiculo;
    }
    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void mostrarVehiculo(){}

    @Override
    public String toString() {
        return "Vehiculo " + tipoVehiculo + '\n' +
                "Marca: " + marca + '\n' +
                "Modelo: " + modelo + '\n' +
                "Patente: " + patente + '\n' +
                "Costo Arriendo: " + costoArriendoDia + '\n' +
                "Descuento Arriendo: " + descuentoArriendo + "%" + '\n' +
                "Dias Arriendo: " + diasArriendo + '\n' +
                "Estado: " + estado + '\n';

    }
}
