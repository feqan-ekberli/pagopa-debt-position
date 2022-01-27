package it.gov.pagopa.debtposition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import it.gov.pagopa.debtposition.entity.PaymentPosition;

/**
 * @author aacitelli
 *
 */

@Repository
public interface PaymentPositionRepository extends JpaRepository<PaymentPosition, Long>, 
JpaSpecificationExecutor<PaymentPosition>, PagingAndSortingRepository<PaymentPosition, Long>{
	

}

