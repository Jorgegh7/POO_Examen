package modelo.vehiculo;

public class VehiculoPasajeros extends Vehiculo {

    private int numeroPasajeros;

    public VehiculoPasajeros() {
    }
    public VehiculoPasajeros(int numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    public int getNumeroPasajeros() {
        return numeroPasajeros;
    }
    public void setNumeroPasajeros(int numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    @Override
    public String toString() {
        return "Vehiculo " + getTipoVehiculo() + '\n' +
                "Marca: " + getMarca() + '\n' +
                "Modelo: " + getModelo() + '\n' +
                "Patente: " + getPatente() + '\n' +
                "Número Asientos: " + getNumeroPasajeros() + '\n' +
                "Costo Arriendo: " + getCostoArriendoDia() + '\n' +
                "Descuento Arriendo: " + getDescuentoArriendo() + "%" + '\n' +
                "Dias Arriendo: " + getDiasArriendo() + '\n' +
                "Estado: " + getEstado() + '\n';
    }
}
