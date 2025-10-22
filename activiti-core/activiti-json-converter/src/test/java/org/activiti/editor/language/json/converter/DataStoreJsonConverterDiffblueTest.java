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
package org.activiti.editor.language.json.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.DataStoreReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DataStoreJsonConverterDiffblueTest {
  /**
   * Test {@link DataStoreJsonConverter#fillJsonTypes(Map)}.
   * <p>
   * Method under test: {@link DataStoreJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DataStoreJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    DataStoreJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<DataStoreJsonConverter> expectedGetResult = DataStoreJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("DataStore"));
  }

  /**
   * Test {@link DataStoreJsonConverter#getStencilId(BaseElement)}.
   * <p>
   * Method under test: {@link DataStoreJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DataStoreJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    DataStoreJsonConverter dataStoreJsonConverter = new DataStoreJsonConverter();

    // Act and Assert
    assertEquals("DataStore", dataStoreJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link DataStoreJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link DataStoreJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement DataStoreJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement() {
    // Arrange
    DataStoreJsonConverter dataStoreJsonConverter = new DataStoreJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    BaseElement actualConvertJsonToElementResult = dataStoreJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof DataStoreReference);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((DataStoreReference) actualConvertJsonToElementResult).getDataState());
    assertNull(((DataStoreReference) actualConvertJsonToElementResult).getDataStoreRef());
    assertNull(((DataStoreReference) actualConvertJsonToElementResult).getItemSubjectRef());
    assertNull(((DataStoreReference) actualConvertJsonToElementResult).getDocumentation());
    assertNull(((DataStoreReference) actualConvertJsonToElementResult).getName());
    assertNull(((DataStoreReference) actualConvertJsonToElementResult).getParentContainer());
    assertNull(((DataStoreReference) actualConvertJsonToElementResult).getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(((DataStoreReference) actualConvertJsonToElementResult).getExecutionListeners().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test new {@link DataStoreJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link DataStoreJsonConverter}
   */
  @Test
  @DisplayName("Test new DataStoreJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DataStoreJsonConverter.<init>()"})
  void testNewDataStoreJsonConverter() {
    // Arrange and Act
    DataStoreJsonConverter actualDataStoreJsonConverter = new DataStoreJsonConverter();

    // Assert
    assertNull(actualDataStoreJsonConverter.shapesArrayNode);
    assertNull(actualDataStoreJsonConverter.flowElementNode);
    assertNull(actualDataStoreJsonConverter.model);
    assertNull(actualDataStoreJsonConverter.processor);
    assertEquals(0.0d, actualDataStoreJsonConverter.subProcessX);
    assertEquals(0.0d, actualDataStoreJsonConverter.subProcessY);
  }
}
