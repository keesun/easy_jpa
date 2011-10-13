package me.whiteship.common.jpa.comment;

import me.whiteship.common.jpa.comment.domain.QueryComment;
import me.whiteship.common.jpa.comment.service.DefaultQeuryCommentService;
import me.whiteship.common.jpa.comment.service.QueryCommentService;
import org.hibernate.EmptyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class QueryHintInterceptor extends EmptyInterceptor {
	
    QueryCommentService service = new DefaultQeuryCommentService();
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void setService(QueryCommentService service) {
		this.service = service;
	}

	@Override
	public String onPrepareStatement(String sql) {
        String resultSql = service.makeResultSqlWith(sql);
		logger.debug(resultSql);
        return super.onPrepareStatement(resultSql);
	}

}
