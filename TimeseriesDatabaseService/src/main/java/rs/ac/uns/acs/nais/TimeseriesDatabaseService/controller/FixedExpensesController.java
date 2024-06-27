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

    @DeleteMapping("delete")
    public ResponseEntity<Boolean> delete(@RequestParam("creatorId") String creatorId,
                                              @RequestParam("createdOn") Instant createdOn) {
        if (service.delete(creatorId, createdOn)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("monthly")
    public ResponseEntity<List<FixedExpenses>> monthly(@RequestParam("start_date") String start_date, @RequestParam("field") String field) {
        return new ResponseEntity<>(service.monthlySum(start_date, field), HttpStatus.OK);
    }

    @GetMapping("aggregateByCreator")
    public ResponseEntity<List<FixedExpenses>> aggregateByCreator(
            @RequestParam(value = "start_date", defaultValue = "0") String start_date,
            @RequestParam(value = "end_date", required = false) String end_date,
            @RequestParam(value = "field", defaultValue = "SALARY") String field) {
        return new ResponseEntity<>(service.aggregateByCreator(start_date, end_date, field), HttpStatus.OK);
    }

    @GetMapping("averageSalary")
    public ResponseEntity<List<FixedExpenses>> averageSalary(
            @RequestParam(value = "start_date", defaultValue = "0") String start_date,
            @RequestParam(value = "end_date", required = false) String end_date) {
        return new ResponseEntity<>(service.averageSalary(start_date, end_date), HttpStatus.OK);
    }
}
