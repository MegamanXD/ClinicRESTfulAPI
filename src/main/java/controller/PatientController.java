package controller;

import model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class PatientController {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired private PatientService patientService;
    @Autowired private VisitService visitService;
    @Autowired private PrescriptionService prescriptionService;
    @Autowired private PrescribedDrugService prescribedDrugService;
    @Autowired private OtherCrapService otherCrapService;

    //Create new
    @CrossOrigin
    @PostMapping(path = "/patients/add")
    public int addPatient(@RequestBody Patient patient){
        return patientService.savePatient(patient);
    }

    //Update existing
    @CrossOrigin
    @PutMapping(path = "/patients/update")
    public int updatePatient(@RequestBody Patient patient){
        if (patientService.getPatientByID(patient.getId()) == null)
            return -1;
        else
            return patientService.updatePatient(patient);
    }

    //Delete
    @CrossOrigin
    @DeleteMapping(path = "/patients/delete/{id}")
    public String deletePatient(@PathVariable int id){
        Patient toBeDeleted = patientService.getPatientByID(id);
        if (toBeDeleted == null)
            return "Can't delete non-existing patient :)";
        else {
            prescriptionService.autoDeletePrescription(id);
            visitService.autoDeleteVisit(id);
            patientService.deletePatient(toBeDeleted);
            return "Deleted:\n" + toBeDeleted;
        }
    }

    //Get all
    @CrossOrigin
    @GetMapping(path = "/patients/all")
    public List<Patient> getAllPatient(){
        return patientService.getAllPatient();
    }

    //Get Patients by ID
    @CrossOrigin
    @GetMapping(path = "/patients/searchID/{id}")
    public Patient searchPatientByID(@PathVariable int id){
        return patientService.getPatientByID(id);
    }

    //Get Patients by Name
    @CrossOrigin
    @GetMapping(path = "/patients/searchName/{name}")
    public List<Patient> searchPatientByName(@PathVariable String name){
        return patientService.getPatientByName(name);
    }

    //Get Patients by Birthdate
    @CrossOrigin
    @GetMapping(path = "/patients/searchBirthdate/{date}")
    public List<Patient> searchPatientByBirthdate(@PathVariable String date){
        try {
            return patientService.getPatientByBirthdate(dateFormat.parse(date));
        } catch (ParseException e) {
            return null;
        }
    }
}
