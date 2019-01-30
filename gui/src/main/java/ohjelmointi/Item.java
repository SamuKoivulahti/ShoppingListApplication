package ohjelmointi;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.*;
import ohjelmointi.mapping.JSONMap;

/**
 * Keeps track of the item values of the list.
 *
 * @author  Samu Koivulahti
 * @version 2018.1215
 * @since   1.8
 */
@JSONMap
@Entity
@Table(name="products")
public class Item {

   /**
    * Keeps track of item name.
    */
    @Transient
    private StringProperty itemName;

   /**
    * Keeps track of item amount.
    */
    @Transient
    private IntegerProperty itemAmount;

   /**
    * Keeps track of itemId.
    */
    @Id
    @Column(name="productID", updatable=false, nullable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int itemId;

   /**
    * Keeps track of item name for database.
    */
    @JSONMap
    @Column(name="productName")
    private String columnName;

   /**
    * Keeps track of item amount for database.
    */
    @JSONMap
    @Column(name="productAmount")
    private Integer columnAmount;

   /**
    * Overrides default constructor.
    */
    public Item() {
        setItemAmount(0);
        setItemName("");
    }

   /**
    * Overloads the default constructor.
    *
    * @param name item name
    * @param amount item amount
    */
    public Item(String name, int amount) {
        setItemAmount(amount);
        setItemName(name);
    }

   /**
    * Updates the items name and amount properties.
    */
    public void updateProperties() {
        setItemAmount(columnAmount);
        setItemName(columnName);
    }
    
   /**
    * Gets item name.
    *
    * @return item name
    */
    public String getItemName() {
        return itemNameProperty().get();
    }

   /**
    * Sets item name.
    * 
    * @param value item name
    */
    public void setItemName(String value) {
        itemNameProperty().set(columnName = value);
    }

    /**
    * Gets item amount.
    *
    * @return item amount
    */
    public int getItemAmount() {
        return itemAmountProperty().get();
    }

    /**
    * Sets item amount.
    *
    * @param value item amount
    */
    public void setItemAmount(int value) {
        itemAmountProperty().set(columnAmount = value);
    }

    /**
    * Creates item name property.
    */
    public StringProperty itemNameProperty() {
        if (itemName == null) {
            itemName = new SimpleStringProperty(this, "Item");
        }

        return itemName;
    }

    /**
    * Creates item amount property.
    */
    public IntegerProperty itemAmountProperty() {
        if (itemAmount == null) {
            itemAmount = new SimpleIntegerProperty(this, "Amount");
        }

        return itemAmount;
    }
}