package org.example.check;

import java.util.List;
import java.util.Set;

import eu.cqse.check.framework.core.Check;
import eu.cqse.check.framework.core.CheckException;
import eu.cqse.check.framework.core.CheckImplementationBase;
import eu.cqse.check.framework.core.ECheckParameter;
import eu.cqse.check.framework.core.EFindingEnablement;
import eu.cqse.check.framework.core.QualityModel;
import eu.cqse.check.framework.scanner.ELanguage;
import eu.cqse.check.framework.shallowparser.framework.EShallowEntityType;
import eu.cqse.check.framework.shallowparser.framework.ShallowEntity;
import eu.cqse.check.framework.shallowparser.framework.ShallowEntityTraversalUtils;

/**
 * Sample check for testing purposes
 */
@Check(name = "Sample Check", groupName = QualityModel.Groups.BAD_PRACTICE, defaultEnablement = EFindingEnablement.OFF, languages = {
		ELanguage.CS, ELanguage.JAVA }, parameters = { ECheckParameter.ABSTRACT_SYNTAX_TREE })
public class SampleCheck extends CheckImplementationBase {

	/** {@inheritDoc} */
	@Override
	public void execute() throws CheckException {
		buildFinding("Sample finding on file", buildLocation().forElement()).createAndStore();
		List<ShallowEntity> methods = ShallowEntityTraversalUtils.listEntitiesOfTypes(
				context.getAbstractSyntaxTree(getCodeViewOption()), Set.of(EShallowEntityType.METHOD));
		for (ShallowEntity method : methods) {
			buildFinding("Sample finding on method", buildLocation().forEntity(method)).createAndStore();
		}
	}
}
