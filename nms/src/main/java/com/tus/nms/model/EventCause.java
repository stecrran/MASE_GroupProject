package com.tus.nms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "event_cause")
public class EventCause {

	//cause codes are not unique, so added id field for unique identificaiton.
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column(name = "cause_code")
    private Integer causeCode;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "description")
    private String description;

    // Getters and Setters
    public Integer getCauseCode() {
        return causeCode;
    }

    public void setCauseCode(Integer causeCode) {
        this.causeCode = causeCode;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Default Constructor
    public EventCause() {
    }

//	public EventCause(Long id, Integer causeCode, Integer eventId, String description) {
//		this.id = id;
//		this.causeCode = causeCode;
//		this.eventId = eventId;
//		this.description = description;
//	}

//	@Override
//	public String toString() {
//		return "EventCause [id=" + id + ", causeCode=" + causeCode + ", eventId=" + eventId + ", description="
//				+ description + "]";
//	}

    
}
