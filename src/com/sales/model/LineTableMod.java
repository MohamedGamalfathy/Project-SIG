
package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class LineTableMod extends AbstractTableModel {
        private ArrayList<InvoiceLine> lines;
        private String [] cols = {"No.","Item Name","Item Price","Count","Item Total"};

    public LineTableMod(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }
        



    @Override
    public int getRowCount() {
    return   lines.size();
        }

    @Override
    public int getColumnCount() {
    return cols.length;

    }
    @Override
    public String getColumnName(int column){
      return cols[column];  
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        InvoiceLine line = lines.get(rowIndex); 
       switch (columnIndex) {
           
           case 0: return line.getInvoice().getInvoiceNum();
           case 1 : return line.getItemName();
           case 2 : return line.getItemPrice();
           case 3 : return line.getCount();
           case 4 : return line.getLineTotal();
           default : return "";
    }
       
    
    }


    
        

    
}
    
