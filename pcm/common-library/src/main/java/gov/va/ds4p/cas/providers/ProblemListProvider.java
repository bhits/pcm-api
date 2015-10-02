 package gov.va.ds4p.cas.providers;
 
 import gov.va.ds4p.policy.reference.ProblemListSensitivityRules;
 import java.io.StringReader;
 import java.io.StringWriter;
 import javax.xml.bind.JAXBContext;
 import javax.xml.bind.Marshaller;
 import javax.xml.bind.Unmarshaller;
 
 public class ProblemListProvider
 {
   public ProblemListSensitivityRules createProblemListSensitivityRulesObjectFromXML(String ruleXML)
   {
     ProblemListSensitivityRules obj = null;
     try {
       JAXBContext context = JAXBContext.newInstance(new Class[] { ProblemListSensitivityRules.class });
       Unmarshaller unmarshaller = context.createUnmarshaller();
       StringReader sr = new StringReader(ruleXML);
 
       Object o = unmarshaller.unmarshal(sr);
       obj = (ProblemListSensitivityRules)o;
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return obj;
   }
 
   public String createProblemListSensitivityRulesXMLFromObject(ProblemListSensitivityRules obj) {
     String res = "";
     try
     {
       JAXBContext context = JAXBContext.newInstance(new Class[] { ProblemListSensitivityRules.class });
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
