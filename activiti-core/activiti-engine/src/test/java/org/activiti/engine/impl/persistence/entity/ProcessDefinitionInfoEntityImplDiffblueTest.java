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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Map;
import org.junit.Test;

public class ProcessDefinitionInfoEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link ProcessDefinitionInfoEntityImpl}
   *   <li>{@link ProcessDefinitionInfoEntityImpl#setInfoJsonId(String)}
   *   <li>{@link ProcessDefinitionInfoEntityImpl#setProcessDefinitionId(String)}
   *   <li>{@link ProcessDefinitionInfoEntityImpl#getInfoJsonId()}
   *   <li>{@link ProcessDefinitionInfoEntityImpl#getProcessDefinitionId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ProcessDefinitionInfoEntityImpl actualProcessDefinitionInfoEntityImpl = new ProcessDefinitionInfoEntityImpl();
    actualProcessDefinitionInfoEntityImpl.setInfoJsonId("42");
    actualProcessDefinitionInfoEntityImpl.setProcessDefinitionId("42");
    String actualInfoJsonId = actualProcessDefinitionInfoEntityImpl.getInfoJsonId();

    // Assert that nothing has changed
    assertEquals("42", actualInfoJsonId);
    assertEquals("42", actualProcessDefinitionInfoEntityImpl.getProcessDefinitionId());
    assertEquals(1, actualProcessDefinitionInfoEntityImpl.getRevision());
    assertFalse(actualProcessDefinitionInfoEntityImpl.isDeleted());
    assertFalse(actualProcessDefinitionInfoEntityImpl.isInserted());
    assertFalse(actualProcessDefinitionInfoEntityImpl.isUpdated());
  }

  /**
   * Test {@link ProcessDefinitionInfoEntityImpl#getPersistentState()}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionInfoEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState() {
    // Arrange and Act
    Object actualPersistentState = (new ProcessDefinitionInfoEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(2, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("infoJsonId"));
    assertNull(((Map<String, Object>) actualPersistentState).get("processDefinitionId"));
  }
}
