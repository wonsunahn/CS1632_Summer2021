import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.*;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class QuickCheckTest {
	@Property
	public void testAdd(@InRange(minInt = -100, maxInt = 100) int x, @InRange(minInt = -100, maxInt = 100) int y) {
		int ret = IntegerOps.add(x, y);
		// TODO: Fill in
	}
	
	@Property
	public void testSquare(@InRange(minInt = -100, maxInt = 100) int x) {
		int ret = IntegerOps.square(x);
		// TODO: Fill in
	}
	
}