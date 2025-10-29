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
package org.activiti.runtime.api.conf;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.model.shared.event.VariableCreatedEvent;
import org.activiti.api.model.shared.event.VariableUpdatedEvent;
import org.activiti.api.runtime.shared.events.VariableEventListener;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.runtime.api.event.internal.VariableEventFilter;
import org.activiti.runtime.api.impl.VariableNameValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CommonRuntimeAutoConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link CommonRuntimeAutoConfiguration#registerVariableCreatedListenerDelegate(RuntimeService, List, VariableEventFilter)}
   */
  @Test
  void testRegisterVariableCreatedListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CommonRuntimeAutoConfiguration commonRuntimeAutoConfiguration = new CommonRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<VariableEventListener<VariableCreatedEvent>> listeners = new ArrayList<>();

    // Act
    commonRuntimeAutoConfiguration
        .registerVariableCreatedListenerDelegate(runtimeService, listeners, new VariableEventFilter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Method under test:
   * {@link CommonRuntimeAutoConfiguration#registerVariableCreatedListenerDelegate(RuntimeService, List, VariableEventFilter)}
   */
  @Test
  void testRegisterVariableCreatedListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CommonRuntimeAutoConfiguration commonRuntimeAutoConfiguration = new CommonRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<VariableEventListener<VariableCreatedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(VariableEventListener.class));

    // Act
    commonRuntimeAutoConfiguration
        .registerVariableCreatedListenerDelegate(runtimeService, listeners, new VariableEventFilter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Method under test:
   * {@link CommonRuntimeAutoConfiguration#registerVariableCreatedListenerDelegate(RuntimeService, List, VariableEventFilter)}
   */
  @Test
  void testRegisterVariableCreatedListenerDelegate3() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CommonRuntimeAutoConfiguration commonRuntimeAutoConfiguration = new CommonRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<VariableEventListener<VariableCreatedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(VariableEventListener.class));
    listeners.add(mock(VariableEventListener.class));

    // Act
    commonRuntimeAutoConfiguration
        .registerVariableCreatedListenerDelegate(runtimeService, listeners, new VariableEventFilter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Method under test:
   * {@link CommonRuntimeAutoConfiguration#registerVariableUpdatedListenerDelegate(RuntimeService, List, VariableEventFilter)}
   */
  @Test
  void testRegisterVariableUpdatedListenerDelegate() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CommonRuntimeAutoConfiguration commonRuntimeAutoConfiguration = new CommonRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<VariableEventListener<VariableUpdatedEvent>> listeners = new ArrayList<>();

    // Act
    commonRuntimeAutoConfiguration
        .registerVariableUpdatedListenerDelegate(runtimeService, listeners, new VariableEventFilter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Method under test:
   * {@link CommonRuntimeAutoConfiguration#registerVariableUpdatedListenerDelegate(RuntimeService, List, VariableEventFilter)}
   */
  @Test
  void testRegisterVariableUpdatedListenerDelegate2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CommonRuntimeAutoConfiguration commonRuntimeAutoConfiguration = new CommonRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<VariableEventListener<VariableUpdatedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(VariableEventListener.class));

    // Act
    commonRuntimeAutoConfiguration
        .registerVariableUpdatedListenerDelegate(runtimeService, listeners, new VariableEventFilter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Method under test:
   * {@link CommonRuntimeAutoConfiguration#registerVariableUpdatedListenerDelegate(RuntimeService, List, VariableEventFilter)}
   */
  @Test
  void testRegisterVariableUpdatedListenerDelegate3() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CommonRuntimeAutoConfiguration commonRuntimeAutoConfiguration = new CommonRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<VariableEventListener<VariableUpdatedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(VariableEventListener.class));
    listeners.add(mock(VariableEventListener.class));

    // Act
    commonRuntimeAutoConfiguration
        .registerVariableUpdatedListenerDelegate(runtimeService, listeners, new VariableEventFilter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Method under test:
   * {@link CommonRuntimeAutoConfiguration#variableNameValidator()}
   */
  @Test
  void testVariableNameValidator() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    VariableNameValidator actualVariableNameValidatorResult = (new CommonRuntimeAutoConfiguration())
        .variableNameValidator();

    // Assert
    assertTrue(actualVariableNameValidatorResult.validateVariables(null).isEmpty());
    assertTrue(actualVariableNameValidatorResult.validate("Name"));
  }
}
