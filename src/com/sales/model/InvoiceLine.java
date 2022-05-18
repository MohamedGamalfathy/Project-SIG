
package com.sales.model;


public class InvoiceLine {
   private String itemName;
   private double itemPrice;
   private int count;
   private Invoice invoice;
   
   

    public InvoiceLine() {
    }

    public InvoiceLine(String itemName, double itemPrice, int count) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
    }

    public InvoiceLine( String itemName, double itemPrice, int count, Invoice invoice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
        this.invoice = invoice;
    }
    
    public double getLineTotal(){
        return itemPrice * count;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "InvoiceLine{" + "invoiceNum=" + invoice.getInvoiceNum() + ", itemName=" + itemName + ", itemPrice=" + itemPrice + ", count=" + count + '}';
    }

    public Invoice getInvoice() {
        return invoice;
    }
    public String getCSV(){
        return invoice.getInvoiceNum()+","+itemName+","+itemPrice+","+count;
    }
    


   
           
    
}
