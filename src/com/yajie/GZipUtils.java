
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtils {

	public static void main(String[] args) throws ParseException, IOException {
		String str = "{\"tel\":\"13776177228\",\"uid\":\"uid123uid\",\"time\":1525314559273,\"time_type\":1,\"call_log\":[{\"call_tel\":\"18550524721\",\"call_method\":2,\"call_time\":1480557956,\"call_duration\":17.0}]}";
		System.out.println(new String(uncompress(compress(str)), "UTF-8"));
	}

	// 压缩  
	public static byte[] compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes("UTF-8"));
		gzip.close();
		
		return out.toByteArray();
	}

	// 解压缩  
    public static byte[] uncompress(byte[] bytes) throws IOException {  
        if (bytes == null || bytes.length == 0) {  
            return null;  
        }  
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);  
        GZIPInputStream gunzip = new GZIPInputStream(in);  
        byte[] buffer = new byte[1024];  
        int n;  
        while ((n = gunzip.read(buffer)) >= 0) {  
            out.write(buffer, 0, n);  
        }  

        return out.toByteArray();  
    }  
    
}
