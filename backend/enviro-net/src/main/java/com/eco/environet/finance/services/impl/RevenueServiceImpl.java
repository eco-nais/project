package com.eco.environet.finance.services.impl;

import com.eco.environet.finance.dto.RevenueDto;
import com.eco.environet.finance.model.*;
import com.eco.environet.finance.repository.*;
import com.eco.environet.finance.services.RevenueService;
import com.eco.environet.projects.dto.ProjectDto;
import com.eco.environet.projects.model.Project;
import com.eco.environet.projects.repository.ProjectRepository;
import com.eco.environet.users.dto.UserContactDto;
import com.eco.environet.users.model.User;
import com.eco.environet.users.repository.UserRepository;
import com.eco.environet.util.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RevenueServiceImpl implements RevenueService {
    @Value("${baseFrontUrl}")
    private String baseFrontUrl;
    private final RevenueRepository repository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final DonationRepository donationRepository;
    private final ProjectDonationRepository projectDonationRepository;
    private final ProjectRevenueRepository projectRevenueRepository;

    @Override
    public RevenueDto create(RevenueDto newRevenue) {
        RevenueType revenueType;
        try {
           revenueType = RevenueType.valueOf(newRevenue.getType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid type provided: " + newRevenue.getType());
        }
        Revenue revenue = Revenue.revenueBuilder()
                .createdOn(new Timestamp(System.currentTimeMillis()))
                .type(revenueType)
                .amount(newRevenue.getAmount())
                .build();

        if (RevenueType.getAllDonations().contains(revenue.getType())) {
            User donator = userRepository.findById(newRevenue.getDonator().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Donator not found with ID: " + newRevenue.getDonator().getId()));
            Donation newDonation = new Donation(revenue);
            newDonation.setDonator(donator);
            if (revenue.getType() == RevenueType.DONATION){
                donationRepository.save(newDonation);
                RevenueDto dto = Mapper.map(revenue, RevenueDto.class);
                dto.setDonator(new UserContactDto(donator));
                return dto;
            }
            if (revenue.getType() == RevenueType.PROJECT_DONATION) {
                ProjectDonation newProjectDonation = new ProjectDonation(newDonation);
                // TODO is it allowed to use projectRepository here?
                Project project = projectRepository.findById(newRevenue.getProject().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + newRevenue.getProject().getId()));
                newProjectDonation.setProject(project);
                projectDonationRepository.save(newProjectDonation);
                RevenueDto dto = Mapper.map(revenue, RevenueDto.class);
                dto.setDonator(new UserContactDto(donator));
                dto.setProject(Mapper.map(newProjectDonation.getProject(), ProjectDto.class));
                return dto;
            }
            // TODO if (revenue.getType() == RevenueType.LECTURE_DONATION){}
        }
        if (revenue.getType() == RevenueType.PROJECT_REVENUE) {
            // TODO is it allowed to use projectRepository here?
            Project project = projectRepository.findById(newRevenue.getProject().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + newRevenue.getProject().getId()));
            ProjectRevenue newProjectRevenue = new ProjectRevenue(revenue);
            newProjectRevenue.setProject(project);
            projectRevenueRepository.save(newProjectRevenue);
            RevenueDto dto = Mapper.map(revenue, RevenueDto.class);
            dto.setProject(Mapper.map(newProjectRevenue.getProject(), ProjectDto.class));
            return dto;
        }

        repository.save(revenue);
        return Mapper.map(revenue, RevenueDto.class);
    }

    @Override
    public RevenueDto findById(Long id) {
        Revenue revenue = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revenue not found with ID: " + id));
        return Mapper.map(revenue, RevenueDto.class);
    }

    @Override
    public Page<RevenueDto> findAll(List<String> types, String startDate, String endDate, double amountAbove, double amountBelow, Pageable pageable) {
        Specification<Revenue> spec = getSpecification(types, startDate, endDate, amountAbove, amountBelow);
        Page<Revenue> all = repository.findAll(spec, pageable);
        return Mapper.mapPage(all, RevenueDto.class);
    }
    private Specification<Revenue> getSpecification(List<String> types, String startDate, String endDate, double amountAbove, double amountBelow){
        List<RevenueType> typeList = getTypesList(types);
        Timestamp startTimestamp = getDate(startDate);
        Timestamp endTimestamp = getDate(endDate);
        validateDates(startTimestamp, endTimestamp);
        validateAmount(amountAbove, amountBelow);

        return Specification.where(
                     typeList == null ? null : RevenueSpecifications.typeIn(typeList))
                .and(RevenueSpecifications.afterDate(startTimestamp))
                .and(RevenueSpecifications.beforeDate(endTimestamp))
                .and(amountAbove <= 0 ? null : (RevenueSpecifications.amountAbove(amountAbove)))
                .and(amountBelow <= 0 ? null : (RevenueSpecifications.amountBelow(amountBelow)));
    }
    private List<RevenueType> getTypesList(List<String> typesString){
        if (typesString == null || typesString.isEmpty()){
            return null;
        }
        List<RevenueType> result = new ArrayList<>();
        for (String type : typesString){
            RevenueType filtered = RevenueType.valueOf(type);
            result.add(filtered);
        }
        return result;
    }
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static Timestamp getDate(String date) {
        if (date == null || date.isEmpty() || date.equals("undefined")) {
            return null;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            java.util.Date parsedDate = dateFormat.parse(date);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            // Handle the exception or throw a custom exception
            throw new IllegalArgumentException("Invalid date format: [ " + date + " ]", e);
        }
    }
    private boolean isValidDates(Timestamp startTimestamp, Timestamp endTimestamp){
        if (startTimestamp == null && endTimestamp == null) {
            return true;
        }
        if ((startTimestamp != null && endTimestamp == null) || (startTimestamp == null && endTimestamp != null)){
            return true;
        }
        if (startTimestamp != null && endTimestamp != null && startTimestamp.before(endTimestamp)) {
            return true;
        }
        return false;
    }
    private void validateDates(Timestamp startTimestamp, Timestamp endTimestamp) {
        if (!isValidDates(startTimestamp, endTimestamp)){
            throw new IllegalArgumentException("Invalid period [start: " + startTimestamp.toString() + " \nend: " + endTimestamp.toString());
        }
    }
    private void validateAmount(double amountAbove, double amountBellow) {
        if (amountAbove < 0 || amountBellow < 0) {
            throw new IllegalArgumentException("Invalid amount range, must be > 0: [from: " + amountAbove + " \nto: " + amountBellow);
        }
        if (amountBellow < amountAbove && amountBellow != 0){
            throw new IllegalArgumentException("Invalid amount range [from: " + amountAbove + " \nto: " + amountBellow);
        }
    }

    @Override
    public RevenueDto update(RevenueDto revenueDto) {
        Revenue revenue = repository.findById(revenueDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Revenue not found with ID: " + revenueDto.getId()));
        if (revenue.getType()!=RevenueType.PROJECT_REVENUE){
            throw new IllegalArgumentException("Invalid type provided! Type must be PROJECT_REVENUE!");
        }
        revenue.setAmount(revenueDto.getAmount());
        repository.save(revenue);
        return Mapper.map(revenue, RevenueDto.class);
    }

    @Override
    public void delete(Long id) {
        Revenue revenue = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revenue not found with ID: " + id));
        repository.delete(revenue);
    }
}
