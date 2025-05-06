// package main;

import java.util.ArrayList;

public class EmpresaLogistica{
    private ArrayList<Casilleros> matrizCasilleros;
    private RegistroPedidos registro;

    public EmpresaLogistica(){
        this.matrizCasilleros = new ArrayList<Casilleros>(200);
        this.registro = new RegistroPedidos();
        for (int i = 0; i < 200; i++){
            matrizCasilleros.add(new Casilleros(i));
        }
    }

    public synchronized Casilleros getCasillero(int i){
        if (i<0 || i>= matrizCasilleros.size()){
            throw new IllegalArgumentException("Indice invalido: " + i );
        }
        return matrizCasilleros.get(i);
    }

    public RegistroPedidos getRegistroPedidos(){
        return registro;
    }

}