 package gov.va.ds4p.cas.providers;
 
 import gov.va.ds4p.policy.reference.MedicationListSensitivityRules;
 import java.io.StringReader;
 import java.io.StringWriter;
 import javax.xml.bind.JAXBContext;
 import javax.xml.bind.Marshaller;
 import javax.xml.bind.Unmarshaller;
 
 public class MedicationsProvider
 {
   public MedicationListSensitivityRules createMedicationRulesObjectFromXML(String ruleXML)
   {
     MedicationListSensitivityRules obj = null;
     try {
       JAXBContext context = JAXBContext.newInstance(new Class[] { MedicationListSensitivityRules.class });
       Unmarshaller unmarshaller = context.createUnmarshaller();
       StringReader sr = new StringReader(ruleXML);
 
       Object o = unmarshaller.unmarshal(sr);
       obj = (MedicationListSensitivityRules)o;
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return obj;
   }
 
   public String createMedicationRulesXMLFromObject(MedicationListSensitivityRules obj) {
     String res = "";
     try
     {
       JAXBContext context = JAXBContext.newInstance(new Class[] { MedicationListSensitivityRules.class });
       Marshaller marshaller = context.createMarshaller();
       StringWriter sw = new StringWriter();
       marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
 
       marshaller.marshal(obj, sw);
 
       res = sw.toString();
     }
     catch (Exception ex) {
       ex.printStackTrace();
     }
 
     return res;
   }
 }

