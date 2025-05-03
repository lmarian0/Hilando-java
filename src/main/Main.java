public class Main {
    public static void main(String[] args) {
       EmpresaLogistica empresa = new EmpresaLogistica();

       //ETAPA 1: PrepararPedido (3 hilos)

       for (int i = 0; i<3; i++){
        Thread preparacion = new Thread(new PrepararPedido(empresa), "Preparation_thread " + i+1);
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
