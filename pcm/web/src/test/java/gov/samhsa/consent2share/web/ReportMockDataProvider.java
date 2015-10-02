package gov.samhsa.consent2share.web;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ReportMockDataProvider {

	public final List<PcmManagerReportEntryDto> pcmManagerReport() {
		final List<PcmManagerReportEntryDto> l = new ArrayList<>();
		IntStream.iterate(0, i -> {
			l.add(generateData());
			return i < 20 ? ++i : 0;
		}).limit(100).count();
		return l;
	}

	private PcmManagerReportEntryDto generateData() {
		final PcmManagerReportEntryDto d1 = new PcmManagerReportEntryDto();
		Arrays.stream(PcmManagerReportEntryDto.class.getDeclaredFields())
				.filter(f -> f.getType() == String.class)
				.forEach(f -> set(f, d1, randData()));
		d1.setAccountCreatedDate(Date.from(LocalDate.now()
				.plusDays(new Random().nextInt(15)).atStartOfDay()
				.atZone(ZoneId.systemDefault()).toInstant()));
		d1.setSignedConsents(new Random().nextInt(15));
		d1.setUnsignedConsents(new Random().nextInt(15));
		return d1;
	}

	private char randChar() {
		final int i = new Random().nextInt(20) + 65;
		return (char) i;
	}

	private String randData() {
		final StringBuilder b = new StringBuilder();
		IntStream.rangeClosed(0, 10).forEach(i -> b.append(randChar()));
		return b.toString();
	}

	private final void set(Field field, Object obj, Object val) {
		try {
			final boolean accessible = field.isAccessible();
			if (!accessible) {
				field.setAccessible(true);
			}
			org.springframework.util.ReflectionUtils.setField(field, obj, val);
			field.setAccessible(accessible);
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public class PcmManagerReportDto {
		private String date;
		private int totalAccounts;
		private int totalUnsignedConsents;
		private int totalSignedConsents;
		private List<PcmManagerReportEntryDto> pcmManagerReportEntries;

		public String getDate() {
			return date;
		}

		public List<PcmManagerReportEntryDto> getPcmManagerReportEntries() {
			return pcmManagerReportEntries;
		}

		public int getTotalAccounts() {
			return totalAccounts;
		}

		public int getTotalSignedConsents() {
			return totalSignedConsents;
		}

		public int getTotalUnsignedConsents() {
			return totalUnsignedConsents;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public void setPcmManagerReportEntries(
				List<PcmManagerReportEntryDto> pcmManagerReportEntries) {
			this.pcmManagerReportEntries = pcmManagerReportEntries;
		}

		public void setTotalAccounts(int totalAccounts) {
			this.totalAccounts = totalAccounts;
		}

		public void setTotalSignedConsents(int totalSignedConsents) {
			this.totalSignedConsents = totalSignedConsents;
		}

		public void setTotalUnsignedConsents(int totalUnsignedConsents) {
			this.totalUnsignedConsents = totalUnsignedConsents;
		}

	}

	public class PcmManagerReportEntryDto {

		private Date accountCreatedDate;
		private String createdByStaffAdministrator;
		private String staffAdministratorLocation;
		private String patientMrn;
		private String patientLastName;
		private String patientFirstName;
		private String patientGender;
		private int unsignedConsents;
		private int signedConsents;

		public Date getAccountCreatedDate() {
			return accountCreatedDate;
		}

		public String getCreatedByStaffAdministrator() {
			return createdByStaffAdministrator;
		}

		public String getPatientFirstName() {
			return patientFirstName;
		}

		public String getPatientGender() {
			return patientGender;
		}

		public String getPatientLastName() {
			return patientLastName;
		}

		public String getPatientMrn() {
			return patientMrn;
		}

		public int getSignedConsents() {
			return signedConsents;
		}

		public String getStaffAdministratorLocation() {
			return staffAdministratorLocation;
		}

		public int getUnsignedConsents() {
			return unsignedConsents;
		}

		public void setAccountCreatedDate(Date accountCreatedDate) {
			this.accountCreatedDate = accountCreatedDate;
		}

		public void setCreatedByStaffAdministrator(
				String createdByStaffAdministrator) {
			this.createdByStaffAdministrator = createdByStaffAdministrator;
		}

		public void setPatientFirstName(String patientFirstName) {
			this.patientFirstName = patientFirstName;
		}

		public void setPatientGender(String patientGender) {
			this.patientGender = patientGender;
		}

		public void setPatientLastName(String patientLastName) {
			this.patientLastName = patientLastName;
		}

		public void setPatientMrn(String patientMrn) {
			this.patientMrn = patientMrn;
		}

		public void setSignedConsents(int signedConsents) {
			this.signedConsents = signedConsents;
		}

		public void setStaffAdministratorLocation(
				String staffAdministratorLocation) {
			this.staffAdministratorLocation = staffAdministratorLocation;
		}

		public void setUnsignedConsents(int unsignedConsents) {
			this.unsignedConsents = unsignedConsents;
		}

	}
}
