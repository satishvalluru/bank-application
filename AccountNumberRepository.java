package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.model.AccountNumberEntity;

@Repository
public interface AccountNumberRepository extends JpaRepository<AccountNumberEntity, Long> {

	@Query(" select acentity from AccountNumberEntity acentity where acno=:acno")
	public Optional<AccountNumberEntity> findByAccNumber(@Param("acno") long acno);
}
