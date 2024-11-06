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

import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.junit.Test;

public class VariableScopeResolverDiffblueTest {
  /**
   * Test
   * {@link VariableScopeResolver#VariableScopeResolver(ProcessEngineConfigurationImpl, VariableScope)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableScopeResolver#VariableScopeResolver(ProcessEngineConfigurationImpl, VariableScope)}
   */
  @Test
  public void testNewVariableScopeResolver_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new VariableScopeResolver(new JtaProcessEngineConfiguration(), null));

  }

  /**
   * Test
   * {@link VariableScopeResolver#VariableScopeResolver(ProcessEngineConfigurationImpl, VariableScope)}.
   * <ul>
   *   <li>When SharedInstance.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableScopeResolver#VariableScopeResolver(ProcessEngineConfigurationImpl, VariableScope)}
   */
  @Test
  public void testNewVariableScopeResolver_whenSharedInstance_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> new VariableScopeResolver(processEngineConfiguration, NoExecutionVariableScope.getSharedInstance()));

  }
}
