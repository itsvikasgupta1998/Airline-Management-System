package com.airline.airlinemanagement.repository;

import com.airline.airlinemanagement.entity.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {}