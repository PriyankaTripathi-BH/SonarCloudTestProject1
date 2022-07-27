package com.bh.realtrack.dto;

public class OrdersAndSlottingCardDetailDTO {

	private double ordersAmountOut;
	private double ordersPercentageOut;
	private double cmAmountOut;
	private double cmPercentageOut;
	private int novaItOut;
	private int aerogtOut;
	private String vPeriod;
	private double cmAsPerc;

	public double getOrdersAmountOut() {
		return ordersAmountOut;
	}

	public void setOrdersAmountOut(double ordersAmountOut) {
		this.ordersAmountOut = ordersAmountOut;
	}

	public double getOrdersPercentageOut() {
		return ordersPercentageOut;
	}

	public void setOrdersPercentageOut(double ordersPercentageOut) {
		this.ordersPercentageOut = ordersPercentageOut;
	}

	public double getCmAmountOut() {
		return cmAmountOut;
	}

	public void setCmAmountOut(double cmAmountOut) {
		this.cmAmountOut = cmAmountOut;
	}

	public double getCmPercentageOut() {
		return cmPercentageOut;
	}

	public void setCmPercentageOut(double cmPercentageOut) {
		this.cmPercentageOut = cmPercentageOut;
	}

	public int getNovaItOut() {
		return novaItOut;
	}

	public void setNovaItOut(int novaItOut) {
		this.novaItOut = novaItOut;
	}

	public int getAerogtOut() {
		return aerogtOut;
	}

	public void setAerogtOut(int aerogtOut) {
		this.aerogtOut = aerogtOut;
	}

	public String getvPeriod() {
		return vPeriod;
	}

	public void setvPeriod(String vPeriod) {
		this.vPeriod = vPeriod;
	}

	public double getCmAsPerc() {
		return cmAsPerc;
	}

	public void setCmAsPerc(double cmAsPerc) {
		this.cmAsPerc = cmAsPerc;
	}

}
