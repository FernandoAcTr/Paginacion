package model;

public class Proceso {
    private int size;
    private String PID;
    private static int id = 0;

    private int numPag;

    public Proceso(int size) {
        this.size = size;
        id++;
        PID = "P"+id;
        numPag = -1; //si queda en -1 significa que no se metio a ninguna pagina. Se fue a swapping.
    }

    public int getSize() {
        return size;
    }

    public String getPID() {
        return PID;
    }

    public void setNumPag(int numPag) {
        this.numPag = numPag;
    }

    public int getNumPag() {
        return numPag;
    }

    @Override
    public boolean equals(Object obj) {
        Proceso p = (Proceso) obj;
        return PID.equals(p.getPID());
    }

    @Override
    public String toString() {
        return PID + " " + size;
    }
}
