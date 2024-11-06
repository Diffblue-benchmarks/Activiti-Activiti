/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.engine.impl.el;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import jakarta.el.ELResolver;
import jakarta.el.FunctionMapper;
import org.junit.Test;

public class ParsingElContextDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ParsingElContext}
   *   <li>{@link ParsingElContext#getELResolver()}
   *   <li>{@link ParsingElContext#getFunctionMapper()}
   *   <li>{@link ParsingElContext#getVariableMapper()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ParsingElContext actualParsingElContext = new ParsingElContext();
    ELResolver actualELResolver = actualParsingElContext.getELResolver();
    FunctionMapper actualFunctionMapper = actualParsingElContext.getFunctionMapper();

    // Assert
    assertNull(actualELResolver);
    assertNull(actualFunctionMapper);
    assertNull(actualParsingElContext.getVariableMapper());
    assertNull(actualParsingElContext.getEvaluationListeners());
    assertNull(actualParsingElContext.getLocale());
    assertFalse(actualParsingElContext.isPropertyResolved());
  }
}
