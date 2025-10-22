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
import com.diffblue.cover.annotations.MethodsUnderTest;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BusinessRuleTaskXMLConverterDiffblueTest {
  /**
   * Test {@link BusinessRuleTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code class}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessRuleTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given 'class'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BusinessRuleTaskXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"})
  void testWriteAdditionalAttributes_givenClass() throws Exception {
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

    // Assert
    verify(writer, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
  }

  /**
   * Test {@link BusinessRuleTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code Class Name}.</li>
   *   <li>Then calls {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessRuleTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given 'Class Name'; then calls writeAttribute(String, String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BusinessRuleTaskXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"})
  void testWriteAdditionalAttributes_givenClassName_thenCallsWriteAttribute() throws Exception {
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

    // Assert
    verify(writer).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("class"), eq("Class Name"));
  }

  /**
   * Test {@link BusinessRuleTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link BusinessRuleTask} (default constructor) Exclude is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessRuleTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given 'true'; when BusinessRuleTask (default constructor) Exclude is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void BusinessRuleTaskXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"})
  void testWriteAdditionalAttributes_givenTrue_whenBusinessRuleTaskExcludeIsTrue() throws Exception {
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

    // Assert
    verify(writer, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BusinessRuleTaskXMLConverter}
   *   <li>{@link BusinessRuleTaskXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>{@link BusinessRuleTaskXMLConverter#getBpmnElementType()}
   *   <li>{@link BusinessRuleTaskXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BusinessRuleTaskXMLConverter.<init>()",
      "Class BusinessRuleTaskXMLConverter.getBpmnElementType()",
      "String BusinessRuleTaskXMLConverter.getXMLElementName()",
      "void BusinessRuleTaskXMLConverter.writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)"})
  void testGettersAndSetters() throws Exception {
    // Arrange and Act
    BusinessRuleTaskXMLConverter actualBusinessRuleTaskXMLConverter = new BusinessRuleTaskXMLConverter();
    ActivitiListener element = new ActivitiListener();
    BpmnModel model = new BpmnModel();
    actualBusinessRuleTaskXMLConverter.writeAdditionalChildElements(element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType = actualBusinessRuleTaskXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("businessRuleTask", actualBusinessRuleTaskXMLConverter.getXMLElementName());
    Class<BusinessRuleTask> expectedBpmnElementType = BusinessRuleTask.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
