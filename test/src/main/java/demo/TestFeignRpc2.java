package demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "rpc-test2", url = "http://localhost:9999/test/demo1")
public interface TestFeignRpc2 {
    @GetMapping
    public String test();
}
