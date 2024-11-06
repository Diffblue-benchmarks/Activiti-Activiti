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
package org.activiti.engine.impl.persistence.deploy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

public class ProcessDefinitionInfoCacheObjectDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link ProcessDefinitionInfoCacheObject}
   *   <li>{@link ProcessDefinitionInfoCacheObject#setId(String)}
   *   <li>{@link ProcessDefinitionInfoCacheObject#setInfoNode(ObjectNode)}
   *   <li>{@link ProcessDefinitionInfoCacheObject#setRevision(int)}
   *   <li>{@link ProcessDefinitionInfoCacheObject#getId()}
   *   <li>{@link ProcessDefinitionInfoCacheObject#getInfoNode()}
   *   <li>{@link ProcessDefinitionInfoCacheObject#getRevision()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ProcessDefinitionInfoCacheObject actualProcessDefinitionInfoCacheObject = new ProcessDefinitionInfoCacheObject();
    actualProcessDefinitionInfoCacheObject.setId("42");
    ObjectNode infoNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));
    actualProcessDefinitionInfoCacheObject.setInfoNode(infoNode);
    actualProcessDefinitionInfoCacheObject.setRevision(1);
    String actualId = actualProcessDefinitionInfoCacheObject.getId();
    ObjectNode actualInfoNode = actualProcessDefinitionInfoCacheObject.getInfoNode();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals(1, actualProcessDefinitionInfoCacheObject.getRevision());
    assertSame(infoNode, actualInfoNode);
  }
}
