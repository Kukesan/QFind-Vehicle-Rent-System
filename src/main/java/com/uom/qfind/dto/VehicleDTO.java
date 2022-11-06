package com.uom.qfind.dto;

public class VehicleDTO {
    private String id;
    private String name;
    private String number;
    private String owner;
    private boolean availability;

    private BookedDTO booked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BookedDTO getBorrowing() {
        return booked;
    }

    public void setBorrowing(BookedDTO borrowing) {
        this.booked = booked;
    }

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
    
}
