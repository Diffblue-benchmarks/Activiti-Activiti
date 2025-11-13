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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ScriptBindingsDiffblueTest {
  /**
   * Test {@link ScriptBindings#containsKey(Object)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptBindings#containsKey(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ScriptBindings.containsKey(Object)"})
  public void testContainsKey_givenHashMapNullIsNull_thenReturnTrue() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    beans.put(JSONObject.NULL, JSONObject.NULL);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBeans(beans);

    BeansResolverFactory beansResolverFactory = new BeansResolverFactory();
    beansResolverFactory.createResolver(
        processEngineConfiguration, NoExecutionVariableScope.getSharedInstance());

    ArrayList<Resolver> scriptResolvers = new ArrayList<>();
    scriptResolvers.add(beansResolverFactory);

    // Act and Assert
    assertTrue(
        new ScriptBindings(scriptResolvers, NoExecutionVariableScope.getSharedInstance())
            .containsKey(JSONObject.NULL));
  }

  /**
   * Test {@link ScriptBindings#get(Object)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   *   <li>Then return {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptBindings#get(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ScriptBindings.get(Object)"})
  public void testGet_givenHashMapNullIsNull_thenReturnNull() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    beans.put(JSONObject.NULL, JSONObject.NULL);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBeans(beans);

    BeansResolverFactory beansResolverFactory = new BeansResolverFactory();
    beansResolverFactory.createResolver(
        processEngineConfiguration, NoExecutionVariableScope.getSharedInstance());

    ArrayList<Resolver> scriptResolvers = new ArrayList<>();
    scriptResolvers.add(beansResolverFactory);
    Object object = JSONObject.NULL;

    // Act
    Object actualGetResult =
        new ScriptBindings(scriptResolvers, NoExecutionVariableScope.getSharedInstance())
            .get(object);

    // Assert
    assertSame(object, actualGetResult);
  }
}
