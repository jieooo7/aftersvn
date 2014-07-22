package cn.icnt.dinners.entity;

import java.io.Serializable;

public class Dishes implements Serializable {
	
	
	public Dishes() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getAddress_str() {
		return address_str;
	}
	public void setAddress_str(String address_str) {
		this.address_str = address_str;
	}
	public Integer getComment_num() {
		return comment_num;
	}
	public void setComment_num(Integer comment_num) {
		this.comment_num = comment_num;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public Integer getFavorite() {
		return favorite;
	}
	public void setFavorite(Integer favorite) {
		this.favorite = favorite;
	}
	public String getFood_str() {
		return food_str;
	}
	public void setFood_str(String food_str) {
		this.food_str = food_str;
	}
	public String getGiving_str() {
		return giving_str;
	}
	public void setGiving_str(String giving_str) {
		this.giving_str = giving_str;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemind() {
		return remind;
	}
	public void setRemind(String remind) {
		this.remind = remind;
	}
	public Integer getShare_count() {
		return share_count;
	}
	public void setShare_count(Integer share_count) {
		this.share_count = share_count;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
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
	public Integer getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}
	private String address_str;
	private Integer comment_num;
	private Integer company_id;
	private String description;
	private String end_time;
	private Integer favorite;
	private String food_str;
	private String giving_str;
	private String information_str;
	private String name_store;
	private String name_url;
	private String phone_str;
	private String remark;
	private String remind;
	private Integer share_count;
	private String start_time;
	private String store_str;
	private Integer sub_company_id;
	private Integer goods_id;
	private String lager_picture;
	public Dishes(String address_str, Integer comment_num, Integer company_id,
			String description, String end_time, Integer favorite,
			String food_str, String giving_str, String information_str,
			String name_store, String name_url, String phone_str,
			String remark, String remind, Integer share_count,
			String start_time, String store_str, Integer sub_company_id,
			Integer goods_id, String lager_picture) {
		super();
		this.address_str = address_str;
		this.comment_num = comment_num;
		this.company_id = company_id;
		this.description = description;
		this.end_time = end_time;
		this.favorite = favorite;
		this.food_str = food_str;
		this.giving_str = giving_str;
		this.information_str = information_str;
		this.name_store = name_store;
		this.name_url = name_url;
		this.phone_str = phone_str;
		this.remark = remark;
		this.remind = remind;
		this.share_count = share_count;
		this.start_time = start_time;
		this.store_str = store_str;
		this.sub_company_id = sub_company_id;
		this.goods_id = goods_id;
		this.lager_picture = lager_picture;
	}

	@Override
	public String toString() {
		return "Dishes [address_str=" + address_str + ", comment_num="
				+ comment_num + ", company_id=" + company_id + ", description="
				+ description + ", end_time=" + end_time + ", favorite="
				+ favorite + ", food_str=" + food_str + ", giving_str="
				+ giving_str + ", information_str=" + information_str
				+ ", name_store=" + name_store + ", name_url=" + name_url
				+ ", phone_str=" + phone_str + ", remark=" + remark
				+ ", remind=" + remind + ", share_count=" + share_count
				+ ", start_time=" + start_time + ", store_str=" + store_str
				+ ", sub_company_id=" + sub_company_id + ", goods_id="
				+ goods_id + ", lager_picture=" + lager_picture + "]";
	}

	public String getLager_picture() {
		return lager_picture;
	}
	public void setLager_picture(String lager_picture) {
		this.lager_picture = lager_picture;
	}

}
