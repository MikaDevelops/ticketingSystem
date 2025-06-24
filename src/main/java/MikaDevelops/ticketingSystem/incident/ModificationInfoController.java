package MikaDevelops.ticketingSystem.incident;

import MikaDevelops.ticketingSystem.dataRepository.DataBaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/latest_modification/{incidentId}")
public class ModificationInfoController {
    private final DataBaseService dataBaseService;
    public ModificationInfoController(DataBaseService dataBaseService){
        this.dataBaseService = dataBaseService;
    }

    @GetMapping
    public ResponseEntity<ModificationInfo> getLatestModification(@PathVariable long incidentId){
        return ResponseEntity.ok(dataBaseService.getLatestModificationInfo(incidentId));
    }
}
