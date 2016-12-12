/*-------------------------------------------------------------------------+
|                                                                          |
| Copyright 2005-2011 the ConQAT Project                                   |
|                                                                          |
| Licensed under the Apache License, Version 2.0 (the "License");          |
| you may not use this file except in compliance with the License.         |
| You may obtain a copy of the License at                                  |
|                                                                          |
|    http://www.apache.org/licenses/LICENSE-2.0                            |
|                                                                          |
| Unless required by applicable law or agreed to in writing, software      |
| distributed under the License is distributed on an "AS IS" BASIS,        |
| WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. |
| See the License for the specific language governing permissions and      |
| limitations under the License.                                           |
+-------------------------------------------------------------------------*/
package eu.cqse.check.sample;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.cqse.check.framework.checktest.CheckTestBase;
import eu.cqse.check.framework.core.CheckInfo;

/**
 * Main class for testing the checks in this library.
 *
 * @ConQAT.Rating YELLOW Hash: CD62B1001924DC6C2FAEFACA1258076E
 */
@RunWith(Parameterized.class)
public class CheckTest extends CheckTestBase {

	/**
	 * Constructor.
	 */
	public CheckTest(File referenceFile, Map<String, CheckInfo> checkInfoBySimpleClassName) {
		super(referenceFile, checkInfoBySimpleClassName);
	}

	/** Generate Test Parameters. */
	@Parameters(name = "{0}")
	public static Collection<Object[]> generateParameters() throws IOException {
		return CheckTestBase.generateParameters(new CheckTest(null, null));
	}
}
