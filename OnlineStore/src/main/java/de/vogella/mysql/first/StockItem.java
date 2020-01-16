package de.vogella.mysql.first;

public class StockItem {

    StockItem(int id, String name, String price, String description){
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int id;
    public String name;
    public String price;
    public String description;
}
