package cn.icnt.dinners.entity;

import java.io.Serializable;

public class Result implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String address_str;
	private Integer company_id;
	private Integer goods_id;
	private String information_str;
	private String name_store;
	private String name_url;
	private String phone_str;
	private String remind;
	private String store_str;
	private Integer sub_company_id;
	private String lager_picture;
	private String date;
	private String description;
	private Integer favorite;
	@Override
	public String toString() {
		return "Result [address_str=" + address_str + ", company_id="
				+ company_id + ", goods_id=" + goods_id + ", information_str="
				+ information_str + ", name_store=" + name_store
				+ ", name_url=" + name_url + ", phone_str=" + phone_str
				+ ", remind=" + remind + ", store_str=" + store_str
				+ ", sub_company_id=" + sub_company_id + ", lager_picture="
				+ lager_picture + ", date=" + date + ", description="
				+ description + ", favorite=" + favorite + "]";
	}
	public Result(String address_str, Integer company_id, Integer goods_id,
			String information_str, String name_store, String name_url,
			String phone_str, String remind, String store_str,
			Integer sub_company_id, String lager_picture, String date,
			String description, Integer favorite) {
		super();
		this.address_str = address_str;
		this.company_id = company_id;
		this.goods_id = goods_id;
		this.information_str = information_str;
		this.name_store = name_store;
		this.name_url = name_url;
		this.phone_str = phone_str;
		this.remind = remind;
		this.store_str = store_str;
		this.sub_company_id = sub_company_id;
		this.lager_picture = lager_picture;
		this.date = date;
		this.description = description;
		this.favorite = favorite;
	}
	public String getAddress_str() {
		return address_str;
	}
	public void setAddress_str(String address_str) {
		this.address_str = address_str;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public Integer getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}
	public String getInformation_str() {
		return information_str;
	}
	public void setInformation_str(String information_str) {
		this.information_str = information_str;
	}
	public String getName_store() {
		return name_store;
	}
	public void setName_store(String name_store) {
		this.name_store = name_store;
	}
	public String getName_url() {
		return name_url;
	}
	public void setName_url(String name_url) {
		this.name_url = name_url;
	}
	public String getPhone_str() {
		return phone_str;
	}
	public void setPhone_str(String phone_str) {
		this.phone_str = phone_str;
	}
	public String getRemind() {
		return remind;
	}
	public void setRemind(String remind) {
		this.remind = remind;
	}
	public String getStore_str() {
		return store_str;
	}
	public void setStore_str(String store_str) {
		this.store_str = store_str;
	}
	public Integer getSub_company_id() {
		return sub_company_id;
	}
	public void setSub_company_id(Integer sub_company_id) {
		this.sub_company_id = sub_company_id;
	}
	public String getLager_picture() {
		return lager_picture;
	}
	public void setLager_picture(String lager_picture) {
		this.lager_picture = lager_picture;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getFavorite() {
		return favorite;
	}
	public void setFavorite(Integer favorite) {
		this.favorite = favorite;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}