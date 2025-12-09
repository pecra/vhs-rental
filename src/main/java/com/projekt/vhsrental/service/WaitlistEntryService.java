package com.projekt.vhsrental.service;


import com.projekt.vhsrental.exception.ForbiddenActionException;
import com.projekt.vhsrental.exception.NotFoundException;
import com.projekt.vhsrental.model.User;
import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.model.WaitlistEntry;
import com.projekt.vhsrental.model.WaitlistEntryDTO;
import com.projekt.vhsrental.repository.RentalRepo;
import com.projekt.vhsrental.repository.UserRepo;
import com.projekt.vhsrental.repository.VHSRepo;
import com.projekt.vhsrental.repository.WaitlistEntryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WaitlistEntryService {

    private final WaitlistEntryRepo waitlistEntryRepo;
    private final UserRepo userRepo;
    private final VHSRepo vhsRepo;
    private final RentalRepo rentalRepo;

    public WaitlistEntryService(WaitlistEntryRepo waitlistRepo,
                           UserRepo userRepo,
                           VHSRepo vhsRepo,
                           RentalRepo rentalRepo) {
        this.waitlistEntryRepo = waitlistRepo;
        this.userRepo = userRepo;
        this.vhsRepo = vhsRepo;
        this.rentalRepo = rentalRepo;
    }

    public WaitlistEntry addToWaitlist(WaitlistEntryDTO dto) {

        log.info("Adding user {} to waitlist for VHS {}", dto.getUserId(), dto.getVhsId());

        User user = userRepo.findById(dto.getUserId()).orElseThrow(() -> new NotFoundException("user.not.found"));
        VHS vhs = vhsRepo.findById(dto.getVhsId()).orElseThrow(() -> new NotFoundException("vhs.not.found"));

        if(waitlistEntryRepo.existsByUserAndVhs(user, vhs)){
            throw new ForbiddenActionException("waitlist.already.exists");
        }

        if(!rentalRepo.existsByVhsAndReturnDateIsNull(vhs)){
            throw new ForbiddenActionException("vhs.not.rented");
        }

        WaitlistEntry waitlistEntry = new WaitlistEntry();
        waitlistEntry.setUser(user);
        waitlistEntry.setVhs(vhs);

        return waitlistEntryRepo.save(waitlistEntry);
    }

    public List<WaitlistEntry> getWaitlistEntriesForVHS(Integer vhsId) {

        log.info("Getting waitlist for VHS {}", vhsId);

        VHS vhs = vhsRepo.findById(vhsId).orElseThrow(() -> new NotFoundException("vhs.not.found"));

        return waitlistEntryRepo.findByVhsOrderByCreatedAtAsc(vhs);

    }

    public WaitlistEntry getNextInWaitlist(VHS vhs) {

        log.info("Getting first on the waitlist for VHS {}", vhs.getVhsId());

        List<WaitlistEntry> list = waitlistEntryRepo.findByVhsOrderByCreatedAtAsc(vhs);

        return list.isEmpty() ? null : list.get(0);
    }


}
