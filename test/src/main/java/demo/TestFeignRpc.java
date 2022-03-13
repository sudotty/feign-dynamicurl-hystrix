package demo;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.net.URI;

@FeignClient(name = "rpc-test", url = "http://localhost:9999/test/demo1")
public interface TestFeignRpc {
    @RequestLine("GET")
    public String test(URI uri);
}
