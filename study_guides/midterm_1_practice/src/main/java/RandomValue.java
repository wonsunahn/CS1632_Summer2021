

import java.util.Random;

public class RandomValue {
	int val = 0;
	Random rand = new Random();

	public void incVal() {
		val += rand.nextInt(2);
	}

	public int getVal() {
		return val;
	}
}