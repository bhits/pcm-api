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
package gov.samhsa.consent;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.XmlTransformer;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConsentBuilderImplTest {

	@InjectMocks
	ConsentBuilderImpl sut;

	@Mock
	ConsentDtoFactory consentDtoFactoryMock;

	@Mock
	XmlTransformer xmlTransformerMock;

	@Mock
	XacmlXslUrlProvider xacmlXslUrlProvider;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildConsent2Cdar2() throws ConsentGenException {

		// Arrange
		final long consentId = 1;
		final ConsentDto consentDtoMock = mock(ConsentDto.class);
		final String cdar2XslUrlStrMock = "cdar2XslUrlStrMock";
		when(consentDtoFactoryMock.createConsentDto(anyLong())).thenReturn(
				consentDtoMock);
		final String cdar2Mock = "cdar2Mock";
		when(xacmlXslUrlProvider.getUrl(XslResource.CDAR2XSLNAME)).thenReturn(
				cdar2XslUrlStrMock);
		when(
				xmlTransformerMock.transform(eq(consentDtoMock),
						eq(cdar2XslUrlStrMock), isA(Optional.class),
						isA(Optional.class))).thenReturn(cdar2Mock);

		// Act
		final String cdar2 = sut.buildConsent2Cdar2(consentId);

		// Assert
		assertEquals(cdar2Mock, cdar2);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildConsent2Cdar2_ConsentGenException()
			throws ConsentGenException {
		// Arrange
		thrown.expect(ConsentGenException.class);
		final long consentId = 1;
		final String cdar2XslUrlStrMock = "cdar2XslUrlStrMock";
		final ConsentDto consentDtoMock = mock(ConsentDto.class);
		when(consentDtoFactoryMock.createConsentDto(anyLong())).thenReturn(
				consentDtoMock);
		when(xacmlXslUrlProvider.getUrl(XslResource.CDAR2XSLNAME)).thenReturn(
				cdar2XslUrlStrMock);
		when(
				xmlTransformerMock.transform(eq(consentDtoMock),
						eq(cdar2XslUrlStrMock), isA(Optional.class),
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
		final String mrnMock = "mrnMock";
		final Long consentId = new Long(1);
		final ConsentDto consentDtoMock = mock(ConsentDto.class);
		final PatientDto patientDtoMock = mock(PatientDto.class);
		when(consentDtoFactoryMock.createConsentDto(consentId)).thenReturn(
				consentDtoMock);
		when(consentDtoMock.getPatientDto()).thenReturn(patientDtoMock);
		when(patientDtoMock.getMedicalRecordNumber()).thenReturn(mrnMock);

		final String xacmlMock = "xacml";
		final String xacmlXslUrl = "xacmlXslUrl";
		when(xacmlXslUrlProvider.getUrl(XslResource.XACMLXSLNAME)).thenReturn(
				xacmlXslUrl);
		when(
				xmlTransformerMock.transform(eq(consentDtoMock),
						eq(xacmlXslUrl), isA(Optional.class),
						isA(Optional.class))).thenReturn(xacmlMock);

		// Act
		final String xacml = sut.buildConsent2Xacml(consentId);

		// Assert
		assertEquals(xacmlMock, xacml);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildConsent2Xacml_ConsentGenException()
			throws ConsentGenException {
		// Arrange
		final String mrnMock = "mrnMock";
		thrown.expect(ConsentGenException.class);
		final Long consentId = new Long(1);
		final ConsentDto consentDtoMock = mock(ConsentDto.class);
		final PatientDto patientDtoMock = mock(PatientDto.class);
		when(consentDtoMock.getPatientDto()).thenReturn(patientDtoMock);
		when(patientDtoMock.getMedicalRecordNumber()).thenReturn(mrnMock);
		when(consentDtoFactoryMock.createConsentDto(consentId)).thenReturn(
				consentDtoMock);

		final String xacmlXslUrlMock = "xacmlXslUrlMock";
		when(xacmlXslUrlProvider.getUrl(XslResource.XACMLXSLNAME)).thenReturn(
				xacmlXslUrlMock);
		when(
				xmlTransformerMock.transform(eq(consentDtoMock),
						eq(xacmlXslUrlMock), isA(Optional.class),
						isA(Optional.class))).thenThrow(
				new RuntimeException("Error in saxon transform"));

		// Act
		sut.buildConsent2Xacml(consentId);

		// Assert
	}

}
