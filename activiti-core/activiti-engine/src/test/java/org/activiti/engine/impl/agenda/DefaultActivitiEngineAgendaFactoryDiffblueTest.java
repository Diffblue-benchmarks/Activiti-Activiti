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
package org.activiti.engine.impl.agenda;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiEngineAgenda;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DefaultActivitiEngineAgendaFactoryDiffblueTest {
  /**
   * Test {@link DefaultActivitiEngineAgendaFactory#createAgenda(CommandContext)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link DefaultActivitiEngineAgenda}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultActivitiEngineAgendaFactory#createAgenda(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiEngineAgenda DefaultActivitiEngineAgendaFactory.createAgenda(CommandContext)"
  })
  public void testCreateAgenda_whenNull_thenReturnDefaultActivitiEngineAgenda() {
    // Arrange and Act
    ActivitiEngineAgenda actualCreateAgendaResult =
        new DefaultActivitiEngineAgendaFactory().createAgenda(null);

    // Assert
    assertTrue(actualCreateAgendaResult instanceof DefaultActivitiEngineAgenda);
    assertNull(actualCreateAgendaResult.getNextOperation());
    assertNull(((DefaultActivitiEngineAgenda) actualCreateAgendaResult).commandContext);
    assertTrue(((DefaultActivitiEngineAgenda) actualCreateAgendaResult).operations.isEmpty());
    assertTrue(actualCreateAgendaResult.isEmpty());
  }
}
