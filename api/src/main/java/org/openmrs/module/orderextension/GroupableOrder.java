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
import org.openmrs.Encounter;
import org.openmrs.OpenmrsData;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.User;

/**
 * Interface indicating that an Order can be Groupable
 */
public interface GroupableOrder extends OpenmrsData {
	
	public Integer getOrderId();
	public void setOrderId(Integer orderId);
	public Patient getPatient();
	public void setPatient(Patient patient);
	public OrderType getOrderType();
	public void setOrderType(OrderType orderType);
	public Concept getConcept();
	public void setConcept(Concept concept);
	public String getInstructions();
	public void setInstructions(String instructions);
	public Date getStartDate();
	public void setStartDate(Date startDate);
	public Date getAutoExpireDate();
	public void setAutoExpireDate(Date autoExpireDate);
	public Encounter getEncounter();
	public void setEncounter(Encounter encounter);
	public Provider getOrderer();
	public void setOrderer(Provider orderer);
	public Boolean getDiscontinued();
	public void setDiscontinued(Boolean discontinued);
	public User getDiscontinuedBy();
	public void setDiscontinuedBy(User discontinuedBy);
	public Date getDiscontinuedDate();
	public void setDiscontinuedDate(Date discontinuedDate);
	public Concept getDiscontinuedReason();
	public void setDiscontinuedReason(Concept discontinuedReason);
	public String getDiscontinuedReasonNonCoded();
	public void setDiscontinuedReasonNonCoded(String discontinuedReasonNonCoded);
	public String getAccessionNumber();
	public void setAccessionNumber(String accessionNumber);

	/**
	 * @return the OrderGroup for the Order
	 */
	public OrderGroup getGroup();
	
	/**
	 * @param group the OrderGrup for the Order
	 */
	public void setGroup(OrderGroup group);
}
