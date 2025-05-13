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
    public List<Pedido> getPreparacion(){
        synchronized (enPreparacion){
            return enPreparacion;
        }
    }
    public List<Pedido> getEntregados(){
        synchronized (entregados){
            return entregados;
        }
    }
    public List<Pedido> getTransito(){
        synchronized (enTransito){
            return enTransito;
        }
    }
    public List<Pedido> getFallidos(){
        synchronized (fallidos){
            return fallidos;
        }
    }
    public List<Pedido> getVerificados(){
        synchronized (verificados){
            return verificados;
        }
    }
    //
     //             ⊂(◉‿◉)つ
    //-----------------------------------------------------------------------------------------------
    public void addPreparacion(Pedido pedido){
        synchronized (enPreparacion){
            enPreparacion.add(pedido);
        }

    }
    public  void addEntregados (Pedido pedido){
        synchronized (entregados){
            entregados.add(pedido);
        }
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
    public void delPreparacion (Pedido pedido){
        synchronized (enPreparacion){
            enPreparacion.remove(pedido);
        }

    }
    public void delEntregados (Pedido pedido){
        synchronized (entregados){
            entregados.remove(pedido);
        }
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
