package com.vcsm.service;

import com.vcsm.model.Complaint;
import com.vcsm.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.logging.Logger;

@Service
public class ComplaintService {

    private static final Logger log = Logger.getLogger(ComplaintService.class.getName());

    @Autowired
    private ComplaintRepository complaintRepository;

    public Complaint fileComplaint(Complaint complaint) {
        log.info("Filing complaint for: " + complaint.getResidentName());
        return complaintRepository.save(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAllOrderByCreatedAtDesc();
    }

    public Optional<Complaint> getComplaintById(Long id) {
        return complaintRepository.findById(id);
    }

    public List<Complaint> getComplaintsByStatus(Complaint.ComplaintStatus status) {
        return complaintRepository.findByStatus(status);
    }

    public Complaint updateStatus(Long id, String status, String resolvedBy, String notes) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found: " + id));
        complaint.setStatus(Complaint.ComplaintStatus.valueOf(status.toUpperCase()));
        if (resolvedBy != null && !resolvedBy.isBlank()) complaint.setResolvedBy(resolvedBy);
        if (notes != null && !notes.isBlank()) complaint.setResolutionNotes(notes);
        return complaintRepository.save(complaint);
    }

    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }

    public Map<String, Long> getComplaintStats() {
        Map<String, Long> stats = new LinkedHashMap<>();
        stats.put("total", complaintRepository.count());
        stats.put("open", complaintRepository.countByStatus(Complaint.ComplaintStatus.OPEN));
        stats.put("inProgress", complaintRepository.countByStatus(Complaint.ComplaintStatus.IN_PROGRESS));
        stats.put("resolved", complaintRepository.countByStatus(Complaint.ComplaintStatus.RESOLVED));
        stats.put("closed", complaintRepository.countByStatus(Complaint.ComplaintStatus.CLOSED));
        return stats;
    }

    public Map<String, Long> getComplaintsByCategory() {
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] row : complaintRepository.countByCategory()) {
            map.put(row[0].toString(), (Long) row[1]);
        }
        return map;
    }
}