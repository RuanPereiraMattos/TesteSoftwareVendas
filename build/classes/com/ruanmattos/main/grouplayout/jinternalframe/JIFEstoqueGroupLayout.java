package com.ruanmattos.main.grouplayout.jinternalframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.data.EstoqueData;
import com.ruanmattos.main.data.ProdutosData;
import com.ruanmattos.main.database.MySQL;
import com.ruanmattos.main.jinternalframe.JIFEstoque;
import com.ruanmattos.main.jinternalframe.JIFProdutos;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
//</editor-fold>

/**
 *
 * @author Ruan Pereira Mattos
 */
public class JIFEstoqueGroupLayout extends GroupLayout implements ActionListener {

    private JIFEstoque jife = null;
    private static Statement stDois = null;
    private static ResultSet rsDois = null;
    private MySQL mySQL = null;

    private static final ArrayList<EstoqueData> arrayListEstoqueData = new ArrayList<>();
    private static final ArrayList<EstoqueData> arrayListEstoqueDataTmp = new ArrayList<>();
    private static final ArrayList<ProdutosData> arrayListProdutoData = new ArrayList<>();
    private static final ArrayList<ProdutosData> arrayListProdutoDataTmp = new ArrayList<>();
    private static final String[] columnNames = new String[]{"Id", "Id Produto", "Nome", "Descricao", "Quantidade"};
    private static final DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
    private static final JTable table = new JTable(dtm);
    private static final JScrollPane jsp = new JScrollPane(table);

    private final JLabel nome, descricao, quantidade;
    private final JTextField nomeTxt, descricaoTxt, quantidadeTxt;
    private final JButton btnBuscar;

    public JIFEstoqueGroupLayout(Container host, JIFEstoque jife) {
        super(host);
        this.jife = jife;
        nome = new JLabel("Nome");
        descricao = new JLabel("Descrição");
        quantidade = new JLabel("Quantidade");
        btnBuscar = new JButton("Buscar");
        nomeTxt = new JTextField();
        descricaoTxt = new JTextField();
        quantidadeTxt = new JTextField();
        nome.setVisible(false);
        nomeTxt.setVisible(false);
        descricao.setVisible(false);
        descricaoTxt.setVisible(false);
        quantidade.setVisible(false);
        quantidadeTxt.setVisible(false);
        btnBuscar.setVisible(false);
        nomeTxt.addKeyListener(ka);
        descricaoTxt.addKeyListener(ka);
        quantidadeTxt.addKeyListener(ka);
        btnBuscar.addActionListener(this);
        setHorizontalGroup(setHorizontalGroup());
        setVerticalGroup(setVerticalGroup());
        atualiza();
        table.addMouseListener(ma);
        jsp.addMouseListener(ma);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getModel().addTableModelListener(tml);
    }

    //<editor-fold defaultstate="collapsed" desc="setHorizontalGroup">
    private Group setHorizontalGroup() {
        return createParallelGroup()
                .addGroup(
                        createSequentialGroup()
                                .addGroup(
                                        createParallelGroup(Alignment.TRAILING, false)
                                                .addComponent(nome)
                                                .addComponent(quantidade)
                                                .addComponent(descricao)
                                )
                                .addGroup(
                                        createParallelGroup()
                                                .addComponent(nomeTxt)
                                                .addComponent(quantidadeTxt)
                                                .addComponent(descricaoTxt)
                                )
                )
                .addComponent(btnBuscar, DEFAULT_SIZE, DEFAULT_SIZE, Integer.MAX_VALUE)
                .addComponent(jsp);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setHorizontalGroup">
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
                                .addComponent(quantidade)
                                .addComponent(quantidadeTxt)
                )
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(btnBuscar)
                )
                .addComponent(jsp);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="atualiza">
    public void atualiza() {
        try {
            if (mySQL == null) {
                mySQL = new MySQL();
            }
            mySQL.st = mySQL.conn.createStatement();
            mySQL.rs = mySQL.st.executeQuery("select * from estoque");
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListEstoqueData.clear();
            arrayListEstoqueDataTmp.clear();
            arrayListProdutoData.clear();
            arrayListProdutoDataTmp.clear();
            while (mySQL.rs.next()) {
                int id = mySQL.rs.getInt("id");
                int id_produto = mySQL.rs.getInt("id_produto");
                int qtd = mySQL.rs.getInt("quantidade");
                EstoqueData ed = new EstoqueData(id, id_produto, qtd);
                arrayListEstoqueData.add(ed);
                stDois = mySQL.conn.createStatement();
                rsDois = stDois.executeQuery("select * from produtos where id=" + id_produto);
                String nome = "";
                String descricao = "";
                float valor = 0;
                if (rsDois.next()) {
                    nome = rsDois.getString("nome");
                    descricao = rsDois.getString("descricao");
                    valor = rsDois.getFloat("valor");
                }
                Object[] o = new Object[]{ed.getId(), ed.getId_produto(), nome, descricao, ed.getQtd()};
                ProdutosData pd = new ProdutosData(id_produto, nome, descricao, valor);
                arrayListProdutoData.add(pd);
                dtm.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFEstoque.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setVisibility">
    public void setVisibility(boolean visible) {
        nome.setVisible(visible);
        nomeTxt.setVisible(visible);
        descricao.setVisible(visible);
        descricaoTxt.setVisible(visible);
        quantidade.setVisible(visible);
        quantidadeTxt.setVisible(visible);
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
                jpm.add(jmiAtualiza);
                jpm.add(jmiBuscar);
                jmiAtualiza.addActionListener((ee) -> {
                    atualiza();
                    jpm.setVisible(false);
                });
                jmiBuscar.addActionListener((ee) -> {
                    setVisibility(!getVisibility());
                });
                jpm.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="TableModelListener">
    private final TableModelListener tml = (e) -> {
        if (e.getType() == TableModelEvent.UPDATE) {
            try {
                if (e.getColumn() == 4) {
                    mySQL.ps = mySQL.conn.prepareStatement("update estoque set quantidade=" + Integer.valueOf(table.getValueAt(e.getFirstRow(), e.getColumn()).toString()) + " where id=" + arrayListEstoqueData.get(e.getFirstRow()).getId());
                    int executeUpdate = mySQL.ps.executeUpdate();
                    if (executeUpdate == 1) {
                        JOptionPane.showMessageDialog(jife, "Estoque do produto atualizado para " + Integer.valueOf(table.getValueAt(e.getFirstRow(), e.getColumn()).toString()) + " com sucesso!");
                        jife.getMyJMB().setJMINovaVenda();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(JIFProdutos.class.getName()).log(Level.SEVERE, null, ex);
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

    //<editor-fold defaultstate="collapsed" desc="actionPerformed">
    @Override
    public void actionPerformed(ActionEvent e) {
        search();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="search">
    private void search() {
        arrayListProdutoDataTmp.clear();
        arrayListEstoqueDataTmp.clear();
        if (!nomeTxt.getText().isBlank() && !descricaoTxt.getText().isBlank() && !quantidadeTxt.getText().isBlank()) {//Nome Descricao Quantidade
            for (int i = 0; i < arrayListProdutoData.size(); i++) {
                EstoqueData ed = arrayListEstoqueData.get(i);
                ProdutosData pd = arrayListProdutoData.get(i);
                CharSequence charSequenceNome = nomeTxt.getText();
                CharSequence charSequenceDescricao = descricaoTxt.getText();
                if (pd.getNome().contains(charSequenceNome) && pd.getDescricao().contains(charSequenceDescricao) && ed.getQtd() == Float.parseFloat(quantidadeTxt.getText())) {
                    arrayListProdutoDataTmp.add(pd);
                    arrayListEstoqueDataTmp.add(ed);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            for (int i = 0; i < arrayListProdutoDataTmp.size(); i++) {
                ProdutosData pd = arrayListProdutoDataTmp.get(i);
                EstoqueData ed = arrayListEstoqueDataTmp.get(i);
                Object[] o = new Object[]{ed.getId(), pd.getId(), pd.getNome(), pd.getDescricao(), ed.getQtd()};
                dtm.addRow(o);
            }
        } else if (!nomeTxt.getText().isBlank() && !descricaoTxt.getText().isBlank()) {//Nome Descricao
            for (int i = 0; i < arrayListProdutoData.size(); i++) {
                EstoqueData ed = arrayListEstoqueData.get(i);
                ProdutosData pd = arrayListProdutoData.get(i);
                CharSequence charSequenceNome = nomeTxt.getText();
                CharSequence charSequenceDescricao = descricaoTxt.getText();
                if (pd.getNome().contains(charSequenceNome) && pd.getDescricao().contains(charSequenceDescricao)) {
                    arrayListProdutoDataTmp.add(pd);
                    arrayListEstoqueDataTmp.add(ed);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            for (int i = 0; i < arrayListProdutoDataTmp.size(); i++) {
                ProdutosData pd = arrayListProdutoDataTmp.get(i);
                EstoqueData ed = arrayListEstoqueDataTmp.get(i);
                Object[] o = new Object[]{ed.getId(), pd.getId(), pd.getNome(), pd.getDescricao(), ed.getQtd()};
                dtm.addRow(o);
            }
        } else if (!nomeTxt.getText().isBlank() && !quantidadeTxt.getText().isBlank()) {//Nome Quantidade
            for (int i = 0; i < arrayListProdutoData.size(); i++) {
                ProdutosData pd = arrayListProdutoData.get(i);
                EstoqueData ed = arrayListEstoqueData.get(i);
                CharSequence charSequenceNome = nomeTxt.getText();
                if (pd.getNome().contains(charSequenceNome) && ed.getQtd() == Float.parseFloat(quantidadeTxt.getText())) {
                    arrayListProdutoDataTmp.add(pd);
                    arrayListEstoqueDataTmp.add(ed);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            for (int i = 0; i < arrayListProdutoDataTmp.size(); i++) {
                ProdutosData pd = arrayListProdutoDataTmp.get(i);
                EstoqueData ed = arrayListEstoqueDataTmp.get(i);
                Object[] o = new Object[]{ed.getId(), pd.getId(), pd.getNome(), pd.getDescricao(), ed.getQtd()};
                dtm.addRow(o);
            }
        } else if (!descricaoTxt.getText().isBlank() && !quantidadeTxt.getText().isBlank()) {//Descricao Quantidade
            for (int i = 0; i < arrayListProdutoData.size(); i++) {
                ProdutosData pd = arrayListProdutoData.get(i);
                EstoqueData ed = arrayListEstoqueData.get(i);
                CharSequence charSequenceDescricao = descricaoTxt.getText();
                if (pd.getDescricao().contains(charSequenceDescricao) && ed.getQtd() == Float.parseFloat(quantidadeTxt.getText())) {
                    arrayListProdutoDataTmp.add(pd);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            for (int i = 0; i < arrayListProdutoDataTmp.size(); i++) {
                ProdutosData pd = arrayListProdutoDataTmp.get(i);
                EstoqueData ed = arrayListEstoqueDataTmp.get(i);
                Object[] o = new Object[]{ed.getId(), pd.getId(), pd.getNome(), pd.getDescricao(), ed.getQtd()};
                dtm.addRow(o);
            }
        } else if (!nomeTxt.getText().isBlank()) {//Nome
            for (int i = 0; i < arrayListProdutoData.size(); i++) {
                EstoqueData ed = arrayListEstoqueData.get(i);
                ProdutosData pd = arrayListProdutoData.get(i);
                CharSequence charSequenceNome = nomeTxt.getText();
                if (pd.getNome().contains(charSequenceNome)) {
                    arrayListProdutoDataTmp.add(pd);
                    arrayListEstoqueDataTmp.add(ed);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            for (int i = 0; i < arrayListProdutoDataTmp.size(); i++) {
                ProdutosData pd = arrayListProdutoDataTmp.get(i);
                EstoqueData ed = arrayListEstoqueDataTmp.get(i);
                Object[] o = new Object[]{ed.getId(), pd.getId(), pd.getNome(), pd.getDescricao(), ed.getQtd()};
                dtm.addRow(o);
            }
        } else if (!descricaoTxt.getText().isBlank()) {//Descricao
            for (int i = 0; i < arrayListProdutoData.size(); i++) {
                EstoqueData ed = arrayListEstoqueData.get(i);
                ProdutosData pd = arrayListProdutoData.get(i);
                CharSequence charSequenceDescricao = descricaoTxt.getText();
                if (pd.getDescricao().contains(charSequenceDescricao)) {
                    arrayListProdutoDataTmp.add(pd);
                    arrayListEstoqueDataTmp.add(ed);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            for (int i = 0; i < arrayListProdutoDataTmp.size(); i++) {
                ProdutosData pd = arrayListProdutoDataTmp.get(i);
                EstoqueData ed = arrayListEstoqueDataTmp.get(i);
                Object[] o = new Object[]{ed.getId(), pd.getId(), pd.getNome(), pd.getDescricao(), ed.getQtd()};
                dtm.addRow(o);
            }
        } else if (!quantidadeTxt.getText().isBlank()) {//Quantidade
            for (int i = 0; i < arrayListProdutoData.size(); i++) {
                ProdutosData pd = arrayListProdutoData.get(i);
                EstoqueData ed = arrayListEstoqueData.get(i);
                if (ed.getQtd() == Float.parseFloat(quantidadeTxt.getText())) {
                    arrayListProdutoDataTmp.add(pd);
                    arrayListEstoqueDataTmp.add(ed);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            for (int i = 0; i < arrayListProdutoDataTmp.size(); i++) {
                ProdutosData pd = arrayListProdutoDataTmp.get(i);
                EstoqueData ed = arrayListEstoqueDataTmp.get(i);
                Object[] o = new Object[]{ed.getId(), pd.getId(), pd.getNome(), pd.getDescricao(), ed.getQtd()};
                dtm.addRow(o);
            }
        } else {
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            for (int i = 0; i < arrayListProdutoData.size(); i++) {
                ProdutosData pd = arrayListProdutoData.get(i);
                EstoqueData ed = arrayListEstoqueData.get(i);
                Object[] o = new Object[]{ed.getId(), pd.getId(), pd.getNome(), pd.getDescricao(), ed.getQtd()};
                dtm.addRow(o);
            }
        }
    }
    //</editor-fold>

}
