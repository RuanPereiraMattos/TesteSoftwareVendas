package com.ruanmattos.main.database;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
//</editor-fold>

/**
 *
 * @author Ruan Pereira Mattos
 */
public class MySQL {

    public Connection conn = null;
    public Statement st = null;
    public ResultSet rs = null;
    public PreparedStatement ps = null;

    public MySQL() {
        try {
            File file = new File(System.getProperty("user.dir") + "\\database_url.txt");
            if (file.exists()) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String url = br.readLine();
                String dbName = br.readLine();
                String user = br.readLine();
                String pass = br.readLine();
                conn = DriverManager.getConnection(url + "/" + dbName, user, pass);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Não foi possível achar o arquivo contento as configurações de onde está o servidor", "Erro ao achar o servidor", JOptionPane.WARNING_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Não foi possível ler o arquivo contento as informações da localização do servidor", "Erro ao achar o servidor", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Não foi conectar no servidor", "Erro ao conectar ao servidor", JOptionPane.WARNING_MESSAGE);
        }
    }

}
