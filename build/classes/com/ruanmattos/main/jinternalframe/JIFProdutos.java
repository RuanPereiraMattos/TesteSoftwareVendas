package com.ruanmattos.main.jinternalframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.grouplayout.jinternalframe.JIFProdutosGroupLayout;
import com.ruanmattos.main.jmenubar.MyJMB;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
//</editor-fold>

/**
 * Class JInternalFrame "filha" da classe MyJMB utilizada para mostrar a lista
 * de clientes cadastrado até o momento atual no banco de dados MySQL
 *
 * @author Ruan Pereira Mattos
 */
public class JIFProdutos extends JInternalFrame {

    protected final MyJMB myJMB;

    /**
     * Construtor único da class JIFProduto (JInternalFrame Produtos) que
     * extende a classe JInternalFrame
     *
     * @param myJMB
     */
    public JIFProdutos(MyJMB myJMB) {
        super("Produtos", true, true, true, true);
        this.myJMB = myJMB;
        JIFProdutosGroupLayout jifpgl = new JIFProdutosGroupLayout(getContentPane(), this);
        setLayout(jifpgl);
        pack();
        setVisible(true);
        if (myJMB.getJfm().getJdp().getHeight() < getHeight()) {
            setSize(getWidth(), myJMB.getJfm().getJdp().getHeight());
        }
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                myJMB.setJIFProdutosFalse();
                super.internalFrameClosing(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }

        });
        //myJMB.getJfm().setSize(getWidth(), getHeight());
    }

    public MyJMB getMyJMB() {
        return myJMB;
    }

    public void setSize() {

    }

}
