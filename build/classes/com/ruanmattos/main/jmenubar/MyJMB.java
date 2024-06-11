package com.ruanmattos.main.jmenubar;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.database.MySQL;
import com.ruanmattos.main.jframe.JFMain;
import com.ruanmattos.main.jframe.JFNovaVenda;
import com.ruanmattos.main.jframe.JFNovoCliente;
import com.ruanmattos.main.jframe.JFNovoProduto;
import com.ruanmattos.main.jinternalframe.JIFAjuda;
import com.ruanmattos.main.jinternalframe.JIFClientes;
import com.ruanmattos.main.jinternalframe.JIFEstoque;
import com.ruanmattos.main.jinternalframe.JIFProdutos;
import com.ruanmattos.main.jinternalframe.JIFVendas;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
//</editor-fold>

/**
 * Class JMenuBar do JFrame principal
 *
 * @author Ruan Pereira Mattos
 */
public class MyJMB extends JMenuBar {

    private MySQL mySQL = null;
    protected JFMain jfm = null;
    private final JMenu jmMenu = new JMenu("Menu");
    private final JMenu jmClientes = new JMenu("Menu Clientes");
    private final JMenu jmProdutos = new JMenu("Menu Produtos");
    private final JMenu jmEstoque = new JMenu("Menu Estoque");
    private final JMenu jmVendas = new JMenu("Menu Vendas");
    private final JMenu jmAjuda = new JMenu("Menu Ajuda");
    private final JMenuItem jmiSair = new JMenuItem("Sair");
    private final JMenuItem jmiClientes = new JMenuItem("Clientes");
    private final JMenuItem jmiNovoCliente = new JMenuItem("Novo Cliente");
    private final JMenuItem jmiProdutos = new JMenuItem("Produtos");
    private final JMenuItem jmiNovoProduto = new JMenuItem("Novo Produto");
    private final JMenuItem jmiEstoque = new JMenuItem("Estoque");
    private final JMenuItem jmiVendas = new JMenuItem("Vendas");
    private final JMenuItem jmiNovaVenda = new JMenuItem("Nova Venda");
    private final JMenuItem jmiAjuda = new JMenuItem("Ajuda");

    private boolean isJIFClientesOpen = false;
    private JIFClientes jifc = null;
    private boolean isJFNovoClienteOpen = false;
    private JFNovoCliente jfnc = null;
    private boolean isJIFProdutosOpen = false;
    private JIFProdutos jifp = null;
    private boolean isJFNovoProdutoOpen = false;
    private JFNovoProduto jfnp = null;
    private boolean isJIFEstoqueOpen = false;
    private JIFEstoque jife = null;
    private boolean isJIFVendasOpen = false;
    private JIFVendas jifv = null;
    private boolean isJFNovaVendaOpen = false;
    private JFNovaVenda jfnv = null;
    private boolean isJIFAjudaOpen = false;
    private JIFAjuda jifa = null;

    //<editor-fold defaultstate="collapsed" desc="ActionListener">
    ActionListener al = (e) -> {
        if (e.getSource() == jmiSair) {
            System.exit(0);
        } else if (e.getSource() == jmiClientes) {
            if (isJIFClientesOpen) {
                JOptionPane.showMessageDialog(this, "Você já está com uma janela de clientes aberta!", "Clientes", JOptionPane.WARNING_MESSAGE);
            } else {
                if (jifc == null) {
                    jifc = new JIFClientes(this);
                    jfm.getJdp().add(jifc);
                    isJIFClientesOpen = true;
                }
            }
        } else if (e.getSource() == jmiNovoCliente) {
            if (isJFNovoClienteOpen) {
                JOptionPane.showMessageDialog(this, "Você já está com uma janela de novo cliente aberta!", "Novo Cliente", JOptionPane.WARNING_MESSAGE);
            } else {
                jfnc = new JFNovoCliente(this, null);
                isJFNovoClienteOpen = true;
            }
        } else if (e.getSource() == jmiProdutos) {
            if (isJIFProdutosOpen) {
                JOptionPane.showMessageDialog(this, "Você já está com uma janela de produtos aberta!", "Produtos", JOptionPane.WARNING_MESSAGE);
            } else {
                jifp = new JIFProdutos(this);
                jfm.getJdp().add(jifp);
                isJIFProdutosOpen = true;
            }
        } else if (e.getSource() == jmiNovoProduto) {
            if (isJFNovoProdutoOpen) {
                JOptionPane.showMessageDialog(this, "Você já está com uma janela de novo produto aberta!", "Novo Produto", JOptionPane.WARNING_MESSAGE);
            } else {
                jfnp = new JFNovoProduto(this, null);
                isJFNovoProdutoOpen = true;
            }
        } else if (e.getSource() == jmiEstoque) {
            if (isJIFEstoqueOpen) {
                JOptionPane.showMessageDialog(this, "Você já está com uma janela de estoque aberta!", "Estoque", JOptionPane.WARNING_MESSAGE);
            } else {
                jife = new JIFEstoque(this);
                jfm.getJdp().add(jife);
                isJIFEstoqueOpen = true;
            }
        } else if (e.getSource() == jmiVendas) {
            if (isJIFVendasOpen) {
                JOptionPane.showMessageDialog(this, "Você já está com uma janela de vendas aberta!", "Nova Venda", JOptionPane.WARNING_MESSAGE);
            } else {
                jifv = new JIFVendas(this);
                jfm.getJdp().add(jifv);
                isJIFVendasOpen = true;
            }
        } else if (e.getSource() == jmiNovaVenda) {
            if (isJFNovaVendaOpen) {
                JOptionPane.showMessageDialog(this, "Você não pode iniciar uma nova venda estando com outra em andamento!", "Nova Venda", JOptionPane.WARNING_MESSAGE);
            } else {
                if (verificaEstoque()) {
                    jfnv = new JFNovaVenda(this, null);
                    isJFNovaVendaOpen = true;
                }else{
                    JOptionPane.showMessageDialog(this, "Você não pode iniciar uma nova venda não tendo produto no estoque!", "Nova Venda", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    };
    //</editor-fold>

    public MyJMB(JFMain jfm) {
        this.jfm = jfm;
        jmiSair.addActionListener(al);
        jmiClientes.addActionListener(al);
        jmiNovoCliente.addActionListener(al);
        jmiProdutos.addActionListener(al);
        jmiNovoProduto.addActionListener(al);
        jmiEstoque.addActionListener(al);
        jmiVendas.addActionListener(al);
        jmiNovaVenda.addActionListener(al);
        jmMenu.add(jmiSair);
        jmClientes.add(jmiClientes);
        jmClientes.add(jmiNovoCliente);
        jmProdutos.add(jmiProdutos);
        jmProdutos.add(jmiNovoProduto);
        jmEstoque.add(jmiEstoque);
        jmVendas.add(jmiVendas);
        if (verificaEstoque()) {
            jmVendas.add(jmiNovaVenda);
        }
        add(jmMenu);
        add(jmClientes);
        add(jmProdutos);
        add(jmEstoque);
        add(jmVendas);
    }

    //<editor-fold defaultstate="collapsed" desc="VerificaEstoque">
    public boolean verificaEstoque() {
        boolean a = false;
        try {
            if (mySQL == null) {
                mySQL = new MySQL();
            }
            mySQL.st = mySQL.conn.createStatement();
            mySQL.rs = mySQL.st.executeQuery("select * from estoque where quantidade > 0");
            a = mySQL.rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(MyJMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="JFMain">
    public JFMain getJfm() {
        return jfm;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setJFNovoClienteFalse">
    public void setJFNovoClienteFalse() {
        jfnc = null;
        isJFNovoClienteOpen = false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setJFNovoProdutoFalse">
    public void setJFNovoProdutoFalse() {
        jfnp = null;
        isJFNovoProdutoOpen = false;
        //System.gc();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setJFNovaVendaFalse">
    public void setJFNovaVendaFalse() {
        jfnv = null;
        isJFNovaVendaOpen = false;
        setJMINovaVenda();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setJIFClientesFalse">
    public void setJIFClientesFalse() {
        jifc = null;
        isJIFClientesOpen = false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setJIFProdutosFalse">
    public void setJIFProdutosFalse() {
        jifp = null;
        isJIFProdutosOpen = false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setJIFEstoqueFalse">
    public void setJIFEstoqueFalse() {
        jife = null;
        isJIFEstoqueOpen = false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setJIFVendasFalse">
    public void setJIFVendasFalse() {
        jifv = null;
        isJIFVendasOpen = false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setJMINovaVenda">
    public void setJMINovaVenda() {
        if (verificaEstoque()) {
            jmVendas.add(jmiNovaVenda);
        } else {
            jmVendas.remove(jmiNovaVenda);
        }
    }
    //</editor-fold>

}
