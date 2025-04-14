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
package org.activiti.bpmn.converter.child.multi.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MultiInstanceParserDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MultiInstanceParser#MultiInstanceParser(List)}
   *   <li>{@link MultiInstanceParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MultiInstanceParser.<init>(List)", "java.lang.String MultiInstanceParser.getElementName()"})
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("multiInstanceLoopCharacteristics", (new MultiInstanceParser(new ArrayList<>())).getElementName());
  }

  /**
   * Test {@link MultiInstanceParser#MultiInstanceParser()}.
   * <p>
   * Method under test: {@link MultiInstanceParser#MultiInstanceParser()}
   */
  @Test
  @DisplayName("Test new MultiInstanceParser()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MultiInstanceParser.<init>()"})
  void testNewMultiInstanceParser() {
    // Arrange, Act and Assert
    assertEquals("multiInstanceLoopCharacteristics", (new MultiInstanceParser()).getElementName());
  }
}
