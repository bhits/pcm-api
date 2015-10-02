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
package gov.samhsa.acs.brms.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The Class RuleExecutionContainer.
 */
@XmlRootElement(name="ruleExecutionContainer")
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleExecutionContainer {
	
	/** The execution response list. */
	@XmlElementWrapper(name="executionResponseList")
	@XmlElement(name="executionResponse")
	private List<RuleExecutionResponse> executionResponseList;
	
	/** The size. */
	@XmlTransient
	private int size;

	/**
	 * Instantiates a new rule execution container.
	 */
	public RuleExecutionContainer()
	{
		executionResponseList = new ArrayList<RuleExecutionResponse>();
	}

	
	/**
	 * Gets the execution response list.
	 *
	 * @return the execution response list
	 */
	public List<RuleExecutionResponse> getExecutionResponseList() {
		return executionResponseList;
	}

	/**
	 * Sets the execution response list.
	 *
	 * @param executionResponseList the new execution response list
	 */
	public void setExecutionResponseList(
			List<RuleExecutionResponse> executionResponseList) {
		this.executionResponseList = executionResponseList;
	}

	/**
	 * Adds the execution response.
	 *
	 * @param ruleExecutionResponse the rule execution response
	 */
	public void addExecutionResponse(RuleExecutionResponse ruleExecutionResponse) {
		getExecutionResponseList().add(ruleExecutionResponse);
	}
	

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	
	public int getSize() {
		return executionResponseList.size();
	}

}
