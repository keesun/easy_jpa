package sandbox.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: keesunbaik
 * Date: 11. 10. 19.
 * Time: 오후 1:52
 * To change this template use File | Settings | File Templates.
 */
public class LmfacoInteceptor implements MethodInterceptor{

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.print("Everyday I'm ");
        return methodProxy.invokeSuper(o, objects);
    }
}
