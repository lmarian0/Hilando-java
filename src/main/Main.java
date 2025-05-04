import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       EmpresaLogistica empresa = new EmpresaLogistica();
       List<Pedido> nuevos_pedidos = new ArrayList<>();

       //Simula la llegada de 500 pedidos a la empresa
       for(int i = 0; i<500; i++){
        Pedido pedido = new Pedido();
        nuevos_pedidos.add(pedido);
       }

       //ETAPA 1: PrepararPedido (3 hilos)

       for (int i = 0; i<3; i++){
        Thread preparacion = new Thread(new PrepararPedido(empresa,nuevos_pedidos), "Preparation_thread " + i+1);
        preparacion.start();
       }

       //ETAPA 2: DespachoPedido (2 hilos)

       for (int i = 0; i<2; i++){
        Thread despacho = new Thread(new DespacharPedido(empresa), "Dispatch_thread " + i+1);
        despacho.start();
       }

       //ETAPA 3: EntregaPedido (3 hilos)

       for (int i = 0; i<3; i++){
        Thread entrega = new Thread(new EntregarPedido(empresa), "Delivety_thread " + i+1);
        entrega.start();
       }

       //ETAPA 4: VerificarPedido (2 hilos)

       for (int i = 0; i<2; i++){
        Thread verificacion = new Thread(new VerificarPedido(empresa), "Verification_thread " + i+1);
        verificacion.start();
       }
    }
}
