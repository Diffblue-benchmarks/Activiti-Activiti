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

public class SetProcessInstanceBusinessKeyCmdDiffblueTest {
  /**
   * Test
   * {@link SetProcessInstanceBusinessKeyCmd#SetProcessInstanceBusinessKeyCmd(String, String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SetProcessInstanceBusinessKeyCmd#SetProcessInstanceBusinessKeyCmd(String, String)}
   */
  @Test
  public void testNewSetProcessInstanceBusinessKeyCmd_when42() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new SetProcessInstanceBusinessKeyCmd("42", null));

  }

  /**
   * Test
   * {@link SetProcessInstanceBusinessKeyCmd#SetProcessInstanceBusinessKeyCmd(String, String)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SetProcessInstanceBusinessKeyCmd#SetProcessInstanceBusinessKeyCmd(String, String)}
   */
  @Test
  public void testNewSetProcessInstanceBusinessKeyCmd_whenEmptyString() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new SetProcessInstanceBusinessKeyCmd("", "Business Key"));

  }

  /**
   * Test
   * {@link SetProcessInstanceBusinessKeyCmd#SetProcessInstanceBusinessKeyCmd(String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SetProcessInstanceBusinessKeyCmd#SetProcessInstanceBusinessKeyCmd(String, String)}
   */
  @Test
  public void testNewSetProcessInstanceBusinessKeyCmd_whenNull() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new SetProcessInstanceBusinessKeyCmd(null, "Business Key"));

  }
}
