package com.bh.realtrack.dto;

public class OrdersAndSlottingCardDetailParametersDTO {

	private String tier;
	private String horizon;
	private String region;
	private String subOrg;
	private String toggleIn;
	private String confidentailIn;

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public String getHorizon() {
		return horizon;
	}

	public void setHorizon(String horizon) {
		this.horizon = horizon;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSubOrg() {
		return subOrg;
	}

	public void setSubOrg(String subOrg) {
		this.subOrg = subOrg;
	}

	public String getToggleIn() {
		return toggleIn;
	}

	public void setToggleIn(String toggleIn) {
		this.toggleIn = toggleIn;
	}

	public String getConfidentailIn() {
		return confidentailIn;
	}

	public void setConfidentailIn(String confidentailIn) {
		this.confidentailIn = confidentailIn;
	}

}
