package me.whiteship.modules.member;


import java.util.ArrayList;
import java.util.List;

import me.whiteship.domain.Member;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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
	
	@Test
	public void getMemberCache(){
        Cache memberCache = cacheManager.getCache("member");

		service.getAMember(1);
		assertThat(memberCache.get(1), is(notNullValue()));
		
		service.getAMember(2);
		assertThat(memberCache.get(2), is(notNullValue()));

		service.getAMember(3);
		assertThat(memberCache.get(3), is(notNullValue()));
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
    public void objectGraph(){
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

	
}
