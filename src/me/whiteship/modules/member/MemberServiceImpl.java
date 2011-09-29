package me.whiteship.modules.member;

import java.util.ArrayList;
import java.util.List;

import me.whiteship.domain.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	List<Member> testMembers;

	public MemberServiceImpl() {
		testMembers = new ArrayList<Member>();
		
		Member keesun = new Member();
		keesun.setName("ks");
		testMembers.add(keesun);
		Member uzin = new Member();
		uzin.setName("uz");
		testMembers.add(uzin);
	}

	@Autowired MemberRepository repository;

	@Cacheable("member")
	public Member getAMember(Integer id) {
        System.out.println("called!");
		return repository.findOne(id);
	}

	@Cacheable("memberList")
	public List<Member> getList(String keyword) {
		return testMembers;
	}

	@CacheEvict("memberList")
	public void remove(String name) {
		int index = 0;
		for(Member member : testMembers) {
			if(member.getName().equals(name)){
				index = testMembers.indexOf(member);
			}
		}
		testMembers.remove(index);
	}

    public void addMember(Member member) {
        repository.save(member);
    }

    public Member nonCachingMember(int i){
        Member member = new Member();
        member.setId(i);
        member.setName("ks");
        return member;
    }

}
