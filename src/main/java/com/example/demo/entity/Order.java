package com.example.demo.entity;

import com.example.demo.status.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@Entity
@Table(name = "CUSTOM_ORDER")
public class Order {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue
    private Long id;

    private String desccription;
    private Status status;


    public Order() {}

    public Order(String desccription, Status status) {
        this.desccription = desccription;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesccription() {
        return desccription;
    }

    public void setDesccription(String desccription) {
        this.desccription = desccription;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.desccription, this.status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof  Order)) return false;

        Order order = (Order)obj;
        return Objects.equals(this.id, order.id) && Objects.equals(this.desccription, order.desccription)
                && this.status == order.status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", desccription='" + desccription + '\'' +
                ", status=" + status +
                '}';
    }
}
