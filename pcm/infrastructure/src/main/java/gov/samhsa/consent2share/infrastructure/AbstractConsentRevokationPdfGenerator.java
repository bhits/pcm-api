/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.infrastructure;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import gov.samhsa.consent2share.domain.consent.Consent;

/**
 * The Class AbstractConsentRevokationPdfGenerator.
 */
public abstract class AbstractConsentRevokationPdfGenerator {

	/**
	 * Generate consent revokation pdf.
	 *
	 * @param consent
	 *            the consent
	 * @return the byte[]
	 */
	public abstract byte[] generateConsentRevokationPdf(Consent consent);

	/**
	 * Creates the table.
	 *
	 * @param consent
	 *            the consent
	 * @return the pdf p table
	 */
	protected PdfPTable createTable(Consent consent) {
		Font fontbold = FontFactory.getFont("Helvetica", 15, Font.BOLD);

		PdfPTable table = new PdfPTable(new float[] { 0.2f, 0.8f });
		table.setWidthPercentage(100f);
		table.getDefaultCell().setBorder(0);

		Chunk chunk2 = new Chunk("NO EXCEPT", fontbold);
		Chunk chunk3 = new Chunk("NO NEVER", fontbold);
		if (consent.getConsentRevokationType().equals("EMERGENCY ONLY")) {
			chunk2.append("\n(This is the option you chose.)");
		} else if (consent.getConsentRevokationType().equals("NO NEVER")) {
			chunk3.append("\n(This is the option you chose.)");
		}

		table.addCell(new PdfPCell(new Phrase(chunk2)));
		table.addCell(new PdfPCell(
				new Phrase(
						"I Deny Consent for all Participants to access "
								+ "my electronic health information through Consent 2 Share for any purpose, EXCEPT "
								+ "in a medical emergency. By checking this box you agree, \"No, none of the Participants"
								+ " may be given access to my medical records through Consent 2 Share unless it is a medical emergency.\"")));
		table.addCell(new PdfPCell(new Phrase(chunk3)));
		table.addCell(new PdfPCell(
				new Phrase(
						"I Deny Consent for all Participants to access my electronic health information"
								+ " through Consent 2 Share for any purpose, INCLUDING in a medical emergency.")));

		return table;
	}
}
