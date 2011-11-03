package sandbox.scope;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: keesun
 * Date: 11. 10. 24
 * Time: 오전 11:17
 * To change this template use File | Settings | File Templates.
 */
public class RandomDestinationPicker implements DestinationPicker {

	private static int instances = 0;

	private int id;
	private List<String> availableDestinations;

	public RandomDestinationPicker(List<String> availableDestinations) {
		this.availableDestinations = availableDestinations;
		id = instances++;
	}

	public String getDestination() {
		return availableDestinations.get(id % availableDestinations.size());
	}
}
