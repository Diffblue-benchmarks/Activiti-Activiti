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
package org.activiti.engine.impl.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class BpmnMessagePayloadMappingProviderDiffblueTest {
  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProvider#getMessagePayload(DelegateExecution)}
   */
  @Test
  public void testGetMessagePayload() {
    // Arrange
    BpmnMessagePayloadMappingProvider bpmnMessagePayloadMappingProvider = new BpmnMessagePayloadMappingProvider(
        new ArrayList<>());

    // Act and Assert
    assertFalse(bpmnMessagePayloadMappingProvider
        .getMessagePayload(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
        .isPresent());
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProvider#getMessagePayload(DelegateExecution)}
   */
  @Test
  public void testGetMessagePayload2() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    BpmnMessagePayloadMappingProvider bpmnMessagePayloadMappingProvider = new BpmnMessagePayloadMappingProvider(
        fieldDeclarations);

    // Act
    Optional<Map<String, Object>> actualMessagePayload = bpmnMessagePayloadMappingProvider
        .getMessagePayload(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    Map<String, Object> getResult = actualMessagePayload.get();
    assertEquals(1, getResult.size());
    assertNull(getResult.get(null));
    assertTrue(actualMessagePayload.isPresent());
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProvider#getMessagePayload(DelegateExecution)}
   */
  @Test
  public void testGetMessagePayload3() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    fieldDeclarations.add(new FieldDeclaration());
    BpmnMessagePayloadMappingProvider bpmnMessagePayloadMappingProvider = new BpmnMessagePayloadMappingProvider(
        fieldDeclarations);

    // Act
    Optional<Map<String, Object>> actualMessagePayload = bpmnMessagePayloadMappingProvider
        .getMessagePayload(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    Map<String, Object> getResult = actualMessagePayload.get();
    assertEquals(1, getResult.size());
    assertNull(getResult.get(null));
    assertTrue(actualMessagePayload.isPresent());
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProvider#getMessagePayload(DelegateExecution)}
   */
  @Test
  public void testGetMessagePayload4() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration("Name", "Type", JSONObject.NULL));
    BpmnMessagePayloadMappingProvider bpmnMessagePayloadMappingProvider = new BpmnMessagePayloadMappingProvider(
        fieldDeclarations);

    // Act
    Optional<Map<String, Object>> actualMessagePayload = bpmnMessagePayloadMappingProvider
        .getMessagePayload(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    Map<String, Object> getResult = actualMessagePayload.get();
    assertEquals(1, getResult.size());
    assertTrue(getResult.containsKey("Name"));
    assertTrue(actualMessagePayload.isPresent());
  }
}
