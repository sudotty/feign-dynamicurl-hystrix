package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping("/test")
public class Test1 {
    @Resource
    TestService testService;


    /**
     * 普通的接口，睡眠时间返回调用的真实时间
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/demo1")
    public String test() throws InterruptedException {
        Thread.sleep(9000);
        return "ok";
    }

    /**
     * 远程调用测试，调用动态URL的时候，熔断测试是否生效
     *
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/test2")
    public String test2() throws URISyntaxException {
        log.info("熔断测试：testService.test() {}", testService.test());
        return "ok2";
    }

    /**
     * 远程调用测试，正常的feign接口，熔断测试是否生效
     *
     * @return
     */
    @GetMapping("/test4")
    public String test4() {
        log.info("熔断测试：testService.test() {}", testService.test4());
        return "ok2";
    }
}
