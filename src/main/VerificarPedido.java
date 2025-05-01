package main;

import java.util.concurrent.ThreadLocalRandom;

public class VerificarPedido extends Proceso{
    public VerificarPedido(EmpresaLogistica eCommerce) {
        super(eCommerce);
    }
    @Override
    public void run() {
        if (verificarDatos())
        {//eCommerce.verificacion();} // método dentro de Empresa que hace la lógica real
        }else {throw new Error("No se pudo verificar la entrega del pedido");}
    }

    public boolean verificarDatos(){
        ThreadLocalRandom probabilidad = ThreadLocalRandom.current();
        probabilidad.nextDouble(1);
        return probabilidad.nextDouble() <= 0.95;
    }
}
