package me.whiteship.common.jpa.comment.domain;

import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;

import java.util.ArrayList;
import java.util.List;

public class QueryComment {

    public static final String PRFIX = "/*";
    public static final String SUFFIX = "*/";

	String queryId;

	List<QueryHint> hintList;
	
	public QueryComment() {
	}

	public QueryComment(String queryId) {
		this.queryId = queryId;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public List<QueryHint> getHintList() {
		if(this.hintList == null)
			this.hintList = new ArrayList<QueryHint>();
		return hintList;
	}

	public void setHintList(List<QueryHint> hintList) {
		this.hintList = hintList;
	}

	public QueryComment addHint(QueryHint hint) {
		getHintList().add(hint);
        return this;
	}

    public QueryComment addHint(int index, String hint) {
        addHint(new QueryHint(index, hint));
        return this;
    }
}
