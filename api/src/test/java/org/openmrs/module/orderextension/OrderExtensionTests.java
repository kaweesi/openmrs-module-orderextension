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

import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.Date;

/**
 * Tests for Order Extension
 */
public class OrderExtensionTests extends BaseModuleContextSensitiveTest {

	@Test
	public void shouldSuccessfullyMergePatientsWithExtendedDrugOrders() throws Exception {

		Patient p1 = Context.getPatientService().getPatient(2);
		Patient p2 = Context.getPatientService().getPatient(6);

		{
			ExtendedDrugOrder drugOrder = new ExtendedDrugOrder();
			drugOrder.setOrderType(Context.getOrderService().getOrderType(1));
			drugOrder.setPatient(p1);
			drugOrder.setConcept(Context.getConceptService().getConcept(88));
			drugOrder.setDrug(Context.getConceptService().getDrug(3));
			drugOrder.setAdministrationInstructions("Take 2 and call me in the morning");
			drugOrder.setStartDate(new Date());
			Context.getOrderService().saveOrder(drugOrder, null);
		}

		Context.getPatientService().mergePatients(p1, p2);
	}
}