package cn.icnt.dinners.beans;

import java.util.List;

public class OrderDetailBean {

//	public UserInfos para;
	public UserInfosHead head;
	public OrderDetail result;

	public class UserInfosHead {
		public String code;
		public String msg;
	}

//	public class UserInfos {
//		public int count;
//		public int start;
//		public String total;
//	}

	public class OrderDetail {
		public List<OrderListItem> goods_list;
//		public String img_url; // 优惠券
		public String order_discount; // 优惠券
		public String order_id;
		public String order_info;// 订单详情
		public String order_info_id; // 订单编号
		public String order_price; // 订单优惠价
		/**
		 * order_state=1：订单未支付
		 * order_state=2：订单已支付 
		 * order_state=3：订单交易成功
		 * order_state=4：订单订单被取消 
		 * order_state=5：订单退款申请中 
		 * order_state=6：订单退款成功
		 */
		public int order_state;
		/**
		 * 商家信息
		 */
		public String order_store;
		public String order_total_price; // 订单总价

		public class OrderListItem {
			public String goods_id; // 商品编号
			public String img_url; // 商品图片
			public String name; // 商品名称
			public String num; // 商品数量
			public String order_id; // 所属订单号
			public String price; // 商品原价
			public String real_price; // 商品优惠价
		}
	}
}
