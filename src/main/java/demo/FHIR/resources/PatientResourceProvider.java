package demo.FHIR.resources;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PatientResourceProvider implements IResourceProvider {

    public PatientResourceProvider() {    }
    private Map<String, Patient> patientMap = new HashMap<String, Patient>();

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Patient.class;
    }

    @Search
    public List<Patient> search(@RequiredParam(name = Patient.SP_FAMILY) StringParam theParam) {
        List<Patient> patients = new ArrayList<Patient>();
        patients = this.searchByFamilyName(theParam.getValue());
        return patients;
    }

    @Read()
    public Patient read(@IdParam IdType theId) {
        loadDummyPatients();
        Patient retVal = patientMap.get(theId.getIdPart());
        if (retVal == null) {
            throw new ResourceNotFoundException(theId);
        }
        return retVal;
    }

    private void loadDummyPatients() {
        Patient patient = new Patient();
        patient.setId("1");
        patient.addIdentifier().setSystem("http://optum.com/MRNs").setValue("007");
        patient.addName().setFamily("Chakravarty").addGiven("Mithun").addGiven("A");
        patient.addAddress().addLine("Address Line 1");
        patient.addAddress().setCity("Mumbai");
        patient.addAddress().setCountry("India");
        patient.addTelecom().setValue("111-111-1111");
        this.patientMap.put("1", patient);
    }

    private List<Patient> searchByFamilyName(String familyName){
        List<Patient> retPatients;
        loadDummyPatients();
        retPatients = patientMap.values()
                .stream()
                .filter(next -> familyName.toLowerCase().equals(next.getNameFirstRep().getFamily().toLowerCase()))
                .collect(Collectors.toList());
        return retPatients;
    }
}
