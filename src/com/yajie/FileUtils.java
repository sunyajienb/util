
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

}
