package rs.ac.uns.acs.nais.TimeseriesDatabaseService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.model.FixedExpenses;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.service.FixedExpensesService;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/fixed-expenses.json")
public class FixedExpensesController {

    private final FixedExpensesService service;

    public FixedExpensesController(FixedExpensesService fixedExpensesService) {
        this.service = fixedExpensesService;
    }

    @PostMapping("save")
    public ResponseEntity<Boolean> save(@RequestBody FixedExpenses fixedExpenses) {
        if (service.save(fixedExpenses)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    // Example endpoint for soft deleting FixedExpenses, if needed
    @DeleteMapping("delete")
    public ResponseEntity<Boolean> delete(@RequestParam("creatorId") String creatorId,
                                              @RequestParam("createdOn") Instant createdOn) {
        if (service.delete(creatorId, createdOn)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("findAll")
    public ResponseEntity<List<FixedExpenses>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("findAllByCreator")
    public ResponseEntity<List<FixedExpenses>> findAllByCreator(@RequestParam("creatorId") String creatorId) {
        return new ResponseEntity<>(service.findAllByCreator(creatorId), HttpStatus.OK);
    }
}
