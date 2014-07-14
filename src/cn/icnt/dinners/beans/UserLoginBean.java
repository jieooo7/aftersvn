package cn.icnt.dinners.beans;

public class UserLoginBean {

	public UserInfos para;
	public UserInfosHead head;
	public String result;

	public class UserInfos {
		public String user_id;
		public String email;
		public String phone;
		public String user_pwd;
		public String level_id;
		public String balance;
		public String picture_url;
		public String login_time;
		public String login_ip;
		public String user_status;
		public String point;
		public String reg_time;
		public String true_name;
		public String sex;
		public String qq;
		public String mobile;
		public String province;
		public String city;
		public String area;
		public String address;
		public String zip;
	}

	public class UserInfosHead {
		public String code;
		public String msg;
	}
}
