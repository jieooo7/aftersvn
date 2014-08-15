package cn.icnt.dinners.beans;

import java.io.Serializable;
import java.util.List;

public class OrderCompanyBean {

    public OrderCompanyHead head;
    public OrderCompanyPara para;
    public List<OrderCompanydesc> result;

    public class OrderCompanyHead {
	public String code;
	public String msg;
    }

    public class OrderCompanyPara {
	public int count;
	public int start;
	public String total;
    }

    public class OrderCompanydesc implements Serializable {
	public String comment_num;
	public String company_id;
	public String contact_tel;
	public String description;
	public String end_time;
	public String favorite;
	public String food_name;
	public String lager_picture;
	public String name_store;
	public String name_url;
	public String remind;
	public String share_count;
	public String start_time;
	public String store_str;
	public String sub_company_id;
	public String address_str;
	public String remark;

	public OrderCompanydesc(String comment_num, String company_id,
		String contact_tel, String description, String end_time, String favorite,
		String food_name, String lager_picture, String name_store,
		String name_url, String remind, String share_count, String start_time,
		String store_str, String sub_company_id, String address_str, String remark) {
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
	    this.remind = remind;
	    this.share_count = share_count;
	    this.start_time = start_time;
	    this.store_str = store_str;
	    this.sub_company_id = sub_company_id;
	    this.address_str = address_str;
	    this.remark = remark;
	}

	@Override
	public String toString() {
	    return "OrderCompanydesc [comment_num=" + comment_num + ", company_id="
		    + company_id + ", contact_tel=" + contact_tel + ", description="
		    + description + ", end_time=" + end_time + ", favorite=" + favorite
		    + ", food_name=" + food_name + ", lager_picture=" + lager_picture
		    + ", name_store=" + name_store + ", name_url=" + name_url
		    + ", remind=" + remind + ", share_count=" + share_count
		    + ", start_time=" + start_time + ", store_str=" + store_str
		    + ", sub_company_id=" + sub_company_id + ", address_str="
		    + address_str + ", remark=" + remark + "]";
	}

    }

}
