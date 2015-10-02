package gov.samhsa.consent2share.web.valueset;

import static org.junit.Assert.assertTrue;
import gov.samhsa.consent2share.domain.valueset.ConceptCodeRepository;
import gov.samhsa.consent2share.service.dto.ConceptCodeDto;
import gov.samhsa.consent2share.service.valueset.ConceptCodeServiceImpl;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:META-INF/spring/applicationContext-test.xml", "classpath:META-INF/spring/applicationContext-test-dataAccess.xml"})
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class ConceptCodeServiceImplIT {

//	private ConceptCodeServiceImpl conceptCodeServiceImpl;
//
//	@Autowired
//	private ConceptCodeRepository conceptCodeRepository;
//
//	@Before
//	public void setUp() {
//		conceptCodeServiceImpl = new ConceptCodeServiceImpl();
//
//	}
//
//	@Test
//	public void testConceptCodePagingReturnCorrectNumberPerPage() {
//		List<ConceptCodeDto> list = conceptCodeServiceImpl.findAll(0);
//
//		assertTrue("Results cannot be greater than page size", list.size() <= ConceptCodeServiceImpl.CONCEPT_CODE_PAGE_SIZE);
//	}
}
