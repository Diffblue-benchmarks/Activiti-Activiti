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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class GetDataObjectsCmdDiffblueTest {
  /**
   * Test
   * {@link GetDataObjectsCmd#GetDataObjectsCmd(String, Collection, boolean)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@link GetDataObjectsCmd#locale} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetDataObjectsCmd#GetDataObjectsCmd(String, Collection, boolean)}
   */
  @Test
  public void testNewGetDataObjectsCmd_whenArrayList_thenReturnLocaleIsNull() {
    // Arrange and Act
    GetDataObjectsCmd actualGetDataObjectsCmd = new GetDataObjectsCmd("42", new ArrayList<>(), true);

    // Assert
    Collection<String> collection = actualGetDataObjectsCmd.dataObjectNames;
    assertTrue(collection instanceof List);
    assertEquals("42", actualGetDataObjectsCmd.executionId);
    assertNull(actualGetDataObjectsCmd.locale);
    assertFalse(actualGetDataObjectsCmd.withLocalizationFallback);
    assertTrue(collection.isEmpty());
    assertTrue(actualGetDataObjectsCmd.isLocal);
  }

  /**
   * Test
   * {@link GetDataObjectsCmd#GetDataObjectsCmd(String, Collection, boolean, String, boolean)}.
   * <ul>
   *   <li>When {@code en}.</li>
   *   <li>Then return {@link GetDataObjectsCmd#locale} is {@code en}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetDataObjectsCmd#GetDataObjectsCmd(String, Collection, boolean, String, boolean)}
   */
  @Test
  public void testNewGetDataObjectsCmd_whenEn_thenReturnLocaleIsEn() {
    // Arrange and Act
    GetDataObjectsCmd actualGetDataObjectsCmd = new GetDataObjectsCmd("42", new ArrayList<>(), true, "en", true);

    // Assert
    Collection<String> collection = actualGetDataObjectsCmd.dataObjectNames;
    assertTrue(collection instanceof List);
    assertEquals("42", actualGetDataObjectsCmd.executionId);
    assertEquals("en", actualGetDataObjectsCmd.locale);
    assertTrue(collection.isEmpty());
    assertTrue(actualGetDataObjectsCmd.isLocal);
    assertTrue(actualGetDataObjectsCmd.withLocalizationFallback);
  }

  /**
   * Test {@link GetDataObjectsCmd#getVariables(ExecutionEntity, CommandContext)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetDataObjectsCmd#getVariables(ExecutionEntity, CommandContext)}
   */
  @Test
  public void testGetVariables_thenReturnEmpty() {
    // Arrange
    GetDataObjectsCmd getDataObjectsCmd = new GetDataObjectsCmd("42", new ArrayList<>(), true);

    // Act and Assert
    assertTrue(
        getDataObjectsCmd.getVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), null).isEmpty());
  }

  /**
   * Test {@link GetDataObjectsCmd#getVariables(ExecutionEntity, CommandContext)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetDataObjectsCmd#getVariables(ExecutionEntity, CommandContext)}
   */
  @Test
  public void testGetVariables_thenReturnEmpty2() {
    // Arrange
    GetDataObjectsCmd getDataObjectsCmd = new GetDataObjectsCmd("42", new ArrayList<>(), false);

    // Act and Assert
    assertTrue(
        getDataObjectsCmd.getVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), null).isEmpty());
  }
}
