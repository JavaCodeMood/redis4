package com.redis_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.CharBuffer;

/**
 * 要实现和Redis服务端通信，首先需要与Redis服务端建立TCP通信连接，
 * 然后使用RESP协议，将想要执行的Redis命令发送至服务端，
 * 并等待服务端响应，然后接收到响应结果，展示给用户。
 * https://yq.aliyun.com/articles/66631
 * 
 * 实现一个简单的获取info的操作
 * @author dell
 *
 */
public class App {

	public static void main(String[] args) {
		//定义redis服务端默认端口
		int port = 6379;
		
		Socket socket = null;  //Socket套接字
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			//创建tcp连接
			socket = new Socket("127.0.0.1",port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			//传送info命令
			//客户端向Redis服务器发送命令，以RESP整块字符串数组的形式
			out.println("*1\r\n$4\r\ninfo\r\n");
			System.out.println("redis命令发送成功！");
			
			//接收服务器的回复
			CharBuffer response = CharBuffer.allocate(1024);
			int readedLen = in.read(response);
			String responseBody = response.flip().toString();
			
			//输出服务器的回复
			System.out.println(responseBody);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//最后关闭相关的流
			if(out != null){
				out.close();
				out = null;
			}
			if(in != null){
				try {
					in.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				in = null;
			}
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				socket = null;
			}
		}

	}

}
