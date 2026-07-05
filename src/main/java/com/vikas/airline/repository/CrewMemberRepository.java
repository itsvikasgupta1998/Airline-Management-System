package com.vikas.airline.repository;

import com.vikas.airline.entity.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {}