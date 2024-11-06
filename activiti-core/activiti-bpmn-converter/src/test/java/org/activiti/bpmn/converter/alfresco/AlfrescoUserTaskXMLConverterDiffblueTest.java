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
import org.activiti.bpmn.model.alfresco.AlfrescoUserTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AlfrescoUserTaskXMLConverterDiffblueTest {
  /**
   * Test {@link AlfrescoUserTaskXMLConverter#getBpmnElementType()}.
   * <p>
   * Method under test: {@link AlfrescoUserTaskXMLConverter#getBpmnElementType()}
   */
  @Test
  @DisplayName("Test getBpmnElementType()")
  void testGetBpmnElementType() {
    // Arrange and Act
    Class<? extends BaseElement> actualBpmnElementType = (new AlfrescoUserTaskXMLConverter()).getBpmnElementType();

    // Assert
    Class<AlfrescoUserTask> expectedBpmnElementType = AlfrescoUserTask.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }

  /**
   * Test new {@link AlfrescoUserTaskXMLConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link AlfrescoUserTaskXMLConverter}
   */
  @Test
  @DisplayName("Test new AlfrescoUserTaskXMLConverter (default constructor)")
  void testNewAlfrescoUserTaskXMLConverter() {
    // Arrange and Act
    AlfrescoUserTaskXMLConverter actualAlfrescoUserTaskXMLConverter = new AlfrescoUserTaskXMLConverter();

    // Assert
    Class<AlfrescoUserTask> expectedBpmnElementType = AlfrescoUserTask.class;
    assertEquals(expectedBpmnElementType, actualAlfrescoUserTaskXMLConverter.getBpmnElementType());
  }
}
