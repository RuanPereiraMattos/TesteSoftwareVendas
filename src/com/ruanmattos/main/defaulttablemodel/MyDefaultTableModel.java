package com.ruanmattos.main.defaulttablemodel;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ruan Pereira Mattos
 */
public class MyDefaultTableModel extends DefaultTableModel {

    public MyDefaultTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public void clear() {
        int rowCount = getRowCount();
        for (int i = 0; i < rowCount; i++) {
            removeRow(0);
        }
    }

}
