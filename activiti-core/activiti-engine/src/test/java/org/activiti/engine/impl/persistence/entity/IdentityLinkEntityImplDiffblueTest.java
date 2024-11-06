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
public class IdentityLinkEntityImplDiffblueTest {
  @InjectMocks
  private IdentityLinkEntityImpl identityLinkEntityImpl;

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
    actualIdentityLinkEntityImpl.getGroupId();
    String actualProcessDefId = actualIdentityLinkEntityImpl.getProcessDefId();
    String actualProcessDefinitionId = actualIdentityLinkEntityImpl.getProcessDefinitionId();
    String actualProcessInstanceId = actualIdentityLinkEntityImpl.getProcessInstanceId();
    String actualTaskId = actualIdentityLinkEntityImpl.getTaskId();
    String actualType = actualIdentityLinkEntityImpl.getType();
    actualIdentityLinkEntityImpl.getUserId();

    // Assert that nothing has changed
    assertEquals("42", actualProcessDefId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals("Type", actualType);
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
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor) UserId is
   * {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#isUser()}
   */
  @Test
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
  public void testIsUser_givenIdentityLinkEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new IdentityLinkEntityImpl()).isUser());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#isGroup()}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor) GroupId is
   * {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#isGroup()}
   */
  @Test
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
  public void testIsGroup_givenIdentityLinkEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new IdentityLinkEntityImpl()).isGroup());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setUserId(String)}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor) GroupId is
   * {@code 42}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setUserId(String)}
   */
  @Test
  public void testSetUserId_givenIdentityLinkEntityImplGroupIdIs42_thenThrowActivitiException() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl2 = new IdentityLinkEntityImpl();
    identityLinkEntityImpl2.setGroupId("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> identityLinkEntityImpl2.setUserId("42"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setUserId(String)}.
   * <ul>
   *   <li>Then {@link IdentityLinkEntityImpl} (default constructor) PersistentState
   * {@code userId} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setUserId(String)}
   */
  @Test
  public void testSetUserId_thenIdentityLinkEntityImplPersistentStateUserIdIs42() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl2 = new IdentityLinkEntityImpl();

    // Act
    identityLinkEntityImpl2.setUserId("42");

    // Assert
    Object persistentState = identityLinkEntityImpl2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertEquals("42", ((Map<String, String>) persistentState).get("userId"));
    assertEquals("42", identityLinkEntityImpl2.getUserId());
    assertTrue(((Map<String, String>) persistentState).containsKey("id"));
    assertTrue(((Map<String, String>) persistentState).containsKey("type"));
    assertTrue(identityLinkEntityImpl2.isUser());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setUserId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link IdentityLinkEntityImpl} (default constructor) UserId is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setUserId(String)}
   */
  @Test
  public void testSetUserId_whenNull_thenIdentityLinkEntityImplUserIdIsNull() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl2 = new IdentityLinkEntityImpl();
    identityLinkEntityImpl2.setGroupId("42");

    // Act
    identityLinkEntityImpl2.setUserId(null);

    // Assert
    Object persistentState = identityLinkEntityImpl2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertNull(identityLinkEntityImpl2.getUserId());
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertFalse(identityLinkEntityImpl2.isUser());
    assertTrue(((Map<String, String>) persistentState).containsKey("groupId"));
    assertTrue(((Map<String, String>) persistentState).containsKey("id"));
    assertTrue(((Map<String, String>) persistentState).containsKey("type"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setGroupId(String)}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor) UserId is
   * {@code 42}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setGroupId(String)}
   */
  @Test
  public void testSetGroupId_givenIdentityLinkEntityImplUserIdIs42_thenThrowActivitiException() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl2 = new IdentityLinkEntityImpl();
    identityLinkEntityImpl2.setUserId("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> identityLinkEntityImpl2.setGroupId("42"));
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setGroupId(String)}.
   * <ul>
   *   <li>Then {@link IdentityLinkEntityImpl} (default constructor) PersistentState
   * {@code groupId} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setGroupId(String)}
   */
  @Test
  public void testSetGroupId_thenIdentityLinkEntityImplPersistentStateGroupIdIs42() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl2 = new IdentityLinkEntityImpl();

    // Act
    identityLinkEntityImpl2.setGroupId("42");

    // Assert
    Object persistentState = identityLinkEntityImpl2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertEquals("42", ((Map<String, String>) persistentState).get("groupId"));
    assertEquals("42", identityLinkEntityImpl2.getGroupId());
    assertTrue(((Map<String, String>) persistentState).containsKey("id"));
    assertTrue(((Map<String, String>) persistentState).containsKey("type"));
    assertTrue(identityLinkEntityImpl2.isGroup());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setGroupId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link IdentityLinkEntityImpl} (default constructor) GroupId is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#setGroupId(String)}
   */
  @Test
  public void testSetGroupId_whenNull_thenIdentityLinkEntityImplGroupIdIsNull() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl2 = new IdentityLinkEntityImpl();
    identityLinkEntityImpl2.setUserId("42");

    // Act
    identityLinkEntityImpl2.setGroupId(null);

    // Assert
    Object persistentState = identityLinkEntityImpl2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertNull(identityLinkEntityImpl2.getGroupId());
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertFalse(identityLinkEntityImpl2.isGroup());
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
  public void testGetProcessInstance_givenIdentityLinkEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new IdentityLinkEntityImpl()).getProcessInstance());
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
  public void testGetProcessDef_givenIdentityLinkEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new IdentityLinkEntityImpl()).getProcessDef());
  }

  /**
   * Test {@link IdentityLinkEntityImpl#setProcessDef(ProcessDefinitionEntity)}.
   * <p>
   * Method under test:
   * {@link IdentityLinkEntityImpl#setProcessDef(ProcessDefinitionEntity)}
   */
  @Test
  public void testSetProcessDef() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    ProcessDefinitionEntityImpl processDef = new ProcessDefinitionEntityImpl();

    // Act
    identityLinkEntityImpl.setProcessDef(processDef);

    // Assert
    ProcessDefinitionEntity processDefinitionEntity = identityLinkEntityImpl.processDef;
    assertTrue(processDefinitionEntity instanceof ProcessDefinitionEntityImpl);
    assertEquals("", processDefinitionEntity.getTenantId());
    assertNull(processDefinitionEntity.getHistoryLevel());
    assertNull(processDefinitionEntity.getAppVersion());
    assertNull(processDefinitionEntity.getId());
    assertNull(processDefinitionEntity.getEngineVersion());
    assertNull(processDefinitionEntity.getCategory());
    assertNull(processDefinitionEntity.getDeploymentId());
    assertNull(processDefinitionEntity.getDescription());
    assertNull(processDefinitionEntity.getDiagramResourceName());
    assertNull(processDefinitionEntity.getKey());
    assertNull(processDefinitionEntity.getName());
    assertNull(processDefinitionEntity.getResourceName());
    assertNull(((ProcessDefinitionEntityImpl) processDefinitionEntity).getVariables());
    assertNull(((ProcessDefinitionEntityImpl) processDefinitionEntity).getIoSpecification());
    assertEquals(0, processDefinitionEntity.getVersion());
    assertEquals(1, processDefinitionEntity.getRevision());
    assertEquals(1, processDefinitionEntity.getSuspensionState());
    assertEquals(2, processDefinitionEntity.getRevisionNext());
    assertFalse(processDefinitionEntity.isDeleted());
    assertFalse(processDefinitionEntity.isInserted());
    assertFalse(processDefinitionEntity.isUpdated());
    assertFalse(processDefinitionEntity.getHasStartFormKey());
    assertFalse(processDefinitionEntity.isGraphicalNotationDefined());
    assertFalse(processDefinitionEntity.hasGraphicalNotation());
    assertFalse(processDefinitionEntity.hasStartFormKey());
    assertFalse(processDefinitionEntity.isSuspended());
    assertFalse(((ProcessDefinitionEntityImpl) processDefinitionEntity).isIdentityLinksInitialized);
    assertSame(processDef, identityLinkEntityImpl.getProcessDef());
    assertSame(processDef.definitionIdentityLinkEntities,
        ((ProcessDefinitionEntityImpl) processDefinitionEntity).definitionIdentityLinkEntities);
  }

  /**
   * Test {@link IdentityLinkEntityImpl#toString()}.
   * <ul>
   *   <li>Then return
   * {@code IdentityLinkEntity[id=42, type=Type, details=AXAXAXAX]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
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
   *   <li>Then return
   * {@code IdentityLinkEntity[id=42, type=Type, groupId=foo]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
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
   *   <li>Then return
   * {@code IdentityLinkEntity[id=42, type=Type, processDefId=foo]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
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
   *   <li>Then return
   * {@code IdentityLinkEntity[id=42, type=Type, processInstanceId=foo]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
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
   *   <li>Then return
   * {@code IdentityLinkEntity[id=42, type=Type, taskId=foo]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
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
   *   <li>Then return
   * {@code IdentityLinkEntity[id=42, type=Type, userId=foo]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
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
  public void testToString_thenReturnIdentityLinkEntityIdNullTypeNull() {
    // Arrange, Act and Assert
    assertEquals("IdentityLinkEntity[id=null, type=null]", (new IdentityLinkEntityImpl()).toString());
  }
}
