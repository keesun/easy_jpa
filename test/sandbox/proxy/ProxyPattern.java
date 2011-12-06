package sandbox.proxy;

public class ProxyPattern {

    static interface Service {
        void doSomething();
    }

    static class Target implements Service {
        public void doSomething() {
            System.out.println("shuffling");
        }
    }

    static class Proxy implements Service {
        Service target = new Target();

        public void doSomething() {
            System.out.print("Every day I'm ");
            target.doSomething();
        }
    }

    public static void main(String args[]) {
        Service proxy = new Proxy();
        proxy.doSomething();
    }

}
