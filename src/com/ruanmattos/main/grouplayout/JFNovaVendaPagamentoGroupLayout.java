package com.ruanmattos.main.grouplayout;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.data.PagamentoData;
import com.ruanmattos.main.data.PagamentosData;
import com.ruanmattos.main.data.ProdutosData;
import com.ruanmattos.main.data.VendaProdutoData;
import com.ruanmattos.main.database.MySQL;
import com.ruanmattos.main.jframe.JFFinalizacao;
import com.ruanmattos.main.jframe.JFNovaVenda;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
//</editor-fold>

/**
 *
 * @author Ruan Pereira Mattos
 */
public class JFNovaVendaPagamentoGroupLayout extends GroupLayout implements ActionListener {

    private MySQL mySQL = null;

    private List<VendaProdutoData> listCarrinho;
    private List<ProdutosData> listProdutosDatas;

    private final String COMPRA = "Valor total da compra: R$ ";
    private final String[] FORMAS_DE_PAGAMENTO = {"Dinheiro", "Cartão Débito", "Cartão Crédito"};
    private final String[] QUANTIDADE_VEZES_PAGAMENTO = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    private final JLabel valorPagamento = new JLabel(COMPRA);
    private final JComboBox<String> jComboBoxFormaPagamento = new JComboBox<>(FORMAS_DE_PAGAMENTO);
    private final JComboBox<String> jComboBoxQuantidadeVezesPagamento = new JComboBox<>(QUANTIDADE_VEZES_PAGAMENTO);
    private final JTextField valorPagamentoTxt = new JTextField();
    private final JButton btnInserirValor = new JButton("Inserir Valor");

    private final List<PagamentosData> listPagamentosData = new ArrayList<>();

    private final String[] columnNames = {"Forma de Pagamento", "Valor Pago"};
    private final DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
    private final JTable table = new JTable(dtm);
    private final JScrollPane jsp = new JScrollPane(table);

    private final JLabel troco = new JLabel();
    private final JLabel falta = new JLabel();

    private final JButton btnFinalizar = new JButton("Finalizar");

    private final PagamentoData pagamentoData = new PagamentoData();
    private final JFrame jf;
    private final JFNovaVenda jfnv;
    private final JFNovaVendaClientesGroupLayout jFrameNovaVendaClientesGroupLayout;

    public JFNovaVendaPagamentoGroupLayout(Container host, JFNovaVenda jfnv, JFrame jf, JFNovaVendaClientesGroupLayout jFrameNovaVendaClientesGroupLayout) {
        super(host);
        this.jfnv = jfnv;
        this.jf = jf;
        this.jFrameNovaVendaClientesGroupLayout = jFrameNovaVendaClientesGroupLayout;
        jComboBoxQuantidadeVezesPagamento.setVisible(false);
        valorPagamento.setText(COMPRA + pagamentoData.valorRestante);
        setAutoCreateGaps(true);
        setAutoCreateContainerGaps(true);
        setHorizontalGroup(setHorizontalGroup());
        setVerticalGroup(setVerticalGroup());
        jComboBoxFormaPagamento.addItemListener(ItemListenerFormaPagamento);
        btnInserirValor.addActionListener(this);
        btnFinalizar.addActionListener((e) -> {
            if (pagamentoData.valorRestante == 0) {
                encerrar();
            }
        });
        table.addMouseListener(MouseAdapterTable);
    }

    //<editor-fold defaultstate="collapsed" desc="MouseAdapterTable">
    private final MouseAdapter MouseAdapterTable = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (e.getButton() == MouseEvent.BUTTON3 && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                String str = "Excluir método de pagamento: ";
                str += (listPagamentosData.get(selectedRow).getTipo() == 0) ? "Dinheiro" : (listPagamentosData.get(selectedRow).getTipo() == 1) ? "Cartão de Débito" : "Cartão de Crédito";
                JPopupMenu jpm = new JPopupMenu();
                JMenuItem jmi = new JMenuItem(str);
                jpm.add(jmi);
                jpm.show(table, e.getX(), e.getY());
            }
        }

    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ItemListenerFormaPagamento">
    private final ItemListener ItemListenerFormaPagamento = (e) -> {
        boolean visible = jComboBoxFormaPagamento.getSelectedIndex() == 2;
        jComboBoxQuantidadeVezesPagamento.setVisible(visible);
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="actionPerformed">
    @Override
    public void actionPerformed(ActionEvent e) {
        if (valorPagamentoTxt.getText().isBlank()) {
            if (listPagamentosData.isEmpty()) {
                switch (jComboBoxFormaPagamento.getSelectedIndex()) {
                    case 0 -> {//Dinheiro
                        JOptionPane.showMessageDialog(null, "Pagamento realizado com Dinheiro", "Pagamento realizado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
                        PagamentosData pd = new PagamentosData(0, jComboBoxQuantidadeVezesPagamento.getSelectedIndex() + 1, pagamentoData.valorCompra, 0);
                        listPagamentosData.add(pd);
                        encerrar();
                    }
                    case 1 -> {//Débito
                        JOptionPane.showMessageDialog(null, "Pagamento realizado com Cartão de Débito", "Pagamento realizado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
                        PagamentosData pd = new PagamentosData(1, 1, pagamentoData.valorCompra, 0);
                        listPagamentosData.add(pd);
                        encerrar();
                    }
                    case 2 -> {//Crédito
                        String message = "Pagamento realizado com cartão de crédito em " + (jComboBoxQuantidadeVezesPagamento.getSelectedIndex() + 1);
                        message += (0 == jComboBoxQuantidadeVezesPagamento.getSelectedIndex()) ? " vez" : " vezes";

                        String[] options = {"Finalizar Venda", "Voltar"};
                        jf.setVisible(false);
                        int showOptionDialog = JOptionPane.showOptionDialog(
                                jf, message, "Pagamento realizado com sucesso!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                        if (showOptionDialog == 0) {
                            PagamentosData pd = new PagamentosData(2, jComboBoxQuantidadeVezesPagamento.getSelectedIndex() + 1, pagamentoData.valorCompra, 0);
                            listPagamentosData.add(pd);
                            encerrar();
                        } else {
                            jf.setVisible(true);
                        }

                    }
                }
            } else {
                switch (jComboBoxFormaPagamento.getSelectedIndex()) {
                    case 0 -> {//Dinheiro
                        PagamentosData pagamentosData = new PagamentosData(0, 1, pagamentoData.valorRestante, 0);
                        listPagamentosData.add(pagamentosData);
                        pagamentoData.valorRestante = 0;
                        String message = "";
                        for (int i = 0; i < listPagamentosData.size(); i++) {
                            switch (listPagamentosData.get(i).getTipo()) {
                                case 0 -> {
                                    message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Dinheiro\n";
                                }
                                case 1 -> {
                                    message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Débito\n";
                                }
                                case 2 -> {
                                    message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Crédito em ";
                                    message += (listPagamentosData.get(i).getVezes() == 0) ? "1 vez\n" : (listPagamentosData.get(i).getVezes()) + " vezes\n";
                                }
                            }
                        }
                        JOptionPane.showMessageDialog(null, message, "Pagamento realizado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
                        encerrar();
                    }
                    case 1 -> {//Débito
                        PagamentosData pagamentosData = new PagamentosData(1, 1, pagamentoData.valorRestante, 0);
                        listPagamentosData.add(pagamentosData);
                        pagamentoData.valorRestante = 0;
                        String message = "";
                        for (int i = 0; i < listPagamentosData.size(); i++) {
                            switch (listPagamentosData.get(i).getTipo()) {
                                case 0 -> {
                                    message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Dinheiro\n";
                                }
                                case 1 -> {
                                    message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Débito\n";
                                }
                                case 2 -> {
                                    message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Crédito em ";
                                    message += (listPagamentosData.get(i).getVezes() == 0) ? "1 vez\n" : (listPagamentosData.get(i).getVezes()) + " vezes\n";
                                }
                            }
                        }
                        JOptionPane.showMessageDialog(null, message, "Pagamento realizado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
                        encerrar();
                    }
                    case 2 -> {//Crédito
                        PagamentosData pagamentosData = new PagamentosData(2, jComboBoxQuantidadeVezesPagamento.getSelectedIndex() + 1, pagamentoData.valorRestante, 0);
                        listPagamentosData.add(pagamentosData);
                        pagamentoData.valorRestante = 0;
                        String message = "";
                        for (int i = 0; i < listPagamentosData.size(); i++) {
                            switch (listPagamentosData.get(i).getTipo()) {
                                case 0 -> {
                                    message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Dinheiro\n";
                                }
                                case 1 -> {
                                    message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Débito\n";
                                }
                                case 2 -> {
                                    message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Crédito em ";
                                    message += (listPagamentosData.get(i).getVezes() == 0) ? "1 vez\n" : (listPagamentosData.get(i).getVezes()) + " vezes\n";
                                }
                            }
                        }
                        JOptionPane.showMessageDialog(null, message, "Pagamento realizado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
                        encerrar();
                    }
                }
            }
        } else {
            switch (jComboBoxFormaPagamento.getSelectedIndex()) {
                case 0 -> {//Dinheiro
                    pagamentoData.valorRestante -= Integer.parseInt(valorPagamentoTxt.getText());
                    if (pagamentoData.valorRestante <= 0) {
                        pagamentoData.valorTroco = (pagamentoData.valorRestante == 0) ? 0 : pagamentoData.valorRestante * -1;
                        PagamentosData pagamentosData = new PagamentosData(0, 1, Float.parseFloat(valorPagamentoTxt.getText()), pagamentoData.valorTroco);
                        listPagamentosData.add(pagamentosData);
                        if (listPagamentosData.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Pagamento realizado com Dinheiro\nTroco R$ " + pagamentoData.valorTroco, "Pagamento realizado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
                            encerrar();
                        } else {
                            String message = "";
                            for (int i = 0; i < listPagamentosData.size(); i++) {
                                switch (listPagamentosData.get(i).getTipo()) {
                                    case 0 -> {
                                        message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Dinheiro\n";
                                    }
                                    case 1 -> {
                                        message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Débito\n";
                                    }
                                    case 2 -> {
                                        message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Crédito em ";
                                        message += (listPagamentosData.get(i).getVezes() == 0) ? "1 vez\n" : (listPagamentosData.get(i).getVezes()) + " vezes\n";
                                    }
                                }
                            }
                            message += "Troco: R$ " + pagamentoData.valorTroco;
                            String[] options = {"Finalizar Venda", "Voltar"};
                            jf.setVisible(false);
                            int showOptionDialog = JOptionPane.showOptionDialog(
                                    jf, message, "Pagamento realizado com sucesso!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (showOptionDialog == 0) {
                                encerrar();
                            } else {
                                jf.setVisible(true);
                            }
                        }
                    } else {
                        PagamentosData pagamentosData = new PagamentosData(0, 1, Float.parseFloat(valorPagamentoTxt.getText()), 0);
                        listPagamentosData.add(pagamentosData);
                        dtm.addRow(new String[]{"Dinheiro", valorPagamentoTxt.getText()});
                        String valor = (1 < pagamentoData.valorRestante) ? "Faltam R$ " + pagamentoData.valorRestante : "Falta R$ " + pagamentoData.valorRestante;
                        falta.setText(valor);
                        jf.pack();
                    }
                }
                case 1 -> {//Débito
                    if (Integer.parseInt(valorPagamentoTxt.getText()) <= pagamentoData.valorRestante) {
                        pagamentoData.valorRestante = (pagamentoData.valorRestante == 0) ? 0 : pagamentoData.valorRestante - Float.parseFloat(valorPagamentoTxt.getText());
                        PagamentosData pagamentosData = new PagamentosData(1, 1, Float.parseFloat(valorPagamentoTxt.getText()), 0);
                        listPagamentosData.add(pagamentosData);
                        dtm.addRow(new String[]{"Débito", valorPagamentoTxt.getText()});
                        String valor = (1 < pagamentoData.valorRestante) ? "Faltam R$ " + pagamentoData.valorRestante : "Falta R$ " + pagamentoData.valorRestante;
                        falta.setText(valor);
                        jf.pack();
                        if (pagamentoData.valorRestante == 0) {
                            String message = "";
                            for (int i = 0; i < listPagamentosData.size(); i++) {
                                switch (listPagamentosData.get(i).getTipo()) {
                                    case 0 -> {
                                        message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Dinheiro\n";
                                    }
                                    case 1 -> {
                                        message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Débito\n";
                                    }
                                    case 2 -> {
                                        message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Crédito em ";
                                        message += (listPagamentosData.get(i).getVezes() == 0) ? "1 vez\n" : (listPagamentosData.get(i).getVezes()) + " vezes\n";
                                    }
                                }
                            }
                            message += "Troco: R$ " + pagamentoData.valorTroco;
                            String[] options = {"Finalizar Venda", "Voltar"};
                            jf.setVisible(false);
                            int showOptionDialog = JOptionPane.showOptionDialog(
                                    jf, message, "Pagamento realizado com sucesso!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (showOptionDialog == 0) {
                                //jf.dispose();
                                encerrar();
                            } else {
                                jf.setVisible(true);
                            }
                        }
                    } else {
                        //Alerta: Não pode pagar com cartão um valor acima do valor da conta
                    }
                }
                case 2 -> {//Crédito
                    if (Integer.parseInt(valorPagamentoTxt.getText()) <= pagamentoData.valorRestante) {
                        //Continua pagamento
                        pagamentoData.valorRestante = (pagamentoData.valorRestante == 0) ? 0 : pagamentoData.valorRestante - Float.parseFloat(valorPagamentoTxt.getText());
                        int qtdVezes = jComboBoxQuantidadeVezesPagamento.getSelectedIndex() + 1;
                        PagamentosData pagamentosData = new PagamentosData(2, qtdVezes, Float.parseFloat(valorPagamentoTxt.getText()), 0);
                        listPagamentosData.add(pagamentosData);
                        String str = "Cartão de Crédito em " + (jComboBoxQuantidadeVezesPagamento.getSelectedIndex() + 1);
                        str += (jComboBoxQuantidadeVezesPagamento.getSelectedIndex() == 0) ? " vez" : " vezes";
                        dtm.addRow(new String[]{str, valorPagamentoTxt.getText()});
                        String valor = (1 < pagamentoData.valorRestante) ? "Faltam R$ " + pagamentoData.valorRestante : "Falta R$ " + pagamentoData.valorRestante;
                        falta.setText(valor);
                        jf.pack();
                        if (pagamentoData.valorRestante == 0) {
                            String message = "";
                            for (int i = 0; i < listPagamentosData.size(); i++) {
                                switch (listPagamentosData.get(i).getTipo()) {
                                    case 0 -> {
                                        message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Dinheiro\n";
                                    }
                                    case 1 -> {
                                        message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Débito\n";
                                    }
                                    case 2 -> {
                                        message += "Parte " + (i + 1) + " R$ " + listPagamentosData.get(i).getValor() + " em Crédito em ";
                                        message += (listPagamentosData.get(i).getVezes() == 0) ? "1 vez\n" : (listPagamentosData.get(i).getVezes()) + " vezes\n";
                                    }
                                }
                            }
                            message += "Troco: R$ " + pagamentoData.valorTroco;
                            String[] options = {"Finalizar Venda", "Voltar"};
                            jf.setVisible(false);
                            int showOptionDialog = JOptionPane.showOptionDialog(
                                    jf, message, "Pagamento realizado com sucesso!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (showOptionDialog == 0) {
                                encerrar();
                            } else {
                                jf.setVisible(true);
                            }
                        }
                    } else {
                        //Alerta: Não pode pagar com cartão um valor acima do valor da conta
                    }
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="encerrar">
    private void encerrar() {
        try {
            mySQL = new MySQL();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            mySQL.ps = mySQL.conn.prepareStatement("insert into vendas(id_cliente, data_venda) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            mySQL.ps.setInt(1, jFrameNovaVendaClientesGroupLayout.getCd().getId());
            mySQL.ps.setTimestamp(2, timestamp);
            int executeUpdate = mySQL.ps.executeUpdate();
            int id_nova_venda = -1;
            if (executeUpdate == 1) {
                mySQL.rs = mySQL.ps.getGeneratedKeys();
                if (mySQL.rs.next()) {
                    id_nova_venda = mySQL.rs.getInt(1);
                }
            }
            String sql = "insert into produtos_vendidos(id_venda, id_produto, nome, valor, descricao, quantidade) values ";
            int idProduto = -1;
            String descricao = "";
            for (int i = 0; i < listCarrinho.size(); i++) {
                VendaProdutoData vpd = listCarrinho.get(i);
                if (vpd.getId_produto() != idProduto) {
                    idProduto = vpd.getId_produto();
                    try (Statement stProduto = mySQL.conn.createStatement(); ResultSet rsProduto = stProduto.executeQuery("select descricao from produtos where id=" + vpd.getId_produto())) {
                        if (rsProduto.next()) {
                            descricao = rsProduto.getString("descricao");
                        }
                    }
                }
                sql += "(" + id_nova_venda + ", " + vpd.getId_produto() + ", '" + vpd.getNome() + "', " + vpd.getValor_und() + ", '" + descricao + "', " + vpd.getQtd() + ")";
                sql += ((listCarrinho.size() - 1) == i) ? ";" : ", ";
            }
            mySQL.ps = mySQL.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            executeUpdate = mySQL.ps.executeUpdate();
            if (executeUpdate != 0) {
                mySQL.rs = mySQL.ps.getGeneratedKeys();
                while (mySQL.rs.next()) {
                    System.out.println("Id Produtos Vendidos: " + mySQL.rs.getInt(1));
                }
            }
            sql = "insert into metodos_pagamento(id_venda, metodo, vezes, valor, troco) values ";
            for (int i = 0; i < listPagamentosData.size(); i++) {
                PagamentosData pd = listPagamentosData.get(i);
                sql += "(" + id_nova_venda + ", " + pd.getTipo() + ", " + pd.getVezes() + ", " + pd.getValor() + ", " + pd.getTroco() + ")";
                sql += ((listPagamentosData.size() - 1) == i) ? ";" : ", ";
            }
            mySQL.ps = mySQL.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            executeUpdate = mySQL.ps.executeUpdate();
            if (executeUpdate != 0) {
                mySQL.rs = mySQL.ps.getGeneratedKeys();
                while (mySQL.rs.next()) {
                    System.out.println("Id Metodos Pagamento: " + mySQL.rs.getInt(1));
                }
            }
            mySQL.st = mySQL.conn.createStatement();
            for (VendaProdutoData vpd : listCarrinho) {
                int id_produto = vpd.getId_produto();
                sql = "select * from estoque where id_produto=" + id_produto;
                mySQL.rs = mySQL.st.executeQuery(sql);
                if (mySQL.rs.next()) {
                    int qtd_estoque = mySQL.rs.getInt("quantidade");
                    sql = "update estoque set quantidade=? where id_produto=?";
                    mySQL.ps = mySQL.conn.prepareStatement(sql);
                    mySQL.ps.setInt(1, qtd_estoque - vpd.getQtd());
                    mySQL.ps.setInt(2, id_produto);
                    executeUpdate = mySQL.ps.executeUpdate();
                    if (executeUpdate != 1) {
                        return;
                    }
                }
            }
            if (jfnv.getJifv() != null) {
                jfnv.getJifv().getMyJMB().verificaEstoque();
            }
            jfnv.setVisible(false);
            new JFFinalizacao(jfnv, jFrameNovaVendaClientesGroupLayout.getCd(), listCarrinho, listPagamentosData, jfnv.getJifv(), jfnv.getMyJMB());
        } catch (SQLException ex) {
            Logger.getLogger(JFNovaVendaPagamentoGroupLayout.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(JFNovaVendaPagamentoGroupLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setHorizontalGroup">
    private GroupLayout.Group setHorizontalGroup() {
        return createParallelGroup()
                .addComponent(valorPagamento)
                .addComponent(jComboBoxFormaPagamento)
                .addComponent(jComboBoxQuantidadeVezesPagamento)
                .addComponent(valorPagamentoTxt)
                .addComponent(btnInserirValor, DEFAULT_SIZE, DEFAULT_SIZE, Integer.MAX_VALUE)
                .addComponent(jsp)
                .addComponent(falta)
                .addComponent(troco)
                .addComponent(btnFinalizar, DEFAULT_SIZE, DEFAULT_SIZE, Integer.MAX_VALUE);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setVerticalGroup">
    private GroupLayout.Group setVerticalGroup() {
        return createSequentialGroup()
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(valorPagamento)
                )
                .addGroup(createParallelGroup(GroupLayout.Alignment.CENTER, false)
                        .addComponent(jComboBoxFormaPagamento)
                )
                .addGroup(createParallelGroup(GroupLayout.Alignment.CENTER, false)
                        .addComponent(jComboBoxQuantidadeVezesPagamento)
                )
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(valorPagamentoTxt)
                )
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(btnInserirValor)
                )
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(jsp)
                )
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(falta)
                )
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(troco)
                )
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(btnFinalizar)
                );
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setValues">
    public void setValues(float valor_total_compra) {
        pagamentoData.valorCompra = pagamentoData.valorRestante = valor_total_compra;
        valorPagamento.setText(COMPRA + "R$ " + pagamentoData.valorRestante);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setListCarrinho">
    public void setListCarrinho(List<VendaProdutoData> listCarrinho) {
        this.listCarrinho = listCarrinho;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setListProdutosData">
    public void setListProdutosData(List<ProdutosData> listProdutosDatas) {
        this.listProdutosDatas = listProdutosDatas;
    }
    //</editor-fold>

}
