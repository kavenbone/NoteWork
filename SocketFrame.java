
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class SocketFrame {
  JFrame frame;
  String title="标题";
  
  TextField editIp;
  TextField editPort;
  TextArea textArea;
  
  public static void main(String[] args) throws IOException {
	  
	  final SocketFrame clientFrame=new SocketFrame();
	  clientFrame.init("socketClient");
	  SocketClient client=new SocketClient(){
		  @Override
		  public void receive(String text) {
    		  try {
    			  byte[] bys=text.getBytes("utf-8");
    			  clientFrame.addContext("server:"+new String(bys,"utf-8"));
    		  } catch (UnsupportedEncodingException e1) {
    			  // TODO Auto-generated catch block
    			  e1.printStackTrace();
    		  }
		  }
	  };
	  JFrame clientJfram=clientFrame.addMyView(client);
	  clientFrame.initIpPort("10.1.12.55", "5209");
	  clientJfram.setVisible(true);
	  
	  final SocketFrame serverFrame=new SocketFrame();
	  serverFrame.init("socketServer");
	  SocketService sever=new SocketService(){
		  @Override
		  public void receive(String text) {
			  try {
    			  byte[] bys=text.getBytes("utf-8");
    			  serverFrame.addContext("server:"+new String(bys,"utf-8"));
    		  } catch (UnsupportedEncodingException e1) {
    			  // TODO Auto-generated catch block
    			  e1.printStackTrace();
    		  }
		  }
	  };
	  JFrame severJfram=serverFrame.addMyView(sever);
	  serverFrame.initIpPort("10.1.12.55", "5209");
	  severJfram.setVisible(true);
	  
	  
  }
  
	public SocketFrame(){
		this.frame=new JFrame();
	}
  //Arguments标签下的VM arguments:-Dfile.encoding=GB18030
  public void init(String title){
		frame.setLayout(new FlowLayout());
		//设置标题
		frame.setTitle(title);
	    //设置大小可更改
	    frame.setResizable( true );
	    //设置大小
	    frame.setSize(300,100);
	    //设置大小及窗口顶点位置
	    frame.setBounds(10,10,500,700);
	    //显示窗口
	    frame.setVisible(true);	  
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	}
  public void initIpPort(String ip,String port){
	  editIp.setText(ip);
	  editPort.setText(port);
  }
  public JFrame addMyView(final SocketFace socketFace){
      Label labelIP=new Label("ip:");//IP
	  frame.add(labelIP);
	  editIp=new TextField("",20);
      frame.add(editIp);
      Label labelPort=new Label("port:");//port
	  frame.add(labelPort);
	  editPort=new TextField("",20);
      frame.add(editPort);
      
      //添加相关的按钮:连接,关闭
      Button connect = new Button("connect");  
      connect.addActionListener(new ActionListener(){
    	  @Override
    	  public void actionPerformed(ActionEvent e) {
    		  socketFace.connect(editIp.getText(), Integer.parseInt(editPort.getText()));
    	  }
      });
      frame.add(connect);
      Button close = new Button("close");//关闭  
      close.addActionListener(new ActionListener(){
    	  @Override
    	  public void actionPerformed(ActionEvent e) {
    		  socketFace.close();
    	  }
      });
      frame.add(close);
      
      //发送
      final TextField edit=new TextField("",20);
      frame.add(edit);
      Button send = new Button("send");//关闭  
      send.addActionListener(new ActionListener(){
    	  @Override
    	  public void actionPerformed(ActionEvent e) {
    		  socketFace.write(edit.getText(),"utf-8");
    	  }
      });
      frame.add(send);
      
      //显示数据
      textArea=new TextArea("");
      textArea.setSize(450, 100);
      textArea.setEditable(false);
      frame.add(textArea);
      
      return frame;
  }
  
  public void addContext(String text){
	  String str=textArea.getText();
	  textArea.setText(str+text+"\n");
  }
}
