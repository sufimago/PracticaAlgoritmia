package backtrackingreinas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Sufi
 */
public class BacktrackingReinas {

    int dimension;
    int[][] tablero = new int[dimension][dimension];
    int[][][] soluciones;
    int solCounter = 0;

    public void MetodoPrincipal() {
        
        dimension=Integer.parseInt(JOptionPane.showInputDialog("INTRODUCE EL TAMAÑO: "));
        soluciones = new int[10000][dimension][dimension];
        initSoluciones();
        tablero = generarTablero(dimension, dimension);
        colocarReina(tablero, 0);
        int Nsol = solCounter;
        solCounter = 0;
        System.out.println("Número de soluciones: " + Nsol);
        for(int i=0;i<Nsol;i++){
            GUI();
            LT.readInt();
            solCounter++;
        }
        System.out.println("Todas las soluciones han sido mostradas");
    }

    //Método que genera tablero de ajedrez en forma de array int
    public int[][] generarTablero(int fila, int columna) {
        int[][] tableroGenerado = new int[fila][columna];
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                tableroGenerado[i][j] = 0;
            }

        }
        return tableroGenerado;
    }

    //Método que resuelve el problema de las n reinas
    public boolean colocarReina(int tablero[][], int etapa)
    {
        //Si todas las reinas estan colocadas entonces se guarda la solución
        if (etapa >= dimension){
            guardarSoluciones(tablero);
            solCounter++;
            return false;
        }
 
        //Se recorre fila por fila en la misma columna
        for (int i = 0; i < dimension; i++) {          
            //Se comprueba si la reina se puede colocar en esa posición
            if (validarPosReina(tablero, i, etapa)) {
                //Coloca esta reina en posición [i] [col]
                tablero[i][etapa] = 1;
 
                //Recursividad para colocar el resto de reinas
                if (colocarReina(tablero, etapa + 1) == true){
                    return true;
                }
                //Si la reina recién colocada no constituye a una solución
                //entonces se irá hacia atrás y se eliminará esa reina.
                tablero[i][etapa] = 0; // BACKTRACKING
            }
        }
        return false;
    }

    /*Método que comprueba si una reina se puede meter o no.
    Notese que como vamos insertando las reinas de izquierda a derecha,
    tan solo hace falta hacer la comprobación a la izquierda del tablero.*/
    public boolean validarPosReina(int tablero[][], int fila, int columna) {
        //Comprobación fila izquierda
        for (int i = 0; i < columna; i++) {
            if (tablero[fila][i] == 1) {
                return false;
            }
        }
        
        //Comprobación diagonal superior izquierda
        for (int i = fila, j = columna; i >= 0 && j >= 0; i--, j--) {
            if (tablero[i][j] == 1) {
                return false;
            }
        }
        
        //Comprobación diagonal inferior izquierda
        for (int i = fila, j = columna; j >= 0 && i < tablero.length; i++, j--) {
            if (tablero[i][j] == 1) {
                return false;
            }
        }
        //Si las comprobaciones han sido satisfactorias, se devuelve true.
        return true;
    }

    public void imprimirTablero(int tablero[][]) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                System.out.print(tablero[i][j] + ",");
            }
            System.out.println();
        }
    }

    //Método que guarda todas las posibles soluciones
    public void initSoluciones() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < tablero.length; j++) {
                for (int k = 0; k < tablero.length; k++) {
                    soluciones[i][j][k] = 0;
                }
            }
        }
    }

    //Metodo que guarda el array de soluciones
    public void guardarSoluciones(int[][] tablero) {

        for (int j = 0; j < dimension; j++) {
            for (int k = 0; k < dimension; k++) {
                soluciones[solCounter][j][k] = tablero[j][k];
            }
        }

    }

    JFrame frame = new JFrame("N REINAS");

    public void GUI() {

        int frameWidth = 512;
        int frameHeight = 512;
        frame.setBounds(400, 100, frameWidth + 5, frameHeight + 25);
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.setFont(new Font((Font.MONOSPACED), Font.BOLD, 20));
                boolean white = true;
                for (int i = 0; i < dimension; i++) {
                    for (int j = 0; j < dimension; j++) {
                        if (white) {
                            g.setColor(Color.white);
                        } else {
                            g.setColor(Color.black);
                        }
                        g.fillRect(j * frameWidth / dimension, i * frameHeight / dimension, frameWidth / dimension, frameHeight / dimension);
                        if (!white) {
                            g.setColor(Color.white);
                        } else {
                            g.setColor(Color.black);
                        }
                        if(soluciones[solCounter][i][j] == 1){
                            g.drawString("R", (j * frameWidth / dimension + ((frameWidth / dimension) / 2)) - 10, i * frameHeight / dimension + ((frameHeight / dimension) / 2));
                        }
                        white = !white;
                    }
                    if (dimension % 2 == 0) {
                        white = !white;
                    }
                }
            }
        };

        frame.add(panel);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);

    }

    public static void main(String[] args) {
        new BacktrackingReinas().MetodoPrincipal();
    }

}
