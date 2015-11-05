/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent.pg;

import static gov.samhsa.pcm.commonunit.matcher.ArgumentMatchers.matching;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.param.Params;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.consent.ConsentBuilderImpl;
import gov.samhsa.consent.ConsentDto;
import gov.samhsa.consent.ConsentDtoFactory;
import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.consent.PatientDto;
import gov.samhsa.consent.XslResource;

import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ConsentBuilderImplTest {

	public static final String C2S_ACCOUNT_ORG = "C2S_ACCOUNT_ORG";

	@InjectMocks
	ConsentBuilderImpl sut;

	@Mock
	ConsentDtoFactory consentDtoFactoryMock;

	@Mock
	XmlTransformer xmlTransformerMock;

	@Mock
	XacmlXslUrlProviderImpl xacmlXslUrlProviderImplMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
		ReflectionTestUtils.setField(sut, "c2sAccountOrg", C2S_ACCOUNT_ORG);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildConsent2Cdar2() throws ConsentGenException {

		// Arrange
		final long consentId = 1;
		final ConsentDto consentDtoMock = mock(ConsentDto.class);
		final String c2cdar2XslUrlMock = "c2cdar2XslUrlMock";
		when(consentDtoFactoryMock.createConsentDto(anyLong())).thenReturn(
				consentDtoMock);
		final String cdar2Mock = "cdar2";
		when(xacmlXslUrlProviderImplMock.getUrl(XslResource.CDAR2XSLNAME))
				.thenReturn(c2cdar2XslUrlMock);
		when(
				xmlTransformerMock.transform(eq(consentDtoMock),
						eq(c2cdar2XslUrlMock), isA(Optional.class),
						isA(Optional.class))).thenReturn(cdar2Mock);

		// Act
		final String cdar2 = sut.buildConsent2Cdar2(consentId);

		// Assert
		assertEquals(cdar2Mock, cdar2);
		verify(xmlTransformerMock).transform(
				eq(consentDtoMock),
				eq(c2cdar2XslUrlMock),
				argThat(matching((Optional<Params> params) -> params
						.isPresent() == false)), isA(Optional.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildConsent2Cdar2_ConsentGenException()
			throws ConsentGenException {
		// Arrange
		thrown.expect(ConsentGenException.class);
		final long consentId = 1;
		final String c2cdar2XslUrlMock = "c2cdar2XslUrlMock";
		final ConsentDto consentDtoMock = mock(ConsentDto.class);
		when(xacmlXslUrlProviderImplMock.getUrl(XslResource.CDAR2XSLNAME))
				.thenReturn(c2cdar2XslUrlMock);
		when(consentDtoFactoryMock.createConsentDto(anyLong())).thenReturn(
				consentDtoMock);
		when(
				xmlTransformerMock.transform(eq(consentDtoMock),
						eq(c2cdar2XslUrlMock), isA(Optional.class),
						isA(Optional.class))).thenThrow(
				new RuntimeException("Error in saxon transform"));

		// Act
		sut.buildConsent2Cdar2(consentId);

		// Assert
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildConsent2Xacml() throws ConsentGenException {
		// Arrange
		final String eidMock = "eidMock";
		final String mrnMock = "mrnMock";
		final String c2xacmlXslUrlMock = "c2xacmlXslUrlMock";
		final Long consentId = new Long(1);
		final ConsentDto consentDtoMock = mock(ConsentDto.class);
		final PatientDto patientDtoMock = mock(PatientDto.class);
		when(xacmlXslUrlProviderImplMock.getUrl(XslResource.XACMLXSLNAME))
				.thenReturn(c2xacmlXslUrlMock);
		when(consentDtoFactoryMock.createConsentDto(consentId)).thenReturn(
				consentDtoMock);
		when(consentDtoMock.getPatientDto()).thenReturn(patientDtoMock);
		// when(patientDtoMock.getEnterpriseIdentifier()).thenReturn(eidMock);
		when(patientDtoMock.getMedicalRecordNumber()).thenReturn(mrnMock);
		final String xacmlMock = "xacml";
		when(
				xmlTransformerMock.transform(eq(consentDtoMock),
						eq(c2xacmlXslUrlMock), isA(Optional.class),
						isA(Optional.class))).thenReturn(xacmlMock);

		// Act
		final String xacml = sut.buildConsent2Xacml(consentId);

		// Assert
		assertEquals(xacmlMock, xacml);
		verify(xmlTransformerMock).transform(
				eq(consentDtoMock),
				eq(c2xacmlXslUrlMock),
				argThat(matching((Optional<Params> params) -> params
						.isPresent() == true
						&& params.get().toMap().size() == 1
						&& params.get().get(ConsentBuilderImpl.PARAM_MRN)
								.equals(mrnMock))), isA(Optional.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildConsent2Xacml_ConsentGenException()
			throws ConsentGenException {
		// Arrange
		final String eidMock = "eidMock";
		final String mrnMock = "mrnMock";
		final String c2xacmlXslUrlMock = "c2xacmlXslUrlMock";
		thrown.expect(ConsentGenException.class);
		final Long consentId = new Long(1);
		final ConsentDto consentDtoMock = mock(ConsentDto.class);
		final PatientDto patientDtoMock = mock(PatientDto.class);
		when(xacmlXslUrlProviderImplMock.getUrl(XslResource.XACMLXSLNAME))
				.thenReturn(c2xacmlXslUrlMock);
		when(consentDtoMock.getPatientDto()).thenReturn(patientDtoMock);
		// when(patientDtoMock.getEnterpriseIdentifier()).thenReturn(eidMock);
		when(patientDtoMock.getMedicalRecordNumber()).thenReturn(mrnMock);
		when(consentDtoFactoryMock.createConsentDto(consentId)).thenReturn(
				consentDtoMock);
		when(
				xmlTransformerMock.transform(eq(consentDtoMock),
						eq(c2xacmlXslUrlMock), isA(Optional.class),
						isA(Optional.class))).thenThrow(
				new RuntimeException("Error in saxon transform"));

		// Act
		sut.buildConsent2Xacml(consentId);

		// Assert
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildConsent2XacmlPdfConsentFrom()
			throws ConsentGenException {
		// Arrange
		final String mrnMock = "mrnMock";
		final String eidMock = "eidMock";
		final String polId = "C2S.PG-DEV.RmETWp:&amp;2.16.840.1.113883.3.704.100.200.1.1.3.1&amp;ISO:1578821153:1427467752:XM2UoY";
		final String polIdNew = "C2S.PG-DEV.RmETWp:&2.16.840.1.113883.3.704.100.200.1.1.3.1&ISO:1427467752:C2S_ACCOUNT_ORG:XM2UoY";
		final String c2xacmlpdfConsentFromXslUrlMock = "c2xacmlpdfConsentFromXslUrlMock";
		final Long consentId = new Long(1);
		final ConsentDto consentDtoMock = mock(ConsentDto.class);
		final PatientDto patientDtoMock = mock(PatientDto.class);
		when(
				xacmlXslUrlProviderImplMock
						.getUrl(XslResource.XACMLPDFCONSENTFROMXSLNAME))
				.thenReturn(c2xacmlpdfConsentFromXslUrlMock);
		when(consentDtoFactoryMock.createConsentDto(consentId)).thenReturn(
				consentDtoMock);
		when(consentDtoMock.getPatientDto()).thenReturn(patientDtoMock);
		when(consentDtoMock.getConsentReferenceid()).thenReturn(polId);
		// when(patientDtoMock.getEnterpriseIdentifier()).thenReturn(eidMock);
		when(patientDtoMock.getMedicalRecordNumber()).thenReturn(mrnMock);
		final String xacmlMock = "xacml";
		when(
				xmlTransformerMock.transform(eq(consentDtoMock),
						eq(c2xacmlpdfConsentFromXslUrlMock),
						isA(Optional.class), isA(Optional.class))).thenReturn(
				xacmlMock);

		// Act
		final String xacml = sut.buildConsent2XacmlPdfConsentFrom(consentId);

		// Assert
		assertEquals(xacmlMock, xacml);
		verify(xmlTransformerMock).transform(
				eq(consentDtoMock),
				eq(c2xacmlpdfConsentFromXslUrlMock),
				argThat(matching((Optional<Params> params) -> params
						.isPresent() == true
						&& params.get().toMap().size() == 2
						&& params.get().toMap()
								.get(ConsentBuilderImpl.PARAM_MRN)
								.equals(mrnMock)
						&& params.get().toMap()
								.get(ConsentBuilderImpl.PARAM_POLICY_ID)
								.equals(polIdNew))), isA(Optional.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildConsent2XacmlPdfConsentFrom_ConsentGenException()
			throws ConsentGenException {
		// Arrange
		final String eidMock = "eidMock";
		final String mrnMock = "mrnMock";
		final String c2xacmlpdfConsentFromXslUrlMock = "c2xacmlpdfConsentFromXslUrlMock";
		thrown.expect(ConsentGenException.class);
		final Long consentId = new Long(1);
		final ConsentDto consentDtoMock = mock(ConsentDto.class);
		final PatientDto patientDtoMock = mock(PatientDto.class);
		when(
				xacmlXslUrlProviderImplMock
						.getUrl(XslResource.XACMLPDFCONSENTFROMXSLNAME))
				.thenReturn(c2xacmlpdfConsentFromXslUrlMock);
		when(consentDtoMock.getPatientDto()).thenReturn(patientDtoMock);
		// when(patientDtoMock.getEnterpriseIdentifier()).thenReturn(eidMock);
		when(patientDtoMock.getMedicalRecordNumber()).thenReturn(mrnMock);
		when(consentDtoFactoryMock.createConsentDto(consentId)).thenReturn(
				consentDtoMock);
		when(
				xmlTransformerMock.transform(eq(consentDtoMock),
						eq(c2xacmlpdfConsentFromXslUrlMock),
						isA(Optional.class), isA(Optional.class))).thenThrow(
				new RuntimeException("Error in saxon transform"));

		// Act
		sut.buildConsent2XacmlPdfConsentFrom(consentId);

		// Assert
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildConsent2XacmlPdfConsentTo() throws ConsentGenException {
		// Arrange
		final String eidMock = "eidMock";
		final String mrnMock = "mrnMock";
		final String polId = "C2S.PG-DEV.RmETWp:&amp;2.16.840.1.113883.3.704.100.200.1.1.3.1&amp;ISO:1578821153:1427467752:XM2UoY";
		final String polIdNew = "C2S.PG-DEV.RmETWp:&2.16.840.1.113883.3.704.100.200.1.1.3.1&ISO:1578821153:C2S_ACCOUNT_ORG:XM2UoY";
		final String c2xacmlpdfConsentToXslUrlMock = "c2xacmlpdfConsentToXslUrlMock";
		final Long consentId = new Long(1);
		final ConsentDto consentDtoMock = mock(ConsentDto.class);
		final PatientDto patientDtoMock = mock(PatientDto.class);
		when(
				xacmlXslUrlProviderImplMock
						.getUrl(XslResource.XACMLPDFCONSENTTOXSLNAME))
				.thenReturn(c2xacmlpdfConsentToXslUrlMock);
		when(consentDtoFactoryMock.createConsentDto(consentId)).thenReturn(
				consentDtoMock);
		when(consentDtoMock.getConsentReferenceid()).thenReturn(polId);
		when(consentDtoMock.getPatientDto()).thenReturn(patientDtoMock);
		// when(patientDtoMock.getEnterpriseIdentifier()).thenReturn(eidMock);
		when(patientDtoMock.getMedicalRecordNumber()).thenReturn(mrnMock);
		final String xacmlMock = "xacml";
		when(
				xmlTransformerMock.transform(eq(consentDtoMock),
						eq(c2xacmlpdfConsentToXslUrlMock), isA(Optional.class),
						isA(Optional.class))).thenReturn(xacmlMock);

		// Act
		final String xacml = sut.buildConsent2XacmlPdfConsentTo(consentId);

		// Assert
		assertEquals(xacmlMock, xacml);
		verify(xmlTransformerMock).transform(
				eq(consentDtoMock),
				eq(c2xacmlpdfConsentToXslUrlMock),
				argThat(matching((Optional<Params> params) -> params
						.isPresent() == true
						&& params.get().toMap().size() == 2
						&& params.get().toMap()
								.get(ConsentBuilderImpl.PARAM_MRN)
								.equals(mrnMock)
						&& params.get().toMap()
								.get(ConsentBuilderImpl.PARAM_POLICY_ID)
								.equals(polIdNew))), isA(Optional.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildConsent2XacmlPdfConsentTo_ConsentGenException()
			throws ConsentGenException {
		// Arrange
		final String eidMock = "eidMock";
		final String mrnMock = "mrnMock";
		final String polId = "C2S.PG-DEV.RmETWp:&amp;2.16.840.1.113883.3.704.100.200.1.1.3.1&amp;ISO:1578821153:1427467752:XM2UoY";
		final String c2xacmlpdfConsentToXslUrlMock = "c2xacmlpdfConsentToXslUrlMock";
		thrown.expect(ConsentGenException.class);
		final Long consentId = new Long(1);
		final ConsentDto consentDtoMock = mock(ConsentDto.class);
		final PatientDto patientDtoMock = mock(PatientDto.class);
		when(
				xacmlXslUrlProviderImplMock
						.getUrl(XslResource.XACMLPDFCONSENTTOXSLNAME))
				.thenReturn(c2xacmlpdfConsentToXslUrlMock);
		when(consentDtoMock.getPatientDto()).thenReturn(patientDtoMock);
		when(consentDtoMock.getConsentReferenceid()).thenReturn(polId);
		// when(patientDtoMock.getEnterpriseIdentifier()).thenReturn(eidMock);
		when(patientDtoMock.getMedicalRecordNumber()).thenReturn(mrnMock);
		when(consentDtoFactoryMock.createConsentDto(consentId)).thenReturn(
				consentDtoMock);
		when(
				xmlTransformerMock.transform(eq(consentDtoMock),
						eq(c2xacmlpdfConsentToXslUrlMock), isA(Optional.class),
						isA(Optional.class))).thenThrow(
				new RuntimeException("Error in saxon transform"));

		// Act
		sut.buildConsent2XacmlPdfConsentTo(consentId);

		// Assert
	}
}
