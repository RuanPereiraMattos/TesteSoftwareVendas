package com.ruanmattos.main.jinternalframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.grouplayout.jinternalframe.JIFVendasGroupLayout;
import com.ruanmattos.main.jmenubar.MyJMB;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
//</editor-fold>

/**
 * Class JIFVendas (JInternalFrame Vendas)
 *
 * @author Ruan Pereira Mattos
 */
public class JIFVendas extends JInternalFrame {

    protected final MyJMB myJMB;

    public JIFVendas(MyJMB myJMB) {
        super("Vendas", true, true, true, true);
        this.myJMB = myJMB;
        setLayout(new JIFVendasGroupLayout(getContentPane(), this));
        pack();
        setVisible(true);
        if (myJMB.getJfm().getJdp().getHeight() < getHeight()) {
            setSize(getWidth(), myJMB.getJfm().getJdp().getHeight());
        }
        addInternalFrameListener(InternalFrameAdapter);
    }

    //<editor-fold defaultstate="collapsed" desc="getMyJMB">
    public MyJMB getMyJMB() {
        return myJMB;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="InternalFrameAdapter">
    private final InternalFrameAdapter InternalFrameAdapter = new InternalFrameAdapter() {
        @Override
        public void internalFrameClosing(InternalFrameEvent e) {
            myJMB.setJIFVendasFalse();
            super.internalFrameClosing(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

    };
    //</editor-fold>

}
