package cn.bytes.jtim.logic;

import cn.bytes.jtim.core.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @version 1.0
 * @date 2020/2/24 20:35
 */
@RestController
@RequestMapping("/v1")
public class TestCon {

//    private final TestService testService;
//
//    public TestCon(TestService testService) {
//        this.testService = testService;
//    }
//
//    @RequestMapping("/vds")
//    public String print() {
//
//
//        System.out.println(testService.sayHello("调用2") + "========>>.");
//        return "123";
//    }

    @PostConstruct
    public void print() {
        System.out.println("gs:  " + groupService);
    }

    @Autowired
    private GroupService groupService;
}
