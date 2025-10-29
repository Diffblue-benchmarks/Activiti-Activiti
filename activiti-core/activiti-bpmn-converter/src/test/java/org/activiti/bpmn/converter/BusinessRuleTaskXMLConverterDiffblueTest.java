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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BusinessRuleTaskXMLConverterDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link BusinessRuleTaskXMLConverter}
   *   <li>
   * {@link BusinessRuleTaskXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>{@link BusinessRuleTaskXMLConverter#getBpmnElementType()}
   *   <li>{@link BusinessRuleTaskXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() throws Exception {
    // Arrange and Act
    BusinessRuleTaskXMLConverter actualBusinessRuleTaskXMLConverter = new BusinessRuleTaskXMLConverter();
    ActivitiListener element = new ActivitiListener();
    BpmnModel model = new BpmnModel();
    actualBusinessRuleTaskXMLConverter.writeAdditionalChildElements(element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType = actualBusinessRuleTaskXMLConverter.getBpmnElementType();

    // Assert that nothing has changed
    assertEquals("businessRuleTask", actualBusinessRuleTaskXMLConverter.getXMLElementName());
    Class<BusinessRuleTask> expectedBpmnElementType = BusinessRuleTask.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }

  /**
   * Method under test:
   * {@link BusinessRuleTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteAdditionalAttributes() throws Exception {
    // Arrange
    BusinessRuleTaskXMLConverter businessRuleTaskXMLConverter = new BusinessRuleTaskXMLConverter();

    BusinessRuleTask element = new BusinessRuleTask();
    element.setExclude(false);
    element.setClassName("Class Name");
    element.setResultVariableName(null);
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    businessRuleTaskXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(writer));

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("class"), eq("Class Name"));
  }

  /**
   * Method under test:
   * {@link BusinessRuleTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteAdditionalAttributes2() throws Exception {
    // Arrange
    BusinessRuleTaskXMLConverter businessRuleTaskXMLConverter = new BusinessRuleTaskXMLConverter();

    BusinessRuleTask element = new BusinessRuleTask();
    element.setExclude(true);
    element.setClassName("Class Name");
    element.setResultVariableName(null);
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    businessRuleTaskXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(writer));

    // Assert that nothing has changed
    verify(writer, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
  }

  /**
   * Method under test:
   * {@link BusinessRuleTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteAdditionalAttributes3() throws Exception {
    // Arrange
    BusinessRuleTaskXMLConverter businessRuleTaskXMLConverter = new BusinessRuleTaskXMLConverter();

    BusinessRuleTask element = new BusinessRuleTask();
    element.setExclude(false);
    element.setClassName("Class Name");
    element.setResultVariableName("class");
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    businessRuleTaskXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(writer));

    // Assert that nothing has changed
    verify(writer, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
  }
}
