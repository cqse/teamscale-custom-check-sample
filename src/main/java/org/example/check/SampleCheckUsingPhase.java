package org.example.check;

import java.util.List;

import org.conqat.lib.commons.collections.CollectionUtils;

import eu.cqse.check.framework.core.Check;
import eu.cqse.check.framework.core.CheckException;
import eu.cqse.check.framework.core.CheckGroupNames;
import eu.cqse.check.framework.core.CheckImplementationBase;
import eu.cqse.check.framework.core.ECheckParameter;
import eu.cqse.check.framework.core.EFindingEnablement;
import eu.cqse.check.framework.core.phase.ECodeViewOption;
import eu.cqse.check.framework.scanner.ELanguage;
import eu.cqse.check.framework.shallowparser.SubTypeNames;
import eu.cqse.check.framework.shallowparser.framework.EShallowEntityType;
import eu.cqse.check.framework.shallowparser.framework.ShallowEntity;
import eu.cqse.check.framework.shallowparser.framework.ShallowEntityTraversalUtils;

/**
 * This class implements an example custom check that demonstrates how to use
 * custom check phases.
 * 
 * The check generates findings for class names that are equal to class names in
 * other files. For example, if ClassA.java has an inner class myClass and
 * ClassB.java also has an inner class myClass, this check generates two
 * findings (on the myClass declarations).
 * 
 * By specifying an implementation of
 * {@link eu.cqse.check.framework.core.phase.IGlobalExtractionPhase} in the
 * phases property of the {@link Check} annotation, we specify that this check
 * requires access to results from the given phase (and the phase must be
 * executed before the check).
 */
@Check(name = "Sample Check with phase", description = "", groupName = CheckGroupNames.BAD_PRACTICE, defaultEnablement = EFindingEnablement.RED, languages = {
		ELanguage.JAVA }, parameters = { ECheckParameter.ABSTRACT_SYNTAX_TREE }, phases = {
				/*
				 * This check requires that SamplePhase has been executed and accesses results
				 * from that phase.
				 */
				SamplePhase.class })
public class SampleCheckUsingPhase extends CheckImplementationBase {
	@Override
	public void execute() throws CheckException {
		List<ShallowEntity> entities = context.getAbstractSyntaxTree(ECodeViewOption.FILTERED);
		for (ShallowEntity entity : ShallowEntityTraversalUtils.listEntitiesOfType(entities, EShallowEntityType.TYPE)) {
			if (!entity.getSubtype().equals(SubTypeNames.CLASS)) {
				continue;
			}
			/*
			 * Retrieve all declarations (including from this file) of classes that have the
			 * same name as the current class. This accesses the results computed by
			 * SamplePhase.
			 * 
			 * It also "registers" this check for this file for re-execution once the value
			 * of <code>context
			 * .accessPhaseInvertedResult(SamplePhase.class).apply(entity.getName())</code>
			 * changes (e.g., because of changes in other files in future commits).
			 */
			List<SamplePhase.ClassDeclarationInfo> otherDeclarations = context
					.accessPhaseInvertedResult(SamplePhase.class).apply(entity.getName());
			// remove declarations from the current file from the list
			otherDeclarations = CollectionUtils.filter(otherDeclarations,
					declaration -> !declaration.getAdditionalInformation().uniformPath
							.equals(context.getUniformPath()));
			if (!otherDeclarations.isEmpty()) {
				String otherUniformPaths = org.conqat.lib.commons.string.StringUtils.concat(
						CollectionUtils.map(otherDeclarations, SamplePhase.ClassDeclarationInfo::getUniformPath), ", ");
				// we could include the other uniform paths in the finding message, but that
				// would make testing this example more complex
				createFinding("found another type with same name", entity);
			}
		}
	}
}
