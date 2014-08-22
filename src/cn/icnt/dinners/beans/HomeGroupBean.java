package cn.icnt.dinners.beans;

import java.io.Serializable;
import java.util.List;

public class HomeGroupBean {

    public GroupInfoPara para;
    public GroupInfoHead head;
    public List<GroupInfo> result;

    public class GroupInfoHead {
	public String code;
	public String msg;
    }

    public class GroupInfoPara {
	public int count;
	public int start;
	public String total;
    }

    public class GroupInfo implements Serializable {
	public String area; //
	public String city; //
	public String comment_num; //
	public String company_id; //
	public String end_time; //
	public String favorite; //
	public String food_name; //
	public List<String> food_url; //
	public String information_str; //
	public String interest; //
	public String name_store; //
	public String original_price; //
	public String phone_str; //
	public String price; //
	public String province; //
	public String remind; //
	public String start_time; //
	public String sub_company_id; //
	public String sub_name; //
	public String share_count; //

    }
}
