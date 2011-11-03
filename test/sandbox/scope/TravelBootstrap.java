package sandbox.scope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TravelBootstrap {

	public static void main(String... args) {
		ApplicationContext applicationContext =
			new ClassPathXmlApplicationContext("sandbox/scope/applicationContext.xml");

		TravelService travelService =
			applicationContext.getBean(TravelService.class);

		travelService.printNextDestination();
		travelService.printNextDestination();
		travelService.printNextDestination();
		travelService.printNextDestination();
	}
}
