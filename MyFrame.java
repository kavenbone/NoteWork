
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class MyFrame {
  JFrame frame;
	public MyFrame(JFrame frame){
		this.frame=frame;
		init();
	}
  //Arguments标签下的VM arguments:-Dfile.encoding=GB18030
  public void init(){
		frame.setLayout(new FlowLayout());
		//设置标题
		frame.setTitle( "Hello, my first frame" );
	    //设置大小可更改
	    frame.setResizable( true );
	    //设置大小
	    frame.setSize(300,100);
	    //设置大小及窗口顶点位置
	    frame.setBounds(10,10,500,700);
	    //设置背景颜色
	    frame.setBackground(Color.blue);
	    //显示窗口
	    frame.setVisible(true);	  
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	}

  public void addMyView(){
      TextArea label=new TextArea("labelStr");
	    label.setSize(450, 100);
	    label.setEditable(false);
	    frame.add(label);
      
      button = new Button("submit");  
      button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				  submit();
			}
        	
      });
      frame.add(button); 
      
      result=new TextField("",20);
      frame.add(result);
      
      frame.setVisible(true);
  }
