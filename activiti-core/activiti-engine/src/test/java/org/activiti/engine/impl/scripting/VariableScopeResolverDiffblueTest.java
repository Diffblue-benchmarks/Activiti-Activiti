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
package org.activiti.engine.impl.scripting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class VariableScopeResolverDiffblueTest {
  /**
   * Test {@link VariableScopeResolver#containsKey(Object)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#addChildExecution(ExecutionEntity)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeResolver#containsKey(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeResolver.containsKey(Object)"})
  public void testContainsKey_thenCallsAddChildExecution() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());
    ExecutionEntityImpl variableScope = mock(ExecutionEntityImpl.class);
    when(variableScope.hasVariable(Mockito.<String>any())).thenReturn(true);
    doNothing().when(variableScope).addChildExecution(Mockito.<ExecutionEntity>any());
    variableScope.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    boolean actualContainsKeyResult = (new VariableScopeResolver(processEngineConfiguration, variableScope))
        .containsKey("Key");

    // Assert
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    verify(variableScope).addChildExecution(isA(ExecutionEntity.class));
    verify(variableScope).hasVariable(eq("Key"));
    assertTrue(actualContainsKeyResult);
  }

  /**
   * Test {@link VariableScopeResolver#containsKey(Object)}.
   * <ul>
   *   <li>When {@code execution}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeResolver#containsKey(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeResolver.containsKey(Object)"})
  public void testContainsKey_whenExecution_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());

    // Act
    boolean actualContainsKeyResult = (new VariableScopeResolver(processEngineConfiguration,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections())).containsKey("execution");

    // Assert
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    assertTrue(actualContainsKeyResult);
  }

  /**
   * Test {@link VariableScopeResolver#containsKey(Object)}.
   * <ul>
   *   <li>When {@code formService}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeResolver#containsKey(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeResolver.containsKey(Object)"})
  public void testContainsKey_whenFormService_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());

    // Act
    boolean actualContainsKeyResult = (new VariableScopeResolver(processEngineConfiguration,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections())).containsKey("formService");

    // Assert
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    assertTrue(actualContainsKeyResult);
  }

  /**
   * Test {@link VariableScopeResolver#containsKey(Object)}.
   * <ul>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeResolver#containsKey(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeResolver.containsKey(Object)"})
  public void testContainsKey_whenKey_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());

    // Act
    boolean actualContainsKeyResult = (new VariableScopeResolver(processEngineConfiguration,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections())).containsKey("Key");

    // Assert
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    assertFalse(actualContainsKeyResult);
  }

  /**
   * Test {@link VariableScopeResolver#get(Object)}.
   * <p>
   * Method under test: {@link VariableScopeResolver#get(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeResolver.get(Object)"})
  public void testGet() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());
    VariableScopeResolver variableScopeResolver = new VariableScopeResolver(processEngineConfiguration,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Object actualGetResult = variableScopeResolver.get("processEngineConfiguration");

    // Assert
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    assertSame(variableScopeResolver.processEngineConfiguration, actualGetResult);
  }

  /**
   * Test {@link VariableScopeResolver#get(Object)}.
   * <p>
   * Method under test: {@link VariableScopeResolver#get(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeResolver.get(Object)"})
  public void testGet2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());
    VariableScopeResolver variableScopeResolver = new VariableScopeResolver(processEngineConfiguration,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Object actualGetResult = variableScopeResolver.get("execution");

    // Assert
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    assertSame(variableScopeResolver.variableScope, actualGetResult);
  }

  /**
   * Test {@link VariableScopeResolver#get(Object)}.
   * <ul>
   *   <li>Then return {@link RuntimeServiceImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeResolver#get(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeResolver.get(Object)"})
  public void testGet_thenReturnRuntimeServiceImpl() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    RuntimeServiceImpl runtimeServiceImpl = new RuntimeServiceImpl();
    when(processEngineConfiguration.getRuntimeService()).thenReturn(runtimeServiceImpl);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());

    // Act
    Object actualGetResult = (new VariableScopeResolver(processEngineConfiguration,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections())).get("runtimeService");

    // Assert
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration).getRuntimeService();
    assertTrue(actualGetResult instanceof RuntimeServiceImpl);
    assertNull(((RuntimeServiceImpl) actualGetResult).getCommandExecutor());
    assertSame(runtimeServiceImpl, actualGetResult);
  }

  /**
   * Test {@link VariableScopeResolver#get(Object)}.
   * <ul>
   *   <li>Then return {@link TaskServiceImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeResolver#get(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeResolver.get(Object)"})
  public void testGet_thenReturnTaskServiceImpl() {
    // Arrange
    RuntimeServiceImpl runtimeServiceImpl = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeServiceImpl)
        .addUserIdentityLink(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    runtimeServiceImpl.addUserIdentityLink("processEngineConfiguration", "processEngineConfiguration",
        "runtimeService");
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    TaskServiceImpl taskServiceImpl = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getTaskService()).thenReturn(taskServiceImpl);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());

    // Act
    Object actualGetResult = (new VariableScopeResolver(processEngineConfiguration,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections())).get("taskService");

    // Assert
    verify(runtimeServiceImpl).addUserIdentityLink(eq("processEngineConfiguration"), eq("processEngineConfiguration"),
        eq("runtimeService"));
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration).getTaskService();
    assertTrue(actualGetResult instanceof TaskServiceImpl);
    assertNull(((TaskServiceImpl) actualGetResult).getCommandExecutor());
    assertSame(taskServiceImpl, actualGetResult);
  }

  /**
   * Test {@link VariableScopeResolver#get(Object)}.
   * <ul>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeResolver#get(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeResolver.get(Object)"})
  public void testGet_whenKey_thenReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());

    // Act
    Object actualGetResult = (new VariableScopeResolver(processEngineConfiguration,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections())).get("Key");

    // Assert
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    assertNull(actualGetResult);
  }
}
