
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jay Sun
 * 直接使用org.apache.commons.io.FileUtils工具类
 * 
 */
public class FileUtils {

	public static void main(String[] args) {
		String path = "C:/工作/电话邦催收服务";
		List<String> list = getFile(path);
		for (String string : list) {
			System.out.println(string);
		}
	}

	/**
	 * @param path 文件夹路径
	 * @return
	 */
	public static List<String> getFile(String path) {
		List<String> fileNames = new ArrayList<>();
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			return fileNames;
		}
		File[] array = file.listFiles();

		for (int i = 0,length = array.length; i < length; i++) {
			if (array[i].isFile()) {
				fileNames.add(array[i].getName());
			} else if (array[i].isDirectory()) {
				getFile(array[i].getPath());
			}
		}

		return fileNames;
	}

	/**
	 * @param fileName 通过FileReader读取
	 * @return
	 */
	public static String readFile(String fileName) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		try {
			String line = null;
			br = new BufferedReader(new FileReader(new File(fileName)));
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (Exception e) {
			throw new RuntimeException("读取文件时出错，" + e.getMessage(), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
	
	/**
	 * @param fileName 通过FileInputStream读取
	 * @return
	 */
	public static String readFileV2(String fileName) {
		BufferedInputStream bis = null;
		byte[] bytes = new byte[4096];
		StringBuilder sb = new StringBuilder();
		
		try {
			bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
			int n = 0;
			while ((n = bis.read(bytes)) > 0) {
				sb.append(new String(bytes, 0, n, "UTF-8"));
			}
		} catch (Exception e) {
			throw new RuntimeException("读取文件时出错，" + e.getMessage(), e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return sb.toString();
	}	
	
}
