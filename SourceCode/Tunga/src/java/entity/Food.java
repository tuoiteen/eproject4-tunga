/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.awt.Image;
import java.util.jar.Attributes.Name;
import javax.persistence.Id;

/**
 *
 * @author Hoangha.FPT
 */
public class Food {
    private int id;
    private int menuId;
    private String name;
    private float price;
    private String image;

    public Food(int id, int menuId, String name, float price, String image) {
        this.id = id;
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

  
    public Food() {
    }
    
    
}

