package com.cyient.designAnalysis;

import java.util.List;

public class DesignGridData {
	
	private String designStatus;
	private String noOfDesigns;
	
	
	public String getDesignStatus() {
		return designStatus;
	}
	public void setDesignStatus(String designStatus) {
		this.designStatus = designStatus;
	}
	
	public String getNoOfDesigns() {
		return noOfDesigns;
	}
	public void setNoOfDesigns(String noOfDesigns) {
		this.noOfDesigns = noOfDesigns;
	}
	@Override
	public String toString() {
		return "DesignGridData [designStatus=" + designStatus
				+ ", noOfDesigns=" + noOfDesigns + "]";
	}
	
	

}
