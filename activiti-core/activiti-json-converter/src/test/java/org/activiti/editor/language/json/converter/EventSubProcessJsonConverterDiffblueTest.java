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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EventSubProcessJsonConverterDiffblueTest {
  /**
   * Test {@link EventSubProcessJsonConverter#fillJsonTypes(Map)}.
   * <p>
   * Method under test: {@link EventSubProcessJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void EventSubProcessJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    EventSubProcessJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<EventSubProcessJsonConverter> expectedGetResult = EventSubProcessJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("EventSubProcess"));
  }

  /**
   * Test {@link EventSubProcessJsonConverter#getStencilId(BaseElement)}.
   * <p>
   * Method under test: {@link EventSubProcessJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String EventSubProcessJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    EventSubProcessJsonConverter eventSubProcessJsonConverter = new EventSubProcessJsonConverter();

    // Act and Assert
    assertEquals("EventSubProcess", eventSubProcessJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test new {@link EventSubProcessJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link EventSubProcessJsonConverter}
   */
  @Test
  @DisplayName("Test new EventSubProcessJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void EventSubProcessJsonConverter.<init>()"})
  void testNewEventSubProcessJsonConverter() {
    // Arrange and Act
    EventSubProcessJsonConverter actualEventSubProcessJsonConverter = new EventSubProcessJsonConverter();

    // Assert
    assertNull(actualEventSubProcessJsonConverter.shapesArrayNode);
    assertNull(actualEventSubProcessJsonConverter.flowElementNode);
    assertNull(actualEventSubProcessJsonConverter.decisionTableMap);
    assertNull(actualEventSubProcessJsonConverter.formMap);
    assertNull(actualEventSubProcessJsonConverter.decisionTableKeyMap);
    assertNull(actualEventSubProcessJsonConverter.formKeyMap);
    assertNull(actualEventSubProcessJsonConverter.model);
    assertNull(actualEventSubProcessJsonConverter.processor);
    assertEquals(0.0d, actualEventSubProcessJsonConverter.subProcessX);
    assertEquals(0.0d, actualEventSubProcessJsonConverter.subProcessY);
  }
}
