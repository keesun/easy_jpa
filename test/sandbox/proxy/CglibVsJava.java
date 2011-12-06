package sandbox.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CglibVsJava {

    static interface Service {
        void doSomething();
    }

    static class Target implements Service {
        public void doSomething() {
            System.out.println("shuffling");
        }
    }

    static class LmafoInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.print("Everyday I'm ");
            return proxy.invokeSuper(obj, args);
        }
    }

    static class LmafoHandler implements InvocationHandler {

        Object target;

        public LmafoHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            System.out.print("Evenry day I'm");
            return method.invoke(target, objects);
        }
    }

    @Test
    public void java(){
        Service target = new Target();
        LmafoHandler handler = new LmafoHandler(target);

        StopWatch stopWatch = new StopWatch("java proxy");
        stopWatch.start("proxy creation");
        Service proxy = (Service) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Service.class}, handler);
        stopWatch.stop();

        stopWatch.start("call proxied method");
        int i = 0;
        for(; i < 1000 ; i++) {
            proxy.doSomething();
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    public void cglib(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallback(new LmafoInterceptor());

        StopWatch stopWatch = new StopWatch("cglib");
        stopWatch.start("proxy creation");
        Service proxy = (Service)enhancer.create();
        stopWatch.stop();

        stopWatch.start("call proxied method");
        int i = 0;
        for(; i < 1000 ; i++) {
            proxy.doSomething();
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

}
