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
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CatchEventXMLConverterDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CatchEventXMLConverter}
   *   <li>
   * {@link CatchEventXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>{@link CatchEventXMLConverter#getBpmnElementType()}
   *   <li>{@link CatchEventXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() throws Exception {
    // Arrange and Act
    CatchEventXMLConverter actualCatchEventXMLConverter = new CatchEventXMLConverter();
    ActivitiListener element = new ActivitiListener();
    BpmnModel model = new BpmnModel();
    actualCatchEventXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType = actualCatchEventXMLConverter.getBpmnElementType();

    // Assert that nothing has changed
    assertEquals("intermediateCatchEvent", actualCatchEventXMLConverter.getXMLElementName());
    Class<IntermediateCatchEvent> expectedBpmnElementType = IntermediateCatchEvent.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
