package gov.samhsa.pcm.infrastructure.report;

import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

/**
 * A factory for creating {@link JasperReportsMultiFormatView} objects.
 *
 */
public class ReportViewFactory {

	/**
	 * Instantiates a new report view factory.
	 */
	private ReportViewFactory() {
	}

	/**
	 * New jasper reports multi format view.
	 *
	 * @param reportProps
	 *            the report props
	 * @return the jasper reports multi format view
	 */
	public static final JasperReportsMultiFormatView newJasperReportsMultiFormatView(
			ReportProps reportProps) {
		final JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setUrl(reportProps.getTemplateUrl());
		view.setReportDataKey(reportProps.getDatasourceKey());
		return view;
	}
}
