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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.Test;

public class HandleFailedJobCmdDiffblueTest {
  /**
   * Test
   * {@link HandleFailedJobCmd#HandleFailedJobCmd(String, ProcessEngineConfigurationImpl, Throwable)}.
   * <p>
   * Method under test:
   * {@link HandleFailedJobCmd#HandleFailedJobCmd(String, ProcessEngineConfigurationImpl, Throwable)}
   */
  @Test
  public void testNewHandleFailedJobCmd() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    Throwable throwable = (new HandleFailedJobCmd("42", processEngineConfiguration, new Throwable())).exception;
    assertNull(throwable.getLocalizedMessage());
    assertNull(throwable.getMessage());
    assertNull(throwable.getCause());
    assertEquals(0, throwable.getSuppressed().length);
  }
}
