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
package org.activiti.engine.impl.bpmn.listener;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.HasExecutionListeners;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.CustomPropertiesResolver;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.cfg.TransactionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ListenerNotificationHelperDiffblueTest {
  @InjectMocks
  private ListenerNotificationHelper listenerNotificationHelper;

  /**
   * Test
   * {@link ListenerNotificationHelper#executeExecutionListeners(HasExecutionListeners, DelegateExecution, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link FlowElement#getExecutionListeners()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ListenerNotificationHelper#executeExecutionListeners(HasExecutionListeners, DelegateExecution, String)}
   */
  @Test
  public void testExecuteExecutionListeners_givenArrayList_thenCallsGetExecutionListeners() {
    // Arrange
    AdhocSubProcess elementWithExecutionListeners = mock(AdhocSubProcess.class);
    when(elementWithExecutionListeners.getExecutionListeners()).thenReturn(new ArrayList<>());

    // Act
    listenerNotificationHelper.executeExecutionListeners(elementWithExecutionListeners,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Event Type");

    // Assert that nothing has changed
    verify(elementWithExecutionListeners).getExecutionListeners();
  }

  /**
   * Test
   * {@link ListenerNotificationHelper#invokeCustomPropertiesResolver(DelegateExecution, CustomPropertiesResolver)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ListenerNotificationHelper#invokeCustomPropertiesResolver(DelegateExecution, CustomPropertiesResolver)}
   */
  @Test
  public void testInvokeCustomPropertiesResolver_givenHashMap_thenReturnEmpty() {
    // Arrange
    ListenerNotificationHelper listenerNotificationHelper = new ListenerNotificationHelper();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    CustomPropertiesResolver customPropertiesResolver = mock(CustomPropertiesResolver.class);
    when(customPropertiesResolver.getCustomPropertiesMap(Mockito.<DelegateExecution>any())).thenReturn(new HashMap<>());

    // Act
    Map<String, Object> actualInvokeCustomPropertiesResolverResult = listenerNotificationHelper
        .invokeCustomPropertiesResolver(execution, customPropertiesResolver);

    // Assert
    verify(customPropertiesResolver).getCustomPropertiesMap(isA(DelegateExecution.class));
    assertTrue(actualInvokeCustomPropertiesResolverResult.isEmpty());
  }

  /**
   * Test
   * {@link ListenerNotificationHelper#invokeCustomPropertiesResolver(DelegateExecution, CustomPropertiesResolver)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ListenerNotificationHelper#invokeCustomPropertiesResolver(DelegateExecution, CustomPropertiesResolver)}
   */
  @Test
  public void testInvokeCustomPropertiesResolver_thenThrowActivitiException() {
    // Arrange
    ListenerNotificationHelper listenerNotificationHelper = new ListenerNotificationHelper();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    CustomPropertiesResolver customPropertiesResolver = mock(CustomPropertiesResolver.class);
    when(customPropertiesResolver.getCustomPropertiesMap(Mockito.<DelegateExecution>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> listenerNotificationHelper.invokeCustomPropertiesResolver(execution, customPropertiesResolver));
    verify(customPropertiesResolver).getCustomPropertiesMap(isA(DelegateExecution.class));
  }

  /**
   * Test
   * {@link ListenerNotificationHelper#invokeCustomPropertiesResolver(DelegateExecution, CustomPropertiesResolver)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ListenerNotificationHelper#invokeCustomPropertiesResolver(DelegateExecution, CustomPropertiesResolver)}
   */
  @Test
  public void testInvokeCustomPropertiesResolver_whenNull_thenReturnNull() {
    // Arrange
    ListenerNotificationHelper listenerNotificationHelper = new ListenerNotificationHelper();

    // Act and Assert
    assertNull(listenerNotificationHelper
        .invokeCustomPropertiesResolver(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), null));
  }

  /**
   * Test
   * {@link ListenerNotificationHelper#addTransactionListener(ActivitiListener, TransactionListener)}.
   * <ul>
   *   <li>Given {@code On Transaction}.</li>
   *   <li>Then calls {@link ActivitiListener#getOnTransaction()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ListenerNotificationHelper#addTransactionListener(ActivitiListener, TransactionListener)}
   */
  @Test
  public void testAddTransactionListener_givenOnTransaction_thenCallsGetOnTransaction() {
    // Arrange
    ListenerNotificationHelper listenerNotificationHelper = new ListenerNotificationHelper();
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getOnTransaction()).thenReturn("On Transaction");

    // Act
    listenerNotificationHelper.addTransactionListener(activitiListener, mock(TransactionListener.class));

    // Assert
    verify(activitiListener, atLeast(1)).getOnTransaction();
  }
}
