package com.ruanmattos.main.jframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.grouplayout.jframe.JFNovoClienteGroupLayout;
import com.ruanmattos.main.grouplayout.jinternalframe.JIFClientesGroupLayout;
import com.ruanmattos.main.jmenubar.MyJMB;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
//</editor-fold>

/**
 * Class JFNovoCliente (JFrame Novo Clientes)
 *
 * @author Ruan Pereira Mattos
 */
public class JFNovoCliente extends JFrame {

    private final MyJMB myJMB;
    private final JIFClientesGroupLayout jifcgl;

    /**
     * Função utilizada pela classe Main através do SystemTray para abrir essa
     * janela de novo cliente de forma "sozinha"
     *
     * @param myJMB se estiver usando esta classe a partir do MyJMB, coloque o
     * this no lugar do myJMB
     * @param jifcgl se estiver usando esta classe a partir do JIFClientes -
     * JIFCGroupLayout, coloque o this no lugar do jifc
     * @throws HeadlessException
     */
    public JFNovoCliente(MyJMB myJMB, JIFClientesGroupLayout jifcgl) {
        super("Novo Cliente");
        this.myJMB = myJMB;
        this.jifcgl = jifcgl;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JFNovoClienteGroupLayout jframeNovoClienteGroupLayout = new JFNovoClienteGroupLayout(getContentPane(), this);
        setLayout(jframeNovoClienteGroupLayout);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (myJMB != null) {
                    myJMB.setJFNovoClienteFalse();
                    myJMB.getJfm().setVisible(true);
                } else if (jifcgl != null) {
                    jifcgl.getJifc().getMyJMB().setJFNovoClienteFalse();
                    jifcgl.getJifc().getMyJMB().getJfm().setVisible(true);
                }
                super.windowClosing(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }

        });
    }

    public JIFClientesGroupLayout getJifcgl() {
        return jifcgl;
    }

    public MyJMB getMyJMB() {
        return myJMB;
    }

}
