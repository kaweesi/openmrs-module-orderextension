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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.orderextension.DrugOrderComparator;
import org.openmrs.module.orderextension.DrugRegimen;
import org.openmrs.module.orderextension.ExtendedDrugOrder;
import org.openmrs.module.orderextension.api.OrderExtensionService;
import org.openmrs.module.orderextension.util.DrugConceptHelper;
import org.openmrs.web.controller.PortletController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * The main controller.
 */
@Controller
public class RegimenExtensionController extends PortletController{
	
	protected final Log log = LogFactory.getLog(getClass());
	
	protected final static String DEFAULT_REDIRECT_URL = "/patientDashboard.form?";
	
	
	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/orderextension/extendedregimen";
	
	@RequestMapping(method=RequestMethod.GET)
	protected void populateModel(HttpServletRequest request, Map<String, Object> model)
	{	
		Patient patient = Context.getPatientService().getPatient((Integer)model.get("patientId"));
		
		List<DrugOrder> allDrugOrders = Context.getOrderService().getDrugOrdersByPatient(patient);
		List<DrugOrder> drugOrdersNonContinuous = new ArrayList<DrugOrder>();
		List<DrugOrder> drugOrdersContinuous = new ArrayList<DrugOrder>();
		List<DrugRegimen> cycles = new ArrayList<DrugRegimen>();
		List<DrugRegimen> fixedLengthRegimens = new ArrayList<DrugRegimen>();
		
		List<Concept> inclusionConcepts = getInclusionIndications();
		
		StringBuilder regimenHeading = new StringBuilder();
		
		for(DrugOrder drugOrder : allDrugOrders)
		{
			if(drugOrder.getDiscontinuedDate() != null || drugOrder.getAutoExpireDate() != null)
			{
				//now check if they are one of the indications that we want to show in the calendar
				if(drugOrder instanceof ExtendedDrugOrder)
				{
					ExtendedDrugOrder edo = (ExtendedDrugOrder)drugOrder;
					if(edo.getIndication() != null && inclusionConcepts.contains(edo.getIndication()))
					{
						drugOrdersNonContinuous.add(drugOrder);
					}
				}
				
			}
			else
			{
				drugOrdersContinuous.add(drugOrder);
			}
			
			if (drugOrder instanceof ExtendedDrugOrder) {
				ExtendedDrugOrder edo = (ExtendedDrugOrder)drugOrder;
				if(edo.getGroup() != null && edo.getGroup() instanceof DrugRegimen) {
					DrugRegimen regimen = (DrugRegimen)edo.getGroup();
					if (regimen.isCyclical())
					{
						if(!cycles.contains(regimen))
						{
							cycles.add(regimen);
							if((drugOrder.isCurrent() || drugOrder.isFuture()) && !regimenHeading.toString().contains(regimen.getName()))
							{
								if(regimenHeading.length() > 0)
								{
									regimenHeading.append(", ");
								}
								regimenHeading.append(regimen.getName());
							}
						}
					}
					else{
						if(drugOrder.isCurrent()  && !regimenHeading.toString().contains(regimen.getName()))
						{
							if(regimenHeading.length() > 0)
							{
								regimenHeading.append(", ");
							}
							regimenHeading.append(regimen.getName());
						}
						
						if(regimen.getLastDrugOrderEndDate() != null && !fixedLengthRegimens.contains(regimen))
						{
							fixedLengthRegimens.add(regimen);
						}
					}
				}
				else
				{
					if(drugOrder.isCurrent() && !drugOrder.isFuture())
					{
						if(regimenHeading.length() > 0)
						{
							regimenHeading.append(", ");
						}
						regimenHeading.append(drugOrder.getDrug().getName());
					}
				}
			}
			else
			{
				if(drugOrder.isCurrent() && !drugOrder.isFuture())
				{
					if(regimenHeading.length() > 0)
					{
						regimenHeading.append(", ");
					}
					regimenHeading.append(drugOrder.getDrug().getName());
				}
			}
		}
		
		Collections.sort(drugOrdersContinuous, new DrugOrderComparator());
		
		DrugConceptHelper drugHelper = new DrugConceptHelper();
		
		model.put("drugOrdersNonContinuous", drugOrdersNonContinuous);
		model.put("regimenHeading", regimenHeading.toString());
		model.put("drugOrdersContinuous", drugOrdersContinuous);
		model.put("cycles", cycles);
		model.put("fixedLengthRegimen", fixedLengthRegimens);
		
		model.put("orderSets", Context.getService(OrderExtensionService.class).getNamedOrderSets(false));
		
		model.put("drugs", drugHelper.getDistinctSortedDrugs());
		
		model.put("indications", drugHelper.getIndications());
		
		model.put("patient", Context.getPatientService().getPatient((Integer)model.get("patientId")));
		
		String redirect = DEFAULT_REDIRECT_URL;
		if(model.get("returnUrl") != null)
		{
			redirect = model.get("returnUrl").toString();
		}
		model.put("redirect", redirect);
	}

	
	
	@Override
    public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException,
        IOException {
	    ModelAndView mav = super.handleRequest(arg0, arg1);
	    return new ModelAndView(SUCCESS_FORM_VIEW, mav.getModel());
    }



	private List<Concept> getInclusionIndications()
	{
		List<Concept> inclusionConcepts = new ArrayList<Concept>();
		
		String inclusionStr = Context.getAdministrationService().getGlobalProperty("orderextension.getIndicationConceptsToIncludeInCalendar");
		
		String[] inclusions = inclusionStr.split(",");
		
		for(String inclusion: inclusions)
		{
			Concept inc = Context.getConceptService().getConceptByUuid(inclusion);
			
			if(inc == null)
			{
				inc = Context.getConceptService().getConcept(Integer.parseInt(inclusion));
			}
			
			if(inc != null)
			{
				inclusionConcepts.add(inc);
			}
		}
		
		return inclusionConcepts;
	}
}
