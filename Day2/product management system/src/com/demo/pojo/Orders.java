package com.demo.pojo;

import java.io.Serializable;
import java.util.List;

public class Orders implements Serializable {

    private int order_id;
    private List<String> product_names;
    private List<Double> product_costs;
    private List<Integer> product_quantities;
    private List<Double> product_percost;
    private double total_amount;

    public List<Double> getProduct_percost() {
        return product_percost;
    }

    public void setProduct_percost(List<Double> product_percost) {
        this.product_percost = product_percost;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public List<String> getProduct_names() {
        return product_names;
    }

    public void setProduct_names(List<String> product_names) {
        this.product_names = product_names;
    }

    public List<Double> getProduct_costs() {
        return product_costs;
    }

    public void setProduct_costs(List<Double> product_costs) {
        this.product_costs = product_costs;
    }

    public List<Integer> getProduct_quantities() {
        return product_quantities;
    }

    public void setProduct_quantities(List<Integer> product_quantities) {
        this.product_quantities = product_quantities;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}
