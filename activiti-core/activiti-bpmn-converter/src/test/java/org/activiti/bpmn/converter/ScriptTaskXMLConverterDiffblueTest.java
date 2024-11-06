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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.Map;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.child.BaseChildElementParser;
import org.activiti.bpmn.converter.child.ScriptTextParser;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ScriptTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ScriptTaskXMLConverterDiffblueTest {
  /**
   * Test new {@link ScriptTaskXMLConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ScriptTaskXMLConverter}
   */
  @Test
  @DisplayName("Test new ScriptTaskXMLConverter (default constructor)")
  void testNewScriptTaskXMLConverter() {
    // Arrange and Act
    ScriptTaskXMLConverter actualScriptTaskXMLConverter = new ScriptTaskXMLConverter();

    // Assert
    Map<String, BaseChildElementParser> stringBaseChildElementParserMap = actualScriptTaskXMLConverter.childParserMap;
    assertEquals(1, stringBaseChildElementParserMap.size());
    BaseChildElementParser getResult = stringBaseChildElementParserMap.get("script");
    assertTrue(getResult instanceof ScriptTextParser);
    assertEquals("script", getResult.getElementName());
    assertEquals("scriptTask", actualScriptTaskXMLConverter.getXMLElementName());
    Class<ScriptTask> expectedBpmnElementType = ScriptTask.class;
    assertEquals(expectedBpmnElementType, actualScriptTaskXMLConverter.getBpmnElementType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ScriptTaskXMLConverter#getBpmnElementType()}
   *   <li>{@link ScriptTaskXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ScriptTaskXMLConverter scriptTaskXMLConverter = new ScriptTaskXMLConverter();

    // Act
    Class<? extends BaseElement> actualBpmnElementType = scriptTaskXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("scriptTask", scriptTaskXMLConverter.getXMLElementName());
    Class<ScriptTask> expectedBpmnElementType = ScriptTask.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }

  /**
   * Test
   * {@link ScriptTaskXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code Script}.</li>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCData(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptTaskXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter); given 'Script'; then calls writeCData(String)")
  void testWriteAdditionalChildElements_givenScript_thenCallsWriteCData() throws Exception {
    // Arrange
    ScriptTaskXMLConverter scriptTaskXMLConverter = new ScriptTaskXMLConverter();

    ScriptTask element = new ScriptTask();
    element.setScript("Script");
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    scriptTaskXMLConverter.writeAdditionalChildElements(element, model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeCData(eq("Script"));
    verify(writer).writeEndElement();
    verify(writer).writeStartElement(eq("script"));
  }
}
