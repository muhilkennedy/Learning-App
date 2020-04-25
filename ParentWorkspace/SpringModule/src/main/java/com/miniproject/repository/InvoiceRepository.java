package com.miniproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.miniproject.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

	String findInvoiceQuery = "select inv from Invoice inv where inv.invoiceId = :invoiceId";
	
	@Query(findInvoiceQuery)
	Invoice findInvoice(@Param("invoiceId") int invoiceId);
	
}
