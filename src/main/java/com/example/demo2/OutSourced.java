package com.example.demo2;


/**
 * extends the parts class
 * if the part is an outsourced part it will add the company name to the part information
 */


public class OutSourced extends Part {

    private String companyName;
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param name the company name to set
     */
    public void setCompanyNameName(String name) {
        this.companyName = companyName;
    }

    /**
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }



}