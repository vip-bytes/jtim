package cn.bytes.jtim.test;

import cn.bytes.jtim.core.client.NettyTcpClient;
import cn.bytes.jtim.core.config.Configuration;

/**
 * @author maliang@sioniov.com
 * @version 1.0
 * @date 2020/2/13 22:16
 */
public class NettyTcpClientTest {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setHost("127.0.0.1");
        configuration.setPort(1999);

        NettyTcpClient nettyTcpClient = new NettyTcpClient(configuration);
        nettyTcpClient.open();
    }
}
