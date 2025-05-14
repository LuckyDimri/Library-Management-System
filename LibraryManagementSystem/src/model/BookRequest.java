package model;

import java.util.Date;

public class BookRequest {
    private int id;
    private int studentId;
    private Integer bookId; // Nullable for new book requests
    private String type;    // "NEW", "HOLD", "REISSUE"
    private String status;  // "PENDING", "APPROVED", "REJECTED"
    private Date requestDate;
    private String comment; // For extra info (e.g., new book details)

    // Constructors
    public BookRequest() {}

    public BookRequest(int id, int studentId, Integer bookId, String type, String status, Date requestDate, String comment) {
        this.id = id;
        this.studentId = studentId;
        this.bookId = bookId;
        this.type = type;
        this.status = status;
        this.requestDate = requestDate;
        this.comment = comment;
    }

    // Getters and Setters
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

    public Integer getBookId() {
        return bookId;
    }
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}