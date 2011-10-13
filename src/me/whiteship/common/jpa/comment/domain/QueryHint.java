package me.whiteship.common.jpa.comment.domain;

public class QueryHint {
	
	int position;
	
	String hint;

	public QueryHint() {
	}
	
	public QueryHint(int position, String hint) {
		this.position = position;
		this.hint = QueryComment.PRFIX + hint + QueryComment.SUFFIX;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

}
