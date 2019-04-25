package cmpGenerator;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class textForUpdateOrder {

	static ClassPathXmlApplicationContext context;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context=new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		context.close();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
