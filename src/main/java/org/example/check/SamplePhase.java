package org.example.check;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.conqat.engine.commons.findings.location.TextRegionLocation;
import org.conqat.lib.commons.collections.CollectionUtils;

import eu.cqse.check.framework.core.CheckException;
import eu.cqse.check.framework.core.ECheckParameter;
import eu.cqse.check.framework.core.phase.ECodeViewOption;
import eu.cqse.check.framework.core.phase.IExtractedValue;
import eu.cqse.check.framework.core.phase.IGlobalExtractionPhase;
import eu.cqse.check.framework.core.phase.ITokenElementContext;
import eu.cqse.check.framework.scanner.ELanguage;
import eu.cqse.check.framework.scanner.IToken;
import eu.cqse.check.framework.shallowparser.SubTypeNames;
import eu.cqse.check.framework.shallowparser.framework.EShallowEntityType;
import eu.cqse.check.framework.shallowparser.framework.ShallowEntity;
import eu.cqse.check.framework.shallowparser.framework.ShallowEntityTraversalUtils;

/**
 * This custom check phase scans all java code files and extracts class
 * declarations (including inner classes). The declarations are persisted in a
 * teamscale-internal database for later access in a custom check.
 *
 * The stored entries look like this:
 *
 * <pre>
 * "uniformPathOfFileA.java" -> "A" (ClassDeclarationInfo1)
 * "uniformPathOfFileA.java" -> "inner" (ClassDeclarationInfo2)
 * "uniformPathOfFileB.java" -> "B" (ClassDeclarationInfo3)
 * "uniformPathOfFileB.java" -> "inner" (ClassDeclarationInfo4)
 * </pre>
 *
 * The information can be accessed "by uniform path" (returns all declaration
 * objects for a given uniform path) or "by value" (returns all declaration
 * objects for e.g., "inner"). For the second access pattern,
 * {@link #needsAccessUniformPathByValue()} must be overwritten and must return
 * true.
 */
public class SamplePhase implements IGlobalExtractionPhase<SamplePhase.ClassDeclarationInfo, TextRegionLocation> {

	@Override
	public EnumSet<ELanguage> getLanguages() {
		// The phase should be executed for Java files only.
		return EnumSet.of(ELanguage.JAVA);
	}

	@Override
	public EnumSet<ECheckParameter> getRequiredContextParameters() {
		// this phase requires that the abstract syntax tree is prepared since it calls
		// context.getAbstractSyntaxTree(..)
		return EnumSet.of(ECheckParameter.ABSTRACT_SYNTAX_TREE);
	}

	@Override
	public boolean needsAccessByValue() {
		/*
		 * The checks accessing the results of this phase need to access "by value"
		 * (e.g.,
		 * <code>context.accessPhaseInvertedResult(SamplePhase.class).apply("inner")</
		 * code>)
		 */
		return true;
	}

	@Override
	public List<ClassDeclarationInfo> extract(ITokenElementContext context) throws CheckException {
		List<ClassDeclarationInfo> results = new ArrayList<>();
		List<ShallowEntity> entities = context.getAbstractSyntaxTree(ECodeViewOption.FILTERED);
		// traverse all TYPE entities in code that is not filtered by text filters (can
		// be configured in project settings)
		for (ShallowEntity entity : ShallowEntityTraversalUtils.listEntitiesOfType(entities, EShallowEntityType.TYPE)) {
			if (!entity.getSubtype().equals(SubTypeNames.CLASS)) {
				// ignore non-class types (interfaces, etc.)
				continue;
			}
			IToken startToken = entity.ownStartTokens().get(0);
			IToken endToken = CollectionUtils.getLast(entity.ownStartTokens());
			TextRegionLocation location = new TextRegionLocation(context.getUniformPath(), context.getUniformPath(),
					startToken.getOffset(), endToken.getOffset(), startToken.getLineNumber(), endToken.getLineNumber());
			// add a new ClassDeclarationInfo with the location of the current type
			results.add(new ClassDeclarationInfo(entity.getName(), location));
		}
		return results;
	}

	@Override
	public ClassDeclarationInfo createValue(String uniformPath, String value,
			TextRegionLocation additionalInformation) {
		return new ClassDeclarationInfo(value, additionalInformation);
	}

	/**
	 * "Transport object" that stores the name and location of a class declaration
	 * (might declare an inner class).
	 *
	 * The result of a check phase are a list of such {@link IExtractedValue}
	 * objects which are stored associated with the currently analyzed file.
	 */
	public static class ClassDeclarationInfo implements IExtractedValue<TextRegionLocation> {

		/**
		 * Simple name of the declared class.
		 */
		private final String className;

		/**
		 * Location of the class declaration (contains uniform path, line, and offset)
		 */
		private final TextRegionLocation location;

		public ClassDeclarationInfo(String className, TextRegionLocation location) {
			this.className = className;
			this.location = location;
		}

		@Override
		public TextRegionLocation getAdditionalInformation() {
			// You can store any (serializable) object that can't be encoded in the uniform
			// path or in getValue() here. In this example, we need only
			// the declaration's location.
			return location;
		}

		@Override
		public String getUniformPath() {
			// this is the uniform path that can be used to access phase results in
			// <code>context.accessPhaseResult(SamplePhase.class).apply("inner")</code>.
			return location.getUniformPath();
		}

		@Override
		public String getValue() {
			// this is the value that can be used to access phase results in
			// <code>context.accessPhaseInvertedResult(SamplePhase.class).apply("inner")</code>.
			return className;
		}
	}
}
