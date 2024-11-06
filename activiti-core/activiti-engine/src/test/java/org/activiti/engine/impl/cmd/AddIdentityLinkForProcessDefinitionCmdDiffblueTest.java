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
import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;

public class AddIdentityLinkForProcessDefinitionCmdDiffblueTest {
  /**
   * Test
   * {@link AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String, String)}.
   * <p>
   * Method under test:
   * {@link AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String, String)}
   */
  @Test
  public void testNewAddIdentityLinkForProcessDefinitionCmd() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkForProcessDefinitionCmd(null, "42", "42"));

    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkForProcessDefinitionCmd("42", null, null));
  }

  /**
   * Test
   * {@link AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String, String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@link AddIdentityLinkForProcessDefinitionCmd#userId} is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String, String)}
   */
  @Test
  public void testNewAddIdentityLinkForProcessDefinitionCmd_when42_thenReturnUserIdIs42() {
    // Arrange and Act
    AddIdentityLinkForProcessDefinitionCmd actualAddIdentityLinkForProcessDefinitionCmd = new AddIdentityLinkForProcessDefinitionCmd(
        "42", "42", "42");

    // Assert
    assertEquals("42", actualAddIdentityLinkForProcessDefinitionCmd.groupId);
    assertEquals("42", actualAddIdentityLinkForProcessDefinitionCmd.processDefinitionId);
    assertEquals("42", actualAddIdentityLinkForProcessDefinitionCmd.userId);
  }

  /**
   * Test
   * {@link AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link AddIdentityLinkForProcessDefinitionCmd#userId} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String, String)}
   */
  @Test
  public void testNewAddIdentityLinkForProcessDefinitionCmd_whenNull_thenReturnUserIdIsNull() {
    // Arrange and Act
    AddIdentityLinkForProcessDefinitionCmd actualAddIdentityLinkForProcessDefinitionCmd = new AddIdentityLinkForProcessDefinitionCmd(
        "42", null, "42");

    // Assert
    assertEquals("42", actualAddIdentityLinkForProcessDefinitionCmd.groupId);
    assertEquals("42", actualAddIdentityLinkForProcessDefinitionCmd.processDefinitionId);
    assertNull(actualAddIdentityLinkForProcessDefinitionCmd.userId);
  }

  /**
   * Test
   * {@link AddIdentityLinkForProcessDefinitionCmd#validateParams(String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddIdentityLinkForProcessDefinitionCmd#validateParams(String, String, String)}
   */
  @Test
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new AddIdentityLinkForProcessDefinitionCmd("42", "42", "42")).validateParams("42", "42", null));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new AddIdentityLinkForProcessDefinitionCmd("42", "42", "42")).validateParams(null, null, "42"));
  }
}
