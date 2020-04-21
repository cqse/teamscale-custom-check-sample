package eu.cqse.check.sample;

import eu.cqse.check.base.EntityCheckBase;
import eu.cqse.check.framework.core.Check;
import eu.cqse.check.framework.core.CheckException;
import eu.cqse.check.framework.core.CheckGroupNames;
import eu.cqse.check.framework.core.ECheckParameter;
import eu.cqse.check.framework.core.EFindingEnablement;
import eu.cqse.check.framework.scanner.ELanguage;
import eu.cqse.check.framework.shallowparser.framework.ShallowEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This class is meant to demonstrate a simple custom check for Teamscale. It
 * creates a new finding for every method encountered in the system.
 */
@Check(name = "Sample Check 1", description = "This is a simple sample check that creates a sample finding for every method in the system.", groupName = CheckGroupNames.BAD_PRACTICE, defaultEnablement = EFindingEnablement.RED, languages = {
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
		createFindingOnFirstLine("This is a sample finding for method " + entity.getName() + ".", entity);
	}
}
