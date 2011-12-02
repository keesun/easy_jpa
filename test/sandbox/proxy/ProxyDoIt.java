package sandbox.proxy;

/**
 * Created by IntelliJ IDEA.
 * User: keesunbaik
 * Date: 11. 10. 19.
 * Time: 오후 1:37
 * To change this template use File | Settings | File Templates.
 */
public class ProxyDoIt implements DoIt{

    DoIt realDoIt;

    public ProxyDoIt(DoIt doIt) {
        realDoIt = doIt;
    }

    @Override
    public void doSomething() {
        System.out.print("Everyday I'm ");
        realDoIt.doSomething();
    }

}
