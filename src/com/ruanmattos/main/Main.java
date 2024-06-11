package com.ruanmattos.main;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.ruanmattos.main.jframe.JFMain;
//</editor-fold>

/**
 * Classe principal do software
 *
 * @author Ruan Pereira Mattos
 */
public class Main {

    /**
     * Função construtora da class que inicia a tela inicial do software
     */
    public Main() {
        JFMain main = new JFMain();
    }

    /**
     * Método que inicia o software
     *
     * @param args
     */
    public static void main(String[] args) {
        new Main();
    }

}
