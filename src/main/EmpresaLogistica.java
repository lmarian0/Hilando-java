// package main;

import java.util.ArrayList;

public class EmpresaLogistica{
    private ArrayList<Casillero> matrizCasilleros;
    private RegistroPedidos registro;

    public EmpresaLogistica(){
        this.matrizCasilleros = new ArrayList<Casillero>(200);
        this.registro = new RegistroPedidos();
        for (int i = 0; i < 200; i++){
            matrizCasilleros.add(new Casillero(i));
        }
    }

    public synchronized Casillero getCasillero(int i){
        if (i<0 || i>= matrizCasilleros.size()){
            throw new IllegalArgumentException("Indice invalido: " + i );
        }
        return matrizCasilleros.get(i);
    }

    public RegistroPedidos getRegistroPedidos(){
        return registro;
    }

}