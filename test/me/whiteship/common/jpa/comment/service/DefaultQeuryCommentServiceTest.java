package me.whiteship.common.jpa.comment.service;

import me.whiteship.common.jpa.comment.domain.QueryComment;
import me.whiteship.common.jpa.comment.domain.QueryHint;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by IntelliJ IDEA.
 * User: keesun
 * Date: 11. 10. 13
 * Time: 오후 5:56
 * To change this template use File | Settings | File Templates.
 */
public class DefaultQeuryCommentServiceTest {

	DefaultQeuryCommentService service;

	@Before
	public void setUp(){
		service = new DefaultQeuryCommentService();
        service.mapper = new ObjectMapper();
	}

	@Test
	public void applyComment(){
		// Given
		String sql = "select from users";
		QueryComment comment = new QueryComment("queryId");
		comment.addHint(new QueryHint(0, "hint hint"));

		// When
		String result = service.applyComment(sql, comment);

		// Then
		assertThat(result, is("/*queryId*/ select /*hint hint*/ from users"));
	}

	@Test
	public void applyCommentWithMultipleSelect(){
		// Given
		String sql = "select u.name from (select user as u from item_transaction)";
		QueryComment comment = new QueryComment("queryId");
		comment.addHint(new QueryHint(0, "hint hint"));

		// When
		String result = service.applyComment(sql, comment);

		// Then
		assertThat(result, is("/*queryId*/ select /*hint hint*/ u.name from (select user as u from item_transaction)"));
	}

	@Test
	public void applyCommentAtSecondPositionWithMultipleSelect(){
		// Given
		String sql = "select u.name from (select user as u from item_transaction)";
		QueryComment comment = new QueryComment("queryId");
		comment.addHint(new QueryHint(1, "hint hint"));

		// When
		String result = service.applyComment(sql, comment);

		// Then
		assertThat(result, is("/*queryId*/ select u.name from (select /*hint hint*/ user as u from item_transaction)"));
	}

	@Test
	public void applyCommentAtMutiplePositionWithMultipleSelect(){
		// Given
		String sql = "select u.name from (select user as u from item_transaction)";
		QueryComment comment = new QueryComment("queryId");
		comment.addHint(new QueryHint(0, "hint0 hint0"));
		comment.addHint(new QueryHint(1, "hint1 hint1"));

        System.out.println(service.convertToString(comment));

		// When
		String result = service.applyComment(sql, comment);

		// Then
		assertThat(result, is("/*queryId*/ select /*hint0 hint0*/ u.name from (select /*hint1 hint1*/ user as u from item_transaction)"));
	}

}
