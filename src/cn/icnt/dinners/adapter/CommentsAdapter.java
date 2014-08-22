/*
 * CommentsAdapter.java
 * classes : cn.icnt.dinners.adapter.CommentsAdapter
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014年8月20日 下午2:45:12
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.adapter;

import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.icnt.dinners.CircleImageView.CircleImageView;
import cn.icnt.dinners.cache.ImageLoader;
import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.dinner.R;
import cn.icnt.dinners.http.MapPackage;
import cn.icnt.dinners.model.CommentsModel;
import cn.icnt.dinners.model.CommentsModel.CommentPara;
import cn.icnt.dinners.model.CommentsModel.CommentParaDetail;
import cn.icnt.dinners.model.ReplyModel;

/**
 * cn.icnt.dinners.adapter.CommentsAdapter
 * @author Andrew Lee <br/>
 * create at 2014年8月20日 下午2:45:12
 */
public class CommentsAdapter extends BaseExpandableListAdapter{
	private static final String TAG = "CommentsAdapter";
	private List<CommentPara> groups;
	  public LayoutInflater inflater;
	  public Activity activity;
	  private ImageLoader mImageLoader;

	  public CommentsAdapter(Activity act, List<CommentPara> groups) {
	    activity = act;
	    this.groups = groups;
	    inflater = act.getLayoutInflater();
	    mImageLoader = new ImageLoader(act);
	  }

	  @Override
	  public Object getChild(int groupPosition, int childPosition) {
	      CommentPara commentsModel = groups.get(groupPosition);
	    return commentsModel.detail.get(childPosition);
	  }

	  @Override
	  public long getChildId(int groupPosition, int childPosition) {
	    return 0;
	  }

	  @Override
	  public View getChildView(int groupPosition, final int childPosition,
	      boolean isLastChild, View convertView, ViewGroup parent) {
		  CommentParaDetail children = (CommentParaDetail) getChild(groupPosition, childPosition);
//	    TextView text = null;
	    ChildViewHolder childViewHolder=null;
	    if (convertView == null) {
	      convertView = inflater.inflate(R.layout.comments_item_child, null);
	      childViewHolder=new ChildViewHolder();
	      childViewHolder.mTextView0 = (TextView) convertView
					.findViewById(R.id.comments_child_tv0);
	      childViewHolder.mTextView1 = (TextView) convertView
					.findViewById(R.id.comments_child_tv1);
	      childViewHolder.mTextView2 = (TextView) convertView
					.findViewById(R.id.comments_child_tv2);
	      convertView.setTag(childViewHolder);
	    }
		 else {
			 childViewHolder = (ChildViewHolder) convertView.getTag();
		}
	    childViewHolder.mTextView0.setText(children.content);
	    childViewHolder.mTextView1.setText(children.nickname);
	    childViewHolder.mTextView2.setText(children.comment_time);
//	    convertView.setOnClickListener(new OnClickListener() {
//	      @Override
//	      public void onClick(View v) {
//	        Toast.makeText(activity, children,
//	            Toast.LENGTH_SHORT).show();
//	      }
//	    });
	    return convertView;
	  }

	  @Override
	  public int getChildrenCount(int groupPosition) {
	    return groups.get(groupPosition).detail.size();
	  }

	  @Override
	  public Object getGroup(int groupPosition) {
	    return groups.get(groupPosition);
	  }

	  @Override
	  public int getGroupCount() {
	    return groups.size();
	  }

	  @Override
	  public void onGroupCollapsed(int groupPosition) {
	    super.onGroupCollapsed(groupPosition);
	  }

	  @Override
	  public void onGroupExpanded(int groupPosition) {
	    super.onGroupExpanded(groupPosition);
	  }

	  @Override
	  public long getGroupId(int groupPosition) {
	    return 0;
	  }

	  @Override
	  public View getGroupView(int groupPosition, boolean isExpanded,
	      View convertView, ViewGroup parent) {
		  GroupViewHolder groupViewHolder=null;
		    if (convertView == null) {
		      convertView = inflater.inflate(R.layout.comments_item, null);
		      groupViewHolder=new GroupViewHolder();
		      groupViewHolder.mTextView0 = (TextView) convertView
						.findViewById(R.id.comment_item_tv0);
		      groupViewHolder.mTextView1 = (TextView) convertView
						.findViewById(R.id.comment_item_tv1);
		      groupViewHolder.mTextView2 = (TextView) convertView
						.findViewById(R.id.comment_item_tv2);
		      groupViewHolder.mTextView3 = (TextView) convertView
						.findViewById(R.id.comment_item_tv3);
		      groupViewHolder.mImageView = (CircleImageView) convertView
						.findViewById(R.id.comment_item_iv0);
		      groupViewHolder.linear = (LinearLayout) convertView
						.findViewById(R.id.comment_item_ll0);
		      groupViewHolder.linear_good = (LinearLayout) convertView
						.findViewById(R.id.comments_good_ll);
		      groupViewHolder.linear_reply = (LinearLayout) convertView
						.findViewById(R.id.comments_reply_ll);
		      convertView.setTag(groupViewHolder);
		    }
			 else {
				 groupViewHolder = (GroupViewHolder) convertView.getTag();
			}
		    CommentPara group = (CommentPara) getGroup(groupPosition);
//		    groupViewHolder.mImageView.setImageResource(R.drawable.replace);
	    groupViewHolder.mTextView0.setText(group.comment_time);
	    groupViewHolder.mTextView1.setText(group.nickname);
	    groupViewHolder.mTextView2.setText(group.content);
	    groupViewHolder.mTextView3.setText(group.support);
	    DebugUtil.i("评价测试", group.comment_time);
	    mImageLoader.DisplayImage(
				MapPackage.PATH + group.picture_url,
				groupViewHolder.mImageView, false);
	    if(groupPosition%2==0){
	    	groupViewHolder.linear.setBackgroundColor(activity.getResources().getColor(R.color.comments_item_backcoler_dark));
	    	
	    }else{
	    	groupViewHolder.linear.setBackgroundColor(activity.getResources().getColor(R.color.comments_item_backcoler_light));

	    }
	    groupViewHolder.linear_good.setOnClickListener(new OnClickListener() {
	        
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	        	
	        	
	        }
	    });
	    
	    groupViewHolder.linear_reply.setOnClickListener(new OnClickListener() {
	        
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	        	
	        	
	        }
	    });
	    
	    
//	    ((CheckedTextView) convertView).setText(group.string);
//	    ((CheckedTextView) convertView).setChecked(isExpanded);
	    return convertView;
	  }

	  @Override
	  public boolean hasStableIds() {
	    return false;
	  }

	  @Override
	  public boolean isChildSelectable(int groupPosition, int childPosition) {
	    return false;
	  }

	  static class GroupViewHolder {
			TextView mTextView0;
			TextView mTextView1;
			TextView mTextView2;
			TextView mTextView3;
			CircleImageView mImageView;
			LinearLayout linear;
			LinearLayout linear_good;
			LinearLayout linear_reply;
		}
	  
	  static class ChildViewHolder {
			TextView mTextView0;
			TextView mTextView1;
			TextView mTextView2;
			
		}
	
}
