package cn.icnt.dinners.beans;

import java.io.Serializable;
import java.util.List;

public class HomeDishesBean {

    public HomeDishesHead head;
    public HomeDishesPara para;
    public List<HomeDishesdesc> result;

    public class HomeDishesHead {
	public String code;
	public String msg;
    }

    public class HomeDishesPara {
	public int count;
	public int start;
	public String total;
    }

    public class HomeDishesdesc implements Serializable {
	public String address_str; //地址
	public String area;
	public String city;
	public String comment_num;//评价数量
	public String company_id;
	public String sub_company_id;
	public String description;//详情
	public String end_time;
	public String favorite;
	public String food_str; //食品名
	public String giving_str; //其他详情
	public String goods_id;
	public String information_str;//商品介绍
	public String interest;
	public String lager_picture;
	public String name_store;
	public String name_url;
	public String phone_str;
	public String province;
	public String remind;//
	public String share_count;//分享数量
	public String start_time;
	public String original_price; //菜品单价

    }

}
