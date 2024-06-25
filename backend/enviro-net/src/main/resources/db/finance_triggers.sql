DROP TRIGGER IF EXISTS delete_fixed_expenses_estimation_trigger ON finance.budget_plan;
DROP FUNCTION IF EXISTS finance.delete_fixed_expenses_estimations;
DROP TRIGGER IF EXISTS update_fixed_expenses_estimation_trigger ON finance.budget_plan;
DROP FUNCTION IF EXISTS finance.update_fixed_expenses_estimations;
DROP TRIGGER IF EXISTS generate_fixed_expenses_estimations_trigger ON finance.budget_plan;
DROP FUNCTION IF EXISTS finance.generate_fixed_expenses_estimations;

-------------------------------------------------------------------------------------------
-- DELETE FIXED_EXPENSES_ESTIMATIONS WHEN BUDGET_PLAN IS CLOSED (DELETED)
CREATE OR REPLACE FUNCTION finance.delete_fixed_expenses_estimations()
RETURNS TRIGGER AS $$
BEGIN
    -- Log the trigger execution
    RAISE NOTICE 'Trigger delete_fixed_expenses_estimations called for budget_plan ID: %, New status: %', NEW.id, NEW.status;

    -- Check if the new status is 'CLOSED'
    IF NEW.status = 'CLOSED' THEN
        DELETE FROM finance.fixed_expenses_estimation
        WHERE budget_plan = NEW.id;

        -- Log the deletion
        RAISE NOTICE 'Deleted fixed_expenses_estimations for budget_plan ID: %', NEW.id;
    END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER delete_fixed_expenses_estimation_trigger
    AFTER UPDATE OF status ON finance.budget_plan
    FOR EACH ROW
    WHEN (NEW.status = 'CLOSED')
EXECUTE FUNCTION finance.delete_fixed_expenses_estimations();
-------------------------------------------------------------------------------------------
-- UPDATE FIXED_EXPENSES_ESTIMATIONS WHEN BUDGET_PLAN IS UPDATED
CREATE OR REPLACE FUNCTION finance.update_fixed_expenses_estimations()
RETURNS TRIGGER AS $$
DECLARE
    record RECORD;
    new_working_days INT;
    new_amount DOUBLE PRECISION;
BEGIN
    -- Log the trigger execution
    RAISE NOTICE 'Trigger update_fixed_expenses_estimations called for budget_plan ID: %, New fiscal period: % - %', NEW.id, NEW.start_date, NEW.end_date;

    -- Check if either start_date or end_date is updated
    IF (NEW.start_date IS DISTINCT FROM OLD.start_date) OR (NEW.end_date IS DISTINCT FROM OLD.end_date) THEN
        -- Update the start_date and end_date in fixed_expenses_estimation
        UPDATE finance.fixed_expenses_estimation
        SET start_date = NEW.start_date, end_date = NEW.end_date
        WHERE budget_plan = NEW.id;

        -- Recalculate the amount for each fixed_expenses_estimation of type SALARY
        FOR record IN
            SELECT fe.id, fe.employee, om.wage AS wage, om.working_hours AS working_hours,
                   om.overtime_wage AS overtime_wage, fe.overtime_hours AS overtime_hours
            FROM finance.fixed_expenses_estimation fe
            JOIN users.organization_members om ON fe.employee = om.id
            WHERE fe.budget_plan = NEW.id AND fe.type = 'SALARY'
        LOOP
            -- Calculate new working days
            new_working_days := (
                SELECT COUNT(*)
                FROM generate_series(NEW.start_date, NEW.end_date, '1 day'::interval) AS date
                WHERE EXTRACT(ISODOW FROM date) < 6  -- Monday to Friday --TODO holidays
            );

            -- Calculate new amount
            new_amount := (record.wage * record.working_hours * new_working_days) +
                            (record.overtime_wage * record.overtime_hours);

            -- Update the amount in the table
            UPDATE finance.fixed_expenses_estimation
            SET amount = new_amount
            WHERE id = record.id;

            -- Log the amount update
            RAISE NOTICE 'Updated amount for fixed_expenses_estimation ID: %, new amount: %', record.id, new_amount;
        END LOOP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_fixed_expenses_estimation_trigger
    AFTER UPDATE OF start_date, end_date ON finance.budget_plan
    FOR EACH ROW
EXECUTE FUNCTION finance.update_fixed_expenses_estimations();
-------------------------------------------------------------------------------------------
-- GENERATE FIXED_EXPENSES_ESTIMATIONS WHEN BUDGET_PLAN IS CREATED
CREATE OR REPLACE FUNCTION finance.generate_fixed_expenses_estimations()
RETURNS TRIGGER AS $$
DECLARE
    budget_plan_id BIGINT;
    budget_plan RECORD;
    new_working_days INT;
    employee RECORD;
    new_description TEXT;
    new_amount DOUBLE PRECISION;
BEGIN
    -- Get the budget_plan details
    budget_plan_id := NEW.id;

    SELECT * INTO budget_plan
    FROM finance.budget_plan
    WHERE id = budget_plan_id;

    -- Calculate new working days
    new_working_days := (
        SELECT COUNT(*)
        FROM generate_series(NEW.start_date, NEW.end_date, '1 day'::interval) AS date
        WHERE EXTRACT(ISODOW FROM date) < 6  -- Monday to Friday --TODO holidays
    );

    -- Iterate through active organization members
    FOR employee IN
        SELECT *
        FROM users.organization_members om
        JOIN users.users u ON om.id = u.id
        WHERE u.active = TRUE
    LOOP
        -- Create the description
        new_description := CONCAT(employee.name, ' ', employee.surname, ' salary for period ', budget_plan.start_date, ' - ', budget_plan.end_date);
        -- Calculate new amount
        new_amount := (employee.wage * employee.working_hours * new_working_days) + (employee.overtime_wage * 0); -- Assuming 0 overtime_hours for new records

        -- Create a new salary record
        INSERT INTO finance.fixed_expenses_estimation (
            budget_plan, fixed_expense_id, type, start_date, end_date, amount, creator, created_on, description, employee, overtime_hours
        )
        VALUES (
            budget_plan_id, -- budget_plan ID
            0, -- fixed_expense_id, 0
            'SALARY', -- type, assuming 'SALARY'
            budget_plan.start_date, -- start_date from budget_plan
            budget_plan.end_date, -- end_date from budget_plan
            new_amount, -- amount
            budget_plan.author, -- creator
            budget_plan.last_updated_on_date, -- created_on
            new_description, -- description
            employee.id, -- employee
            0 -- overtime_hours, assuming 0 for new records
            );
        END LOOP;
    RETURN NEW;
END;

$$ LANGUAGE plpgsql;
CREATE TRIGGER generate_fixed_expenses_estimations_trigger
    AFTER INSERT ON finance.budget_plan
    FOR EACH ROW
EXECUTE FUNCTION finance.generate_fixed_expenses_estimations();
