package com.ruanmattos.main.grouplayout.jinternalframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.data.ClienteData;
import com.ruanmattos.main.data.PagamentosData;
import com.ruanmattos.main.data.VendaProdutoData;
import com.ruanmattos.main.data.VendasData;
import com.ruanmattos.main.database.MySQL;
import com.ruanmattos.main.jframe.JFFinalizacao;
import com.ruanmattos.main.jframe.JFNovaVenda;
import com.ruanmattos.main.jframe.JFProdutosVendidos;
import com.ruanmattos.main.jinternalframe.JIFClientes;
import com.ruanmattos.main.jinternalframe.JIFProdutos;
import com.ruanmattos.main.jinternalframe.JIFVendas;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import javax.swing.table.DefaultTableModel;
//</editor-fold>

/**
 *
 * @author Ruan Pereira Mattos
 */
public class JIFVendasGroupLayout extends GroupLayout {

    private final JIFVendas jifv;
    private MySQL mySQL = null;

    private final JLabel cliente, hora, minuto, dia, mes, ano;
    private final JTextField clienteTxt, horaTxt, minutoTxt, diaTxt, mesTxt, anoTxt;
    private final JButton btnBuscar = new JButton("Buscar");

    private static final ArrayList<ClienteData> arrayListClienteData = new ArrayList<>();
    private static final ArrayList<ClienteData> arrayListClienteDataTmp = new ArrayList<>();
    private static final ArrayList<VendasData> arrayListVendasData = new ArrayList<>();
    private static final ArrayList<VendasData> arrayListVendasDataTmp = new ArrayList<>();
    private static final String[] columnNames = new String[]{"Id", "Cliente", "Hora", "Minuto", "Dia", "Mes", "Ano"};
    private static final DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
    private static final JTable table = new JTable(dtm);
    private static final JScrollPane jsp = new JScrollPane(table);

    private boolean isJFProdutosVendidosOpen = false;
    private int qtdJFProdutosVendidosOpened = 0;

    public JIFVendasGroupLayout(Container host, JIFVendas jifv) {
        super(host);
        this.jifv = jifv;
        cliente = new JLabel("Cliente");
        hora = new JLabel("Hora");
        minuto = new JLabel("Minuto");
        dia = new JLabel("Dia");
        mes = new JLabel("Mês");
        ano = new JLabel("Ano");
        clienteTxt = new JTextField();
        horaTxt = new JTextField();
        minutoTxt = new JTextField();
        diaTxt = new JTextField();
        mesTxt = new JTextField();
        anoTxt = new JTextField();
        cliente.setVisible(false);
        clienteTxt.setVisible(false);
        hora.setVisible(false);
        horaTxt.setVisible(false);
        minuto.setVisible(false);
        minutoTxt.setVisible(false);
        dia.setVisible(false);
        diaTxt.setVisible(false);
        mes.setVisible(false);
        mesTxt.setVisible(false);
        ano.setVisible(false);
        anoTxt.setVisible(false);
        btnBuscar.setVisible(false);
        clienteTxt.addKeyListener(ka);
        horaTxt.addKeyListener(ka);
        minutoTxt.addKeyListener(ka);
        diaTxt.addKeyListener(ka);
        mesTxt.addKeyListener(ka);
        anoTxt.addKeyListener(ka);
        btnBuscar.addActionListener(al);
        atualiza();
        setHorizontalGroup(setHorizontalGroup());
        setVerticalGroup(setVerticalGroup());
        table.addMouseListener(ma);
        jsp.addMouseListener(ma);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    //<editor-fold defaultstate="collapsed" desc="setHorizontalGroup">
    private Group setHorizontalGroup() {
        return createParallelGroup()
                .addGroup(
                        createSequentialGroup()
                                .addGroup(
                                        createParallelGroup(Alignment.TRAILING, false)
                                                .addComponent(cliente)
                                                .addComponent(hora)
                                                .addComponent(minuto)
                                                .addComponent(dia)
                                                .addComponent(mes)
                                                .addComponent(ano)
                                )
                                .addGroup(
                                        createParallelGroup()
                                                .addComponent(clienteTxt)
                                                .addComponent(horaTxt)
                                                .addComponent(minutoTxt)
                                                .addComponent(diaTxt)
                                                .addComponent(mesTxt)
                                                .addComponent(anoTxt)
                                )
                )
                .addComponent(btnBuscar, Alignment.CENTER, DEFAULT_SIZE, DEFAULT_SIZE, Integer.MAX_VALUE)
                .addComponent(jsp);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setVerticalGroup">
    private Group setVerticalGroup() {
        return createSequentialGroup()
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(cliente)
                                .addComponent(clienteTxt)
                )
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(hora)
                                .addComponent(horaTxt)
                )
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(minuto)
                                .addComponent(minutoTxt)
                )
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(dia)
                                .addComponent(diaTxt)
                )
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(mes)
                                .addComponent(mesTxt)
                )
                .addGroup(
                        createParallelGroup(Alignment.CENTER, false)
                                .addComponent(ano)
                                .addComponent(anoTxt)
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
            String sql = "select * from vendas order by data_venda desc";
            mySQL.rs = mySQL.st.executeQuery(sql);
            for (VendasData vd : arrayListVendasData) {
                dtm.removeRow(0);
            }
            arrayListVendasData.clear();
            arrayListClienteData.clear();
            while (mySQL.rs.next()) {
                int id = mySQL.rs.getInt("id");
                int idCliente = mySQL.rs.getInt("id_cliente");
                Timestamp timestamp = mySQL.rs.getTimestamp("data_venda");
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(timestamp.getTime());
                int hora = c.get(Calendar.HOUR_OF_DAY);
                int minuto = c.get(Calendar.MINUTE);
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH) + 1;
                int ano = c.get(Calendar.YEAR);
                Statement stCliente = mySQL.conn.createStatement();
                ResultSet rsCliente = stCliente.executeQuery("select * from clientes where id=" + idCliente);
                String nome = "";
                if (rsCliente.next()) {
                    nome = rsCliente.getString("nome");
                }
                rsCliente.close();
                stCliente.close();
                VendasData vd = new VendasData(id, idCliente, nome, hora, minuto, dia, mes, ano);
                vd.data_venda = timestamp;
                Statement stMetodosPagamento = mySQL.conn.createStatement();
                ResultSet rsMetodosPagamento = stMetodosPagamento.executeQuery("select * from metodos_pagamento where id_venda=" + id);
                List<PagamentosData> pagamentosDatas = new ArrayList<>();
                while (rsMetodosPagamento.next()) {
                    int tipo = rsMetodosPagamento.getInt("metodo");
                    int vezes = rsMetodosPagamento.getInt("vezes");
                    Float valor = rsMetodosPagamento.getFloat("valor");
                    Float troco = rsMetodosPagamento.getFloat("troco");
                    PagamentosData pd = new PagamentosData(tipo, vezes, valor, troco);
                    pagamentosDatas.add(pd);
                }
                vd.pagamentos = pagamentosDatas;
                arrayListVendasData.add(vd);
                arrayListClienteData.add(new ClienteData(idCliente, nome, null, null));
                dtm.addRow(vd.toObject());
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFProdutos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setVisible">
    private void setVisibility(boolean visible) {
        cliente.setVisible(visible);
        clienteTxt.setVisible(visible);
        hora.setVisible(visible);
        horaTxt.setVisible(visible);
        minuto.setVisible(visible);
        minutoTxt.setVisible(visible);
        dia.setVisible(visible);
        diaTxt.setVisible(visible);
        mes.setVisible(visible);
        mesTxt.setVisible(visible);
        ano.setVisible(visible);
        anoTxt.setVisible(visible);
        btnBuscar.setVisible(visible);
        setAutoCreateGaps(visible);
        setAutoCreateContainerGaps(visible);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getVisibility">
    public boolean getVisibility() {
        return cliente.isVisible();
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
                JMenuItem jmiNovaVenda = new JMenuItem("Nova Venda");
                JMenuItem jmiBuscar = new JMenuItem("Buscar");
                JMenuItem jmiVerProdutos = new JMenuItem("Ver Produtos Vendidos");
                JMenuItem jmiReemprimir = new JMenuItem("Reemprimir venda");
                jmiAtualiza.addActionListener((ee) -> {
                    atualiza();
                });
                jmiNovaVenda.addActionListener((ee) -> {
                    new JFNovaVenda(null, jifv);
                    jifv.getMyJMB().getJfm().setVisible(false);
                });
                jmiBuscar.addActionListener((ee) -> {
                    setVisibility(!getVisibility());
                });
                jmiVerProdutos.addActionListener((ee) -> {
                    final String[] options = new String[]{"Abrir a nova tela", "Cancelar"};
                    final String msg = "Você já possui uma janela com os produtos vendidos de outra pessoa, caso queira abrir outra tela,\n a tela anterior vai parar de obedecer os comando funcionando apenas o fechamento da mesma";
                    if (arrayListVendasDataTmp.isEmpty()) {
                        if (isJFProdutosVendidosOpen) {
                            int showConfirmDialog = JOptionPane.showOptionDialog(jifv, msg, "Cuidado!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                            if (showConfirmDialog == 0) {
                                new JFProdutosVendidos(JIFVendasGroupLayout.this, arrayListVendasData.get(selectedRow), arrayListClienteData.get(selectedRow));
                                qtdJFProdutosVendidosOpened++;
                            }
                            System.out.println("OPCAO: " + showConfirmDialog);
                        } else {
                            new JFProdutosVendidos(JIFVendasGroupLayout.this, arrayListVendasData.get(selectedRow), arrayListClienteData.get(selectedRow));
                            isJFProdutosVendidosOpen = true;
                            qtdJFProdutosVendidosOpened++;
                        }
                    } else {
                        if (isJFProdutosVendidosOpen) {
                            int showConfirmDialog = JOptionPane.showOptionDialog(jifv, msg, "Cuidado!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                            if (showConfirmDialog == 0) {
                                new JFProdutosVendidos(JIFVendasGroupLayout.this, arrayListVendasDataTmp.get(selectedRow), arrayListClienteDataTmp.get(selectedRow));
                                qtdJFProdutosVendidosOpened++;
                            }
                            System.out.println("OPCAO: " + showConfirmDialog);
                        } else {
                            new JFProdutosVendidos(JIFVendasGroupLayout.this, arrayListVendasDataTmp.get(selectedRow), arrayListClienteDataTmp.get(selectedRow));
                            isJFProdutosVendidosOpen = true;
                            qtdJFProdutosVendidosOpened++;
                        }
                    }
                });
                jmiReemprimir.addActionListener((ee) -> {
                    if (selectedRow != -1) {
                        reemprimir(arrayListVendasData.get(selectedRow).getId());
                    }
                });
                jpm.add(jmiAtualiza);
                try {
                    Statement st = mySQL.conn.createStatement();
                    ResultSet rs = st.executeQuery("select * from estoque where quantidade != 0");
                    if (rs.next()) {
                        jpm.add(jmiNovaVenda);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JIFVendasGroupLayout.class.getName()).log(Level.SEVERE, null, ex);
                }
                jpm.add(jmiBuscar);
                if (selectedRow != -1) {
                    jpm.add(jmiVerProdutos);
                    jpm.add(jmiReemprimir);
                }
                if (selectedRow != -1) {
                    String nome = arrayListVendasData.get(selectedRow).getNome();
                    JMenuItem jmiExcluirVenda = new JMenuItem("Excluir Venda " + nome);
                    jmiExcluirVenda.addActionListener((ee) -> {
                        try {
                            VendasData vd = arrayListVendasData.get(selectedRow);
                            mySQL.ps = mySQL.conn.prepareStatement("delete from metodos_pagamento where id_venda=" + vd.getId());
                            int executeUpdate = mySQL.ps.executeUpdate();
                            //Mudar para while
                            if (executeUpdate == 1) {
                                mySQL.ps = mySQL.conn.prepareStatement("delete from produtos_vendidos where id_venda=" + vd.getId());
                                executeUpdate = mySQL.ps.executeUpdate();
                                if (executeUpdate != 0) {
                                    mySQL.ps = mySQL.conn.prepareStatement("delete from vendas where id=" + vd.getId());
                                    executeUpdate = mySQL.ps.executeUpdate();
                                    if (executeUpdate != 0) {
                                        atualiza();
                                    }
                                }
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(JIFClientes.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });
                    jpm.add(jmiExcluirVenda);
                }
                jpm.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="reemprimir">
    private void reemprimir(int id_venda) {
        try {
            mySQL.st = mySQL.conn.createStatement();
            mySQL.rs = mySQL.st.executeQuery("select * from vendas where id=" + id_venda);
            int id_cliente;
            List<VendaProdutoData> listVendaProdutoDatas = new ArrayList<>();
            ClienteData cd = null;
            boolean cdInitialized = false;
            if (mySQL.rs.next()) {
                id_cliente = mySQL.rs.getInt("id_cliente");
                mySQL.rs = mySQL.st.executeQuery("select * from clientes where id=" + id_cliente);
                if (mySQL.rs.next()) {
                    String nome = mySQL.rs.getString("nome");
                    String telefone = mySQL.rs.getString("telefone");
                    String cpf = mySQL.rs.getString("cpf");
                    cd = new ClienteData(id_cliente, nome, telefone, cpf);
                    cdInitialized = true;
                }
            }
            mySQL.rs = mySQL.st.executeQuery("select * from produtos_vendidos where id_venda=" + id_venda);
            while (mySQL.rs.next()) {
                int id_produto = mySQL.rs.getInt("id_produto");
                float valor = mySQL.rs.getFloat("valor");
                int qtd = mySQL.rs.getInt("quantidade");
                String nome = mySQL.rs.getString("nome");
                VendaProdutoData vpd = new VendaProdutoData(id_produto, nome, qtd, valor, (valor * qtd));
                listVendaProdutoDatas.add(vpd);
            }
            String sql = "select * from metodos_pagamento where id_venda=" + id_venda;
            mySQL.rs = mySQL.st.executeQuery(sql);
            List<PagamentosData> listPagamentosDatas = new ArrayList<>();
            while (mySQL.rs.next()) {
                int tipo = mySQL.rs.getInt("metodo");
                int vezes = mySQL.rs.getInt("vezes");
                Float valor = mySQL.rs.getFloat("valor");
                Float troco = mySQL.rs.getFloat("troco");
                PagamentosData pd = new PagamentosData(tipo, vezes, valor, troco);
                listPagamentosDatas.add(pd);
            }
            if (cdInitialized) {
                new JFFinalizacao(null, cd, listVendaProdutoDatas, listPagamentosDatas, jifv, null);
            } else {
                JOptionPane.showMessageDialog(jifv, "Não foi possível buscar os produtos");
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFVendas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="KeyAdapter">
    private KeyAdapter ka = new KeyAdapter() {
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
    private ActionListener al = (e) -> {
        search();
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="search">
    private void search() {
        arrayListVendasDataTmp.clear();
        arrayListClienteDataTmp.clear();
        arrayListVendasData.forEach(vd -> {
            boolean clienteMatch = false;
            boolean horaMatch = false;
            boolean minutoMatch = false;
            boolean dayMatch = false;
            boolean monthMatch = false;
            boolean yearMatch = false;
            if (!clienteTxt.getText().isBlank()) {//Modificar para procurar somente entre a ocorrencia atual e o textfield
                try {
                    mySQL.rs = mySQL.st.executeQuery("select * from vendas where id_cliente=" + vd.getId_cliente());
                    if (mySQL.rs.next()) {
                        mySQL.rs = mySQL.st.executeQuery("select * from clientes where id=" + vd.getId_cliente());
                        if (mySQL.rs.next()) {
                            clienteMatch = mySQL.rs.getString("nome").contains(clienteTxt.getText());
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JIFVendasGroupLayout.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                clienteMatch = true;
            }
            if (!horaTxt.getText().isBlank()) {
                horaMatch = vd.getHora() == Integer.parseInt(horaTxt.getText());
                System.out.println("Hora NÃO em branco: Possui a resposta? " + horaMatch);
            } else {
                System.out.println("Hora em branco");
                horaMatch = true;
            }
            if (!minutoTxt.getText().isBlank()) {
                minutoMatch = vd.getMinuto() == Integer.parseInt(minutoTxt.getText());
                System.out.println("Minuto NÃO em branco: Possui a resposta? " + minutoMatch);
            } else {
                System.out.println("Minuto em branco");
                minutoMatch = true;
            }
            if (!diaTxt.getText().isBlank()) {
                System.out.println("Dia não em branco");
                if (vd.getDia() == Integer.parseInt(diaTxt.getText())) {
                    dayMatch = true;
                }
            } else {
                System.out.println("Dia em branco");
                dayMatch = true;
            }
            if (!mesTxt.getText().isBlank()) {
                System.out.println("Mes não em branco");
                if (vd.getMes() == Integer.parseInt(mesTxt.getText())) {
                    monthMatch = true;
                }
            } else {
                System.out.println("Mes em branco");
                monthMatch = true;
            }
            if (!anoTxt.getText().isBlank()) {
                System.out.println("Ano não em branco");
                if (vd.getAno() == Integer.parseInt(anoTxt.getText())) {
                    yearMatch = true;
                }
            } else {
                System.out.println("Ano em branco");
                yearMatch = true;
            }
            if (clienteMatch && horaMatch && minutoMatch && dayMatch && monthMatch && yearMatch) {
                arrayListVendasDataTmp.add(vd);
                arrayListClienteDataTmp.add(new ClienteData(vd.getId_cliente(), vd.getNome(), null, null));
            }
        });
        int rowCount = dtm.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            dtm.removeRow(0);
        }
        if (clienteTxt.getText().isBlank() && horaTxt.getText().isBlank() && minutoTxt.getText().isBlank() && diaTxt.getText().isBlank() && mesTxt.getText().isBlank() && anoTxt.getText().isBlank()) {
            for (VendasData vd : arrayListVendasData) {
                dtm.addRow(vd.toObject());
            }
            return;
        }
        if (!arrayListVendasDataTmp.isEmpty()) {
            arrayListVendasDataTmp.forEach(vd -> {
                dtm.addRow(vd.toObject());
            });
        }
    }
    //</editor-fold>

    public void setIsJFProdutosVendidosClose() {
        this.isJFProdutosVendidosOpen = false;
    }

    public int getQtdJFProdutosVendidosOpened() {
        return qtdJFProdutosVendidosOpened;
    }

    public void setQtdJFProdutosVendidosOpened() {
        qtdJFProdutosVendidosOpened--;
    }
}
