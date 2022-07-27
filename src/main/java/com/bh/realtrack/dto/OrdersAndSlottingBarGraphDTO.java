package com.bh.realtrack.dto;

public class OrdersAndSlottingBarGraphDTO {

	private String monthYear;
	private double ClosedWonOut;
	private double UpsideOut;
	private double OmitOut;
	private double comitAndRiskOut;

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public double getClosedWonOut() {
		return ClosedWonOut;
	}

	public void setClosedWonOut(double closedWonOut) {
		ClosedWonOut = closedWonOut;
	}

	public double getUpsideOut() {
		return UpsideOut;
	}

	public void setUpsideOut(double upsideOut) {
		UpsideOut = upsideOut;
	}

	public double getOmitOut() {
		return OmitOut;
	}

	public void setOmitOut(double omitOut) {
		OmitOut = omitOut;
	}

	public double getComitAndRiskOut() {
		return comitAndRiskOut;
	}

	public void setComitAndRiskOut(double comitAndRiskOut) {
		this.comitAndRiskOut = comitAndRiskOut;
	}

}
