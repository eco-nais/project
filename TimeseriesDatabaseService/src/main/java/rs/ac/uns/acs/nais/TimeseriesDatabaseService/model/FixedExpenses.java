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
    private Boolean deleted; // Flag for soft delete

    @Column(tag = true)
    private Integer employee; // Employee ID or reference

    @Column
    private Double _overtime_hours; // Overtime hours for the expense

    @Column
    private String _start_date; // Stored as string

    @Column
    private String _end_date; // Stored as string

    @Column
    private String _description;

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

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_start_date() {
        return _start_date;
    }

    public void set_start_date(String _start_date) {
        this._start_date = _start_date;
    }

    public String get_end_date() {
        return _end_date;
    }

    public void set_end_date(String _end_date) {
        this._end_date = _end_date;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    public Double get_overtime_hours() {
        return _overtime_hours;
    }

    public void set_overtime_hours(Double _overtime_hours) {
        this._overtime_hours = _overtime_hours;
    }
}
