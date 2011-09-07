package sandbox;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SampleTest {
	
	@Autowired SampleBean bean;
	
	@Autowired Environment env;
	
	@Test
	public void di(){
		assertThat(bean.getName(), is("keesun"));
		
		String name = env.getProperty("my.name");
		assertThat(name, is("keesun"));
	}

}
