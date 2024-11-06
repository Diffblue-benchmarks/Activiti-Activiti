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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.junit.Test;

public class AbstractSetProcessDefinitionStateCmdDiffblueTest {
  /**
   * Test
   * {@link AbstractSetProcessDefinitionStateCmd#findProcessDefinition(CommandContext)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractSetProcessDefinitionStateCmd#findProcessDefinition(CommandContext)}
   */
  @Test
  public void testFindProcessDefinition_thenReturnSizeIsOne() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    ActivateProcessDefinitionCmd activateProcessDefinitionCmd = new ActivateProcessDefinitionCmd(
        processDefinitionEntity, true,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "42");

    // Act
    List<ProcessDefinitionEntity> actualFindProcessDefinitionResult = activateProcessDefinitionCmd
        .findProcessDefinition(null);

    // Assert
    assertEquals(1, actualFindProcessDefinitionResult.size());
    assertSame(activateProcessDefinitionCmd.processDefinitionEntity, actualFindProcessDefinitionResult.get(0));
  }

  /**
   * Test
   * {@link AbstractSetProcessDefinitionStateCmd#findProcessDefinition(CommandContext)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractSetProcessDefinitionStateCmd#findProcessDefinition(CommandContext)}
   */
  @Test
  public void testFindProcessDefinition_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ActivateProcessDefinitionCmd(null, true,
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "42"))
            .findProcessDefinition(null));
  }
}
