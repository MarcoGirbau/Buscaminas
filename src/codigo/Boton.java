/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import javax.swing.JButton;

/**
 * @author Marco Girbau
 */
public class Boton extends JButton
{
    private int mina = 0;
    private int i = 0;
    private int j = 0;
    private int numMinasAlrededor = 0;
    
    public Boton (int _i, int _j)
    {
        i = _i;
        j = _j;
        this.setBorder(null);
    }

    public int geti() {
        return i;
    }

    public int getj() {
        return j;
    }

    public void setMina(int mina) {
        this.mina = mina;
    }

    public void setNumMinasAlrededor(int numMinasAlrededor) {
        this.numMinasAlrededor = numMinasAlrededor;
    }

    public int getMina() {
        return mina;
    }
    
}
