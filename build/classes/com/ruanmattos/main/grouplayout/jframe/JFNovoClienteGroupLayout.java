package com.ruanmattos.main.grouplayout.jframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.database.MySQL;
import com.ruanmattos.main.jframe.JFNovoCliente;
import com.ruanmattos.main.jinternalframe.JIFClientes;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
//</editor-fold>

/**
 *
 * @author Ruan Pereira Mattos
 */
public class JFNovoClienteGroupLayout extends GroupLayout implements ActionListener {

    private MySQL mySQL = null;
    private final JFNovoCliente jfnc;

    private final JLabel nome;
    private final JLabel telefone;
    private final JLabel cpf;
    private final JTextField nomeTxt;
    private final JTextField telefoneTxt;
    private final JTextField cpfTxt;
    private final JButton btn;

    public JFNovoClienteGroupLayout(Container host, JFNovoCliente jfnc) {
        super(host);
        this.jfnc = jfnc;
        nome = new JLabel("Nome");
        telefone = new JLabel("Telefone");
        cpf = new JLabel("CPF");
        nomeTxt = new JTextField();
        telefoneTxt = new JTextField();
        cpfTxt = new JTextField();
        btn = new JButton("Cadastrar");
        setAutoCreateGaps(true);
        setAutoCreateContainerGaps(true);
        setHorizontalGroup(setHorizontalGroup());
        setVerticalGroup(setVerticalGroup());
        nomeTxt.addKeyListener(ka);
        telefoneTxt.addKeyListener(ka);
        cpfTxt.addKeyListener(ka);
        btn.addActionListener(this);
    }

    //<editor-fold defaultstate="collapsed" desc="setHorizontalGroup">
    private GroupLayout.Group setHorizontalGroup() {
        return createParallelGroup()
                .addGroup(createSequentialGroup()
                        .addGroup(
                                createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(nome)
                                        .addComponent(cpf)
                                        .addComponent(telefone)
                        )
                        .addGroup(createParallelGroup()
                                .addComponent(nomeTxt)
                                .addComponent(cpfTxt)
                                .addComponent(telefoneTxt)
                        )
                )
                .addComponent(btn, DEFAULT_SIZE, DEFAULT_SIZE, Integer.MAX_VALUE);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setVerticalGroup">
    private GroupLayout.Group setVerticalGroup() {
        return createSequentialGroup()
                .addGroup(createParallelGroup(GroupLayout.Alignment.CENTER, false)
                        .addComponent(nome)
                        .addComponent(nomeTxt)
                )
                .addGroup(createParallelGroup(GroupLayout.Alignment.CENTER, false)
                        .addComponent(cpf)
                        .addComponent(cpfTxt)
                )
                .addGroup(createParallelGroup(GroupLayout.Alignment.CENTER, false)
                        .addComponent(telefone)
                        .addComponent(telefoneTxt)
                )
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(btn)
                );
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="actionPerformed">
    @Override
    public void actionPerformed(ActionEvent e) {
        cadastrar();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="KeyAdapter">
    KeyAdapter ka = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (e.getComponent() == telefoneTxt) {
                if (e.getKeyCode() != 8) {
                    switch (telefoneTxt.getText().length()) {
                        case 1 ->
                            telefoneTxt.setText("(" + telefoneTxt.getText());
                        case 3 ->
                            telefoneTxt.setText(telefoneTxt.getText() + ") ");
                        case 10 ->
                            telefoneTxt.setText(telefoneTxt.getText() + "-");
                    }
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                cadastrar();
            }
        }

    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cadastrar">
    private void cadastrar() {
        /*String sql = "insert into clientes (";
        if (!nomeTxt.getText().isBlank()) {
        sql += "nome";
        } else {
        JOptionPane.showMessageDialog(
        jfnc,
        "Não é possível cadastrar um usuário SEM nome",
        "Ops, você deve preencher no mínimo o nome do cliente!",
        JOptionPane.WARNING_MESSAGE);
        return;
        }
        if (!cpfTxt.getText().isBlank()) {
        if (!nomeTxt.getText().isBlank()) {
        sql += ", cpf";
        } else {
        JOptionPane.showMessageDialog(
        jfnc,
        "Não é possível cadastrar um usuário somente com CPF!",
        "Ops, você deve preencher o nome do cliente",
        JOptionPane.WARNING_MESSAGE);
        }
        }
        if (telefoneTxt.getText().isBlank() || telefoneTxt.getText().length() != 15) {
        JOptionPane.showMessageDialog(
        jfnc,
        "Você deve informar o telefone no seguinte formato (xx) 12345-1234",
        "Telefone incorreto",
        JOptionPane.WARNING_MESSAGE);
        return;
        }
        if (!nomeTxt.getText().isBlank()) {
        sql += ", telefone";
        } else {
        JOptionPane.showMessageDialog(
        jfnc,
        "Não é possível cadastrar um usuário somente com telefone!",
        "Ops, você deve preencher o nome do cliente",
        JOptionPane.WARNING_MESSAGE);
        }*/
        if (!nomeTxt.getText().isBlank() && !cpfTxt.getText().isBlank() && !telefoneTxt.getText().isBlank() && telefoneTxt.getText().length() == 15) {
            try {
                if (mySQL == null) {
                    mySQL = new MySQL();
                }
                String sql = "insert into clientes (nome, telefone, cpf) values (?, ?, ?)";
                mySQL.ps = mySQL.conn.prepareStatement(sql);
                mySQL.ps.setString(1, nomeTxt.getText());
                mySQL.ps.setString(2, telefoneTxt.getText());
                mySQL.ps.setString(3, cpfTxt.getText());
                mySQL.ps.execute();
                mySQL.ps.close();
                mySQL.conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(JIFClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (!nomeTxt.getText().isBlank()) {
            try {
                if (mySQL == null) {
                    mySQL = new MySQL();
                }
                mySQL.ps = mySQL.conn.prepareStatement("insert into clientes(nome) value (?)");
                mySQL.ps.setString(1, nomeTxt.getText());
                mySQL.ps.execute();
                mySQL.ps.close();
                mySQL.conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(JIFClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (jfnc.getJifcgl() != null) {
            jfnc.getJifcgl().getJifc().getMyJMB().getJfm().setVisible(true);
            jfnc.getJifcgl().atualiza();
            jfnc.dispose();
        } else if (jfnc.getMyJMB() != null) {
            jfnc.getMyJMB().setJFNovoClienteFalse();
            jfnc.getMyJMB().setVisible(true);
            jfnc.dispose();
        } else {
            jfnc.dispose();
        }
    }
    //</editor-fold>

}
