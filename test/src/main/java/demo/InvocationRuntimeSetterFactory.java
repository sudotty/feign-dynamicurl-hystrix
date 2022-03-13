package demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import feign.Feign;
import feign.Target;

import java.lang.reflect.Method;
import java.net.URI;

/**
 * 用来控制 hystrix 运行时的参数配置，可自定义
 *
 * @author ying.chen2
 * @date 2020/1/13
 */
public interface InvocationRuntimeSetterFactory {

  /**
   * 创建 hystrix Setter
   * @param target
   * @param proxy
   * @param method
   * @param args
   * @return
   * @thows
   * @author ying.chen2
   * @date 2020/1/13
   */
  HystrixCommand.Setter create(final Target<?> target, final Object proxy, final Method method, final Object[] args);


  final class Default implements InvocationRuntimeSetterFactory {

    @Override
    public HystrixCommand.Setter create(final Target<?> target, final Object proxy, final Method method, final Object[] args) {
      String groupKey = target.name();
      String commandKey = Feign.configKey(target.type(), method);

      Object anyUri = null;
      if(args != null && args.length > 0) {
        for (Object arg : args) {
          if(arg instanceof URI) {
            anyUri = arg;
          }
        }
      }

      if (anyUri != null) {
        URI uri = (URI) anyUri;
        groupKey = groupKey + "#" + uri.toString();
        commandKey = commandKey + "#" + uri.toString();
      }

      return HystrixCommand.Setter
              .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
              .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey));
    }
  }
}