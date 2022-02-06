package com.jb.couponsystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jb.couponsystem.data.LocalDateConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Company company;
    private String title;
    @Convert(converter = LocalDateConverter.class)
    private LocalDateTime startDate;
    @Convert(converter = LocalDateConverter.class)
    private LocalDateTime endDate;
    private int category;
    private int amount;
    private String description;
    private double price;
    private String image;
    @ManyToMany
    @JoinTable(name = "customer_coupon", joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Customer> customers;

    public Coupon() {
        customers = new ArrayList<>();
    }

    public Coupon(String title, LocalDateTime startDate, LocalDateTime endDate, int category, int amount, String desc, double price, String image) {
        this();
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.amount = amount;
        this.description = desc;
        this.price = price;
        this.image = image;
    }

    public Coupon(long id, String title, LocalDateTime startDate, LocalDateTime endDate,
                  int category, int amount, String desc, double price, String image) {
        this();
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.amount = amount;
        this.description = desc;
        this.price = price;
        this.image = image;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", company=" + company +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", category=" + category +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", customers=" + customers +
                '}';
    }
}
