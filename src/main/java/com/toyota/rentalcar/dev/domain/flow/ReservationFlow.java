package com.toyota.rentalcar.dev.domain.flow;

import com.toyota.rentalcar.dev.domain.Reservation;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReservationFlow {

    public enum Step {
        Dates(0),
        Extras(1),
        Guest(2),
        Review(3),
        Payment(4);

        int flowStep;

        Step(int flowStep) { this.flowStep = flowStep;}

        public static Step from(int flowStep){
            switch (flowStep){
                case 0:
                    return Dates;
                case 1:
                    return Extras;
                case 2:
                    return Guest;
                case 3:
                    return Review;
                case 4:
                    return Payment;
                default:
                    return Dates;
            }
        }
    }

    @Valid
    private Reservation reservation = new Reservation();

    private List<StepDescription> stepDescriptions = new ArrayList<>();

    private Set<Step> completedSteps = new HashSet<>();

    private Step activeStep = Step.Dates;

    public ReservationFlow(){
        stepDescriptions.add(new StepDescription(0, "Dates", "Choose your reservation dates"));
        stepDescriptions.add(new StepDescription(1, "Extras", "Select optional extras"));
        stepDescriptions.add(new StepDescription(2, "Guests", "Provide guest details"));
        stepDescriptions.add(new StepDescription(3, "Review", "Verify your reservation"));
        stepDescriptions.add(new StepDescription(4, "Payment", "Provide payment details"));
    }

    public Reservation getReservation() { return reservation; }

    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public void setActive(Step step) {
        activeStep = step;
    }

    public Step getActiveStep() {
        return activeStep;
    }

    public StepDescription getActiveStepDescription() {
        return stepDescriptions.get(activeStep.flowStep);
    }

    public void completeStep(Step step) {
        completedSteps.add(step);
    }

    public void incompleteStep(Step step) {
        completedSteps.remove(step);
    }

    public boolean isActive(Step step) {
        return step == activeStep;
    }

    public boolean isCompleted(Step step) {
        return completedSteps.contains(step);
    }

    public void enterStep(Step step) {
        setActive(step);
        incompleteStep(step);
    }

    private static class StepDescription {
        private int flowStep;
        private String title;
        private String description;

        public StepDescription(int flowStep, String title, String description) {
            this.flowStep = flowStep;
            this.title = title;
            this.description = description;
        }

        public int getFlowStep() {
            return flowStep;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        private int normalizedFlowStep() {
            return flowStep + 1;
        }

        public String getFlowStepWithTitle() {
            return normalizedFlowStep() + ". " + title;
        }

        public String getFlowStepWithDescription() {
            return normalizedFlowStep() + ". " + description;
        }
    }
}
