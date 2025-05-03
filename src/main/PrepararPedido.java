// package main;
import java.util.Random;

public class PrepararPedido extends Proceso{
    private Random random = new Random();
    private Object controlCasillero = new Object();

    public PrepararPedido(EmpresaLogistica eCommerce) {
        super(eCommerce);
    }

    private boolean verifVacio(Casilleros casillero){
        if(casillero.getEstado() == EstadoCasillero.VACIO){
            return true;
        }else{
            return false;
        }
    }

    // private Casilleros buscarCasilleroLibre(){
    //     while(true){
    //         int numRand = random.nextInt(201);
    //         synchronized (controlCasillero){
    //             if(verifVacio(eCommerce.getCasillero(numRand))){
    //                 return eCommerce.getCasillero(numRand);
    //             }
    //         }
    //     }
    // }

    private Casilleros buscarCasilleroLibre() {
        int intentos = 0;
        int maxIntentos = 500; // Limitar el número de intentos para evitar bucles infinitos
    
        while (intentos < maxIntentos) {
            int numRand = random.nextInt(201); // Generar un índice aleatorio
            synchronized (controlCasillero) {
                Casilleros casillero = eCommerce.getCasillero(numRand);
                if (verifVacio(casillero)) {
                    return casillero;
                }
            }
            intentos++;
        }
    
        throw new IllegalStateException("No se encontró un casillero libre después de " + maxIntentos + " intentos.");
    }

    @Override
    public void run() {
        // Buscar un casillero libre
        Casilleros casillero = buscarCasilleroLibre();
                    
        synchronized (controlCasillero) {
            casillero.setEstado(EstadoCasillero.OCUPADO);
            eCommerce.getRegistroPedidos().addPreparacion(casillero.getPedido());
        }
    }
}
