package cn.icnt.dinners.utils;

public class Container {
    public static final String CODEKEY = "1234";
    /**
     * 服务端IP
     */
    public static final String URL = "http://115.29.13.164/";
    /**
     * 登陆地址
     */
    public static final String LOGIN_URL = URL + "login.do?t=";
    /**
     * 忘记密码地址
     */
    public static final String FINDPASSWOED = URL + "reset_password.do?t=";
    /**
     * 注册地址
     */
    public static final String REGIEST = URL + "reg.do?t=";
    /**
     * 订单详情地址
     */
    public static final String ORDERDETAIL = URL + "order_detail.do?t=";
    /**
     * 订单列表地址
     */
    public static final String MYORDERLIST = URL + "order_list.do?t=";
    /**
     * 提交评价
     */
    public static final String COMMENTUPDATE = URL + "update_comment.do?t=";
    /**
     * 评价列表请求
     */
    public static final String COMMENT = URL + "comment.do?t=";
    /**
     * 删除订单
     */
    public static final String DELETEORDER = URL + "delete_order.do?t=";
    /**
     * 订单评论接口
     */
    public static final String ORDERCOMMENT = URL + "order_comment.do";
    /**
     * 上传用户头像接口
     */
    public static final String UPLOADPHOTO = URL + "upload_photo.do";
    /**
     * 餐厅推荐接口
     */
    public static final String COMPANY = URL + "company_desc.do";
    /**
     * 菜品推荐接口
     */
    public static final String GOODS = URL + "goods.do";
    /**
     * 收藏接口
     */
    public static final String COLLECT = URL + "favorite.do";
    /**
     * 团购接口
     */
    public static final String GROUP = URL + "group_buying.do";
    /**
     * 周边接口
     */
    public static final String CIRCUM = URL + "near_company.do";
    /**
     * 菜品评价列表接口
     */
    public static final String COMMENT_LIST = URL + "comment_list.do";

}
