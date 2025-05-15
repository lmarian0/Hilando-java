//package main;

import java.util.ArrayList;
import java.util.List;

public class RegistroPedidos {

    private List<Pedido> enPreparacion;
    private List<Pedido> enTransito;
    private List<Pedido> entregados;
    private List<Pedido> fallidos;
    private List<Pedido> verificados;

    private final static Object KeyPreparacion=new Object(), KeyEntrega=new Object(), KeyTransito=new Object(), KeyFallidos=new Object(), KeyVerificados=new Object();

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
    //
     //             ⊂(◉‿◉)つ
    //-----------------------------------------------------------------------------------------------
    public void addPreparacion(Pedido pedido){
        if(pedido == null){
            System.out.println("No se puede agregar un pedido nulo a la lista de pedidos en preparacion");
            return;
        }
        synchronized (KeyPreparacion) {
            if (enPreparacion.contains(pedido)) {
                System.out.println("El pedido ya existe en la lista de pedidos en preparacion");
                return;
            }else{
                enPreparacion.add(pedido);
            }
        }
    }


    public void addEntregados (Pedido pedido){
        if(pedido == null){
            System.out.println("No se puede agregar un pedido nulo a la lista de pedidos entregados");
            return;
        }
        synchronized (KeyEntrega) {
            if (entregados.contains(pedido)) {
                System.out.println("El pedido ya existe en la lista de pedidos entregados");
                return;
            }else{
                entregados.add(pedido);
            }
        }

    }
    public  void addTransito (Pedido pedido){
        if(pedido == null){
            System.out.println("No se puede agregar un pedido nulo a la lista de pedidos en transito");
            return;
        }
        synchronized (KeyTransito) {
            if (enTransito.contains(pedido)) {
                System.out.println("El pedido ya existe en la lista de pedidos en transito");
                return;
            }else{
                enTransito.add(pedido);
            }
        }
    }
    public void addFallidos (Pedido pedido){
        if(pedido == null){
            System.out.println("No se puede agregar un pedido nulo a la lista de pedidos fallidos");
            return;
        }
        synchronized (KeyFallidos) {
            if (fallidos.contains(pedido)) {
                System.out.println("El pedido ya existe en la lista de pedidos fallidos");
                return;
            }else{
                fallidos.add(pedido);
            }
        }
    }
    public void addVerificados(Pedido pedido){
        if(pedido == null){
            System.out.println("No se puede agregar un pedido nulo a la lista de pedidos verificados");
            return;
        }
        synchronized (KeyVerificados) {
            if (verificados.contains(pedido)) {
                System.out.println("El pedido ya existe en la lista de pedidos verificados");
                return;
            }else{
                verificados.add(pedido);
            }
        }
    }

    //------------------------------------------------------------------------------------------------

    public void delPreparacion(Pedido pedido){
        synchronized (KeyPreparacion){
            enPreparacion.remove(pedido);
        }
    }

    public void delEntregados (Pedido pedido){
        synchronized (KeyEntrega){
            entregados.remove(pedido);
        }
    }

    public void delTransito (Pedido pedido){
        synchronized (KeyTransito){
            enTransito.remove(pedido);
        }
    }

}
