package com.ruanmattos.main.jinternalframe;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Ruan Pereira Mattos
 */
public class JIFAjuda extends JInternalFrame {
    
    private static final String url = System.getProperty("user.dir") + "\\img";
    private static final List<String> menuImages = new ArrayList<>();
    private static final List<String> clientesImages = new ArrayList<>();
    private static final List<String> produtosImages = new ArrayList<>();
    private static final List<String> vendasImages = new ArrayList<>();
    private static final JTabbedPane JTP = new JTabbedPane();
    private static final JPanel painelMenu = new JPanel();
    private static final JPanel painelClientes = new JPanel();
    private static final JPanel painelProdutos = new JPanel();
    private static final JPanel painelEstoque = new JPanel();
    private static final JPanel painelVendas = new JPanel();
    
    public JIFAjuda() {
        super("Menu Ajuda", true, true, true, true);
        add(JTP);
        JTP.add("Menu", painelMenu);
        JTP.add("Clientes", painelClientes);
        JTP.add("Produtos", painelProdutos);
        JTP.add("Estoque", painelEstoque);
        JTP.add("Vendas", painelVendas);
        pack();
        setVisible(true);
        setPainelMenu();
    }
    
    private void setPainelMenu() {
        File file = new File(url);
        if (file.exists()) {
            File menu = new File(file, "menu");
            if (menu.exists()) {
                File[] listFiles = menu.listFiles();
                for (File menuImgs : listFiles) {
                    menuImages.add(menuImgs.getAbsolutePath());
                    System.out.println(menuImgs);
                }
            }
            menu = new File(file, "clientes");
            if (menu.exists()) {
                File[] listFiles = menu.listFiles();
                for (File clientesImgs : listFiles) {
                    clientesImages.add(clientesImgs.getAbsolutePath());
                }
            }
        } else {
            file.mkdir();
        }
        painelMenu.setLayout(new BorderLayout());
        JLabel img = new JLabel();
        File menuImagem = new File(menuImages.get(0));
        //BufferedImage bi = ImageIO.read(menuImagem);
        //img.setIcon(bi.getScaledInstance(img.getWidth(), img.get, WIDTH));
        JButton btnPrevious = new JButton("Anterior");
        JButton btnNext = new JButton("Próxima");
        painelMenu.add(btnPrevious, BorderLayout.WEST);
        painelMenu.add(btnNext, BorderLayout.EAST);
        /*GroupLayout gl = new GroupLayout(painelMenu.getParent());
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);
        gl.setHorizontalGroup(
        gl.createSequentialGroup());
        gl.setVerticalGroup(
        gl.createSequentialGroup());*/
    }
    
    static class MyJFrame extends JFrame {
        
        public MyJFrame() {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            JDesktopPane jdp = new JDesktopPane();
            jdp.add(new JIFAjuda());
            add(jdp);
            //pack();
            setSize(800, 600);
            setVisible(true);
        }
        
    }
    
    public static void main(String[] args) {
        new MyJFrame();
    }
}
