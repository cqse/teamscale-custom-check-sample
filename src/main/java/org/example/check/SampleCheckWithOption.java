package org.example.check;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.cqse.check.framework.core.Check;
import eu.cqse.check.framework.core.CheckException;
import eu.cqse.check.framework.core.CheckImplementationBase;
import eu.cqse.check.framework.core.ECheckParameter;
import eu.cqse.check.framework.core.EFindingEnablement;
import eu.cqse.check.framework.core.QualityModel;
import eu.cqse.check.framework.core.option.CheckOption;
import eu.cqse.check.framework.scanner.ELanguage;
import eu.cqse.check.framework.shallowparser.framework.EShallowEntityType;
import eu.cqse.check.framework.shallowparser.framework.ShallowEntity;
import eu.cqse.check.framework.shallowparser.framework.ShallowEntityTraversalUtils;

/**
 * This class is meant to demonstrate a custom check with option for Teamscale. It creates a new
 * finding for every method encountered in the system, unless the file containing the code is
 * excluded by the regular expression provided via the option.
 */
@Check(name = SampleCheckWithOption.CHECK_NAME, description = "This is a simple sample check that uses a custom option.", groupName = QualityModel.Groups.BAD_PRACTICE, defaultEnablement = EFindingEnablement.RED, languages = {
		ELanguage.JAVA }, parameters = { ECheckParameter.ABSTRACT_SYNTAX_TREE })
public class SampleCheckWithOption extends CheckImplementationBase {

	static final String CHECK_NAME = "Sample Check with option";

	private static final Logger LOGGER = LogManager.getLogger();

	@CheckOption(name = CHECK_NAME
			+ " - Filename exclude", description = "Regular expressions to exclude certain files. Leave empty to exclude nothing.")
	private String fileNameExclude = ".*Test.java$";

	private Pattern fileNameExcludePattern;

	@Override
	public void initialize() throws CheckException {
		super.initialize();

		fileNameExcludePattern = Pattern.compile(fileNameExclude);
	}

	@Override
	public void execute() throws CheckException {
		// Select all methods
		List<ShallowEntity> methods = ShallowEntityTraversalUtils.listEntitiesOfTypes(
				context.getAbstractSyntaxTree(getCodeViewOption()), Set.of(EShallowEntityType.METHOD));
		for (ShallowEntity method : methods) {
			processMethod(method);
		}
	}

	private void processMethod(ShallowEntity entity) {
		// Exclude entities located in files matching the file-name-exclude pattern.
		if (fileNameExcludePattern.matcher(context.getUniformPath()).matches()) {
			LOGGER.info("Excluding entity {} (file-name-exclude pattern)", entity);
			return;
		}

		LOGGER.info("Checking entity {}", entity);

		// Create a new finding on the first line of each method.
		buildFinding("This is a sample finding for method " + entity.getName() + ", in a file whose name doesn't match "
				+ fileNameExclude + ".", buildLocation().forEntityFirstLine(entity)).createAndStore();
	}
}
