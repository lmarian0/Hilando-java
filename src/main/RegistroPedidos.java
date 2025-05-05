//package main;

import java.util.ArrayList;
import java.util.List;

public class RegistroPedidos {

    private List<Pedido> enPreparacion;
    private List<Pedido> enTransito;
    private List<Pedido> entregados;
    private List<Pedido> fallidos;
    private List<Pedido> verificados;

    public RegistroPedidos(){
        this.enPreparacion= new ArrayList<>();
        this.entregados = new ArrayList<>();
        this.enTransito = new ArrayList<>();
        this.fallidos = new ArrayList<>();
        this.verificados = new ArrayList<>();


    }

    // -----------------------------------------------------------------------------------------------
    public synchronized List<Pedido> getPreparacion(){
        return enPreparacion;
    }
    public synchronized List<Pedido> getEntregados(){
        return entregados;
    }
    public synchronized List<Pedido> getTransito(){
        return enTransito;
    }
    public synchronized List<Pedido> getFallidos(){
        return fallidos;
    }
    public synchronized List<Pedido> getVerificados(){
        return verificados;
    }
    //
     //             ⊂(◉‿◉)つ
    //-----------------------------------------------------------------------------------------------
    public synchronized void addPreparacion(Pedido pedido){
        enPreparacion.add(pedido);

    }
    public synchronized void addEntregados (Pedido pedido){
        entregados.add(pedido);
    }
    public synchronized void addTransito (Pedido pedido){
        enTransito.add(pedido);
    }
    public synchronized void addFallidos (Pedido pedido){
        fallidos.add(pedido);
    }
    public synchronized void addVerificados(Pedido pedido){
        verificados.add(pedido);
    }

    //------------------------------------------------------------------------------------------------
    public synchronized void delPreparacion (Pedido pedido){
        enPreparacion.remove(pedido);

    }
    public synchronized void delEntregados (Pedido pedido){
        entregados.remove(pedido);
    }

    public synchronized void delTransito (Pedido pedido){
        enTransito.remove(pedido);
    }

    public synchronized void delFallidos (Pedido pedido){
        fallidos.remove(pedido);
    }

    public synchronized void delVerificados(Pedido pedido){
        verificados.remove(pedido);
    }



}
