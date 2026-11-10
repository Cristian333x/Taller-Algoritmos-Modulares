import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    static String[] cod = new String[20], prod = new String[20], cat = new String[20];
    static double[] precio = new double[20];
    static int[] stock = new int[20], entrada = new int[40], salida = new int[40];
    static int np = 0, ne = 0, nv = 0;
    static double vendido = 0;

    static String[] codigo = new String[25], nombres = new String[25];
    static double[][] notas = new double[25][3];
    static double[] asistencia = new double[25], definitiva = new double[25];

    static String[] placas = new String[40], torres = new String[40];
    static String[] aptos = new String[40], tipos = new String[40];
    static boolean[] dentro = new boolean[40];

    static String leer(String m) {
        System.out.print(m);
        return sc.nextLine();
    }

    static int entero(String m) {
        return Integer.parseInt(leer(m));
    }

    static double decimal(String m) {
        return Double.parseDouble(leer(m));
    }

    static int hora(String m) {
        String[] h = leer(m).split(":");
        int minutos = Integer.parseInt(h[0]) * 60 + Integer.parseInt(h[1]);
        if (h.length != 2 || minutos < 0 || minutos >= 1440)
            return -1;
        return minutos;
    }

    public static void main(String[] args) {
        int op;
        do {
            System.out.println("\n1.Cafeteria  2.Notas  3.Parqueadero  0.Salir");
            op = entero("Opcion: ");
            if (op == 1) cafeteria();
            else if (op == 2) notas();
            else if (op == 3) parqueadero();
        } while (op != 0);
    }

    static void cafeteria() {
        int op;
        do {
            System.out.println("\n1.Registrar  2.Vender  3.Reporte  0.Volver");
            op = entero("Opcion: ");

            if (op == 1 && np < 20) {
                String c = leer("Codigo: ");
                boolean existe = false;
                for (int i = 0; i < np; i++)
                    if (cod[i].equalsIgnoreCase(c)) existe = true;

                if (!existe) {
                    cod[np] = c;
                    prod[np] = leer("Nombre: ");
                    precio[np] = decimal("Precio: ");
                    stock[np] = entero("Cantidad: ");
                    cat[np] = leer("Categoria: ");

                    if (precio[np] > 0 && stock[np] >= 0 &&
                        (cat[np].equalsIgnoreCase("Bebidas") ||
                         cat[np].equalsIgnoreCase("Panaderia") ||
                         cat[np].equalsIgnoreCase("Snacks"))) np++;
                    else System.out.println("Datos incorrectos.");
                }
            } else if (op == 2) {
                String c = leer("Codigo: ");
                for (int i = 0; i < np; i++)
                    if (cod[i].equalsIgnoreCase(c)) {
                        int n = entero("Cantidad: ");
                        if (n > 0 && n <= stock[i]) {
                            stock[i] -= n;
                            vendido += n * precio[i];
                            System.out.println("Venta realizada.");
                        } else System.out.println("Stock insuficiente.");
                    }
            } else if (op == 3) {
                System.out.println("Total vendido: $" + vendido);
                for (int i = 0; i < np; i++)
                    if (stock[i] == 0) System.out.println("Agotado: " + prod[i]);
            }
        } while (op != 0);
    }

    static void notas() {
        int op;
        do {
            System.out.println("\n1.Registrar  2.Reporte  0.Volver");
            op = entero("Opcion: ");

            if (op == 1 && ne < 25) {
                String c = leer("Codigo: ");
                boolean existe = false;
                for (int i = 0; i < ne; i++)
                    if (codigo[i].equalsIgnoreCase(c)) existe = true;

                if (!existe) {
                    codigo[ne] = c;
                    nombres[ne] = leer("Nombre: ");
                    double suma = 0;

                    for (int j = 0; j < 3; j++) {
                        do {
                            notas[ne][j] = decimal("Nota " + (j + 1) + ": ");
                        } while (notas[ne][j] < 0 || notas[ne][j] > 5);
                        suma += notas[ne][j];
                    }

                    do {
                        asistencia[ne] = decimal("Asistencia (%): ");
                    } while (asistencia[ne] < 0 || asistencia[ne] > 100);

                    definitiva[ne++] = suma / 3;
                }
            } else if (op == 2 && ne > 0) {
                double suma = 0;
                int mejor = 0, peor = 0;

                for (int i = 0; i < ne; i++) {
                    suma += definitiva[i];
                    if (definitiva[i] > definitiva[mejor]) mejor = i;
                    if (definitiva[i] < definitiva[peor]) peor = i;

                    System.out.println(nombres[i] + ": " + definitiva[i] +
                        (definitiva[i] >= 3 ? " Aprobado" :
                        definitiva[i] >= 2 ? " Habilita" : " Reprueba"));
                }

                System.out.println("Promedio: " + suma / ne);
                System.out.println("Mejor: " + nombres[mejor]);
                System.out.println("Peor: " + nombres[peor]);
            }
        } while (op != 0);
    }

    static void parqueadero() {
        int op;
        do {
            System.out.println("\n1.Ingresar  2.Salida  3.Reporte  0.Volver");
            op = entero("Opcion: ");

            if (op == 1 && nv < 40) {
                String p = leer("Placa: ");
                boolean existe = false;

                for (int i = 0; i < nv; i++)
                    if (placas[i].equalsIgnoreCase(p) && dentro[i]) existe = true;

                if (!existe) {
                    placas[nv] = p;
                    torres[nv] = leer("Torre: ");
                    aptos[nv] = leer("Apartamento: ");
                    tipos[nv] = leer("Tipo (Carro/Moto/Bicicleta): ");

                    if (!tipos[nv].equalsIgnoreCase("Carro") &&
                        !tipos[nv].equalsIgnoreCase("Moto") &&
                        !tipos[nv].equalsIgnoreCase("Bicicleta")) {
                        System.out.println("Tipo incorrecto.");
                    } else {
                        entrada[nv] = hora("Entrada (HH:mm): ");
                        if (entrada[nv] >= 0) dentro[nv++] = true;
                        else System.out.println("Hora incorrecta.");
                    }
                } else System.out.println("Vehiculo ya registrado.");

            } else if (op == 2) {
                String p = leer("Placa: ");
                for (int i = 0; i < nv; i++)
                    if (placas[i].equalsIgnoreCase(p) && dentro[i]) {
                        salida[i] = hora("Salida (HH:mm): ");
                        if (salida[i] >= entrada[i]) {
                            dentro[i] = false;
                            int t = salida[i] - entrada[i];
                            System.out.println("Permanencia: " + t / 60 +
                                " horas y " + t % 60 + " minutos.");
                        } else System.out.println("Hora incorrecta.");
                        break;
                    }

            } else if (op == 3) {
                int c = 0, m = 0, b = 0, mayor = -1, pos = -1;

                for (int i = 0; i < nv; i++) {
                    if (tipos[i].equalsIgnoreCase("Carro")) c++;
                    else if (tipos[i].equalsIgnoreCase("Moto")) m++;
                    else b++;

                    if (!dentro[i] && salida[i] - entrada[i] > mayor) {
                        mayor = salida[i] - entrada[i];
                        pos = i;
                    }
                }

                System.out.println("Carros: " + c + " Motos: " + m +
                    " Bicicletas: " + b);
                if (pos >= 0) System.out.println("Mayor permanencia: " + placas[pos]);
            }
        } while (op != 0);
    }
}
