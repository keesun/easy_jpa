package sandbox.proxy;

/**
 * Created by IntelliJ IDEA.
 * User: keesunbaik
 * Date: 11. 10. 19.
 * Time: 오후 1:34
 * To change this template use File | Settings | File Templates.
 */
public class RealDoIt implements DoIt {

    public void doSomething() {
        System.out.println("Shuffling");
    }
}
