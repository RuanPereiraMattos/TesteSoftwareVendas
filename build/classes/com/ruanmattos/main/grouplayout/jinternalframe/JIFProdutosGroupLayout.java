package com.ruanmattos.main.grouplayout.jinternalframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.data.ProdutosData;
import com.ruanmattos.main.database.MySQL;
import com.ruanmattos.main.jframe.JFNovoProduto;
import com.ruanmattos.main.jinternalframe.JIFClientes;
import com.ruanmattos.main.jinternalframe.JIFProdutos;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
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
public class JIFProdutosGroupLayout extends GroupLayout {

    private MySQL mySQL = null;

    private static final ArrayList<ProdutosData> arrayListProdutosData = new ArrayList<>();
    private static final ArrayList<ProdutosData> arrayListProdutosDataTmp = new ArrayList<>();
    private static final String[] columnNames = new String[]{"Nome", "Descrição", "Valor"};
    private static final DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
    private static final JTable table = new JTable(dtm);
    private static final JScrollPane jsp = new JScrollPane(table);

    private final JIFProdutos jifp;

    private final JLabel nome, descricao, valor;
    private final JTextField nomeTxt, descricaoTxt, valorTxt;
    private final JButton btnBuscar = new JButton("Buscar");

    public JIFProdutosGroupLayout(Container host, JIFProdutos jifp) {
        super(host);
        this.jifp = jifp;
        nome = new JLabel("Nome");
        descricao = new JLabel("Descrição");
        valor = new JLabel("Valor");
        nomeTxt = new JTextField();
        descricaoTxt = new JTextField();
        valorTxt = new JTextField();
        nome.setVisible(false);
        nomeTxt.setVisible(false);
        descricao.setVisible(false);
        descricaoTxt.setVisible(false);
        valor.setVisible(false);
        valorTxt.setVisible(false);
        btnBuscar.setVisible(false);
        nomeTxt.addKeyListener(ka);
        descricaoTxt.addKeyListener(ka);
        valorTxt.addKeyListener(ka);
        btnBuscar.addActionListener(al);
        setHorizontalGroup(setHorizontalGroup());
        setVerticalGroup(setVerticalGroup());
        atualiza();
        table.addMouseListener(ma);
        jsp.addMouseListener(ma);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getModel().addTableModelListener(tml);
    }

    //<editor-fold defaultstate="collapsed" desc="atualiza">
    public void atualiza() {
        try {
            if (mySQL == null) {
                mySQL = new MySQL();
            }
            mySQL.st = mySQL.conn.createStatement();
            mySQL.rs = mySQL.st.executeQuery("select * from produtos order by nome");
            for (ProdutosData pd : arrayListProdutosData) {
                dtm.removeRow(0);
            }
            arrayListProdutosData.clear();
            while (mySQL.rs.next()) {
                int id = mySQL.rs.getInt("id");
                String nome = mySQL.rs.getString("nome");
                String descricao = mySQL.rs.getString("descricao");
                float valor = mySQL.rs.getFloat("valor");
                ProdutosData produtosData = new ProdutosData(id, nome, descricao, valor);
                arrayListProdutosData.add(produtosData);
                dtm.addRow(produtosData.toObject());
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFProdutos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setHorizontalGroup">
    private GroupLayout.Group setHorizontalGroup() {
        return createParallelGroup()
                .addGroup(
                        createSequentialGroup()
                                .addGroup(
                                        createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(nome)
                                                .addComponent(descricao)
                                                .addComponent(valor)
                                )
                                .addGroup(
                                        createParallelGroup()
                                                .addComponent(nomeTxt)
                                                .addComponent(descricaoTxt)
                                                .addComponent(valorTxt)
                                )
                )
                .addComponent(btnBuscar, DEFAULT_SIZE, DEFAULT_SIZE, Integer.MAX_VALUE)
                .addComponent(jsp);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setVerticalGroup">
    private GroupLayout.Group setVerticalGroup() {
        return createSequentialGroup()
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(nome)
                                .addComponent(nomeTxt)
                )
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(descricao)
                                .addComponent(descricaoTxt)
                )
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(valor)
                                .addComponent(valorTxt)
                )
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(btnBuscar)
                )
                .addComponent(jsp);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setVisibility">
    public void setVisibility(boolean visible) {
        nome.setVisible(visible);
        nomeTxt.setVisible(visible);
        descricao.setVisible(visible);
        descricaoTxt.setVisible(visible);
        valor.setVisible(visible);
        valorTxt.setVisible(visible);
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
        arrayListProdutosDataTmp.clear();
        if (!nomeTxt.getText().isBlank() && !descricaoTxt.getText().isBlank() && !valorTxt.getText().isBlank()) {//Nome Descricao Valor
            for (int i = 0; i < arrayListProdutosData.size(); i++) {
                ProdutosData pd = arrayListProdutosData.get(i);
                CharSequence charSequenceNome = nomeTxt.getText();
                CharSequence charSequenceDescricao = descricaoTxt.getText();
                if (pd.getNome().contains(charSequenceNome) && pd.getDescricao().contains(charSequenceDescricao) && pd.getValor() == Float.parseFloat(valorTxt.getText())) {
                    arrayListProdutosDataTmp.add(pd);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListProdutosDataTmp.forEach(pd -> {
                dtm.addRow(pd.toObject());
            });
        } else if (!nomeTxt.getText().isBlank() && !descricaoTxt.getText().isBlank()) {//Nome Descricao
            for (int i = 0; i < arrayListProdutosData.size(); i++) {
                ProdutosData pd = arrayListProdutosData.get(i);
                CharSequence charSequenceNome = nomeTxt.getText();
                CharSequence charSequenceDescricao = descricaoTxt.getText();
                if (pd.getNome().contains(charSequenceNome) && pd.getDescricao().contains(charSequenceDescricao)) {
                    arrayListProdutosDataTmp.add(pd);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListProdutosDataTmp.forEach(pd -> {
                dtm.addRow(pd.toObject());
            });
        } else if (!nomeTxt.getText().isBlank() && !valorTxt.getText().isBlank()) {//Nome Valor
            for (int i = 0; i < arrayListProdutosData.size(); i++) {
                ProdutosData pd = arrayListProdutosData.get(i);
                CharSequence charSequenceNome = nomeTxt.getText();
                if (pd.getNome().contains(charSequenceNome) && pd.getValor() == Float.parseFloat(valorTxt.getText())) {
                    arrayListProdutosDataTmp.add(pd);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListProdutosDataTmp.forEach(pd -> {
                dtm.addRow(pd.toObject());
            });
        } else if (!descricaoTxt.getText().isBlank() && !valorTxt.getText().isBlank()) {//Descricao Valor
            for (int i = 0; i < arrayListProdutosData.size(); i++) {
                ProdutosData pd = arrayListProdutosData.get(i);
                CharSequence charSequenceDescricao = descricaoTxt.getText();
                if (pd.getDescricao().contains(charSequenceDescricao) && pd.getValor() == Float.parseFloat(valorTxt.getText())) {
                    arrayListProdutosDataTmp.add(pd);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListProdutosDataTmp.forEach(pd -> {
                dtm.addRow(pd.toObject());
            });
        } else if (!nomeTxt.getText().isBlank()) {//Nome
            for (int i = 0; i < arrayListProdutosData.size(); i++) {
                ProdutosData pd = arrayListProdutosData.get(i);
                CharSequence charSequenceNome = nomeTxt.getText();
                if (pd.getNome().contains(charSequenceNome)) {
                    arrayListProdutosDataTmp.add(pd);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListProdutosDataTmp.forEach(pd -> {
                dtm.addRow(pd.toObject());
            });
        } else if (!descricaoTxt.getText().isBlank()) {//Descricao
            for (int i = 0; i < arrayListProdutosData.size(); i++) {
                ProdutosData pd = arrayListProdutosData.get(i);
                CharSequence charSequenceDescricao = descricaoTxt.getText();
                if (pd.getDescricao().contains(charSequenceDescricao)) {
                    arrayListProdutosDataTmp.add(pd);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListProdutosDataTmp.forEach(pd -> {
                dtm.addRow(pd.toObject());
            });
        } else if (!valorTxt.getText().isBlank()) {//Valor
            for (int i = 0; i < arrayListProdutosData.size(); i++) {
                ProdutosData pd = arrayListProdutosData.get(i);
                if (pd.getValor() == Float.parseFloat(valorTxt.getText())) {
                    arrayListProdutosDataTmp.add(pd);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListProdutosDataTmp.forEach(pd -> {
                dtm.addRow(pd.toObject());
            });
        } else {
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListProdutosData.forEach(pd -> {
                dtm.addRow(pd.toObject());
            });
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="MouseAdapter">
    MouseAdapter ma = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            int selectedRow = table.getSelectedRow();
            if (e.getButton() == MouseEvent.BUTTON3) {
                JPopupMenu jpm = new JPopupMenu();
                JMenuItem jmiAtualiza = new JMenuItem("Atualiza");
                JMenuItem jmiNovoCliente = new JMenuItem("Novo Produto");
                JMenuItem jmiBuscar = new JMenuItem("Buscar");
                jmiAtualiza.addActionListener((ee) -> {
                    atualiza();
                });
                jmiNovoCliente.addActionListener((ee) -> {
                    //JFNovoProduto jfNovoProduto = 
                            new JFNovoProduto(null, JIFProdutosGroupLayout.this);
                    jifp.getMyJMB().getJfm().setVisible(false);
                });
                jmiBuscar.addActionListener((ee) -> {
                    setVisibility(!getVisibility());
                });
                jpm.add(jmiAtualiza);
                jpm.add(jmiNovoCliente);
                jpm.add(jmiBuscar);
                if (selectedRow != -1) {
                    String nome = arrayListProdutosData.get(selectedRow).getNome();
                    JMenuItem jmiExcluirProduto = new JMenuItem("Excluir produto " + nome);
                    jmiExcluirProduto.addActionListener((ee) -> {
                        try {
                            //ADICIONAR VERIFICAÇÂO DO PRODUTO SE NÂO EXISTE DENTRO DE VENDAS
                            mySQL.rs = mySQL.st.executeQuery("select * from estoque where id_produto=" + arrayListProdutosData.get(selectedRow).getId());
                            if (mySQL.rs.next()) {//Verifica se existe a mercadoria no estqoue
                                if (mySQL.rs.getInt("quantidade") <= 0) {//Verifica se a quantidade deste produto no estoque esta zerado
                                    mySQL.ps = mySQL.conn.prepareStatement("delete from estoque where id_produto=?");
                                    mySQL.ps.setInt(1, arrayListProdutosData.get(selectedRow).getId());
                                    int executeUpdate = mySQL.ps.executeUpdate();
                                    if (executeUpdate == 1) {
                                        mySQL.ps = mySQL.conn.prepareStatement("delete from produtos where id=?");
                                        mySQL.ps.setInt(1, arrayListProdutosData.get(selectedRow).getId());
                                        executeUpdate = mySQL.ps.executeUpdate();
                                        if (executeUpdate == 1) {
                                            atualiza();
                                        }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(jmiNovoCliente, "Você não pode deletar produtos que não tenham estoque zerado", "Estoque", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(JIFClientes.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });
                    jpm.add(jmiExcluirProduto);
                }
                jpm.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="TableModelListener">
    private final TableModelListener tml = (e) -> {
        if (e.getType() == TableModelEvent.UPDATE) {
            try {
                switch (e.getColumn()) {
                    case 0 -> {//Nome
                        mySQL.ps = mySQL.conn.prepareStatement("update produtos set nome='" + table.getValueAt(e.getFirstRow(), e.getColumn()).toString() + "' where id='" + arrayListProdutosData.get(e.getFirstRow()).getId() + "'");
                        mySQL.ps.execute();
                    }
                    case 1 -> {//Descricao
                        mySQL.ps = mySQL.conn.prepareStatement("update produtos set descricao='" + table.getValueAt(e.getFirstRow(), e.getColumn()).toString() + "' where id='" + arrayListProdutosData.get(e.getFirstRow()).getId() + "'");
                        mySQL.ps.execute();
                    }
                    case 2 -> {//Valor
                        mySQL.ps = mySQL.conn.prepareStatement("update produtos set valor=" + Float.valueOf(table.getValueAt(e.getFirstRow(), e.getColumn()).toString()) + " where id='" + arrayListProdutosData.get(e.getFirstRow()).getId() + "'");
                        mySQL.ps.execute();
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(JIFProdutos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    //</editor-fold>
    
    public JIFProdutos getJifp(){
        return jifp;
    }

}
