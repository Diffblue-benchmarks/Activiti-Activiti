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
import org.activiti.bpmn.model.ExclusiveGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExclusiveGatewayXMLConverterDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link ExclusiveGatewayXMLConverter}
   *   <li>
   * {@link ExclusiveGatewayXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>
   * {@link ExclusiveGatewayXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>{@link ExclusiveGatewayXMLConverter#getBpmnElementType()}
   *   <li>{@link ExclusiveGatewayXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() throws Exception {
    // Arrange and Act
    ExclusiveGatewayXMLConverter actualExclusiveGatewayXMLConverter = new ExclusiveGatewayXMLConverter();
    ActivitiListener element = new ActivitiListener();
    BpmnModel model = new BpmnModel();
    actualExclusiveGatewayXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(null));
    ActivitiListener element2 = new ActivitiListener();
    BpmnModel model2 = new BpmnModel();
    actualExclusiveGatewayXMLConverter.writeAdditionalChildElements(element2, model2,
        new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType = actualExclusiveGatewayXMLConverter.getBpmnElementType();

    // Assert that nothing has changed
    assertEquals("exclusiveGateway", actualExclusiveGatewayXMLConverter.getXMLElementName());
    Class<ExclusiveGateway> expectedBpmnElementType = ExclusiveGateway.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}