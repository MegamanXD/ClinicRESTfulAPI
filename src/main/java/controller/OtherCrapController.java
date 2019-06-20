package controller;

import model.AvailableDrug;
import model.ICD;
import model.PrescribedDrug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class OtherCrapController {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired private PatientService patientService;
    @Autowired private VisitService visitService;
    @Autowired private PrescriptionService prescriptionService;
    @Autowired private PrescribedDrugService prescribedDrugService;
    @Autowired private OtherCrapService otherCrapService;

    //*************API for all drugs*************//
    @CrossOrigin
    @GetMapping(path = "/available_drugs/all")
    public List<AvailableDrug> getAllAvailableDrugs(){
        return otherCrapService.getAllAvailableDrugs();
    }
    //------------------------------------------------------------------------------------------------//

    //*************API for ICD*************//
    @CrossOrigin
    @GetMapping(path = "/icd/all")
    public List<ICD> getAllICDs(){
        return otherCrapService.getAllICDs();
    }
    //------------------------------------------------------------------------------------------------//

    //Report that counts number of drugs prescribed
    @CrossOrigin
    @GetMapping(path = "/prescribed_drugs/report")
    public String reportDrugsPrescribed() {
        StringBuilder sb = new StringBuilder("Number of drugs prescribed = ");
        List<PrescribedDrug> prescribedDrugs = prescribedDrugService.getAllPrescribedDrug();
        sb.append(prescribedDrugs.size()).append("\n\n");
        for (PrescribedDrug prescribedDrug : prescribedDrugs) {
            sb.append(prescribedDrug).append("\n");
        }
        return sb.toString();
    }

}

