package rs.ac.uns.acs.nais.TimeseriesDatabaseService.model;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.time.Instant;

@Measurement(name = "fixed_expenses")
public class FixedExpenses {

    @Column(timestamp = true)
    private Instant created;

    @Column
    private String _field; // type

    @Column
    private Double _value; // amount

    @Column(tag = true)
    private String creator_id;

    @Column(tag = true)
    private String employee; // Employee ID or reference

    @Column(tag = true)
    private String start_date;

    @Column(tag = true)
    private String end_date;

    @Column(tag = true)
    private String description;

    public FixedExpenses() {
    }

    public String get_field() {
        return _field;
    }

    public void set_field(String _field) {
        this._field = _field;
    }

    public Double get_value() {
        return _value;
    }

    public void set_value(Double _value) {
        this._value = _value;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }
}
