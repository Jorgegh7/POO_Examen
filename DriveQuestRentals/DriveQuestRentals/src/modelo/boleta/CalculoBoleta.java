package modelo.boleta;

public interface CalculoBoleta {

    double IVA = 0.19;
    double DESCUENTO_PASAJEROS = 0.12;
    double DESCUENTO_CARGA = 0.07;

    public void calculoCostoArriendo(Boleta boleta);

}
