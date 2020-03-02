package cn.bytes.jtim.core.channel.module.task;

import cn.bytes.jtim.core.module.connection.Connection;
import cn.bytes.jtim.core.module.connection.ConnectionModule;
import cn.bytes.jtim.core.protocol.protobuf.HeartbeatRequest;
import cn.bytes.jtim.core.protocol.protobuf.Message;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Objects;

/**
 * @version 1.0
 * @date 2020/2/20 22:24
 */
@Slf4j
public class SimpleHeartbeatRunTaskModule extends AbstractRunTaskModule implements Runnable {

    public SimpleHeartbeatRunTaskModule() {
        getScheduledThreadPoolExecutor().scheduleWithFixedDelay(this, getDelay(), getDelay(), getTimeUnit());
    }

    @Override
    public void run() {
        try {

            ConnectionModule connectionModule = getHost();
            if (Objects.isNull(connectionModule)) {
                return;
            }
            Collection<Connection> connections = connectionModule.getConnections();
            if (Objects.isNull(connections) || connections.size() <= 0) {
                return;
            }
            final int heartTime = getConfiguration().getHeartbeatTime();
            connections.parallelStream().forEach(connection -> {
                final long lastWriteTime = connection.getLastWriteTime();
                final long nowTime = System.currentTimeMillis();
                if ((nowTime - lastWriteTime) / 1000 >= heartTime) {
                    connectionModule.writeAndFlush(connection, Message.newBuilder()
                            .setCmd(Message.Cmd.HeartbeatRequest)
                            .setHeartbeatRequest(HeartbeatRequest.newBuilder().setPing(ByteString.copyFrom("1", Charset.defaultCharset())).build())
                            .build());
                    connection.setLastWriteTime(nowTime);
                }
            });
        } catch (Exception e) {
            log.error("心跳发送错误: {}", e);
        }
    }
}
