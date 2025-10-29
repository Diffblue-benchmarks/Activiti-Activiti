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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricIdentityLinkEntityImplDiffblueTest {
  @InjectMocks
  private HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl;

  /**
   * Method under test:
   * {@link HistoricIdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState() {
    // Arrange and Act
    Object actualPersistentState = (new HistoricIdentityLinkEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(2, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("id"));
    assertNull(((Map<String, Object>) actualPersistentState).get("type"));
  }

  /**
   * Method under test:
   * {@link HistoricIdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState2() {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl = new HistoricIdentityLinkEntityImpl();
    historicIdentityLinkEntityImpl.setDeleted(true);
    historicIdentityLinkEntityImpl.setId("42");
    historicIdentityLinkEntityImpl.setInserted(true);
    historicIdentityLinkEntityImpl.setType("Type");
    historicIdentityLinkEntityImpl.setUpdated(true);
    historicIdentityLinkEntityImpl.setUserId(null);
    historicIdentityLinkEntityImpl.setGroupId(null);
    historicIdentityLinkEntityImpl.setTaskId(null);
    historicIdentityLinkEntityImpl.setProcessInstanceId("foo");
    historicIdentityLinkEntityImpl.setDetails(null);

    // Act
    Object actualPersistentState = historicIdentityLinkEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) actualPersistentState).size());
    assertEquals("42", ((Map<String, String>) actualPersistentState).get("id"));
    assertEquals("Type", ((Map<String, String>) actualPersistentState).get("type"));
    assertEquals("foo", ((Map<String, String>) actualPersistentState).get("processInstanceId"));
  }

  /**
   * Method under test:
   * {@link HistoricIdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState3() {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl = new HistoricIdentityLinkEntityImpl();
    historicIdentityLinkEntityImpl.setDeleted(true);
    historicIdentityLinkEntityImpl.setId("42");
    historicIdentityLinkEntityImpl.setInserted(true);
    historicIdentityLinkEntityImpl.setType("Type");
    historicIdentityLinkEntityImpl.setUpdated(true);
    historicIdentityLinkEntityImpl.setUserId(null);
    historicIdentityLinkEntityImpl.setGroupId(null);
    historicIdentityLinkEntityImpl.setTaskId("foo");
    historicIdentityLinkEntityImpl.setProcessInstanceId(null);
    historicIdentityLinkEntityImpl.setDetails(null);

    // Act
    Object actualPersistentState = historicIdentityLinkEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) actualPersistentState).size());
    assertEquals("42", ((Map<String, String>) actualPersistentState).get("id"));
    assertEquals("Type", ((Map<String, String>) actualPersistentState).get("type"));
    assertEquals("foo", ((Map<String, String>) actualPersistentState).get("taskId"));
  }

  /**
   * Method under test:
   * {@link HistoricIdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState4() {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl = new HistoricIdentityLinkEntityImpl();
    historicIdentityLinkEntityImpl.setDeleted(true);
    historicIdentityLinkEntityImpl.setId("42");
    historicIdentityLinkEntityImpl.setInserted(true);
    historicIdentityLinkEntityImpl.setType("Type");
    historicIdentityLinkEntityImpl.setUpdated(true);
    historicIdentityLinkEntityImpl.setUserId(null);
    historicIdentityLinkEntityImpl.setGroupId("foo");
    historicIdentityLinkEntityImpl.setTaskId(null);
    historicIdentityLinkEntityImpl.setProcessInstanceId(null);
    historicIdentityLinkEntityImpl.setDetails(null);

    // Act
    Object actualPersistentState = historicIdentityLinkEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) actualPersistentState).size());
    assertEquals("42", ((Map<String, String>) actualPersistentState).get("id"));
    assertEquals("Type", ((Map<String, String>) actualPersistentState).get("type"));
    assertEquals("foo", ((Map<String, String>) actualPersistentState).get("groupId"));
  }

  /**
   * Method under test:
   * {@link HistoricIdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState5() {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl = new HistoricIdentityLinkEntityImpl();
    historicIdentityLinkEntityImpl.setDeleted(true);
    historicIdentityLinkEntityImpl.setId("42");
    historicIdentityLinkEntityImpl.setInserted(true);
    historicIdentityLinkEntityImpl.setType("Type");
    historicIdentityLinkEntityImpl.setUpdated(true);
    historicIdentityLinkEntityImpl.setUserId("foo");
    historicIdentityLinkEntityImpl.setGroupId(null);
    historicIdentityLinkEntityImpl.setTaskId(null);
    historicIdentityLinkEntityImpl.setProcessInstanceId(null);
    historicIdentityLinkEntityImpl.setDetails(null);

    // Act
    Object actualPersistentState = historicIdentityLinkEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) actualPersistentState).size());
    assertEquals("42", ((Map<String, String>) actualPersistentState).get("id"));
    assertEquals("Type", ((Map<String, String>) actualPersistentState).get("type"));
    assertEquals("foo", ((Map<String, String>) actualPersistentState).get("userId"));
  }

  /**
   * Method under test: {@link HistoricIdentityLinkEntityImpl#isUser()}
   */
  @Test
  public void testIsUser() {
    // Arrange, Act and Assert
    assertFalse((new HistoricIdentityLinkEntityImpl()).isUser());
  }

  /**
   * Method under test: {@link HistoricIdentityLinkEntityImpl#isUser()}
   */
  @Test
  public void testIsUser2() {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl = new HistoricIdentityLinkEntityImpl();
    historicIdentityLinkEntityImpl.setUserId("42");

    // Act and Assert
    assertTrue(historicIdentityLinkEntityImpl.isUser());
  }

  /**
   * Method under test: {@link HistoricIdentityLinkEntityImpl#isGroup()}
   */
  @Test
  public void testIsGroup() {
    // Arrange, Act and Assert
    assertFalse((new HistoricIdentityLinkEntityImpl()).isGroup());
  }

  /**
   * Method under test: {@link HistoricIdentityLinkEntityImpl#isGroup()}
   */
  @Test
  public void testIsGroup2() {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl = new HistoricIdentityLinkEntityImpl();
    historicIdentityLinkEntityImpl.setGroupId("42");

    // Act and Assert
    assertTrue(historicIdentityLinkEntityImpl.isGroup());
  }

  /**
   * Method under test: {@link HistoricIdentityLinkEntityImpl#setUserId(String)}
   */
  @Test
  public void testSetUserId() {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl2 = new HistoricIdentityLinkEntityImpl();

    // Act
    historicIdentityLinkEntityImpl2.setUserId("42");

    // Assert
    Object persistentState = historicIdentityLinkEntityImpl2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertEquals("42", ((Map<String, String>) persistentState).get("userId"));
    assertEquals("42", historicIdentityLinkEntityImpl2.getUserId());
    assertTrue(((Map<String, String>) persistentState).containsKey("id"));
    assertTrue(((Map<String, String>) persistentState).containsKey("type"));
    assertTrue(historicIdentityLinkEntityImpl2.isUser());
  }

  /**
   * Method under test: {@link HistoricIdentityLinkEntityImpl#setUserId(String)}
   */
  @Test
  public void testSetUserId2() {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl2 = new HistoricIdentityLinkEntityImpl();
    historicIdentityLinkEntityImpl2.setGroupId("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> historicIdentityLinkEntityImpl2.setUserId("42"));
  }

  /**
   * Method under test: {@link HistoricIdentityLinkEntityImpl#setUserId(String)}
   */
  @Test
  public void testSetUserId3() {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl2 = new HistoricIdentityLinkEntityImpl();
    historicIdentityLinkEntityImpl2.setGroupId("42");

    // Act
    historicIdentityLinkEntityImpl2.setUserId(null);

    // Assert
    Object persistentState = historicIdentityLinkEntityImpl2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertNull(historicIdentityLinkEntityImpl2.getUserId());
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertFalse(historicIdentityLinkEntityImpl2.isUser());
    assertTrue(((Map<String, String>) persistentState).containsKey("groupId"));
    assertTrue(((Map<String, String>) persistentState).containsKey("id"));
    assertTrue(((Map<String, String>) persistentState).containsKey("type"));
  }

  /**
   * Method under test: {@link HistoricIdentityLinkEntityImpl#setGroupId(String)}
   */
  @Test
  public void testSetGroupId() {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl2 = new HistoricIdentityLinkEntityImpl();

    // Act
    historicIdentityLinkEntityImpl2.setGroupId("42");

    // Assert
    Object persistentState = historicIdentityLinkEntityImpl2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertEquals("42", ((Map<String, String>) persistentState).get("groupId"));
    assertEquals("42", historicIdentityLinkEntityImpl2.getGroupId());
    assertTrue(((Map<String, String>) persistentState).containsKey("id"));
    assertTrue(((Map<String, String>) persistentState).containsKey("type"));
    assertTrue(historicIdentityLinkEntityImpl2.isGroup());
  }

  /**
   * Method under test: {@link HistoricIdentityLinkEntityImpl#setGroupId(String)}
   */
  @Test
  public void testSetGroupId2() {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl2 = new HistoricIdentityLinkEntityImpl();
    historicIdentityLinkEntityImpl2.setUserId("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> historicIdentityLinkEntityImpl2.setGroupId("42"));
  }

  /**
   * Method under test: {@link HistoricIdentityLinkEntityImpl#setGroupId(String)}
   */
  @Test
  public void testSetGroupId3() {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl2 = new HistoricIdentityLinkEntityImpl();
    historicIdentityLinkEntityImpl2.setUserId("42");

    // Act
    historicIdentityLinkEntityImpl2.setGroupId(null);

    // Assert
    Object persistentState = historicIdentityLinkEntityImpl2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertNull(historicIdentityLinkEntityImpl2.getGroupId());
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertFalse(historicIdentityLinkEntityImpl2.isGroup());
    assertTrue(((Map<String, String>) persistentState).containsKey("id"));
    assertTrue(((Map<String, String>) persistentState).containsKey("type"));
    assertTrue(((Map<String, String>) persistentState).containsKey("userId"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link HistoricIdentityLinkEntityImpl}
   *   <li>{@link HistoricIdentityLinkEntityImpl#setDetails(byte[])}
   *   <li>{@link HistoricIdentityLinkEntityImpl#setProcessInstanceId(String)}
   *   <li>{@link HistoricIdentityLinkEntityImpl#setTaskId(String)}
   *   <li>{@link HistoricIdentityLinkEntityImpl#setType(String)}
   *   <li>{@link HistoricIdentityLinkEntityImpl#getDetails()}
   *   <li>{@link HistoricIdentityLinkEntityImpl#getGroupId()}
   *   <li>{@link HistoricIdentityLinkEntityImpl#getProcessInstanceId()}
   *   <li>{@link HistoricIdentityLinkEntityImpl#getTaskId()}
   *   <li>{@link HistoricIdentityLinkEntityImpl#getType()}
   *   <li>{@link HistoricIdentityLinkEntityImpl#getUserId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() throws UnsupportedEncodingException {
    // Arrange and Act
    HistoricIdentityLinkEntityImpl actualHistoricIdentityLinkEntityImpl = new HistoricIdentityLinkEntityImpl();
    byte[] details = "AXAXAXAX".getBytes("UTF-8");
    actualHistoricIdentityLinkEntityImpl.setDetails(details);
    actualHistoricIdentityLinkEntityImpl.setProcessInstanceId("42");
    actualHistoricIdentityLinkEntityImpl.setTaskId("42");
    actualHistoricIdentityLinkEntityImpl.setType("Type");
    byte[] actualDetails = actualHistoricIdentityLinkEntityImpl.getDetails();
    actualHistoricIdentityLinkEntityImpl.getGroupId();
    String actualProcessInstanceId = actualHistoricIdentityLinkEntityImpl.getProcessInstanceId();
    String actualTaskId = actualHistoricIdentityLinkEntityImpl.getTaskId();
    String actualType = actualHistoricIdentityLinkEntityImpl.getType();
    actualHistoricIdentityLinkEntityImpl.getUserId();

    // Assert that nothing has changed
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals("Type", actualType);
    assertFalse(actualHistoricIdentityLinkEntityImpl.isDeleted());
    assertFalse(actualHistoricIdentityLinkEntityImpl.isInserted());
    assertFalse(actualHistoricIdentityLinkEntityImpl.isUpdated());
    assertSame(details, actualDetails);
  }
}
