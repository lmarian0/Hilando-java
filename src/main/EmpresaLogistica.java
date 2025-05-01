package main;

import java.util.ArrayList;

import main.Casilleros;
import main.EstadoCasillero;
import main.RegistroPedidos;

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

    public Casilleros buscarCasillero(){
        for (int i = 0; i < matrizCasilleros.size(); i++){
            if (getCasillero(i).getPedido().getEstado().equals(EstadoCasillero.VACIO)){
                return getCasillero(i);
            }
        }
        throw new IllegalStateException("No hay casilleros vacios");
    }

    public void generarEstadisticas(){
    }

}