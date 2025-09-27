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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HistoricProcessInstanceEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link HistoricProcessInstanceEntityImpl#HistoricProcessInstanceEntityImpl()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setBusinessKey(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setDeploymentId(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setDescription(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setEndActivityId(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setLocalizedDescription(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setLocalizedName(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setName(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setProcessDefinitionKey(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setProcessDefinitionName(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setProcessDefinitionVersion(Integer)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setQueryVariables(List)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setStartActivityId(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setStartUserId(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setSuperProcessInstanceId(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#setTenantId(String)}
   *   <li>{@link HistoricProcessInstanceEntityImpl#toString()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#getBusinessKey()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#getDeploymentId()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#getEndActivityId()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#getLocalizedDescription()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#getLocalizedName()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#getProcessDefinitionKey()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#getProcessDefinitionName()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#getProcessDefinitionVersion()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#getStartActivityId()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#getStartUserId()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#getSuperProcessInstanceId()}
   *   <li>{@link HistoricProcessInstanceEntityImpl#getTenantId()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricProcessInstanceEntityImpl.<init>()",
    "String HistoricProcessInstanceEntityImpl.getBusinessKey()",
    "String HistoricProcessInstanceEntityImpl.getDeploymentId()",
    "String HistoricProcessInstanceEntityImpl.getEndActivityId()",
    "String HistoricProcessInstanceEntityImpl.getLocalizedDescription()",
    "String HistoricProcessInstanceEntityImpl.getLocalizedName()",
    "String HistoricProcessInstanceEntityImpl.getProcessDefinitionKey()",
    "String HistoricProcessInstanceEntityImpl.getProcessDefinitionName()",
    "Integer HistoricProcessInstanceEntityImpl.getProcessDefinitionVersion()",
    "String HistoricProcessInstanceEntityImpl.getStartActivityId()",
    "String HistoricProcessInstanceEntityImpl.getStartUserId()",
    "String HistoricProcessInstanceEntityImpl.getSuperProcessInstanceId()",
    "String HistoricProcessInstanceEntityImpl.getTenantId()",
    "void HistoricProcessInstanceEntityImpl.setBusinessKey(String)",
    "void HistoricProcessInstanceEntityImpl.setDeploymentId(String)",
    "void HistoricProcessInstanceEntityImpl.setDescription(String)",
    "void HistoricProcessInstanceEntityImpl.setEndActivityId(String)",
    "void HistoricProcessInstanceEntityImpl.setLocalizedDescription(String)",
    "void HistoricProcessInstanceEntityImpl.setLocalizedName(String)",
    "void HistoricProcessInstanceEntityImpl.setName(String)",
    "void HistoricProcessInstanceEntityImpl.setProcessDefinitionKey(String)",
    "void HistoricProcessInstanceEntityImpl.setProcessDefinitionName(String)",
    "void HistoricProcessInstanceEntityImpl.setProcessDefinitionVersion(Integer)",
    "void HistoricProcessInstanceEntityImpl.setQueryVariables(List)",
    "void HistoricProcessInstanceEntityImpl.setStartActivityId(String)",
    "void HistoricProcessInstanceEntityImpl.setStartUserId(String)",
    "void HistoricProcessInstanceEntityImpl.setSuperProcessInstanceId(String)",
    "void HistoricProcessInstanceEntityImpl.setTenantId(String)",
    "String HistoricProcessInstanceEntityImpl.toString()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    HistoricProcessInstanceEntityImpl actualHistoricProcessInstanceEntityImpl =
        new HistoricProcessInstanceEntityImpl();
    actualHistoricProcessInstanceEntityImpl.setBusinessKey("Business Key");
    actualHistoricProcessInstanceEntityImpl.setDeploymentId("42");
    actualHistoricProcessInstanceEntityImpl.setDescription(
        "The characteristics of someone or something");
    actualHistoricProcessInstanceEntityImpl.setEndActivityId("42");
    actualHistoricProcessInstanceEntityImpl.setLocalizedDescription("Localized Description");
    actualHistoricProcessInstanceEntityImpl.setLocalizedName("Localized Name");
    actualHistoricProcessInstanceEntityImpl.setName("Name");
    actualHistoricProcessInstanceEntityImpl.setProcessDefinitionKey("Process Definition Key");
    actualHistoricProcessInstanceEntityImpl.setProcessDefinitionName("Process Definition Name");
    actualHistoricProcessInstanceEntityImpl.setProcessDefinitionVersion(1);
    actualHistoricProcessInstanceEntityImpl.setQueryVariables(new ArrayList<>());
    actualHistoricProcessInstanceEntityImpl.setStartActivityId("42");
    actualHistoricProcessInstanceEntityImpl.setStartUserId("42");
    actualHistoricProcessInstanceEntityImpl.setSuperProcessInstanceId("42");
    actualHistoricProcessInstanceEntityImpl.setTenantId("42");
    String actualToStringResult = actualHistoricProcessInstanceEntityImpl.toString();
    String actualBusinessKey = actualHistoricProcessInstanceEntityImpl.getBusinessKey();
    String actualDeploymentId = actualHistoricProcessInstanceEntityImpl.getDeploymentId();
    String actualEndActivityId = actualHistoricProcessInstanceEntityImpl.getEndActivityId();
    String actualLocalizedDescription =
        actualHistoricProcessInstanceEntityImpl.getLocalizedDescription();
    String actualLocalizedName = actualHistoricProcessInstanceEntityImpl.getLocalizedName();
    String actualProcessDefinitionKey =
        actualHistoricProcessInstanceEntityImpl.getProcessDefinitionKey();
    String actualProcessDefinitionName =
        actualHistoricProcessInstanceEntityImpl.getProcessDefinitionName();
    Integer actualProcessDefinitionVersion =
        actualHistoricProcessInstanceEntityImpl.getProcessDefinitionVersion();
    String actualStartActivityId = actualHistoricProcessInstanceEntityImpl.getStartActivityId();
    String actualStartUserId = actualHistoricProcessInstanceEntityImpl.getStartUserId();
    String actualSuperProcessInstanceId =
        actualHistoricProcessInstanceEntityImpl.getSuperProcessInstanceId();

    // Assert
    assertEquals("42", actualDeploymentId);
    assertEquals("42", actualEndActivityId);
    assertEquals("42", actualStartActivityId);
    assertEquals("42", actualStartUserId);
    assertEquals("42", actualSuperProcessInstanceId);
    assertEquals("42", actualHistoricProcessInstanceEntityImpl.getTenantId());
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("HistoricProcessInstanceEntity[superProcessInstanceId=42]", actualToStringResult);
    assertEquals("Localized Description", actualLocalizedDescription);
    assertEquals("Localized Name", actualLocalizedName);
    assertEquals("Process Definition Key", actualProcessDefinitionKey);
    assertEquals("Process Definition Name", actualProcessDefinitionName);
    assertNull(actualHistoricProcessInstanceEntityImpl.getDurationInMillis());
    assertNull(actualHistoricProcessInstanceEntityImpl.getId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getDeleteReason());
    assertNull(actualHistoricProcessInstanceEntityImpl.getProcessDefinitionId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getProcessInstanceId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getEndTime());
    assertNull(actualHistoricProcessInstanceEntityImpl.getStartTime());
    assertEquals(1, actualProcessDefinitionVersion.intValue());
    assertFalse(actualHistoricProcessInstanceEntityImpl.isDeleted());
    assertFalse(actualHistoricProcessInstanceEntityImpl.isInserted());
    assertFalse(actualHistoricProcessInstanceEntityImpl.isUpdated());
    assertTrue(actualHistoricProcessInstanceEntityImpl.queryVariables.isEmpty());
  }

  /**
   * Test {@link
   * HistoricProcessInstanceEntityImpl#HistoricProcessInstanceEntityImpl(ExecutionEntity)}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceEntityImpl#HistoricProcessInstanceEntityImpl(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricProcessInstanceEntityImpl.<init>(ExecutionEntity)"})
  public void testNewHistoricProcessInstanceEntityImpl() {
    // Arrange and Act
    HistoricProcessInstanceEntityImpl actualHistoricProcessInstanceEntityImpl =
        new HistoricProcessInstanceEntityImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    Object persistentState = actualHistoricProcessInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("", actualHistoricProcessInstanceEntityImpl.getTenantId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getProcessDefinitionVersion());
    assertNull(actualHistoricProcessInstanceEntityImpl.getDurationInMillis());
    assertNull(actualHistoricProcessInstanceEntityImpl.getId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getBusinessKey());
    assertNull(actualHistoricProcessInstanceEntityImpl.getDeploymentId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getDescription());
    assertNull(actualHistoricProcessInstanceEntityImpl.getEndActivityId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getLocalizedDescription());
    assertNull(actualHistoricProcessInstanceEntityImpl.getLocalizedName());
    assertNull(actualHistoricProcessInstanceEntityImpl.getName());
    assertNull(actualHistoricProcessInstanceEntityImpl.getProcessDefinitionKey());
    assertNull(actualHistoricProcessInstanceEntityImpl.getProcessDefinitionName());
    assertNull(actualHistoricProcessInstanceEntityImpl.getStartActivityId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getStartUserId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getSuperProcessInstanceId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getDeleteReason());
    assertNull(actualHistoricProcessInstanceEntityImpl.getProcessDefinitionId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getProcessInstanceId());
    assertNull(actualHistoricProcessInstanceEntityImpl.description);
    assertNull(actualHistoricProcessInstanceEntityImpl.name);
    assertNull(actualHistoricProcessInstanceEntityImpl.getEndTime());
    assertNull(actualHistoricProcessInstanceEntityImpl.getStartTime());
    assertNull(actualHistoricProcessInstanceEntityImpl.getQueryVariables());
    assertNull(actualHistoricProcessInstanceEntityImpl.queryVariables);
    assertEquals(12, ((Map<String, Object>) persistentState).size());
    assertFalse(actualHistoricProcessInstanceEntityImpl.isDeleted());
    assertFalse(actualHistoricProcessInstanceEntityImpl.isInserted());
    assertFalse(actualHistoricProcessInstanceEntityImpl.isUpdated());
    assertTrue(((Map<String, Object>) persistentState).containsKey("businessKey"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("deploymentId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("endStateName"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("name"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(actualHistoricProcessInstanceEntityImpl.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link
   * HistoricProcessInstanceEntityImpl#HistoricProcessInstanceEntityImpl(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceEntityImpl#HistoricProcessInstanceEntityImpl(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricProcessInstanceEntityImpl.<init>(ExecutionEntity)"})
  public void testNewHistoricProcessInstanceEntityImpl_givenNull() {
    // Arrange
    ExecutionEntityImpl processInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    processInstance.setTenantId(null);

    // Act
    HistoricProcessInstanceEntityImpl actualHistoricProcessInstanceEntityImpl =
        new HistoricProcessInstanceEntityImpl(processInstance);

    // Assert
    Object persistentState = actualHistoricProcessInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals("", actualHistoricProcessInstanceEntityImpl.getTenantId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getProcessDefinitionVersion());
    assertNull(actualHistoricProcessInstanceEntityImpl.getDurationInMillis());
    assertNull(actualHistoricProcessInstanceEntityImpl.getId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getBusinessKey());
    assertNull(actualHistoricProcessInstanceEntityImpl.getDeploymentId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getDescription());
    assertNull(actualHistoricProcessInstanceEntityImpl.getEndActivityId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getLocalizedDescription());
    assertNull(actualHistoricProcessInstanceEntityImpl.getLocalizedName());
    assertNull(actualHistoricProcessInstanceEntityImpl.getName());
    assertNull(actualHistoricProcessInstanceEntityImpl.getProcessDefinitionKey());
    assertNull(actualHistoricProcessInstanceEntityImpl.getProcessDefinitionName());
    assertNull(actualHistoricProcessInstanceEntityImpl.getStartActivityId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getStartUserId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getSuperProcessInstanceId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getDeleteReason());
    assertNull(actualHistoricProcessInstanceEntityImpl.getProcessDefinitionId());
    assertNull(actualHistoricProcessInstanceEntityImpl.getProcessInstanceId());
    assertNull(actualHistoricProcessInstanceEntityImpl.description);
    assertNull(actualHistoricProcessInstanceEntityImpl.name);
    assertNull(actualHistoricProcessInstanceEntityImpl.getEndTime());
    assertNull(actualHistoricProcessInstanceEntityImpl.getStartTime());
    assertNull(actualHistoricProcessInstanceEntityImpl.getQueryVariables());
    assertNull(actualHistoricProcessInstanceEntityImpl.queryVariables);
    assertEquals(12, ((Map<String, Object>) persistentState).size());
    assertFalse(actualHistoricProcessInstanceEntityImpl.isDeleted());
    assertFalse(actualHistoricProcessInstanceEntityImpl.isInserted());
    assertFalse(actualHistoricProcessInstanceEntityImpl.isUpdated());
    assertTrue(((Map<String, Object>) persistentState).containsKey("businessKey"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("deploymentId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("endStateName"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("name"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(actualHistoricProcessInstanceEntityImpl.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getPersistentState()}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object HistoricProcessInstanceEntityImpl.getPersistentState()"})
  public void testGetPersistentState() {
    // Arrange and Act
    Object actualPersistentState = new HistoricProcessInstanceEntityImpl().getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(12, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("businessKey"));
    assertNull(((Map<String, Object>) actualPersistentState).get("deleteReason"));
    assertNull(((Map<String, Object>) actualPersistentState).get("deploymentId"));
    assertNull(((Map<String, Object>) actualPersistentState).get("durationInMillis"));
    assertNull(((Map<String, Object>) actualPersistentState).get("endStateName"));
    assertNull(((Map<String, Object>) actualPersistentState).get("endTime"));
    assertNull(((Map<String, Object>) actualPersistentState).get("name"));
    assertNull(((Map<String, Object>) actualPersistentState).get("processDefinitionId"));
    assertNull(((Map<String, Object>) actualPersistentState).get("processDefinitionName"));
    assertNull(((Map<String, Object>) actualPersistentState).get("processDefinitionVersion"));
    assertNull(((Map<String, Object>) actualPersistentState).get("superProcessInstanceId"));
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getName()}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricProcessInstanceEntityImpl.getName()"})
  public void testGetName() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        new HistoricProcessInstanceEntityImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    historicProcessInstanceEntityImpl.setLocalizedName("");

    // Act and Assert
    assertNull(historicProcessInstanceEntityImpl.getName());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getName()}.
   *
   * <ul>
   *   <li>Given {@link HistoricProcessInstanceEntityImpl#HistoricProcessInstanceEntityImpl()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricProcessInstanceEntityImpl.getName()"})
  public void testGetName_givenHistoricProcessInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new HistoricProcessInstanceEntityImpl().getName());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getName()}.
   *
   * <ul>
   *   <li>Then return {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricProcessInstanceEntityImpl.getName()"})
  public void testGetName_thenReturnFoo() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        new HistoricProcessInstanceEntityImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    historicProcessInstanceEntityImpl.setLocalizedName("foo");

    // Act and Assert
    assertEquals("foo", historicProcessInstanceEntityImpl.getName());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getDescription()}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getDescription()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricProcessInstanceEntityImpl.getDescription()"})
  public void testGetDescription() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        new HistoricProcessInstanceEntityImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    historicProcessInstanceEntityImpl.setLocalizedDescription("");

    // Act and Assert
    assertNull(historicProcessInstanceEntityImpl.getDescription());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getDescription()}.
   *
   * <ul>
   *   <li>Given {@link HistoricProcessInstanceEntityImpl#HistoricProcessInstanceEntityImpl()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getDescription()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricProcessInstanceEntityImpl.getDescription()"})
  public void testGetDescription_givenHistoricProcessInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new HistoricProcessInstanceEntityImpl().getDescription());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getDescription()}.
   *
   * <ul>
   *   <li>Then return {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getDescription()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricProcessInstanceEntityImpl.getDescription()"})
  public void testGetDescription_thenReturnFoo() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        new HistoricProcessInstanceEntityImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    historicProcessInstanceEntityImpl.setLocalizedDescription("foo");

    // Act and Assert
    assertEquals("foo", historicProcessInstanceEntityImpl.getDescription());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Given {@link HistoricProcessInstanceEntityImpl#HistoricProcessInstanceEntityImpl()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricProcessInstanceEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_givenHistoricProcessInstanceEntityImpl_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new HistoricProcessInstanceEntityImpl().getProcessVariables().isEmpty());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor) Id is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricProcessInstanceEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_givenHistoricVariableInstanceEntityImplIdIsNull() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl =
        new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl.setCreateTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl.setLastUpdatedTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setId(null);
    historicVariableInstanceEntityImpl.setTaskId(null);

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntityImpl);

    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        new HistoricProcessInstanceEntityImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    historicProcessInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act and Assert
    assertTrue(historicProcessInstanceEntityImpl.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Given {@link HistoricVariableInstanceEntityImpl} (default constructor) TaskId is {@code
   *       foo}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricProcessInstanceEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_givenHistoricVariableInstanceEntityImplTaskIdIsFoo() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl =
        new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl.setCreateTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl.setLastUpdatedTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setId("foo");
    historicVariableInstanceEntityImpl.setTaskId("foo");

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntityImpl);

    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        new HistoricProcessInstanceEntityImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    historicProcessInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act and Assert
    assertTrue(historicProcessInstanceEntityImpl.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricProcessInstanceEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_thenReturnEmpty() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        new HistoricProcessInstanceEntityImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    historicProcessInstanceEntityImpl.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(historicProcessInstanceEntityImpl.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map HistoricProcessInstanceEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_thenReturnSizeIsOne() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl =
        new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntityImpl.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntityImpl.setCreateTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setDeleted(true);
    historicVariableInstanceEntityImpl.setDoubleValue(10.0d);
    historicVariableInstanceEntityImpl.setExecutionId("42");
    historicVariableInstanceEntityImpl.setInserted(true);
    historicVariableInstanceEntityImpl.setLastUpdatedTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntityImpl.setLongValue(42L);
    historicVariableInstanceEntityImpl.setName("Name");
    historicVariableInstanceEntityImpl.setProcessInstanceId("42");
    historicVariableInstanceEntityImpl.setRevision(1);
    historicVariableInstanceEntityImpl.setTextValue("42");
    historicVariableInstanceEntityImpl.setTextValue2("42");
    historicVariableInstanceEntityImpl.setUpdated(true);
    historicVariableInstanceEntityImpl.setVariableType(new BigDecimalType());
    historicVariableInstanceEntityImpl.setId("foo");
    historicVariableInstanceEntityImpl.setTaskId(null);

    ArrayList<HistoricVariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(historicVariableInstanceEntityImpl);

    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        new HistoricProcessInstanceEntityImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    historicProcessInstanceEntityImpl.setQueryVariables(queryVariables);

    // Act
    Map<String, Object> actualProcessVariables =
        historicProcessInstanceEntityImpl.getProcessVariables();

    // Assert
    assertEquals(1, actualProcessVariables.size());
    assertTrue(actualProcessVariables.containsKey("Name"));
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getQueryVariables()}.
   *
   * <ul>
   *   <li>Given {@link HistoricProcessInstanceEntityImpl#HistoricProcessInstanceEntityImpl()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getQueryVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List HistoricProcessInstanceEntityImpl.getQueryVariables()"})
  public void testGetQueryVariables_givenHistoricProcessInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new HistoricProcessInstanceEntityImpl().getQueryVariables());
  }

  /**
   * Test {@link HistoricProcessInstanceEntityImpl#getQueryVariables()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceEntityImpl#getQueryVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List HistoricProcessInstanceEntityImpl.getQueryVariables()"})
  public void testGetQueryVariables_thenReturnEmpty() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        new HistoricProcessInstanceEntityImpl(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    historicProcessInstanceEntityImpl.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(historicProcessInstanceEntityImpl.getQueryVariables().isEmpty());
  }
}
