package com.projekt.vhsrental.repository;

import com.projekt.vhsrental.model.User;
import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.model.WaitlistEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitlistEntryRepo extends JpaRepository<WaitlistEntry, Integer> {

    List<WaitlistEntry> findByVhsOrderByAddedAtAsc(VHS vhs);

    boolean existsByUserAndVhs(User user, VHS vhs );

    void deleteByVhs(VHS vhs);

}
