package controller;

import model.Patient;
import model.PrescribedDrug;
import model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class PrescriptionController {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired private PatientService patientService;
    @Autowired private VisitService visitService;
    @Autowired private PrescriptionService prescriptionService;
    @Autowired private PrescribedDrugService prescribedDrugService;
    @Autowired private OtherCrapService otherCrapService;

    //Create new
    @CrossOrigin
    @PostMapping(path = "/prescriptions/add")
    public int addPrescription(@RequestBody Prescription prescription){
        for (PrescribedDrug prescribedDrug : prescription.getPrescribedDrug()){
            prescribedDrug.setPrescription(prescription);
        }
        prescription.setVisit(visitService.getVisitByID(prescription.getVisit().getId()));
        return prescriptionService.savePrescription(prescription);
    }

    //Update exisiting
    @CrossOrigin
    @PutMapping(path = "/prescriptions/update")
    public int updatePrescription(@RequestBody Prescription prescription){
        if (prescriptionService.getPrescriptionByID(prescription.getId()) == null)
            return -1;

        else{
            prescription.setVisit(prescriptionService.getPrescriptionByID(prescription.getId()).getVisit());
            for (PrescribedDrug prescribedDrug : prescribedDrugService.getPrescribedDrugByPrescription(prescription)) {
                prescribedDrugService.deletePrescribedDrug(prescribedDrug);
            }
            for (PrescribedDrug prescribedDrug : prescription.getPrescribedDrug()) {
                prescribedDrug.setPrescription(prescription);
            }
            return prescriptionService.updatePrescription(prescription);
        }
    }

    //Delete
    @CrossOrigin
    @DeleteMapping(path = "/prescriptions/delete/{id}")
    public String deletePrescription(@PathVariable int id){
        Prescription toBeDeleted = prescriptionService.getPrescriptionByID(id);
        if (toBeDeleted == null)
            return "Can't delete non-existing prescription :)";
        else{
            prescriptionService.deletePrescription(toBeDeleted);
            return "Deleted:\n" + toBeDeleted;
        }
    }

    //Get all
    @CrossOrigin
    @GetMapping(path = "/prescriptions/all")
    public List<Prescription> getAllPrescription(){
        return prescriptionService.getAllPrescription();
    }
}
