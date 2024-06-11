package com.ruanmattos.main.grouplayout.jframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.database.MySQL;
import com.ruanmattos.main.jframe.JFNovoProduto;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
//</editor-fold>

/**
 *
 * @author Ruan Pereira Mattos
 */
public class JFNovoProdutoGroupLayout extends GroupLayout implements ActionListener {

    private final JFNovoProduto jfnp;

    private MySQL mySQL = null;

    private final JLabel nome = new JLabel("Nome:");
    private final JLabel descricao = new JLabel("Descrição:");
    private final JLabel valor = new JLabel("Valor Unitário:");
    private final JTextField nomeTxt = new JTextField();
    private final JTextField descricaoTxt = new JTextField();
    private final NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
    //<editor-fold defaultstate="collapsed" desc="NumberFormatter">
    private final NumberFormatter numberFormatter = new NumberFormatter(numberFormat) {
        @Override
        public void setValueClass(Class<?> valueClass) {
            super.setValueClass(Float.class); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void setMinimum(Comparable<?> minimum) {
            super.setMinimum(0); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void setMaximum(Comparable<?> max) {
            super.setMaximum(Float.MAX_VALUE); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void setAllowsInvalid(boolean allowsInvalid) {
            super.setAllowsInvalid(false); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public void setCommitsOnValidEdit(boolean commit) {
            super.setCommitsOnValidEdit(true); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

    };
    //</editor-fold>
    private final JFormattedTextField valorTxt = new JFormattedTextField(numberFormatter);
    private final JButton btn = new JButton("Cadastrar");

    private final boolean canCadastrate;

    //Inicia por esse
    public JFNovoProdutoGroupLayout(Container host, JFNovoProduto jfnp) {
        super(host);
        this.jfnp = jfnp;
        canCadastrate = valorTxt.getText().isBlank();
        setAutoCreateGaps(true);
        setAutoCreateContainerGaps(true);
        nomeTxt.addKeyListener(ka);
        descricaoTxt.addKeyListener(ka);
        valorTxt.addKeyListener(ka);
        btn.addActionListener(this);
        setHorizontalGroup(setHorizontalGroup());
        setVerticalGroup(setVerticalGroup());
    }

    //<editor-fold defaultstate="collapsed" desc="setHorizontalGroup">
    private GroupLayout.Group setHorizontalGroup() {
        return createParallelGroup()
                .addGroup(createSequentialGroup()
                        .addGroup(
                                createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(nome)
                                        .addComponent(descricao)
                                        .addComponent(valor)
                        )
                        .addGroup(createParallelGroup()
                                .addComponent(nomeTxt)
                                .addComponent(descricaoTxt)
                                .addComponent(valorTxt)
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
                        .addComponent(descricao)
                        .addComponent(descricaoTxt)
                )
                .addGroup(createParallelGroup(GroupLayout.Alignment.CENTER, false)
                        .addComponent(valor)
                        .addComponent(valorTxt)
                )
                .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                .addComponent(btn)
                );
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="KeyAdapter">
    private final KeyAdapter ka = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                cadastrar();
            }
        }

    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="actionPerformed">
    @Override
    public void actionPerformed(ActionEvent e) {
        cadastrar();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cadastrar">
    private void cadastrar() {
        /*if (!canCadastrate) {
        JOptionPane.showMessageDialog(jfnp, "Ocorreu algum erro, feche o software e abra novamente!", "Ops!", JOptionPane.ERROR_MESSAGE);
        if (jfnp.getMyJMB() != null) {
        jfnp.getMyJMB().setJFNovoProdutoFalse();
        jfnp.getMyJMB().getJfm().setVisible(true);
        } else if (jfnp.getJifpgl() != null) {
        jfnp.getJifpgl().getJifp().getMyJMB().setJFNovoProdutoFalse();
        jfnp.getJifpgl().getJifp().getMyJMB().getJfm().setVisible(true);
        jfnp.getJifpgl().atualiza();
        }
        jfnp.dispose();
        }*/
        if (!nomeTxt.getText().isBlank() && !descricaoTxt.getText().isBlank() && !valorTxt.getText().isBlank()) {
            try {
                if (mySQL == null) {
                    mySQL = new MySQL();
                }
                mySQL.ps = mySQL.conn.prepareStatement("insert into produtos(nome, descricao, valor) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                mySQL.ps.setString(1, nomeTxt.getText());
                mySQL.ps.setString(2, descricaoTxt.getText());
                mySQL.ps.setFloat(3, Float.parseFloat(valorTxt.getValue().toString()));
                int executeUpdate = mySQL.ps.executeUpdate();
                if (executeUpdate == 1) {
                    mySQL.rs = mySQL.ps.getGeneratedKeys();
                    if (mySQL.rs.next()) {
                        int id_produto = mySQL.rs.getInt(1);
                        mySQL.ps = mySQL.conn.prepareStatement("insert into estoque(id_produto, quantidade) values(?, ?)");
                        mySQL.ps.setInt(1, id_produto);
                        mySQL.ps.setInt(2, 0);
                        executeUpdate = mySQL.ps.executeUpdate();
                        if (executeUpdate == 1) {
                            mySQL.ps.close();
                            mySQL.rs.close();
                            mySQL.conn.close();
                            if (jfnp.getMyJMB() != null) {
                                jfnp.getMyJMB().setJFNovoProdutoFalse();
                                jfnp.getMyJMB().getJfm().setVisible(true);
                            } else if (jfnp.getJifpgl() != null) {
                                jfnp.getJifpgl().getJifp().getMyJMB().setJFNovoProdutoFalse();
                                jfnp.getJifpgl().getJifp().getMyJMB().getJfm().setVisible(true);
                                jfnp.getJifpgl().atualiza();
                            }
                            jfnp.dispose();
                        } else {
                            JOptionPane.showMessageDialog(jfnp, "Por favor, salve essa tela ou o nome do produto e entre em contato com o setor de TI para consertarem o problema", "Erro ao criar o item " + nomeTxt.getText() + " no estoque!", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(JFNovoProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String msg = "Você deve terminar de preencher os seguintes campos: ";
            if (nomeTxt.getText().isBlank()) {
                msg += "Nome do Produto";
            }
            if (descricaoTxt.getText().isBlank()) {
                if (nomeTxt.getText().isBlank()) {
                    msg += ", Descrição do Produto";
                } else {
                    msg += "Descrição do Produto";
                }
            }
            if (valorTxt.getText().isBlank()) {
                if (nomeTxt.getText().isBlank() || descricaoTxt.getText().isBlank()) {
                    msg += ", Valor do Produto";
                } else {
                    msg += "Valor do Produto";
                }
            }
            JOptionPane.showMessageDialog(jfnp, msg, "Estão faltando dados para terminar o cadastro do produto", JOptionPane.WARNING_MESSAGE);
        }
    }
    //</editor-fold>

}
