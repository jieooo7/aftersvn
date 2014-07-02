package cn.icnt.dinners.utils;


public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "cloud/files/";
		} else {
			return CommonUtil.getRootFilePath() + "cloud/files/";
		}
	}
}
