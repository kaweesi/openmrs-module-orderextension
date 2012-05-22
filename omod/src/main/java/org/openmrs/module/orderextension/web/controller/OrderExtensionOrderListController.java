/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.orderextension.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.DrugOrder;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.orderextension.DrugRegimen;
import org.openmrs.module.orderextension.OrderSet;
import org.openmrs.module.orderextension.api.OrderExtensionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Primary Controller for administering orders
 */
@Controller
public class OrderExtensionOrderListController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Shows the page to list order sets
	 */
	@RequestMapping(value = "/module/orderextension/orderList")
	public void listOrders(ModelMap model, @RequestParam(value="patientId", required=true) Integer patientId) {
		Patient patient = Context.getPatientService().getPatient(patientId);
		model.addAttribute("patient", patient);
		
		List<DrugRegimen> regimens = Context.getService(OrderExtensionService.class).getOrderGroups(patient, DrugRegimen.class);
		model.addAttribute("regimens", regimens);
		
		List<DrugOrder> drugOrders = Context.getOrderService().getDrugOrdersByPatient(patient);
		for (DrugRegimen r : regimens) {
			drugOrders.removeAll(r.getMembers());
		}
		model.addAttribute("drugOrders", drugOrders);
		
		model.addAttribute("orderSets", Context.getService(OrderExtensionService.class).getNamedOrderSets(false));
	}
	
	/**
	 * Shows the page to list order sets
	 */
	@RequestMapping(value = "/module/orderextension/addOrdersFromSet")
	public String addOrdersFromSet(ModelMap model, 
								 @RequestParam(value="patientId", required=true) Integer patientId,
								 @RequestParam(value="orderSet", required=true) Integer orderSetId,
								 @RequestParam(value="startDate", required=true) Date startDate,
								 @RequestParam(value="numCycles", required=false) Integer numCycles) {
		
		Patient patient = Context.getPatientService().getPatient(patientId);
		OrderSet orderSet = Context.getService(OrderExtensionService.class).getOrderSet(orderSetId);
		Context.getService(OrderExtensionService.class).addOrdersForPatient(patient, orderSet, startDate, numCycles);
		return "redirect:orderList.form?patientId="+patientId;
	}
}
