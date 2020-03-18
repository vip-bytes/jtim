package cn.bytes.jtim.connector.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author maliang@sioniov.com
 * @version 1.0
 * @date 2020/3/18 16:27
 */
@Slf4j
public class ConfigTest {

    @Test
    public void osName() {
        final String osName = System.getProperty("os.name");
        System.out.println("==?");
        log.info("osName = {} isLinux = {}", osName, osName.toLowerCase().contains("linux"));
    }
}
