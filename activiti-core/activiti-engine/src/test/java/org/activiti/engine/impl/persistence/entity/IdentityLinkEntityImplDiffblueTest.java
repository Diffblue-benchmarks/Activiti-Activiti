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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class IdentityLinkEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link IdentityLinkEntityImpl}
   *   <li>{@link IdentityLinkEntityImpl#setDetails(byte[])}
   *   <li>{@link IdentityLinkEntityImpl#setProcessDefId(String)}
   *   <li>{@link IdentityLinkEntityImpl#setProcessInstanceId(String)}
   *   <li>{@link IdentityLinkEntityImpl#setTaskId(String)}
   *   <li>{@link IdentityLinkEntityImpl#setType(String)}
   *   <li>{@link IdentityLinkEntityImpl#getDetails()}
   *   <li>{@link IdentityLinkEntityImpl#getGroupId()}
   *   <li>{@link IdentityLinkEntityImpl#getProcessDefId()}
   *   <li>{@link IdentityLinkEntityImpl#getProcessDefinitionId()}
   *   <li>{@link IdentityLinkEntityImpl#getProcessInstanceId()}
   *   <li>{@link IdentityLinkEntityImpl#getTaskId()}
   *   <li>{@link IdentityLinkEntityImpl#getType()}
   *   <li>{@link IdentityLinkEntityImpl#getUserId()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void IdentityLinkEntityImpl.<init>()", "byte[] IdentityLinkEntityImpl.getDetails()",
      "String IdentityLinkEntityImpl.getGroupId()", "String IdentityLinkEntityImpl.getProcessDefId()",
      "String IdentityLinkEntityImpl.getProcessDefinitionId()", "String IdentityLinkEntityImpl.getProcessInstanceId()",
      "String IdentityLinkEntityImpl.getTaskId()", "String IdentityLinkEntityImpl.getType()",
      "String IdentityLinkEntityImpl.getUserId()", "void IdentityLinkEntityImpl.setDetails(byte[])",
      "void IdentityLinkEntityImpl.setProcessDefId(String)", "void IdentityLinkEntityImpl.setProcessInstanceId(String)",
      "void IdentityLinkEntityImpl.setTaskId(String)", "void IdentityLinkEntityImpl.setType(String)"})
  public void testGettersAndSetters() throws UnsupportedEncodingException {
    // Arrange and Act
    IdentityLinkEntityImpl actualIdentityLinkEntityImpl = new IdentityLinkEntityImpl();
    byte[] details = "AXAXAXAX".getBytes("UTF-8");
    actualIdentityLinkEntityImpl.setDetails(details);
    actualIdentityLinkEntityImpl.setProcessDefId("42");
    actualIdentityLinkEntityImpl.setProcessInstanceId("42");
    actualIdentityLinkEntityImpl.setTaskId("42");
    actualIdentityLinkEntityImpl.setType("Type");
    byte[] actualDetails = actualIdentityLinkEntityImpl.getDetails();
    String actualGroupId = actualIdentityLinkEntityImpl.getGroupId();
    String actualProcessDefId = actualIdentityLinkEntityImpl.getProcessDefId();
    String actualProcessDefinitionId = actualIdentityLinkEntityImpl.getProcessDefinitionId();
    String actualProcessInstanceId = actualIdentityLinkEntityImpl.getProcessInstanceId();
    String actualTaskId = actualIdentityLinkEntityImpl.getTaskId();
    String actualType = actualIdentityLinkEntityImpl.getType();
    String actualUserId = actualIdentityLinkEntityImpl.getUserId();

    // Assert
    assertEquals("42", actualProcessDefId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals("Type", actualType);
    assertNull(actualIdentityLinkEntityImpl.getId());
    assertNull(actualGroupId);
    assertNull(actualUserId);
    assertFalse(actualIdentityLinkEntityImpl.isDeleted());
    assertFalse(actualIdentityLinkEntityImpl.isInserted());
    assertFalse(actualIdentityLinkEntityImpl.isUpdated());
    assertSame(details, actualDetails);
  }

  /**
   * Test {@link IdentityLinkEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor).</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object IdentityLinkEntityImpl.getPersistentState()"})
  public void testGetPersistentState_givenIdentityLinkEntityImpl_thenReturnSizeIsTwo() {
    // Arrange and Act
    Object actualPersistentState = (new IdentityLinkEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(2, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("id"));
    assertNull(((Map<String, Object>) actualPersistentState).get("type"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return {@code groupId} is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object IdentityLinkEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnGroupIdIsFoo() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setGroupId("foo");
    identityLinkEntityImpl.setTaskId(null);
    identityLinkEntityImpl.setProcessInstanceId(null);
    identityLinkEntityImpl.setProcessDefId(null);
    identityLinkEntityImpl.setDetails(null);

    // Act
    Object actualPersistentState = identityLinkEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) actualPersistentState).size());
    assertEquals("42", ((Map<String, String>) actualPersistentState).get("id"));
    assertEquals("Type", ((Map<String, String>) actualPersistentState).get("type"));
    assertEquals("foo", ((Map<String, String>) actualPersistentState).get("groupId"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return {@code processDefId} is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object IdentityLinkEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnProcessDefIdIsFoo() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setGroupId(null);
    identityLinkEntityImpl.setTaskId(null);
    identityLinkEntityImpl.setProcessInstanceId(null);
    identityLinkEntityImpl.setProcessDefId("foo");
    identityLinkEntityImpl.setDetails(null);

    // Act
    Object actualPersistentState = identityLinkEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) actualPersistentState).size());
    assertEquals("42", ((Map<String, String>) actualPersistentState).get("id"));
    assertEquals("Type", ((Map<String, String>) actualPersistentState).get("type"));
    assertEquals("foo", ((Map<String, String>) actualPersistentState).get("processDefId"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return {@code processInstanceId} is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object IdentityLinkEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnProcessInstanceIdIsFoo() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setGroupId(null);
    identityLinkEntityImpl.setTaskId(null);
    identityLinkEntityImpl.setProcessInstanceId("foo");
    identityLinkEntityImpl.setProcessDefId(null);
    identityLinkEntityImpl.setDetails(null);

    // Act
    Object actualPersistentState = identityLinkEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) actualPersistentState).size());
    assertEquals("42", ((Map<String, String>) actualPersistentState).get("id"));
    assertEquals("Type", ((Map<String, String>) actualPersistentState).get("type"));
    assertEquals("foo", ((Map<String, String>) actualPersistentState).get("processInstanceId"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return {@code taskId} is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object IdentityLinkEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnTaskIdIsFoo() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setGroupId(null);
    identityLinkEntityImpl.setTaskId("foo");
    identityLinkEntityImpl.setProcessInstanceId(null);
    identityLinkEntityImpl.setProcessDefId(null);
    identityLinkEntityImpl.setDetails(null);

    // Act
    Object actualPersistentState = identityLinkEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) actualPersistentState).size());
    assertEquals("42", ((Map<String, String>) actualPersistentState).get("id"));
    assertEquals("Type", ((Map<String, String>) actualPersistentState).get("type"));
    assertEquals("foo", ((Map<String, String>) actualPersistentState).get("taskId"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return {@code userId} is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object IdentityLinkEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnUserIdIsFoo() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId("foo");
    identityLinkEntityImpl.setGroupId(null);
    identityLinkEntityImpl.setTaskId(null);
    identityLinkEntityImpl.setProcessInstanceId(null);
    identityLinkEntityImpl.setProcessDefId(null);
    identityLinkEntityImpl.setDetails(null);

    // Act
    Object actualPersistentState = identityLinkEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) actualPersistentState).size());
    assertEquals("42", ((Map<String, String>) actualPersistentState).get("id"));
    assertEquals("Type", ((Map<String, String>) actualPersistentState).get("type"));
    assertEquals("foo", ((Map<String, String>) actualPersistentState).get("userId"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#isUser()}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor) UserId is {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#isUser()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean IdentityLinkEntityImpl.isUser()"})
  public void testIsUser_givenIdentityLinkEntityImplUserIdIs42_thenReturnTrue() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setUserId("42");

    // Act and Assert
    assertTrue(identityLinkEntityImpl.isUser());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#isUser()}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#isUser()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean IdentityLinkEntityImpl.isUser()"})
  public void testIsUser_givenIdentityLinkEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new IdentityLinkEntityImpl()).isUser());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#isGroup()}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor) GroupId is {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#isGroup()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean IdentityLinkEntityImpl.isGroup()"})
  public void testIsGroup_givenIdentityLinkEntityImplGroupIdIs42_thenReturnTrue() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setGroupId("42");

    // Act and Assert
    assertTrue(identityLinkEntityImpl.isGroup());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#isGroup()}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#isGroup()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean IdentityLinkEntityImpl.isGroup()"})
  public void testIsGroup_givenIdentityLinkEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new IdentityLinkEntityImpl()).isGroup());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setUserId(String)}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor) GroupId is {@code 42}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setUserId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void IdentityLinkEntityImpl.setUserId(String)"})
  public void testSetUserId_givenIdentityLinkEntityImplGroupIdIs42_thenThrowActivitiException() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setGroupId("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> identityLinkEntityImpl.setUserId("42"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setUserId(String)}.
   * <ul>
   *   <li>Then {@link IdentityLinkEntityImpl} (default constructor) PersistentState {@code userId} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setUserId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void IdentityLinkEntityImpl.setUserId(String)"})
  public void testSetUserId_thenIdentityLinkEntityImplPersistentStateUserIdIs42() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();

    // Act
    identityLinkEntityImpl.setUserId("42");

    // Assert
    Object persistentState = identityLinkEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertEquals("42", ((Map<String, String>) persistentState).get("userId"));
    assertEquals("42", identityLinkEntityImpl.getUserId());
    assertTrue(((Map<String, String>) persistentState).containsKey("id"));
    assertTrue(((Map<String, String>) persistentState).containsKey("type"));
    assertTrue(identityLinkEntityImpl.isUser());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setUserId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then not {@link IdentityLinkEntityImpl} (default constructor) User.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setUserId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void IdentityLinkEntityImpl.setUserId(String)"})
  public void testSetUserId_whenNull_thenNotIdentityLinkEntityImplUser() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setGroupId("42");

    // Act
    identityLinkEntityImpl.setUserId(null);

    // Assert that nothing has changed
    Object persistentState = identityLinkEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertFalse(identityLinkEntityImpl.isUser());
    assertTrue(((Map<String, String>) persistentState).containsKey("groupId"));
    assertTrue(((Map<String, String>) persistentState).containsKey("id"));
    assertTrue(((Map<String, String>) persistentState).containsKey("type"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setGroupId(String)}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor) UserId is {@code 42}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setGroupId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void IdentityLinkEntityImpl.setGroupId(String)"})
  public void testSetGroupId_givenIdentityLinkEntityImplUserIdIs42_thenThrowActivitiException() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setUserId("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> identityLinkEntityImpl.setGroupId("42"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setGroupId(String)}.
   * <ul>
   *   <li>Then {@link IdentityLinkEntityImpl} (default constructor) PersistentState {@code groupId} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setGroupId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void IdentityLinkEntityImpl.setGroupId(String)"})
  public void testSetGroupId_thenIdentityLinkEntityImplPersistentStateGroupIdIs42() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();

    // Act
    identityLinkEntityImpl.setGroupId("42");

    // Assert
    Object persistentState = identityLinkEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertEquals("42", ((Map<String, String>) persistentState).get("groupId"));
    assertEquals("42", identityLinkEntityImpl.getGroupId());
    assertTrue(((Map<String, String>) persistentState).containsKey("id"));
    assertTrue(((Map<String, String>) persistentState).containsKey("type"));
    assertTrue(identityLinkEntityImpl.isGroup());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setGroupId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then not {@link IdentityLinkEntityImpl} (default constructor) Group.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setGroupId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void IdentityLinkEntityImpl.setGroupId(String)"})
  public void testSetGroupId_whenNull_thenNotIdentityLinkEntityImplGroup() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setUserId("42");

    // Act
    identityLinkEntityImpl.setGroupId(null);

    // Assert that nothing has changed
    Object persistentState = identityLinkEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertFalse(identityLinkEntityImpl.isGroup());
    assertTrue(((Map<String, String>) persistentState).containsKey("id"));
    assertTrue(((Map<String, String>) persistentState).containsKey("type"));
    assertTrue(((Map<String, String>) persistentState).containsKey("userId"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#getTask()}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#getTask()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.impl.persistence.entity.TaskEntity IdentityLinkEntityImpl.getTask()"})
  public void testGetTask_givenIdentityLinkEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new IdentityLinkEntityImpl()).getTask());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#getProcessInstance()}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#getProcessInstance()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionEntity IdentityLinkEntityImpl.getProcessInstance()"})
  public void testGetProcessInstance_givenIdentityLinkEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new IdentityLinkEntityImpl()).getProcessInstance());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setProcessInstance(ExecutionEntity)}.
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setProcessInstance(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void IdentityLinkEntityImpl.setProcessInstance(ExecutionEntity)"})
  public void testSetProcessInstance() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    ExecutionEntityImpl processInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    identityLinkEntityImpl.setProcessInstance(processInstance);

    // Assert
    assertTrue(identityLinkEntityImpl.processInstance instanceof ExecutionEntityImpl);
    assertSame(processInstance, processInstance.getSourceActivityExecution());
    assertSame(processInstance, identityLinkEntityImpl.getProcessInstance());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#getProcessDef()}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#getProcessDef()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionEntity IdentityLinkEntityImpl.getProcessDef()"})
  public void testGetProcessDef_givenIdentityLinkEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new IdentityLinkEntityImpl()).getProcessDef());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setProcessDef(ProcessDefinitionEntity)}.
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setProcessDef(ProcessDefinitionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void IdentityLinkEntityImpl.setProcessDef(ProcessDefinitionEntity)"})
  public void testSetProcessDef() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    ProcessDefinitionEntityImpl processDef = new ProcessDefinitionEntityImpl();

    // Act
    identityLinkEntityImpl.setProcessDef(processDef);

    // Assert
    assertTrue(identityLinkEntityImpl.processDef instanceof ProcessDefinitionEntityImpl);
    assertSame(processDef, identityLinkEntityImpl.getProcessDef());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#toString()}.
   * <ul>
   *   <li>Then return {@code IdentityLinkEntity[id=42, type=Type, details=AXAXAXAX]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String IdentityLinkEntityImpl.toString()"})
  public void testToString_thenReturnIdentityLinkEntityId42TypeTypeDetailsAxaxaxax()
      throws UnsupportedEncodingException {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setGroupId(null);
    identityLinkEntityImpl.setTaskId(null);
    identityLinkEntityImpl.setProcessInstanceId(null);
    identityLinkEntityImpl.setProcessDefId(null);
    identityLinkEntityImpl.setDetails("AXAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertEquals("IdentityLinkEntity[id=42, type=Type, details=AXAXAXAX]", identityLinkEntityImpl.toString());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#toString()}.
   * <ul>
   *   <li>Then return {@code IdentityLinkEntity[id=42, type=Type, groupId=foo]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String IdentityLinkEntityImpl.toString()"})
  public void testToString_thenReturnIdentityLinkEntityId42TypeTypeGroupIdFoo() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setGroupId("foo");
    identityLinkEntityImpl.setTaskId(null);
    identityLinkEntityImpl.setProcessInstanceId(null);
    identityLinkEntityImpl.setProcessDefId(null);
    identityLinkEntityImpl.setDetails(null);

    // Act and Assert
    assertEquals("IdentityLinkEntity[id=42, type=Type, groupId=foo]", identityLinkEntityImpl.toString());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#toString()}.
   * <ul>
   *   <li>Then return {@code IdentityLinkEntity[id=42, type=Type, processDefId=foo]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String IdentityLinkEntityImpl.toString()"})
  public void testToString_thenReturnIdentityLinkEntityId42TypeTypeProcessDefIdFoo() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setGroupId(null);
    identityLinkEntityImpl.setTaskId(null);
    identityLinkEntityImpl.setProcessInstanceId(null);
    identityLinkEntityImpl.setProcessDefId("foo");
    identityLinkEntityImpl.setDetails(null);

    // Act and Assert
    assertEquals("IdentityLinkEntity[id=42, type=Type, processDefId=foo]", identityLinkEntityImpl.toString());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#toString()}.
   * <ul>
   *   <li>Then return {@code IdentityLinkEntity[id=42, type=Type, processInstanceId=foo]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String IdentityLinkEntityImpl.toString()"})
  public void testToString_thenReturnIdentityLinkEntityId42TypeTypeProcessInstanceIdFoo() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setGroupId(null);
    identityLinkEntityImpl.setTaskId(null);
    identityLinkEntityImpl.setProcessInstanceId("foo");
    identityLinkEntityImpl.setProcessDefId(null);
    identityLinkEntityImpl.setDetails(null);

    // Act and Assert
    assertEquals("IdentityLinkEntity[id=42, type=Type, processInstanceId=foo]", identityLinkEntityImpl.toString());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#toString()}.
   * <ul>
   *   <li>Then return {@code IdentityLinkEntity[id=42, type=Type, taskId=foo]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String IdentityLinkEntityImpl.toString()"})
  public void testToString_thenReturnIdentityLinkEntityId42TypeTypeTaskIdFoo() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setGroupId(null);
    identityLinkEntityImpl.setTaskId("foo");
    identityLinkEntityImpl.setProcessInstanceId(null);
    identityLinkEntityImpl.setProcessDefId(null);
    identityLinkEntityImpl.setDetails(null);

    // Act and Assert
    assertEquals("IdentityLinkEntity[id=42, type=Type, taskId=foo]", identityLinkEntityImpl.toString());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#toString()}.
   * <ul>
   *   <li>Then return {@code IdentityLinkEntity[id=42, type=Type, userId=foo]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String IdentityLinkEntityImpl.toString()"})
  public void testToString_thenReturnIdentityLinkEntityId42TypeTypeUserIdFoo() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId("foo");
    identityLinkEntityImpl.setGroupId(null);
    identityLinkEntityImpl.setTaskId(null);
    identityLinkEntityImpl.setProcessInstanceId(null);
    identityLinkEntityImpl.setProcessDefId(null);
    identityLinkEntityImpl.setDetails(null);

    // Act and Assert
    assertEquals("IdentityLinkEntity[id=42, type=Type, userId=foo]", identityLinkEntityImpl.toString());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#toString()}.
   * <ul>
   *   <li>Then return {@code IdentityLinkEntity[id=null, type=null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String IdentityLinkEntityImpl.toString()"})
  public void testToString_thenReturnIdentityLinkEntityIdNullTypeNull() {
    // Arrange, Act and Assert
    assertEquals("IdentityLinkEntity[id=null, type=null]", (new IdentityLinkEntityImpl()).toString());
  }
}
