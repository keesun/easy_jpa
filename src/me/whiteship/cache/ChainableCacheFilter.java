package me.whiteship.cache;

import org.aopalliance.intercept.MethodInvocation;

public interface ChainableCacheFilter {

    /**
     * true를 리턴하면 캐시 인터셉터를 사용하지 않고, 곧 바로 타겟 메서드만 실행합니다.
     * 해당 필터 이후의 필터가 적용되지 않으며, 캐시 전처리, 후처리 메서드 역시 적용되지 않습니다.
     * @return 캐시 인터셉터 사용을 중단하고 싶다면, true. 계속해서 캐시 인터셉터와 필터를 적용하고 싶다면 false.
     * @param invocation @see(org.aopalliance.intercept.MethodInvocation)
     */
    boolean wantToUseTheTargetMethod(MethodInvocation invocation);

    /**
     * 스프링이 제공하는 캐시 인터셉터를 실행하기 이전에 필요한 작업이 있다면 이곳에서 합니다.
     * @param invocation @see(org.aopalliance.intercept.MethodInvocation)
     */
    void preInvoke(MethodInvocation invocation);

    /**
     * 스프링이 제공하는 캐시 인터셉터를 실행한 이후에 필요한 작업이 있다면 이곳에서 합니다.
     * @param result 스프링 캐시 인터셉터의 호출 결과
     * @param invocation @see(org.aopalliance.intercept.MethodInvocation)
     */
    void postInvoke(Object result, MethodInvocation invocation);
}
