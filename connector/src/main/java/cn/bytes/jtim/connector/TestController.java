package cn.bytes.jtim.connector;

import cn.bytes.jtim.core.service.TestService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @date 2020/2/24 19:11
 */
@RestController
@RequestMapping("/v1")
public class TestController implements InitializingBean {

    @Reference
    private TestService testService;

    @RequestMapping("/vds")
    public String print() {
        System.out.println(testService);
        try {
            System.out.println(testService.sayHello("调用2") + "========>>.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "123";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(testService + "==>cg");
    }
}
