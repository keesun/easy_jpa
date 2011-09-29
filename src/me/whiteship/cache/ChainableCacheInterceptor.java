package me.whiteship.cache;

import com.sun.net.httpserver.Filter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class ChainableCacheInterceptor extends CacheInterceptor {

    List<ChainableCacheFilter> filterList;

    public List<ChainableCacheFilter> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<ChainableCacheFilter> filterList) {
        this.filterList = filterList;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if(filterList != null && !filterList.isEmpty()) {
            //전처리
            for(ChainableCacheFilter filter : filterList) {
                if(filter.wantToUseTheTargetMethod(invocation)) {
                    return invocation.proceed();
                }

                filter.preInvoke(invocation);
            }

            //스프링 인터셉터 실행
            Object result = super.invoke(invocation);

            //후처리
            //TODO: 여기서 역순으로 실행해야 하는건지.. 고민 중.
            for(ChainableCacheFilter filter : filterList) {
                filter.postInvoke(result, invocation);
            }

            return result;
        } else {
            return super.invoke(invocation);
        }
    }
}
