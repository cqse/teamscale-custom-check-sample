package org.example.check;

import eu.cqse.check.base.EntityCheckBase;
import eu.cqse.check.framework.core.*;
import eu.cqse.check.framework.core.option.CheckOption;
import eu.cqse.check.framework.scanner.ELanguage;
import eu.cqse.check.framework.shallowparser.framework.ShallowEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;


/**
 * This class is meant to demonstrate a custom check with parameter for Teamscale. It creates a new finding for every
 * method encountered in the system, unless the file containing the code is excluded by the regular expression provided
 * via the parameter.
 */
@Check(name = SampleCheckWithParameter.CHECK_NAME,
		description = "This is a simple sample check that uses a custom parameter.",
		groupName = QualityModel.Groups.BAD_PRACTICE, defaultEnablement = EFindingEnablement.RED,
		languages = { ELanguage.JAVA }, parameters = { ECheckParameter.ABSTRACT_SYNTAX_TREE })
public class SampleCheckWithParameter extends EntityCheckBase {
	static final String CHECK_NAME = "Sample Check with parameter";

	private static final Logger LOGGER = LogManager.getLogger();

	@CheckOption(name = CHECK_NAME + " - Filename exclude",
			description = "Regular expressions to exclude certain files. Leave empty to exclude nothing.")
	private String fileNameExclude = ".*Test.java$";

	private Pattern fileNameExcludePattern;

	@Override
	public void initialize() throws CheckException {
		super.initialize();

		this.fileNameExcludePattern = Pattern.compile(fileNameExclude);
	}

	@Override
	protected String getXPathSelectionString() {
		// Select all methods
		return "//METHOD";
	}

	// This method is now called for every entity selected by the XPath
	// expression in getXPathSelectionString.
	@Override
	protected void processEntity(ShallowEntity entity) throws CheckException {
		// Exclude entities located in files matching the file-name-exclude pattern.
		if (this.fileNameExcludePattern.matcher(this.context.getUniformPath()).matches()) {
			LOGGER.info("Excluding entity {} (file-name-exclude pattern)", entity);
			return;
		}

		LOGGER.info("Checking entity {}", entity);

		// Create a new finding on the first line of each method.
		buildFinding("This is a sample finding for method " + entity.getName()
				+ ", in a file whose name doesn't match " + this.fileNameExclude + ".", buildLocation().forEntityFirstLine(entity)).createAndStore();
	}
}
