
package com.sales.model;

import java.util.ArrayList;


public class Invoice {
    private int invoiceNum ;
    private String invoiceDate;
    private String customerName;
    private ArrayList<InvoiceLine> lines;
    


    public Invoice() {
    }

    public Invoice(int invoiceNum, String invoiceDate, String customerName) {
        this.invoiceNum = invoiceNum;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
    }
    
        public double getInvoiceTotal(){
        double total =0.0;
        for ( InvoiceLine line : getLines()){
            total += line.getLineTotal();
        }
        
        return total;
    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getInvoiceDate() {

        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
      
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<InvoiceLine> getLines() {
        if(lines == null){
            lines = new ArrayList<>();
            
        }
        return lines;
    }
    

    @Override
    public String toString() {
        return "Invoice{" + "invoiceNum=" + invoiceNum + ", invoiceDate=" + invoiceDate + ", customerName=" + customerName + '}';
    }
    
    public String getCSV(){
        return invoiceNum+","+invoiceDate+","+customerName;
    }
    

    
    
}
