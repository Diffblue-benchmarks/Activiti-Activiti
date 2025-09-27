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
import java.util.HashMap;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ComplexGateway;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ComplexGatewayXMLConverterDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ComplexGatewayXMLConverter}
   *   <li>{@link ComplexGatewayXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   *       XMLStreamWriter)}
   *   <li>{@link ComplexGatewayXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel,
   *       XMLStreamWriter)}
   *   <li>{@link ComplexGatewayXMLConverter#getBpmnElementType()}
   *   <li>{@link ComplexGatewayXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ComplexGatewayXMLConverter.<init>()",
    "Class ComplexGatewayXMLConverter.getBpmnElementType()",
    "java.lang.String ComplexGatewayXMLConverter.getXMLElementName()",
    "void ComplexGatewayXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)",
    "void ComplexGatewayXMLConverter.writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testGettersAndSetters() throws Exception {
    // Arrange and Act
    ComplexGatewayXMLConverter actualComplexGatewayXMLConverter = new ComplexGatewayXMLConverter();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message element =
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build();
    BpmnModel model = new BpmnModel();
    actualComplexGatewayXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(null));
    Builder builderResult2 = Message.builder();
    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    Message element2 =
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build();
    BpmnModel model2 = new BpmnModel();
    actualComplexGatewayXMLConverter.writeAdditionalChildElements(
        element2, model2, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType =
        actualComplexGatewayXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("complexGateway", actualComplexGatewayXMLConverter.getXMLElementName());
    Class<ComplexGateway> expectedBpmnElementType = ComplexGateway.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
