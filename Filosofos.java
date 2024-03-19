import java.util.Random;

public class Tenedor {

    public static TenedorClas tenedor1 = new TenedorClas("t1", 0);
    public static TenedorClas tenedor2 = new TenedorClas("t2", 0);
    public static TenedorClas tenedor3 = new TenedorClas("t3", 0);
    public static TenedorClas tenedor4 = new TenedorClas("t4", 0);
    public static TenedorClas tenedor5 = new TenedorClas("t5", 0);

    private static boolean running = true;

    public static class TenedorClas {
        public String id;
        public int ocupadoPor;

        public TenedorClas(String id, int oc) {
            this.id = id;
            this.ocupadoPor = oc;
        }
    }

    public static class Monje extends Thread {

        public String monje;
        public int numeroMonje;
        public TenedorClas tenedorIzq;
        public TenedorClas tenedorDer;
        public Random random = new Random();

        public Monje(String nombre, TenedorClas tenedorIzq, TenedorClas tenedorDer, int numeroMonje) {
            this.monje = nombre;
            this.numeroMonje = numeroMonje;
            this.tenedorIzq = tenedorIzq;
            this.tenedorDer = tenedorDer;
        }

        @Override
        public void run() {
            while (running) {
                tomarTenedor();
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void tomarTenedor() {
            synchronized (tenedorIzq) {
                if (tenedorIzq.ocupadoPor == 0) {
                    tenedorIzq.ocupadoPor = numeroMonje;
                    synchronized (tenedorDer) {
                        if (tenedorDer.ocupadoPor == 0) {
                            tenedorDer.ocupadoPor = numeroMonje;
                            System.out.println("El  " + monje + " toma tenedor IZQUIERDO y DERECHO y COME!!!");
                            try {
                                Thread.sleep(random.nextInt(1000));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            tenedorIzq.ocupadoPor = 0;
                            tenedorDer.ocupadoPor = 0;
                            System.out.println("El  " + monje + " SUELTA tenedores y MEDITA");
                        } else {
                            tenedorIzq.ocupadoPor = 0;
                            System.out.println("El  " + monje + " no puede comer, el tenedor DERECHO está ocupado");
                        }
                    }
                } else {
                    System.out.println("El  " + monje + " no puede comer, el tenedor IZQUIERDO está ocupado");
                }
            }
        }
    }

    public static void main(String[] args) {

        Monje monje1 = new Monje("monje1", tenedor1, tenedor2, 1);
        Monje monje2 = new Monje("monje2", tenedor2, tenedor3, 2);
        Monje monje3 = new Monje("monje3", tenedor3, tenedor4, 3);
        Monje monje4 = new Monje("monje4", tenedor4, tenedor5, 4);
        Monje monje5 = new Monje("monje5", tenedor5, tenedor1, 5);
        System.out.println("Presione CTL + C para detener la ejecución");
        monje1.start();
        monje2.start();
        monje3.start();
        monje4.start();
        monje5.start();
    }
}

