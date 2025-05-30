package com.noovosoft.salaried.client;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Taxation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID clientId;
    private String clientType; // e.g., "Salaried", "Business", "House"
    private String taxType; // e.g., "Income Tax", "GST", "Property Tax"
    private double taxableAmount;
    private double taxAmount;
    private LocalDate assessmentDate;
    private String status; // e.g., "Filed", "Pending", "Failed"

    public Taxation() {}

    public Taxation(UUID clientId, String clientType, String taxType, double taxableAmount, double taxAmount, LocalDate assessmentDate, String status) {
        this.clientId = clientId;
        this.clientType = clientType;
        this.taxType = taxType;
        this.taxableAmount = taxableAmount;
        this.taxAmount = taxAmount;
        this.assessmentDate = assessmentDate;
        this.status = status;
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public double getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(double taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}