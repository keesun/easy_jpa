package me.whiteship.modules.member;


import me.whiteship.domain.Member;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class MemberServiceImplTest {
	
	@Autowired MemberService service;
    @Autowired CacheManager cacheManager;
    @Autowired ApplicationContext context;

	@Test
	public void getMemberCache(){
//        for(String name : context.getBeanDefinitionNames()) {
//            System.out.println("=======================");
//            System.out.println(context.getBean(name));
//        }

        Cache memberCache = cacheManager.getCache("member");

        Member me = new Member();
        me.setName("ks");
        service.addMember(me);

		service.getAMember(me.getId());
        assertThat(memberCache.get(me.getId()), is(notNullValue()));
		service.getAMember(me.getId());
        service.getAMember(me.getId());

//		service.getAMember(2);
//		assertThat(memberCache.get(2), is(notNullValue()));
//
//		service.getAMember(3);
//		assertThat(memberCache.get(3), is(notNullValue()));


	}
	
	@Test
    public void getListCache(){
        Cache memberListCache = cacheManager.getCache("memberList");

		service.getList("ks");
		assertThat(memberListCache.get("ks"), is(notNullValue()));
		
		service.getList("uz");
		assertThat(memberListCache.get("uz"), is(notNullValue()));
	}
	
	@Test
	public void getListCacheWithDelete(){
        Cache memberListCache = cacheManager.getCache("memberList");

		service.getList("ks");
        assertThat(memberListCache.get("ks"), is(notNullValue()));

		service.remove("ks");
        assertThat(memberListCache.get("ks"), is(nullValue()));

		service.getList("uz");
		assertThat(memberListCache.get("uz"), is(notNullValue()));
	}

    @Test(expected = LazyInitializationException.class)
    public void lazyInitializationException(){
        Member me = new Member();
        me.setName("ks");
        Member wife = new Member();
        wife.setName("uz");
        me.addFamilly(wife);

        service.addMember(me);
        assertThat(me.getId(), is(notNullValue()));

        service.getAMember(me.getId());

        Member cachedMember = (Member) cacheManager.getCache("member").get(me.getId()).get();

        //LazyInitializationException
        assertThat(cachedMember.getFamilly().get(0).getName(), is("uz"));
    }

    @Test
    public void imNotUsingCache(){
        Cache memberCache = cacheManager.getCache("member");

        Member member1 = service.nonCachingMember(100);
        assertThat(memberCache.get(100), is(nullValue()));

        Member member2 = service.nonCachingMember(100);
        Member member3 = service.nonCachingMember(100);

    }

	
}
