package cn.bytes.jtim.common.enums;

/**
 * @version 1.0
 * @date 2020/2/12 0:06
 */
public enum ConnectionSource {
    PC,ANDROID,IOS,JAVA_CLIENT,GOLANG_CLIENT,OTHER;


    public static ConnectionSource mapping(String name) {
        ConnectionSource[] connectionSources = ConnectionSource.values();
        for(ConnectionSource connectionSource: connectionSources) {
            if(connectionSource.name().equalsIgnoreCase(name)) {
                return connectionSource;
            }
        }
        return OTHER;
    }

}
