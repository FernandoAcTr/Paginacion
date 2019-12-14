package model;

public class Test {
    public static void main(String [] args){
        try {
            Memoria memoria = new Memoria(4, 1024, 4);

            Proceso p1 = new Proceso(64);
            Proceso p2 = new Proceso(50);
            Proceso p3 = new Proceso(100);

            memoria.colocarProceso(p1);
            memoria.colocarProceso(p2);
            memoria.colocarProceso(p3);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
