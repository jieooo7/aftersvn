/*
 * CommentsModel.java
 * classes : cn.icnt.dinners.model.CommentsModel
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年8月21日 上午10:14:34
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.model;

import java.util.List;

/**
 * cn.icnt.dinners.model.CommentsModel
 * 
 * @author Andrew Lee <br/>
 *         create at 2014年8月21日 上午10:14:34
 */
public class CommentsModel {
    public CommentHead head;
    public List<CommentPara> para;

    public class CommentHead {
	public String code;
	public String lager_url;
	public String msg;
    }

    public class CommentPara {
	public String comment_id;
	public String comment_time;
	public String content;
	public String lager_url;
	public String nickname;
	public String picture_url;
	public String support;
	public List<CommentParaDetail> detail;
    }

    public class CommentParaDetail {
	public String content;
	public String nickname;
	public String comment_time;
    }
}
