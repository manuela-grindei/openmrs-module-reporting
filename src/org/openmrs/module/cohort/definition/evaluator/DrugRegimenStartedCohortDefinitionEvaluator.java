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
package org.openmrs.module.cohort.definition.evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.cohort.definition.CohortDefinition;
import org.openmrs.module.cohort.definition.DrugRegimenStartedCohortDefinition;
import org.openmrs.module.cohort.definition.DrugsStartedCohortDefinition;
import org.openmrs.module.cohort.query.service.CohortQueryService;
import org.openmrs.module.evaluation.EvaluationContext;
import org.openmrs.module.regimen.CohortRegimenHistory;

/**
 * 
 */
@Handler(supports={DrugRegimenStartedCohortDefinition.class})
public class DrugRegimenStartedCohortDefinitionEvaluator implements CohortDefinitionEvaluator {

	private Log log = LogFactory.getLog(getClass());
	
	/**
	 * Default Constructor
	 */
	public DrugRegimenStartedCohortDefinitionEvaluator() {}
	
	/**
     * @see CohortDefinitionEvaluator#evaluateCohort(CohortDefinition, EvaluationContext)
     */
    public Cohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) {
    	DrugRegimenStartedCohortDefinition definition = (DrugRegimenStartedCohortDefinition) cohortDefinition;
	
    	CohortRegimenHistory regimenHistory = new CohortRegimenHistory(context.getBaseCohort());    	

    	// Only search for patients that started a first line regimen between dates
    	if (definition.getStartedFirstLineOnly()) { 
    		return regimenHistory.getPatientsStartedFirstLineRegimenBetweenDates(
    				definition.getStartedOnOrAfter(), definition.getStartedOnOrBefore());
    	}
    	
    	// Otherwise, search for patients that started any regimen between dates
    	return regimenHistory.getPatientsStartedRegimenBetweenDates(
				definition.getStartedOnOrAfter(), definition.getStartedOnOrBefore());

    }
}