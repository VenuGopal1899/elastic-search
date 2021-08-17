package com.eldorado.elasticsearch.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Product {

    private String productId;
    private String title;
    private String description;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product(String productId, String title, String description) {
        super();
        this.productId = productId;
        this.title = title;
        this.description = description;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", title=" + title + ", description=" + description + "]";
    }
}
