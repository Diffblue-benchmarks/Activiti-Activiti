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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.HistoricVariableInstanceQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.db.HasRevision;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricVariableInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricVariableInstanceDataManager;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.VariableType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricVariableInstanceEntityManagerImplDiffblueTest {
  @Mock
  private HistoricVariableInstanceDataManager historicVariableInstanceDataManager;

  @InjectMocks
  private HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link HistoricVariableInstanceEntityManagerImpl#HistoricVariableInstanceEntityManagerImpl(ProcessEngineConfigurationImpl, HistoricVariableInstanceDataManager)}
   *   <li>
   * {@link HistoricVariableInstanceEntityManagerImpl#setHistoricVariableInstanceDataManager(HistoricVariableInstanceDataManager)}
   *   <li>{@link HistoricVariableInstanceEntityManagerImpl#getDataManager()}
   *   <li>
   * {@link HistoricVariableInstanceEntityManagerImpl#getHistoricVariableInstanceDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    HistoricVariableInstanceEntityManagerImpl actualHistoricVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration,
        new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    MybatisHistoricVariableInstanceDataManager historicVariableInstanceDataManager = new MybatisHistoricVariableInstanceDataManager(
        new JtaProcessEngineConfiguration());
    actualHistoricVariableInstanceEntityManagerImpl
        .setHistoricVariableInstanceDataManager(historicVariableInstanceDataManager);
    DataManager<HistoricVariableInstanceEntity> actualDataManager = actualHistoricVariableInstanceEntityManagerImpl
        .getDataManager();

    // Assert that nothing has changed
    assertSame(historicVariableInstanceDataManager, actualDataManager);
    assertSame(historicVariableInstanceDataManager,
        actualHistoricVariableInstanceEntityManagerImpl.getHistoricVariableInstanceDataManager());
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#copyAndInsert(VariableInstanceEntity)}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#copyAndInsert(VariableInstanceEntity)}
   */
  @Test
  public void testCopyAndInsert() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    doNothing().when(historicVariableInstanceDataManager).insert(Mockito.<HistoricVariableInstanceEntity>any());
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    when(historicVariableInstanceDataManager.create()).thenReturn(historicVariableInstanceEntityImpl);
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration, historicVariableInstanceDataManager);

    // Act
    HistoricVariableInstanceEntity actualCopyAndInsertResult = historicVariableInstanceEntityManagerImpl
        .copyAndInsert(new VariableInstanceEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getClock();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(historicVariableInstanceDataManager).create();
    verify(historicVariableInstanceDataManager).insert(isA(HistoricVariableInstanceEntity.class));
    assertSame(historicVariableInstanceEntityImpl, actualCopyAndInsertResult);
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#copyAndInsert(VariableInstanceEntity)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#copyAndInsert(VariableInstanceEntity)}
   */
  @Test
  public void testCopyAndInsert_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    doNothing().when(historicVariableInstanceDataManager).insert(Mockito.<HistoricVariableInstanceEntity>any());
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    when(historicVariableInstanceDataManager.create()).thenReturn(historicVariableInstanceEntityImpl);
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration, historicVariableInstanceDataManager);

    // Act
    HistoricVariableInstanceEntity actualCopyAndInsertResult = historicVariableInstanceEntityManagerImpl
        .copyAndInsert(new VariableInstanceEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(historicVariableInstanceDataManager).create();
    verify(historicVariableInstanceDataManager).insert(isA(HistoricVariableInstanceEntity.class));
    assertSame(historicVariableInstanceEntityImpl, actualCopyAndInsertResult);
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#copyAndInsert(VariableInstanceEntity)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#copyAndInsert(VariableInstanceEntity)}
   */
  @Test
  public void testCopyAndInsert_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    doNothing().when(historicVariableInstanceDataManager).insert(Mockito.<HistoricVariableInstanceEntity>any());
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    when(historicVariableInstanceDataManager.create()).thenReturn(historicVariableInstanceEntityImpl);
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration, historicVariableInstanceDataManager);

    // Act
    HistoricVariableInstanceEntity actualCopyAndInsertResult = historicVariableInstanceEntityManagerImpl
        .copyAndInsert(new VariableInstanceEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getClock();
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(historicVariableInstanceDataManager).create();
    verify(historicVariableInstanceDataManager).insert(isA(HistoricVariableInstanceEntity.class));
    assertSame(historicVariableInstanceEntityImpl, actualCopyAndInsertResult);
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#copyAndInsert(VariableInstanceEntity)}.
   * <ul>
   *   <li>Then calls {@link HasRevision#getRevision()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#copyAndInsert(VariableInstanceEntity)}
   */
  @Test
  public void testCopyAndInsert_thenCallsGetRevision() throws UnsupportedEncodingException {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = mock(
        HistoricVariableInstanceEntityImpl.class);
    doNothing().when(historicVariableInstanceEntityImpl).setId(Mockito.<String>any());
    doNothing().when(historicVariableInstanceEntityImpl).setRevision(anyInt());
    doNothing().when(historicVariableInstanceEntityImpl).setBytes(Mockito.<byte[]>any());
    doNothing().when(historicVariableInstanceEntityImpl).setCreateTime(Mockito.<Date>any());
    doNothing().when(historicVariableInstanceEntityImpl).setDoubleValue(Mockito.<Double>any());
    doNothing().when(historicVariableInstanceEntityImpl).setExecutionId(Mockito.<String>any());
    doNothing().when(historicVariableInstanceEntityImpl).setLastUpdatedTime(Mockito.<Date>any());
    doNothing().when(historicVariableInstanceEntityImpl).setLongValue(Mockito.<Long>any());
    doNothing().when(historicVariableInstanceEntityImpl).setName(Mockito.<String>any());
    doNothing().when(historicVariableInstanceEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(historicVariableInstanceEntityImpl).setTaskId(Mockito.<String>any());
    doNothing().when(historicVariableInstanceEntityImpl).setTextValue(Mockito.<String>any());
    doNothing().when(historicVariableInstanceEntityImpl).setTextValue2(Mockito.<String>any());
    doNothing().when(historicVariableInstanceEntityImpl).setVariableType(Mockito.<VariableType>any());
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    doNothing().when(historicVariableInstanceDataManager).insert(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.create()).thenReturn(historicVariableInstanceEntityImpl);
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration, historicVariableInstanceDataManager);
    VariableInstanceEntity variableInstance = mock(VariableInstanceEntity.class);
    when(variableInstance.getBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    when(variableInstance.getRevision()).thenReturn(1);
    when(variableInstance.getDoubleValue()).thenReturn(10.0d);
    when(variableInstance.getLongValue()).thenReturn(42L);
    when(variableInstance.getId()).thenReturn("42");
    when(variableInstance.getExecutionId()).thenReturn("42");
    when(variableInstance.getProcessInstanceId()).thenReturn("42");
    when(variableInstance.getTaskId()).thenReturn("42");
    when(variableInstance.getName()).thenReturn("Name");
    when(variableInstance.getTextValue()).thenReturn("42");
    when(variableInstance.getTextValue2()).thenReturn("42");
    when(variableInstance.getByteArrayRef()).thenReturn(new ByteArrayRef("42"));
    when(variableInstance.getType()).thenReturn(new BigDecimalType());

    // Act
    historicVariableInstanceEntityManagerImpl.copyAndInsert(variableInstance);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getClock();
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(variableInstance).getRevision();
    verify(historicVariableInstanceEntityImpl).setId(eq("42"));
    verify(historicVariableInstanceEntityImpl).setRevision(eq(1));
    verify(variableInstance).getId();
    verify(historicVariableInstanceEntityImpl).setBytes(isA(byte[].class));
    verify(historicVariableInstanceEntityImpl).setCreateTime(isA(Date.class));
    verify(historicVariableInstanceEntityImpl).setDoubleValue(eq(10.0d));
    verify(historicVariableInstanceEntityImpl).setExecutionId(eq("42"));
    verify(historicVariableInstanceEntityImpl, atLeast(1)).setLastUpdatedTime(Mockito.<Date>any());
    verify(historicVariableInstanceEntityImpl).setLongValue(eq(42L));
    verify(historicVariableInstanceEntityImpl).setName(eq("Name"));
    verify(historicVariableInstanceEntityImpl).setProcessInstanceId(eq("42"));
    verify(historicVariableInstanceEntityImpl).setTaskId(eq("42"));
    verify(historicVariableInstanceEntityImpl).setTextValue(eq("42"));
    verify(historicVariableInstanceEntityImpl).setTextValue2(eq("42"));
    verify(historicVariableInstanceEntityImpl, atLeast(1)).setVariableType(isA(VariableType.class));
    verify(variableInstance).getExecutionId();
    verify(variableInstance).getProcessInstanceId();
    verify(variableInstance).getTaskId();
    verify(variableInstance).getByteArrayRef();
    verify(variableInstance, atLeast(1)).getType();
    verify(historicVariableInstanceDataManager).create();
    verify(historicVariableInstanceDataManager).insert(isA(HistoricVariableInstanceEntity.class));
    verify(variableInstance).getBytes();
    verify(variableInstance).getDoubleValue();
    verify(variableInstance).getLongValue();
    verify(variableInstance).getName();
    verify(variableInstance).getTextValue();
    verify(variableInstance).getTextValue2();
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#copyVariableValue(HistoricVariableInstanceEntity, VariableInstanceEntity)}.
   * <ul>
   *   <li>Then {@link HistoricVariableInstanceEntityImpl} (default constructor)
   * PersistentState {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#copyVariableValue(HistoricVariableInstanceEntity, VariableInstanceEntity)}
   */
  @Test
  public void testCopyVariableValue_thenHistoricVariableInstanceEntityImplPersistentStateMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setClock(new DefaultClockImpl());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration,
        new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    HistoricVariableInstanceEntityImpl historicVariableInstance = new HistoricVariableInstanceEntityImpl();

    // Act
    historicVariableInstanceEntityManagerImpl.copyVariableValue(historicVariableInstance,
        new VariableInstanceEntityImpl());

    // Assert
    Object persistentState = historicVariableInstance.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(6, ((Map<String, Date>) persistentState).size());
    assertTrue(((Map<String, Date>) persistentState).containsKey("createTime"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("doubleValue"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("longValue"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("textValue"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("textValue2"));
    Date expectedGetResult = historicVariableInstance.getLastUpdatedTime();
    assertSame(expectedGetResult, ((Map<String, Date>) persistentState).get("lastUpdatedTime"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   * with {@code HistoricVariableInstanceEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricVariableInstanceEntityBoolean() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration, historicVariableInstanceDataManager);

    // Act
    historicVariableInstanceEntityManagerImpl.delete(new HistoricVariableInstanceEntityImpl(), true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   * with {@code HistoricVariableInstanceEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricVariableInstanceEntityBoolean2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration, historicVariableInstanceDataManager);

    // Act
    historicVariableInstanceEntityManagerImpl.delete(new HistoricVariableInstanceEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   * with {@code HistoricVariableInstanceEntity}, {@code boolean}.
   * <ul>
   *   <li>Given {@link ByteArrayRef#ByteArrayRef()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricVariableInstanceEntityBoolean_givenByteArrayRef() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration, historicVariableInstanceDataManager);
    HistoricVariableInstanceEntityImpl entity = mock(HistoricVariableInstanceEntityImpl.class);
    when(entity.getByteArrayRef()).thenReturn(new ByteArrayRef());

    // Act
    historicVariableInstanceEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(entity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   * with {@code HistoricVariableInstanceEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link ByteArrayRef#delete()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricVariableInstanceEntityBoolean_thenCallsDelete() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration, historicVariableInstanceDataManager);
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    HistoricVariableInstanceEntityImpl entity = mock(HistoricVariableInstanceEntityImpl.class);
    when(entity.getByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    historicVariableInstanceEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(byteArrayRef).delete();
    verify(entity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   * with {@code HistoricVariableInstanceEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricVariableInstanceEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        processEngineConfiguration, historicVariableInstanceDataManager);

    // Act
    historicVariableInstanceEntityManagerImpl.delete(new HistoricVariableInstanceEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   * with {@code HistoricVariableInstanceEntity}, {@code boolean}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then calls {@link ByteArrayRef#delete()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricVariableInstanceEntityBoolean_whenFalse_thenCallsDelete() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        mock(ProcessEngineConfigurationImpl.class), historicVariableInstanceDataManager);
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    HistoricVariableInstanceEntityImpl entity = mock(HistoricVariableInstanceEntityImpl.class);
    when(entity.getByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    historicVariableInstanceEntityManagerImpl.delete(entity, false);

    // Assert
    verify(byteArrayRef).delete();
    verify(entity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstanceByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId("42");

    // Assert that nothing has changed
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstanceByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstanceByProcessInstanceId3() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList = new ArrayList<>();
    historicVariableInstanceEntityList.add(new HistoricVariableInstanceEntityImpl());
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstanceByProcessInstanceId4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList = new ArrayList<>();
    historicVariableInstanceEntityList.add(new HistoricVariableInstanceEntityImpl());
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId("42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstanceByProcessInstanceId5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = mock(
        HistoricVariableInstanceEntityImpl.class);
    when(historicVariableInstanceEntityImpl.getByteArrayRef()).thenReturn(new ByteArrayRef());

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList = new ArrayList<>();
    historicVariableInstanceEntityList.add(historicVariableInstanceEntityImpl);
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceEntityImpl, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then calls {@link ByteArrayRef#delete()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstanceByProcessInstanceId_thenCallsDelete() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = mock(
        HistoricVariableInstanceEntityImpl.class);
    when(historicVariableInstanceEntityImpl.getByteArrayRef()).thenReturn(byteArrayRef);

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList = new ArrayList<>();
    historicVariableInstanceEntityList.add(historicVariableInstanceEntityImpl);
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(byteArrayRef).delete();
    verify(historicVariableInstanceEntityImpl, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstanceByProcessInstanceId_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList = new ArrayList<>();
    historicVariableInstanceEntityList.add(new HistoricVariableInstanceEntityImpl());
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstanceCountByQueryCriteria(HistoricVariableInstanceQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstanceCountByQueryCriteria(HistoricVariableInstanceQueryImpl)}
   */
  @Test
  public void testFindHistoricVariableInstanceCountByQueryCriteria_thenReturnThree() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    when(historicVariableInstanceDataManager
        .findHistoricVariableInstanceCountByQueryCriteria(Mockito.<HistoricVariableInstanceQueryImpl>any()))
        .thenReturn(3L);
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicVariableInstanceDataManager);

    // Act
    long actualFindHistoricVariableInstanceCountByQueryCriteriaResult = historicVariableInstanceEntityManagerImpl
        .findHistoricVariableInstanceCountByQueryCriteria(new HistoricVariableInstanceQueryImpl());

    // Assert
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstanceCountByQueryCriteria(isA(HistoricVariableInstanceQueryImpl.class));
    assertEquals(3L, actualFindHistoricVariableInstanceCountByQueryCriteriaResult);
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstancesByQueryCriteria(HistoricVariableInstanceQueryImpl, Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstancesByQueryCriteria(HistoricVariableInstanceQueryImpl, Page)}
   */
  @Test
  public void testFindHistoricVariableInstancesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByQueryCriteria(
        Mockito.<HistoricVariableInstanceQueryImpl>any(), Mockito.<Page>any())).thenReturn(new ArrayList<>());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicVariableInstanceDataManager);
    HistoricVariableInstanceQueryImpl historicProcessVariableQuery = new HistoricVariableInstanceQueryImpl();

    // Act
    List<HistoricVariableInstance> actualFindHistoricVariableInstancesByQueryCriteriaResult = historicVariableInstanceEntityManagerImpl
        .findHistoricVariableInstancesByQueryCriteria(historicProcessVariableQuery, new Page(1, 3));

    // Assert
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstancesByQueryCriteria(isA(HistoricVariableInstanceQueryImpl.class), isA(Page.class));
    assertTrue(actualFindHistoricVariableInstancesByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstanceByVariableInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstanceByVariableInstanceId(String)}
   */
  @Test
  public void testFindHistoricVariableInstanceByVariableInstanceId() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = new HistoricVariableInstanceEntityImpl();
    when(historicVariableInstanceDataManager.findHistoricVariableInstanceByVariableInstanceId(Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityImpl);

    // Act
    HistoricVariableInstanceEntity actualFindHistoricVariableInstanceByVariableInstanceIdResult = historicVariableInstanceEntityManagerImpl
        .findHistoricVariableInstanceByVariableInstanceId("42");

    // Assert
    verify(historicVariableInstanceDataManager).findHistoricVariableInstanceByVariableInstanceId(eq("42"));
    assertSame(historicVariableInstanceEntityImpl, actualFindHistoricVariableInstanceByVariableInstanceIdResult);
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstancesByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert that nothing has changed
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstancesByTaskId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstancesByTaskId3() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList = new ArrayList<>();
    historicVariableInstanceEntityList.add(new HistoricVariableInstanceEntityImpl());
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstancesByTaskId4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList = new ArrayList<>();
    historicVariableInstanceEntityList.add(new HistoricVariableInstanceEntityImpl());
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstancesByTaskId5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = mock(
        HistoricVariableInstanceEntityImpl.class);
    when(historicVariableInstanceEntityImpl.getByteArrayRef()).thenReturn(new ByteArrayRef());

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList = new ArrayList<>();
    historicVariableInstanceEntityList.add(historicVariableInstanceEntityImpl);
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceEntityImpl, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   * <ul>
   *   <li>Then calls {@link ByteArrayRef#delete()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstancesByTaskId_thenCallsDelete() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl = mock(
        HistoricVariableInstanceEntityImpl.class);
    when(historicVariableInstanceEntityImpl.getByteArrayRef()).thenReturn(byteArrayRef);

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList = new ArrayList<>();
    historicVariableInstanceEntityList.add(historicVariableInstanceEntityImpl);
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(byteArrayRef).delete();
    verify(historicVariableInstanceEntityImpl, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricVariableInstancesByTaskId_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList = new ArrayList<>();
    historicVariableInstanceEntityList.add(new HistoricVariableInstanceEntityImpl());
    doNothing().when(historicVariableInstanceDataManager).delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstancesByNativeQuery(Map, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstancesByNativeQuery(Map, int, int)}
   */
  @Test
  public void testFindHistoricVariableInstancesByNativeQuery_thenReturnEmpty() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    when(historicVariableInstanceDataManager
        .findHistoricVariableInstancesByNativeQuery(Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicVariableInstanceDataManager);

    // Act
    List<HistoricVariableInstance> actualFindHistoricVariableInstancesByNativeQueryResult = historicVariableInstanceEntityManagerImpl
        .findHistoricVariableInstancesByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByNativeQuery(isA(Map.class), eq(1),
        eq(3));
    assertTrue(actualFindHistoricVariableInstancesByNativeQueryResult.isEmpty());
  }

  /**
   * Test
   * {@link HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstanceCountByNativeQuery(Map)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstanceCountByNativeQuery(Map)}
   */
  @Test
  public void testFindHistoricVariableInstanceCountByNativeQuery_thenReturnThree() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager = mock(
        HistoricVariableInstanceDataManager.class);
    when(historicVariableInstanceDataManager
        .findHistoricVariableInstanceCountByNativeQuery(Mockito.<Map<String, Object>>any())).thenReturn(3L);
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl = new HistoricVariableInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicVariableInstanceDataManager);

    // Act
    long actualFindHistoricVariableInstanceCountByNativeQueryResult = historicVariableInstanceEntityManagerImpl
        .findHistoricVariableInstanceCountByNativeQuery(new HashMap<>());

    // Assert
    verify(historicVariableInstanceDataManager).findHistoricVariableInstanceCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindHistoricVariableInstanceCountByNativeQueryResult);
  }
}
