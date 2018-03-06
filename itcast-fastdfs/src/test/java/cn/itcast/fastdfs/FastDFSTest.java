package cn.itcast.fastdfs;

import java.io.IOException;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class FastDFSTest {

	@Test
	public void test() throws Exception {
		
		//配置文件路径
		String conf_filename = ClassLoader.getSystemResource("tracker.conf").getPath();
		
		//设置全局配置信息
		ClientGlobal.init(conf_filename);
		
		//创建TrackerClient
		TrackerClient trackerClient = new TrackerClient();
		
		//创建trackerServer
		TrackerServer trackerServer = trackerClient.getConnection();
		
		//创建storageServer；由于storageServer可由TrackerClient对象的信息提供；所以可以为null
		StorageServer storageServer = null;
		//创建storageClient
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		
		//上传文件
		/**
		 * 参数1：文件路径
		 * 参数2：文件拓展名
		 * 参数3：文件的属性信息
		 */
		String[] upload_file = storageClient.upload_file("D:\\itcast\\pics\\575968fcN2faf4aa4.jpg", "jpg", null);
		
		/**
		 * 返回信息；如下：
		 * group1
			M00/00/00/wKgMqFmc1s-AJMSVAABw0se6LsY192.jpg
		 */
		if(upload_file != null) {
			for (String str : upload_file) {
				System.out.println(str);
			}
			
			//获取存储服务器信息
			ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer, upload_file[0], upload_file[1]);
			for (ServerInfo serverInfo : serverInfos) {
				System.out.println("ip为：" + serverInfo.getIpAddr() + "；port为：" + serverInfo.getPort());
			}
			
			//图片的完整路径
			String url = "http://" + serverInfos[0].getIpAddr() + "/" + upload_file[0] + "/" + upload_file[1];
			System.out.println(url);
		}
	}

}
