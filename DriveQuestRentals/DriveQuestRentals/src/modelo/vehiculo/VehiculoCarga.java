package modelo.vehiculo;

public class VehiculoCarga extends Vehiculo {

    private int capacidadCarga;

    public VehiculoCarga() {
    }
    public VehiculoCarga(int capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public int getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(int capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    @Override
    public String toString() {
        return "Vehiculo " + getTipoVehiculo() + '\n' +
                "Marca: " + getMarca() + '\n' +
                "Modelo: " + getModelo() + '\n' +
                "Patente: " + getPatente() + '\n' +
                "Capacidad Carga: " + getCapacidadCarga() + "Kg" + '\n' +
                "Costo Arriendo: " + getCostoArriendoDia() + '\n' +
                "Descuento Arriendo: " + getDescuentoArriendo() + "%" + '\n' +
                "Dias Arriendo: " + getDiasArriendo() + '\n' +
                "Estado: " + getEstado() + '\n';
    }
}
