
import java.util.ArrayList;

public class EmpresaLogistica{
    private ArrayList<Casilleros> matrizCasilleros;
    private RegistroPedidos registro;


    public EmpresaLogistica(){
        this.matrizCasilleros = new ArrayList<Casilleros>(200);
        this.registro = new RegistroPedidos();
    }

    public void iniciarProcesos(){
    }

    public Casilleros getCasillero(int i){
        return matrizCasilleros.get(i);
    }

    public Casilleros buscarCasilleroLibre(){
        for (int i = 0; i < matrizCasilleros.size(); i++){
            if (getCasillero(i).getEstado() == EstadoCasillero.VACIO){ //el proposito se entiende, pero basta con corroborar si el casillero esto libre
                return getCasillero(i);
            }
        }
        throw new IllegalStateException("No hay casilleros vacios");
    }

    public void generarEstadisticas(){
    }

}