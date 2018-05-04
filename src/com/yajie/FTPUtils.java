
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public static void testFtp() {
		boolean success = downFile("120.27.219.236", 22, "root", "da#@I*O(qdd2017", "/home/operator",
				"18515453268_2018-01-29.json", "C:/yajie");
		if (success) {
			System.out.println("下载成功");
		} else {
			System.out.println("下载失败");
		}
	}
	
	/**
	 * Description: 从FTP服务器下载文件 @Version1.0
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param userName
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public static boolean downFile(String url, int port, String userName, String password, String remotePath,
			String fileName, String localPath) {
		boolean success = false;
		FTPClient ftpClient = new FTPClient();

		try {
			int reply;
			ftpClient.connect(url, port);
			ftpClient.login(userName, password);
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return success;
			}

			ftpClient.changeWorkingDirectory(remotePath);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			for (FTPFile ftpFile : ftpFiles) {
				if (ftpFile.getName().equals(fileName)) {
					File file = new File(localPath + "/" + ftpFile.getName());

					OutputStream outputStream = new FileOutputStream(file);
					ftpClient.retrieveFile(ftpFile.getName(), outputStream);
					outputStream.close();
				}
			}

			ftpClient.logout();
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return success;
	}
  
