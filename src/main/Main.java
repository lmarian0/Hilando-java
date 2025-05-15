import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       EmpresaLogistica empresa = new EmpresaLogistica();
       GeneradorPedidos nuevos_pedidos = new GeneradorPedidos();
       List<Thread> threads = new ArrayList<>(); // Lista para almacenar los hilos

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
        Thread entrega = new Thread(new EntregarPedido(empresa,50), "Delivery_thread " + (i+1));
        entrega.start();
        threads.add(entrega);
      }


      //  //ETAPA 4: VerificarPedido (2 hilos)

      // for (int i = 0; i<2; i++){
      //   Thread verificacion = new Thread(new VerificarPedido(empresa), "Verification_thread " + (i+1));
      //   verificacion.start();
      //   threads.add(verificacion);
      //  }

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
