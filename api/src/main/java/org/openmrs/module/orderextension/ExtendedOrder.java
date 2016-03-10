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
import org.openmrs.Order;
import org.openmrs.Provider;
import org.openmrs.User;

/**
 * Adds the ability to Group an Order
 */
public class ExtendedOrder extends Order implements GroupableOrder {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Provides a means for Grouping the Order
	 */
	private OrderGroup group;

	/**
	 * Default Constructor
	 */
	public ExtendedOrder() {
		super();
	}

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
	public void setOrderer(Provider orderer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean getDiscontinued() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDiscontinued(Boolean discontinued) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getDiscontinuedBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDiscontinuedBy(User discontinuedBy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getDiscontinuedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDiscontinuedDate(Date discontinuedDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Concept getDiscontinuedReason() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDiscontinuedReason(Concept discontinuedReason) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDiscontinuedReasonNonCoded() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDiscontinuedReasonNonCoded(String discontinuedReasonNonCoded) {
		// TODO Auto-generated method stub
		
	}
}
