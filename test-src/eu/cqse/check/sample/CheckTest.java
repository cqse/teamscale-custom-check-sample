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

import java.io.IOException;

import eu.cqse.check.framework.checktest.CheckTestBase;
import junit.framework.Test;

/**
 * Main class for testing the checks in this library.
 * 
 * @author $Author: hummelb $
 * @version $Rev: 54345 $
 * @ConQAT.Rating GREEN Hash: 2DB08F8BA1C126E6730349DE39B7E7D3
 */
public class CheckTest extends CheckTestBase {

	/** Creates smoke test suite */
	public static Test suite() throws IOException {
		return new CheckTest().createSuite();
	}
}
