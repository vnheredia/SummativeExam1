package MonolitoCapas.Biblioteca.src.com.biblioteca.model;
import java.util.UUID;

public class Multa {
    private UUID idUsuario;
    private double monto;

    public Multa(UUID idUsuario, double monto) {
        this.idUsuario = idUsuario;
        this.monto = monto;
    }

    public UUID getIdUsuario() { return idUsuario; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
}