package com.ruanmattos.main.grouplayout;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.data.ClienteData;
import com.ruanmattos.main.database.MySQL;
import com.ruanmattos.main.defaulttablemodel.MyDefaultTableModel;
import com.ruanmattos.main.jframe.JFNovaVenda;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
//</editor-fold>

/**
 *
 * @author Ruan Pereira Mattos
 */
public class JFNovaVendaClientesGroupLayout extends GroupLayout implements ActionListener {

    private MySQL mySQL = null;

    private ClienteData cd = null;
    private final JLabel nome = new JLabel("Nome");
    private final JLabel telefone = new JLabel("Telefone");
    private final JTextField nomeTxt = new JTextField();
    private final JTextField telefoneTxt = new JTextField();
    private final JButton btnAdicionarCliente = new JButton("Adicionar Cliente");

    private final String[] columnNames = {"Nome", "Telefone"};
    private final MyDefaultTableModel dtm = new MyDefaultTableModel(columnNames, 0);
    private final JTable table = new JTable(dtm);
    private final JScrollPane jsp = new JScrollPane(table);
    private final List<ClienteData> listClienteData = new ArrayList<>();
    private final List<ClienteData> listClienteDataTemp = new ArrayList<>();

    public JFNovaVendaClientesGroupLayout(Container host) {
        super(host);
        setAutoCreateGaps(true);
        setAutoCreateContainerGaps(true);
        setHorizontalGroup(setHorizontalGroup());
        setVerticalGroup(setVerticalGroup());
        atualizaClientes();
        table.addMouseListener(ma);
        btnAdicionarCliente.addActionListener(this);
        nomeTxt.addKeyListener(ka);
        telefoneTxt.addKeyListener(ka);
    }

    //<editor-fold defaultstate="collapsed" desc="setHorizontalGroup">
    private GroupLayout.Group setHorizontalGroup() {
        return createParallelGroup()
                .addGroup(createSequentialGroup()
                        .addGroup(
                                createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(nome)
                                        .addComponent(telefone)
                        )
                        .addGroup(createParallelGroup()
                                .addComponent(nomeTxt)
                                .addComponent(telefoneTxt)
                        )
                )
                .addComponent(btnAdicionarCliente, DEFAULT_SIZE, DEFAULT_SIZE, Integer.MAX_VALUE)
                .addComponent(jsp);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setVerticalGroup">
    private GroupLayout.Group setVerticalGroup() {
        return createSequentialGroup()
                .addGroup(createParallelGroup(GroupLayout.Alignment.CENTER, false)
                        .addComponent(nome)
                        .addComponent(nomeTxt)
                )
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(telefone)
                                .addComponent(telefoneTxt)
                )
                .addGroup(createParallelGroup(GroupLayout.Alignment.CENTER, false)
                        .addComponent(btnAdicionarCliente)
                )
                .addComponent(jsp);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="atualizaClientes">
    private void atualizaClientes() {
        try {
            mySQL = new MySQL();
            mySQL.st = mySQL.conn.createStatement();
            mySQL.rs = mySQL.st.executeQuery("select * from clientes order by nome");
            while (mySQL.rs.next()) {
                int id = mySQL.rs.getInt("id");
                String nome = mySQL.rs.getString("nome");
                String telefone = mySQL.rs.getString("telefone");
                String cpf = mySQL.rs.getString("cpf");
                ClienteData cd = new ClienteData(id, nome, telefone, cpf);
                listClienteData.add(cd);
                dtm.addRow(cd.toObjectNomeTelefoneCPF());
            }
        } catch (SQLException ex) {
            Logger.getLogger(JFNovaVendaClientesGroupLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ClienteData">
    public ClienteData getCd() {
        return cd;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="actionPèrformed">
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!listClienteDataTemp.isEmpty()) {
            if (table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                ClienteData cd = listClienteDataTemp.get(selectedRow);
                nomeTxt.setText(cd.getNome());
                telefoneTxt.setText(cd.getTelefone());
                this.cd = cd;
            }
        } else {
            if (table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                ClienteData cd = listClienteData.get(selectedRow);
                nomeTxt.setText(cd.getNome());
                telefoneTxt.setText(cd.getTelefone());
                this.cd = cd;
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="KeyAdapter">
    private final KeyAdapter ka = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                listClienteDataTemp.clear();
                if (!nomeTxt.getText().isBlank() && !telefoneTxt.getText().isBlank()) {
                    for (int i = 0; i < listClienteData.size(); i++) {
                        ClienteData cd = listClienteData.get(i);
                        CharSequence charSequenceNome = nomeTxt.getText();
                        CharSequence charSequenceTelefone = telefoneTxt.getText();
                        if (cd.getNome().contains(charSequenceNome) && cd.getTelefone().contains(charSequenceTelefone)) {
                            listClienteDataTemp.add(cd);
                        }
                    }
                    int rowCount = dtm.getRowCount();
                    for (int i = 0; i < rowCount; i++) {
                        dtm.removeRow(0);
                    }
                    listClienteDataTemp.forEach(cd -> {
                        dtm.addRow(cd.toObjectNomeTelefoneCPF());
                    });
                } else if (!nomeTxt.getText().isBlank()) {
                    for (int i = 0; i < listClienteData.size(); i++) {
                        ClienteData cd = listClienteData.get(i);
                        CharSequence charSequence = nomeTxt.getText();
                        if (cd.getNome().contains(charSequence)) {
                            listClienteDataTemp.add(cd);
                        }
                    }
                    int rowCount = dtm.getRowCount();
                    for (int i = 0; i < rowCount; i++) {
                        dtm.removeRow(0);
                    }
                    listClienteDataTemp.forEach(cd -> {
                        dtm.addRow(cd.toObjectNomeTelefoneCPF());
                    });
                } else if (!telefoneTxt.getText().isBlank()) {
                    for (int i = 0; i < listClienteData.size(); i++) {
                        ClienteData cd = listClienteData.get(i);
                        CharSequence charSequence = telefoneTxt.getText();
                        if (cd.getTelefone().contains(charSequence)) {
                            listClienteDataTemp.add(cd);
                        }
                    }
                    int rowCount = dtm.getRowCount();
                    for (int i = 0; i < rowCount; i++) {
                        dtm.removeRow(0);
                    }
                    listClienteDataTemp.forEach(cd -> {
                        dtm.addRow(cd.toObjectNomeTelefoneCPF());
                    });
                } else {
                    int rowCount = dtm.getRowCount();
                    for (int i = 0; i < rowCount; i++) {
                        dtm.removeRow(0);
                    }
                    listClienteData.forEach(cd -> {
                        dtm.addRow(cd.toObjectNomeTelefoneCPF());
                    });
                }
            }
        }

    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="MouseAdapter">
    private final MouseAdapter ma = new MouseAdapter() {

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (table.getSelectedRow() != -1) {
                    int selectedRow = table.getSelectedRow();
                    if (listClienteDataTemp.isEmpty()) {
                        cd = listClienteData.get(selectedRow);
                    } else {
                        cd = listClienteDataTemp.get(selectedRow);
                    }
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                if (table.getSelectedRow() != -1) {
                    int selectedRow = table.getSelectedRow();
                    JPopupMenu jpm = new JPopupMenu();
                    String nomeNome = "";
                    if (listClienteDataTemp.isEmpty()) {
                        nomeNome = "Selecionar cliente " + listClienteData.get(selectedRow).getNome() + " telefone " + listClienteData.get(selectedRow).getTelefone();
                    } else {
                        nomeNome = "Selecionar cliente " + listClienteDataTemp.get(selectedRow).getNome() + " telefone " + listClienteDataTemp.get(selectedRow).getTelefone();
                    }
                    JMenuItem jmi = new JMenuItem(nomeNome);
                    jmi.addActionListener((ee) -> {
                        try {
                            String nome = listClienteData.get(table.getSelectedRow()).getNome();
                            String telefone = listClienteData.get(table.getSelectedRow()).getTelefone();
                            nomeTxt.setText(nome);
                            telefoneTxt.setText(telefone);
                            //Mudar a pesquisa para List Cliente Data
                            String URL = "jdbc:mysql://10.0.0.106:3306/mydb";
                            String USER = "usuario";
                            String PASS = "usuario";
                            Connection conn = DriverManager.getConnection(URL, USER, PASS);
                            Statement st = conn.createStatement();
                            ResultSet rs = st.executeQuery("select * from clientes where nome='" + nome + "' and telefone='" + telefone + "'");
                            if (rs.next()) {
                                int id = rs.getInt("id");
                                String cpf = rs.getString("cpf");
                                cd = new ClienteData(id, nome, telefone, cpf);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(JFNovaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    jpm.add(jmi);
                    jpm.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }
    };
    //</editor-fold>

}
