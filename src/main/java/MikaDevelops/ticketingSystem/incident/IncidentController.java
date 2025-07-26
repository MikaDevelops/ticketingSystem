package MikaDevelops.ticketingSystem.incident;

import MikaDevelops.ticketingSystem.dataRepository.DataBaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentController {

    private final DataBaseService dataBaseService;

    public IncidentController(DataBaseService dataBaseService){
        this.dataBaseService = dataBaseService;
    }

    @GetMapping
    public ResponseEntity<List<Incident>> getAllIncidents(){
        return ResponseEntity.ok( dataBaseService.getAllIncidents() );
    }

    @GetMapping("/{incidentId}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable long incidentId){
        return  ResponseEntity.ok( dataBaseService.getIncidentById(incidentId) );
    }
}
