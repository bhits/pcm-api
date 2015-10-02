package gov.samhsa.acs.documentsegmentation.valueset.dto;



import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "valueSetQueryListDto")
@XmlRootElement(name = "valueSetQueryListDto")
public class ValueSetQueryListDto implements Serializable {

	private static final long serialVersionUID = 7222161222016840595L;
	public ValueSetQueryListDto(){
		
	}	
	
	@XmlElement(name = "valueSetQueryDtos")
	private Set<ValueSetQueryDto> valueSetQueryDtos = new HashSet<ValueSetQueryDto>();

	public Set<ValueSetQueryDto> getValueSetQueryDtos() {
		return valueSetQueryDtos;
	}

	public void setValueSetQueryDtos(Set<ValueSetQueryDto> valueSetQueryDtos) {
		this.valueSetQueryDtos = valueSetQueryDtos;
	}

}

