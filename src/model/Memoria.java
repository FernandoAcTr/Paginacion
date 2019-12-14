package model;

import java.util.List;

public class Memoria {

    private Pagina paginas[];
    private int size;
    private Swapping swapping;

    public Memoria(int numPags, int size, int framesPerPage) throws Exception {

        if( (numPags & (numPags-1)) != 0)
            throw new Exception("El numero de paginas debe ser potencia de 2");

        if( (framesPerPage & (framesPerPage-1)) != 0)
            throw new Exception("El numero de frames en cada pagina debe ser potencia de 2");

        if( (size & (size-1)) != 0)
            throw new Exception("El tamaño de memoria debe ser potencia de 2");

        this.size = size;
        paginas = new Pagina[numPags];
        swapping = new Swapping();

        for (int i = 0; i < numPags; i++) {
            paginas[i] = new Pagina(i+1, size/numPags, framesPerPage);
        }
    }

    /**
     * Busca la primera pagina en donde quepa el proceso. Si no cabe en ninguna pagina manda el proceso a Swap
     * @param proceso El proceso en donde se quiere almacenar
     * @return boolean True si el proceso fue colocado en la memoria, false si se fue a swapping
     */
    public boolean colocarProceso(Proceso proceso){
        boolean swap = true;
        boolean colocado = false;

        for (Pagina pagina : paginas){
            if(pagina.cabeProceso(proceso) && !colocado) {
                proceso.setNumPag(pagina.getNumPag());
                colocado = pagina.colocarProceso(proceso);
                swap = false;
            }
        }

        if (swap)
            swapping.colocarProceso(proceso);

        return colocado;
    }

    /**
     * Saca un proceso de la memoria
     * @param PID El PID del proceso que se quiere sacar
     * @param numPag La pagina en donde esta el proceso que se quiere sacar
     * @return List - Lista de procesos que se sacaron de swap con el espacio liberado de la pagina.
     */
    public List<Proceso> sacarProceso(String PID, int numPag){
        Pagina page = paginas[numPag-1];
        page.sacarProceso(PID);

        //despues de sacar un proceso, buscar en swap si no existe alguno que ocupe su lugar
        int free = page.getTotalFree();
        List<Proceso> listSwap = swapping.sacarProceso(free);

        for (Proceso proceso : listSwap){
            page.colocarProceso(proceso);
            proceso.setNumPag(page.getNumPag());
        }

        return listSwap;
    }

    /**
     * Regresa una determinada pagina de la memoria
     * @param numPag la pagina que se desea comenzando desde el 1
     * @return
     */
    public Pagina getPagina(int numPag){
        return paginas[numPag-1];
    }

    public int getTotalPags(){
        return paginas.length;
    }

    public int getSize() {
        return size;
    }

    public Swapping getSwapping() {
        return swapping;
    }
}
