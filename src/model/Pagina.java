package model;

public class Pagina {
    Frame[] frames;
    int numPag;
    int size;

    public Pagina(int numPag, int size, int numFrames){

        frames = new Frame[numFrames];
        this.numPag = numPag;
        this.size = size;

        for (int i = 0; i < numFrames; i++)
            frames[i] = new Frame(size / numFrames);
    }

    /**
     * Hace una busqueda secuencial en los frames de la pagina, buscando el primer frame libre.
     * Cuando encuentra un frame libre, mete en proceso en el, si el proceso no cabe en un solo frame, continua
     * buscando frames libres hasta que el proceso se almacene totalmente
     * @param proceso el proceso que se quiere almacenar
     * @return boolean - Si ha colocado exitosamente el proceso en la pagina
     */
    public boolean colocarProceso(Proceso proceso) {
        int faltante = proceso.getSize();
        int framesOcupados = 0;

        for (Frame frame : frames) {
            if (frame.isEmpty()) {

                framesOcupados++;
                frame.setProceso(proceso, faltante, framesOcupados);
                faltante -= frame.getOcupado();

                if (faltante <= 0)
                    return true;
            }
        }

        return false;
    }

    /**
     * Hace una busqueda secuencial en los frames de la pagina buscando en cuales esta almacenado el proceso.
     * Saca al proceso de todos ellos.
     * @param PID El PID del proceso que se quiere sacar de la pagina
     */
    public void sacarProceso(String PID){

        for (Frame frame : frames)
            if (!frame.isEmpty() && frame.getProceso().getPID().equalsIgnoreCase(PID))
                frame.sacarProceso();
    }

    /**
     * Verifica que un proceso quepa en la pagina, mediante su espacio disponible
     * @param proceso el proceso que se quiere almacenar en la pagina
     * @return true si el proceso cabe. false de lo contrario
     */
    public boolean cabeProceso(Proceso proceso) {
        int free = getTotalFree();
        return free >= proceso.getSize();
    }

    /**
     * Obtiene el espacio libre de los frames que no estan ocupados (espacio disponible)
     * @return int - espacio total disponible en la pagina
     */
    public int getTotalFree() {
        int sumTotalFree = 0;

        for (Frame frame : frames)
            if (frame.isEmpty())
                sumTotalFree += frame.getSize();

        return sumTotalFree;
    }

    /**
     * Obtiene el espacio libre de los frames que estan ocupados (espacio fragmentado)
     * @return int - espacio total fragmentado en la pagina
     */
    public int getFragmentation() {
        int sumFrag = 0;

        for (Frame frame : frames)
            if (!frame.isEmpty())
                sumFrag += frame.getFragmetation();

        return sumFrag;
    }

    public float getPercentageFragment(){
        return (float)(getFragmentation() * 100 / size);
    }

    /**
     * Regresa un frame de la pagina.
     * @param numFrame el numero del frame comenzando desde el 1
     * @return
     */
    public Frame getFrame(int numFrame){
        return frames[numFrame-1];
    }

    public int getNumPag() {
        return numPag;
    }

    /**
     * Regresa el numero de frames de la pagina
     * @return int numero de frames
     */
    public int getTotalFrames(){
        return frames.length;
    }
}
