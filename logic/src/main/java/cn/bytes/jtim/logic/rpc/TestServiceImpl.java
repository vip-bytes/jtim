package cn.bytes.jtim.logic.rpc;

import cn.bytes.jtim.core.service.TestService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @version 1.0
 * @date 2020/2/24 17:46
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String sayHello(String name) {

        int i = 1 / 0;

        return

                "Dubbo : " + name;
    }
}
