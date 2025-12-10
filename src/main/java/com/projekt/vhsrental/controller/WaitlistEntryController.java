package com.projekt.vhsrental.controller;


import com.projekt.vhsrental.model.WaitlistEntry;
import com.projekt.vhsrental.model.WaitlistEntryDTO;
import com.projekt.vhsrental.service.WaitlistEntryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/waitlist")
public class WaitlistEntryController {

    private final WaitlistEntryService service;

    public WaitlistEntryController(WaitlistEntryService service) {
        this.service = service;
    }
    @PostMapping
    public WaitlistEntry addToWaitlist(@Valid @RequestBody WaitlistEntryDTO dto) {
        log.debug("HTTP POST /api/waitlist");
        return service.addToWaitlist(dto);
    }

    @GetMapping("/vhs/{vhsId}")
    public List<WaitlistEntry> getWaitlistForVhs(@PathVariable Integer vhsId) {
        log.debug("HTTP GET /api/waitlist/vhs/{}", vhsId);
        return service.getWaitlistEntriesForVHS(vhsId);
    }

    @DeleteMapping("{entryId}")
    public void deleteWaitlistEntry(@PathVariable Integer entryId) {
        log.debug("HTTP DELETE /api/waitlist/{}", entryId);
        service.deleteWaitlistEntry(entryId);
    }
}
