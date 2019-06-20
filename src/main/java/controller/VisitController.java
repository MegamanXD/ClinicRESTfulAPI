package controller;

import model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class VisitController {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired private PatientService patientService;
    @Autowired private VisitService visitService;
    @Autowired private PrescriptionService prescriptionService;
    @Autowired private PrescribedDrugService prescribedDrugService;
    @Autowired private OtherCrapService otherCrapService;

    //Create new
    @CrossOrigin
    @PostMapping(path = "/visits/add")
    public int addVisit(@RequestBody Visit visit){
        visit.setPatient(patientService.getPatientByID(visit.getPatient().getId()));
        return visitService.saveVisit(visit);
    }

    //Update existing
    @CrossOrigin
    @PutMapping(path = "/visits/update")
    public int updateVisit(@RequestBody Visit visit){
        if (visitService.getVisitByID(visit.getId()) == null)
            return -1;
        else{
            visit.setPatient(visitService.getVisitByID(visit.getId()).getPatient());
            return visitService.updateVisit(visit);
        }
    }

    //Delete
    @CrossOrigin
    @DeleteMapping(path = "/visits/delete/{id}")
    public String deleteVisit(@PathVariable int id){
        Visit toBeDeleted = visitService.getVisitByID(id);

        if (toBeDeleted == null)
            return "Can't delete non-existing visit :)";
        else{
            if (prescriptionService.getPrescriptionByVisit(id).isEmpty())
                visitService.deleteVisit(toBeDeleted);
            else{
                prescriptionService.autoDeletePrescriptionByVisit(id);
                visitService.deleteVisit(toBeDeleted);
            }
            return "Deleted:\n" + toBeDeleted;
        }
    }

    //Get all
    @CrossOrigin
    @GetMapping(path = "/visits/all")
    public List<Visit> getAllVisit(){
        return visitService.getAllVisit();
    }

    //Get Visits by Days
    @CrossOrigin
    @GetMapping(path = "/visits/searchDate/{startDate}/{endDate}")
    public List<Visit> searchVisitBy2Date(@PathVariable String startDate, @PathVariable String endDate){
        try {
            return visitService.getVisitsBetween2Dates(dateFormat.parse(startDate),dateFormat.parse(endDate));
        } catch (ParseException e) {
            return null;
        }
    }

    //Get Visits by Patient
    @CrossOrigin
    @GetMapping(path = "/visits/searchPatient/{patientID}")
    public List<Visit> searchVisitByPatient(@PathVariable int patientID){
        return visitService.getVisitByPatient(patientID);
    }

    //Report that counts number of visits/day
    @CrossOrigin
    @GetMapping(path = "/visits/report/{date}")
    public String reportVisitsInADay(@PathVariable String date){
        StringBuilder sb = new StringBuilder();
        try {
            List<Visit> foundVisits = visitService.getVisitsByDate(dateFormat.parse(date));
            sb.append("Number of visits found = ").append(foundVisits.size()).append("\n\n");
            for (Visit visit : foundVisits) {
                sb.append(visit).append("\n\n");
            }
            return sb.toString();
        } catch (ParseException e) {
            return "Wrong date format! The correct format is: dd-mm-yyyy";
        }
    }
}
