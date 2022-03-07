package myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * Test Spring services
 */
@SpringBootTest
public class TestMyApp {

	@Autowired
	ApplicationContext context;

	@Autowired
	IHello helloByService;

	@Resource(name = "helloService")
	IHello helloByName;
	
	@Autowired
	String bye;



	@Test
	public void testHelloService() {
		assertTrue(helloByService instanceof HelloService);
		helloByService.hello();
	}

	@Test
	public void testHelloByName() {
		assertEquals(helloByService, helloByName);
	}

	@Test
	public void testHelloByContext() {
		assertEquals(helloByName, context.getBean(IHello.class));
		assertEquals(helloByName, context.getBean("helloService"));
	}

	@Test
	public void testBye() {
		assertEquals(bye, "Bye.");
	}

	/*========== Test service Logger ===========*/
	@Autowired
	ILogger loggerByService;
	@Resource(name = "stderrLogger")
	ILogger loggerByName;

	@Test
	public void testLoggerService(){
		assertTrue(loggerByService instanceof StderrLogger);
		loggerByService.log("Hello, World...");

	}
	@Test
	public void testLoggerByName(){
		assertEquals(loggerByService, loggerByName);
	}
	@Test
	public void testLoggerByContext() {
		assertEquals(loggerByName, context.getBean(ILogger.class));
		assertEquals(loggerByName, context.getBean("stderrLogger"));
	}

	/*====== Test fileLoggerWithConstructor =======*/
	@Autowired
	@Qualifier("fileLoggerWithConstructor")
	ILogger fileLoggerWithConstructor;
	@Test
	public void testFileLoggerWithConstructor(){
		assertTrue(fileLoggerWithConstructor instanceof FileLogger);
		fileLoggerWithConstructor.log("Hello, from fileLogger");
	}
	@Test
	public void testFileLoggerWithConstructorByContext() {
		assertEquals(fileLoggerWithConstructor, context.getBean("fileLoggerWithConstructor"));
	}

	/*========= Test BeanFileLogger ========= */
	@Autowired
	@Qualifier("beanFileLogger")
	ILogger beanFileLogger;
	@Test
	public void testBeanFileLogger(){
		assertTrue(beanFileLogger instanceof BeanFileLogger);
		//beanFileLogger.log("beanFile");
	}
	@Test
	public void testBeanFileLoggerByContext() {
		assertEquals(beanFileLogger, context.getBean("beanFileLogger"));
	}


}
