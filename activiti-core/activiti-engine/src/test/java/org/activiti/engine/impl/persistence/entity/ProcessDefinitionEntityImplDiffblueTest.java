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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.engine.impl.bpmn.data.IOSpecification;
import org.junit.Test;

public class ProcessDefinitionEntityImplDiffblueTest {
  /**
   * Test {@link ProcessDefinitionEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    processDefinitionEntityImpl.setVariables(variables);

    // Act
    Object actualPersistentState = processDefinitionEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(2, ((Map<String, Integer>) actualPersistentState).size());
    assertNull(((Map<String, Integer>) actualPersistentState).get("category"));
    assertEquals(1, ((Map<String, Integer>) actualPersistentState).get("suspensionState").intValue());
  }

  /**
   * Test {@link ProcessDefinitionEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given {@link ProcessDefinitionEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_givenProcessDefinitionEntityImpl() {
    // Arrange and Act
    Object actualPersistentState = (new ProcessDefinitionEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(2, ((Map<String, Integer>) actualPersistentState).size());
    assertNull(((Map<String, Integer>) actualPersistentState).get("category"));
    assertEquals(1, ((Map<String, Integer>) actualPersistentState).get("suspensionState").intValue());
  }

  /**
   * Test {@link ProcessDefinitionEntityImpl#isSuspended()}.
   * <ul>
   *   <li>Given {@link ProcessDefinitionEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionEntityImpl#isSuspended()}
   */
  @Test
  public void testIsSuspended_givenProcessDefinitionEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ProcessDefinitionEntityImpl()).isSuspended());
  }

  /**
   * Test {@link ProcessDefinitionEntityImpl#isSuspended()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionEntityImpl#isSuspended()}
   */
  @Test
  public void testIsSuspended_thenReturnTrue() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    processDefinitionEntityImpl.setSuspensionState(2);

    // Act and Assert
    assertTrue(processDefinitionEntityImpl.isSuspended());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessDefinitionEntityImpl#setAppVersion(Integer)}
   *   <li>{@link ProcessDefinitionEntityImpl#setCategory(String)}
   *   <li>{@link ProcessDefinitionEntityImpl#setDeploymentId(String)}
   *   <li>{@link ProcessDefinitionEntityImpl#setDescription(String)}
   *   <li>{@link ProcessDefinitionEntityImpl#setDiagramResourceName(String)}
   *   <li>{@link ProcessDefinitionEntityImpl#setEngineVersion(String)}
   *   <li>{@link ProcessDefinitionEntityImpl#setGraphicalNotationDefined(boolean)}
   *   <li>{@link ProcessDefinitionEntityImpl#setHasStartFormKey(boolean)}
   *   <li>{@link ProcessDefinitionEntityImpl#setHistoryLevel(Integer)}
   *   <li>{@link ProcessDefinitionEntityImpl#setIoSpecification(IOSpecification)}
   *   <li>{@link ProcessDefinitionEntityImpl#setKey(String)}
   *   <li>{@link ProcessDefinitionEntityImpl#setName(String)}
   *   <li>{@link ProcessDefinitionEntityImpl#setResourceName(String)}
   *   <li>{@link ProcessDefinitionEntityImpl#setStartFormKey(boolean)}
   *   <li>{@link ProcessDefinitionEntityImpl#setSuspensionState(int)}
   *   <li>{@link ProcessDefinitionEntityImpl#setTenantId(String)}
   *   <li>{@link ProcessDefinitionEntityImpl#setVariables(Map)}
   *   <li>{@link ProcessDefinitionEntityImpl#setVersion(int)}
   *   <li>{@link ProcessDefinitionEntityImpl#toString()}
   *   <li>{@link ProcessDefinitionEntityImpl#getAppVersion()}
   *   <li>{@link ProcessDefinitionEntityImpl#getCategory()}
   *   <li>{@link ProcessDefinitionEntityImpl#getDeploymentId()}
   *   <li>{@link ProcessDefinitionEntityImpl#getDescription()}
   *   <li>{@link ProcessDefinitionEntityImpl#getDiagramResourceName()}
   *   <li>{@link ProcessDefinitionEntityImpl#getEngineVersion()}
   *   <li>{@link ProcessDefinitionEntityImpl#getHasStartFormKey()}
   *   <li>{@link ProcessDefinitionEntityImpl#getHistoryLevel()}
   *   <li>{@link ProcessDefinitionEntityImpl#getIoSpecification()}
   *   <li>{@link ProcessDefinitionEntityImpl#getKey()}
   *   <li>{@link ProcessDefinitionEntityImpl#getName()}
   *   <li>{@link ProcessDefinitionEntityImpl#getResourceName()}
   *   <li>{@link ProcessDefinitionEntityImpl#getSuspensionState()}
   *   <li>{@link ProcessDefinitionEntityImpl#getTenantId()}
   *   <li>{@link ProcessDefinitionEntityImpl#getVariables()}
   *   <li>{@link ProcessDefinitionEntityImpl#getVersion()}
   *   <li>{@link ProcessDefinitionEntityImpl#hasGraphicalNotation()}
   *   <li>{@link ProcessDefinitionEntityImpl#hasStartFormKey()}
   *   <li>{@link ProcessDefinitionEntityImpl#isGraphicalNotationDefined()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();

    // Act
    processDefinitionEntityImpl.setAppVersion(1);
    processDefinitionEntityImpl.setCategory("Category");
    processDefinitionEntityImpl.setDeploymentId("42");
    processDefinitionEntityImpl.setDescription("The characteristics of someone or something");
    processDefinitionEntityImpl.setDiagramResourceName("Diagram Resource Name");
    processDefinitionEntityImpl.setEngineVersion("1.0.2");
    processDefinitionEntityImpl.setGraphicalNotationDefined(true);
    processDefinitionEntityImpl.setHasStartFormKey(true);
    processDefinitionEntityImpl.setHistoryLevel(1);
    IOSpecification ioSpecification = new IOSpecification();
    processDefinitionEntityImpl.setIoSpecification(ioSpecification);
    processDefinitionEntityImpl.setKey("Key");
    processDefinitionEntityImpl.setName("Name");
    processDefinitionEntityImpl.setResourceName("Resource Name");
    processDefinitionEntityImpl.setStartFormKey(true);
    processDefinitionEntityImpl.setSuspensionState(1);
    processDefinitionEntityImpl.setTenantId("42");
    HashMap<String, Object> variables = new HashMap<>();
    processDefinitionEntityImpl.setVariables(variables);
    processDefinitionEntityImpl.setVersion(1);
    String actualToStringResult = processDefinitionEntityImpl.toString();
    Integer actualAppVersion = processDefinitionEntityImpl.getAppVersion();
    String actualCategory = processDefinitionEntityImpl.getCategory();
    String actualDeploymentId = processDefinitionEntityImpl.getDeploymentId();
    String actualDescription = processDefinitionEntityImpl.getDescription();
    String actualDiagramResourceName = processDefinitionEntityImpl.getDiagramResourceName();
    String actualEngineVersion = processDefinitionEntityImpl.getEngineVersion();
    boolean actualHasStartFormKey = processDefinitionEntityImpl.getHasStartFormKey();
    Integer actualHistoryLevel = processDefinitionEntityImpl.getHistoryLevel();
    IOSpecification actualIoSpecification = processDefinitionEntityImpl.getIoSpecification();
    String actualKey = processDefinitionEntityImpl.getKey();
    String actualName = processDefinitionEntityImpl.getName();
    String actualResourceName = processDefinitionEntityImpl.getResourceName();
    int actualSuspensionState = processDefinitionEntityImpl.getSuspensionState();
    String actualTenantId = processDefinitionEntityImpl.getTenantId();
    Map<String, Object> actualVariables = processDefinitionEntityImpl.getVariables();
    int actualVersion = processDefinitionEntityImpl.getVersion();
    boolean actualHasGraphicalNotationResult = processDefinitionEntityImpl.hasGraphicalNotation();
    boolean actualHasStartFormKeyResult = processDefinitionEntityImpl.hasStartFormKey();
    boolean actualIsGraphicalNotationDefinedResult = processDefinitionEntityImpl.isGraphicalNotationDefined();

    // Assert that nothing has changed
    assertEquals("1.0.2", actualEngineVersion);
    assertEquals("42", actualDeploymentId);
    assertEquals("42", actualTenantId);
    assertEquals("Category", actualCategory);
    assertEquals("Diagram Resource Name", actualDiagramResourceName);
    assertEquals("Key", actualKey);
    assertEquals("Name", actualName);
    assertEquals("ProcessDefinitionEntity[null]", actualToStringResult);
    assertEquals("Resource Name", actualResourceName);
    assertEquals("The characteristics of someone or something", actualDescription);
    assertEquals(1, actualAppVersion.intValue());
    assertEquals(1, actualHistoryLevel.intValue());
    assertEquals(1, actualSuspensionState);
    assertEquals(1, actualVersion);
    assertTrue(actualVariables.isEmpty());
    assertTrue(actualHasStartFormKey);
    assertTrue(actualHasGraphicalNotationResult);
    assertTrue(actualHasStartFormKeyResult);
    assertTrue(actualIsGraphicalNotationDefinedResult);
    assertSame(variables, actualVariables);
    assertSame(ioSpecification, actualIoSpecification);
  }

  /**
   * Test new {@link ProcessDefinitionEntityImpl} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ProcessDefinitionEntityImpl}
   */
  @Test
  public void testNewProcessDefinitionEntityImpl() {
    // Arrange and Act
    ProcessDefinitionEntityImpl actualProcessDefinitionEntityImpl = new ProcessDefinitionEntityImpl();

    // Assert
    Object persistentState = actualProcessDefinitionEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("", actualProcessDefinitionEntityImpl.getTenantId());
    assertEquals(2, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("category"));
    assertNull(actualProcessDefinitionEntityImpl.getAppVersion());
    assertNull(actualProcessDefinitionEntityImpl.getHistoryLevel());
    assertNull(actualProcessDefinitionEntityImpl.getId());
    assertNull(actualProcessDefinitionEntityImpl.getCategory());
    assertNull(actualProcessDefinitionEntityImpl.getDeploymentId());
    assertNull(actualProcessDefinitionEntityImpl.getDescription());
    assertNull(actualProcessDefinitionEntityImpl.getDiagramResourceName());
    assertNull(actualProcessDefinitionEntityImpl.getEngineVersion());
    assertNull(actualProcessDefinitionEntityImpl.getKey());
    assertNull(actualProcessDefinitionEntityImpl.getName());
    assertNull(actualProcessDefinitionEntityImpl.getResourceName());
    assertNull(actualProcessDefinitionEntityImpl.getVariables());
    assertNull(actualProcessDefinitionEntityImpl.getIoSpecification());
    assertEquals(0, actualProcessDefinitionEntityImpl.getVersion());
    assertEquals(1, ((Map<String, Integer>) persistentState).get("suspensionState").intValue());
    assertEquals(1, actualProcessDefinitionEntityImpl.getRevision());
    assertEquals(1, actualProcessDefinitionEntityImpl.getSuspensionState());
    assertEquals(2, actualProcessDefinitionEntityImpl.getRevisionNext());
    assertFalse(actualProcessDefinitionEntityImpl.isDeleted());
    assertFalse(actualProcessDefinitionEntityImpl.isInserted());
    assertFalse(actualProcessDefinitionEntityImpl.isUpdated());
    assertFalse(actualProcessDefinitionEntityImpl.getHasStartFormKey());
    assertFalse(actualProcessDefinitionEntityImpl.hasGraphicalNotation());
    assertFalse(actualProcessDefinitionEntityImpl.hasStartFormKey());
    assertFalse(actualProcessDefinitionEntityImpl.isGraphicalNotationDefined());
    assertFalse(actualProcessDefinitionEntityImpl.isSuspended());
    assertFalse(actualProcessDefinitionEntityImpl.isIdentityLinksInitialized);
    assertTrue(actualProcessDefinitionEntityImpl.definitionIdentityLinkEntities.isEmpty());
  }
}
