package org.jfree.data.test;

import static org.junit.Assert.*; import org.jfree.data.Range; import org.junit.*;

public class RangeTest {
    private Range exampleRange;
    
    @BeforeClass public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception { exampleRange = new Range(-1, 1);
    }

    /**
	 * This test covers a nominal value for the exampleRange for contains() 
	 */
    @Test
    public void testInRangeValueForMethodContains() {
    	assertEquals("The expected boolean should be true", true, exampleRange.contains(0));
    }
    
    /**
	 * This test covers an out of range value above the upper boundary for the exampleRange for contains() 
	 */
    @Test
    public void testPositiveOutOfRangeValueForMethodContains() {
    	assertEquals("The expected boolean should be false", false, exampleRange.contains(25));
    }
    
    /**
	 * This test covers an out of range value below the lower boundary for the exampleRange for contains() 
	 */
    @Test
    public void testNegativeOutOfRangeValueForMethodContains() {
    	assertEquals("The expected boolean should be false", false, exampleRange.contains(-25));
    }
    
    /**
	 * This test covers a value slightly below the upper boundary for the exampleRange for contains() 
	 */
    @Test
    public void testBelowUpperBoundaryForMethodContains() {
    	assertEquals("The expected boolean should be true", true, exampleRange.contains(0.99));
    }
    
    /**
	 * This test covers a value equal to the upper boundary for the exampleRange for contains() 
	 */
    @Test
    public void testEqualToUpperBoundaryForMethodContains() {
    	assertEquals("The expected boolean should be true", true, exampleRange.contains(1));
    }
    
    /**
	 * This test covers a value slightly above the upper boundary for the exampleRange for contains() 
	 */
    @Test
    public void testAboveUpperBoundaryForMethodContains() {
    	assertEquals("The expected boolean should be false", false, exampleRange.contains(1.01));
    }
    
    /**
	 * This test covers a value slightly below the lower boundary for the exampleRange for contains() 
	 */
    @Test
    public void testBelowLowerBoundaryForMethodContains() {
    	assertEquals("The expected boolean should be false", false, exampleRange.contains(-1.01));
    }
    
    /**
	 * This test covers a value equal to the lower boundary for the exampleRange for contains() 
	 */
    @Test
    public void testEqualToLowerBoundaryForMethodContains() {
    	assertEquals("The expected boolean should be true", true, exampleRange.contains(-1));
    }
    
    /**
	 * This test covers a value slightly above the lower boundary for the exampleRange for contains() 
	 */
    @Test
    public void testAboveLowerBoundaryForMethodContains() {
    	assertEquals("The expected boolean should be true", true, exampleRange.contains(-0.99));
    }
    
    /**
	 * This test covers a nominal value for the exampleRange for contains() 
	 */
    @Test
    public void testNominalValueForMethodContains() {
    	assertEquals("The expected boolean should be true", true, exampleRange.contains(0.5));
    }
    
    /**
	 * This test covers the getCentralValue() method using the exampleRange 
	 */
    @Test
    public void testCentralValueShouldBeCorrect() {
        assertEquals("The central value of -1 and 1 should be 0",
        0, exampleRange.getCentralValue(), .000000001d);
    }
    
    /**
	 * This test covers the getLength() method using the exampleRange 
	 */
    @Test
    public void testLengthShouldBeCorrect() {
        assertEquals("The length of the -1 to 1 range should be 2",
        2, exampleRange.getLength(), .000000001d);
    }
    
    /**
	 * This test covers the getLowerBound() method using the exampleRange 
	 */
    @Test
    public void testMethodGetLowerBound() {
    	assertEquals("The expected value should be -1", -1.0, exampleRange.getLowerBound(), .000000001d);
    }
    
    /**
	 * This test covers the getUpperBound() method using the exampleRange 
	 */
    @Test
    public void testMethodGetUpperBound() {
    	assertEquals("The expected value should be 1", 1.0, exampleRange.getUpperBound(), .000000001d);
    }

    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    
}