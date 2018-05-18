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
    Icon mina = new ImageIcon(getClass().getResource("/imagenes/jorgecisnerosmini.jpg"));
    Icon pregunta = new ImageIcon(getClass().getResource("/imagenes/bombquestion.gif"));
    
    Boton [][] arrayBotones = new Boton[filas][columnas];
    
    boolean cancer [][] = new boolean[filas][columnas];
    int sida [][] = new int[filas][columnas];
    
    /**
     * Creates new form VentanaBuscaminas
     */
    public VentanaBuscaminas() 
    {
        initComponents();
        setSize(400,400);
        setLocationRelativeTo(null);
        setTitle("Buscaminas by Marco Girbau");
        setResizable(false);
        getContentPane().setLayout(new GridLayout(filas, columnas));
        jLabel1.setText("Has perdido por imbecil");
        jButton1.setText("Reiniciar");
        jDialog.setLocationRelativeTo(null);
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
        for(int k = 0; k < filas; k++)
        {
            for(int m = 0; m < columnas; m++)
            {
                cancer[k][m] = false;
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
            miboton.setText("!!");
        }
        if(e.getButton() == MouseEvent.BUTTON2)
        {
            miboton.setText("");
        }
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            miboton.setEnabled(false);
            int valorboton = miboton.getValor();
            if(valorboton != 0)
            {
                String valor = Integer.toString(miboton.getValor());
                miboton.setText(valor);
            }
            else
            {
                buscaEspaciosDeMierda(miboton.geti(), miboton.getj());
            }
            if(miboton.getMina() == 1)
            {
                miboton.setIcon(mina);
                jDialog.setVisible(true);
                jDialog.setSize(250,150);
                System.out.println("Has perdido por imbecil");
                mierdaSeca();
                
                //System.exit(0);
                //this.setEnabled(false);
            }
        }
    }
    
    private void ponMinas(int numeroMinas)
    {
        Random r = new Random();
        for(int i = 0; i < numeroMinas; i++)
        {
            int f = r.nextInt(filas);
            int c = r.nextInt(columnas);
            while(arrayBotones[f][c].getMina() == 1)
            {
                f = r.nextInt(filas);
                c = r.nextInt(columnas);
            }
            arrayBotones[f][c].setMina(1);
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
                for (int k = -1; k <= 1; k++)
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
                    arrayBotones[i][j].setValor(minas);
                    sida[i][j] = minas;
                }
                minas = 0;
            }
        }
    }
    
    private void buscaEspaciosDeMierda(int i , int j)
    {
        if(j > 0 && arrayBotones[i][j - 1].isEnabled() && arrayBotones[i][j - 1].getValor() == 0)
        {
            arrayBotones[i][j - 1].setEnabled(false);//La mina de la izquierda
            buscaEspaciosDeMierda(i, j - 1); //Viva la recursividad
            //Si boton esta enabled y valor es 0 entonces deshabilito el boton e invoco la funcion buscaEspaciosDeMierda con i, j - 1
        }
        if(i > 0 && arrayBotones[i - 1][j].isEnabled() && arrayBotones[i - 1][j].getValor() == 0)
        {
            arrayBotones[i - 1][j].setEnabled(false);//La mina de arriba
            buscaEspaciosDeMierda(i - 1, j); //Viva la recursividad
            //Si boton esta enabled y valor es 0 entonces deshabilito el boton e invoco la funcion buscaEspaciosDeMierda con i - 1, j
        }
        if(i < filas - 1 && arrayBotones[i + 1][j].isEnabled() && arrayBotones[i + 1][j].getValor() == 0)
        {
            arrayBotones[i + 1][j].setEnabled(false);//La mina de abajo
            buscaEspaciosDeMierda(i + 1, j); //Viva la recursividad
            //Si boton esta enabled y valor es 0 entonces deshabilito el boton e invoco la funcion buscaEspaciosDeMierda con i + 1, j
        }
        if(j < columnas - 1 && arrayBotones[i][j + 1].isEnabled() && arrayBotones[i][j + 1].getValor() == 0)
        {
            arrayBotones[i][j + 1].setEnabled(false);//La mina de la derecha
            buscaEspaciosDeMierda(i, j + 1); //Viva la recursividad
            //Si boton esta enabled y valor es 0 entonces deshabilito el boton e invoco la funcion buscaEspaciosDeMierda con i, j + 1
        }
    }
    
    private void mierdaSeca()
    {
        for(int i = 0; i < filas; i++)
        {
            for(int j = 0; j < columnas; j++)
            {
                if(arrayBotones[i][j].getMina() == 0)
                {
                    if(arrayBotones[i][j].getNumMinasAlrededor() != 0)
                    {
                       arrayBotones[i][j].setText(String.valueOf(arrayBotones[i][j].getNumMinasAlrededor())); 
                    }
                }
                else
                {
                    arrayBotones[i][j].setIcon(mina);
                }
                arrayBotones[i][j].setEnabled(false);
            }
        }
    }
    
    private void reset()
    {
        VentanaBuscaminas sifilis = new VentanaBuscaminas();
        sifilis.setVisible(true);
        dispose();
        jDialog.setVisible(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        jButton1.setText("jButton1");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jDialogLayout = new javax.swing.GroupLayout(jDialog.getContentPane());
        jDialog.getContentPane().setLayout(jDialogLayout);
        jDialogLayout.setHorizontalGroup(
            jDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addContainerGap(295, Short.MAX_VALUE))
        );
        jDialogLayout.setVerticalGroup(
            jDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(234, Short.MAX_VALUE))
        );

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

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        reset();
    }//GEN-LAST:event_jButton1MousePressed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
