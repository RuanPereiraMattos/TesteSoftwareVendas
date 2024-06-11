package com.ruanmattos.main.jinternalframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.grouplayout.jinternalframe.JIFEstoqueGroupLayout;
import com.ruanmattos.main.jmenubar.MyJMB;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
//</editor-fold>

/**
 * Class JIFEstoque (JInternalFrame Estoque) utilizada para mostrar os produtos
 * do estoque juntamente com suas respectivas quantidades
 *
 * @author Ruan Pereira Mattos
 */
public class JIFEstoque extends JInternalFrame {

    protected final MyJMB myJMB;

    /**
     * Construtor utilizado pela classe MyJMB(My JMenuBar)
     *
     * @param myJMB
     */
    public JIFEstoque(MyJMB myJMB) {
        super("Estoque", true, true, true, true);
        this.myJMB = myJMB;
        JIFEstoqueGroupLayout jifegl = new JIFEstoqueGroupLayout(getContentPane(), this);
        setLayout(jifegl);
        pack();
        setVisible(true);        
        if (myJMB.getJfm().getJdp().getHeight() < getHeight()) {
            setSize(getWidth(), myJMB.getJfm().getJdp().getHeight());
        }
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                myJMB.setJIFEstoqueFalse();
                super.internalFrameClosing(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }

        });
    }

    public MyJMB getMyJMB() {
        return myJMB;
    }

}
