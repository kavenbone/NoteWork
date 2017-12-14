

/**
 * 防火墙,close()才完成write,PrintWrite比outputStream好,自动断连接
 * 乱码要能设置编辑
 *
 */
public interface SocketFace {
	
    
    /**
     * 创建连接
     * connect("10.1.12.55", 5209);
     * @param ip 服务端无用
     * @param port
     */
    public void connect(String ip,Integer port);
    /**
     * 发送数据
     * @param text
     */
    public void write(String text,String chartSet);
    /**关闭资源 */
    public void close();
}


