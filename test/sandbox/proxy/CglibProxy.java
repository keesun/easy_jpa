package sandbox.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CglibProxy {

    static interface Service {
        void doSomething();
    }

    static class Target implements Service {
        public void doSomething() {
            System.out.println("shuffling");
        }
    }

    static class LmafoInterceptor implements MethodInterceptor {

        Object target;

        public LmafoInterceptor(Object target) {
            this.target = target;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.print("Everyday I'm ");
            return method.invoke(target, args);
        }
    }

    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        cglibProxy.doIt();
    }

    public void doIt() {
        Service target = new Target();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new LmafoInterceptor(target));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Service proxy = (Service)enhancer.create();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

        proxy.doSomething();

        //Proxy API
        System.out.println(Proxy.isProxyClass(proxy.getClass()));

    }

}

