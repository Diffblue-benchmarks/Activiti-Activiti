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
package org.activiti.engine.impl.variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class EntityMetaDataDiffblueTest {
  /**
   * Test {@link EntityMetaData#getIdType()}.
   * <p>
   * Method under test: {@link EntityMetaData#getIdType()}
   */
  @Test
  public void testGetIdType() {
    // Arrange, Act and Assert
    assertNull((new EntityMetaData()).getIdType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link EntityMetaData}
   *   <li>{@link EntityMetaData#setEntityClass(Class)}
   *   <li>{@link EntityMetaData#setJPAEntity(boolean)}
   *   <li>{@link EntityMetaData#getEntityClass()}
   *   <li>{@link EntityMetaData#getIdField()}
   *   <li>{@link EntityMetaData#getIdMethod()}
   *   <li>{@link EntityMetaData#isJPAEntity()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    EntityMetaData actualEntityMetaData = new EntityMetaData();
    Class<Object> entityClass = Object.class;
    actualEntityMetaData.setEntityClass(entityClass);
    actualEntityMetaData.setJPAEntity(true);
    Class<?> actualEntityClass = actualEntityMetaData.getEntityClass();
    actualEntityMetaData.getIdField();
    actualEntityMetaData.getIdMethod();

    // Assert that nothing has changed
    assertTrue(actualEntityMetaData.isJPAEntity());
    Class<Object> expectedEntityClass = Object.class;
    assertEquals(expectedEntityClass, actualEntityClass);
    assertSame(entityClass, actualEntityClass);
  }
}
