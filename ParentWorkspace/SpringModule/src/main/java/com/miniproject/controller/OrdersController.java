package com.miniproject.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.messages.DocumentResponse;
import com.miniproject.messages.GenericResponse;
import com.miniproject.messages.Response;
import com.miniproject.model.OrderDetail;
import com.miniproject.model.Orders;
import com.miniproject.model.User;
import com.miniproject.service.LoginService;
import com.miniproject.service.OrdersService;
import com.miniproject.util.CommonUtil;
import com.miniproject.util.JWTUtil;
import com.miniproject.util.LogUtil;
import com.miniproject.util.ResponseUtil;

/**
 * @author Muhil Kennedy
 * contains endpoints related to orders.
 */
@RestController
@RequestMapping("order")
public class OrdersController {

	@Autowired
	private LoginService userService;
	
	@Autowired
	private OrdersService orderService;
	
	@Autowired
	private JWTUtil jwtTokenUtil;

	@RequestMapping(value = "/getInvoiceDocument", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<DocumentResponse> getInvoice(
			@RequestParam(value = "orderId", required = true) String orderId, HttpServletRequest request) {
		GenericResponse<DocumentResponse> response = new GenericResponse<>();
		DocumentResponse docResponse = new DocumentResponse();
		try {
			docResponse.setFileContent(orderService.getDocumentContentByOrderId(Integer.parseInt(orderId)));
			response.setStatus(Response.Status.OK);
			response.setData(docResponse);
		} catch (Exception ex) {
			LogUtil.getLogger().error("getInvoice : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(value = "/createOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Orders> createOrder(@RequestParam(value = "email", required = false) String email,
			@RequestBody List<OrderDetail> orderDetails, HttpServletRequest request) {
		GenericResponse<Orders> response = new GenericResponse<>();
		try {
			if (CommonUtil.isNullOrEmptyString(email)) {
				email = jwtTokenUtil.getUserEmailFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));
			}
			User user = userService.findActiveUser(email);
			if (user != null) {
				orderService.createOrder(user, orderDetails);
			} else {
				response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception ex) {
			LogUtil.getLogger().error("createOrder : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = "/getOrder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Orders> getOrder(@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "userId", required = false) String userId, HttpServletRequest request) {
		GenericResponse<Orders> response = new GenericResponse<>();
		try {
			if (!CommonUtil.isNullOrEmptyString(orderId)) {
				Orders order = orderService.findOrderById(Integer.parseInt(orderId));
				if (order != null) {
					response.setData(ResponseUtil.cleanUpOrderResponse(order));
					response.setStatus(Response.Status.OK);
				} else {
					response.setStatus(Response.Status.NO_CONTENT);
				}
			} else if (!CommonUtil.isNullOrEmptyString(userId)) {
				List<Orders> orders = orderService.findOrderByUserId(Integer.parseInt(userId));
				if (orders != null && orders.size() > 0) {
					response.setDataList(ResponseUtil.cleanUpOrderResponse(orders));
					response.setStatus(Response.Status.OK);
				} else {
					response.setStatus(Response.Status.NO_CONTENT);
				}
			}

		} catch (Exception ex) {
			LogUtil.getLogger().error("createOrder : " + ex);
			List<String> msg = Arrays.asList(ex.getMessage());
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}
