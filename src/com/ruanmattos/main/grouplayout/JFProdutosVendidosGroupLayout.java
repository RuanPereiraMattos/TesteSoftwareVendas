package com.ruanmattos.main.grouplayout;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.grouplayout.jinternalframe.JIFVendasGroupLayout;
import com.ruanmattos.main.data.ProdutoVendido;
import com.ruanmattos.main.database.MySQL;
import com.ruanmattos.main.jframe.JFProdutosVendidos;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
//</editor-fold>

/**
 *
 * @author Ruan Pereira Mattos
 */
public class JFProdutosVendidosGroupLayout extends GroupLayout {

    private MySQL mySQL = null;

    private int id_venda = -1;

    JLabel nome, descricao, valorUnd, qtd, valorTot;
    JTextField nomeTxt, descricaoTxt, valorUndTxt, qtdTxt, valorTotTxt;
    JButton btnBuscar = new JButton("Buscar");

    private static final String[] columnNames = {"Nome", "Descrição", "Valor Unitário", "Quantidade", "Valor Total"};
    private static final List<ProdutoVendido> arrayListProdutoVendidos = new ArrayList<>();
    private static final List<ProdutoVendido> arrayListProdutoVendidosTmp = new ArrayList<>();
    private static final DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
    private static final JTable table = new JTable(dtm);
    private static final JScrollPane jsp = new JScrollPane(table);

    public JFProdutosVendidosGroupLayout(Container host, int id_venda) {
        super(host);
        this.id_venda = id_venda;
        nome = new JLabel("Nome");
        descricao = new JLabel("Descrição");
        valorUnd = new JLabel("Valor Unitário");
        qtd = new JLabel("Quantidade");
        valorTot = new JLabel("Valor Total");
        nomeTxt = new JTextField();
        descricaoTxt = new JTextField();
        valorUndTxt = new JTextField();
        qtdTxt = new JTextField();
        valorTotTxt = new JTextField();
        nome.setVisible(false);
        nomeTxt.setVisible(false);
        descricao.setVisible(false);
        descricaoTxt.setVisible(false);
        valorUnd.setVisible(false);
        valorUndTxt.setVisible(false);
        qtd.setVisible(false);
        qtdTxt.setVisible(false);
        valorTot.setVisible(false);
        valorTotTxt.setVisible(false);
        btnBuscar.setVisible(false);
        nomeTxt.addKeyListener(ka);
        descricaoTxt.addKeyListener(ka);
        valorUndTxt.addKeyListener(ka);
        qtdTxt.addKeyListener(ka);
        valorTotTxt.addKeyListener(ka);
        btnBuscar.addActionListener(al);
        setHorizontalGroup(setHorizontalGroup());
        setVerticalGroup(setVerticalGroup());
        table.addMouseListener(ma);
        jsp.addMouseListener(ma);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        atualiza();
    }

    //<editor-fold defaultstate="collapsed" desc="setHorizontalGroup">
    private Group setHorizontalGroup() {
        return createParallelGroup()
                .addGroup(
                        createSequentialGroup()
                                .addGroup(
                                        createParallelGroup(Alignment.TRAILING, false)
                                                .addComponent(nome)
                                                .addComponent(descricao)
                                                .addComponent(valorUnd)
                                                .addComponent(qtd)
                                                .addComponent(valorTot)
                                )
                                .addGroup(
                                        createSequentialGroup()
                                                .addGroup(
                                                        createParallelGroup()
                                                                .addComponent(nomeTxt)
                                                                .addComponent(descricaoTxt)
                                                                .addComponent(valorUndTxt)
                                                                .addComponent(qtdTxt)
                                                                .addComponent(valorTotTxt)
                                                )
                                )
                )
                .addComponent(btnBuscar, DEFAULT_SIZE, DEFAULT_SIZE, Integer.MAX_VALUE)
                .addComponent(jsp);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setVerticalGroup">
    private Group setVerticalGroup() {
        return createSequentialGroup()
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(nome)
                                .addComponent(nomeTxt)
                )
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(descricao)
                                .addComponent(descricaoTxt)
                )
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(valorUnd)
                                .addComponent(valorUndTxt)
                )
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(qtd)
                                .addComponent(qtdTxt)
                )
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(valorTot)
                                .addComponent(valorTotTxt)
                )
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(btnBuscar)
                )
                .addComponent(jsp);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="atualiza">
    private void atualiza() {
        try {
            if (mySQL == null) {
                mySQL = new MySQL();
            }
            mySQL.st = mySQL.conn.createStatement();
            String sql = "select * from produtos_vendidos where id_venda=" + id_venda;
            mySQL.rs = mySQL.st.executeQuery(sql);
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListProdutoVendidos.clear();
            while (mySQL.rs.next()) {
                int id_produto = mySQL.rs.getInt("id_produto");
                String nome = mySQL.rs.getString("nome");
                String descricao = mySQL.rs.getString("descricao");
                float valor = mySQL.rs.getFloat("valor");
                int qtd = mySQL.rs.getInt("quantidade");
                float valor_total = valor * qtd;
                ProdutoVendido pv = new ProdutoVendido(id_produto, id_venda, nome, descricao, valor, qtd, valor_total);
                arrayListProdutoVendidos.add(pv);
                dtm.addRow(pv.toObject());
            }
        } catch (SQLException ex) {
            Logger.getLogger(JFProdutosVendidos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setVisibility">
    public void setVisibility(boolean visible) {
        nome.setVisible(visible);
        nomeTxt.setVisible(visible);
        descricao.setVisible(visible);
        descricaoTxt.setVisible(visible);
        valorUnd.setVisible(visible);
        valorUndTxt.setVisible(visible);
        qtd.setVisible(visible);
        qtdTxt.setVisible(visible);
        valorTot.setVisible(visible);
        valorTotTxt.setVisible(visible);
        btnBuscar.setVisible(visible);
        setAutoCreateGaps(visible);
        setAutoCreateContainerGaps(visible);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getVisibility">
    public boolean getVisibility() {
        return nome.isVisible();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="MouseAdapter">
    MouseAdapter ma = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (e.getButton() == MouseEvent.BUTTON3) {
                JPopupMenu jpm = new JPopupMenu();
                JMenuItem jmiAtualiza = new JMenuItem("Atualiza");
                JMenuItem jmiBuscar = new JMenuItem("Buscar");
                jmiAtualiza.addActionListener((ee) -> {
                    atualiza();
                });
                jmiBuscar.addActionListener((ee) -> {
                    setVisibility(!getVisibility());
                });
                jpm.add(jmiAtualiza);
                jpm.add(jmiBuscar);
                jpm.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="KeyAdapter">
    private final KeyAdapter ka = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                search();
            }
        }
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ActionListener">
    private final ActionListener al = (e) -> {
        search();
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="search">
    private void search() {
        arrayListProdutoVendidosTmp.clear();
        arrayListProdutoVendidos.forEach(pv -> {
            boolean nomeMatch = false;
            boolean descricaoMatch = false;
            boolean valorUndMatch = false;
            boolean qtdMatch = false;
            boolean valorTotalMatch = false;
            if (!nomeTxt.getText().isBlank()) {
                try {
                    mySQL.rs = mySQL.st.executeQuery("select * from produtos_vendidos where id_venda=" + pv.getId_venda() + " and id_produto=" + pv.getId());
                    while (mySQL.rs.next()) {
                        nomeMatch = mySQL.rs.getString("nome").contains(nomeTxt.getText());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JIFVendasGroupLayout.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                nomeMatch = true;
            }
            if (!descricaoTxt.getText().isBlank()) {
                try {
                    mySQL.rs = mySQL.st.executeQuery("select * from produtos_vendidos where id=" + pv.getId());
                    if (mySQL.rs.next()) {
                        descricaoMatch = mySQL.rs.getString("descricao").contains(descricaoTxt.getText());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JIFVendasGroupLayout.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                descricaoMatch = true;
            }
            if (!valorUndTxt.getText().isBlank()) {
                valorUndMatch = pv.getValor_und() == Integer.parseInt(valorUndTxt.getText());
            } else {
                valorUndMatch = true;
            }
            if (!qtdTxt.getText().isBlank()) {
                qtdMatch = pv.getQtd() == Integer.parseInt(qtdTxt.getText());
            } else {
                qtdMatch = true;
            }
            if (!valorTotTxt.getText().isBlank()) {
                valorTotalMatch = pv.getValor_total() == Integer.parseInt(valorTotTxt.getText());
            } else {
                valorTotalMatch = true;
            }
            if (nomeMatch && descricaoMatch && qtdMatch && valorUndMatch && valorTotalMatch) {
                arrayListProdutoVendidosTmp.add(pv);
            }
        });
        int rowCount = dtm.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            dtm.removeRow(0);
        }
        if (nomeTxt.getText().isBlank() && descricaoTxt.getText().isBlank() && qtdTxt.getText().isBlank() && valorUnd.getText().isBlank() && valorTot.getText().isBlank()) {
            for (ProdutoVendido pv : arrayListProdutoVendidos) {
                dtm.addRow(pv.toObject());
            }
            return;
        }
        if (!arrayListProdutoVendidosTmp.isEmpty()) {
            arrayListProdutoVendidosTmp.forEach(vd -> {
                dtm.addRow(vd.toObject());
            });
        }
    }
    //</editor-fold>

}
