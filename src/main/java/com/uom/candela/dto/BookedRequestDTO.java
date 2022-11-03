package com.uom.candela.dto;

public class BookedRequestDTO {
    private String vehicleId;
    private String userId;
    private boolean returnOrnot;


	public String getBookId() {
        return vehicleId;
    }

    public void setBookId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getMemberId() {
        return userId;
    }

    public void setMemberId(String userId) {
        this.userId = userId;
    }
    
    public boolean isReturnOrnot() {
		return returnOrnot;
	}

	public void setReturnOrnot(boolean returnOrnot) {
		this.returnOrnot = returnOrnot;
	}

}
