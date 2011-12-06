package sandbox.proxy;

import org.springframework.util.StopWatch;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JavaProxy {

    interface Service {
        void doSomething();
    }

    class Target implements Service {
        public void doSomething() {
            System.out.println("shuffling");
        }
    }

    class LmafoHandler implements InvocationHandler {

        Object target;

        public LmafoHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            System.out.println("Evenry day I'm");
            return method.invoke(target, objects);
        }
    }

    public static void main(String[] args) {
        JavaProxy javaProxy = new JavaProxy();
        javaProxy.doIt();
    }

    public void doIt() {
        Service target = new Target();
        LmafoHandler handler = new LmafoHandler(target);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Service proxy = (Service) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Service.class}, handler);
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());

        proxy.doSomething();

        //Proxy API
        System.out.println(Proxy.isProxyClass(proxy.getClass()));

    }


}
