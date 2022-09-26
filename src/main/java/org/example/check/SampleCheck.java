package org.example.check;

import eu.cqse.check.base.EntityCheckBase;
import eu.cqse.check.framework.core.*;
import eu.cqse.check.framework.scanner.ELanguage;
import eu.cqse.check.framework.shallowparser.framework.ShallowEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This class is meant to demonstrate a simple custom check for Teamscale. The
 * description of a check is defined in `src/main/resources/check-descriptions/Sample Check.md`.
 * The name of the file has to match the name of the check, except a few special characters (:\/*"?|<>').
 * Those have to be replaced by a dash (-), as they would result in an invalid file name on Windows.
 *
 * Alternatively the description can also be given as `description` attribute in the `@Check` annotation.
 *
 * This check creates a new finding for every method encountered in the system.
 */
@Check(name = "Sample Check", groupName = QualityModel.Groups.BAD_PRACTICE, defaultEnablement = EFindingEnablement.RED, languages = {
		ELanguage.JAVA }, parameters = { ECheckParameter.ABSTRACT_SYNTAX_TREE })
public class SampleCheck extends EntityCheckBase {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	protected String getXPathSelectionString() {
		// Select all methods
		return "//METHOD";
	}

	// This method is now called for every entity selected by the XPath
	// expression in getXPathSelectionString.
	@Override
	protected void processEntity(ShallowEntity entity) throws CheckException {
		LOGGER.info("Checking entity {}", entity);
		// Create a new finding on the first line of each method.
		buildFinding("This is a sample finding for method " + entity.getName() + ".", buildLocation().forEntityFirstLine(entity)).createAndStore();
	}
}
