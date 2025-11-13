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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SubProcessJsonConverterDiffblueTest {
  /**
   * Test {@link SubProcessJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link SubProcessJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcessJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    SubProcessJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<SubProcessJsonConverter> expectedGetResult = SubProcessJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("SubProcess"));
  }

  /**
   * Test {@link SubProcessJsonConverter#getStencilId(BaseElement)}.
   *
   * <p>Method under test: {@link SubProcessJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SubProcessJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    SubProcessJsonConverter subProcessJsonConverter = new SubProcessJsonConverter();

    // Act and Assert
    assertEquals("SubProcess", subProcessJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test new {@link SubProcessJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link SubProcessJsonConverter}
   */
  @Test
  @DisplayName("Test new SubProcessJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcessJsonConverter.<init>()"})
  void testNewSubProcessJsonConverter() {
    // Arrange and Act
    SubProcessJsonConverter actualSubProcessJsonConverter = new SubProcessJsonConverter();

    // Assert
    assertNull(actualSubProcessJsonConverter.shapesArrayNode);
    assertNull(actualSubProcessJsonConverter.flowElementNode);
    assertNull(actualSubProcessJsonConverter.decisionTableMap);
    assertNull(actualSubProcessJsonConverter.formMap);
    assertNull(actualSubProcessJsonConverter.decisionTableKeyMap);
    assertNull(actualSubProcessJsonConverter.formKeyMap);
    assertNull(actualSubProcessJsonConverter.model);
    assertNull(actualSubProcessJsonConverter.processor);
    assertEquals(0.0d, actualSubProcessJsonConverter.subProcessX);
    assertEquals(0.0d, actualSubProcessJsonConverter.subProcessY);
  }
}
