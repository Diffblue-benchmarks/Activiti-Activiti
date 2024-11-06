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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.junit.Test;
import org.mockito.Mockito;

public class ScriptBindingsFactoryDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ScriptBindingsFactory#ScriptBindingsFactory(ProcessEngineConfigurationImpl, List)}
   *   <li>{@link ScriptBindingsFactory#setResolverFactories(List)}
   *   <li>{@link ScriptBindingsFactory#getResolverFactories()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ScriptBindingsFactory actualScriptBindingsFactory = new ScriptBindingsFactory(processEngineConfiguration,
        new ArrayList<>());
    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    actualScriptBindingsFactory.setResolverFactories(resolverFactories);
    List<ResolverFactory> actualResolverFactories = actualScriptBindingsFactory.getResolverFactories();

    // Assert that nothing has changed
    assertTrue(actualResolverFactories.isEmpty());
    assertSame(resolverFactories, actualResolverFactories);
  }

  /**
   * Test {@link ScriptBindingsFactory#createResolvers(VariableScope)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptBindingsFactory#createResolvers(VariableScope)}
   */
  @Test
  public void testCreateResolvers_thenReturnEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ScriptBindingsFactory scriptBindingsFactory = new ScriptBindingsFactory(processEngineConfiguration,
        new ArrayList<>());

    // Act and Assert
    assertTrue(scriptBindingsFactory.createResolvers(NoExecutionVariableScope.getSharedInstance()).isEmpty());
  }

  /**
   * Test {@link ScriptBindingsFactory#createResolvers(VariableScope)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptBindingsFactory#createResolvers(VariableScope)}
   */
  @Test
  public void testCreateResolvers_thenReturnSizeIsOne() {
    // Arrange
    ResolverFactory resolverFactory = mock(ResolverFactory.class);
    BeansResolverFactory beansResolverFactory = new BeansResolverFactory();
    when(resolverFactory.createResolver(Mockito.<ProcessEngineConfigurationImpl>any(), Mockito.<VariableScope>any()))
        .thenReturn(beansResolverFactory);

    ArrayList<ResolverFactory> resolverFactories = new ArrayList<>();
    resolverFactories.add(resolverFactory);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    ScriptBindingsFactory scriptBindingsFactory = new ScriptBindingsFactory(processEngineConfiguration,
        new ArrayList<>());
    scriptBindingsFactory.setResolverFactories(resolverFactories);

    // Act
    List<Resolver> actualCreateResolversResult = scriptBindingsFactory
        .createResolvers(NoExecutionVariableScope.getSharedInstance());

    // Assert
    verify(resolverFactory).createResolver(isA(ProcessEngineConfigurationImpl.class), isA(VariableScope.class));
    assertEquals(1, actualCreateResolversResult.size());
    Resolver getResult = actualCreateResolversResult.get(0);
    assertTrue(getResult instanceof BeansResolverFactory);
    assertNull(((BeansResolverFactory) getResult).processEngineConfiguration);
    assertSame(beansResolverFactory, getResult);
  }
}
