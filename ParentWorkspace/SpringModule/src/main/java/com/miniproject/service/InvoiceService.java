package com.miniproject.service;

import com.miniproject.model.Invoice;
import com.miniproject.model.Orders;
import com.miniproject.model.User;

public interface InvoiceService {

	void save(Invoice invoice);

	Invoice findInvoice(int id);

	Invoice generateInvoiceDocument(User user, Orders order) throws Exception;

}
