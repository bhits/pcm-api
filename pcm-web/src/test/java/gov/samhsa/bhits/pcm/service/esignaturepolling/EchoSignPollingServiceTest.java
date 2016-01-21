package gov.samhsa.bhits.pcm.service.esignaturepolling;

import echosign.api.clientv20.dto20.ArrayOfDocumentHistoryEvent;
import echosign.api.clientv20.dto20.DocumentHistoryEvent;
import echosign.api.clientv20.dto20.DocumentInfo;
import gov.samhsa.bhits.pcm.domain.consent.Consent;
import gov.samhsa.bhits.pcm.domain.consent.ConsentRepository;
import gov.samhsa.bhits.pcm.domain.consent.SignedPDFConsent;
import gov.samhsa.bhits.pcm.domain.consent.SignedPDFConsentRevocation;
import gov.samhsa.bhits.pcm.infrastructure.EchoSignSignatureService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EchoSignPollingServiceTest {

    private static final String NOT_SIGNED_STAUS = "NOT_SIGNED";
    private static final String SIGNED_STAUS = "SIGNED";
    private static final String DOCUMENT_ID = "SIGNED_DOCUMENT_ID";
    private static final String REVOKED_DOCUMENT_ID = "REVOKED_DOCUMENT_ID";
    private static final Long CONSENT_ID = 1L;
    private static final String CHILD_DOCUMENT_KEY = "CHILD_DOCUMENT_KEY";
    private static final String ECHO_SIGN_DESCRIPTION = "Document esigned by";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private ConsentRepository consentRepositoryMock;
    @Mock
    private EchoSignSignatureService signatureServiceMock;

    @Spy
    @InjectMocks
    private EchoSignPollingService sut;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPoll_Given_Signed_And_Existing_Child_Document_Key() {
        // Arrange
        SignedPDFConsent signedPDFConsentMock = mock(SignedPDFConsent.class);
        when(signedPDFConsentMock.getDocumentId()).thenReturn(DOCUMENT_ID);
        when(signedPDFConsentMock.getDocumentSignedStatus()).thenReturn(NOT_SIGNED_STAUS);

        Consent consentSignedMock = mock(Consent.class);
        when(consentSignedMock.getSignedPdfConsent()).thenReturn(signedPDFConsentMock);
        when(consentSignedMock.getId()).thenReturn(CONSENT_ID);

        List<Consent> consentSignedListMock = new LinkedList<Consent>();
        consentSignedListMock.add(consentSignedMock);

        when(consentRepositoryMock.findBySignedPdfConsentDocumentSignedStatusNot(SIGNED_STAUS)).thenReturn(consentSignedListMock);

        when(signatureServiceMock.getChildDocumentKey(DOCUMENT_ID)).thenReturn(CHILD_DOCUMENT_KEY);

        DocumentInfo signedDocumentInfoMock = mock(DocumentInfo.class);
        when(signatureServiceMock.getDocumentInfo(CHILD_DOCUMENT_KEY)).thenReturn(signedDocumentInfoMock);

        ArrayOfDocumentHistoryEvent arrayOfDocumentHistoryEventMock = mock(ArrayOfDocumentHistoryEvent.class);
        when(signedDocumentInfoMock.getEvents()).thenReturn(arrayOfDocumentHistoryEventMock);

        List<DocumentHistoryEvent> listDocumentHistoryEvent = new LinkedList<DocumentHistoryEvent>();
        DocumentHistoryEvent documentHistoryEventMock = mock(DocumentHistoryEvent.class);
        listDocumentHistoryEvent.add(documentHistoryEventMock);
        when(arrayOfDocumentHistoryEventMock.getDocumentHistoryEvent()).thenReturn(listDocumentHistoryEvent);

        when(documentHistoryEventMock.getDescription()).thenReturn(ECHO_SIGN_DESCRIPTION);

        doReturn(SIGNED_STAUS).when(sut).getLatestSignStatus(signedDocumentInfoMock);

        XMLGregorianCalendar xMLGregorianCalendarMock = mock(XMLGregorianCalendar.class);
        when(documentHistoryEventMock.getDate()).thenReturn(xMLGregorianCalendarMock);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        Date date = gregorianCalendar.getTime();
        when(xMLGregorianCalendarMock.toGregorianCalendar()).thenReturn(gregorianCalendar);

        byte[] latestDataFromEchoSign = new byte[10];
        when(signatureServiceMock.getLatestDocument(CHILD_DOCUMENT_KEY)).thenReturn(latestDataFromEchoSign);

        // Act
        sut.poll();

        // Assert
        verify(consentSignedMock, times(1)).setSignedDate(date);
        verify(signedPDFConsentMock, times(1)).setContent(latestDataFromEchoSign, CONSENT_ID);
        verify(consentRepositoryMock, times(1)).save(consentSignedMock);
    }

    @Test(expected = EchoSignPollingServiceException.class)
    public void testPoll_Given_Signed_And_Existing_Child_Document_Key_And_Saving_Exception() {
        // Arrange
        SignedPDFConsent signedPDFConsentMock = mock(SignedPDFConsent.class);
        when(signedPDFConsentMock.getDocumentId()).thenReturn(DOCUMENT_ID);
        when(signedPDFConsentMock.getDocumentSignedStatus()).thenReturn(NOT_SIGNED_STAUS);

        Consent consentSignedMock = mock(Consent.class);
        when(consentSignedMock.getSignedPdfConsent()).thenReturn(signedPDFConsentMock);
        when(consentSignedMock.getId()).thenReturn(CONSENT_ID);

        List<Consent> consentSignedListMock = new LinkedList<Consent>();
        consentSignedListMock.add(consentSignedMock);

        when(consentRepositoryMock.findBySignedPdfConsentDocumentSignedStatusNot(SIGNED_STAUS)).thenReturn(consentSignedListMock);

        when(signatureServiceMock.getChildDocumentKey(DOCUMENT_ID)).thenReturn(CHILD_DOCUMENT_KEY);

        DocumentInfo signedDocumentInfoMock = mock(DocumentInfo.class);
        when(signatureServiceMock.getDocumentInfo(CHILD_DOCUMENT_KEY)).thenReturn(signedDocumentInfoMock);

        ArrayOfDocumentHistoryEvent arrayOfDocumentHistoryEventMock = mock(ArrayOfDocumentHistoryEvent.class);
        when(signedDocumentInfoMock.getEvents()).thenReturn(arrayOfDocumentHistoryEventMock);

        List<DocumentHistoryEvent> listDocumentHistoryEvent = new LinkedList<DocumentHistoryEvent>();
        DocumentHistoryEvent documentHistoryEventMock = mock(DocumentHistoryEvent.class);
        listDocumentHistoryEvent.add(documentHistoryEventMock);
        when(arrayOfDocumentHistoryEventMock.getDocumentHistoryEvent()).thenReturn(listDocumentHistoryEvent);

        when(documentHistoryEventMock.getDescription()).thenReturn(ECHO_SIGN_DESCRIPTION);

        doReturn(SIGNED_STAUS).when(sut).getLatestSignStatus(signedDocumentInfoMock);

        XMLGregorianCalendar xMLGregorianCalendarMock = mock(XMLGregorianCalendar.class);
        when(documentHistoryEventMock.getDate()).thenReturn(xMLGregorianCalendarMock);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        Date date = gregorianCalendar.getTime();
        when(xMLGregorianCalendarMock.toGregorianCalendar()).thenReturn(gregorianCalendar);

        byte[] latestDataFromEchoSign = new byte[10];
        when(signatureServiceMock.getLatestDocument(CHILD_DOCUMENT_KEY)).thenReturn(latestDataFromEchoSign);

        when(consentRepositoryMock.save(consentSignedMock)).thenThrow(new RuntimeException("Bla"));

        // Act
        sut.poll();
    }

    @Test
    public void testPoll_Revoked() {
        // Arrange
        SignedPDFConsent revokedPDFConsentMock = mock(SignedPDFConsent.class);
        when(revokedPDFConsentMock.getDocumentId()).thenReturn(DOCUMENT_ID);
        when(revokedPDFConsentMock.getDocumentSignedStatus()).thenReturn(NOT_SIGNED_STAUS).thenReturn(NOT_SIGNED_STAUS);
        SignedPDFConsentRevocation signedPDFConsentRevocation = mock(SignedPDFConsentRevocation.class);
        when(signedPDFConsentRevocation.getDocumentId()).thenReturn(REVOKED_DOCUMENT_ID);
        when(signedPDFConsentRevocation.getDocumentSignedStatus()).thenReturn(SIGNED_STAUS);
        Consent consentRevokedMock = mock(Consent.class);
        when(consentRevokedMock.getSignedPdfConsentRevoke()).thenReturn(signedPDFConsentRevocation);
        when(consentRevokedMock.getSignedPdfConsent()).thenReturn(revokedPDFConsentMock);
        when(consentRevokedMock.getId()).thenReturn(CONSENT_ID);
        List<Consent> consentRevokedListMock = new LinkedList<Consent>();
        consentRevokedListMock.add(consentRevokedMock);
        when(consentRepositoryMock.findBySignedPdfConsentRevokeDocumentSignedStatusNot(SIGNED_STAUS)).thenReturn(consentRevokedListMock);
        when(signatureServiceMock.getChildDocumentKey(DOCUMENT_ID)).thenReturn(DOCUMENT_ID);
        DocumentInfo signedDocumentInfoMock = mock(DocumentInfo.class);
        when(signatureServiceMock.getDocumentInfo(DOCUMENT_ID)).thenReturn(signedDocumentInfoMock);
        ArrayOfDocumentHistoryEvent arrayOfDocumentHistoryEventMock = mock(ArrayOfDocumentHistoryEvent.class);
        when(signedDocumentInfoMock.getEvents()).thenReturn(arrayOfDocumentHistoryEventMock);
        List<DocumentHistoryEvent> listDocumentHistoryEvent = new LinkedList<DocumentHistoryEvent>();
        DocumentHistoryEvent documentHistoryEventMock = mock(DocumentHistoryEvent.class);
        listDocumentHistoryEvent.add(documentHistoryEventMock);
        when(arrayOfDocumentHistoryEventMock.getDocumentHistoryEvent()).thenReturn(listDocumentHistoryEvent);
        when(documentHistoryEventMock.getDescription()).thenReturn(ECHO_SIGN_DESCRIPTION);
        XMLGregorianCalendar xMLGregorianCalendarMock = mock(XMLGregorianCalendar.class);
        when(documentHistoryEventMock.getDate()).thenReturn(xMLGregorianCalendarMock);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        Date date = gregorianCalendar.getTime();
        when(xMLGregorianCalendarMock.toGregorianCalendar()).thenReturn(gregorianCalendar);
        doNothing().when(consentRevokedMock).setSignedDate(date);
        doReturn(SIGNED_STAUS).when(sut).getLatestSignStatus(signedDocumentInfoMock);

        // Act
        sut.poll();

        // Assert
        verify(consentRepositoryMock, times(1)).save(consentRevokedMock);
        //assertTrue(domainEventHandler.isEventFired());
    }

}
