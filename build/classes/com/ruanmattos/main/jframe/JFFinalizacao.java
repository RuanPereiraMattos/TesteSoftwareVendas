package com.ruanmattos.main.jframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.data.ClienteData;
import com.ruanmattos.main.data.PagamentosData;
import com.ruanmattos.main.data.VendaProdutoData;
import com.ruanmattos.main.jinternalframe.JIFVendas;
import com.ruanmattos.main.jmenubar.MyJMB;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
//</editor-fold>

/**
 *
 * @author Ruan Pereira Mattos
 */
public class JFFinalizacao extends JFrame {

    private float totalCompra = 0;
    private final JFNovaVenda jfnv;
    private final JIFVendas jifv;

    public JFFinalizacao(JFNovaVenda jfnv, ClienteData cd, List<VendaProdutoData> listVendaProdutoDatas, List<PagamentosData> listPagamentosData, JIFVendas jifv, MyJMB myJMB) throws HeadlessException {
        this.jfnv = jfnv;
        this.jifv = jifv;
        JPanel jp = new JPanel(new java.awt.GridBagLayout());
        JScrollPane jsp = new JScrollPane(jp);
        jp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                imprimir(cd, listVendaProdutoDatas, listPagamentosData);
            }

        });
        add(jsp);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        Insets insets = new Insets(10, 10, 0, 0);
        int x = 0;
        int y = 0;
        JLabel nome = new JLabel("Nome do Cliente: " + cd.getNome());
        gbc.insets = insets;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        jp.add(nome, gbc);
        y++;
        //Modificar para quando o cliente não tiver telefone no sistema
        JLabel telefone = new JLabel("Telefone do Cliente: " + cd.getTelefone());
        System.out.println("Telefone " + cd.getTelefone());
        gbc.insets = insets;
        gbc.gridy = y;
        if (cd.getTelefone() != null) {
            jp.add(telefone, gbc);
            y++;
        }
        insets.left = 20;
        for (int i = 0; i < listVendaProdutoDatas.size(); i++) {
            VendaProdutoData vpd = listVendaProdutoDatas.get(i);
            JLabel id = new JLabel("Id Produto: " + vpd.getId());
            insets.top = 20;
            gbc.insets = insets;
            gbc.gridy = y;
            jp.add(id, gbc);
            insets.top = 10;
            y++;
            JLabel nome_produto = new JLabel("Nome do Produto: " + vpd.getNome());
            gbc.insets = insets;
            gbc.gridy = y;
            jp.add(nome_produto, gbc);
            y++;
            JLabel qtd = new JLabel("Quantidade do Produto: " + vpd.getQtd());
            gbc.insets = insets;
            gbc.gridy = y;
            jp.add(qtd, gbc);
            y++;
            JLabel valor_und = new JLabel("Valor Unitário do Produto: R$ " + vpd.getValor_und());
            gbc.insets = insets;
            gbc.gridy = y;
            jp.add(valor_und, gbc);
            y++;
            JLabel valor_total = new JLabel("Valor Total do Produto: R$ " + vpd.getValor_total());
            gbc.insets = insets;
            gbc.gridy = y;
            jp.add(valor_total, gbc);
            y++;
            totalCompra += vpd.getValor_total();
        }
        JLabel valor_total_compra = new JLabel("Valor Total da Compra: R$ " + totalCompra);
        insets.left = 10;
        insets.top = 20;
        gbc.insets = insets;
        gbc.gridy = y;
        jp.add(valor_total_compra, gbc);
        y++;
        insets.left = 20;
        for (int i = 0; i < listPagamentosData.size(); i++) {
            PagamentosData pd = listPagamentosData.get(i);
            String strTipo = "";
            String strVezes = "";
            switch (pd.getTipo()) {
                case 0 -> {
                    strTipo = "Dinheiro R$ " + pd.getValor();
                    if (0 < pd.getTroco()) {
                        strTipo += " Troco R$ " + pd.getTroco();
                    }
                }
                case 1 -> {
                    strTipo = "Cartão de Débito R$ " + pd.getValor();
                }
                case 2 -> {
                    strVezes = (pd.getVezes() == 1) ? "1 vez" : pd.getVezes() + " vezes";
                    strTipo = "Cartão de Crédito " + strVezes + " R$ " + pd.getValor();
                }
            }
            JLabel tipo = new JLabel(strTipo);
            insets.bottom = ((listPagamentosData.size() - 1) == i) ? 20 : 0;
            gbc.insets = insets;
            gbc.gridy = y;
            jp.add(tipo, gbc);
            y++;
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("Menu");
        JMenuItem jmi = new JMenuItem("Imprimir");
        jmi.addActionListener((e) -> {
            imprimir(cd, listVendaProdutoDatas, listPagamentosData);
        });
        jm.add(jmi);
        jmb.add(jm);
        setJMenuBar(jmb);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        if (getHeight() < (d.height / 3 * 2)) {
            setSize(getWidth() + 20, getHeight());
        } else {
            setSize(getWidth() + 20, (d.height / 3) * 2);
        }
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }

        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (jfnv != null) {
                    if (jfnv.getJifv() != null) {
                        jfnv.getJifv().getMyJMB().setJFNovaVendaFalse();
                        jfnv.getJifv().getMyJMB().getJfm().setVisible(true);
                    }
                    if (jfnv.getMyJMB() != null) {
                        jfnv.getMyJMB().setJFNovaVendaFalse();
                        jfnv.getMyJMB().getJfm().setVisible(true);
                    }
                    jfnv.dispose();
                } else {
                    if (jifv != null) {
                        jifv.getMyJMB().getJfm().setVisible(true);
                    }
                }
                dispose();
                super.windowClosing(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }
        });
    }

    //<editor-fold defaultstate="collapsed" desc="Imprimir">
    private void imprimir(ClienteData cd, List<VendaProdutoData> listVendaProdutoDatas, List<PagamentosData> listPagamentosDatas) {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable((graphics, pf, pageIndex) -> {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }
            int xx = (int) pf.getImageableX() + 10;
            int yy = (int) pf.getImageableY() + 10;
            graphics.translate(xx, yy);
            graphics.drawString("Nome do Cliente: " + cd.getNome(), xx, yy);
            yy += 20;
            graphics.drawString("Telefone do Cliente: " + cd.getTelefone(), xx, yy);
            yy += 30;
            xx += 10;
            for (VendaProdutoData vpd : listVendaProdutoDatas) {
                graphics.drawString("Nome do Produto: " + vpd.getNome(), xx, yy);
                yy += 20;
                graphics.drawString("Quantidade do Produto: " + vpd.getQtd(), xx, yy);
                yy += 20;
                graphics.drawString("Valor Unitário do Produto: R$ " + vpd.getValor_und(), xx, yy);
                yy += 20;
                graphics.drawString("Valor Total do Produto: R$ " + vpd.getValor_total(), xx, yy);
                yy += 30;
            }
            xx -= 10;
            graphics.drawString("Valor Total da Compra: R$ " + totalCompra, xx, yy);
            xx += 10;
            yy += 30;
            for (PagamentosData lpd : listPagamentosDatas) {
                switch (lpd.getTipo()) {
                    case 0 -> {
                        String str = "Dinheiro R$ " + lpd.getValor();
                        if (0 < lpd.getTroco()) {
                            str += " Troco R$ " + lpd.getTroco();
                        }
                        graphics.drawString(str, xx, yy);
                        yy += 20;
                    }
                    case 1 -> {
                        graphics.drawString("Cartão de Débito R$ " + lpd.getValor(), xx, yy);
                        yy += 20;
                    }
                    case 2 -> {
                        graphics.drawString("Cartão de Crédito " + ((lpd.getVezes() == 1) ? "1 vez " : lpd.getVezes() + " vezes ") + "R$ " + lpd.getValor(), xx, yy);
                        yy += 20;
                    }
                }
            }
            graphics.dispose();

            return Printable.PAGE_EXISTS;
        });
        if (printerJob.printDialog()) {
            try {
                printerJob.print();
                if (jfnv != null) {
                    if (jfnv.getJifv() != null) {
                        jfnv.getJifv().getMyJMB().getJfm().setVisible(true);
                    }
                    if (jfnv.getMyJMB() != null) {
                        jfnv.getMyJMB().getJfm().setVisible(true);
                    }
                    jfnv.dispose();
                } else {
                    if (jifv != null) {
                        jifv.getMyJMB().getJfm().setVisible(true);
                    }
                }
                dispose();
            } catch (PrinterException ex) {
                Logger.getLogger(JFFinalizacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //</editor-fold>

}
