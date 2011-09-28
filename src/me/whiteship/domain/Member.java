package me.whiteship.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Member> familly;

    public Member() {
        this.familly = new ArrayList<Member>();

    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public List<Member> getFamilly() {
        return familly;
    }

    public void setFamilly(List<Member> familly) {
        this.familly = familly;
    }

    public void addFamilly(Member member) {
        this.getFamilly().add(member);
    }
}
