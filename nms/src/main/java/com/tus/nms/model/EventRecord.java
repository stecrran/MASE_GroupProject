package com.tus.nms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "event_records")
public class EventRecord {

	//event id's are not unique, so added id field for unique identificaiton.
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "date_time")
    private String dateTime;

    @Column(name = "failure_class")
    private String failureClass; 
    // some values in file have "(null)", Integer.parseInt(string) does not work
    // so changed from Integer to String
    // Need to confirm how to handle this String or Integer.

    @Column(name = "ue_type")
    private Integer ueType;

    @Column(name = "market")
    private Integer market;

    @Column(name = "operator")
    private Integer operator;

    @Column(name = "cell_id")
    private Integer cellId;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "cause_code")
    private String causeCode;
    // some values in file have "(null)", Integer.parseInt(string) does not work
    // so changed from Integer to String
    // Need to confirm how to handle this String or Integer.

    @Column(name = "ne_version")
    private String neVersion;

    @Column(name = "imsi")
    private String imsi;

    @Column(name = "hier3_id")
    private String hier3Id;

    @Column(name = "hier32_id")
    private String hier32Id;

    @Column(name = "hier321_id")
    private String hier321Id;

    // Getters and Setters
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFailureClass() {
        return failureClass;
    }

    public void setFailureClass(String failureClass) {
        this.failureClass = failureClass;
    }

    public Integer getUeType() {
        return ueType;
    }

    public void setUeType(Integer ueType) {
        this.ueType = ueType;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getCellId() {
        return cellId;
    }

    public void setCellId(Integer cellId) {
        this.cellId = cellId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCauseCode() {
        return causeCode;
    }

    public void setCauseCode(String causeCode) {
        this.causeCode = causeCode;
    }

    public String getNeVersion() {
        return neVersion;
    }

    public void setNeVersion(String neVersion) {
        this.neVersion = neVersion;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getHier3Id() {
        return hier3Id;
    }

    public void setHier3Id(String hier3Id) {
        this.hier3Id = hier3Id;
    }

    public String getHier32Id() {
        return hier32Id;
    }

    public void setHier32Id(String hier32Id) {
        this.hier32Id = hier32Id;
    }

    public String getHier321Id() {
        return hier321Id;
    }

    public void setHier321Id(String hier321Id) {
        this.hier321Id = hier321Id;
    }

    public EventRecord() {
    }
    
	public EventRecord(Long id, Integer eventId, String dateTime, String failureClass, Integer ueType, Integer market,
			Integer operator, Integer cellId, Integer duration, String causeCode, String neVersion, String imsi,
			String hier3Id, String hier32Id, String hier321Id) {
		super();
		this.id = id;
		this.eventId = eventId;
		this.dateTime = dateTime;
		this.failureClass = failureClass;
		this.ueType = ueType;
		this.market = market;
		this.operator = operator;
		this.cellId = cellId;
		this.duration = duration;
		this.causeCode = causeCode;
		this.neVersion = neVersion;
		this.imsi = imsi;
		this.hier3Id = hier3Id;
		this.hier32Id = hier32Id;
		this.hier321Id = hier321Id;
	}

	@Override
	public String toString() {
		return "EventRecord [id=" + id + ", eventId=" + eventId + ", dateTime=" + dateTime + ", failureClass="
				+ failureClass + ", ueType=" + ueType + ", market=" + market + ", operator=" + operator + ", cellId="
				+ cellId + ", duration=" + duration + ", causeCode=" + causeCode + ", neVersion=" + neVersion
				+ ", imsi=" + imsi + ", hier3Id=" + hier3Id + ", hier32Id=" + hier32Id + ", hier321Id=" + hier321Id
				+ "]";
	}
    
	
    
}
