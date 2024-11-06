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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExclusiveGatewayMatchersDiffblueTest {
  /**
   * Test {@link ExclusiveGatewayMatchers#getActivityType()}.
   * <p>
   * Method under test: {@link ExclusiveGatewayMatchers#getActivityType()}
   */
  @Test
  @DisplayName("Test getActivityType()")
  void testGetActivityType() {
    // Arrange, Act and Assert
    assertEquals("exclusiveGateway", ExclusiveGatewayMatchers.exclusiveGateway("Definition Key").getActivityType());
  }

  /**
   * Test {@link ExclusiveGatewayMatchers#exclusiveGateway(String)}.
   * <p>
   * Method under test: {@link ExclusiveGatewayMatchers#exclusiveGateway(String)}
   */
  @Test
  @DisplayName("Test exclusiveGateway(String)")
  void testExclusiveGateway() {
    // Arrange, Act and Assert
    assertEquals("exclusiveGateway", ExclusiveGatewayMatchers.exclusiveGateway("Definition Key").getActivityType());
  }
}
