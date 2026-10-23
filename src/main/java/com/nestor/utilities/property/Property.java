package com.nestor.utilities.property;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String city;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(nullable = false, length = 2)
    private String state;

    @Column(nullable = false, length = 10)
    private String zip;

    private String neighborhood;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Instant createdAt;

    protected Property() {
        // required by JPA
    }

    public Property(String name, String streetAddress, String city, String state,
                    String zip, String neighborhood, String type) {
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.neighborhood = neighborhood;
        this.type = type;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getStreetAddress() { return streetAddress; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZip() { return zip; }
    public String getNeighborhood() { return neighborhood; }
    public String getType() { return type; }
    public Instant getCreatedAt() { return createdAt; }
}