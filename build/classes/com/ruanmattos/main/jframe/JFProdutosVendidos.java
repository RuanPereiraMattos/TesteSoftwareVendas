package com.ruanmattos.main.jframe;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.data.ClienteData;
import com.ruanmattos.main.data.VendasData;
import com.ruanmattos.main.grouplayout.JFProdutosVendidosGroupLayout;
import com.ruanmattos.main.grouplayout.jinternalframe.JIFVendasGroupLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
//</editor-fold>

/**
 *
 * @author Ruan Pereira Mattos
 */
public class JFProdutosVendidos extends JFrame {

    public JFProdutosVendidos(JIFVendasGroupLayout jifvgl, VendasData vendasData, ClienteData clienteData) throws HeadlessException {
        super(clienteData.getNome()
                + " Hora: " + vendasData.getHora()
                + " Minuto: " + vendasData.getMinuto()
                + " Dia: " + vendasData.getDia()
                + " Mes: " + vendasData.getMes()
                + " Ano: " + vendasData.getAno());
        JFProdutosVendidosGroupLayout jfpvgl = new JFProdutosVendidosGroupLayout(getContentPane(), vendasData.getId());
        setLayout(jfpvgl);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (jifvgl != null) {
                    if (jifvgl.getQtdJFProdutosVendidosOpened() == 1) {
                        jifvgl.setIsJFProdutosVendidosClose();
                    }
                    jifvgl.setQtdJFProdutosVendidosOpened();
                }
                dispose();
                super.windowClosing(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }

        });
    }

}
