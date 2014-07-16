package cn.icnt.dinners.entity;

import java.util.ArrayList;
import java.util.List;

public class Present {
	public List<Head> head;
	public List<Para> para;
	public static class Para{
		
		public int count;
		public int start;
		public int total;
		
	}
	public List<ResultList> result;
	public static class Head{
		public int code;
		public String msg;
		
		
	}
	public static class ResultList {
		public String date;
		public String desc;
		public String information_str;
		public String name_store;
		public String name_url;
		public String share_count;
		public String store_str;
		public String sub_company_id;

	}

}
