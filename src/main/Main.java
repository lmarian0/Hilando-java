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

      for (int i = 0; i<2; i++){
        Thread verificacion = new Thread(new VerificarPedido(empresa), "Verification_thread " + (i+1));
        verificacion.start();
        threads.add(verificacion);
      }

      try{
        Thread.sleep(1); // Damos un tiempo para que arranquen los subprocesos
      } catch(InterruptedException e){
        System.out.println("Finalizo el registro");
      }


    
   // Esperar hasta que finalice la generacion de los pedidos y vamos revisando si finalizo la generacion de los pedidos
while (!nuevos_pedidos.getFinalizado()) {
  try {
      Thread.sleep(1000); // Pausa para evitar que se consuman recursos con el while
  } catch (InterruptedException e) {
      System.out.println("Monitoreo interrumpido.");
      break;
  }
}

// Esperar hasta que el primer hilo de PrepararPedido termine
Thread primerPreparador = threads.get(1); // Primer hilo de PrepararPedido (posición 1 si logThread está en 0)
while (primerPreparador.getState() != Thread.State.TERMINATED) {
  try {
      Thread.sleep(500); // Pausa para evitar que se consuman recursos con el while
  } catch (InterruptedException e) {
      System.out.println("Se interrumpio la espera del primer hilo de PrepararPedido");
  }
}

// Ahora revisamos si el siguiente hilo está en TIMED_WAITING y lo interrumpimos si es necesario
for (int i = 0; i < threads.size() - 1; i++) {
  Thread hiloActual = threads.get(i);
  Thread siguienteHilo = threads.get(i + 1);

  System.out.println(hiloActual.getName() + " Estado: " + hiloActual.getState());
  System.out.println(siguienteHilo.getName() + " Estado: " + siguienteHilo.getState());

  if (hiloActual.getState() == Thread.State.TERMINATED && (siguienteHilo.getState() == Thread.State.TIMED_WAITING)) {
      System.out.println("Interrumpiendo " + siguienteHilo.getName());
      siguienteHilo.interrupt(); // Interrumpimos el hilo siguiente
      try {
        threads.get(i).join(); 
        Thread.sleep(100);
      } catch (InterruptedException e) {
        System.out.println("Se interrumpio el apagado en serie.");
      }
      
      // Interrumpimos solo uno
  }
  // Ahora revisamos si el siguiente hilo o el actual están BLOCKED volvemos un paso atras para que vuelva a revisar el estado de los procesos
  else if( siguienteHilo.getState() == Thread.State.BLOCKED || hiloActual.getState() == Thread.State.BLOCKED){ i=-1;} 

  // Si terminaron todos los procesos anteriores y el hilo verificador es RUNNABLE, interrumpimos el log y los verificadores
  if((i == threads.size()-1 || i == threads.size()-2) && hiloActual.getState() == Thread.State.RUNNABLE){ 
    threads.get(0).interrupt(); // Interrumpimos el log
    threads.get(i).interrupt(); // Interrumpimos el verificador 1
    threads.get(i++).interrupt(); // Interrumpimos el verificador 2
    
    try {
      Thread.sleep(100); // Esperamos a que el log termine
      System.exit(0); // Terminamos el programa
    } catch (InterruptedException e) {
      System.out.println("Se interrumpio la finalizacion del programa");
    }
    
  }
}
}
}
