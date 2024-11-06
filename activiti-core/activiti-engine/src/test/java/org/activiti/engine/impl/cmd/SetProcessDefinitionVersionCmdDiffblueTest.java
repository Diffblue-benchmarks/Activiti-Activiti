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

import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;

public class SetProcessDefinitionVersionCmdDiffblueTest {
  /**
   * Test
   * {@link SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}
   */
  @Test
  public void testNewSetProcessDefinitionVersionCmd_whenEmptyString() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new SetProcessDefinitionVersionCmd("", 1));

  }

  /**
   * Test
   * {@link SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}
   */
  @Test
  public void testNewSetProcessDefinitionVersionCmd_whenNull() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new SetProcessDefinitionVersionCmd(null, 1));

    assertThrows(ActivitiIllegalArgumentException.class, () -> new SetProcessDefinitionVersionCmd("42", null));
  }

  /**
   * Test
   * {@link SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}.
   * <ul>
   *   <li>When zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SetProcessDefinitionVersionCmd#SetProcessDefinitionVersionCmd(String, Integer)}
   */
  @Test
  public void testNewSetProcessDefinitionVersionCmd_whenZero() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new SetProcessDefinitionVersionCmd("42", 0));

  }
}
