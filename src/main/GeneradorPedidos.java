import java.util.ArrayList;
import java.util.List;

public class GeneradorPedidos {

    private List<Pedido>Arrivados;
    private final int max_pedidos = 500;

    public GeneradorPedidos(){
        Arrivados = new ArrayList<Pedido>();
        
        //genera 500 pedidos
        for(int i = 0; i<max_pedidos; i++){
            Arrivados.add(new Pedido(i+1));
        }
    }
    
    public synchronized Pedido obtenerPedido(){         //Solo vamos a tener 1 instancia de GeneradorPedidos, entonces tener el metodo synchronized nos asegura no tomar 1 pedido por distintos hilos
        if(Arrivados.size() == 0){
            System.out.println("No hay mas pedidos");
            return null;                               //Poner condicion de verificacion para que no siga si pedido == null
        }
        return Arrivados.remove(0);
    }
}
