public class GestorProceso {
    private EmpresaLogistica empresa;

    public GestorProceso(EmpresaLogistica empresa) {
        this.empresa = empresa;
    }

    public void iniciarProcesos(){
        iniciarPreparacionPedido();
        iniciarDespachoPedido();
        iniciarEntregaPedido();

        
    }

    public void iniciarPreparacionPedido(){
        Thread hiloPreparacion1 = new Thread(new PrepararPedido(empresa));
        Thread hiloPreparacion2 = new Thread(new PrepararPedido(empresa));
        Thread hiloPreparacion3 = new Thread(new PrepararPedido(empresa));

        hiloPreparacion1.start();
        hiloPreparacion2.start();
        hiloPreparacion3.start();

        try {
            hiloPreparacion1.join();
            hiloPreparacion2.join();
            hiloPreparacion3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void iniciarDespachoPedido(){
        Thread hiloDespacho1 = new Thread(new DespacharPedido(empresa));
        Thread hiloDespacho2 = new Thread(new DespacharPedido(empresa));

        hiloDespacho1.start();
        hiloDespacho2.start();

        try {
            hiloDespacho1.join();
            hiloDespacho2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void iniciarEntregaPedido(){
        Thread hiloEntrega1 = new Thread(new EntregarPedido(empresa));
        Thread hiloEntrega2 = new Thread(new EntregarPedido(empresa));
        Thread hiloEntrega3 = new Thread(new EntregarPedido(empresa));

        hiloEntrega1.start();
        hiloEntrega2.start();
        hiloEntrega3.start();

        try {
            hiloEntrega1.join();
            hiloEntrega2.join();
            hiloEntrega3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



