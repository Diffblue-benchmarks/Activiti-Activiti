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
   * Method under test: {@link IdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState() {
    // Arrange and Act
    Object actualPersistentState = (new IdentityLinkEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(2, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("id"));
    assertNull(((Map<String, Object>) actualPersistentState).get("type"));
  }

  /**
   * Method under test: {@link IdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState2() {
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
   * Method under test: {@link IdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState3() {
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
   * Method under test: {@link IdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState4() {
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
   * Method under test: {@link IdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState5() {
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
   * Method under test: {@link IdentityLinkEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState6() {
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
   * Method under test: {@link IdentityLinkEntityImpl#isUser()}
   */
  @Test
  public void testIsUser() {
    // Arrange, Act and Assert
    assertFalse((new IdentityLinkEntityImpl()).isUser());
  }

  /**
   * Method under test: {@link IdentityLinkEntityImpl#isUser()}
   */
  @Test
  public void testIsUser2() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setUserId("42");

    // Act and Assert
    assertTrue(identityLinkEntityImpl.isUser());
  }

  /**
   * Method under test: {@link IdentityLinkEntityImpl#isGroup()}
   */
  @Test
  public void testIsGroup() {
    // Arrange, Act and Assert
    assertFalse((new IdentityLinkEntityImpl()).isGroup());
  }

  /**
   * Method under test: {@link IdentityLinkEntityImpl#isGroup()}
   */
  @Test
  public void testIsGroup2() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setGroupId("42");

    // Act and Assert
    assertTrue(identityLinkEntityImpl.isGroup());
  }

  /**
   * Method under test: {@link IdentityLinkEntityImpl#setUserId(String)}
   */
  @Test
  public void testSetUserId() {
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
   * Method under test: {@link IdentityLinkEntityImpl#setUserId(String)}
   */
  @Test
  public void testSetUserId2() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl2 = new IdentityLinkEntityImpl();
    identityLinkEntityImpl2.setGroupId("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> identityLinkEntityImpl2.setUserId("42"));
  }

  /**
   * Method under test: {@link IdentityLinkEntityImpl#setUserId(String)}
   */
  @Test
  public void testSetUserId3() {
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
   * Method under test: {@link IdentityLinkEntityImpl#setGroupId(String)}
   */
  @Test
  public void testSetGroupId() {
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
   * Method under test: {@link IdentityLinkEntityImpl#setGroupId(String)}
   */
  @Test
  public void testSetGroupId2() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl2 = new IdentityLinkEntityImpl();
    identityLinkEntityImpl2.setUserId("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> identityLinkEntityImpl2.setGroupId("42"));
  }

  /**
   * Method under test: {@link IdentityLinkEntityImpl#setGroupId(String)}
   */
  @Test
  public void testSetGroupId3() {
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
   * Method under test: {@link IdentityLinkEntityImpl#getTask()}
   */
  @Test
  public void testGetTask() {
    // Arrange, Act and Assert
    assertNull((new IdentityLinkEntityImpl()).getTask());
  }

  /**
   * Method under test: {@link IdentityLinkEntityImpl#getProcessInstance()}
   */
  @Test
  public void testGetProcessInstance() {
    // Arrange, Act and Assert
    assertNull((new IdentityLinkEntityImpl()).getProcessInstance());
  }

  /**
   * Method under test: {@link IdentityLinkEntityImpl#getProcessDef()}
   */
  @Test
  public void testGetProcessDef() {
    // Arrange, Act and Assert
    assertNull((new IdentityLinkEntityImpl()).getProcessDef());
  }

  /**
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
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("IdentityLinkEntity[id=null, type=null]", (new IdentityLinkEntityImpl()).toString());
  }

  /**
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  public void testToString2() throws UnsupportedEncodingException {
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
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  public void testToString3() {
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
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  public void testToString4() {
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
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  public void testToString5() {
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
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  public void testToString6() {
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
   * Method under test: {@link IdentityLinkEntityImpl#toString()}
   */
  @Test
  public void testToString7() {
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
}
