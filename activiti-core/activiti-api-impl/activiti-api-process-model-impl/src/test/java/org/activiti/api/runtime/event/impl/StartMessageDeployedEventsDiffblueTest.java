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
package org.activiti.api.runtime.event.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.StartMessageDeploymentDefinition;
import org.activiti.api.process.model.events.MessageDefinitionEvent;
import org.activiti.api.process.model.events.MessageDefinitionEvent.MessageDefinitionEvents;
import org.activiti.api.process.model.events.StartMessageDeployedEvent;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
import org.activiti.api.runtime.model.impl.StartMessageDeploymentDefinitionImpl;
import org.activiti.api.runtime.model.impl.StartMessageDeploymentDefinitionImpl.Builder;
import org.activiti.api.runtime.model.impl.StartMessageSubscriptionImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StartMessageDeployedEventsDiffblueTest {
  /**
   * Test {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}.
   *
   * <ul>
   *   <li>Then return Source size is one.
   * </ul>
   *
   * <p>Method under test: {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEvents(List); then return Source size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartMessageDeployedEvents.<init>(List)"})
  void testNewStartMessageDeployedEvents_thenReturnSourceSizeIsOne() {
    // Arrange
    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();

    StartMessageDeployedEventImpl.Builder builderResult = StartMessageDeployedEventImpl.builder();

    Builder builderResult2 = StartMessageDeploymentDefinitionImpl.builder();

    StartMessageSubscriptionImpl.Builder withConfigurationResult =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");

    Builder withMessageSubscriptionResult =
        builderResult2.withMessageSubscription(
            withConfigurationResult
                .withCreated(
                    Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .withEventName("Event Name")
                .withId("42")
                .withProcessDefinitionId("42")
                .build());
    ProcessDefinitionImpl processDefinition = new ProcessDefinitionImpl();
    processDeployedEvents.add(
        builderResult
            .withEntity(
                withMessageSubscriptionResult.withProcessDefinition(processDefinition).build())
            .build());

    // Act and Assert
    Object source = new StartMessageDeployedEvents(processDeployedEvents).getSource();
    assertTrue(source instanceof List);
    assertEquals(1, ((List<StartMessageDeployedEventImpl>) source).size());
    StartMessageDeployedEventImpl getResult = ((List<StartMessageDeployedEventImpl>) source).get(0);
    StartMessageDeploymentDefinition entity = getResult.getEntity();
    ProcessDefinition processDefinition2 = entity.getProcessDefinition();
    assertTrue(processDefinition2 instanceof ProcessDefinitionImpl);
    assertTrue(entity instanceof StartMessageDeploymentDefinitionImpl);
    assertTrue(entity.getMessageSubscription() instanceof StartMessageSubscriptionImpl);
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(0, getResult.getProcessDefinitionVersion().intValue());
    assertEquals(MessageDefinitionEvents.START_MESSAGE_DEPLOYED, getResult.getEventType());
    assertSame(processDefinition, processDefinition2);
  }

  /**
   * Test {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}.
   *
   * <ul>
   *   <li>Then return Source size is two.
   * </ul>
   *
   * <p>Method under test: {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEvents(List); then return Source size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartMessageDeployedEvents.<init>(List)"})
  void testNewStartMessageDeployedEvents_thenReturnSourceSizeIsTwo() {
    // Arrange
    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();

    StartMessageDeployedEventImpl.Builder builderResult = StartMessageDeployedEventImpl.builder();

    Builder builderResult2 = StartMessageDeploymentDefinitionImpl.builder();

    StartMessageSubscriptionImpl.Builder withConfigurationResult =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");

    Builder withMessageSubscriptionResult =
        builderResult2.withMessageSubscription(
            withConfigurationResult
                .withCreated(
                    Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .withEventName("Event Name")
                .withId("42")
                .withProcessDefinitionId("42")
                .build());
    processDeployedEvents.add(
        builderResult
            .withEntity(
                withMessageSubscriptionResult
                    .withProcessDefinition(new ProcessDefinitionImpl())
                    .build())
            .build());

    StartMessageDeployedEventImpl.Builder builderResult3 = StartMessageDeployedEventImpl.builder();

    Builder builderResult4 = StartMessageDeploymentDefinitionImpl.builder();

    StartMessageSubscriptionImpl.Builder withConfigurationResult2 =
        StartMessageSubscriptionImpl.builder()
            .withActivityId("42")
            .withConfiguration("Configuration");

    Builder withMessageSubscriptionResult2 =
        builderResult4.withMessageSubscription(
            withConfigurationResult2
                .withCreated(
                    Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .withEventName("Event Name")
                .withId("42")
                .withProcessDefinitionId("42")
                .build());
    processDeployedEvents.add(
        builderResult3
            .withEntity(
                withMessageSubscriptionResult2
                    .withProcessDefinition(new ProcessDefinitionImpl())
                    .build())
            .build());

    // Act and Assert
    Object source = new StartMessageDeployedEvents(processDeployedEvents).getSource();
    assertTrue(source instanceof List);
    assertEquals(2, ((List<StartMessageDeployedEventImpl>) source).size());
    StartMessageDeployedEventImpl getResult = ((List<StartMessageDeployedEventImpl>) source).get(1);
    assertTrue(getResult.getEntity() instanceof StartMessageDeploymentDefinitionImpl);
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(0, getResult.getProcessDefinitionVersion().intValue());
    assertEquals(MessageDefinitionEvents.START_MESSAGE_DEPLOYED, getResult.getEventType());
  }

  /**
   * Test {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Source Empty.
   * </ul>
   *
   * <p>Method under test: {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  @DisplayName(
      "Test new StartMessageDeployedEvents(List); when ArrayList(); then return Source Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartMessageDeployedEvents.<init>(List)"})
  void testNewStartMessageDeployedEvents_whenArrayList_thenReturnSourceEmpty() {
    // Arrange
    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();

    // Act
    StartMessageDeployedEvents actualStartMessageDeployedEvents =
        new StartMessageDeployedEvents(processDeployedEvents);

    // Assert
    Object source = actualStartMessageDeployedEvents.getSource();
    assertTrue(source instanceof List);
    assertTrue(((List<Object>) source).isEmpty());
    assertSame(processDeployedEvents, source);
    assertSame(
        processDeployedEvents, actualStartMessageDeployedEvents.getStartMessageDeployedEvents());
  }

  /**
   * Test {@link StartMessageDeployedEvents#getStartMessageDeployedEvents()}.
   *
   * <p>Method under test: {@link StartMessageDeployedEvents#getStartMessageDeployedEvents()}
   */
  @Test
  @DisplayName("Test getStartMessageDeployedEvents()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List StartMessageDeployedEvents.getStartMessageDeployedEvents()"})
  void testGetStartMessageDeployedEvents() {
    // Arrange, Act and Assert
    assertTrue(
        new StartMessageDeployedEvents(new ArrayList<>())
            .getStartMessageDeployedEvents()
            .isEmpty());
  }
}
