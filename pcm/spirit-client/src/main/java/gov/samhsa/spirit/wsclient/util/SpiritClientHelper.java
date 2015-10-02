package gov.samhsa.spirit.wsclient.util;

import gov.samhsa.spirit.wsclient.adapter.SpiritConstants;
import gov.samhsa.spirit.wsclient.dto.PatientDto;
import gov.samhsa.spirit.wsclient.exception.SpiritAdapterException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spirit.ehr.ws.client.generated.EhrDomainClientDto;
import com.spirit.ehr.ws.client.generated.EhrPIDClientDto;
import com.spirit.ehr.ws.client.generated.EhrPatientClientDto;
import com.spirit.ehr.ws.client.generated.EhrPatientRsp;

public class SpiritClientHelper {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public EhrPatientClientDto convertFromPatientDto(PatientDto patientDto,
			boolean isBasic, EhrPatientClientDto eClientDto) {
		// Create a patient object
		if (null == eClientDto)
			eClientDto = new EhrPatientClientDto();

		// Set the patient information to look for
		// There are many more search arguments you can use not only these three
		eClientDto.setFamilyName(patientDto.getLastName());
		eClientDto.setGivenName(patientDto.getFirstName());
		eClientDto.setCountry(patientDto.getCountry());
		eClientDto.setSex(patientDto.getGenderCode());
		eClientDto.setBirthdate(patientDto.getBirthDate().toString());
		// Set SSN
		setSsn(eClientDto, patientDto);
		if (!isBasic) {
			// Set the patient additional information
			eClientDto.setEmailHome(patientDto.getEmailHome());
			eClientDto.setStreetAddress(patientDto.getStreetAddress());
			eClientDto.setCity(patientDto.getCity());
			eClientDto.setState(patientDto.getState());
			eClientDto.setZip(patientDto.getZip());
			eClientDto.setHomePhone(patientDto.getHomePhone());
			eClientDto.setMaritalStatus(patientDto.getMaritalStatus());
			eClientDto.setReligion(patientDto.getReligion());
			eClientDto.setRace(patientDto.getRace());
			eClientDto.setLanguage(patientDto.getLanguage());
		}
		return eClientDto;
	}

	public PatientDto convertFromEhrPatientClientDto(
			EhrPatientRsp patientResponse, PatientDto patientDto)
			throws SpiritAdapterException {
		try {
			EhrPatientClientDto ehrPatientClientDto = patientResponse
					.getResponseData().get(0);

			for (EhrPIDClientDto ehrPIDClientDto : ehrPatientClientDto.getPid()) {
				if (ehrPIDClientDto.getPatientIDType().equals("RRI"))
					patientDto.setPatientId(ehrPIDClientDto.getPatientID());
			}

			patientDto.setStreetAddress(ehrPatientClientDto.getStreetAddress());
			patientDto.setCity(ehrPatientClientDto.getCity());
			patientDto.setState(ehrPatientClientDto.getState());
			patientDto.setZip(ehrPatientClientDto.getZip());
			patientDto.setHomePhone(ehrPatientClientDto.getHomePhone());
			patientDto.setSsnNumber(ehrPatientClientDto.getSsnNumber());
			patientDto.setMaritalStatus(ehrPatientClientDto.getMaritalStatus());
			patientDto.setReligion(ehrPatientClientDto.getReligion());
			patientDto.setRace(ehrPatientClientDto.getRace());
			patientDto.setLanguage(ehrPatientClientDto.getLanguage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpiritAdapterException(
					"Local Id is not created by exchange", e);
		}

		return patientDto;
	}

	private void setSsn(EhrPatientClientDto eClientDto, PatientDto patientDto) {
		EhrPIDClientDto ssn = null;
		// Check if there is already an SSN
		for (EhrPIDClientDto pid : eClientDto.getPid()) {
			if (SpiritConstants.DOMAIN_ID_SSN.equals(pid.getDomain()
					.getAuthUniversalID())) {
				// Found SSN
				ssn = pid;
				break;
			}
		}
		if (ssn == null) {
			// If SSN not found, create a new one
			ssn = createPID(patientDto.getSsnNumber(),
					SpiritConstants.DOMAIN_ID_SSN,
					SpiritConstants.DOMAIN_TYPE_DEFAULT,
					SpiritConstants.ID_TYPE_SSN);
			eClientDto.getPid().add(ssn);
		} else {
			// If SSN found, update the number with new value
			ssn.setPatientID(patientDto.getSsnNumber());
		}
	}

	private EhrPIDClientDto createPID(String id, String domainId,
			String domainType, String IdType) {
		EhrPIDClientDto pid = new EhrPIDClientDto();
		pid.setPatientID(id);
		pid.setPatientIDType(IdType);

		EhrDomainClientDto dom = new EhrDomainClientDto();
		dom.setAuthUniversalID(domainId);

		if (domainType != null)
			dom.setAuthUniversalIDType(domainType);
		else
			dom.setAuthUniversalIDType(SpiritConstants.DOMAIN_TYPE_DEFAULT);

		pid.setDomain(dom);

		return pid;
	}

	/* Generates random alphanumeric string */
	public String createC2SLocalIdentifier(String c2sEnvType) {

		StringBuilder localIdIdBuilder = new StringBuilder();
		// localIdIdBuilder.append(SpiritConstants.C2S_ID_PREFIX);
		if (null != c2sEnvType) {
			localIdIdBuilder.append(new String(c2sEnvType).toUpperCase());
			localIdIdBuilder.append(".");
		}
		localIdIdBuilder.append(RandomStringUtils.randomAlphanumeric((6)));
		return localIdIdBuilder.toString();
	}

	public List<EhrPIDClientDto> createPIDList(String patientId,
			String domainId, String domainIdType) {
		EhrPIDClientDto pid = new EhrPIDClientDto();

		EhrDomainClientDto dom = new EhrDomainClientDto();
		dom.setAuthUniversalID(domainId);
		dom.setAuthUniversalIDType(domainIdType);
		pid.setDomain(dom);

		pid.setEhrPIDType(Integer.parseInt(SpiritConstants.C2S_EHRP_ID_TYPE));
		pid.setPatientIDType(SpiritConstants.C2S_PATIENT_ID_TYPE);
		pid.setPatientID(patientId);

		List<EhrPIDClientDto> pids = new ArrayList<EhrPIDClientDto>();
		pids.add(pid);
		return pids;
	}
}
