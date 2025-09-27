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
package org.activiti.engine.impl.jobexecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.util.json.Cookie;
import org.activiti.engine.impl.util.json.JSONException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TimerChangeProcessDefinitionSuspensionStateJobHandlerDiffblueTest {
  /**
   * Test {@link
   * TimerChangeProcessDefinitionSuspensionStateJobHandler#createJobHandlerConfiguration(boolean)}.
   *
   * <ul>
   *   <li>Then return {@code {"includeProcessInstances":false}}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TimerChangeProcessDefinitionSuspensionStateJobHandler#createJobHandlerConfiguration(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.String TimerChangeProcessDefinitionSuspensionStateJobHandler.createJobHandlerConfiguration(boolean)"
  })
  public void testCreateJobHandlerConfiguration_thenReturnIncludeProcessInstancesFalse() {
    // Arrange, Act and Assert
    assertEquals(
        "{\"includeProcessInstances\":false}",
        TimerChangeProcessDefinitionSuspensionStateJobHandler.createJobHandlerConfiguration(false));
  }

  /**
   * Test {@link
   * TimerChangeProcessDefinitionSuspensionStateJobHandler#createJobHandlerConfiguration(boolean)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@code {"includeProcessInstances":true}}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TimerChangeProcessDefinitionSuspensionStateJobHandler#createJobHandlerConfiguration(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.String TimerChangeProcessDefinitionSuspensionStateJobHandler.createJobHandlerConfiguration(boolean)"
  })
  public void testCreateJobHandlerConfiguration_whenTrue_thenReturnIncludeProcessInstancesTrue() {
    // Arrange, Act and Assert
    assertEquals(
        "{\"includeProcessInstances\":true}",
        TimerChangeProcessDefinitionSuspensionStateJobHandler.createJobHandlerConfiguration(true));
  }

  /**
   * Test {@link
   * TimerChangeProcessDefinitionSuspensionStateJobHandler#getIncludeProcessInstances(JSONObject)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TimerChangeProcessDefinitionSuspensionStateJobHandler#getIncludeProcessInstances(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TimerChangeProcessDefinitionSuspensionStateJobHandler.getIncludeProcessInstances(JSONObject)"
  })
  public void testGetIncludeProcessInstances_givenFalse_thenReturnFalse() throws JSONException {
    // Arrange
    JSONObject jobHandlerCfgJson = Cookie.toJSONObject("=;");
    jobHandlerCfgJson.put("includeProcessInstances", false);

    // Act and Assert
    assertFalse(
        TimerChangeProcessDefinitionSuspensionStateJobHandler.getIncludeProcessInstances(
            jobHandlerCfgJson));
  }

  /**
   * Test {@link
   * TimerChangeProcessDefinitionSuspensionStateJobHandler#getIncludeProcessInstances(JSONObject)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TimerChangeProcessDefinitionSuspensionStateJobHandler#getIncludeProcessInstances(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TimerChangeProcessDefinitionSuspensionStateJobHandler.getIncludeProcessInstances(JSONObject)"
  })
  public void testGetIncludeProcessInstances_givenTrue_thenReturnTrue() throws JSONException {
    // Arrange
    JSONObject jobHandlerCfgJson = Cookie.toJSONObject("=;");
    jobHandlerCfgJson.put("includeProcessInstances", true);

    // Act and Assert
    assertTrue(
        TimerChangeProcessDefinitionSuspensionStateJobHandler.getIncludeProcessInstances(
            jobHandlerCfgJson));
  }
}
