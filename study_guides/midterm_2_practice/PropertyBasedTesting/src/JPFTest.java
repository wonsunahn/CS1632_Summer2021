import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import gov.nasa.jpf.vm.Verify;

public class JPFTest {
	private static int x;
	private static int y;
	
	@BeforeClass
	public static void setUp() {
		x = Verify.getInt(-10, 10);
		y = Verify.getInt(-10, 10);
	}
	
	@Test
	public void testAdd() {
		int ret = IntegerOps.add(x, y);
		assertEquals(y, ret - x);
		if(x > 0 && y > 0) {
			if(x <= Integer.MAX_VALUE/2 && y <= Integer.MAX_VALUE/2) {
				assertTrue(ret > 0);
			}
		}
		if(x > 0 && y > 0) {
			if(ret > 0) {
				assertTrue(ret > x && ret > y);
			}
		}
		if(x == 0) { // identity property of zero for addition
			assertEquals(y, ret);
		}
		assertEquals(y + x, ret); // commutative property
	}
	
	@Test
	public void testSquare() {
		int ret = IntegerOps.square(x);
		// Invariant: the square root of a value is always positive or zero.
		assertTrue(ret >= 0);
		// Invariant: the square root of a value is greater than the given value if it is larger than 1.  Otherwise, it is less than. 
		assertTrue(ret >= x);
		// Invariant: the squared of a square root is equal to the original value (+- some epsilon)
	    if(x >= 0) assertEquals((int)Math.sqrt(ret), x);
		//assertTrue(Math.abs(ret * ret - d) < 0.0001);
	}
	
}