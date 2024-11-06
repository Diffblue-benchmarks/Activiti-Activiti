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
package org.activiti.engine.impl.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActivitiVersionDiffblueTest {
  @InjectMocks
  private ActivitiVersion activitiVersion;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiVersion#ActivitiVersion(String, List)}
   *   <li>{@link ActivitiVersion#getMainVersion()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ActivitiVersion actualActivitiVersion = new ActivitiVersion("1.0.2", new ArrayList<>());

    // Assert
    assertEquals("1.0.2", actualActivitiVersion.getMainVersion());
    assertTrue(actualActivitiVersion.alternativeVersionStrings.isEmpty());
  }

  /**
   * Test {@link ActivitiVersion#ActivitiVersion(String)}.
   * <p>
   * Method under test: {@link ActivitiVersion#ActivitiVersion(String)}
   */
  @Test
  public void testNewActivitiVersion() {
    // Arrange and Act
    ActivitiVersion actualActivitiVersion = new ActivitiVersion("1.0.2");

    // Assert
    List<String> stringList = actualActivitiVersion.alternativeVersionStrings;
    assertEquals(1, stringList.size());
    assertEquals("1.0.2", stringList.get(0));
    assertEquals("1.0.2", actualActivitiVersion.getMainVersion());
  }

  /**
   * Test {@link ActivitiVersion#matches(String)}.
   * <p>
   * Method under test: {@link ActivitiVersion#matches(String)}
   */
  @Test
  public void testMatches() {
    // Arrange, Act and Assert
    assertFalse((new ActivitiVersion("Main Version", new ArrayList<>())).matches("1.0.2"));
  }

  /**
   * Test {@link ActivitiVersion#matches(String)}.
   * <ul>
   *   <li>Given {@link ActivitiVersion#ActivitiVersion(String)} with mainVersion is
   * {@code 1.0.2}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiVersion#matches(String)}
   */
  @Test
  public void testMatches_givenActivitiVersionWithMainVersionIs102_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new ActivitiVersion("1.0.2")).matches("1.0.2"));
  }

  /**
   * Test {@link ActivitiVersion#matches(String)}.
   * <ul>
   *   <li>Given {@link ActivitiVersion#ActivitiVersion(String)} with
   * {@code Main Version}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiVersion#matches(String)}
   */
  @Test
  public void testMatches_givenActivitiVersionWithMainVersion_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ActivitiVersion("Main Version")).matches("1.0.2"));
  }

  /**
   * Test {@link ActivitiVersion#equals(Object)}, and {@link Object#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiVersion#equals(Object)}
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ActivitiVersion activitiVersion = new ActivitiVersion("1.0.2");
    ActivitiVersion activitiVersion2 = new ActivitiVersion("1.0.2");

    // Act and Assert
    assertEquals(activitiVersion, activitiVersion2);
    int notExpectedHashCodeResult = activitiVersion.hashCode();
    assertNotEquals(notExpectedHashCodeResult, activitiVersion2.hashCode());
  }

  /**
   * Test {@link ActivitiVersion#equals(Object)}, and {@link Object#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiVersion#equals(Object)}
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ActivitiVersion activitiVersion = new ActivitiVersion("1.0.2");

    // Act and Assert
    assertEquals(activitiVersion, activitiVersion);
    int expectedHashCodeResult = activitiVersion.hashCode();
    assertEquals(expectedHashCodeResult, activitiVersion.hashCode());
  }

  /**
   * Test {@link ActivitiVersion#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiVersion#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ActivitiVersion activitiVersion = new ActivitiVersion("Main Version");

    // Act and Assert
    assertNotEquals(activitiVersion, new ActivitiVersion("1.0.2"));
  }

  /**
   * Test {@link ActivitiVersion#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiVersion#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ActivitiVersion("1.0.2"), null);
  }

  /**
   * Test {@link ActivitiVersion#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiVersion#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ActivitiVersion("1.0.2"), "Different type to ActivitiVersion");
  }
}
