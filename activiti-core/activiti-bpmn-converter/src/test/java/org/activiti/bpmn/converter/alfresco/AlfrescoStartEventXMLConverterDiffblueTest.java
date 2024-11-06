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
package org.activiti.bpmn.converter.alfresco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.alfresco.AlfrescoStartEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AlfrescoStartEventXMLConverterDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link AlfrescoStartEventXMLConverter}
   *   <li>{@link AlfrescoStartEventXMLConverter#getBpmnElementType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualBpmnElementType = (new AlfrescoStartEventXMLConverter()).getBpmnElementType();

    // Assert
    Class<AlfrescoStartEvent> expectedBpmnElementType = AlfrescoStartEvent.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
