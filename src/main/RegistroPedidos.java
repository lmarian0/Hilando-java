package main;

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
    public List<Pedido> getPreparacion(){
        return enPreparacion;
    }
    public List<Pedido> getEntregados(){
        return entregados;
    }
    public List<Pedido> getTransito(){
        return enTransito;
    }
    public List<Pedido> getFallidos(){
        return fallidos;
    }
    public List<Pedido> getVerificados(){
        return verificados;
    }

    //-----------------------------------------------------------------------------------------------
    public void addPreparacion(Pedido pedido){
        enPreparacion.add(pedido);

    }
    public void addEntregados (Pedido pedido){
        entregados.add(pedido);
    }
    public void addTransito (Pedido pedido){
        enTransito.add(pedido);
    }
    public void addFallidos (Pedido pedido){
        fallidos.add(pedido);
    }
    public void addVerificados(Pedido pedido){
        verificados.add(pedido);
    }

    //------------------------------------------------------------------------------------------------
    public void delPreparacion (Pedido pedido){
        enPreparacion.remove(pedido);

    }
    public void delEntregados (Pedido pedido){
        entregados.remove(pedido);
    }

    public void delTransito (Pedido pedido){
        enTransito.remove(pedido);
    }

    public void delFallidos (Pedido pedido){
        fallidos.remove(pedido);
    }

    public void delVerificados(Pedido pedido){
        verificados.remove(pedido);
    }



}
