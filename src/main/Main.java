import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       EmpresaLogistica empresa = new EmpresaLogistica();
       List<Pedido> nuevos_pedidos = new ArrayList<>();
       List<Thread> threads = new ArrayList<>(); // Lista para almacenar los hilos

       //Simula la llegada de 500 pedidos a la empresa
       for(int i = 0; i<500; i++){
        Pedido pedido = new Pedido(i);
        nuevos_pedidos.add(pedido);
       }

       // Iniciar el LOG de estadisticas
       Thread logThread = new Thread(new LogEstadisticas(empresa));
       logThread.start();
       threads.add(logThread);

       //ETAPA 1: PrepararPedido (3 hilos)

       for (int i = 0; i<3; i++){
        Thread preparacion = new Thread(new PrepararPedido(empresa,nuevos_pedidos), "Preparation_thread " + (i+1));
        preparacion.start();
        threads.add(preparacion);
       }

       //ETAPA 2: DespachoPedido (2 hilos)

       for (int i = 0; i<2; i++){
        Thread despacho = new Thread(new DespacharPedido(empresa), "Dispatch_thread " + (i+1));
        despacho.start();
        threads.add(despacho);
       }

       //ETAPA 3: EntregaPedido (3 hilos)

        for (int i = 0; i<3; i++){
        Thread entrega = new Thread(new EntregarPedido(empresa,1), "Delivery_thread " + (i+1));
        entrega.start();
        threads.add(entrega);
       }

       //ETAPA 4: VerificarPedido (2 hilos)

      for (int i = 0; i<2; i++){
        Thread verificacion = new Thread(new VerificarPedido(empresa), "Verification_thread " + (i+1));
        verificacion.start();
        threads.add(verificacion);
       }

       try{
        Thread.sleep(30000); // Simular tiempo de ejecucion del programa
       } catch(InterruptedException e){
        System.out.println("Finalizo el registro");
       }

       for (Thread thread : threads){
        thread.interrupt();
       }
    }
}
