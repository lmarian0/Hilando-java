// package main;

import java.util.ArrayList;

public class EmpresaLogistica{
    private ArrayList<Casilleros> matrizCasilleros;
    private RegistroPedidos registro = new RegistroPedidos(); //registro de pedidos

    public EmpresaLogistica(){
        this.matrizCasilleros = new ArrayList<Casilleros>(200);
        this.registro = new RegistroPedidos();
        for (int i = 0; i < 200; i++){
            matrizCasilleros.add(new Casilleros());
        }
    }

    public void iniciarProcesos(){
    }

    public Casilleros getCasillero(int i){
        return matrizCasilleros.get(i);
    }

    public RegistroPedidos getRegistroPedidos(){
        return registro;
    }

    public void generarEstadisticas(){
    }

}