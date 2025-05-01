
import java.util.ArrayList;

public class EmpresaLogistica{
    private ArrayList<Casilleros> matrizCasilleros;
    private RegistroPedidos registro;


    public EmpresaLogistica(){
        this.matrizCasilleros = new ArrayList<Casilleros>(200);
        this.registro = new RegistroPedidos();
    }

    public Casilleros getCasillero(int i){
        return matrizCasilleros.get(i);
    }



/**
 * The function searches for a free cell in a matrix of cells and returns it, or throws an exception if
 * no free cell is found.
 * 
 * @return The method `buscarCasilleroLibre` is returning a Casilleros object, specifically the first
 * @throws IllegalStateException if there are no free cells available in the matrix.
 * Casilleros object in the list that has a state of `VACIO` (empty).
 */
    public Casilleros buscarCasilleroLibre() throws IllegalStateException{
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