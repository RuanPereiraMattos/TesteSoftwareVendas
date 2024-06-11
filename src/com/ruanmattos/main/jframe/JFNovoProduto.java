package com.ruanmattos.main.jframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.jmenubar.MyJMB;
import com.ruanmattos.main.grouplayout.jframe.JFNovoProdutoGroupLayout;
import com.ruanmattos.main.grouplayout.jinternalframe.JIFProdutosGroupLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
//</editor-fold>

/**
 * Classe JFNovoProduto (JFrame Novo Produto) que extende a classe JFrame
 *
 * @author Ruan Pereira Mattos
 */
public class JFNovoProduto extends JFrame {

    private final JIFProdutosGroupLayout jifpgl;
    private final MyJMB myJMB;

    /**
     * Construtor utilizado pela classe Main através do SystemTray
     *
     * @param myJMB
     * @param jifpgl
     * @throws HeadlessException
     */
    public JFNovoProduto(MyJMB myJMB, JIFProdutosGroupLayout jifpgl) {
        setTitle("JIFProdutos");
        this.myJMB = myJMB;
        this.jifpgl = jifpgl;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JFNovoProdutoGroupLayout jfNovoProdutoGroupLayout = new JFNovoProdutoGroupLayout(this.getContentPane(), this);
        setLayout(jfNovoProdutoGroupLayout);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(300, this.getHeight());
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (myJMB != null) {
                    myJMB.setJFNovoProdutoFalse();
                    myJMB.getJfm().setVisible(true);
                } else if (jifpgl != null) {
                    jifpgl.getJifp().getMyJMB().setJFNovoProdutoFalse();
                    jifpgl.getJifp().getMyJMB().getJfm().setVisible(true);
                }
                //JFNovoProduto.this.dispose();
                super.windowClosing(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }
        });
        /*if (!jfNovoProdutoGroupLayout.getValor()) {
        closing();
        dispose();
        }*/
    }

    public JIFProdutosGroupLayout getJifpgl() {
        return jifpgl;
    }

    public MyJMB getMyJMB() {
        return myJMB;
    }

}
