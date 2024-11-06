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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.util.json.JSONException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class TimerChangeProcessDefinitionSuspensionStateJobHandlerDiffblueTest {
  /**
   * Test
   * {@link TimerChangeProcessDefinitionSuspensionStateJobHandler#createJobHandlerConfiguration(boolean)}.
   * <ul>
   *   <li>Then return {@code {"includeProcessInstances":false}}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerChangeProcessDefinitionSuspensionStateJobHandler#createJobHandlerConfiguration(boolean)}
   */
  @Test
  public void testCreateJobHandlerConfiguration_thenReturnIncludeProcessInstancesFalse() {
    // Arrange, Act and Assert
    assertEquals("{\"includeProcessInstances\":false}",
        TimerChangeProcessDefinitionSuspensionStateJobHandler.createJobHandlerConfiguration(false));
  }

  /**
   * Test
   * {@link TimerChangeProcessDefinitionSuspensionStateJobHandler#createJobHandlerConfiguration(boolean)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then return {@code {"includeProcessInstances":true}}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerChangeProcessDefinitionSuspensionStateJobHandler#createJobHandlerConfiguration(boolean)}
   */
  @Test
  public void testCreateJobHandlerConfiguration_whenTrue_thenReturnIncludeProcessInstancesTrue() {
    // Arrange, Act and Assert
    assertEquals("{\"includeProcessInstances\":true}",
        TimerChangeProcessDefinitionSuspensionStateJobHandler.createJobHandlerConfiguration(true));
  }

  /**
   * Test
   * {@link TimerChangeProcessDefinitionSuspensionStateJobHandler#getIncludeProcessInstances(JSONObject)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerChangeProcessDefinitionSuspensionStateJobHandler#getIncludeProcessInstances(JSONObject)}
   */
  @Test
  public void testGetIncludeProcessInstances_givenFalse_thenReturnFalse() throws JSONException {
    // Arrange
    JSONObject jobHandlerCfgJson = mock(JSONObject.class);
    when(jobHandlerCfgJson.getBoolean(Mockito.<String>any())).thenReturn(false);

    // Act
    boolean actualIncludeProcessInstances = TimerChangeProcessDefinitionSuspensionStateJobHandler
        .getIncludeProcessInstances(jobHandlerCfgJson);

    // Assert
    verify(jobHandlerCfgJson).getBoolean(eq("includeProcessInstances"));
    assertFalse(actualIncludeProcessInstances);
  }

  /**
   * Test
   * {@link TimerChangeProcessDefinitionSuspensionStateJobHandler#getIncludeProcessInstances(JSONObject)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerChangeProcessDefinitionSuspensionStateJobHandler#getIncludeProcessInstances(JSONObject)}
   */
  @Test
  public void testGetIncludeProcessInstances_givenTrue_thenReturnTrue() throws JSONException {
    // Arrange
    JSONObject jobHandlerCfgJson = mock(JSONObject.class);
    when(jobHandlerCfgJson.getBoolean(Mockito.<String>any())).thenReturn(true);

    // Act
    boolean actualIncludeProcessInstances = TimerChangeProcessDefinitionSuspensionStateJobHandler
        .getIncludeProcessInstances(jobHandlerCfgJson);

    // Assert
    verify(jobHandlerCfgJson).getBoolean(eq("includeProcessInstances"));
    assertTrue(actualIncludeProcessInstances);
  }
}
