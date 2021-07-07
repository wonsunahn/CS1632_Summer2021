import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.*;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class MonkeySimStochasticTest {
	/**
	 * TODO: For Extra Credit, write a property-based test method testMonkeySim.
	 * Invokes runSimulation with s being the starting monkey with the banana. The
	 * invariant that you should check is that runSimulation does not throw an
	 * InfiniteLoopException. As of now, runSimulation throws no exception of the
	 * kind and just falls into the infinite loop, given the defect triggering
	 * sequence. So, MonkeySim will also have to be modified to throw that exception
	 * for this to work.
	 * 
	 * @param s The starting monkey with the banana. Use the @InRange annotation to
	 *          make sure the generated random number for s is greater than or equal
	 *          to 1.
	 */
}
