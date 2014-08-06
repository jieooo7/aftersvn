package cn.icnt.dinners.entity;

import java.io.Serializable;

public class Store implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Store() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	public Store(Integer comment_num, Integer company_id, String contact_tel,
			String description, String end_time, Integer favorite,
			String food_name, String lager_picture, String name_store,
			String name_url, String remark, String remind, Integer share_count,
			String start_time, String store_str, Integer sub_company_id) {
		super();
		this.comment_num = comment_num;
		this.company_id = company_id;
		this.contact_tel = contact_tel;
		this.description = description;
		this.end_time = end_time;
		this.favorite = favorite;
		this.food_name = food_name;
		this.lager_picture = lager_picture;
		this.name_store = name_store;
		this.name_url = name_url;
		this.remark = remark;
		this.remind = remind;
		this.share_count = share_count;
		this.start_time = start_time;
		this.store_str = store_str;
		this.sub_company_id = sub_company_id;
	}

	@Override
	public String toString() {
		return "Store [comment_num=" + comment_num + ", company_id="
				+ company_id + ", contact_tel=" + contact_tel
				+ ", description=" + description + ", end_time=" + end_time
				+ ", favorite=" + favorite + ", food_name=" + food_name
				+ ", lager_picture=" + lager_picture + ", name_store="
				+ name_store + ", name_url=" + name_url + ", remark=" + remark
				+ ", remind=" + remind + ", share_count=" + share_count
				+ ", start_time=" + start_time + ", store_str=" + store_str
				+ ", sub_company_id=" + sub_company_id + "]";
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

	public String getContact_tel() {
		return contact_tel;
	}

	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
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

	public String getFood_name() {
		return food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public String getLager_picture() {
		return lager_picture;
	}

	public void setLager_picture(String lager_picture) {
		this.lager_picture = lager_picture;
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


	private Integer comment_num;
	private Integer company_id;
	private String contact_tel;
	private String description;
	private String end_time;
	private Integer favorite;
	private String food_name;
	private String lager_picture;
	private String name_store;
	private String name_url;
	
	private String remark;
	private String remind;
	private Integer share_count;
	private String start_time;
	private String store_str;
	private Integer sub_company_id;
	

	
	
	
}
