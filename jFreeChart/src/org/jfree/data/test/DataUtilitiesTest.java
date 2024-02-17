package org.jfree.data.test;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.*;
import java.lang.Number;


public class DataUtilitiesTest extends DataUtilities {
	private Values2D singleValues;
	private KeyedValues originalKV;
	private Values2D validValues;
	private Values2D nullValues;
	private Values2D negativeValues;
	
	@BeforeClass public static void setUpBeforeClass() throws Exception { }
	
	@Before
	public void setup() throws Exception {
		/**
		 * Setup: Creating Mockery objects of the Values2D interface.
		 * Each object is made up of different values for specific testing purposes
		 * The below objects are used to test the methods:
		 * 		calculateColumnTotal(),
		 * 		calculateRowTotal()
		 */
	     Mockery validMockingContext = new Mockery();
	     validValues = validMockingContext.mock(Values2D.class);
	     validMockingContext.checking(new Expectations() {
	         {
	             one(validValues).getColumnCount();
	             will(returnValue(2));
	             one(validValues).getRowCount();
	             will(returnValue(2));
	             one(validValues).getValue(0, 0);
	             will(returnValue(7.5));
	             one(validValues).getValue(1, 0);
	             will(returnValue(2.5));
	             one(validValues).getValue(0, 1);
	             will(returnValue(7.5));
	             one(validValues).getValue(1, 1);
	             will(returnValue(2.5));
	         }
	     });
	     
	     Mockery nullMockingContext = new Mockery();
	     nullValues = nullMockingContext.mock(Values2D.class);
	     nullMockingContext.checking(new Expectations() {
	         {
	        	 one(nullValues).getColumnCount();
	             will(returnValue(1));
	             
	             one(nullValues).getRowCount();
	             will(returnValue(1));
	             
	             allowing(nullValues).getValue(with(any(Integer.class)), with(any(Integer.class)));
	             will(returnValue(null));
	         }
	     });
	     
	    Mockery negativeMockingContext = new Mockery();
	    negativeValues = negativeMockingContext.mock(Values2D.class);
	    
	    // getValue(row, col)
	    // Column index 1 total = -3 + -8 + -4 = -15
	    negativeMockingContext.checking(new Expectations() {
	        {
	            one(negativeValues).getColumnCount();
	            will(returnValue(2));
	            one(negativeValues).getRowCount();
	            will(returnValue(3));
	            one(negativeValues).getValue(0, 0);
	            will(returnValue(-1));
	            one(negativeValues).getValue(1, 0);
	            will(returnValue(-2));
	            one(negativeValues).getValue(2, 0);
	            will(returnValue(-5));
	            one(negativeValues).getValue(0, 1);
	            will(returnValue(-8));
	            one(negativeValues).getValue(1, 1);
	            will(returnValue(-3));
	            one(negativeValues).getValue(2, 1);
	            will(returnValue(-4));
	        }
	    });
	    
	    Mockery singleMocklingContext = new Mockery();
        singleValues = singleMocklingContext.mock(Values2D.class);
        singleMocklingContext.checking(new Expectations() {
             {
                 one(singleValues).getColumnCount();
                 will(returnValue(1));
                 one(singleValues).getRowCount();
                 will(returnValue(1));
                 one(singleValues).getValue(0, 0);
                 will(returnValue(3.0));
             }
         });
	}
	
	/**
	 * Testing calculateColumnTotal for a column with 2 rows and double type values.
	 * validValues mock data column with index=0 has the values 7.5 and 2.5
	 * result should equal 10.0.
	 */
	@Test
	public void testCalculateColumnTotalForTwoValues() {

	     double result = DataUtilities.calculateColumnTotal(validValues, 0);
	     // verify
	     assertEquals("Expected total: 10.0 but was " + result, 10.0, result, .000000001d);
	     // tear-down: NONE in this test method
	}
	
	/**
	 * Testing calculateColumnTotal for a column with negative values.
	 * Negative mock data column index=1 contains values -3, -4, -8. The total should be -15.0.
	 */
	@Test
	public void testCalculateColTotalForNegativeValues() {
		
		double result = DataUtilities.calculateColumnTotal(negativeValues, 1);
		
		assertEquals("Expected total: -15.0 but was " + result, -15.0, result, .000000001d);
	}
	
	/**
	 * Testing if calculateColumnTotal throws an invalid parameter exception when passed a column
	 * the contains null values.
	 * Mock data for Values2D column index 0 contains all null data. 
	 * This should throw an InvalidParameterException if caught correctly.
	 */
	@Test
	public void testCalcColNullDataThrowsException() {

	    try {
	    	
		    // Code that should throw InvalidParameterException when invalid data is passed
		    DataUtilities.calculateColumnTotal(nullValues, 0);
		    fail("Expected InvalidParameterException with null data, but no exception was thrown.");
		    
	    } catch (InvalidParameterException e) {
	        // If an InvalidParameterException is thrown, the test passes
	    }
	}
	
	/**
	 * Testing if the InvalidParameterException is thrown when a null data object is passed as an arg.
	 * Will pass if the InvalidParameterException is thrown.
	 */
	@Test
	public void testCalcColumnNullObject() {
		try {
			 DataUtilities.calculateColumnTotal(null, 1);
			 
			 fail("Expected InvalidParameterException is not thrown");
		 } catch (Exception e) {
			 assertTrue(e instanceof InvalidParameterException);
		 } catch (AssertionError ae) {
			 String errorMessage = "Expected exception: InvalidParameterException\n" +
                   "Actual exception: " + ae.getMessage();
			 throw new AssertionError(errorMessage, ae);
	     }
	}
	
	/**
	 * Testing calculateRowTotal for a row with 2 columns and double type values.
	 * validValues mock data column with index=0 has the values 7.5 and 7.5
	 * result should equal 15.0.
	 */
	@Test
	public void testRowTotalWithTwoDoubleValues() {
		double result = DataUtilities.calculateRowTotal(validValues, 0);
		assertEquals("Expected total: 15.0 but was " + result, 15.0, result, .000000001d);
		
	}
	
	/**
	 * Testing calculateRowTotal with a row with negative values.
	 * Negative mock data column index=2 contains values -4, -5. The total should be -9.0.
	 */
	@Test
	public void testRowTotalWithTwoNegatives() {
		double result = DataUtilities.calculateRowTotal(negativeValues, 2);
		assertEquals("Expected total: -9.0 but was " + result, -9.0, result, .000000001d);
	}
	
	/**
	 * Testing if calculateRowTotal throws an invalid parameter exception when passed a column
	 * the contains null values.
	 * Mock data for Values2D column index 0 contains all null data. 
	 * This should throw an InvalidParameterException if caught correctly.
	 */
	@Test
	public void testCalcRowNullDataThrowsException() {
		
	    try {
	    	
		    // Code that should throw InvalidParameterException when invalid data is passed
		    DataUtilities.calculateRowTotal(nullValues, 0);
		    fail("Expected InvalidParameterException with null data, but no exception was thrown.");
		    
	    } catch (InvalidParameterException e) {
	        // If an InvalidParameterException is thrown, the test passes
	    }
	}
	
	/**
	 * Testing if the InvalidParameterException is thrown when a null data object is passed as an arg.
	 * Will pass if the InvalidParameterException is thrown.
	 */
	@Test
	public void testCalcRowNullObject() {
		try {
			 DataUtilities.calculateRowTotal(null, 1);
			 
			 fail("Expected InvalidParameterException is not thrown");
		 } catch (Exception e) {
			 assertTrue(e instanceof InvalidParameterException);
		 } catch (AssertionError ae) {
			 String errorMessage = "Expected exception: InvalidParameterException\n" +
                   "Actual exception: " + ae.getMessage();
			 throw new AssertionError(errorMessage, ae);
	     }
	}
	
	
	/**
     * Testing calculateRowTotal with a row with a single value.
     * Single mock data column index=0 contains value 3. The total should be 3.0.
     */
    @Test
    public void testRowTotalSingleValue() {
        double result = DataUtilities.calculateRowTotal(singleValues, 0);
        assertEquals("Expected total: 3.0 but was " + result, 3.0, result, .000000001d);
    }
	
    /**
	 * This test covers the throw of an InvalidParameterException with a null input value
	 * for the method getCumulativePercentages()
	 */
    @Test
    public void testInvalidParameterExceptionThrownWithNullValue() {


        try {
            DataUtilities.getCumulativePercentages(null);

        } catch (InvalidParameterException e) {

        } catch (IllegalArgumentException e) {
            fail("Wrong exception is thrown. Expected: InvalidParameterException, Actual: IllegalArgumentException");
        } catch (AssertionError ae) {
            fail("Wrong exception is thrown. Expected: InvalidParameterException, Actual: AssertionError");
        }
        
    }
	
	/**
	 * This test covers the use of a KeyedValues object of three items with positive integer
	 * values for the method getCumulativePercentages()
	 */
	@Test
	public void testCorrectCumulativePercentageForPositiveIntegers() {
		
	
		Mockery mockingContextKV = new Mockery();
		originalKV = mockingContextKV.mock(KeyedValues.class);
		mockingContextKV.checking(new Expectations() {
			{
				
				allowing(originalKV).getItemCount();
				will(returnValue(3));
				
				allowing(originalKV).getKey(0);
				will(returnValue(0));
				
				allowing(originalKV).getKey(1);
				will(returnValue(1));
				
				allowing(originalKV).getKey(2);
				will(returnValue(2));
				
				
				allowing(originalKV).getValue(0);
				will(returnValue(5));
				
				allowing(originalKV).getValue(1);
				will(returnValue(9));
				
				allowing(originalKV).getValue(2);
				will(returnValue(2));
			}
		});
		
		KeyedValues result = DataUtilities.getCumulativePercentages(originalKV);
		
		
		assertEquals("The cumulative percentage of key 0 should be 0.3125",
				0.3125, result.getValue(0).doubleValue(), .000000001d);
		
		
		
		assertEquals("The cumulative percentage of key 1 should be 0.875",
				0.875, result.getValue(1).doubleValue(), .000000001d);
		
		
		
		assertEquals("The cumulative percentage of key 2 should be 1.0",
				1.0, result.getValue(2).doubleValue(), .000000001d);
	}
	
	/**
	 * This test covers the use of a KeyedValues object of three items with floating point
	 * values for the method getCumulativePercentages() 
	 */
	@Test
	public void testCorrectCumulativePercentageForFloatingPointValues() {
		
	
		Mockery mockingContextKV = new Mockery();
		originalKV = mockingContextKV.mock(KeyedValues.class);
		mockingContextKV.checking(new Expectations() {
			{
				
				allowing(originalKV).getItemCount();
				will(returnValue(3));
				
				allowing(originalKV).getKey(0);
				will(returnValue(0));
				
				allowing(originalKV).getKey(1);
				will(returnValue(1));
				
				allowing(originalKV).getKey(2);
				will(returnValue(2));
				
				
				allowing(originalKV).getValue(0);
				will(returnValue(1.3));
				
				allowing(originalKV).getValue(1);
				will(returnValue(4.7));
				
				allowing(originalKV).getValue(2);
				will(returnValue(0.6));
			}
		});
		
		KeyedValues result = DataUtilities.getCumulativePercentages(originalKV);
	
		assertEquals("The cumulative percentage of key 0 should be 0.19696969697",
				0.19696969697, result.getValue(0).doubleValue(), .000000001d);
		
	
		
		assertEquals("The cumulative percentage of key 1 should be 0.909090909091",
				0.909090909091, result.getValue(1).doubleValue(), .000000001d);
		
		
		
		assertEquals("The cumulative percentage of key 2 should be 1.0",
				1.0, result.getValue(2).doubleValue(), .000000001d);
	}
	
	/**
	 * This test covers the use of a KeyedValues object of three items with negative values
	 * for the method getCumulativePercentages() 
	 */
	@Test
	public void testCorrectCumulativePercentageForNegatives() {
		
	
		Mockery mockingContextKV = new Mockery();
		originalKV = mockingContextKV.mock(KeyedValues.class);
		mockingContextKV.checking(new Expectations() {
			{
				
				allowing(originalKV).getItemCount();
				will(returnValue(3));
				
				allowing(originalKV).getKey(0);
				will(returnValue(0));
				
				allowing(originalKV).getKey(1);
				will(returnValue(1));
				
				allowing(originalKV).getKey(2);
				will(returnValue(2));
				
				
				allowing(originalKV).getValue(0);
				will(returnValue(-10));
				
				allowing(originalKV).getValue(1);
				will(returnValue(-3));
				
				allowing(originalKV).getValue(2);
				will(returnValue(-5));
			}
		});
		
		KeyedValues result = DataUtilities.getCumulativePercentages(originalKV);
	
		assertEquals("The cumulative percentage of key 0 should be 0.555555555556",
				0.555555555556, result.getValue(0).doubleValue(), .000000001d);
		
	
		
		assertEquals("The cumulative percentage of key 1 should be 0.722222222222",
				0.722222222222, result.getValue(1).doubleValue(), .000000001d);
		
			
		
		assertEquals("The cumulative percentage of key 2 should be 1.0",
				1.0, result.getValue(2).doubleValue(), .000000001d);
	}
	
	/**
	 * This test covers the use of a KeyedValues object of a single item for the method getCumulativePercentages()
	 */
	@Test
	public void testCorrectCumulativePercentageForSinglePair() {
		
	
		Mockery mockingContextKV = new Mockery();
		originalKV = mockingContextKV.mock(KeyedValues.class);
		mockingContextKV.checking(new Expectations() {
			{
				
				allowing(originalKV).getItemCount();
				will(returnValue(1));
				
				allowing(originalKV).getKey(0);
				will(returnValue(0));
				
				allowing(originalKV).getValue(0);
				will(returnValue(5));

			}
		});
		
		KeyedValues result = DataUtilities.getCumulativePercentages(originalKV);
	
		assertEquals("The cumulative percentage of key 0 should be 1.0",
				1.0, result.getValue(0).doubleValue(), .000000001d);
		
	}
	
	/**
	 * This test covers using empty data for the KeyedValued object for the method getCumulativePercentages() 
	 */
	@Test
	public void testCumulativePercentageForEmptyData() {
		
		Mockery mockingContextKV = new Mockery();
		originalKV = mockingContextKV.mock(KeyedValues.class);
		mockingContextKV.checking(new Expectations() {
			{
				allowing(originalKV).getItemCount();
				will(returnValue(0));
			}
		});
		
		KeyedValues result = DataUtilities.getCumulativePercentages(originalKV);
	
		assertEquals("The item count should be 0",
				0, result.getItemCount(), .000000001d);
	}
	
	/**
	 * This test covers valid array values for method createNumberArray() 
	 */
	@Test
    public void testValidValueForCreateNumberArray() {
        double[] doubleArrayTest = {-1.1, 2.2, 3.3};
        Number numberTest[] = new Number[3];
        numberTest[0] = new Double("-1.1");
        numberTest[1] = new Double("2.2");
        numberTest[2] = new Double("3.3");
        assertArrayEquals(numberTest, DataUtilities.createNumberArray(doubleArrayTest));
    }

	/**
	 * This test covers empty array values for method createNumberArray() 
	 */
    @Test
    public void testEmptyValueForCreateNumberArray() {
        double[] emptyArrayTest = {};
        Number numberTest[] = {};
        assertArrayEquals(numberTest, DataUtilities.createNumberArray(emptyArrayTest));
    }

    /**
	 * This test covers null array values for method createNumberArray() 
	 */
    @Test
    public void testNullValueForCreateNumberArray() {
        try {
            double[] nullArrayTest = null;
            DataUtilities.createNumberArray(nullArrayTest);
        } catch (InvalidParameterException e) {

        } catch (IllegalArgumentException iae) {
            fail("Unexpected IllegalArgumentException: " + iae.getMessage());
        } catch (AssertionError ae) {
            String errorMessage = "Expected exception: InvalidParameterException\n" + "Actual exception: " + ae.getMessage();
            System.err.println(errorMessage); // Print the error message to the error stream
            throw new AssertionError(errorMessage, ae);
        }
    }
    
    /**
	 * This test covers valid array values for method createNumberArray2D() 
	 */
    @Test
    public void testValidValueForCreateNumberArray2D() {
        double[][] double2dArrayTest = {{1.1, -2.2}, {-3.3, 4.4}};
        Double firstNumberArray[] = new Double[2];
        Double secondNumberArray[] = new Double[2];
        firstNumberArray[0] = new Double("1.1");
        firstNumberArray[1] = new Double("-2.2");
        secondNumberArray[0] = new Double("-3.3");
        secondNumberArray[1] = new Double("4.4");
        Number[][] number2dTest = {firstNumberArray, secondNumberArray};
        assertArrayEquals(number2dTest, DataUtilities.createNumberArray2D(double2dArrayTest));
    }

    /**
	 * This test covers empty array values for method createNumberArray2D() 
	 */
    @Test
    public void testEmptyValueForCreateNumberArray2D() {
        double[][] double2dArrayTest = {};
        Number[][] number2dTest = {};
        assertArrayEquals(number2dTest, DataUtilities.createNumberArray2D(double2dArrayTest));
    }
    
    /**
	 * This test covers null array values for method createNumberArray2D() 
	 */
    @Test
    public void testNullValueForCreateNumberArray2D() {
        try {
            double[][] nullArrayTest = null;
            DataUtilities.createNumberArray2D(nullArrayTest);
        } catch (InvalidParameterException e) {

        } catch (IllegalArgumentException iae) {
            fail("Unexpected IllegalArgumentException: " + iae.getMessage());
        } catch (AssertionError ae) {
            String errorMessage = "Expected exception: InvalidParameterException\n" + "Actual exception: " + ae.getMessage();
            System.err.println(errorMessage); // Print the error message to the error stream
            throw new AssertionError(errorMessage, ae);
        }
    }
	 
}