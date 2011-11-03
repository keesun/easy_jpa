package sandbox.scope;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;


/**
 * Created by IntelliJ IDEA.
 * User: keesun
 * Date: 11. 10. 24
 * Time: 오전 11:15
 * To change this template use File | Settings | File Templates.
 */
public class TravelServiceImpl implements TravelService {
	private Logger logger = LoggerFactory.getLogger(TravelServiceImpl.class);

	private DestinationPicker destinationPicker;

	@Async
	public void printNextDestination() {
		logger.info("Running from thread: " + Thread.currentThread().getId());

		System.out.println("Your next holiday destination: " +
							destinationPicker.getDestination());
	}

	public void setDestinationPicker(DestinationPicker destinationPicker) {
		this.destinationPicker = destinationPicker;
	}
}