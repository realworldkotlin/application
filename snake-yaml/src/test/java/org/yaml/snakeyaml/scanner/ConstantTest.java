/**
 * Copyright (c) 2008, http://www.snakeyaml.org
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yaml.snakeyaml.scanner;

import junit.framework.TestCase;

public class ConstantTest extends TestCase {

    public void testHasChar() {
        assertTrue(Constant.LINEBR.has('\n'));
        assertTrue(Constant.LINEBR.has('\u0085'));
        assertFalse(Constant.LINEBR.has(' '));
    }

    public void testHasStringChar() {
        assertTrue(Constant.LINEBR.has(' ', " "));
    }

    public void testHas0() {
        assertTrue(Constant.LINEBR.has((char) 0, "\0"));
    }

}
