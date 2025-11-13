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
package org.activiti.bpmn.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BoundaryEventXMLConverterDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link BoundaryEventXMLConverter}
   *   <li>{@link BoundaryEventXMLConverter#getBpmnElementType()}
   *   <li>{@link BoundaryEventXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BoundaryEventXMLConverter.<init>()",
    "Class BoundaryEventXMLConverter.getBpmnElementType()",
    "java.lang.String BoundaryEventXMLConverter.getXMLElementName()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    BoundaryEventXMLConverter actualBoundaryEventXMLConverter = new BoundaryEventXMLConverter();
    Class<? extends BaseElement> actualBpmnElementType =
        actualBoundaryEventXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("boundaryEvent", actualBoundaryEventXMLConverter.getXMLElementName());
    Class<BoundaryEvent> expectedBpmnElementType = BoundaryEvent.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
