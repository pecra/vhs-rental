package com.projekt.vhsrental.repository;

import com.projekt.vhsrental.model.VHS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VHSRepo extends JpaRepository<VHS, Integer> {
}
