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
package org.activiti.engine.test.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import jakarta.el.ELContext;
import org.activiti.engine.impl.el.ParsingElContext;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class MockElResolverDiffblueTest {
  /**
   * Test {@link MockElResolver#getCommonPropertyType(ELContext, Object)}.
   * <p>
   * Method under test:
   * {@link MockElResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  public void testGetCommonPropertyType() {
    // Arrange
    MockElResolver mockElResolver = new MockElResolver();

    // Act
    Class<?> actualCommonPropertyType = mockElResolver.getCommonPropertyType(new ParsingElContext(), JSONObject.NULL);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link MockElResolver#getFeatureDescriptors(ELContext, Object)}.
   * <p>
   * Method under test:
   * {@link MockElResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  public void testGetFeatureDescriptors() {
    // Arrange
    MockElResolver mockElResolver = new MockElResolver();

    // Act and Assert
    assertNull(mockElResolver.getFeatureDescriptors(new ParsingElContext(), JSONObject.NULL));
  }

  /**
   * Test {@link MockElResolver#getType(ELContext, Object, Object)}.
   * <p>
   * Method under test: {@link MockElResolver#getType(ELContext, Object, Object)}
   */
  @Test
  public void testGetType() {
    // Arrange
    MockElResolver mockElResolver = new MockElResolver();

    // Act and Assert
    assertNull(mockElResolver.getType(new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Test {@link MockElResolver#getValue(ELContext, Object, Object)}.
   * <p>
   * Method under test: {@link MockElResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    MockElResolver mockElResolver = new MockElResolver();

    // Act and Assert
    assertNull(mockElResolver.getValue(new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Test {@link MockElResolver#isReadOnly(ELContext, Object, Object)}.
   * <p>
   * Method under test:
   * {@link MockElResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  public void testIsReadOnly() {
    // Arrange
    MockElResolver mockElResolver = new MockElResolver();

    // Act and Assert
    assertFalse(mockElResolver.isReadOnly(new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }
}
