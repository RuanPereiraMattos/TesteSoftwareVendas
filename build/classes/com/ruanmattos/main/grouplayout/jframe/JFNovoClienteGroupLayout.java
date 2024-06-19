package com.ruanmattos.main.grouplayout.jframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.database.MySQL;
import com.ruanmattos.main.jframe.JFNovoCliente;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
        String sql = "insert into clientes ";
        if (!nomeTxt.getText().isBlank() && !cpfTxt.getText().isBlank() && !telefoneTxt.getText().isBlank()) {//Todos os campos contém algo
            if (cpfTxt.getText().length() != 11 && telefoneTxt.getText().length() != 15) {//CPF e Telefone estão com os campos formatados incorretamente
                if (cpfTxt.getText().length() != 11) {//CPF está formatado incorretamente
                    JOptionPane.showMessageDialog(
                            jfnc,
                            "Você deve preencher corretamente o campo CPF:\nExemplo: 12345678912\n"
                            + "Você deve preencher corretamente o campo telefone:\nExemplo: (xx) xxxxx-xxxx",
                            "Campo CPF e Telefone preenchidos de maneira errada",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } else {//CPF ou Telefone estão com os campos formatados incorretamente
                if (cpfTxt.getText().length() != 11) {//Somente CPF está formatado incorretamente
                    JOptionPane.showMessageDialog(
                            jfnc,
                            "Você deve preencher corretamente o campo CPF:\nExemplo: xxxxxxxxxxx",
                            "Campo CPF preenchido de maneira errada",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (telefoneTxt.getText().length() != 15) {//Somente Telefone está formatado incorretamente
                    JOptionPane.showMessageDialog(
                            jfnc,
                            "Você deve preencher corretamente o campo telefone:\nExemplo: (xx) xxxxx-xxxx",
                            "Campo Telefone preenchido de maneira errada",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            if (mySQL == null) {
                mySQL = new MySQL();
            }
            sql += " (nome, cpf, telefone) values (\"" + nomeTxt.getText() + "\", \"" + cpfTxt.getText() + "\", \"" + telefoneTxt.getText() + "\")";
            if (mySQL.insert(sql, "Não foi possível gravar o usuário novo no banco de dados, tente novamente!", jfnc) == -1) {
                return;
            }
            JOptionPane.showMessageDialog(jfnc, "Cliente:\nNome: " + nomeTxt.getText() + "\nCPF: " + cpfTxt.getText() + "\nTelefone: " + telefoneTxt.getText() + "\nCadastrado(a) com sucesso");
        } else if (!nomeTxt.getText().isBlank() && !cpfTxt.getText().isBlank()) {
            if (cpfTxt.getText().length() != 11) {
                JOptionPane.showMessageDialog(
                            jfnc,
                            "Você deve preencher corretamente o campo CPF:\nExemplo: xxxxxxxxxxx",
                            "Campo CPF preenchido de maneira errada",
                            JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (mySQL == null) {
                mySQL = new MySQL();
            }
            sql += " (nome, cpf) values (\"" + nomeTxt.getText() + "\", \"" + cpfTxt.getText() + "\")";
            if (mySQL.insert(sql, "Não foi possível gravar o usuário novo no banco de dados, tente novamente!", jfnc) == 1) {
                JOptionPane.showMessageDialog(jfnc, "Cliente:\nNome: " + nomeTxt.getText() + "\nCPF: " + cpfTxt.getText() + "\nCadastrado(a) com sucesso");
            }
        } else if (!nomeTxt.getText().isBlank() && !telefoneTxt.getText().isBlank()) {
            if (telefoneTxt.getText().length() != 15) {
                JOptionPane.showMessageDialog(
                            jfnc,
                            "Você deve preencher corretamente o campo telefone:\nExemplo: (xx) xxxxx-xxxx",
                            "Campo Telefone preenchido de maneira errada",
                            JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (mySQL == null) {
                mySQL = new MySQL();
            }
            sql += " (nome, telefone) values (\"" + nomeTxt.getText() + "\", \"" + telefoneTxt.getText() + "\")";
            if (mySQL.insert(sql, "Não foi possível gravar o usuário novo no banco de dados, tente novamente!", jfnc) == 1) {
                JOptionPane.showMessageDialog(jfnc, "Cliente:\nNome: " + nomeTxt.getText() + "\nTelefone: " + telefoneTxt.getText() + "\nCadastrado(a) com sucesso");
            }
        } else if (!nomeTxt.getText().isBlank()) {
            if (mySQL == null) {
                mySQL = new MySQL();
            }
            sql += " (nome) value (\"" + nomeTxt.getText() + "\")";
            if (mySQL.insert(sql, "Não foi possível gravar o usuário novo no banco de dados, tente novamente!", jfnc) == 1) {
                JOptionPane.showMessageDialog(jfnc, "Cliente:\nNome: " + nomeTxt.getText() + "\nCadastrado(a) com sucesso");
            }
        } else {
            JOptionPane.showMessageDialog(jfnc, "Você precisa preencher no mínimo o nome do cliente para poder proceguir com o cadastro!");
            return;
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
