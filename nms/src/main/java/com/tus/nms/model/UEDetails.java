package com.tus.nms.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "ue_details")
public class UEDetails {

    @Id
    @Column(name = "tac", nullable = false)
    private Integer tac;

    @Column(name = "marketing_name")
    private String marketingName;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "access_capability")
    private String accessCapability;

    // Getters and Setters
    public Integer getTac() {
        return tac;
    }

    public void setTac(Integer tac) {
        this.tac = tac;
    }

    public String getMarketingName() {
        return marketingName;
    }

    public void setMarketingName(String marketingName) {
        this.marketingName = marketingName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getAccessCapability() {
        return accessCapability;
    }

    public void setAccessCapability(String accessCapability) {
        this.accessCapability = accessCapability;
    }

    // Default Constructor
    public UEDetails() {
    }

	public UEDetails(Integer tac, String marketingName, String manufacturer, String accessCapability) {
		this.tac = tac;
		this.marketingName = marketingName;
		this.manufacturer = manufacturer;
		this.accessCapability = accessCapability;
	}

	@Override
	public String toString() {
		return "UETable [tac=" + tac + ", marketingName=" + marketingName + ", manufacturer=" + manufacturer
				+ ", accessCapability=" + accessCapability + "]";
	}    
}
