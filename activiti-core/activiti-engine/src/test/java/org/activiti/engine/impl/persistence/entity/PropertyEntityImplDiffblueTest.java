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
import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PropertyEntityImplDiffblueTest {
  @InjectMocks
  private PropertyEntityImpl propertyEntityImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link PropertyEntityImpl}
   *   <li>{@link PropertyEntityImpl#setName(String)}
   *   <li>{@link PropertyEntityImpl#setValue(String)}
   *   <li>{@link PropertyEntityImpl#toString()}
   *   <li>{@link PropertyEntityImpl#getId()}
   *   <li>{@link PropertyEntityImpl#getName()}
   *   <li>{@link PropertyEntityImpl#getPersistentState()}
   *   <li>{@link PropertyEntityImpl#getValue()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    PropertyEntityImpl actualPropertyEntityImpl = new PropertyEntityImpl();
    actualPropertyEntityImpl.setName("Name");
    actualPropertyEntityImpl.setValue("42");
    String actualToStringResult = actualPropertyEntityImpl.toString();
    String actualId = actualPropertyEntityImpl.getId();
    String actualName = actualPropertyEntityImpl.getName();
    Object actualPersistentState = actualPropertyEntityImpl.getPersistentState();

    // Assert that nothing has changed
    assertEquals("42", actualPropertyEntityImpl.getValue());
    assertEquals("42", actualPersistentState);
    assertEquals("Name", actualId);
    assertEquals("Name", actualName);
    assertEquals("PropertyEntity[name=Name, value=42]", actualToStringResult);
    assertEquals(1, actualPropertyEntityImpl.getRevision());
    assertFalse(actualPropertyEntityImpl.isDeleted());
    assertFalse(actualPropertyEntityImpl.isInserted());
    assertFalse(actualPropertyEntityImpl.isUpdated());
  }

  /**
   * Test {@link PropertyEntityImpl#setId(String)}.
   * <p>
   * Method under test: {@link PropertyEntityImpl#setId(String)}
   */
  @Test
  public void testSetId() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new PropertyEntityImpl()).setId("42"));
  }
}
