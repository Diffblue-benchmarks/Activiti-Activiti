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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.io.UnsupportedEncodingException;
import org.junit.Test;

public class ResourceEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ResourceEntityImpl}
   *   <li>{@link ResourceEntityImpl#setBytes(byte[])}
   *   <li>{@link ResourceEntityImpl#setDeploymentId(String)}
   *   <li>{@link ResourceEntityImpl#setGenerated(boolean)}
   *   <li>{@link ResourceEntityImpl#setName(String)}
   *   <li>{@link ResourceEntityImpl#toString()}
   *   <li>{@link ResourceEntityImpl#getBytes()}
   *   <li>{@link ResourceEntityImpl#getDeploymentId()}
   *   <li>{@link ResourceEntityImpl#getName()}
   *   <li>{@link ResourceEntityImpl#getPersistentState()}
   *   <li>{@link ResourceEntityImpl#isGenerated()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() throws UnsupportedEncodingException {
    // Arrange and Act
    ResourceEntityImpl actualResourceEntityImpl = new ResourceEntityImpl();
    byte[] bytes = "AXAXAXAX".getBytes("UTF-8");
    actualResourceEntityImpl.setBytes(bytes);
    actualResourceEntityImpl.setDeploymentId("42");
    actualResourceEntityImpl.setGenerated(true);
    actualResourceEntityImpl.setName("Name");
    String actualToStringResult = actualResourceEntityImpl.toString();
    byte[] actualBytes = actualResourceEntityImpl.getBytes();
    String actualDeploymentId = actualResourceEntityImpl.getDeploymentId();
    String actualName = actualResourceEntityImpl.getName();
    actualResourceEntityImpl.getPersistentState();
    boolean actualIsGeneratedResult = actualResourceEntityImpl.isGenerated();

    // Assert that nothing has changed
    assertEquals("42", actualDeploymentId);
    assertEquals("Name", actualName);
    assertEquals("ResourceEntity[id=null, name=Name]", actualToStringResult);
    assertFalse(actualResourceEntityImpl.isDeleted());
    assertFalse(actualResourceEntityImpl.isInserted());
    assertFalse(actualResourceEntityImpl.isUpdated());
    assertTrue(actualIsGeneratedResult);
    assertSame(bytes, actualBytes);
  }
}
