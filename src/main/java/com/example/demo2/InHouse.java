package com.example.demo2;


/**
 * in house class
 * extends the parts class
 * if the part is an inhouse part it will add the machine id to the part information
 */

public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * @param id the machine ID to set
     */
    public void setMachineId(int id) {
        this.machineId = machineId;
    }
    /**
     * @return the machine ID
     */
    public int getMachineId() {

        return machineId;
    }





}
