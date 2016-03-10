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
package org.openmrs.module.orderextension;

import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Provider;
import org.openmrs.User;

/**
 * Adds the ability to Group a DrugOrder, and also adds some additional properties
 */
public class ExtendedDrugOrder extends DrugOrder implements GroupableOrder {

	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor
	 */
	public ExtendedDrugOrder() {
		super();
	}

	/**
	 * Provides a means for Grouping the Order
	 */
	private OrderGroup group;
	
	/**
	 * Provides a means for recording an indication/reason for this DrugOrder
	 */
	private Concept indication;
	
	/**
	 * Provides the means to specify the route of drug administration (eg. IV, IM, PO)
	 */
	private Concept route;
	
	/**
	 * Provides an administration instructions field
	 */
	private String administrationInstructions;

	/**
	 * @return the group
	 */
	public OrderGroup getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(OrderGroup group) {
		this.group = group;
	}

	/**
	 * @return the indication
	 */
	public Concept getIndication() {
		return indication;
	}

	/**
	 * @param indication the indication to set
	 */
	public void setIndication(Concept indication) {
		this.indication = indication;
	}

	/**
	 * @return the route
	 */
	public Concept getRoute() {
		return route;
	}

	/**
	 * @param route the route to set
	 */
	public void setRoute(Concept route) {
		this.route = route;
	}

	/**
	 * @return the administrationInstructions
	 */
	public String getAdministrationInstructions() {
		return administrationInstructions;
	}

	/**
	 * @param administrationInstructions the administrationInstructions to set
	 */
	public void setAdministrationInstructions(String administrationInstructions) {
		this.administrationInstructions = administrationInstructions;
	}

	@Override
	public Date getStartDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStartDate(Date startDate) {
		// TODO Auto-generated method stub
	}

	@Override
	public Provider getOrderer() {
		return super.getOrderer();
	}

	@Override
	public void setOrderer(Provider orderer) {
		//super.setOrderer(orderer);
	}

	@Override
	public Boolean getDiscontinued() {
		return !super.isActive();
	}

	@Override
	public void setDiscontinued(Boolean discontinued) {
		// TODO Auto-generated method stub
	}

	@Override
	public User getDiscontinuedBy() {
		return null;
	}

	@Override
	public void setDiscontinuedBy(User discontinuedBy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getDiscontinuedDate() {
		return super.getDateStopped();
	}

	@Override
	public void setDiscontinuedDate(Date discontinuedDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Concept getDiscontinuedReason() {
		return super.getOrderReason();
	}

	@Override
	public void setDiscontinuedReason(Concept discontinuedReason) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDiscontinuedReasonNonCoded() {
		return super.getOrderReasonNonCoded();
	}

	@Override
	public void setDiscontinuedReasonNonCoded(String discontinuedReasonNonCoded) {
		// TODO Auto-generated method stub
		
	}
}
