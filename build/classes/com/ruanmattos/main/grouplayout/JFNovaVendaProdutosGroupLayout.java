package com.ruanmattos.main.grouplayout;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.data.ProdutosData;
import com.ruanmattos.main.data.VendaProdutoData;
import com.ruanmattos.main.database.MySQL;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
//</editor-fold>

/**
 *
 * @author Ruan Pereira Mattos
 */
public class JFNovaVendaProdutosGroupLayout extends GroupLayout implements ActionListener {

    private MySQL mySQL = null;

    private  final List<ProdutosData> listProdutosData = new ArrayList<>();
    private  final List<VendaProdutoData> listCarrinho = new ArrayList<>();

    private  final JLabel nomeLbl = new JLabel("Produto:");
    private  final JLabel qtdLbl = new JLabel("Quantidade:");
    private  final JLabel valorUndLbl = new JLabel("Valor Unitário:");
    private  final JLabel valorUndTxt = new JLabel();
    private  final JLabel valorTotalLbl = new JLabel("Valor Total Produto:");
    private  final JLabel valorTotalTxt = new JLabel();
    private  final JLabel valorTotalCompra = new JLabel();
    private  final ArrayList<String> produtos = new ArrayList<>();
    private  final JComboBox<String> jComboBoxProdutos = new JComboBox<>();
    private  final JSpinner jSpinnerQTD = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
    private  final JButton btnAdicionarProduto = new JButton("Adicionar produto no carrinho");
    private  final String[] columnNames = {"Id", "Nome Produto", "Quantidade", "Valor Und.", "Valor Total"};
    private  final DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
    private  final JTable table = new JTable(dtm);
    private  final JScrollPane jsp = new JScrollPane(table);

    public JFNovaVendaProdutosGroupLayout(Container host) {
        super(host);
        try {
            mySQL = new MySQL();
            mySQL.st = mySQL.conn.createStatement();
            String sql = "select * from produtos inner join estoque on produtos.id = estoque.id_produto where quantidade > 0";
            mySQL.rs = mySQL.st.executeQuery(sql);
            while (mySQL.rs.next()) {
                int id = mySQL.rs.getInt("id");
                String nome = mySQL.rs.getString("nome");
                int quantidade = mySQL.rs.getInt("quantidade");
                Float valor = mySQL.rs.getFloat("valor");
                ProdutosData pd = new ProdutosData(id, nome, quantidade, valor);
                produtos.add(pd.getNome());
                listProdutosData.add(pd);
                jComboBoxProdutos.addItem(nome);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JFNovaVendaProdutosGroupLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
        jSpinnerQTD.addMouseWheelListener(mwlQuantidade);
        jSpinnerQTD.addChangeListener(clQuantidade);
        setAutoCreateGaps(true);
        setAutoCreateContainerGaps(true);
        setHorizontalGroup(setHorizontalGroup());
        setVerticalGroup(setVerticalGroup());
        jComboBoxProdutos.addMouseWheelListener(MouseWheelListenerProdutos);
        btnAdicionarProduto.addActionListener(this);
        table.addMouseListener(MouseAdapterTable);
        table.addKeyListener(KeyAdapterTable);
    }

    //<editor-fold defaultstate="collapsed" desc="setHorizontalGroup">
    private GroupLayout.Group setHorizontalGroup() {
        return createParallelGroup()
                .addGroup(
                        createSequentialGroup()
                                .addGroup(
                                        createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(nomeLbl)
                                                .addComponent(qtdLbl)
                                                .addComponent(valorUndLbl)
                                                .addComponent(valorTotalLbl)
                                )
                                .addGroup(
                                        createParallelGroup()
                                                .addComponent(jComboBoxProdutos)
                                                .addComponent(jSpinnerQTD)
                                                .addComponent(valorUndTxt)
                                                .addComponent(valorTotalTxt)
                                )
                )
                .addComponent(btnAdicionarProduto, GroupLayout.Alignment.CENTER, DEFAULT_SIZE, DEFAULT_SIZE, Integer.MAX_VALUE)
                .addComponent(jsp)
                .addComponent(valorTotalCompra);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setVerticalGroup">
    private GroupLayout.Group setVerticalGroup() {
        return createSequentialGroup()
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(nomeLbl)
                                .addComponent(jComboBoxProdutos)
                ).addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(qtdLbl)
                                .addComponent(jSpinnerQTD)
                ).addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(valorUndLbl)
                                .addComponent(valorUndTxt)
                ).addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(valorTotalLbl)
                                .addComponent(valorTotalTxt)
                ).addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(btnAdicionarProduto)
                )
                .addComponent(jsp)
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(valorTotalCompra)
                );
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="MouseWheelListenerQuantidade">
    private MouseWheelListener mwlQuantidade = (e) -> {
        if (e.getWheelRotation() < 0) {//-1 == UP
            if (Integer.parseInt(jSpinnerQTD.getValue().toString()) < listProdutosData.get(jComboBoxProdutos.getSelectedIndex()).getQuantidade()) {
                jSpinnerQTD.setValue(Integer.parseInt(jSpinnerQTD.getValue().toString()) + 1);
            }
        } else {//1 == DOWN
            if (Integer.parseInt(jSpinnerQTD.getValue().toString()) > 1) {
                jSpinnerQTD.setValue(Integer.parseInt(jSpinnerQTD.getValue().toString()) - 1);
            }
        }
        atualizaValor();
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ChangeListenerQuantidade">
    private ChangeListener clQuantidade = (e) -> {
        if (listProdutosData.get(jComboBoxProdutos.getSelectedIndex()).getQuantidade() < Integer.parseInt(jSpinnerQTD.getValue().toString())) {
            jSpinnerQTD.setValue(listProdutosData.get(jComboBoxProdutos.getSelectedIndex()).getQuantidade());
        }
        atualizaValor();
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="MouseWheelListenerProdutos">
    private final MouseWheelListener MouseWheelListenerProdutos = (MouseWheelEvent e) -> {
        if (e.getWheelRotation() < 0) {//-1 == UP
            if (jComboBoxProdutos.getSelectedIndex() > 0) {
                jComboBoxProdutos.setSelectedIndex(jComboBoxProdutos.getSelectedIndex() - 1);
            }
        } else {//1 == DOWN
            if (jComboBoxProdutos.getSelectedIndex() < jComboBoxProdutos.getItemCount() - 1) {
                jComboBoxProdutos.setSelectedIndex(jComboBoxProdutos.getSelectedIndex() + 1);
            }
        }
        atualizaValor();
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="MouseAdapterTable">
    private final MouseAdapter MouseAdapterTable = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (e.getButton() == MouseEvent.BUTTON3) {
                excluirProdutoCarrinho(e);
            }
        }

    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="KeyAdapterTable">
    private final KeyAdapter KeyAdapterTable = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (table.getSelectedRow() != -1) {
                if (e.getKeyChar() == KeyEvent.VK_DELETE) {
                    int selectedRow = table.getSelectedRow();
                    listCarrinho.remove(selectedRow);
                    listProdutosData.remove(selectedRow);
                    dtm.removeRow(selectedRow);
                    atualizaValorTotalExcluido();
                }
            }
        }
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="excluirProdutoCarrinho">
    private void excluirProdutoCarrinho(MouseEvent e) {
        int selectedRow = table.getSelectedRow();
        JPopupMenu jpm = new JPopupMenu();
        JMenuItem jmi = new JMenuItem("Deletar produto " + listCarrinho.get(selectedRow).nome);
        jmi.addActionListener((ee) -> {
            dtm.removeRow(selectedRow);
            listCarrinho.remove(selectedRow);
            listProdutosData.remove(selectedRow);
            atualizaValorTotalExcluido();
        });
        jpm.add(jmi);
        jpm.show(e.getComponent(), e.getX(), e.getY());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="atualizaValorTotal">
    private void atualizaValorTotal() {
        int id = listProdutosData.get(jComboBoxProdutos.getSelectedIndex()).getId();
        String nome = listProdutosData.get(jComboBoxProdutos.getSelectedIndex()).getNome();
        Float valor_und = listProdutosData.get(jComboBoxProdutos.getSelectedIndex()).getValor();
        int qtd = Integer.parseInt(jSpinnerQTD.getValue().toString());
        Float valor_total = valor_und * qtd;
        VendaProdutoData vpd = new VendaProdutoData(id, nome, qtd, valor_und, valor_total);
        listCarrinho.add(vpd);
        dtm.addRow(vpd.toObjectIdProduto());
        float totalCompra = 0;
        for (VendaProdutoData vpdd : listCarrinho) {
            totalCompra += vpdd.valor_total;
        }
        valorTotalCompra.setText("Valor Total da Compra R$ " + totalCompra);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="atualizaValorTotalExcluido">
    private void atualizaValorTotalExcluido() {
        float totalCompra = 0;
        for (VendaProdutoData vpdd : listCarrinho) {
            totalCompra += vpdd.valor_total;
        }
        valorTotalCompra.setText("Valor Total da Compra R$ " + totalCompra);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="atualizaValor">
    private void atualizaValor() {
        valorUndTxt.setText("R$ " + listProdutosData.get(jComboBoxProdutos.getSelectedIndex()).getValor());
        valorTotalTxt.setText("R$ " + (Integer.parseInt(jSpinnerQTD.getValue().toString()) * listProdutosData.get(jComboBoxProdutos.getSelectedIndex()).getValor()));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="actionPermformed">
    @Override
    public void actionPerformed(ActionEvent e) {
        atualizaValorTotal();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getListCarrinho">
    public List<VendaProdutoData> getListCarrinho() {
        return listCarrinho;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getListCarrinho">
    public List<ProdutosData> getListProdutosData() {
        return listProdutosData;
    }
    //</editor-fold>

}
