package com.ruanmattos.main.jframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.data.VendaProdutoData;
import com.ruanmattos.main.grouplayout.JFNovaVendaClientesGroupLayout;
import com.ruanmattos.main.grouplayout.JFNovaVendaPagamentoGroupLayout;
import com.ruanmattos.main.grouplayout.JFNovaVendaProdutosGroupLayout;
import com.ruanmattos.main.jinternalframe.JIFVendas;
import com.ruanmattos.main.jmenubar.MyJMB;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
//</editor-fold>

/**
 * Class JFNovaVenda (JFrame Nova Venda) implementando um único construtor obtendo JIFVendas (JInternalFrame Vendas) ou MyJMB (My JMenuBar) ou até mesmo nullo para os dois parametros pela classe ser
 * chamada através da classe do SystemTray
 *
 * @author Ruan Pereira Mattos
 */
public class JFNovaVenda extends JFrame {

    private JIFVendas jifv = null;
    private MyJMB myJMB = null;
    private final JTabbedPane jtp = new JTabbedPane();
    JPanel painelProdutos = new JPanel();
    JPanel painelClientes = new JPanel();
    JPanel painelPagamento = new JPanel();

    /**
     * Construtor principal da classe obtendo JIFVendas (JInternalFrame Vendas) ou MyJMB (My JMenuBar) ou até mesmo nullo para os dois parametros pela classe ser chamada através da classe do
     * SystemTray
     *
     * @param jifv JInternalFrameVendas
     * @param myJMB MyJMenuBar
     * @throws HeadlessException
     */
    public JFNovaVenda(MyJMB myJMB, JIFVendas jifv) throws HeadlessException {
        super("Nova Venda");
        this.jifv = jifv;
        this.myJMB = myJMB;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(jtp);
        setVisible(true);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (jifv != null) {
                    jifv.getMyJMB().setJFNovaVendaFalse();
                    jifv.getMyJMB().getJfm().setVisible(true);
                } else if (myJMB != null) {
                    myJMB.setJFNovaVendaFalse();
                    myJMB.getJfm().setVisible(true);
                }
                super.windowClosing(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }
        });
    }

    private void initComponents() {
        jtp.addTab("Produtos", painelProdutos);
        jtp.addTab("Clientes", painelClientes);
        jtp.addTab("Pagamento", painelPagamento);
        JFNovaVendaProdutosGroupLayout jFrameNovaVendaProdutosGroupLayout = new JFNovaVendaProdutosGroupLayout(painelProdutos);
        JFNovaVendaClientesGroupLayout jFrameNovaVendaClientesGroupLayout = new JFNovaVendaClientesGroupLayout(painelClientes);
        JFNovaVendaPagamentoGroupLayout jFrameNovaVendaPagamentoGroupLayout = new JFNovaVendaPagamentoGroupLayout(painelPagamento, this, this, jFrameNovaVendaClientesGroupLayout);
        painelProdutos.setLayout(jFrameNovaVendaProdutosGroupLayout);
        painelClientes.setLayout(jFrameNovaVendaClientesGroupLayout);
        painelPagamento.setLayout(jFrameNovaVendaPagamentoGroupLayout);
        jtp.addChangeListener((e) -> {
            if (jtp.getSelectedIndex() == 2) {
                float valor_total_compra = 0;
                for (VendaProdutoData vpd : jFrameNovaVendaProdutosGroupLayout.getListCarrinho()) {
                    valor_total_compra += vpd.valor_total;
                }
                jFrameNovaVendaPagamentoGroupLayout.setListCarrinho(jFrameNovaVendaProdutosGroupLayout.getListCarrinho());
                jFrameNovaVendaPagamentoGroupLayout.setListProdutosData(jFrameNovaVendaProdutosGroupLayout.getListProdutosData());
                jFrameNovaVendaPagamentoGroupLayout.setValues(valor_total_compra);
            }
        });
    }

    public JIFVendas getJifv() {
        return jifv;
    }

    public MyJMB getMyJMB() {
        return myJMB;
    }

    public void destroi() {
        System.gc();
    }

}
