package cn.icnt.dinners.entity;

import java.io.Serializable;

public class Mopp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String longitude; 
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Mopp [longitude=" + longitude + ", latitude=" + latitude + "]";
	}
	public Mopp(String longitude, String latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public Mopp() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String latitude;
}
