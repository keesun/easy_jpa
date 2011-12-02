package sandbox.proxy;

import net.sf.cglib.proxy.Enhancer;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by IntelliJ IDEA.
 * User: keesunbaik
 * Date: 11. 10. 19.
 * Time: 오후 1:37
 * To change this template use File | Settings | File Templates.
 */
public class DoItTest {

    @Test
    @Ignore
    public void doit(){
        DoIt doIt = new ProxyDoIt(new RealDoIt());
        doIt.doSomething();
    }

    @Test
    @Ignore
    public void doItWithPorxy(){
        LmfaoHandler lh = new LmfaoHandler(new RealDoIt());
        DoIt doIt = (DoIt) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{DoIt.class}, lh);
        doIt.doSomething();
    }

    @Test
    public void doItWithCGLib(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealDoIt.class);
        enhancer.setCallback(new LmfacoInteceptor());
        DoIt doIt = (DoIt) enhancer.create();
        doIt.doSomething();
    }
}
