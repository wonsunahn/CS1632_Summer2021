import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.*;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.junit.Test;

@RunWith(JUnitQuickcheck.class)
public class MonkeySimStochasticTest {
	// TODO: Add member variables as needed
	
	/**
	 * Preconditions: none
	 * Execution steps: Call MonkeySim.main("s"), where s is a random integer greater than equal to 1
	 * Postconditions: Call terminates without the InfiniteLoopException thrown 
	 * 
	 * @param s Integer argument to pass to MonkeySim.main.  Use the @InRange annotation to
	 *          make sure the generated random number for s is greater than or equal
	 *          to 1.
	 */
	
	// TODO: Write a QuickCheck test case testMainStochastic that does the above.
	
	/**
	 * Preconditions: none
	 * Execution steps: Call MonkeySim.main("<FILL_IN_YOUR_NUMBER>")
	 * Postconditions: Call terminates without the InfiniteLoopException thrown 
	 */
	
	// TODO: Write a regular JUnit test case testMainInfiniteLoop that does the
	// above. Replace <FILL_IN_YOUR_NUMBER> with a number that triggers an infinite
	// loop defect in the current implementation.
}
