package com.ruanmattos.main.jinternalframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.grouplayout.jinternalframe.JIFClientesGroupLayout;
import com.ruanmattos.main.jmenubar.MyJMB;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
//</editor-fold>

/**
 * Class JIFClientes (JInternalFrame Clientes)
 *
 * @author Ruan Pereira Mattos
 */
public class JIFClientes extends JInternalFrame {

    private final MyJMB myJMB;
    private final JIFClientesGroupLayout jifcGroupLayout;

    //<editor-fold defaultstate="collapsed" desc="JIFClientes">
    public JIFClientes(MyJMB myJMB) {
        super("Clientes", true, true, true, true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.myJMB = myJMB;
        jifcGroupLayout = new JIFClientesGroupLayout(getContentPane(), this);
        setLayout(jifcGroupLayout);
        pack();
        setVisible(true);
        if (myJMB.getJfm().getJdp().getHeight() < getHeight()) {
            setSize(getWidth(), myJMB.getJfm().getJdp().getHeight());
        }
        addInternalFrameListener(ifa);
    }
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="InternalFrameAdapter">
    private final InternalFrameAdapter ifa = new InternalFrameAdapter() {
        @Override
        public void internalFrameClosing(InternalFrameEvent e) {
            if (myJMB != null) {
                myJMB.setJIFClientesFalse();
            }
            super.internalFrameClosing(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }
    };
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="getMyJMB">
    public MyJMB getMyJMB() {
        return myJMB;
    }
    //</editor-fold>

}
