/*
 * EasyFile.java
 * classes : com.cloud.app.utils.EasyFile
 * author Andrew Lee
 * V 1.0.0
 * Create at 2014��6��18�� ����5:47:09
 * Copyright: 2014 Interstellar Cloud Inc. All rights reserved.
 */
package cn.icnt.dinners.utils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import cn.icnt.dinners.debug.DebugUtil;
import cn.icnt.dinners.http.GsonTools;

import com.google.gson.Gson;


/**
 * cn.icnt.dinners.utils.EasyFile
 * @author Andrew Lee <br/>
 * create at 2014年7月4日 下午4:56:05
 */
public class EasyFile {
	private static final String TAG = "EasyFile";

	/**
	 * 写入文件
	 * 
	 * @param filePath   文件路径
	 * @param list    list类型
	 * @return boolean类型
	 */
	public static boolean writeFile(String filePath,
			List<Map<String, String>> list) {
		String fileContent = "";
		// 创建文件路径
		if (FileHelper.createDirectory(FileManager.getSaveFilePath() + "json/")) {
			Gson gson = new Gson();
			fileContent = gson.toJson(list);

			// 比较文件内容是否相同，相同则不写入
			if ((MD5.getMD5(fileContent).equals(MD5.getMD5(readFileStr(filePath)))) != true) {
				DebugUtil.i("file", "write file");
				return FileHelper.writeFile(FileManager.getSaveFilePath()
						+ "json/" + filePath, fileContent, false);
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 读取文件
	 * 
	 * @param filePath 文件路径
	 * @return list 返回list结果
	 */
	public static List<Map<String, String>> readFile(String filePath) {

		InputStream is = FileHelper.readFile(FileManager.getSaveFilePath()
				+ "json/" + filePath);
		String st = "";
		try {
//			读取文件流，并转换成String
			st = new String(FileHelper.readAll(is));
		} catch (Exception e) {

		}
		List<Map<String, String>> list = GsonTools.getListMaps(st);
		return list;
	}

	/**
	 * 读取文件
	 * 
	 * @param filePath
	 * @return String 返回String类型
	 */
	public static String readFileStr(String filePath) {
		InputStream is = FileHelper.readFile(FileManager.getSaveFilePath()
				+ "json/" + filePath);
		String st = "";
		try {
			st = new String(FileHelper.readAll(is));
		} catch (Exception e) {

		}
		return st;
	}
}
