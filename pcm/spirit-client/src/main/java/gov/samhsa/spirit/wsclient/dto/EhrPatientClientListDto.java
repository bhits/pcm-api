package gov.samhsa.spirit.wsclient.dto;

import java.util.List;

import com.spirit.ehr.ws.client.generated.EhrPatientClientDto;

public class EhrPatientClientListDto {
	
	private List<EhrPatientClientDto> ehrPatientClientListDto;
	private String stateId;	
	
	public EhrPatientClientListDto() {
		super();
	}
	public EhrPatientClientListDto(
			List<EhrPatientClientDto> ehrPatientClientListDto, String stateId) {
		super();
		this.ehrPatientClientListDto = ehrPatientClientListDto;
		this.stateId = stateId;
	}
	public List<EhrPatientClientDto> getEhrPatientClientListDto() {
		return ehrPatientClientListDto;
	}
	public void setEhrPatientClientListDto(
			List<EhrPatientClientDto> ehrPatientClientListDto) {
		this.ehrPatientClientListDto = ehrPatientClientListDto;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
}
