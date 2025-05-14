package model;

import java.util.Date;

public class Fine {
    private int id;
    private int studentId;
    private int issuedBookId;
    private double amount;
    private boolean paid;
    private Date date;

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getIssuedBookId() {
        return issuedBookId;
    }
    public void setIssuedBookId(int issuedBookId) {
        this.issuedBookId = issuedBookId;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}