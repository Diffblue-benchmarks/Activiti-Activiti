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
package org.activiti.runtime.api.event.internal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.api.task.runtime.events.TaskCreatedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.runtime.api.event.impl.TaskCreatedEventImpl;
import org.activiti.runtime.api.event.impl.ToAPITaskCreatedEventConverter;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskCreatedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TaskCreatedListenerDelegate#TaskCreatedListenerDelegate(List,
   *       ToAPITaskCreatedEventConverter)}
   *   <li>{@link TaskCreatedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TaskCreatedListenerDelegate.<init>(List, ToAPITaskCreatedEventConverter)",
    "boolean TaskCreatedListenerDelegate.isFailOnException()"
  })
  void testGettersAndSetters() {
    // Arrange
    ArrayList<TaskRuntimeEventListener<TaskCreatedEvent>> taskCreatedListeners = new ArrayList<>();
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    ToAPITaskCreatedEventConverter taskCreatedEventConverter =
        new ToAPITaskCreatedEventConverter(taskConverter);

    // Act
    TaskCreatedListenerDelegate actualTaskCreatedListenerDelegate =
        new TaskCreatedListenerDelegate(taskCreatedListeners, taskCreatedEventConverter);

    // Assert
    assertFalse(actualTaskCreatedListenerDelegate.isFailOnException());
  }

  /**
   * Test {@link TaskCreatedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link TaskCreatedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(ActivitiEvent); given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskCreatedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenArrayListAddIdentityLinkEntityImpl() {
    // Arrange
    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener2 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener2).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener3 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener3).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener4 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener4).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener5 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener5).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener6 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener6).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener7 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener7).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener8 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener8).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener9 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener9).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener10 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener10).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener11 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener11).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener12 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener12).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener13 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener13).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener14 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener14).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener15 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener15).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener16 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener16).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener17 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener17).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener18 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener18).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener19 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener19).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener20 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener20).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener21 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener21).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener22 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener22).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener23 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener23).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener24 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener24).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener25 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener25).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener26 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener26).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener27 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener27).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener28 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener28).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener29 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener29).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener30 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener30).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener31 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener31).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener32 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener32).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener33 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener33).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener34 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener34).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener35 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener35).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener36 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener36).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener37 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener37).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener38 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener38).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener39 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener39).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener40 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener40).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener41 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener41).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener42 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener42).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener43 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener43).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener44 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener44).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener45 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener45).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener46 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener46).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener47 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener47).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener48 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener48).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener49 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener49).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener50 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener50).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener51 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener51).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener52 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener52).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener53 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener53).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener54 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener54).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener55 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener55).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener56 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener56).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener57 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener57).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener58 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener58).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener59 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener59).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener60 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener60).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener61 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener61).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener62 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener62).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener63 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener63).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener64 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener64).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener65 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener65).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener66 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener66).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener67 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener67).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener68 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener68).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener69 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener69).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener70 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener70).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener71 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener71).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener72 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener72).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener73 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener73).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener74 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener74).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener75 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener75).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener76 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener76).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener77 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener77).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener78 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener78).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener79 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener79).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener80 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener80).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener81 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener81).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener82 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener82).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener83 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener83).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener84 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener84).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener85 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener85).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener86 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener86).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener87 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener87).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener88 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener88).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener89 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener89).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener90 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener90).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener91 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener91).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener92 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener92).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener93 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener93).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener94 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener94).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener95 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener95).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener96 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener96).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener97 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener97).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener98 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener98).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener99 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener99).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener100 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener100).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener101 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener101).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener102 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener102).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener103 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener103).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener104 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener104).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener105 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener105).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener106 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener106).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener107 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener107).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener108 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener108).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener109 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener109).onEvent(Mockito.<TaskCreatedEvent>any());

    ArrayList<TaskRuntimeEventListener<TaskCreatedEvent>> taskCreatedListeners = new ArrayList<>();
    taskCreatedListeners.add(taskRuntimeEventListener109);
    taskCreatedListeners.add(taskRuntimeEventListener108);
    taskCreatedListeners.add(taskRuntimeEventListener107);
    taskCreatedListeners.add(taskRuntimeEventListener106);
    taskCreatedListeners.add(taskRuntimeEventListener105);
    taskCreatedListeners.add(taskRuntimeEventListener104);
    taskCreatedListeners.add(taskRuntimeEventListener103);
    taskCreatedListeners.add(taskRuntimeEventListener102);
    taskCreatedListeners.add(taskRuntimeEventListener101);
    taskCreatedListeners.add(taskRuntimeEventListener100);
    taskCreatedListeners.add(taskRuntimeEventListener99);
    taskCreatedListeners.add(taskRuntimeEventListener98);
    taskCreatedListeners.add(taskRuntimeEventListener97);
    taskCreatedListeners.add(taskRuntimeEventListener96);
    taskCreatedListeners.add(taskRuntimeEventListener95);
    taskCreatedListeners.add(taskRuntimeEventListener94);
    taskCreatedListeners.add(taskRuntimeEventListener93);
    taskCreatedListeners.add(taskRuntimeEventListener92);
    taskCreatedListeners.add(taskRuntimeEventListener91);
    taskCreatedListeners.add(taskRuntimeEventListener90);
    taskCreatedListeners.add(taskRuntimeEventListener89);
    taskCreatedListeners.add(taskRuntimeEventListener88);
    taskCreatedListeners.add(taskRuntimeEventListener87);
    taskCreatedListeners.add(taskRuntimeEventListener86);
    taskCreatedListeners.add(taskRuntimeEventListener85);
    taskCreatedListeners.add(taskRuntimeEventListener84);
    taskCreatedListeners.add(taskRuntimeEventListener83);
    taskCreatedListeners.add(taskRuntimeEventListener82);
    taskCreatedListeners.add(taskRuntimeEventListener81);
    taskCreatedListeners.add(taskRuntimeEventListener80);
    taskCreatedListeners.add(taskRuntimeEventListener79);
    taskCreatedListeners.add(taskRuntimeEventListener78);
    taskCreatedListeners.add(taskRuntimeEventListener77);
    taskCreatedListeners.add(taskRuntimeEventListener76);
    taskCreatedListeners.add(taskRuntimeEventListener75);
    taskCreatedListeners.add(taskRuntimeEventListener74);
    taskCreatedListeners.add(taskRuntimeEventListener73);
    taskCreatedListeners.add(taskRuntimeEventListener72);
    taskCreatedListeners.add(taskRuntimeEventListener71);
    taskCreatedListeners.add(taskRuntimeEventListener70);
    taskCreatedListeners.add(taskRuntimeEventListener69);
    taskCreatedListeners.add(taskRuntimeEventListener68);
    taskCreatedListeners.add(taskRuntimeEventListener67);
    taskCreatedListeners.add(taskRuntimeEventListener66);
    taskCreatedListeners.add(taskRuntimeEventListener65);
    taskCreatedListeners.add(taskRuntimeEventListener64);
    taskCreatedListeners.add(taskRuntimeEventListener63);
    taskCreatedListeners.add(taskRuntimeEventListener62);
    taskCreatedListeners.add(taskRuntimeEventListener61);
    taskCreatedListeners.add(taskRuntimeEventListener60);
    taskCreatedListeners.add(taskRuntimeEventListener59);
    taskCreatedListeners.add(taskRuntimeEventListener58);
    taskCreatedListeners.add(taskRuntimeEventListener57);
    taskCreatedListeners.add(taskRuntimeEventListener56);
    taskCreatedListeners.add(taskRuntimeEventListener55);
    taskCreatedListeners.add(taskRuntimeEventListener54);
    taskCreatedListeners.add(taskRuntimeEventListener53);
    taskCreatedListeners.add(taskRuntimeEventListener52);
    taskCreatedListeners.add(taskRuntimeEventListener51);
    taskCreatedListeners.add(taskRuntimeEventListener50);
    taskCreatedListeners.add(taskRuntimeEventListener49);
    taskCreatedListeners.add(taskRuntimeEventListener48);
    taskCreatedListeners.add(taskRuntimeEventListener47);
    taskCreatedListeners.add(taskRuntimeEventListener46);
    taskCreatedListeners.add(taskRuntimeEventListener45);
    taskCreatedListeners.add(taskRuntimeEventListener44);
    taskCreatedListeners.add(taskRuntimeEventListener43);
    taskCreatedListeners.add(taskRuntimeEventListener42);
    taskCreatedListeners.add(taskRuntimeEventListener41);
    taskCreatedListeners.add(taskRuntimeEventListener40);
    taskCreatedListeners.add(taskRuntimeEventListener39);
    taskCreatedListeners.add(taskRuntimeEventListener38);
    taskCreatedListeners.add(taskRuntimeEventListener37);
    taskCreatedListeners.add(taskRuntimeEventListener36);
    taskCreatedListeners.add(taskRuntimeEventListener35);
    taskCreatedListeners.add(taskRuntimeEventListener34);
    taskCreatedListeners.add(taskRuntimeEventListener33);
    taskCreatedListeners.add(taskRuntimeEventListener32);
    taskCreatedListeners.add(taskRuntimeEventListener31);
    taskCreatedListeners.add(taskRuntimeEventListener30);
    taskCreatedListeners.add(taskRuntimeEventListener29);
    taskCreatedListeners.add(taskRuntimeEventListener28);
    taskCreatedListeners.add(taskRuntimeEventListener27);
    taskCreatedListeners.add(taskRuntimeEventListener26);
    taskCreatedListeners.add(taskRuntimeEventListener25);
    taskCreatedListeners.add(taskRuntimeEventListener24);
    taskCreatedListeners.add(taskRuntimeEventListener23);
    taskCreatedListeners.add(taskRuntimeEventListener22);
    taskCreatedListeners.add(taskRuntimeEventListener21);
    taskCreatedListeners.add(taskRuntimeEventListener20);
    taskCreatedListeners.add(taskRuntimeEventListener19);
    taskCreatedListeners.add(taskRuntimeEventListener18);
    taskCreatedListeners.add(taskRuntimeEventListener17);
    taskCreatedListeners.add(taskRuntimeEventListener16);
    taskCreatedListeners.add(taskRuntimeEventListener15);
    taskCreatedListeners.add(taskRuntimeEventListener14);
    taskCreatedListeners.add(taskRuntimeEventListener13);
    taskCreatedListeners.add(taskRuntimeEventListener12);
    taskCreatedListeners.add(taskRuntimeEventListener11);
    taskCreatedListeners.add(taskRuntimeEventListener10);
    taskCreatedListeners.add(taskRuntimeEventListener9);
    taskCreatedListeners.add(taskRuntimeEventListener8);
    taskCreatedListeners.add(taskRuntimeEventListener7);
    taskCreatedListeners.add(taskRuntimeEventListener6);
    taskCreatedListeners.add(taskRuntimeEventListener5);
    taskCreatedListeners.add(taskRuntimeEventListener4);
    taskCreatedListeners.add(taskRuntimeEventListener3);
    taskCreatedListeners.add(taskRuntimeEventListener2);
    taskCreatedListeners.add(taskRuntimeEventListener);

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter taskConverter = new APITaskConverter(taskService);
    ToAPITaskCreatedEventConverter taskCreatedEventConverter =
        new ToAPITaskCreatedEventConverter(taskConverter);

    TaskCreatedListenerDelegate taskCreatedListenerDelegate =
        new TaskCreatedListenerDelegate(taskCreatedListeners, taskCreatedEventConverter);

    TaskEntityImpl taskEntityImpl = mock(TaskEntityImpl.class);
    when(taskEntityImpl.isDeleted()).thenReturn(true);
    when(taskEntityImpl.getPriority()).thenReturn(1);
    when(taskEntityImpl.getAppVersion()).thenReturn(1);
    when(taskEntityImpl.getId()).thenReturn("42");
    when(taskEntityImpl.getAssignee()).thenReturn("Assignee");
    when(taskEntityImpl.getBusinessKey()).thenReturn("Business Key");
    when(taskEntityImpl.getDescription()).thenReturn("The characteristics of someone or something");
    when(taskEntityImpl.getFormKey()).thenReturn("Form Key");
    when(taskEntityImpl.getName()).thenReturn("Name");
    when(taskEntityImpl.getOwner()).thenReturn("Owner");
    when(taskEntityImpl.getParentTaskId()).thenReturn("42");
    when(taskEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(taskEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(taskEntityImpl.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(taskEntityImpl.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(taskEntityImpl.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(taskEntityImpl.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    taskCreatedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(taskEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(taskRuntimeEventListener109).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener108).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener107).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener106).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener105).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener104).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener103).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener102).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener101).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener100).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener99).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener98).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener97).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener96).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener95).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener94).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener93).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener92).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener91).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener90).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener89).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener88).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener87).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener86).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener85).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener84).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener83).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener82).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener81).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener80).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener79).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener78).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener77).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener76).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener75).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener74).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener73).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener72).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener71).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener70).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener69).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener68).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener67).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener66).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener65).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener64).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener63).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener62).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener61).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener60).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener59).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener58).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener57).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener56).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener55).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener54).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener53).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener52).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener51).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener50).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener49).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener48).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener47).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener46).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener45).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener44).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener43).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener42).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener41).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener40).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener39).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener38).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener37).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener36).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener35).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener34).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener33).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener32).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener31).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener30).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener29).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener28).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener27).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener26).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener25).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener24).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener23).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener22).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener21).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener20).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener19).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener18).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener17).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener16).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener15).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener14).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener13).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener12).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener11).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener10).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener9).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener8).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener7).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener6).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener5).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener4).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener3).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener2).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener).onEvent(isA(TaskCreatedEvent.class));
    verify(taskService).getIdentityLinksForTask("42");
    verify(taskEntityImpl, atLeast(1)).getId();
    verify(taskEntityImpl).getAppVersion();
    verify(taskEntityImpl).getAssignee();
    verify(taskEntityImpl).getBusinessKey();
    verify(taskEntityImpl).getClaimTime();
    verify(taskEntityImpl).getCreateTime();
    verify(taskEntityImpl).getDescription();
    verify(taskEntityImpl).getDueDate();
    verify(taskEntityImpl).getFormKey();
    verify(taskEntityImpl).getName();
    verify(taskEntityImpl).getOwner();
    verify(taskEntityImpl).getParentTaskId();
    verify(taskEntityImpl).getPriority();
    verify(taskEntityImpl).getProcessDefinitionId();
    verify(taskEntityImpl).getProcessInstanceId();
    verify(taskEntityImpl).getTaskDefinitionKey();
    verify(taskEntityImpl).isDeleted();
  }

  /**
   * Test {@link TaskCreatedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link ToAPITaskCreatedEventConverter#from(ActivitiEntityEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskCreatedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls from(ActivitiEntityEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskCreatedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_thenCallsFrom() {
    // Arrange
    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener2 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener2).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener3 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener3).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener4 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener4).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener5 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener5).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener6 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener6).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener7 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener7).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener8 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener8).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener9 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener9).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener10 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener10).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener11 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener11).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener12 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener12).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener13 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener13).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener14 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener14).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener15 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener15).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener16 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener16).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener17 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener17).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener18 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener18).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener19 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener19).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener20 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener20).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener21 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener21).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener22 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener22).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener23 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener23).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener24 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener24).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener25 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener25).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener26 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener26).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener27 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener27).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener28 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener28).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener29 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener29).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener30 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener30).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener31 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener31).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener32 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener32).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener33 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener33).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener34 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener34).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener35 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener35).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener36 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener36).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener37 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener37).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener38 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener38).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener39 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener39).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener40 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener40).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener41 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener41).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener42 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener42).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener43 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener43).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener44 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener44).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener45 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener45).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener46 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener46).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener47 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener47).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener48 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener48).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener49 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener49).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener50 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener50).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener51 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener51).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener52 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener52).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener53 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener53).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener54 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener54).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener55 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener55).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener56 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener56).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener57 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener57).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener58 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener58).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener59 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener59).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener60 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener60).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener61 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener61).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener62 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener62).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener63 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener63).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener64 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener64).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener65 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener65).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener66 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener66).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener67 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener67).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener68 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener68).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener69 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener69).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener70 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener70).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener71 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener71).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener72 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener72).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener73 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener73).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener74 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener74).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener75 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener75).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener76 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener76).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener77 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener77).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener78 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener78).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener79 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener79).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener80 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener80).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener81 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener81).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener82 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener82).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener83 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener83).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener84 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener84).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener85 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener85).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener86 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener86).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener87 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener87).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener88 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener88).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener89 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener89).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener90 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener90).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener91 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener91).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener92 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener92).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener93 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener93).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener94 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener94).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener95 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener95).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener96 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener96).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener97 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener97).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener98 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener98).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener99 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener99).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener100 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener100).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener101 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener101).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener102 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener102).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener103 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener103).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener104 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener104).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener105 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener105).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener106 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener106).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener107 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener107).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener108 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener108).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener109 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener109).onEvent(Mockito.<TaskCreatedEvent>any());

    ArrayList<TaskRuntimeEventListener<TaskCreatedEvent>> taskCreatedListeners = new ArrayList<>();
    taskCreatedListeners.add(taskRuntimeEventListener109);
    taskCreatedListeners.add(taskRuntimeEventListener108);
    taskCreatedListeners.add(taskRuntimeEventListener107);
    taskCreatedListeners.add(taskRuntimeEventListener106);
    taskCreatedListeners.add(taskRuntimeEventListener105);
    taskCreatedListeners.add(taskRuntimeEventListener104);
    taskCreatedListeners.add(taskRuntimeEventListener103);
    taskCreatedListeners.add(taskRuntimeEventListener102);
    taskCreatedListeners.add(taskRuntimeEventListener101);
    taskCreatedListeners.add(taskRuntimeEventListener100);
    taskCreatedListeners.add(taskRuntimeEventListener99);
    taskCreatedListeners.add(taskRuntimeEventListener98);
    taskCreatedListeners.add(taskRuntimeEventListener97);
    taskCreatedListeners.add(taskRuntimeEventListener96);
    taskCreatedListeners.add(taskRuntimeEventListener95);
    taskCreatedListeners.add(taskRuntimeEventListener94);
    taskCreatedListeners.add(taskRuntimeEventListener93);
    taskCreatedListeners.add(taskRuntimeEventListener92);
    taskCreatedListeners.add(taskRuntimeEventListener91);
    taskCreatedListeners.add(taskRuntimeEventListener90);
    taskCreatedListeners.add(taskRuntimeEventListener89);
    taskCreatedListeners.add(taskRuntimeEventListener88);
    taskCreatedListeners.add(taskRuntimeEventListener87);
    taskCreatedListeners.add(taskRuntimeEventListener86);
    taskCreatedListeners.add(taskRuntimeEventListener85);
    taskCreatedListeners.add(taskRuntimeEventListener84);
    taskCreatedListeners.add(taskRuntimeEventListener83);
    taskCreatedListeners.add(taskRuntimeEventListener82);
    taskCreatedListeners.add(taskRuntimeEventListener81);
    taskCreatedListeners.add(taskRuntimeEventListener80);
    taskCreatedListeners.add(taskRuntimeEventListener79);
    taskCreatedListeners.add(taskRuntimeEventListener78);
    taskCreatedListeners.add(taskRuntimeEventListener77);
    taskCreatedListeners.add(taskRuntimeEventListener76);
    taskCreatedListeners.add(taskRuntimeEventListener75);
    taskCreatedListeners.add(taskRuntimeEventListener74);
    taskCreatedListeners.add(taskRuntimeEventListener73);
    taskCreatedListeners.add(taskRuntimeEventListener72);
    taskCreatedListeners.add(taskRuntimeEventListener71);
    taskCreatedListeners.add(taskRuntimeEventListener70);
    taskCreatedListeners.add(taskRuntimeEventListener69);
    taskCreatedListeners.add(taskRuntimeEventListener68);
    taskCreatedListeners.add(taskRuntimeEventListener67);
    taskCreatedListeners.add(taskRuntimeEventListener66);
    taskCreatedListeners.add(taskRuntimeEventListener65);
    taskCreatedListeners.add(taskRuntimeEventListener64);
    taskCreatedListeners.add(taskRuntimeEventListener63);
    taskCreatedListeners.add(taskRuntimeEventListener62);
    taskCreatedListeners.add(taskRuntimeEventListener61);
    taskCreatedListeners.add(taskRuntimeEventListener60);
    taskCreatedListeners.add(taskRuntimeEventListener59);
    taskCreatedListeners.add(taskRuntimeEventListener58);
    taskCreatedListeners.add(taskRuntimeEventListener57);
    taskCreatedListeners.add(taskRuntimeEventListener56);
    taskCreatedListeners.add(taskRuntimeEventListener55);
    taskCreatedListeners.add(taskRuntimeEventListener54);
    taskCreatedListeners.add(taskRuntimeEventListener53);
    taskCreatedListeners.add(taskRuntimeEventListener52);
    taskCreatedListeners.add(taskRuntimeEventListener51);
    taskCreatedListeners.add(taskRuntimeEventListener50);
    taskCreatedListeners.add(taskRuntimeEventListener49);
    taskCreatedListeners.add(taskRuntimeEventListener48);
    taskCreatedListeners.add(taskRuntimeEventListener47);
    taskCreatedListeners.add(taskRuntimeEventListener46);
    taskCreatedListeners.add(taskRuntimeEventListener45);
    taskCreatedListeners.add(taskRuntimeEventListener44);
    taskCreatedListeners.add(taskRuntimeEventListener43);
    taskCreatedListeners.add(taskRuntimeEventListener42);
    taskCreatedListeners.add(taskRuntimeEventListener41);
    taskCreatedListeners.add(taskRuntimeEventListener40);
    taskCreatedListeners.add(taskRuntimeEventListener39);
    taskCreatedListeners.add(taskRuntimeEventListener38);
    taskCreatedListeners.add(taskRuntimeEventListener37);
    taskCreatedListeners.add(taskRuntimeEventListener36);
    taskCreatedListeners.add(taskRuntimeEventListener35);
    taskCreatedListeners.add(taskRuntimeEventListener34);
    taskCreatedListeners.add(taskRuntimeEventListener33);
    taskCreatedListeners.add(taskRuntimeEventListener32);
    taskCreatedListeners.add(taskRuntimeEventListener31);
    taskCreatedListeners.add(taskRuntimeEventListener30);
    taskCreatedListeners.add(taskRuntimeEventListener29);
    taskCreatedListeners.add(taskRuntimeEventListener28);
    taskCreatedListeners.add(taskRuntimeEventListener27);
    taskCreatedListeners.add(taskRuntimeEventListener26);
    taskCreatedListeners.add(taskRuntimeEventListener25);
    taskCreatedListeners.add(taskRuntimeEventListener24);
    taskCreatedListeners.add(taskRuntimeEventListener23);
    taskCreatedListeners.add(taskRuntimeEventListener22);
    taskCreatedListeners.add(taskRuntimeEventListener21);
    taskCreatedListeners.add(taskRuntimeEventListener20);
    taskCreatedListeners.add(taskRuntimeEventListener19);
    taskCreatedListeners.add(taskRuntimeEventListener18);
    taskCreatedListeners.add(taskRuntimeEventListener17);
    taskCreatedListeners.add(taskRuntimeEventListener16);
    taskCreatedListeners.add(taskRuntimeEventListener15);
    taskCreatedListeners.add(taskRuntimeEventListener14);
    taskCreatedListeners.add(taskRuntimeEventListener13);
    taskCreatedListeners.add(taskRuntimeEventListener12);
    taskCreatedListeners.add(taskRuntimeEventListener11);
    taskCreatedListeners.add(taskRuntimeEventListener10);
    taskCreatedListeners.add(taskRuntimeEventListener9);
    taskCreatedListeners.add(taskRuntimeEventListener8);
    taskCreatedListeners.add(taskRuntimeEventListener7);
    taskCreatedListeners.add(taskRuntimeEventListener6);
    taskCreatedListeners.add(taskRuntimeEventListener5);
    taskCreatedListeners.add(taskRuntimeEventListener4);
    taskCreatedListeners.add(taskRuntimeEventListener3);
    taskCreatedListeners.add(taskRuntimeEventListener2);
    taskCreatedListeners.add(taskRuntimeEventListener);

    ToAPITaskCreatedEventConverter taskCreatedEventConverter =
        mock(ToAPITaskCreatedEventConverter.class);
    Optional<TaskCreatedEvent> ofResult = Optional.of(new TaskCreatedEventImpl(new TaskImpl()));
    when(taskCreatedEventConverter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);

    TaskCreatedListenerDelegate taskCreatedListenerDelegate =
        new TaskCreatedListenerDelegate(taskCreatedListeners, taskCreatedEventConverter);

    // Act
    taskCreatedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(mock(TaskEntityImpl.class), ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(taskRuntimeEventListener109).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener108).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener107).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener106).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener105).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener104).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener103).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener102).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener101).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener100).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener99).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener98).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener97).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener96).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener95).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener94).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener93).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener92).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener91).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener90).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener89).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener88).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener87).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener86).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener85).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener84).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener83).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener82).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener81).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener80).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener79).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener78).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener77).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener76).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener75).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener74).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener73).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener72).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener71).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener70).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener69).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener68).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener67).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener66).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener65).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener64).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener63).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener62).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener61).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener60).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener59).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener58).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener57).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener56).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener55).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener54).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener53).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener52).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener51).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener50).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener49).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener48).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener47).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener46).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener45).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener44).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener43).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener42).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener41).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener40).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener39).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener38).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener37).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener36).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener35).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener34).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener33).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener32).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener31).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener30).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener29).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener28).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener27).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener26).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener25).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener24).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener23).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener22).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener21).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener20).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener19).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener18).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener17).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener16).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener15).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener14).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener13).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener12).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener11).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener10).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener9).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener8).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener7).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener6).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener5).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener4).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener3).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener2).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener).onEvent(isA(TaskCreatedEvent.class));
    verify(taskCreatedEventConverter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Test {@link TaskCreatedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link APITaskConverter#fromWithCandidates(Task)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskCreatedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls fromWithCandidates(Task)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskCreatedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_thenCallsFromWithCandidates() {
    // Arrange
    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener2 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener2).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener3 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener3).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener4 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener4).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener5 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener5).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener6 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener6).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener7 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener7).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener8 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener8).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener9 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener9).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener10 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener10).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener11 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener11).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener12 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener12).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener13 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener13).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener14 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener14).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener15 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener15).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener16 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener16).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener17 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener17).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener18 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener18).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener19 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener19).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener20 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener20).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener21 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener21).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener22 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener22).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener23 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener23).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener24 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener24).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener25 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener25).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener26 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener26).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener27 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener27).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener28 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener28).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener29 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener29).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener30 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener30).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener31 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener31).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener32 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener32).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener33 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener33).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener34 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener34).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener35 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener35).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener36 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener36).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener37 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener37).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener38 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener38).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener39 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener39).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener40 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener40).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener41 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener41).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener42 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener42).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener43 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener43).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener44 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener44).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener45 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener45).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener46 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener46).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener47 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener47).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener48 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener48).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener49 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener49).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener50 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener50).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener51 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener51).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener52 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener52).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener53 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener53).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener54 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener54).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener55 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener55).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener56 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener56).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener57 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener57).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener58 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener58).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener59 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener59).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener60 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener60).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener61 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener61).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener62 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener62).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener63 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener63).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener64 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener64).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener65 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener65).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener66 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener66).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener67 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener67).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener68 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener68).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener69 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener69).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener70 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener70).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener71 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener71).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener72 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener72).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener73 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener73).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener74 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener74).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener75 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener75).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener76 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener76).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener77 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener77).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener78 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener78).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener79 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener79).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener80 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener80).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener81 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener81).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener82 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener82).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener83 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener83).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener84 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener84).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener85 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener85).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener86 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener86).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener87 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener87).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener88 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener88).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener89 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener89).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener90 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener90).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener91 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener91).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener92 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener92).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener93 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener93).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener94 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener94).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener95 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener95).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener96 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener96).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener97 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener97).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener98 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener98).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener99 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener99).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener100 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener100).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener101 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener101).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener102 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener102).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener103 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener103).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener104 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener104).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener105 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener105).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener106 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener106).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener107 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener107).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener108 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener108).onEvent(Mockito.<TaskCreatedEvent>any());

    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener109 =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener109).onEvent(Mockito.<TaskCreatedEvent>any());

    ArrayList<TaskRuntimeEventListener<TaskCreatedEvent>> taskCreatedListeners = new ArrayList<>();
    taskCreatedListeners.add(taskRuntimeEventListener109);
    taskCreatedListeners.add(taskRuntimeEventListener108);
    taskCreatedListeners.add(taskRuntimeEventListener107);
    taskCreatedListeners.add(taskRuntimeEventListener106);
    taskCreatedListeners.add(taskRuntimeEventListener105);
    taskCreatedListeners.add(taskRuntimeEventListener104);
    taskCreatedListeners.add(taskRuntimeEventListener103);
    taskCreatedListeners.add(taskRuntimeEventListener102);
    taskCreatedListeners.add(taskRuntimeEventListener101);
    taskCreatedListeners.add(taskRuntimeEventListener100);
    taskCreatedListeners.add(taskRuntimeEventListener99);
    taskCreatedListeners.add(taskRuntimeEventListener98);
    taskCreatedListeners.add(taskRuntimeEventListener97);
    taskCreatedListeners.add(taskRuntimeEventListener96);
    taskCreatedListeners.add(taskRuntimeEventListener95);
    taskCreatedListeners.add(taskRuntimeEventListener94);
    taskCreatedListeners.add(taskRuntimeEventListener93);
    taskCreatedListeners.add(taskRuntimeEventListener92);
    taskCreatedListeners.add(taskRuntimeEventListener91);
    taskCreatedListeners.add(taskRuntimeEventListener90);
    taskCreatedListeners.add(taskRuntimeEventListener89);
    taskCreatedListeners.add(taskRuntimeEventListener88);
    taskCreatedListeners.add(taskRuntimeEventListener87);
    taskCreatedListeners.add(taskRuntimeEventListener86);
    taskCreatedListeners.add(taskRuntimeEventListener85);
    taskCreatedListeners.add(taskRuntimeEventListener84);
    taskCreatedListeners.add(taskRuntimeEventListener83);
    taskCreatedListeners.add(taskRuntimeEventListener82);
    taskCreatedListeners.add(taskRuntimeEventListener81);
    taskCreatedListeners.add(taskRuntimeEventListener80);
    taskCreatedListeners.add(taskRuntimeEventListener79);
    taskCreatedListeners.add(taskRuntimeEventListener78);
    taskCreatedListeners.add(taskRuntimeEventListener77);
    taskCreatedListeners.add(taskRuntimeEventListener76);
    taskCreatedListeners.add(taskRuntimeEventListener75);
    taskCreatedListeners.add(taskRuntimeEventListener74);
    taskCreatedListeners.add(taskRuntimeEventListener73);
    taskCreatedListeners.add(taskRuntimeEventListener72);
    taskCreatedListeners.add(taskRuntimeEventListener71);
    taskCreatedListeners.add(taskRuntimeEventListener70);
    taskCreatedListeners.add(taskRuntimeEventListener69);
    taskCreatedListeners.add(taskRuntimeEventListener68);
    taskCreatedListeners.add(taskRuntimeEventListener67);
    taskCreatedListeners.add(taskRuntimeEventListener66);
    taskCreatedListeners.add(taskRuntimeEventListener65);
    taskCreatedListeners.add(taskRuntimeEventListener64);
    taskCreatedListeners.add(taskRuntimeEventListener63);
    taskCreatedListeners.add(taskRuntimeEventListener62);
    taskCreatedListeners.add(taskRuntimeEventListener61);
    taskCreatedListeners.add(taskRuntimeEventListener60);
    taskCreatedListeners.add(taskRuntimeEventListener59);
    taskCreatedListeners.add(taskRuntimeEventListener58);
    taskCreatedListeners.add(taskRuntimeEventListener57);
    taskCreatedListeners.add(taskRuntimeEventListener56);
    taskCreatedListeners.add(taskRuntimeEventListener55);
    taskCreatedListeners.add(taskRuntimeEventListener54);
    taskCreatedListeners.add(taskRuntimeEventListener53);
    taskCreatedListeners.add(taskRuntimeEventListener52);
    taskCreatedListeners.add(taskRuntimeEventListener51);
    taskCreatedListeners.add(taskRuntimeEventListener50);
    taskCreatedListeners.add(taskRuntimeEventListener49);
    taskCreatedListeners.add(taskRuntimeEventListener48);
    taskCreatedListeners.add(taskRuntimeEventListener47);
    taskCreatedListeners.add(taskRuntimeEventListener46);
    taskCreatedListeners.add(taskRuntimeEventListener45);
    taskCreatedListeners.add(taskRuntimeEventListener44);
    taskCreatedListeners.add(taskRuntimeEventListener43);
    taskCreatedListeners.add(taskRuntimeEventListener42);
    taskCreatedListeners.add(taskRuntimeEventListener41);
    taskCreatedListeners.add(taskRuntimeEventListener40);
    taskCreatedListeners.add(taskRuntimeEventListener39);
    taskCreatedListeners.add(taskRuntimeEventListener38);
    taskCreatedListeners.add(taskRuntimeEventListener37);
    taskCreatedListeners.add(taskRuntimeEventListener36);
    taskCreatedListeners.add(taskRuntimeEventListener35);
    taskCreatedListeners.add(taskRuntimeEventListener34);
    taskCreatedListeners.add(taskRuntimeEventListener33);
    taskCreatedListeners.add(taskRuntimeEventListener32);
    taskCreatedListeners.add(taskRuntimeEventListener31);
    taskCreatedListeners.add(taskRuntimeEventListener30);
    taskCreatedListeners.add(taskRuntimeEventListener29);
    taskCreatedListeners.add(taskRuntimeEventListener28);
    taskCreatedListeners.add(taskRuntimeEventListener27);
    taskCreatedListeners.add(taskRuntimeEventListener26);
    taskCreatedListeners.add(taskRuntimeEventListener25);
    taskCreatedListeners.add(taskRuntimeEventListener24);
    taskCreatedListeners.add(taskRuntimeEventListener23);
    taskCreatedListeners.add(taskRuntimeEventListener22);
    taskCreatedListeners.add(taskRuntimeEventListener21);
    taskCreatedListeners.add(taskRuntimeEventListener20);
    taskCreatedListeners.add(taskRuntimeEventListener19);
    taskCreatedListeners.add(taskRuntimeEventListener18);
    taskCreatedListeners.add(taskRuntimeEventListener17);
    taskCreatedListeners.add(taskRuntimeEventListener16);
    taskCreatedListeners.add(taskRuntimeEventListener15);
    taskCreatedListeners.add(taskRuntimeEventListener14);
    taskCreatedListeners.add(taskRuntimeEventListener13);
    taskCreatedListeners.add(taskRuntimeEventListener12);
    taskCreatedListeners.add(taskRuntimeEventListener11);
    taskCreatedListeners.add(taskRuntimeEventListener10);
    taskCreatedListeners.add(taskRuntimeEventListener9);
    taskCreatedListeners.add(taskRuntimeEventListener8);
    taskCreatedListeners.add(taskRuntimeEventListener7);
    taskCreatedListeners.add(taskRuntimeEventListener6);
    taskCreatedListeners.add(taskRuntimeEventListener5);
    taskCreatedListeners.add(taskRuntimeEventListener4);
    taskCreatedListeners.add(taskRuntimeEventListener3);
    taskCreatedListeners.add(taskRuntimeEventListener2);
    taskCreatedListeners.add(taskRuntimeEventListener);

    APITaskConverter taskConverter = mock(APITaskConverter.class);
    when(taskConverter.fromWithCandidates(Mockito.<Task>any())).thenReturn(new TaskImpl());
    ToAPITaskCreatedEventConverter taskCreatedEventConverter =
        new ToAPITaskCreatedEventConverter(taskConverter);

    TaskCreatedListenerDelegate taskCreatedListenerDelegate =
        new TaskCreatedListenerDelegate(taskCreatedListeners, taskCreatedEventConverter);

    // Act
    taskCreatedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(mock(TaskEntityImpl.class), ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(taskRuntimeEventListener109).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener108).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener107).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener106).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener105).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener104).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener103).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener102).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener101).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener100).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener99).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener98).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener97).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener96).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener95).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener94).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener93).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener92).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener91).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener90).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener89).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener88).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener87).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener86).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener85).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener84).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener83).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener82).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener81).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener80).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener79).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener78).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener77).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener76).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener75).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener74).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener73).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener72).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener71).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener70).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener69).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener68).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener67).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener66).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener65).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener64).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener63).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener62).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener61).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener60).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener59).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener58).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener57).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener56).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener55).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener54).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener53).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener52).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener51).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener50).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener49).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener48).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener47).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener46).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener45).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener44).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener43).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener42).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener41).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener40).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener39).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener38).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener37).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener36).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener35).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener34).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener33).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener32).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener31).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener30).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener29).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener28).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener27).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener26).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener25).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener24).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener23).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener22).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener21).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener20).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener19).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener18).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener17).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener16).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener15).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener14).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener13).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener12).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener11).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener10).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener9).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener8).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener7).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener6).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener5).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener4).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener3).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener2).onEvent(isA(TaskCreatedEvent.class));
    verify(taskRuntimeEventListener).onEvent(isA(TaskCreatedEvent.class));
    verify(taskConverter).fromWithCandidates(isA(Task.class));
  }

  /**
   * Test {@link TaskCreatedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link TaskService#getIdentityLinksForTask(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskCreatedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls getIdentityLinksForTask(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskCreatedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_thenCallsGetIdentityLinksForTask() {
    // Arrange
    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter taskConverter = new APITaskConverter(taskService);
    ToAPITaskCreatedEventConverter taskCreatedEventConverter =
        new ToAPITaskCreatedEventConverter(taskConverter);
    TaskCreatedListenerDelegate taskCreatedListenerDelegate =
        new TaskCreatedListenerDelegate(new ArrayList<>(), taskCreatedEventConverter);

    TaskEntityImpl taskEntityImpl = mock(TaskEntityImpl.class);
    when(taskEntityImpl.isDeleted()).thenReturn(true);
    when(taskEntityImpl.getPriority()).thenReturn(1);
    when(taskEntityImpl.getAppVersion()).thenReturn(1);
    when(taskEntityImpl.getId()).thenReturn("42");
    when(taskEntityImpl.getAssignee()).thenReturn("Assignee");
    when(taskEntityImpl.getBusinessKey()).thenReturn("Business Key");
    when(taskEntityImpl.getDescription()).thenReturn("The characteristics of someone or something");
    when(taskEntityImpl.getFormKey()).thenReturn("Form Key");
    when(taskEntityImpl.getName()).thenReturn("Name");
    when(taskEntityImpl.getOwner()).thenReturn("Owner");
    when(taskEntityImpl.getParentTaskId()).thenReturn("42");
    when(taskEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(taskEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(taskEntityImpl.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(taskEntityImpl.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(taskEntityImpl.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(taskEntityImpl.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    taskCreatedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(taskEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(taskEntityImpl, atLeast(1)).getId();
    verify(taskEntityImpl).getAppVersion();
    verify(taskEntityImpl).getAssignee();
    verify(taskEntityImpl).getBusinessKey();
    verify(taskEntityImpl).getClaimTime();
    verify(taskEntityImpl).getCreateTime();
    verify(taskEntityImpl).getDescription();
    verify(taskEntityImpl).getDueDate();
    verify(taskEntityImpl).getFormKey();
    verify(taskEntityImpl).getName();
    verify(taskEntityImpl).getOwner();
    verify(taskEntityImpl).getParentTaskId();
    verify(taskEntityImpl).getPriority();
    verify(taskEntityImpl).getProcessDefinitionId();
    verify(taskEntityImpl).getProcessInstanceId();
    verify(taskEntityImpl).getTaskDefinitionKey();
    verify(taskEntityImpl).isDeleted();
  }

  /**
   * Test {@link TaskCreatedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link TaskService#getIdentityLinksForTask(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskCreatedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls getIdentityLinksForTask(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskCreatedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_thenCallsGetIdentityLinksForTask2() {
    // Arrange
    TaskRuntimeEventListener<TaskCreatedEvent> taskRuntimeEventListener =
        mock(TaskRuntimeEventListener.class);
    doNothing().when(taskRuntimeEventListener).onEvent(Mockito.<TaskCreatedEvent>any());

    ArrayList<TaskRuntimeEventListener<TaskCreatedEvent>> taskCreatedListeners = new ArrayList<>();
    taskCreatedListeners.add(taskRuntimeEventListener);

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter taskConverter = new APITaskConverter(taskService);
    ToAPITaskCreatedEventConverter taskCreatedEventConverter =
        new ToAPITaskCreatedEventConverter(taskConverter);

    TaskCreatedListenerDelegate taskCreatedListenerDelegate =
        new TaskCreatedListenerDelegate(taskCreatedListeners, taskCreatedEventConverter);

    TaskEntityImpl taskEntityImpl = mock(TaskEntityImpl.class);
    when(taskEntityImpl.isDeleted()).thenReturn(true);
    when(taskEntityImpl.getPriority()).thenReturn(1);
    when(taskEntityImpl.getAppVersion()).thenReturn(1);
    when(taskEntityImpl.getId()).thenReturn("42");
    when(taskEntityImpl.getAssignee()).thenReturn("Assignee");
    when(taskEntityImpl.getBusinessKey()).thenReturn("Business Key");
    when(taskEntityImpl.getDescription()).thenReturn("The characteristics of someone or something");
    when(taskEntityImpl.getFormKey()).thenReturn("Form Key");
    when(taskEntityImpl.getName()).thenReturn("Name");
    when(taskEntityImpl.getOwner()).thenReturn("Owner");
    when(taskEntityImpl.getParentTaskId()).thenReturn("42");
    when(taskEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(taskEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(taskEntityImpl.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(taskEntityImpl.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(taskEntityImpl.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(taskEntityImpl.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    taskCreatedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(taskEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(taskRuntimeEventListener).onEvent(isA(TaskCreatedEvent.class));
    verify(taskService).getIdentityLinksForTask("42");
    verify(taskEntityImpl, atLeast(1)).getId();
    verify(taskEntityImpl).getAppVersion();
    verify(taskEntityImpl).getAssignee();
    verify(taskEntityImpl).getBusinessKey();
    verify(taskEntityImpl).getClaimTime();
    verify(taskEntityImpl).getCreateTime();
    verify(taskEntityImpl).getDescription();
    verify(taskEntityImpl).getDueDate();
    verify(taskEntityImpl).getFormKey();
    verify(taskEntityImpl).getName();
    verify(taskEntityImpl).getOwner();
    verify(taskEntityImpl).getParentTaskId();
    verify(taskEntityImpl).getPriority();
    verify(taskEntityImpl).getProcessDefinitionId();
    verify(taskEntityImpl).getProcessInstanceId();
    verify(taskEntityImpl).getTaskDefinitionKey();
    verify(taskEntityImpl).isDeleted();
  }
}
