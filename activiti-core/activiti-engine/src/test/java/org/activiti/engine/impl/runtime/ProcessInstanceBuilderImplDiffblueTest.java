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
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessInstanceBuilderImplDiffblueTest {
  @InjectMocks
  private ProcessInstanceBuilderImpl processInstanceBuilderImpl;

  @Mock
  private RuntimeServiceImpl runtimeServiceImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessInstanceBuilderImpl#ProcessInstanceBuilderImpl(RuntimeServiceImpl)}
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
  public void testGettersAndSetters() {
    // Arrange and Act
    ProcessInstanceBuilderImpl actualProcessInstanceBuilderImpl = new ProcessInstanceBuilderImpl(
        new RuntimeServiceImpl());
    ProcessInstanceBuilder actualBusinessKeyResult = actualProcessInstanceBuilderImpl.businessKey("Business Key");
    ProcessInstanceBuilder actualMessageNameResult = actualProcessInstanceBuilderImpl.messageName("Message Name");
    ProcessInstanceBuilder actualNameResult = actualProcessInstanceBuilderImpl.name("Process Instance Name");
    ProcessInstanceBuilder actualProcessDefinitionIdResult = actualProcessInstanceBuilderImpl.processDefinitionId("42");
    ProcessInstanceBuilder actualProcessDefinitionKeyResult = actualProcessInstanceBuilderImpl
        .processDefinitionKey("Process Definition Key");
    ProcessInstanceBuilder actualTenantIdResult = actualProcessInstanceBuilderImpl.tenantId("42");
    String actualBusinessKey = actualProcessInstanceBuilderImpl.getBusinessKey();
    String actualMessageName = actualProcessInstanceBuilderImpl.getMessageName();
    String actualProcessDefinitionId = actualProcessInstanceBuilderImpl.getProcessDefinitionId();
    String actualProcessDefinitionKey = actualProcessInstanceBuilderImpl.getProcessDefinitionKey();
    String actualProcessInstanceName = actualProcessInstanceBuilderImpl.getProcessInstanceName();
    String actualTenantId = actualProcessInstanceBuilderImpl.getTenantId();
    Map<String, Object> actualTransientVariables = actualProcessInstanceBuilderImpl.getTransientVariables();

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
   * <p>
   * Method under test: {@link ProcessInstanceBuilderImpl#variables(Map)}
   */
  @Test
  public void testVariables() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl = new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilderImpl.variables(null);

    // Act and Assert
    assertSame(processInstanceBuilderImpl, processInstanceBuilderImpl.variables(null));
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#variables(Map)}.
   * <ul>
   *   <li>Given {@link BiFunction}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceBuilderImpl#variables(Map)}
   */
  @Test
  public void testVariables_givenBiFunction_whenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl = new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());

    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));
    variables.put("foo", JSONObject.NULL);

    // Act and Assert
    assertSame(processInstanceBuilderImpl, processInstanceBuilderImpl.variables(variables));
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#variables(Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceBuilderImpl#variables(Map)}
   */
  @Test
  public void testVariables_givenFoo_whenHashMapFooIsNull() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl = new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", JSONObject.NULL);

    // Act and Assert
    assertSame(processInstanceBuilderImpl, processInstanceBuilderImpl.variables(variables));
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#variables(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceBuilderImpl#variables(Map)}
   */
  @Test
  public void testVariables_whenHashMap() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl = new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());

    // Act and Assert
    assertSame(processInstanceBuilderImpl, processInstanceBuilderImpl.variables(new HashMap<>()));
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#variable(String, Object)}.
   * <p>
   * Method under test:
   * {@link ProcessInstanceBuilderImpl#variable(String, Object)}
   */
  @Test
  public void testVariable() {
    // Arrange, Act and Assert
    assertSame(processInstanceBuilderImpl, processInstanceBuilderImpl.variable("Variable Name", JSONObject.NULL));
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#transientVariables(Map)}.
   * <p>
   * Method under test: {@link ProcessInstanceBuilderImpl#transientVariables(Map)}
   */
  @Test
  public void testTransientVariables() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl = new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilderImpl.transientVariables(null);

    // Act and Assert
    assertSame(processInstanceBuilderImpl, processInstanceBuilderImpl.transientVariables(null));
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#transientVariables(Map)}.
   * <ul>
   *   <li>Given {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceBuilderImpl#transientVariables(Map)}
   */
  @Test
  public void testTransientVariables_givenBiFunction() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl = new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());

    HashMap<String, Object> transientVariables = new HashMap<>();
    transientVariables.computeIfPresent("foo", mock(BiFunction.class));
    transientVariables.put("foo", JSONObject.NULL);

    // Act and Assert
    assertSame(processInstanceBuilderImpl, processInstanceBuilderImpl.transientVariables(transientVariables));
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#transientVariables(Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceBuilderImpl#transientVariables(Map)}
   */
  @Test
  public void testTransientVariables_givenFoo_whenHashMapFooIsNull() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl = new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());

    HashMap<String, Object> transientVariables = new HashMap<>();
    transientVariables.put("foo", JSONObject.NULL);

    // Act and Assert
    assertSame(processInstanceBuilderImpl, processInstanceBuilderImpl.transientVariables(transientVariables));
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#transientVariables(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceBuilderImpl#transientVariables(Map)}
   */
  @Test
  public void testTransientVariables_whenHashMap() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl = new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());

    // Act and Assert
    assertSame(processInstanceBuilderImpl, processInstanceBuilderImpl.transientVariables(new HashMap<>()));
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#transientVariable(String, Object)}.
   * <p>
   * Method under test:
   * {@link ProcessInstanceBuilderImpl#transientVariable(String, Object)}
   */
  @Test
  public void testTransientVariable() {
    // Arrange, Act and Assert
    assertSame(processInstanceBuilderImpl,
        processInstanceBuilderImpl.transientVariable("Variable Name", JSONObject.NULL));
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#hasProcessDefinitionIdOrKey()}.
   * <p>
   * Method under test:
   * {@link ProcessInstanceBuilderImpl#hasProcessDefinitionIdOrKey()}
   */
  @Test
  public void testHasProcessDefinitionIdOrKey() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl = new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilderImpl.processDefinitionId(null);
    processInstanceBuilderImpl.processDefinitionKey("foo");

    // Act and Assert
    assertTrue(processInstanceBuilderImpl.hasProcessDefinitionIdOrKey());
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#hasProcessDefinitionIdOrKey()}.
   * <p>
   * Method under test:
   * {@link ProcessInstanceBuilderImpl#hasProcessDefinitionIdOrKey()}
   */
  @Test
  public void testHasProcessDefinitionIdOrKey2() {
    // Arrange
    ProcessInstanceBuilderImpl processInstanceBuilderImpl = new ProcessInstanceBuilderImpl(new RuntimeServiceImpl());
    processInstanceBuilderImpl.processDefinitionId("foo");
    processInstanceBuilderImpl.processDefinitionKey(null);

    // Act and Assert
    assertTrue(processInstanceBuilderImpl.hasProcessDefinitionIdOrKey());
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#hasProcessDefinitionIdOrKey()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInstanceBuilderImpl#hasProcessDefinitionIdOrKey()}
   */
  @Test
  public void testHasProcessDefinitionIdOrKey_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ProcessInstanceBuilderImpl(new RuntimeServiceImpl())).hasProcessDefinitionIdOrKey());
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#start()}.
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceBuilderImpl#start()}
   */
  @Test
  public void testStart_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(runtimeService.startProcessInstance(Mockito.<ProcessInstanceBuilderImpl>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    // Act
    ProcessInstance actualStartResult = (new ProcessInstanceBuilderImpl(runtimeService)).start();

    // Assert
    verify(runtimeService).startProcessInstance(isA(ProcessInstanceBuilderImpl.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualStartResult);
  }

  /**
   * Test {@link ProcessInstanceBuilderImpl#create()}.
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceBuilderImpl#create()}
   */
  @Test
  public void testCreate_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(runtimeService.createProcessInstance(Mockito.<ProcessInstanceBuilderImpl>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);

    // Act
    ProcessInstance actualCreateResult = (new ProcessInstanceBuilderImpl(runtimeService)).create();

    // Assert
    verify(runtimeService).createProcessInstance(isA(ProcessInstanceBuilderImpl.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualCreateResult);
  }
}
