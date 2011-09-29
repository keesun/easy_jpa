package me.whiteship.cache;

import org.aopalliance.intercept.MethodInvocation;

public class IgnoreCacheFilter implements ChainableCacheFilter {

    @Override
    public boolean wantToUseTheTargetMethod(MethodInvocation invocation) {
        return true;
    }

    @Override
    public void preInvoke(MethodInvocation invocation) {

    }

    @Override
    public void postInvoke(Object result, MethodInvocation invocation) {

    }
}
