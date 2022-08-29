package ua.mk.essur.task4finaltask.weblogic.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Integer id;

    @Column(name = "customer_name", length = 100)
    private String customerName;

    @Column(name = "customer_company", length = 100)
    private String customerCompany;

    @ManyToMany
    @JoinTable(name = "customer_orders",
            joinColumns = @JoinColumn(name = "customer_customer_id"),
            inverseJoinColumns = @JoinColumn(name = "orders_order_id"))
    private Collection<Order> orders = new ArrayList<>();

    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Order order) {
        orders.add(order);
    }
    public void setOrdersList(Collection<Order> orders){ this.orders.addAll(orders);}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}