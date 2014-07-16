package cn.icnt.dinners.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;

public class ActivityList {

    public static HashMap< String, Activity> Activitys=new HashMap<String, Activity>();
    
    public static void destoryActivity(HashMap<String, Activity> activitys){
    			for (Map.Entry<String, Activity> entry : activitys.entrySet()) {
    				Activity activity = entry.getValue();
    				if (activity != null) {
    					activity.finish();
    				}
    			}
    };
    
    public static List<Activity> activitys = new ArrayList<Activity>();
    
    public static void destoryActivity(List<Activity> activitys){
    	for (Activity activity : activitys) {
			if(activity !=null){
				activity.finish();
			}
		}
    }

}
