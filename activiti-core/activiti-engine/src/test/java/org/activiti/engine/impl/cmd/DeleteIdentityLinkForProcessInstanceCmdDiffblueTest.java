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

public class DeleteIdentityLinkForProcessInstanceCmdDiffblueTest {
  /**
   * Test
   * {@link DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String, String, String)}.
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkForProcessInstanceCmd() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessInstanceCmd(null, "42", "42", "Type"));

    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessInstanceCmd("42", "42", "42", null));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessInstanceCmd("42", null, null, "Type"));
  }

  /**
   * Test
   * {@link DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link DeleteIdentityLinkForProcessInstanceCmd#userId} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkForProcessInstanceCmd_whenNull_thenReturnUserIdIsNull() {
    // Arrange and Act
    DeleteIdentityLinkForProcessInstanceCmd actualDeleteIdentityLinkForProcessInstanceCmd = new DeleteIdentityLinkForProcessInstanceCmd(
        "42", null, "42", "Type");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("Type", actualDeleteIdentityLinkForProcessInstanceCmd.type);
    assertNull(actualDeleteIdentityLinkForProcessInstanceCmd.userId);
  }

  /**
   * Test
   * {@link DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String, String, String)}.
   * <ul>
   *   <li>When {@code Type}.</li>
   *   <li>Then return {@link DeleteIdentityLinkForProcessInstanceCmd#userId} is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkForProcessInstanceCmd_whenType_thenReturnUserIdIs42() {
    // Arrange and Act
    DeleteIdentityLinkForProcessInstanceCmd actualDeleteIdentityLinkForProcessInstanceCmd = new DeleteIdentityLinkForProcessInstanceCmd(
        "42", "42", "42", "Type");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.userId);
    assertEquals("Type", actualDeleteIdentityLinkForProcessInstanceCmd.type);
  }

  /**
   * Test
   * {@link DeleteIdentityLinkForProcessInstanceCmd#validateParams(String, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkForProcessInstanceCmd#validateParams(String, String, String, String)}
   */
  @Test
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type")).validateParams("42", "42", null,
            "Type"));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type")).validateParams("42", "42", "42",
            null));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type")).validateParams(null, null, "42",
            "Type"));
  }
}
