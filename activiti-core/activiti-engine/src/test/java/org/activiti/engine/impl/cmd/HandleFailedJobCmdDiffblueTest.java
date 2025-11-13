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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HandleFailedJobCmdDiffblueTest {
  /**
   * Test {@link HandleFailedJobCmd#HandleFailedJobCmd(String, ProcessEngineConfigurationImpl,
   * Throwable)}.
   *
   * <p>Method under test: {@link HandleFailedJobCmd#HandleFailedJobCmd(String,
   * ProcessEngineConfigurationImpl, Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HandleFailedJobCmd.<init>(String, ProcessEngineConfigurationImpl, Throwable)"
  })
  public void testNewHandleFailedJobCmd() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    HandleFailedJobCmd actualHandleFailedJobCmd =
        new HandleFailedJobCmd("42", processEngineConfiguration, new Throwable());

    // Assert
    Throwable throwable = actualHandleFailedJobCmd.exception;
    assertNull(throwable.getLocalizedMessage());
    assertNull(throwable.getMessage());
    assertNull(throwable.getCause());
    assertEquals(0, throwable.getSuppressed().length);
  }
}
