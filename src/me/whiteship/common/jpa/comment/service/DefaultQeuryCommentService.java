package me.whiteship.common.jpa.comment.service;

import me.whiteship.common.jpa.comment.domain.QueryComment;
import me.whiteship.common.jpa.comment.domain.QueryHint;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultQeuryCommentService implements QueryCommentService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String SELECT = "select";

    ObjectMapper mapper = new ObjectMapper();

    @Autowired(required = false)
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String convertToString(QueryComment comment)
			throws QeuryCommentException {
		String commentJSON = null;
		try {
			commentJSON = mapper.writeValueAsString(comment);
		} catch (Exception e) {
			throw new QeuryCommentException(e);
		}
		return commentJSON;
	}

	public QueryComment convertToComment(String commentString)
			throws QeuryCommentException {
		QueryComment comment = null;
		try {
			comment = mapper.readValue(commentString, QueryComment.class);
		} catch (Exception e) {
			throw new QeuryCommentException(e);
		}
		return comment ;
	}

	public String applyComment(String sql, QueryComment comment) {
		StringBuilder result = new StringBuilder();
		if(!comment.getQueryId().isEmpty()){
			result.append(QueryComment.PRFIX);
			result.append(comment.getQueryId());
			result.append(QueryComment.SUFFIX);
            result.append(" ");
		}
		
		List<QueryHint> hints = comment.getHintList();

		if(hints.size() > 0) {
			List<Integer> selectPositions = getSelectPositions(sql);

			for(QueryHint hint : comment.getHintList()){
				int position = hint.getPosition();
				int selectPosition = selectPositions.get(position);
				
				String headSql = sql.substring(0, selectPosition);
				String restSql = sql.substring(selectPosition + 6);

				StringBuilder middelSqlBuilder = new StringBuilder(SELECT);
				middelSqlBuilder.append(" ");
				middelSqlBuilder.append(hint.getHint());
				sql = headSql + middelSqlBuilder.toString() + restSql;
				selectPositions = getSelectPositions(sql);
			}
		}
				
		result.append(sql);
		logger.debug(result.toString());
		return result.toString();
	}

    public String makeResultSqlWith(String sql) {
        if(!sql.contains("\"queryId\"")) {
            return sql;
        }
        String commentString = sql.substring(3, sql.lastIndexOf("*/") - 1);
        String sqlBody = sql.substring(sql.lastIndexOf("*/") + 3);
        QueryComment comment = convertToComment(commentString);
		return applyComment(sqlBody, comment);
    }

    protected List<Integer> getSelectPositions(String sql) {
		List<Integer> selectPositions = new ArrayList<Integer>();
		Matcher matcher = Pattern.compile("select").matcher(sql);
		while(matcher.find()){
			selectPositions.add(matcher.start());
		}
		return selectPositions;
	}

}
