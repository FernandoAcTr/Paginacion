package model;

public class Frame {
    private Proceso proceso;
    private int ocupado;
    private int size;

    private String descripcion;

    public Frame(int size) {
        this.size = size;
        ocupado = 0;
    }

    public Proceso getProceso() {
        return proceso;
    }

    /**
     * Almacena un proceso en el frame. Si el espacio requerido es mayor al size del frame, ocupa el frame completo.
     * De lo contrario ocupa solo lo requerido
     *
     * @param proceso   el proceso que se requiere almacenar
     * @param requerido el espacio requerido para almacenar el proceso
     * @param numFrame  si el proceso es una subdivision de un proceso grande, el numero de frames que lleva ocupados
     */
    public void setProceso(Proceso proceso, int requerido, int numFrame) {
        this.proceso = proceso;

        if (requerido >= size)
            this.ocupado = size;
         else
            this.ocupado = requerido;

         //Si sera necesario hacer una subdivision del proceso se asigna una descripcion
        if (requerido > size || numFrame > 1)
            descripcion = proceso.getPID() + "." + numFrame;
        else
            descripcion = proceso.getPID();
    }

    public void sacarProceso() {
        proceso = null;
        ocupado = 0;
        descripcion = "";
    }

    public int getFragmetation() {
        return size - ocupado;
    }

    public int getOcupado() {
        return ocupado;
    }

    public int getSize() {
        return size;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isEmpty() {
        return proceso == null;
    }
}
