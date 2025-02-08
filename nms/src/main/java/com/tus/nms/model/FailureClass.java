package com.tus.nms.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "failure_class")
public class FailureClass {

    @Id
    @Column(name = "failure_class", nullable = false)
    private Integer failureClass;

    @Column(name = "description")
    private String description;

    // Getters and Setters
    public Integer getFailureClass() {
        return failureClass;
    }

    public void setFailureClass(Integer failureClass) {
        this.failureClass = failureClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Default Constructor
    public FailureClass() {
    }

	public FailureClass(Integer failureClass, String description) {
		this.failureClass = failureClass;
		this.description = description;
	}

	@Override
	public String toString() {
		return "FailureClass [failureClass=" + failureClass + ", description=" + description + "]";
	}
}
