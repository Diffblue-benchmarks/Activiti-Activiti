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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.NativeDeploymentQuery;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class AbstractNativeQueryDiffblueTest {
  /**
   * Test {@link AbstractNativeQuery#setCommandExecutor(CommandExecutor)}.
   *
   * <p>Method under test: {@link AbstractNativeQuery#setCommandExecutor(CommandExecutor)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractNativeQuery AbstractNativeQuery.setCommandExecutor(CommandExecutor)"})
  public void testSetCommandExecutor() {
    // Arrange
    NativeDeploymentQueryImpl nativeDeploymentQueryImpl =
        new NativeDeploymentQueryImpl((CommandContext) null);
    CommandConfig defaultConfig = new CommandConfig();
    CommandContextInterceptor first = new CommandContextInterceptor();

    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, first);

    // Act
    AbstractNativeQuery<NativeDeploymentQuery, Deployment> actualSetCommandExecutorResult =
        nativeDeploymentQueryImpl.setCommandExecutor(commandExecutor);

    // Assert
    CommandExecutor commandExecutor2 = nativeDeploymentQueryImpl.commandExecutor;
    assertTrue(commandExecutor2 instanceof CommandExecutorImpl);
    assertSame(nativeDeploymentQueryImpl, actualSetCommandExecutorResult);
    assertSame(defaultConfig, commandExecutor2.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor2).getFirst());
  }

  /**
   * Test {@link AbstractNativeQuery#sql(String)}.
   *
   * <ul>
   *   <li>Then return {@link NativeDeploymentQueryImpl#NativeDeploymentQueryImpl(CommandContext)}
   *       with commandContext is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractNativeQuery#sql(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.engine.query.NativeQuery AbstractNativeQuery.sql(String)"})
  public void testSql_thenReturnNativeDeploymentQueryImplWithCommandContextIsNull() {
    // Arrange
    NativeDeploymentQueryImpl nativeDeploymentQueryImpl =
        new NativeDeploymentQueryImpl((CommandContext) null);

    // Act
    NativeDeploymentQuery actualSqlResult = nativeDeploymentQueryImpl.sql("Sql Statement");

    // Assert
    assertSame(nativeDeploymentQueryImpl, actualSqlResult);
  }

  /**
   * Test {@link AbstractNativeQuery#parameter(String, Object)}.
   *
   * <ul>
   *   <li>Then return {@link NativeDeploymentQueryImpl#NativeDeploymentQueryImpl(CommandContext)}
   *       with commandContext is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractNativeQuery#parameter(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.query.NativeQuery AbstractNativeQuery.parameter(String, Object)"
  })
  public void testParameter_thenReturnNativeDeploymentQueryImplWithCommandContextIsNull() {
    // Arrange
    NativeDeploymentQueryImpl nativeDeploymentQueryImpl =
        new NativeDeploymentQueryImpl((CommandContext) null);

    // Act
    NativeDeploymentQuery actualParameterResult =
        nativeDeploymentQueryImpl.parameter("Name", JSONObject.NULL);

    // Assert
    assertSame(nativeDeploymentQueryImpl, actualParameterResult);
  }

  /**
   * Test {@link AbstractNativeQuery#getParameters()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link AbstractNativeQuery#getParameters()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Map AbstractNativeQuery.getParameters()"})
  public void testGetParameters_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new NativeDeploymentQueryImpl((CommandContext) null).getParameters().isEmpty());
  }
}
