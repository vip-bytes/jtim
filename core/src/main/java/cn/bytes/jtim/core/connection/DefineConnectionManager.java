//package cn.bytes.jtim.core.connection;
//
//import cn.bytes.jtim.core.module.Module;
//import io.netty.channel.Channel;
//
//import java.util.Map;
//
///**
// * @version 1.0
// * @date 2020/2/12 0:10
// */
//public interface DefineConnectionManager extends Map<String, Connection>, Module {
//
//    /**
//     * 保存连接信息
//     */
//    DefineConnectionManager saveConnection(Connection connection);
//
//    DefineConnectionManager removeConnection(Connection connection);
//
//    DefineConnectionManager removeConnection(Channel channel);
//
//    int onLine();
//
//    /**
//     * 根据连接编号写入数据
//     *
//     * @param body
//     */
//    DefineConnectionManager writeAndFlush(String channelId, Object body);
//
//    DefineConnectionManager writeAndFlush(Connection connection, Object body);
//
//}
