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
package org.activiti.engine.impl.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ProcessInstanceBuilderImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProcessInstanceBuilderImpl#ProcessInstanceBuilderImpl(RuntimeServiceImpl)}
   *   <li>{@link ProcessInstanceBuilderImpl#businessKey(String)}
   *   <li>{@link ProcessInstanceBuilderImpl#messageName(String)}
   *   <li>{@link ProcessInstanceBuilderImpl#name(String)}
   *   <li>{@link ProcessInstanceBuilderImpl#processDefinitionId(String)}
   *   <li>{@link ProcessInstanceBuilderImpl#processDefinitionKey(String)}
   *   <li>{@link ProcessInstanceBuilderImpl#tenantId(String)}
   *   <li>{@link ProcessInstanceBuilderImpl#getBusinessKey()}
   *   <li>{@link ProcessInstanceBuilderImpl#getMessageName()}
   *   <li>{@link ProcessInstanceBuilderImpl#getProcessDefinitionId()}
   *   <li>{@link ProcessInstanceBuilderImpl#getProcessDefinitionKey()}
   *   <li>{@link ProcessInstanceBuilderImpl#getProcessInstanceName()}
   *   <li>{@link ProcessInstanceBuilderImpl#getTenantId()}
   *   <li>{@link ProcessInstanceBuilderImpl#getTransientVariables()}
   *   <li>{@link ProcessInstanceBuilderImpl#getVariables()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessInstanceBuilderImpl.<init>(RuntimeServiceImpl)",
    "ProcessInstanceBuilder ProcessInstanceBuilderImpl.businessKey(String)",
    "String ProcessInstanceBuilderImpl.getBusinessKey()",
    "String ProcessInstanceBuilderImpl.getMessageName()",
    "String ProcessInstanceBuilderImpl.getProcessDefinitionId()",
    "String ProcessInstanceBuilderImpl.getProcessDefinitionKey()",
    "String ProcessInstanceBuilderImpl.getProcessInstanceName()",
    "String ProcessInstanceBuilderImpl.getTenantId()",
    "Map ProcessInstanceBuilderImpl.getTransientVariables()",
    "Map ProcessInstanceBuilderImpl.getVariables()",
    "ProcessInstanceBuilder ProcessInstanceBuilderImpl.messageName(String)",
    "ProcessInstanceBuilder ProcessInstanceBuilderImpl.name(String)",
    "ProcessInstanceBuilder ProcessInstanceBuilderImpl.processDefinitionId(String)",
    "ProcessInstanceBuilder ProcessInstanceBuilderImpl.processDefinitionKey(String)",
    "ProcessInstanceBuilder ProcessInstanceBuilderImpl.tenantId(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ProcessInstanceBuilderImpl actualProcessInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    ProcessInstanceBuilder actualBusinessKeyResult =
        actualProcessInstanceBuilderImpl.businessKey("Business Key");
    ProcessInstanceBuilder actualMessageNameResult =
        actualProcessInstanceBuilderImpl.messageName("Message Name");
    ProcessInstanceBuilder actualNameResult =
        actualProcessInstanceBuilderImpl.name("Process Instance Name");
    ProcessInstanceBuilder actualProcessDefinitionIdResult =
        actualProcessInstanceBuilderImpl.processDefinitionId("42");
    ProcessInstanceBuilder actualProcessDefinitionKeyResult =
        actualProcessInstanceBuilderImpl.processDefinitionKey("Process Definition Key");
    ProcessInstanceBuilder actualTenantIdResult = actualProcessInstanceBuilderImpl.tenantId("42");
    String actualBusinessKey = actualProcessInstanceBuilderImpl.getBusinessKey();
    String actualMessageName = actualProcessInstanceBuilderImpl.getMessageName();
    String actualProcessDefinitionId = actualProcessInstanceBuilderImpl.getProcessDefinitionId();
    String actualProcessDefinitionKey = actualProcessInstanceBuilderImpl.getProcessDefinitionKey();
    String actualProcessInstanceName = actualProcessInstanceBuilderImpl.getProcessInstanceName();
    String actualTenantId = actualProcessInstanceBuilderImpl.getTenantId();
    Map<String, Object> actualTransientVariables =
        actualProcessInstanceBuilderImpl.getTransientVariables();

    // Assert
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("42", actualTenantId);
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("Message Name", actualMessageName);
    assertEquals("Process Definition Key", actualProcessDefinitionKey);
    assertEquals("Process Instance Name", actualProcessInstanceName);
    assertNull(actualTransientVariables);
    assertNull(actualProcessInstanceBuilderImpl.getVariables());
    assertNull(actualProcessInstanceBuilderImpl.runtimeService.getCommandExecutor());
    assertSame(actualProcessInstanceBuilderImpl, actualBusinessKeyResult);
    assertSame(actualProcessInstanceBuilderImpl, actualMessageNameResult);
    assertSame(actualProcessInstanceBuilderImpl, actualNameResult);
    assertSame(actualProcessInstanceBuilderImpl, actualProcessDefinitionIdResult);
    assertSame(actualProcessInstanceBuilderImpl, actualProcessDefinitionKeyResult);
    assertSame(actualProcessInstanceBuilderImpl, actualTenantIdResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#variables(Map)}.
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#variables(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceBuilder ProcessInstanceBuilderImpl.variables(Map)"})
  public void testVariables() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());

    // Act
    ProcessInstanceBuilder actualVariablesResult =
        processInstanceBuilderImpl.variables(new HashMap<>());

    // Assert
    assertSame(processInstanceBuilderImpl, actualVariablesResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#variables(Map)}.
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#variables(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceBuilder ProcessInstanceBuilderImpl.variables(Map)"})
  public void testVariables2() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilderImpl.variables(new HashMap<>());

    // Act
    ProcessInstanceBuilder actualVariablesResult =
        processInstanceBuilderImpl.variables(new HashMap<>());

    // Assert
    assertSame(processInstanceBuilderImpl, actualVariablesResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#variables(Map)}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#variables(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceBuilder ProcessInstanceBuilderImpl.variables(Map)"})
  public void testVariables_givenKey_whenHashMapKeyIsNull() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("Key", JSONObject.NULL);

    // Act
    ProcessInstanceBuilder actualVariablesResult = processInstanceBuilderImpl.variables(variables);

    // Assert
    assertSame(processInstanceBuilderImpl, actualVariablesResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#variables(Map)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#variables(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceBuilder ProcessInstanceBuilderImpl.variables(Map)"})
  public void testVariables_whenNull() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilderImpl.variables(new HashMap<>());

    // Act
    ProcessInstanceBuilder actualVariablesResult = processInstanceBuilderImpl.variables(null);

    // Assert
    assertSame(processInstanceBuilderImpl, actualVariablesResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#variable(String, Object)}.
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#variable(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceBuilder ProcessInstanceBuilderImpl.variable(String, Object)"})
  public void testVariable() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());

    // Act
    ProcessInstanceBuilder actualVariableResult =
        processInstanceBuilderImpl.variable("Variable Name", JSONObject.NULL);

    // Assert
    assertSame(processInstanceBuilderImpl, actualVariableResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#variable(String, Object)}.
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#variable(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceBuilder ProcessInstanceBuilderImpl.variable(String, Object)"})
  public void testVariable2() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilderImpl.variables(new HashMap<>());

    // Act
    ProcessInstanceBuilder actualVariableResult =
        processInstanceBuilderImpl.variable("Variable Name", JSONObject.NULL);

    // Assert
    assertSame(processInstanceBuilderImpl, actualVariableResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#transientVariables(Map)}.
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#transientVariables(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceBuilder ProcessInstanceBuilderImpl.transientVariables(Map)"})
  public void testTransientVariables() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());

    // Act
    ProcessInstanceBuilder actualTransientVariablesResult =
        processInstanceBuilderImpl.transientVariables(new HashMap<>());

    // Assert
    assertSame(processInstanceBuilderImpl, actualTransientVariablesResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#transientVariables(Map)}.
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#transientVariables(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceBuilder ProcessInstanceBuilderImpl.transientVariables(Map)"})
  public void testTransientVariables2() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilderImpl.transientVariables(new HashMap<>());

    // Act
    ProcessInstanceBuilder actualTransientVariablesResult =
        processInstanceBuilderImpl.transientVariables(new HashMap<>());

    // Assert
    assertSame(processInstanceBuilderImpl, actualTransientVariablesResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#transientVariables(Map)}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#transientVariables(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceBuilder ProcessInstanceBuilderImpl.transientVariables(Map)"})
  public void testTransientVariables_givenKey_whenHashMapKeyIsNull() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());

    HashMap<String, Object> transientVariables = new HashMap<>();
    transientVariables.put("Key", JSONObject.NULL);

    // Act
    ProcessInstanceBuilder actualTransientVariablesResult =
        processInstanceBuilderImpl.transientVariables(transientVariables);

    // Assert
    assertSame(processInstanceBuilderImpl, actualTransientVariablesResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#transientVariables(Map)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#transientVariables(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstanceBuilder ProcessInstanceBuilderImpl.transientVariables(Map)"})
  public void testTransientVariables_whenNull() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilderImpl.transientVariables(new HashMap<>());

    // Act
    ProcessInstanceBuilder actualTransientVariablesResult =
        processInstanceBuilderImpl.transientVariables(null);

    // Assert
    assertSame(processInstanceBuilderImpl, actualTransientVariablesResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#transientVariable(String, Object)}.
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#transientVariable(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstanceBuilder ProcessInstanceBuilderImpl.transientVariable(String, Object)"
  })
  public void testTransientVariable() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());

    // Act
    ProcessInstanceBuilder actualTransientVariableResult =
        processInstanceBuilderImpl.transientVariable("Variable Name", JSONObject.NULL);

    // Assert
    assertSame(processInstanceBuilderImpl, actualTransientVariableResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#transientVariable(String, Object)}.
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#transientVariable(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstanceBuilder ProcessInstanceBuilderImpl.transientVariable(String, Object)"
  })
  public void testTransientVariable2() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilderImpl.transientVariables(new HashMap<>());

    // Act
    ProcessInstanceBuilder actualTransientVariableResult =
        processInstanceBuilderImpl.transientVariable("Variable Name", JSONObject.NULL);

    // Assert
    assertSame(processInstanceBuilderImpl, actualTransientVariableResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#hasProcessDefinitionIdOrKey()}.
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#hasProcessDefinitionIdOrKey()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessInstanceBuilderImpl.hasProcessDefinitionIdOrKey()"})
  public void testHasProcessDefinitionIdOrKey() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilderImpl.processDefinitionId("foo");
    processInstanceBuilderImpl.processDefinitionKey("foo");

    // Act and Assert
    assertTrue(processInstanceBuilderImpl.hasProcessDefinitionIdOrKey());
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#hasProcessDefinitionIdOrKey()}.
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#hasProcessDefinitionIdOrKey()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessInstanceBuilderImpl.hasProcessDefinitionIdOrKey()"})
  public void testHasProcessDefinitionIdOrKey2() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl =
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilderImpl.processDefinitionId(null);
    processInstanceBuilderImpl.processDefinitionKey("foo");

    // Act and Assert
    assertTrue(processInstanceBuilderImpl.hasProcessDefinitionIdOrKey());
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#hasProcessDefinitionIdOrKey()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#hasProcessDefinitionIdOrKey()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessInstanceBuilderImpl.hasProcessDefinitionIdOrKey()"})
  public void testHasProcessDefinitionIdOrKey_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(
        new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()).hasProcessDefinitionIdOrKey());
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#start()}.
   *
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstance ProcessInstanceBuilderImpl.start()"})
  public void testStart_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(runtimeService.startProcessInstance(Mockito.<ProcessInstanceBuilderImpl>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    // Act
    ProcessInstance actualStartResult = new ProcessInstanceBuilderImpl(runtimeService).start();

    // Assert
    verify(runtimeService).startProcessInstance(isA(ProcessInstanceBuilderImpl.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#create()}.
   *
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceBuilderImpl#create()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstance ProcessInstanceBuilderImpl.create()"})
  public void testCreate_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(runtimeService.createProcessInstance(Mockito.<ProcessInstanceBuilderImpl>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    // Act
    ProcessInstance actualCreateResult = new ProcessInstanceBuilderImpl(runtimeService).create();

    // Assert
    verify(runtimeService).createProcessInstance(isA(ProcessInstanceBuilderImpl.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualCreateResult);
  }
}
