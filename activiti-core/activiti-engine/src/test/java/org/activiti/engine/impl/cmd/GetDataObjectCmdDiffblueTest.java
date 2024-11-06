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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class GetDataObjectCmdDiffblueTest {
  /**
   * Test {@link GetDataObjectCmd#GetDataObjectCmd(String, String, boolean)}.
   * <ul>
   *   <li>When {@code Data Object Name}.</li>
   *   <li>Then return {@link GetDataObjectCmd#locale} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetDataObjectCmd#GetDataObjectCmd(String, String, boolean)}
   */
  @Test
  public void testNewGetDataObjectCmd_whenDataObjectName_thenReturnLocaleIsNull() {
    // Arrange and Act
    GetDataObjectCmd actualGetDataObjectCmd = new GetDataObjectCmd("42", "Data Object Name", true);

    // Assert
    assertEquals("42", actualGetDataObjectCmd.executionId);
    assertEquals("Data Object Name", actualGetDataObjectCmd.dataObjectName);
    assertNull(actualGetDataObjectCmd.locale);
    assertFalse(actualGetDataObjectCmd.withLocalizationFallback);
    assertTrue(actualGetDataObjectCmd.isLocal);
  }

  /**
   * Test
   * {@link GetDataObjectCmd#GetDataObjectCmd(String, String, boolean, String, boolean)}.
   * <ul>
   *   <li>When {@code en}.</li>
   *   <li>Then return {@link GetDataObjectCmd#locale} is {@code en}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetDataObjectCmd#GetDataObjectCmd(String, String, boolean, String, boolean)}
   */
  @Test
  public void testNewGetDataObjectCmd_whenEn_thenReturnLocaleIsEn() {
    // Arrange and Act
    GetDataObjectCmd actualGetDataObjectCmd = new GetDataObjectCmd("42", "Data Object Name", true, "en", true);

    // Assert
    assertEquals("42", actualGetDataObjectCmd.executionId);
    assertEquals("Data Object Name", actualGetDataObjectCmd.dataObjectName);
    assertEquals("en", actualGetDataObjectCmd.locale);
    assertTrue(actualGetDataObjectCmd.isLocal);
    assertTrue(actualGetDataObjectCmd.withLocalizationFallback);
  }
}
