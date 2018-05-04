
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SFtpUtils {

public static void testSFtp(){
		try {
			downloadSftpFile("120.27.219.236", 22, "root", "da#@I*O(qdd2017", "/root",
					"18515453268_2018-01-30.json", "C:/yajie");
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param sftp_ip
	 * @param sftp_port
	 * @param sftp_username
	 * @param sftp_password
	 * @param sftp_path
	 * @param fileName
	 * @param localPath
	 * @throws JSchException
	 */
	public static void downloadSftpFile(String sftp_ip, int sftp_port, String sftp_username, String sftp_password, 
			String sftp_path, String fileName, String localPath) throws JSchException {

		Session session = null;
		Channel channel = null;
		JSch jsch = new JSch();
		session = jsch.getSession(sftp_username, sftp_ip, sftp_port);
		session.setPassword(sftp_password);
		session.setTimeout(1000);
		Properties config = new Properties();
		// 设置不用检查HostKey，设成yes，一旦计算机的密匙发生变化，就拒绝连接。
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();

		channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp chSftp = (ChannelSftp) channel;

		String localFilePath = localPath + File.separator;
		try {
			chSftp.cd("/home/operator");
			// 使用ChannelSftp的get(文件名，本地路径{包含文件名})方法下载文件
			chSftp.get(fileName, localFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("download error.");
		} finally {
			chSftp.quit();
			channel.disconnect();
			session.disconnect();
		}
	}
  
}
