
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public abstract class SocketClient implements SocketFace{
	Socket socket;
	PrintWriter write ;//可以
	//OutputStream write;//不可以
    BufferedReader in;

    /**
     * 接收数据
     */
    public abstract void receive(String text);
    /**
     * 创建连接
     * connect("10.1.12.55", 5209);
     * @param ip
     * @param port
     */
    public void connect(final String ip,final Integer port){
    	Runnable runnable=new Runnable(){//线程,处理循环阻塞
			@Override
			public void run() {
				connect2(ip,port);
			}
    	};
    	Thread thread=new Thread(runnable);
    	thread.start();
    }
    private void connect2(String ip,Integer port){
    	try {
    		//server,client主要区另在这2行
            socket = new Socket(ip, port);//Socket服务器地址和端口
            receive("客户端启动成功");
            
            //生成相关的IO流
            write = new PrintWriter(socket.getOutputStream());//可以
//            write=socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String receive="";
            while(!receive.equals("endiiafdoefda")){//解决客户端关闭时,服务端也关闭
                //接收数据
            	receive=in.readLine();
            	receive(receive);
            }
        } catch (Exception e) {
            System.out.println("can not listen to:" + e);// 出错，打印出错信息
        }
    }
    /**
     * 发送数据
     * @param text
     */
    public void write(String text,String chartSet){
    	//write.write(text.getBytes());
    	try {
			byte[] bytes=text.getBytes(chartSet);
			String txt=new String(bytes,chartSet);
			write.println(txt);
			write.flush();//刷新输出流，使Server马上收到该字符串
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
    }
    /**关闭资源 */
    public void close(){
        try {
        	write.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
    }
}
