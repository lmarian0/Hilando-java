package main;

import java.util.concurrent.ThreadLocalRandom;

public class DespacharPedido extends Proceso{
    public DespacharPedido(EmpresaLogistica eCommerce) {
        super(eCommerce);
    }
    @Override
    public void run() {
        if (verificarDatos())
        {   eCommerce.buscarCasilleroLibre(); // método dentro de Empresa que hace la lógica real
            System.out.println("Despachazao Pedido");
            System.out.println(eCommerce.getCasillero(0).getEstado());
        }
        else {
            registro.delPreparacion(casillero.getPedido()); /**REVISAR PEDIDOTEMP*/
            throw new Error("No se pudo despachar el pedido");
        }
    }

    public boolean verificarDatos(){
        ThreadLocalRandom probabilidad = ThreadLocalRandom.current();
        probabilidad.nextDouble(1);
        return probabilidad.nextDouble() <= 0.85;
    }
}
