/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author Marco Girbau
 */
public class VentanaBuscaminas extends javax.swing.JFrame 
{
    int filas = 8;
    int columnas = 8;
    int numeroMinas = 10;
    
    Icon bandera = new ImageIcon(getClass().getResource("/imagenes/bombflagged.gif"));
    Icon mina = new ImageIcon(getClass().getResource("/imagenes/bombdeath.gif"));
    Icon pregunta = new ImageIcon(getClass().getResource("/imagenes/bombquestion.gif"));
    
    Boton [][] arrayBotones = new Boton[filas][columnas];
    
    /**
     * Creates new form VentanaBuscaminas
     */
    public VentanaBuscaminas() 
    {
        initComponents();
        setSize(400,400);
        setTitle("Buscaminas by Marco Girbau");
        setResizable(false);
        getContentPane().setLayout(new GridLayout(filas, columnas));
        for(int i = 0; i < filas; i++)
        {
            for(int j = 0; j < columnas; j++)
            {
                Boton boton = new Boton(i,j);
                getContentPane().add(boton);
                arrayBotones[i][j] = boton;
                boton.addMouseListener(new MouseAdapter() 
                {
                    @Override
                    public void mousePressed(MouseEvent evt)
                    {
                        botonPulsado(evt);
                    }
                });
            }
        }
        ponMinas(numeroMinas);
        cuentaMinas();
    }
    
    private void botonPulsado(MouseEvent e)
    {
        Boton miboton = (Boton) e.getComponent();
        if(e.getButton() == MouseEvent.BUTTON3)
        {
            miboton.setIcon(bandera);
        }
        if(e.getButton() == MouseEvent.BUTTON2)
        {
            miboton.setIcon(pregunta);
        }
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            miboton.setEnabled(false);
        }
    }
    
    private void ponMinas(int numeroMinas)
    {
        Random r = new Random();
        for(int i = 0; i < numeroMinas; i++)
        {
            int f = r.nextInt(filas);
            int c = r.nextInt(columnas);
            if(arrayBotones[f][c].getMina() == 0)
            {
                arrayBotones[f][c].setMina(1);
            }
            if(arrayBotones[f][c].getMina() == 1)
            {
                arrayBotones[f][c].setIcon(mina);
            }
            //arrayBotones[f][c].setText("m");
        }
    }
    
    //cuentaMinas es un metodo que para cada boton calcula el numero 
    //de minas que tiene alrededor
    private void cuentaMinas()
    {
        int minas = 0;
        for(int i = 0; i < filas; i++)
        {
            for(int j = 0; j < columnas; j++)
            {
//                if((i > 0) && (j > 0) && (i < filas - 1) && (j < columnas - 1))
//                {
//                    minas += arrayBotones[i - 1][j - 1].getMina();//La mina de arriba a la izquierda
//                    minas += arrayBotones[i][j - 1].getMina();//La mina de la izquierda
//                    minas += arrayBotones[i + 1][j - 1].getMina();//La mina de abajo a la izquierda
//                    
//                    minas += arrayBotones[i - 1][j].getMina();//La mina de arriba
//                    minas += arrayBotones[i + 1][j].getMina();//La mina de abajo
//                    
//                    minas += arrayBotones[i - 1][j + 1].getMina();//La mina de arriba a la derecha
//                    minas += arrayBotones[i][j + 1].getMina();//La mina de la derecha
//                    minas += arrayBotones[i + 1][j + 1].getMina();//La mina de abajo a la derecha
//                }
                for (int k = -1; k <= 1; k++)//Este bucle recorre supuestamente, porque tengo que arreglarlo, las 8 casillas de alrededor
                {
                    for (int m = -1; m <= 1; m++)
                    {
                        if ((i + k >= 0) && (j + m >= 0) && (i + k < filas) && (j + m < columnas))
                        {
                            minas += arrayBotones[i + k][j + m].getMina();
                        }
                    }
                }
                
                arrayBotones[i][j].setNumMinasAlrededor(minas);
                
                //TODO comentar la siguiente parte para que no aparezcan los numeros al iniciar la partida
                if(arrayBotones[i][j].getMina() == 0 && arrayBotones[i][j].getNumMinasAlrededor() != 0)
                {
                    arrayBotones[i][j].setText(String.valueOf(minas));
                }
                minas = 0;
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaBuscaminas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
