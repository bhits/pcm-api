package gov.samhsa.consent2share.domain.account;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmailTokenTest {

	private EmailToken emailToken = null;
	
	@Before
	public void setUp() {
		emailToken = new EmailToken();
		emailToken.setIsTokenUsed(true);
	}
	
	@Test
	public void testGetExpiredDate_Succeeds(){
		
		Integer expireInHours = 12;
		emailToken.setExpireInHours(expireInHours);
		Date requestDateTime = new Date();
		emailToken.setRequestDateTime(requestDateTime);
		
		Date expiredDate = emailToken.getExpiredDate();
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(requestDateTime); 
	    cal.add(Calendar.HOUR_OF_DAY, expireInHours);
	    Date calculatedExpiredDate = cal.getTime();
	    
	    Assert.assertTrue(calculatedExpiredDate.equals(expiredDate));
	}
	
	@Test
	public void testIsTokenExpired_When_Used_Returns_True(){		
		Integer expireInHours = 12;
		emailToken.setExpireInHours(expireInHours);
		
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(now); 
	    cal.add(Calendar.HOUR_OF_DAY, -(expireInHours+1));
	    Date requestDateTime = cal.getTime();
		
		emailToken.setRequestDateTime(requestDateTime);
		
		Boolean isExpired = emailToken.isTokenExpired();
		
		Assert.assertTrue(isExpired);
	}
	
	@Test
	public void testIsTokenExpired_When_Not_Used_Returns_False(){		
		Integer expireInHours = 12;
		emailToken.setExpireInHours(expireInHours);
		
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(now); 
	    cal.add(Calendar.HOUR_OF_DAY, 1);
	    Date requestDateTime = cal.getTime();
		
		emailToken.setRequestDateTime(requestDateTime);
		emailToken.setIsTokenUsed(false);
		
		Boolean isExpired = emailToken.isTokenExpired();
		
		Assert.assertFalse(isExpired);
	}
	
	@After
	public void tearDown() {
		emailToken = null;
	}
}
