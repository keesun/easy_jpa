package me.whiteship.common.jpa.comment.service;


import me.whiteship.common.jpa.comment.domain.QueryComment;

public interface QueryCommentService {
	
	String convertToString(QueryComment comment) throws QeuryCommentException;

	QueryComment convertToComment(String commentString) throws QeuryCommentException;

	String applyComment(String sql, QueryComment comment);

    String makeResultSqlWith(String sql);

}
