package model;

import java.util.ArrayList;
import java.util.List;

public class Swapping {
    private ArrayList<Proceso> procesos;

    public Swapping() {
        procesos = new ArrayList<>();
    }

    public void colocarProceso(Proceso proceso) {
        procesos.add(proceso);
    }

    /**
     * Regresa los procesos necesarios de la cola swapp para ocupar el espacio de la pagina
     *
     * @param totalFree Total de espacio disponible en la pagina, para ocupar por procesos de swapp
     * @return ArrayList - lista de procesos que se sacaron de swap.
     */
    public List<Proceso> sacarProceso(int totalFree) {

        ArrayList<Proceso> procesSwap = new ArrayList<>();

        Proceso proceso;

        do{
            proceso = getProcesoBySize(totalFree);
            if (proceso != null) {
                procesos.remove(proceso);
                procesSwap.add(proceso);
                totalFree -= proceso.getSize();
            }
        }while (proceso != null);

        return procesSwap;
    }

    private Proceso getProcesoBySize(int size) {
        for (Proceso proceso : procesos)
            if (proceso.getSize() <= size)
                return proceso;

        return null;
    }

    public ArrayList<Proceso> getProcesos() {
        return procesos;
    }
}
