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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.AbstractQuery.NullHandlingOnOrder;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessInstanceQueryImplDiffblueTest {
  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ProcessInstanceQueryImpl#ProcessInstanceQueryImpl()} ExecutionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId_when42_thenProcessInstanceQueryImplExecutionIdIs42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQueryImpl actualProcessInstanceIdResult = processInstanceQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getExecutionId());
    assertEquals("42", processInstanceQueryImpl.getProcessInstanceId());
    assertSame(processInstanceQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessInstanceQueryImpl()).processInstanceId(null));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceIds(Set)"})
  public void testProcessInstanceIds_given42_whenHashSetAdd42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    HashSet<String> processInstanceIds = new HashSet<>();
    processInstanceIds.add("42");
    processInstanceIds.add("Set of process instance ids is empty");

    // Act
    ProcessInstanceQuery actualProcessInstanceIdsResult = processInstanceQueryImpl
        .processInstanceIds(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, processInstanceQueryImpl.getProcessInstanceIds());
    assertSame(processInstanceQueryImpl, actualProcessInstanceIdsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}.
   * <ul>
   *   <li>Then {@link ProcessInstanceQueryImpl#ProcessInstanceQueryImpl()} ProcessInstanceIds is {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceIds(Set)"})
  public void testProcessInstanceIds_thenProcessInstanceQueryImplProcessInstanceIdsIsHashSet() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    HashSet<String> processInstanceIds = new HashSet<>();
    processInstanceIds.add("Set of process instance ids is empty");

    // Act
    ProcessInstanceQuery actualProcessInstanceIdsResult = processInstanceQueryImpl
        .processInstanceIds(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, processInstanceQueryImpl.getProcessInstanceIds());
    assertSame(processInstanceQueryImpl, actualProcessInstanceIdsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceIds(Set)"})
  public void testProcessInstanceIds_whenHashSet_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processInstanceIds(new HashSet<>()));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceBusinessKey(String)} with {@code businessKey}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceBusinessKey(String)"})
  public void testProcessInstanceBusinessKeyWithBusinessKey() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessInstanceBusinessKeyResult = processInstanceQueryImpl
        .processInstanceBusinessKey("Business Key");

    // Assert
    assertEquals("Business Key", processInstanceQueryImpl.getBusinessKey());
    assertSame(processInstanceQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceBusinessKey(String)} with {@code businessKey}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceBusinessKey(String)"})
  public void testProcessInstanceBusinessKeyWithBusinessKey2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessInstanceQueryImpl()).processInstanceBusinessKey(null));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceBusinessKey(String, String)} with {@code businessKey}, {@code processDefinitionKey}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceBusinessKey(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceBusinessKey(String, String)"})
  public void testProcessInstanceBusinessKeyWithBusinessKeyProcessDefinitionKey() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessInstanceBusinessKeyResult = processInstanceQueryImpl
        .processInstanceBusinessKey("Business Key", "Process Definition Key");

    // Assert
    assertEquals("Business Key", processInstanceQueryImpl.getBusinessKey());
    assertEquals("Process Definition Key", processInstanceQueryImpl.getProcessDefinitionKey());
    assertSame(processInstanceQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceBusinessKey(String, String)} with {@code businessKey}, {@code processDefinitionKey}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceBusinessKey(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceBusinessKey(String, String)"})
  public void testProcessInstanceBusinessKeyWithBusinessKeyProcessDefinitionKey2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessInstanceQueryImpl()).processInstanceBusinessKey(null, "Process Definition Key"));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceTenantId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ProcessInstanceQueryImpl#ProcessInstanceQueryImpl()} TenantId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceTenantId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceTenantId(String)"})
  public void testProcessInstanceTenantId_when42_thenProcessInstanceQueryImplTenantIdIs42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessInstanceTenantIdResult = processInstanceQueryImpl.processInstanceTenantId("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getTenantId());
    assertSame(processInstanceQueryImpl, actualProcessInstanceTenantIdResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceTenantId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceTenantId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceTenantId(String)"})
  public void testProcessInstanceTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessInstanceQueryImpl()).processInstanceTenantId(null));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceTenantIdLike(String)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceTenantIdLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceTenantIdLike(String)"})
  public void testProcessInstanceTenantIdLike() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessInstanceTenantIdLikeResult = processInstanceQueryImpl
        .processInstanceTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", processInstanceQueryImpl.getTenantIdLike());
    assertSame(processInstanceQueryImpl, actualProcessInstanceTenantIdLikeResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceTenantIdLike(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceTenantIdLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceTenantIdLike(String)"})
  public void testProcessInstanceTenantIdLike_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessInstanceQueryImpl()).processInstanceTenantIdLike(null));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceWithoutTenantId()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceWithoutTenantId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceWithoutTenantId()"})
  public void testProcessInstanceWithoutTenantId() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessInstanceWithoutTenantIdResult = processInstanceQueryImpl
        .processInstanceWithoutTenantId();

    // Assert
    assertTrue(processInstanceQueryImpl.isWithoutTenantId());
    assertSame(processInstanceQueryImpl, actualProcessInstanceWithoutTenantIdResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionCategory(String)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionCategory(String)"})
  public void testProcessDefinitionCategory() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessDefinitionCategoryResult = processInstanceQueryImpl
        .processDefinitionCategory("Process Definition Category");

    // Assert
    assertEquals("Process Definition Category", processInstanceQueryImpl.getProcessDefinitionCategory());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionCategoryResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionCategory(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionCategory(String)"})
  public void testProcessDefinitionCategory_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessInstanceQueryImpl()).processDefinitionCategory(null));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionName(String)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionName(String)"})
  public void testProcessDefinitionName() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessDefinitionNameResult = processInstanceQueryImpl
        .processDefinitionName("Process Definition Name");

    // Assert
    assertEquals("Process Definition Name", processInstanceQueryImpl.getProcessDefinitionName());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionName(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionName(String)"})
  public void testProcessDefinitionName_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessInstanceQueryImpl()).processDefinitionName(null));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionVersion(Integer)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionVersion(Integer)"})
  public void testProcessDefinitionVersion() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessDefinitionVersionResult = processInstanceQueryImpl.processDefinitionVersion(1);

    // Assert
    assertEquals(1, processInstanceQueryImpl.getProcessDefinitionVersion().intValue());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionVersionResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionVersion(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionVersion(Integer)"})
  public void testProcessDefinitionVersion_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessInstanceQueryImpl()).processDefinitionVersion(null));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionId(String)}.
   * <ul>
   *   <li>Then {@link ProcessInstanceQueryImpl#ProcessInstanceQueryImpl()} ProcessDefinitionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId_thenProcessInstanceQueryImplProcessDefinitionIdIs42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQueryImpl actualProcessDefinitionIdResult = processInstanceQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getProcessDefinitionId());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessInstanceQueryImpl()).processDefinitionId(null));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionIds(Set)"})
  public void testProcessDefinitionIds() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    HashSet<String> processDefinitionIds = new HashSet<>();
    processDefinitionIds.add("Set of process definition ids is empty");

    // Act
    ProcessInstanceQuery actualProcessDefinitionIdsResult = processInstanceQueryImpl
        .processDefinitionIds(processDefinitionIds);

    // Assert
    assertSame(processDefinitionIds, processInstanceQueryImpl.getProcessDefinitionIds());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionIdsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionIds(Set)"})
  public void testProcessDefinitionIds_given42_whenHashSetAdd42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    HashSet<String> processDefinitionIds = new HashSet<>();
    processDefinitionIds.add("42");
    processDefinitionIds.add("Set of process definition ids is empty");

    // Act
    ProcessInstanceQuery actualProcessDefinitionIdsResult = processInstanceQueryImpl
        .processDefinitionIds(processDefinitionIds);

    // Assert
    assertSame(processDefinitionIds, processInstanceQueryImpl.getProcessDefinitionIds());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionIdsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionIds(Set)"})
  public void testProcessDefinitionIds_whenHashSet_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processDefinitionIds(new HashSet<>()));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionKey(String)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.processDefinitionKey(String)"})
  public void testProcessDefinitionKey() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQueryImpl actualProcessDefinitionKeyResult = processInstanceQueryImpl
        .processDefinitionKey("Process Definition Key");

    // Assert
    assertEquals("Process Definition Key", processInstanceQueryImpl.getProcessDefinitionKey());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionKey(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.processDefinitionKey(String)"})
  public void testProcessDefinitionKey_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessInstanceQueryImpl()).processDefinitionKey(null));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("Set of process definition keys is empty");

    // Act
    ProcessInstanceQuery actualProcessDefinitionKeysResult = processInstanceQueryImpl
        .processDefinitionKeys(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, processInstanceQueryImpl.getProcessDefinitionKeys());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionKeysResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys_given42_whenHashSetAdd42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("42");
    processDefinitionKeys.add("Set of process definition keys is empty");

    // Act
    ProcessInstanceQuery actualProcessDefinitionKeysResult = processInstanceQueryImpl
        .processDefinitionKeys(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, processInstanceQueryImpl.getProcessDefinitionKeys());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionKeysResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys_whenHashSet_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processDefinitionKeys(new HashSet<>()));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#deploymentId(String)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#deploymentId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.deploymentId(String)"})
  public void testDeploymentId() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQueryImpl actualDeploymentIdResult = processInstanceQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getDeploymentId());
    assertSame(processInstanceQueryImpl, actualDeploymentIdResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.deploymentIdIn(List)"})
  public void testDeploymentIdIn_given42_whenArrayListAdd42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("42");
    deploymentIds.add("foo");

    // Act
    ProcessInstanceQueryImpl actualDeploymentIdInResult = processInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, processInstanceQueryImpl.getDeploymentIds());
    assertSame(processInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.deploymentIdIn(List)"})
  public void testDeploymentIdIn_givenFoo_whenArrayListAddFoo() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("foo");

    // Act
    ProcessInstanceQueryImpl actualDeploymentIdInResult = processInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, processInstanceQueryImpl.getDeploymentIds());
    assertSame(processInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.deploymentIdIn(List)"})
  public void testDeploymentIdIn_whenArrayList() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();
    ArrayList<String> deploymentIds = new ArrayList<>();

    // Act
    ProcessInstanceQueryImpl actualDeploymentIdInResult = processInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, processInstanceQueryImpl.getDeploymentIds());
    assertSame(processInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#superProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#superProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.superProcessInstanceId(String)"})
  public void testSuperProcessInstanceId() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualSuperProcessInstanceIdResult = processInstanceQueryImpl.superProcessInstanceId("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getSuperProcessInstanceId());
    assertSame(processInstanceQueryImpl, actualSuperProcessInstanceIdResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#subProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#subProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.subProcessInstanceId(String)"})
  public void testSubProcessInstanceId() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualSubProcessInstanceIdResult = processInstanceQueryImpl.subProcessInstanceId("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getSubProcessInstanceId());
    assertSame(processInstanceQueryImpl, actualSubProcessInstanceIdResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#excludeSubprocesses(boolean)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#excludeSubprocesses(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.excludeSubprocesses(boolean)"})
  public void testExcludeSubprocesses() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualExcludeSubprocessesResult = processInstanceQueryImpl.excludeSubprocesses(true);

    // Assert
    assertTrue(processInstanceQueryImpl.isExcludeSubprocesses());
    assertSame(processInstanceQueryImpl, actualExcludeSubprocessesResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#involvedUser(String)}.
   * <ul>
   *   <li>Then {@link ProcessInstanceQueryImpl#ProcessInstanceQueryImpl()} InvolvedUser is {@code Involved User}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#involvedUser(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.involvedUser(String)"})
  public void testInvolvedUser_thenProcessInstanceQueryImplInvolvedUserIsInvolvedUser() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualInvolvedUserResult = processInstanceQueryImpl.involvedUser("Involved User");

    // Assert
    assertEquals("Involved User", processInstanceQueryImpl.getInvolvedUser());
    assertSame(processInstanceQueryImpl, actualInvolvedUserResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#involvedUser(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#involvedUser(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.involvedUser(String)"})
  public void testInvolvedUser_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ProcessInstanceQueryImpl()).involvedUser(null));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#active()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#active()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.active()"})
  public void testActive() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.active());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#suspended()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#suspended()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.suspended()"})
  public void testSuspended() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.suspended());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceName(String)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceName(String)"})
  public void testProcessInstanceName() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessInstanceNameResult = processInstanceQueryImpl.processInstanceName("Name");

    // Assert
    assertEquals("Name", processInstanceQueryImpl.getName());
    assertSame(processInstanceQueryImpl, actualProcessInstanceNameResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceNameLike(String)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceNameLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceNameLike(String)"})
  public void testProcessInstanceNameLike() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessInstanceNameLikeResult = processInstanceQueryImpl
        .processInstanceNameLike("Name Like");

    // Assert
    assertEquals("Name Like", processInstanceQueryImpl.getNameLike());
    assertSame(processInstanceQueryImpl, actualProcessInstanceNameLikeResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceNameLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceNameLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceNameLikeIgnoreCase(String)"})
  public void testProcessInstanceNameLikeIgnoreCase() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessInstanceNameLikeIgnoreCaseResult = processInstanceQueryImpl
        .processInstanceNameLikeIgnoreCase("Name Like Ignore Case");

    // Assert
    assertEquals("name like ignore case", processInstanceQueryImpl.getNameLikeIgnoreCase());
    assertSame(processInstanceQueryImpl, actualProcessInstanceNameLikeIgnoreCaseResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#endOr()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#endOr()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.endOr()"})
  public void testEndOr() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ProcessInstanceQueryImpl()).endOr());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueEquals(String, Object)} with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueEquals(String, Object)"})
  public void testVariableValueEqualsWithVariableNameVariableValue_whenNull() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueEqualsResult = processInstanceQueryImpl.variableValueEquals("Variable Name",
        JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueEquals(String, Object)} with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueEquals(String, Object)"})
  public void testVariableValueEqualsWithVariableNameVariableValue_whenNull2() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueEqualsResult = processInstanceQueryImpl.variableValueEquals("Variable Name",
        null);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueEquals(Object)} with {@code variableValue}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueEquals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueEquals(Object)"})
  public void testVariableValueEqualsWithVariableValue() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueEqualsResult = processInstanceQueryImpl
        .variableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueNotEquals(String, Object)} with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueNotEquals(String, Object)"})
  public void testVariableValueNotEqualsWithVariableNameVariableValue_whenNull() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueNotEqualsResult = processInstanceQueryImpl
        .variableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueNotEquals(String, Object)} with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueNotEquals(String, Object)"})
  public void testVariableValueNotEqualsWithVariableNameVariableValue_whenNull2() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueNotEqualsResult = processInstanceQueryImpl
        .variableValueNotEquals("Variable Name", null);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueEqualsIgnoreCase(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueEqualsIgnoreCase(String, String)"})
  public void testVariableValueEqualsIgnoreCaseWithNameValue() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueEqualsIgnoreCaseResult = processInstanceQueryImpl
        .variableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueNotEqualsIgnoreCase(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueNotEqualsIgnoreCase(String, String)"})
  public void testVariableValueNotEqualsIgnoreCaseWithNameValue() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueNotEqualsIgnoreCaseResult = processInstanceQueryImpl
        .variableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueGreaterThan(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueGreaterThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueGreaterThan(String, Object)"})
  public void testVariableValueGreaterThanWithNameValue() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueGreaterThanResult = processInstanceQueryImpl
        .variableValueGreaterThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueGreaterThanResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueGreaterThanOrEqual(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueGreaterThanOrEqual(String, Object)"})
  public void testVariableValueGreaterThanOrEqualWithNameValue() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueGreaterThanOrEqualResult = processInstanceQueryImpl
        .variableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueLessThan(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueLessThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueLessThan(String, Object)"})
  public void testVariableValueLessThanWithNameValue() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueLessThanResult = processInstanceQueryImpl.variableValueLessThan("Name",
        JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueLessThanResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueLessThanOrEqual(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueLessThanOrEqual(String, Object)"})
  public void testVariableValueLessThanOrEqualWithNameValue() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueLessThanOrEqualResult = processInstanceQueryImpl
        .variableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueLessThanOrEqualResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueLike(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueLike(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueLike(String, String)"})
  public void testVariableValueLikeWithNameValue() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueLikeResult = processInstanceQueryImpl.variableValueLike("Name", "42");

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueLikeResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.variableValueLikeIgnoreCase(String, String)"})
  public void testVariableValueLikeIgnoreCaseWithNameValue() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueLikeIgnoreCaseResult = processInstanceQueryImpl
        .variableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#startedBefore(Date)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#startedBefore(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.startedBefore(Date)"})
  public void testStartedBefore() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();
    Date beforeTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.startedBefore(beforeTime));
    assertSame(beforeTime, processInstanceQueryImpl.getStartedBefore());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#startedAfter(Date)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#startedAfter(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.startedAfter(Date)"})
  public void testStartedAfter() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();
    Date afterTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.startedAfter(afterTime));
    assertSame(afterTime, processInstanceQueryImpl.getStartedAfter());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#startedBy(String)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#startedBy(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.startedBy(String)"})
  public void testStartedBy() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualStartedByResult = processInstanceQueryImpl.startedBy("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getStartedBy());
    assertSame(processInstanceQueryImpl, actualStartedByResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}.
   * <ul>
   *   <li>Then return {@code TEMPRES_ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessInstanceQueryImpl.getMssqlOrDB2OrderBy()"})
  public void testGetMssqlOrDB2OrderBy_thenReturnTempresIdAsc() {
    // Arrange, Act and Assert
    assertEquals("TEMPRES_ID_ asc", (new ProcessInstanceQueryImpl()).getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}.
   * <ul>
   *   <li>Then return {@code TEMPRES_ID_ asc asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessInstanceQueryImpl.getMssqlOrDB2OrderBy()"})
  public void testGetMssqlOrDB2OrderBy_thenReturnTempresIdAscAsc() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();
    processInstanceQueryImpl.addOrder("RES.ID_ asc", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act and Assert
    assertEquals("TEMPRES_ID_ asc asc", processInstanceQueryImpl.getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#iswithException()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#iswithException()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessInstanceQueryImpl.iswithException()"})
  public void testIswithException() {
    // Arrange, Act and Assert
    assertFalse((new ProcessInstanceQueryImpl()).iswithException());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.involvedGroupsIn(List)"})
  public void testInvolvedGroupsIn_given42_whenArrayListAdd42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("42");
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    ProcessInstanceQuery actualInvolvedGroupsInResult = processInstanceQueryImpl.involvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, processInstanceQueryImpl.getInvolvedGroups());
    assertSame(processInstanceQueryImpl, actualInvolvedGroupsInResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}.
   * <ul>
   *   <li>Then {@link ProcessInstanceQueryImpl#ProcessInstanceQueryImpl()} InvolvedGroups is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.involvedGroupsIn(List)"})
  public void testInvolvedGroupsIn_thenProcessInstanceQueryImplInvolvedGroupsIsArrayList() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    ProcessInstanceQuery actualInvolvedGroupsInResult = processInstanceQueryImpl.involvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, processInstanceQueryImpl.getInvolvedGroups());
    assertSame(processInstanceQueryImpl, actualInvolvedGroupsInResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.involvedGroupsIn(List)"})
  public void testInvolvedGroupsIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.involvedGroupsIn(new ArrayList<>()));
  }
}
