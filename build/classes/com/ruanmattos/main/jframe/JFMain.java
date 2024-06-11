package com.ruanmattos.main.jframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.jmenubar.MyJMB;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//</editor-fold>

/**
 * Class do JFrame principal
 *
 * @author Ruan Pereira Mattos
 */
public class JFMain extends JFrame {

    protected final JDesktopPane jdp = new JDesktopPane();

    public JFMain() throws HeadlessException {
        super("Ruan Mattos");
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(d.width / 2, d.height / 2);
        setLocationRelativeTo(null);
        setJMenuBar(new MyJMB(this));
        add(jdp);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int showOptionDialog = JOptionPane.showOptionDialog(JFMain.this, "Você deseja realmente sair?", "Fechando...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Sim", "Não"}, 1);
                if (showOptionDialog == 0) {
                    System.exit(0);
                }
            }

        });
    }

    public JDesktopPane getJdp() {
        return jdp;
    }

}
