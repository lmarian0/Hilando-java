import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogEstadisticas implements Runnable {
    private EmpresaLogistica empresa;
    private long startTime;

    public LogEstadisticas(EmpresaLogistica empresa){
        this.empresa = empresa;
        this.startTime = System.currentTimeMillis(); 
    }

    @Override
    public void run(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Registro de estadisticas"))){
            while (!Thread.currentThread().isInterrupted()){
                try{
                    //Obtencion de estadisticas
                    int pedidosFallidos = empresa.getRegistroPedidos().getFallidos().size();
                    int pedidosVerificados = empresa.getRegistroPedidos().getVerificados().size();

                    //Registro de estadisticas en archivo de salida
                    writer.write("Pedidos fallidos: "+ pedidosFallidos + 
                    ", Pedidos verificados: " + pedidosVerificados);
                    writer.newLine();
                    writer.flush();

                    Thread.sleep(200); //Registra cada 200 ms
                }
                catch(InterruptedException e){
                    Thread.interrupted();
                    break;
                }
            }

            // Imprimir estadísticas finales
            imprimirEstadisticasFinales(writer);
        }
        catch(IOException e){
            System.err.println("Error al escribir el archivo de log: " + e.getMessage());
        }
    }

    private void imprimirEstadisticasFinales(BufferedWriter writer) throws IOException{
        long endTime = System.currentTimeMillis();
        long tiempoTotal = endTime - startTime;

        // Estadísticas finales
        writer.write("=== Estadísticas Finales ===");
        writer.newLine();
        writer.write("Tiempo total del programa: " + tiempoTotal + " ms");
        writer.newLine();

        // Estadísticas de casilleros
        writer.write("=== Estadísticas de Casilleros ===");
        writer.newLine();
        int count = 0;
        for (int i = 0; i < 200; i++) {
            Casilleros casillero = empresa.getCasillero(i);
            writer.write("Casillero " + i + ": Ocupaciones totales = " + casillero.getContadorOcupaciones());
            writer.newLine();
            count += casillero.getContadorOcupaciones();
        }
        writer.write("Total de ocupaciones en casilleros: " + count);
        writer.newLine();
        writer.write("Robos a deliberys= " + (500-count));
        writer.flush();
    }
}
