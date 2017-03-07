package com.redis_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.CharBuffer;

/**
 * Ҫʵ�ֺ�Redis�����ͨ�ţ�������Ҫ��Redis����˽���TCPͨ�����ӣ�
 * Ȼ��ʹ��RESPЭ�飬����Ҫִ�е�Redis�����������ˣ�
 * ���ȴ��������Ӧ��Ȼ����յ���Ӧ�����չʾ���û���
 * https://yq.aliyun.com/articles/66631
 * 
 * ʵ��һ���򵥵Ļ�ȡinfo�Ĳ���
 * @author dell
 *
 */
public class App {

	public static void main(String[] args) {
		//����redis�����Ĭ�϶˿�
		int port = 6379;
		
		Socket socket = null;  //Socket�׽���
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			//����tcp����
			socket = new Socket("127.0.0.1",port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			//����info����
			//�ͻ�����Redis���������������RESP�����ַ����������ʽ
			out.println("*1\r\n$4\r\ninfo\r\n");
			System.out.println("redis����ͳɹ���");
			
			//���շ������Ļظ�
			CharBuffer response = CharBuffer.allocate(1024);
			int readedLen = in.read(response);
			String responseBody = response.flip().toString();
			
			//����������Ļظ�
			System.out.println(responseBody);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//���ر���ص���
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
