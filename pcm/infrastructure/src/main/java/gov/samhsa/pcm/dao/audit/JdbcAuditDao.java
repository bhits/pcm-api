package gov.samhsa.pcm.dao.audit;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcAuditDao{
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;	
	
	public JdbcAuditDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	//For prove of concept purpose
	public void readPatientAuditRev(){
		String sql= "SELECT REV FROM PATIENT_AUDIT";
		jdbcTemplate=new JdbcTemplate(dataSource);
		System.out.println(jdbcTemplate.queryForList(sql));
	}
	
//	public String readPatientCreateDate(Long id){
//		String sql="select min(revtstmp) from revinfo join patient_audit on revinfo.rev=patient_audit.rev where patient_audit.id=?";
//		jdbcTemplate=new JdbcTemplate(dataSource);
//		jdbcTemplate.queryForObject(sql,new Object[]{id});
//		
//	}
}
