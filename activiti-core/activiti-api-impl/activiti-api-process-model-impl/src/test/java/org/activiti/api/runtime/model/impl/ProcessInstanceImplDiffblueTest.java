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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.process.model.ProcessInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessInstanceImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ProcessInstanceImpl}
   *   <li>{@link ProcessInstanceImpl#setBusinessKey(String)}
   *   <li>{@link ProcessInstanceImpl#setCompletedDate(Date)}
   *   <li>{@link ProcessInstanceImpl#setId(String)}
   *   <li>{@link ProcessInstanceImpl#setInitiator(String)}
   *   <li>{@link ProcessInstanceImpl#setName(String)}
   *   <li>{@link ProcessInstanceImpl#setParentId(String)}
   *   <li>{@link ProcessInstanceImpl#setProcessDefinitionId(String)}
   *   <li>{@link ProcessInstanceImpl#setProcessDefinitionKey(String)}
   *   <li>{@link ProcessInstanceImpl#setProcessDefinitionName(String)}
   *   <li>{@link ProcessInstanceImpl#setProcessDefinitionVersion(Integer)}
   *   <li>{@link ProcessInstanceImpl#setStartDate(Date)}
   *   <li>
   * {@link ProcessInstanceImpl#setStatus(ProcessInstance.ProcessInstanceStatus)}
   *   <li>{@link ProcessInstanceImpl#toString()}
   *   <li>{@link ProcessInstanceImpl#getBusinessKey()}
   *   <li>{@link ProcessInstanceImpl#getCompletedDate()}
   *   <li>{@link ProcessInstanceImpl#getId()}
   *   <li>{@link ProcessInstanceImpl#getInitiator()}
   *   <li>{@link ProcessInstanceImpl#getName()}
   *   <li>{@link ProcessInstanceImpl#getParentId()}
   *   <li>{@link ProcessInstanceImpl#getProcessDefinitionId()}
   *   <li>{@link ProcessInstanceImpl#getProcessDefinitionKey()}
   *   <li>{@link ProcessInstanceImpl#getProcessDefinitionName()}
   *   <li>{@link ProcessInstanceImpl#getProcessDefinitionVersion()}
   *   <li>{@link ProcessInstanceImpl#getStartDate()}
   *   <li>{@link ProcessInstanceImpl#getStatus()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ProcessInstanceImpl actualProcessInstanceImpl = new ProcessInstanceImpl();
    actualProcessInstanceImpl.setBusinessKey("Business Key");
    Date completedDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualProcessInstanceImpl.setCompletedDate(completedDate);
    actualProcessInstanceImpl.setId("42");
    actualProcessInstanceImpl.setInitiator("Initiator");
    actualProcessInstanceImpl.setName("Name");
    actualProcessInstanceImpl.setParentId("42");
    actualProcessInstanceImpl.setProcessDefinitionId("42");
    actualProcessInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    actualProcessInstanceImpl.setProcessDefinitionName("Process Definition Name");
    actualProcessInstanceImpl.setProcessDefinitionVersion(1);
    Date startDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualProcessInstanceImpl.setStartDate(startDate);
    actualProcessInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);
    actualProcessInstanceImpl.toString();
    String actualBusinessKey = actualProcessInstanceImpl.getBusinessKey();
    Date actualCompletedDate = actualProcessInstanceImpl.getCompletedDate();
    String actualId = actualProcessInstanceImpl.getId();
    String actualInitiator = actualProcessInstanceImpl.getInitiator();
    String actualName = actualProcessInstanceImpl.getName();
    String actualParentId = actualProcessInstanceImpl.getParentId();
    String actualProcessDefinitionId = actualProcessInstanceImpl.getProcessDefinitionId();
    String actualProcessDefinitionKey = actualProcessInstanceImpl.getProcessDefinitionKey();
    String actualProcessDefinitionName = actualProcessInstanceImpl.getProcessDefinitionName();
    Integer actualProcessDefinitionVersion = actualProcessInstanceImpl.getProcessDefinitionVersion();
    Date actualStartDate = actualProcessInstanceImpl.getStartDate();
    ProcessInstance.ProcessInstanceStatus actualStatus = actualProcessInstanceImpl.getStatus();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("42", actualParentId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("Initiator", actualInitiator);
    assertEquals("Name", actualName);
    assertEquals("Process Definition Key", actualProcessDefinitionKey);
    assertEquals("Process Definition Name", actualProcessDefinitionName);
    assertEquals(1, actualProcessDefinitionVersion.intValue());
    assertEquals(ProcessInstance.ProcessInstanceStatus.CREATED, actualStatus);
    assertSame(completedDate, actualCompletedDate);
    assertSame(startDate, actualStartDate);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}, and
   * {@link ProcessInstanceImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessInstanceImpl#equals(Object)}
   *   <li>{@link ProcessInstanceImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertEquals(processInstanceImpl, processInstanceImpl2);
    int expectedHashCodeResult = processInstanceImpl.hashCode();
    assertEquals(expectedHashCodeResult, processInstanceImpl2.hashCode());
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}, and
   * {@link ProcessInstanceImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessInstanceImpl#equals(Object)}
   *   <li>{@link ProcessInstanceImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertEquals(processInstanceImpl, processInstanceImpl);
    int expectedHashCodeResult = processInstanceImpl.hashCode();
    assertEquals(expectedHashCodeResult, processInstanceImpl.hashCode());
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("App Version");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey(null);
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl.setCompletedDate(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("Id");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator(null);
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName(null);
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("Parent Id");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("Process Definition Id");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey(null);
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName(null);
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(0);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl.setStartDate(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(null);

    ProcessInstanceImpl processInstanceImpl2 = new ProcessInstanceImpl();
    processInstanceImpl2.setAppVersion("1.0.2");
    processInstanceImpl2.setBusinessKey("Business Key");
    processInstanceImpl2
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setId("42");
    processInstanceImpl2.setInitiator("Initiator");
    processInstanceImpl2.setName("Name");
    processInstanceImpl2.setParentId("42");
    processInstanceImpl2.setProcessDefinitionId("42");
    processInstanceImpl2.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl2.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl2.setProcessDefinitionVersion(1);
    processInstanceImpl2
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl2.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, processInstanceImpl2);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, null);
  }

  /**
   * Test {@link ProcessInstanceImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    processInstanceImpl.setAppVersion("1.0.2");
    processInstanceImpl.setBusinessKey("Business Key");
    processInstanceImpl
        .setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setId("42");
    processInstanceImpl.setInitiator("Initiator");
    processInstanceImpl.setName("Name");
    processInstanceImpl.setParentId("42");
    processInstanceImpl.setProcessDefinitionId("42");
    processInstanceImpl.setProcessDefinitionKey("Process Definition Key");
    processInstanceImpl.setProcessDefinitionName("Process Definition Name");
    processInstanceImpl.setProcessDefinitionVersion(1);
    processInstanceImpl
        .setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    processInstanceImpl.setStatus(ProcessInstance.ProcessInstanceStatus.CREATED);

    // Act and Assert
    assertNotEquals(processInstanceImpl, "Different type to ProcessInstanceImpl");
  }
}
