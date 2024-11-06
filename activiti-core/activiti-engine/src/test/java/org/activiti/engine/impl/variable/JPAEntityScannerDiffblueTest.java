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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import jakarta.persistence.Id;
import org.junit.Test;

public class JPAEntityScannerDiffblueTest {
  /**
   * Test {@link JPAEntityScanner#scanClass(Class)}.
   * <ul>
   *   <li>When {@code jakarta.persistence.Id}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityScanner#scanClass(Class)}
   */
  @Test
  public void testScanClass_whenJakartaPersistenceId() {
    // Arrange
    JPAEntityScanner jpaEntityScanner = new JPAEntityScanner();
    Class<Id> clazz = Id.class;

    // Act
    EntityMetaData actualScanClassResult = jpaEntityScanner.scanClass(clazz);

    // Assert
    assertNull(actualScanClassResult.getEntityClass());
    assertNull(actualScanClassResult.getIdType());
    assertNull(actualScanClassResult.getIdField());
    assertNull(actualScanClassResult.getIdMethod());
    assertFalse(actualScanClassResult.isJPAEntity());
  }

  /**
   * Test {@link JPAEntityScanner#scanClass(Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityScanner#scanClass(Class)}
   */
  @Test
  public void testScanClass_whenJavaLangObject() {
    // Arrange
    JPAEntityScanner jpaEntityScanner = new JPAEntityScanner();
    Class<Object> clazz = Object.class;

    // Act
    EntityMetaData actualScanClassResult = jpaEntityScanner.scanClass(clazz);

    // Assert
    assertNull(actualScanClassResult.getEntityClass());
    assertNull(actualScanClassResult.getIdType());
    assertNull(actualScanClassResult.getIdField());
    assertNull(actualScanClassResult.getIdMethod());
    assertFalse(actualScanClassResult.isJPAEntity());
  }

  /**
   * Test {@link JPAEntityScanner#scanClass(Class)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityScanner#scanClass(Class)}
   */
  @Test
  public void testScanClass_whenNull() {
    // Arrange and Act
    EntityMetaData actualScanClassResult = (new JPAEntityScanner()).scanClass(null);

    // Assert
    assertNull(actualScanClassResult.getEntityClass());
    assertNull(actualScanClassResult.getIdType());
    assertNull(actualScanClassResult.getIdField());
    assertNull(actualScanClassResult.getIdMethod());
    assertFalse(actualScanClassResult.isJPAEntity());
  }
}
