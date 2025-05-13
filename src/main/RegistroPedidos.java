//package main;

import java.util.ArrayList;
import java.util.List;

public class RegistroPedidos {

    private List<Pedido> enPreparacion;
    private List<Pedido> enTransito;
    private List<Pedido> entregados;
    private List<Pedido> fallidos;
    private List<Pedido> verificados;

    private final Object KeyPreparacion, KeyEntrega, KeyTransito, KeyDespacho, KeyFallidos, KeyVerificados;

    public RegistroPedidos(){
        this.enPreparacion= new ArrayList<>();
        this.entregados = new ArrayList<>();
        this.enTransito = new ArrayList<>();
        this.fallidos = new ArrayList<>();
        this.verificados = new ArrayList<>();

        this.KeyPreparacion = new Object();
        this.KeyEntrega = new Object();
        this.KeyVerificados = new Object();
        this.KeyTransito = new Object();
        this.KeyDespacho = new Object();
        this.KeyFallidos = new Object();
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
                KeyPreparacion.notifyAll(); //Notifica a los hilos que esten esperando
            }
        }
    }

    public void delPreparacion (Pedido pedido){
        enPreparacion.remove(pedido);

    }


    public synchronized void addEntregados (Pedido pedido){
        entregados.add(pedido);
    }
    public  void addTransito (Pedido pedido){
        synchronized (enTransito){
            enTransito.add(pedido);
        }
    }
    public void addFallidos (Pedido pedido){
        synchronized (fallidos){
            fallidos.add(pedido);
        }
    }
    public void addVerificados(Pedido pedido){
        synchronized (verificados){
            verificados.add(pedido);
        }
    }

    //------------------------------------------------------------------------------------------------
    public synchronized void delEntregados (Pedido pedido){
        entregados.remove(pedido);
    }

    public void delTransito (Pedido pedido){
        synchronized (enTransito){
            enTransito.remove(pedido);
        }
    }

    public void delFallidos (Pedido pedido){
        synchronized (fallidos){
            fallidos.remove(pedido);
        }
    }

    public  void delVerificados(Pedido pedido){
        synchronized (verificados){
            verificados.remove(pedido);
        }
    }



}
