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
package org.activiti.engine;

import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.Clock;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessEngineConfigurationDiffblueTest {
  /**
   * Test {@link ProcessEngineConfiguration#setClock(Clock)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setClock(Clock)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setClock(Clock)"})
  public void testSetClock() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultClockImpl clock = new DefaultClockImpl();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setClock(clock));
    assertSame(clock, jtaProcessEngineConfiguration.getClock());
  }
}
