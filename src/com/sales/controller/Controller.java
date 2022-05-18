
package com.sales.controller;

import com.sales.model.InvTableMod;
import com.sales.model.Invoice;
import com.sales.model.InvoiceLine;
import com.sales.model.LineTableMod;
import com.sales.view.InvoiceDialog;
import com.sales.view.InvoiceLineDialog;
import com.sales.view.SIGframe;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Controller implements ActionListener, ListSelectionListener {
    private SIGframe fr;
    private InvoiceDialog invoiceDialog;
    private InvoiceLineDialog invLineDialog;

    public Controller (SIGframe frame){
        this.fr=frame;
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
        String actionCommand= e.getActionCommand();
        System.out.println("Action "+actionCommand);
        switch (actionCommand){
            case "Load File" :
                loadFile();
                break;
            case "Save File" :
                saveFile();
                break;
            case "Create New Invoice" :
                createNewInvoice();
                break;
            case "Delete Invoice" :
                deleteInvoice();
                break;
            case "Create New Item" :
                createNewItem();
                break;
            case "Delete Item" :
                deleteItem();
                break;
            case "createInvoiceCancel" : 
                createInvoiceCancel();
                break;
            case "createInvoiceOK" :
                createInvoiceOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
            case "createLineOK" :
                createLineOK();
                break;

                
        }
                

    }
     @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndx = fr.getInvoiceTable().getSelectedRow();
        if (selectedIndx != -1){
        System.out.println("You have selected row:  "+ selectedIndx);
        Invoice currentInv =fr.getInvoices().get(selectedIndx);
        fr.getInvoiceNumberLabel().setText(""+currentInv.getInvoiceNum());
        fr.getInvoiceDateLabel().setText(currentInv.getInvoiceDate());
        fr.getCutomerNameLabel().setText(currentInv.getCustomerName());
        fr.getTotalLabel().setText(""+currentInv.getInvoiceTotal());
        LineTableMod lineTableMod =new LineTableMod (currentInv.getLines());
        fr.getInvoicelineTable().setModel(lineTableMod);
        lineTableMod.fireTableDataChanged();
        }
    }

    private void loadFile() {
        JFileChooser filechooser =new JFileChooser ();
       int result= filechooser.showOpenDialog(fr);
      try{ if (result==JFileChooser.APPROVE_OPTION){
        File invoiceHeader =   filechooser.getSelectedFile();
        Path headerPath = Paths.get(invoiceHeader.getAbsolutePath());
        List<String> headerLines = Files.readAllLines(headerPath);
        System.out.println("Invoice have beean read");
        
        ArrayList<Invoice> invoiceArr =new ArrayList<>();
        for (String headerLine: headerLines ){
         String [] headerParts =  headerLine.split(",");
         int invoiceNum = Integer.parseInt(headerParts[0]); 
         String invoiceDate = headerParts[1];
         String customerName = headerParts[2];
         Invoice invoice = new Invoice (invoiceNum, invoiceDate, customerName);
         invoiceArr.add(invoice);
        }
         System.out.println("checkout");
         result= filechooser.showOpenDialog(fr);
         if (result==JFileChooser.APPROVE_OPTION){
        File invoiceLine =   filechooser.getSelectedFile();
        Path linePath = Paths.get(invoiceLine.getAbsolutePath());
        List<String> lineLines = Files.readAllLines(linePath);
        System.out.println("Invoice have beean read");
        ArrayList<InvoiceLine> lineArr =new ArrayList<>();
        for (String lineLine: lineLines ){
         String [] lineParts =  lineLine.split(",");
         int invoiceNum = Integer.parseInt(lineParts[0]); 
         String itemName = lineParts[1];
         double itemPrice = Double.parseDouble( lineParts[2]);
         int count = Integer.parseInt(lineParts[3]);
         Invoice inv = null ;
         for (Invoice invoice :invoiceArr){
             if (invoice.getInvoiceNum() == invoiceNum){
                 inv = invoice;
                 break;
                 
             }
             
         }
         InvoiceLine line =new InvoiceLine(itemName,itemPrice,count,inv);
         inv.getLines().add(line);
        }
        }
         fr.setInvoices (invoiceArr);
         InvTableMod invTableMod =new InvTableMod (invoiceArr);
         fr.setInvTableMod(invTableMod);
         fr.getInvoiceTable().setModel(invTableMod);
         fr.getInvTableMod().fireTableDataChanged();
       }
      }catch(IOException ec){
          ec.printStackTrace();
      }
        
    }

    private void saveFile() {
       ArrayList<Invoice> invoices = fr.getInvoices();
       String invoiceHeaders="";
       String invoiceLines = "";
       for(Invoice invoice : invoices){
           String invoiceCSV = invoice.getCSV();
           invoiceHeaders += invoiceCSV;
           invoiceHeaders += "\n";
           for (InvoiceLine line : invoice.getLines()){
              String invoiceLineCSV = line.getCSV();
              invoiceLines += invoiceLineCSV ;
              invoiceLines += "\n";
            
           }
       }
       try{
       JFileChooser fileChooser = new JFileChooser();
      int result =  fileChooser.showSaveDialog(fr);
      if ( result == JFileChooser.APPROVE_OPTION){
          File invoiceFile =   fileChooser.getSelectedFile();
          FileWriter headerFileWriter = new FileWriter(invoiceFile);
          headerFileWriter.write(invoiceHeaders);
          headerFileWriter.flush();
          headerFileWriter.close();
        
          result = fileChooser.showSaveDialog(fr);
             if ( result == JFileChooser.APPROVE_OPTION){
               File invoiceLineFile = fileChooser.getSelectedFile();
               FileWriter lineFileWriter = new FileWriter(invoiceLineFile);
               lineFileWriter.write(invoiceLines);
               lineFileWriter.flush();
               lineFileWriter.close();
               
             }
   
      }
       }catch(Exception ec){
           
           ec.printStackTrace();
       }
    }

    private void createNewInvoice() {
        invoiceDialog = new InvoiceDialog(fr);
        invoiceDialog.setVisible(true);
        
    }

    private void deleteInvoice() {
     int selectedR= fr.getInvoiceTable().getSelectedRow();
     if(selectedR != -1){
         fr.getInvoices().remove(selectedR);
         fr.getInvTableMod().fireTableDataChanged();
     }
    
    }

    private void createNewItem() {
        invLineDialog =new InvoiceLineDialog(fr);
        invLineDialog.setVisible(true);
        
    }

    private void deleteItem() {
        int selectedInvoice = fr.getInvoiceTable().getSelectedRow();
        int selectedR= fr.getInvoicelineTable().getSelectedRow();
        
     if(selectedInvoice != -1 && selectedR != -1){
         Invoice invoice = fr.getInvoices().get(selectedInvoice);
         invoice.getLines().remove(selectedR);
         LineTableMod lineTableMod =new LineTableMod(invoice.getLines());
         fr.getInvoicelineTable().setModel(lineTableMod);
         lineTableMod.fireTableDataChanged();
         fr.getInvTableMod().fireTableDataChanged();
     }
        
    }

    private void createInvoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog =null;
    }

    private void createInvoiceOK() {
        String invoiceDate = invoiceDialog.getInvDateField().getText();
        String customerName = invoiceDialog.getCustNameField().getText();
        int invoiceNum = fr.getFollowingInvoiceNum();
        
        Invoice invoice = new Invoice (invoiceNum,invoiceDate,customerName);
        fr.getInvoices().add(invoice);
        fr.getInvTableMod().fireTableDataChanged();
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void createLineCancel() {
        invLineDialog.setVisible(false);
        invLineDialog.dispose();
        invLineDialog =null;
    }

    private void createLineOK() {
        String itemName = invLineDialog.getItemNameField().getText();
        String countstr = invLineDialog.getItemCountField().getText();
        String itemPricestr = invLineDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countstr);
        double itemPrice =Double.parseDouble(itemPricestr);
        int selectedInvoice = fr.getInvoiceTable().getSelectedRow();
        if(selectedInvoice != -1){
           Invoice invoice = fr.getInvoices().get(selectedInvoice);
           InvoiceLine line = new InvoiceLine(itemName,itemPrice,count,invoice);
           invoice.getLines().add(line);
           LineTableMod lineTableMod = (LineTableMod) fr.getInvoicelineTable().getModel();
           lineTableMod.fireTableDataChanged();
           fr.getInvTableMod().fireTableDataChanged();
           
           
        }
        invLineDialog.setVisible(false);
        invLineDialog.dispose();
        invLineDialog = null; 
    }

   

   
}
