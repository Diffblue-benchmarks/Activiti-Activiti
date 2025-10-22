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
package org.activiti.test.matchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class IntermediateCatchEventMatchersDiffblueTest {
  /**
   * Test {@link IntermediateCatchEventMatchers#getActivityType()}.
   * <p>
   * Method under test: {@link IntermediateCatchEventMatchers#getActivityType()}
   */
  @Test
  @DisplayName("Test getActivityType()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String IntermediateCatchEventMatchers.getActivityType()"})
  void testGetActivityType() {
    // Arrange, Act and Assert
    assertEquals("intermediateCatchEvent",
        IntermediateCatchEventMatchers.intermediateCatchEvent("Definition Key").getActivityType());
  }

  /**
   * Test {@link IntermediateCatchEventMatchers#intermediateCatchEvent(String)}.
   * <p>
   * Method under test: {@link IntermediateCatchEventMatchers#intermediateCatchEvent(String)}
   */
  @Test
  @DisplayName("Test intermediateCatchEvent(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"IntermediateCatchEventMatchers IntermediateCatchEventMatchers.intermediateCatchEvent(String)"})
  void testIntermediateCatchEvent() {
    // Arrange, Act and Assert
    assertEquals("intermediateCatchEvent",
        IntermediateCatchEventMatchers.intermediateCatchEvent("Definition Key").getActivityType());
  }
}
