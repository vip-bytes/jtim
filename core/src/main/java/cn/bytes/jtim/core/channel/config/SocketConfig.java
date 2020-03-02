package cn.bytes.jtim.core.channel.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocketConfig {

    @Builder.Default
    private boolean noDelay = true;

    @Builder.Default
    private int sendBufferSize = 32 * 1024;

    @Builder.Default
    private int receiveBufferSize = 32 * 1024;

    @Builder.Default
    private boolean keepAlive = false;

    @Builder.Default
    private int acceptBackLog = 1024;

    @Builder.Default
    private int writeBufferWaterLow = 32 * 1024;

    @Builder.Default
    private int writeBufferWaterHigh = 64 * 1024;
}
