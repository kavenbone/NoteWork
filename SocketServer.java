
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class SocketServer  implements SocketFace{
	ServerSocket server=null;
	Socket socket=null;
	
	//BufferedReader in;
    //由Socket对象得到输入流，并构造相应的BufferedReader对象
    //PrintWriter writer ;//可以
    OutputStream writer;//不可以,可以了
    InputStream in;

	
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
    	try{
    		//server,client主要区另在这4行
    		server=new ServerSocket(port);//监听此端口
    		receive("server start");
    		socket=server.accept();//监听用户连接,有阻塞,多个呢?
    		receive("client connect");
    		
    		//相关IO流
            //in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //writer=new PrintWriter(socket.getOutputStream());//可以
            writer=socket.getOutputStream();//不可以
	    in = socket.getInputStream();//input ,output都要对应的才行,之前不行,因为没对应	
	
            
            //监听客户端数据
            String receive="";
	    byte[] byt=new byte[1024];
            while(!receive.equals("endiiafdoefda")){//解决客户端关闭时,服务端也关闭
            	//receive=in.readLine();//接收客户端数据
		in.read(byt);
            	receive=new String(byt);
            	receive(receive);
            }
    	}catch(Exception e) {//出错，打印出错信息
            System.out.println("Error."+e);
        }
    }
    /**
     * 发送数据
     * @param text
     */
    public void write(String text,String chartSet){
		    try {
				byte[] bytes=text.getBytes(chartSet);
				String txt=new String(bytes,chartSet);
				//writer.println(txt);
			        writer.write(text.getBytes());
				writer.flush();//刷新输出流，使Server马上收到该字符串
			    
			    

			    
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    /**关闭资源 */
    public void close(){
        try {
        	writer.close(); //关闭Socket输出流
            in.close(); //关闭Socket输入流
            socket.close(); //关闭Socket
            server.close(); //关闭ServerSocket
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
    }
}
