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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BeansResolverFactoryDiffblueTest {
  /**
   * Test {@link BeansResolverFactory#createResolver(ProcessEngineConfigurationImpl, VariableScope)}.
   * <p>
   * Method under test: {@link BeansResolverFactory#createResolver(ProcessEngineConfigurationImpl, VariableScope)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Resolver BeansResolverFactory.createResolver(ProcessEngineConfigurationImpl, VariableScope)"})
  public void testCreateResolver() {
    // Arrange
    BeansResolverFactory beansResolverFactory = new BeansResolverFactory();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    Resolver actualCreateResolverResult = beansResolverFactory.createResolver(processEngineConfiguration,
        NoExecutionVariableScope.getSharedInstance());

    // Assert
    assertTrue(beansResolverFactory.processEngineConfiguration instanceof JtaProcessEngineConfiguration);
    assertTrue(
        ((BeansResolverFactory) actualCreateResolverResult).processEngineConfiguration instanceof JtaProcessEngineConfiguration);
    assertTrue(actualCreateResolverResult instanceof BeansResolverFactory);
    assertSame(beansResolverFactory, actualCreateResolverResult);
  }

  /**
   * Test {@link BeansResolverFactory#containsKey(Object)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BeansResolverFactory#containsKey(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean BeansResolverFactory.containsKey(Object)"})
  public void testContainsKey_givenHashMapNullIsNull_thenReturnTrue() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    beans.put(JSONObject.NULL, JSONObject.NULL);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBeans(beans);

    BeansResolverFactory beansResolverFactory = new BeansResolverFactory();
    beansResolverFactory.createResolver(processEngineConfiguration, NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertTrue(beansResolverFactory.containsKey(JSONObject.NULL));
  }

  /**
   * Test {@link BeansResolverFactory#containsKey(Object)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Beans is {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BeansResolverFactory#containsKey(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean BeansResolverFactory.containsKey(Object)"})
  public void testContainsKey_givenJtaProcessEngineConfigurationBeansIsHashMap_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBeans(new HashMap<>());

    BeansResolverFactory beansResolverFactory = new BeansResolverFactory();
    beansResolverFactory.createResolver(processEngineConfiguration, NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertFalse(beansResolverFactory.containsKey(JSONObject.NULL));
  }

  /**
   * Test {@link BeansResolverFactory#get(Object)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Beans is {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BeansResolverFactory#get(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object BeansResolverFactory.get(Object)"})
  public void testGet_givenJtaProcessEngineConfigurationBeansIsHashMap_thenReturnNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBeans(new HashMap<>());

    BeansResolverFactory beansResolverFactory = new BeansResolverFactory();
    beansResolverFactory.createResolver(processEngineConfiguration, NoExecutionVariableScope.getSharedInstance());

    // Act and Assert
    assertNull(beansResolverFactory.get(JSONObject.NULL));
  }

  /**
   * Test new {@link BeansResolverFactory} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link BeansResolverFactory}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BeansResolverFactory.<init>()"})
  public void testNewBeansResolverFactory() {
    // Arrange, Act and Assert
    assertNull((new BeansResolverFactory()).processEngineConfiguration);
  }
}
