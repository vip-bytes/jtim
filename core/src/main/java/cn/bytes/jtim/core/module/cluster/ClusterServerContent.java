package cn.bytes.jtim.core.module.cluster;

import lombok.*;

import java.io.Serializable;

/**
 * @version 1.0
 * @date 2020/2/18 14:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = {"host", "port", "tag"})
public class ClusterServerContent implements Serializable {

    private String host;

    private int port;

    private String tag;

    private long upTime = System.currentTimeMillis();

}
