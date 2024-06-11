package com.ruanmattos.main.grouplayout.jinternalframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.data.ClienteData;
import com.ruanmattos.main.database.MySQL;
import com.ruanmattos.main.jframe.JFNovoCliente;
import com.ruanmattos.main.jinternalframe.JIFClientes;
import com.ruanmattos.main.jinternalframe.JIFProdutos;
import java.awt.Container;
import java.awt.event.ActionEvent;
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
public class JIFClientesGroupLayout extends GroupLayout implements ActionListener {

    private MySQL mySQL = null;

    private final JLabel nome, telefone;
    private final JTextField nomeTxt, telefoneTxt;
    private final JButton btnBuscar;

    private static final ArrayList<ClienteData> arrayListClienteData = new ArrayList<>();
    private static final ArrayList<ClienteData> arrayListClienteDataTmp = new ArrayList<>();
    private static final String[] COLUMN_NAMES = new String[]{"Nome", "Telefone", "CPF"};
    private static final DefaultTableModel dtm = new DefaultTableModel(COLUMN_NAMES, 0);
    private static final JTable table = new JTable(dtm);
    private static final JScrollPane jsp = new JScrollPane(table);
    private final JIFClientes jifc;

    public JIFClientesGroupLayout(Container host, JIFClientes jifc) {
        super(host);
        this.jifc = jifc;
        nome = new JLabel("Nome");
        telefone = new JLabel("Telefone");
        btnBuscar = new JButton("Buscar");
        nomeTxt = new JTextField();
        telefoneTxt = new JTextField();
        nome.setVisible(false);
        nomeTxt.setVisible(false);
        telefone.setVisible(false);
        telefoneTxt.setVisible(false);
        btnBuscar.setVisible(false);
        table.addMouseListener(ma);
        jsp.addMouseListener(ma);
        nomeTxt.addKeyListener(ka);
        telefoneTxt.addKeyListener(ka);
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
    private GroupLayout.Group setHorizontalGroup() {
        return createParallelGroup()
                .addGroup(
                        createSequentialGroup()
                                .addGroup(
                                        createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(nome)
                                                .addComponent(telefone)
                                )
                                .addGroup(
                                        createParallelGroup()
                                                .addComponent(nomeTxt)
                                                .addComponent(telefoneTxt)
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
                                .addComponent(telefone)
                                .addComponent(telefoneTxt)
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
        telefone.setVisible(visible);
        telefoneTxt.setVisible(visible);
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

    //<editor-fold defaultstate="collapsed" desc="atualiza">
    public void atualiza() {
        try {
            if (mySQL == null) {
                mySQL = new MySQL();
            }
            mySQL.st = mySQL.conn.createStatement();
            mySQL.rs = mySQL.st.executeQuery("select * from clientes order by nome");
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListClienteData.clear();
            arrayListClienteDataTmp.clear();
            while (mySQL.rs.next()) {
                int id = mySQL.rs.getInt("id");
                String nome = mySQL.rs.getString("nome");
                String telefone = mySQL.rs.getString("telefone");
                String cpf = mySQL.rs.getString("cpf");
                ClienteData cd = new ClienteData(id, nome, telefone, cpf);
                dtm.addRow(cd.toObjectNomeTelefoneCPF());
                arrayListClienteData.add(cd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFClientes.class.getName()).log(Level.SEVERE, null, ex);
            jifc.dispose();
            JOptionPane.showMessageDialog(jifc, ex.toString(), "Algum erro aconteceu", JOptionPane.INFORMATION_MESSAGE);
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
                JMenuItem jmiNovoCliente = new JMenuItem("Novo Cliente");
                JMenuItem jmiBusca = new JMenuItem("Buscar");
                jpm.add(jmiAtualiza);
                jpm.add(jmiNovoCliente);
                jpm.add(jmiBusca);
                jmiAtualiza.addActionListener((ee) -> {
                    atualiza();
                });
                jmiNovoCliente.addActionListener((ee) -> {
                    jifc.getMyJMB().getJfm().setVisible(false);
                    JFNovoCliente jfNovoCliente = new JFNovoCliente(null, JIFClientesGroupLayout.this);
                });
                jmiBusca.addActionListener((ee) -> {
                    setVisibility(!getVisibility());
                });
                if (selectedRow != -1) {
                    String nome = arrayListClienteData.get(selectedRow).getNome();
                    JMenuItem jmi = new JMenuItem("Excluir cliente " + nome);
                    jmi.addActionListener((ee) -> {
                        try {
                            mySQL.st = mySQL.conn.createStatement();
                            mySQL.rs = mySQL.st.executeQuery("select * from vendas where id_cliente=" + arrayListClienteData.get(selectedRow).getId());
                            if (mySQL.rs.next()) {
                                JOptionPane.showMessageDialog(jmiNovoCliente, "Você não pode deletar um usuário que possui pelo menos uma venda no sistema", "Erro ao deletar o cliente: " + arrayListClienteData.get(selectedRow).getNome(), JOptionPane.WARNING_MESSAGE);
                            } else {
                                mySQL.ps = mySQL.conn.prepareStatement("delete from clientes where id=?");
                                mySQL.ps.setInt(1, arrayListClienteData.get(selectedRow).getId());
                                int executeUpdate = mySQL.ps.executeUpdate();
                                if (executeUpdate == 1) {
                                    atualiza();
                                }
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(JIFClientes.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });
                    jpm.add(jmi);
                }
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

    //<editor-fold defaultstate="collapsed" desc="actionPerformed">
    @Override
    public void actionPerformed(ActionEvent e) {
        search();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="search">
    private void search() {
        arrayListClienteDataTmp.clear();
        if (!nomeTxt.getText().isBlank() && !telefoneTxt.getText().isBlank()) {            
            for (int i = 0; i < arrayListClienteData.size(); i++) {
                ClienteData cd = arrayListClienteData.get(i);
                CharSequence charSequenceNome = nomeTxt.getText();
                CharSequence charSequenceTelefone = telefoneTxt.getText();
                if (cd.getNome().contains(charSequenceNome) && cd.getTelefone().contains(charSequenceTelefone)) {
                    arrayListClienteDataTmp.add(cd);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListClienteDataTmp.forEach(cd -> {
                dtm.addRow(cd.toObjectNomeTelefoneCPF());
            });
        } else if (!nomeTxt.getText().isBlank()) {
            for (int i = 0; i < arrayListClienteData.size(); i++) {
                ClienteData cd = arrayListClienteData.get(i);
                CharSequence charSequence = nomeTxt.getText();
                if (cd.getNome().contains(charSequence)) {
                    arrayListClienteDataTmp.add(cd);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListClienteDataTmp.forEach(cd -> {
                dtm.addRow(cd.toObjectNomeTelefoneCPF());
            });
        } else if (!telefoneTxt.getText().isBlank()) {
            for (int i = 0; i < arrayListClienteData.size(); i++) {
                ClienteData cd = arrayListClienteData.get(i);
                CharSequence charSequence = telefoneTxt.getText();
                if (cd.getTelefone().contains(charSequence)) {
                    arrayListClienteDataTmp.add(cd);
                }
            }
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListClienteDataTmp.forEach(cd -> {
                dtm.addRow(cd.toObjectNomeTelefoneCPF());
            });
        } else {
            int rowCount = dtm.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
            arrayListClienteData.forEach(cd -> {
                dtm.addRow(cd.toObjectNomeTelefoneCPF());
            });
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="TableModelListener">
    private final TableModelListener tml = (e) -> {
        if (e.getType() == TableModelEvent.UPDATE) {
            try {
                String valueChanged = table.getValueAt(e.getFirstRow(), e.getColumn()).toString();
                int clientId = arrayListClienteData.get(e.getFirstRow()).getId();
                switch (e.getColumn()) {
                    case 0 -> {//Nome
                        mySQL.ps = mySQL.conn.prepareStatement("update clientes set nome='" + valueChanged + "' where id='" + clientId + "'");
                        mySQL.ps.execute();
                    }
                    case 1 -> {//Telefone
                        mySQL.ps = mySQL.conn.prepareStatement("update clientes set telefone='" + valueChanged + "' where id='" + clientId + "'");
                        mySQL.ps.execute();
                    }
                    case 2 -> {
                        mySQL.ps = mySQL.conn.prepareStatement("update clientes set cpf='" + valueChanged + "' where id=" + clientId);
                        mySQL.ps.execute();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(JIFProdutos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="JIFClientes">
    public JIFClientes getJifc() {
        return jifc;
    }
    //</editor-fold>

}
