package com.miniproject.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.model.Invoice;
import com.miniproject.model.Orders;
import com.miniproject.model.User;
import com.miniproject.repository.InvoiceRepository;
import com.miniproject.service.InvoiceService;
import com.miniproject.util.InvoiceUtil;

@Service
public class InvoiceServiceImpl implements InvoiceService{

	@Autowired
	private InvoiceRepository invRepo;
	
	@Autowired
	private InvoiceUtil invUtil;
	
	@Override
	@Transactional
	public void save(Invoice invoice) {
		invRepo.save(invoice);
	}

	@Override
	public Invoice findInvoice(int id) {
		return invRepo.findInvoice(id);
	}
	
	@Override
	public Invoice generateInvoiceDocument(User user, Orders order) throws Exception {
		Invoice invoice = new Invoice(invUtil.generateInvoice(user, order), order);
		save(invoice);
		return invoice;
	}
	
}
