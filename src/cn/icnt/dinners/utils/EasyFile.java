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
 * �ļ���д com.cloud.app.utils.EasyFile
 * 
 * @author Andrew Lee <br/>
 *         create at 2014��6��18�� ����5:47:09
 */
public class EasyFile {
	private static final String TAG = "EasyFile";

	/**
	 * д��list���ݵ��ļ�
	 * 
	 * @param filePath
	 *            �ļ���
	 * @param list
	 *            list����
	 * @return д����߲�д��
	 */
	public static boolean writeFile(String filePath,
			List<Map<String, String>> list) {
		String fileContent = "";
		// �����ļ�Ŀ¼��û���򴴽�
		if (FileHelper.createDirectory(FileManager.getSaveFilePath() + "json/")) {
			Gson gson = new Gson();
			fileContent = gson.toJson(list);

			// �ж��ļ������Ƿ���ȣ�����д��
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
	 * ��ȡ���ݵ�list
	 * 
	 * @param filePath
	 *            �ļ���
	 * @return list����
	 */
	public static List<Map<String, String>> readFile(String filePath) {

		InputStream is = FileHelper.readFile(FileManager.getSaveFilePath()
				+ "json/" + filePath);
		String st = "";
		try {
			st = new String(FileHelper.readAll(is));
		} catch (Exception e) {

		}
		List<Map<String, String>> list = GsonTools.getListMaps(st);
		return list;
	}

	/**
	 * ��ȡ�ļ�String����
	 * 
	 * @param filePath
	 *            �ļ���
	 * @return String����
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
