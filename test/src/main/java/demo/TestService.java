package demo;

import feign.Feign;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hystrix.SetterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;

@Component
@Import(FeignClientsConfiguration.class)
@Slf4j
public class TestService {
    TestFeignRpc testFeignRpc;
    @Resource
    TestFeignRpc2 testFeignRpc2;

    public TestService(Encoder encoder, Decoder decoder) {
        testFeignRpc = Feign.builder()
                .invocationHandlerFactory((target, dispatch) -> new HystrixInvocationHandler(target, dispatch, new SetterFactory.Default(), null, new InvocationRuntimeSetterFactory.Default()))
                .encoder(encoder).decoder(decoder)
                .target(Target.EmptyTarget.create(TestFeignRpc.class));
    }

    /**
     * 走的是动态URL请求，动态构建的feign
     *
     * @return
     * @throws URISyntaxException
     */
    public String test() throws URISyntaxException {
        return testFeignRpc.test(new URI("http://localhost:9999/test/demo1"));
    }

    public String test4() {
        return testFeignRpc2.test();
    }
}
