package sandbox.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: keesunbaik
 * Date: 11. 10. 19.
 * Time: 오후 1:43
 * To change this template use File | Settings | File Templates.
 */
public class LmfaoHandler implements InvocationHandler{

    Object target;

    public LmfaoHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.print("Everyday I'm ");
        return method.invoke(target, objects);
    }
}
