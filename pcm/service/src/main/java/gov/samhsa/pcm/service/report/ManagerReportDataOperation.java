package gov.samhsa.pcm.service.report;

import java.util.Collection;

import gov.samhsa.pcm.service.dto.ManagerReportEntryDto;


public class ManagerReportDataOperation {
	
	private static final long MILLISTODAY = 1000 * 60 * 60 * 24;

	public static void getAvgCreationActive(Collection<ManagerReportEntryDto>reportEntryDtos){
		
		Double reportlistDtos = reportEntryDtos
				.stream()
				.filter(reportValue -> reportValue.getActiveAccountDateTime() != null)
				.mapToDouble(
						reportValue -> reportValue.getActiveAccountDateTime()
								.getTime()
								- reportValue.getAccountCreatedDate().getTime())
				.average().orElse(0.0);

		System.out.println(reportlistDtos/MILLISTODAY);
	}
	
	public static void getAvgCreationInitial(Collection<ManagerReportEntryDto>reportEntryDtos){
		
		Double reportlistDtos = reportEntryDtos
				.stream()
				.filter(reportValue -> reportValue.getConsentInitialDateTime() != null)
				.mapToDouble(
						reportValue -> reportValue.getConsentInitialDateTime()
								.getTime()
								- reportValue.getAccountCreatedDate().getTime())
				.average().orElse(0.0);

		System.out.println(reportlistDtos/MILLISTODAY);
	}
}
