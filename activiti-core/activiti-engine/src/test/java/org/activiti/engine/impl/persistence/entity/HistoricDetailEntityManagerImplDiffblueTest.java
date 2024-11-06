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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.impl.HistoricDetailQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.db.HasRevision;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricDetailDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricDetailDataManager;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.VariableType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricDetailEntityManagerImplDiffblueTest {
  @Mock
  private HistoricDetailDataManager historicDetailDataManager;

  @InjectMocks
  private HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link HistoricDetailEntityManagerImpl#HistoricDetailEntityManagerImpl(ProcessEngineConfigurationImpl, HistoricDetailDataManager)}
   *   <li>
   * {@link HistoricDetailEntityManagerImpl#setHistoricDetailDataManager(HistoricDetailDataManager)}
   *   <li>{@link HistoricDetailEntityManagerImpl#getDataManager()}
   *   <li>{@link HistoricDetailEntityManagerImpl#getHistoricDetailDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    HistoricDetailEntityManagerImpl actualHistoricDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, new MybatisHistoricDetailDataManager(new JtaProcessEngineConfiguration()));
    MybatisHistoricDetailDataManager historicDetailDataManager = new MybatisHistoricDetailDataManager(
        new JtaProcessEngineConfiguration());
    actualHistoricDetailEntityManagerImpl.setHistoricDetailDataManager(historicDetailDataManager);
    DataManager<HistoricDetailEntity> actualDataManager = actualHistoricDetailEntityManagerImpl.getDataManager();

    // Assert that nothing has changed
    assertSame(historicDetailDataManager, actualDataManager);
    assertSame(historicDetailDataManager, actualHistoricDetailEntityManagerImpl.getHistoricDetailDataManager());
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#copyAndInsertHistoricDetailVariableInstanceUpdateEntity(VariableInstanceEntity)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#copyAndInsertHistoricDetailVariableInstanceUpdateEntity(VariableInstanceEntity)}
   */
  @Test
  public void testCopyAndInsertHistoricDetailVariableInstanceUpdateEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    doNothing().when(historicDetailDataManager).insert(Mockito.<HistoricDetailEntity>any());
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    when(historicDetailDataManager.createHistoricDetailVariableInstanceUpdate())
        .thenReturn(historicDetailVariableInstanceUpdateEntityImpl);
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, historicDetailDataManager);

    // Act
    HistoricDetailVariableInstanceUpdateEntity actualCopyAndInsertHistoricDetailVariableInstanceUpdateEntityResult = historicDetailEntityManagerImpl
        .copyAndInsertHistoricDetailVariableInstanceUpdateEntity(new VariableInstanceEntityImpl());

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(historicDetailDataManager).insert(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).createHistoricDetailVariableInstanceUpdate();
    assertSame(historicDetailVariableInstanceUpdateEntityImpl,
        actualCopyAndInsertHistoricDetailVariableInstanceUpdateEntityResult);
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#copyAndInsertHistoricDetailVariableInstanceUpdateEntity(VariableInstanceEntity)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#copyAndInsertHistoricDetailVariableInstanceUpdateEntity(VariableInstanceEntity)}
   */
  @Test
  public void testCopyAndInsertHistoricDetailVariableInstanceUpdateEntity2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    doNothing().when(historicDetailDataManager).insert(Mockito.<HistoricDetailEntity>any());
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    when(historicDetailDataManager.createHistoricDetailVariableInstanceUpdate())
        .thenReturn(historicDetailVariableInstanceUpdateEntityImpl);
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, historicDetailDataManager);

    // Act
    HistoricDetailVariableInstanceUpdateEntity actualCopyAndInsertHistoricDetailVariableInstanceUpdateEntityResult = historicDetailEntityManagerImpl
        .copyAndInsertHistoricDetailVariableInstanceUpdateEntity(new VariableInstanceEntityImpl());

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(historicDetailDataManager).insert(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).createHistoricDetailVariableInstanceUpdate();
    assertSame(historicDetailVariableInstanceUpdateEntityImpl,
        actualCopyAndInsertHistoricDetailVariableInstanceUpdateEntityResult);
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#copyAndInsertHistoricDetailVariableInstanceUpdateEntity(VariableInstanceEntity)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#copyAndInsertHistoricDetailVariableInstanceUpdateEntity(VariableInstanceEntity)}
   */
  @Test
  public void testCopyAndInsertHistoricDetailVariableInstanceUpdateEntity3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    doNothing().when(historicDetailDataManager).insert(Mockito.<HistoricDetailEntity>any());
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    when(historicDetailDataManager.createHistoricDetailVariableInstanceUpdate())
        .thenReturn(historicDetailVariableInstanceUpdateEntityImpl);
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, historicDetailDataManager);

    // Act
    HistoricDetailVariableInstanceUpdateEntity actualCopyAndInsertHistoricDetailVariableInstanceUpdateEntityResult = historicDetailEntityManagerImpl
        .copyAndInsertHistoricDetailVariableInstanceUpdateEntity(new VariableInstanceEntityImpl());

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(historicDetailDataManager).insert(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).createHistoricDetailVariableInstanceUpdate();
    assertSame(historicDetailVariableInstanceUpdateEntityImpl,
        actualCopyAndInsertHistoricDetailVariableInstanceUpdateEntityResult);
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#copyAndInsertHistoricDetailVariableInstanceUpdateEntity(VariableInstanceEntity)}.
   * <ul>
   *   <li>Then calls {@link HasRevision#getRevision()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#copyAndInsertHistoricDetailVariableInstanceUpdateEntity(VariableInstanceEntity)}
   */
  @Test
  public void testCopyAndInsertHistoricDetailVariableInstanceUpdateEntity_thenCallsGetRevision()
      throws UnsupportedEncodingException {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = mock(
        HistoricDetailVariableInstanceUpdateEntityImpl.class);
    doNothing().when(historicDetailVariableInstanceUpdateEntityImpl).setExecutionId(Mockito.<String>any());
    doNothing().when(historicDetailVariableInstanceUpdateEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(historicDetailVariableInstanceUpdateEntityImpl).setTaskId(Mockito.<String>any());
    doNothing().when(historicDetailVariableInstanceUpdateEntityImpl).setTime(Mockito.<Date>any());
    doNothing().when(historicDetailVariableInstanceUpdateEntityImpl).setBytes(Mockito.<byte[]>any());
    doNothing().when(historicDetailVariableInstanceUpdateEntityImpl).setDoubleValue(Mockito.<Double>any());
    doNothing().when(historicDetailVariableInstanceUpdateEntityImpl).setLongValue(Mockito.<Long>any());
    doNothing().when(historicDetailVariableInstanceUpdateEntityImpl).setName(Mockito.<String>any());
    doNothing().when(historicDetailVariableInstanceUpdateEntityImpl).setRevision(anyInt());
    doNothing().when(historicDetailVariableInstanceUpdateEntityImpl).setTextValue(Mockito.<String>any());
    doNothing().when(historicDetailVariableInstanceUpdateEntityImpl).setTextValue2(Mockito.<String>any());
    doNothing().when(historicDetailVariableInstanceUpdateEntityImpl).setVariableType(Mockito.<VariableType>any());
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    doNothing().when(historicDetailDataManager).insert(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.createHistoricDetailVariableInstanceUpdate())
        .thenReturn(historicDetailVariableInstanceUpdateEntityImpl);
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, historicDetailDataManager);
    VariableInstanceEntity variableInstance = mock(VariableInstanceEntity.class);
    when(variableInstance.getBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    when(variableInstance.getRevision()).thenReturn(1);
    when(variableInstance.getDoubleValue()).thenReturn(10.0d);
    when(variableInstance.getLongValue()).thenReturn(42L);
    when(variableInstance.getExecutionId()).thenReturn("42");
    when(variableInstance.getProcessInstanceId()).thenReturn("42");
    when(variableInstance.getTaskId()).thenReturn("42");
    when(variableInstance.getName()).thenReturn("Name");
    when(variableInstance.getTextValue()).thenReturn("42");
    when(variableInstance.getTextValue2()).thenReturn("42");
    when(variableInstance.getType()).thenReturn(new BigDecimalType());

    // Act
    historicDetailEntityManagerImpl.copyAndInsertHistoricDetailVariableInstanceUpdateEntity(variableInstance);

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(variableInstance).getRevision();
    verify(historicDetailVariableInstanceUpdateEntityImpl).setExecutionId(eq("42"));
    verify(historicDetailVariableInstanceUpdateEntityImpl).setProcessInstanceId(eq("42"));
    verify(historicDetailVariableInstanceUpdateEntityImpl).setTaskId(eq("42"));
    verify(historicDetailVariableInstanceUpdateEntityImpl).setTime(isA(Date.class));
    verify(historicDetailVariableInstanceUpdateEntityImpl).setBytes(isA(byte[].class));
    verify(historicDetailVariableInstanceUpdateEntityImpl).setDoubleValue(eq(10.0d));
    verify(historicDetailVariableInstanceUpdateEntityImpl).setLongValue(eq(42L));
    verify(historicDetailVariableInstanceUpdateEntityImpl).setName(eq("Name"));
    verify(historicDetailVariableInstanceUpdateEntityImpl).setRevision(eq(1));
    verify(historicDetailVariableInstanceUpdateEntityImpl).setTextValue(eq("42"));
    verify(historicDetailVariableInstanceUpdateEntityImpl).setTextValue2(eq("42"));
    verify(historicDetailVariableInstanceUpdateEntityImpl).setVariableType(isA(VariableType.class));
    verify(variableInstance).getExecutionId();
    verify(variableInstance).getProcessInstanceId();
    verify(variableInstance).getTaskId();
    verify(variableInstance).getType();
    verify(historicDetailDataManager).insert(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).createHistoricDetailVariableInstanceUpdate();
    verify(variableInstance, atLeast(1)).getBytes();
    verify(variableInstance).getDoubleValue();
    verify(variableInstance).getLongValue();
    verify(variableInstance).getName();
    verify(variableInstance).getTextValue();
    verify(variableInstance).getTextValue2();
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   * with {@code HistoricDetailEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricDetailEntityBoolean() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, historicDetailDataManager);

    // Act
    historicDetailEntityManagerImpl.delete(new HistoricDetailAssignmentEntityImpl(), true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   * with {@code HistoricDetailEntity}, {@code boolean}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricDetailEntityBoolean2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, historicDetailDataManager);

    // Act
    historicDetailEntityManagerImpl.delete(new HistoricDetailAssignmentEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   * with {@code HistoricDetailEntity}, {@code boolean}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricDetailEntityBoolean_given42() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, historicDetailDataManager);

    HistoricDetailVariableInstanceUpdateEntityImpl entity = new HistoricDetailVariableInstanceUpdateEntityImpl();
    entity.setActivityInstanceId("42");
    entity.setCachedValue(JSONObject.NULL);
    entity.setDeleted(true);
    entity.setDetailType("Detail Type");
    entity.setDoubleValue(10.0d);
    entity.setExecutionId("42");
    entity.setId("42");
    entity.setInserted(true);
    entity.setLongValue(42L);
    entity.setName("Name");
    entity.setProcessInstanceId("42");
    entity.setRevision(1);
    entity.setTaskId("42");
    entity.setTextValue("42");
    entity.setTextValue2("42");
    entity.setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    entity.setUpdated(true);
    entity.setVariableType(new BigDecimalType());

    // Act
    historicDetailEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   * with {@code HistoricDetailEntity}, {@code boolean}.
   * <ul>
   *   <li>Given {@link ByteArrayRef#ByteArrayRef()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricDetailEntityBoolean_givenByteArrayRef() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, historicDetailDataManager);
    HistoricDetailVariableInstanceUpdateEntityImpl entity = mock(HistoricDetailVariableInstanceUpdateEntityImpl.class);
    when(entity.getByteArrayRef()).thenReturn(new ByteArrayRef());

    // Act
    historicDetailEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(entity, atLeast(1)).getByteArrayRef();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   * with {@code HistoricDetailEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls {@link ByteArrayRef#delete()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricDetailEntityBoolean_thenCallsDelete() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, historicDetailDataManager);
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    HistoricDetailVariableInstanceUpdateEntityImpl entity = mock(HistoricDetailVariableInstanceUpdateEntityImpl.class);
    when(entity.getByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    historicDetailEntityManagerImpl.delete(entity, true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(byteArrayRef).delete();
    verify(entity, atLeast(1)).getByteArrayRef();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   * with {@code HistoricDetailEntity}, {@code boolean}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricDetailEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        processEngineConfiguration, historicDetailDataManager);

    // Act
    historicDetailEntityManagerImpl.delete(new HistoricDetailAssignmentEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   * with {@code HistoricDetailEntity}, {@code boolean}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then calls {@link ByteArrayRef#delete()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#delete(HistoricDetailEntity, boolean)}
   */
  @Test
  public void testDeleteWithHistoricDetailEntityBoolean_whenFalse_thenCallsDelete() {
    // Arrange
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        mock(ProcessEngineConfigurationImpl.class), historicDetailDataManager);
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    HistoricDetailVariableInstanceUpdateEntityImpl entity = mock(HistoricDetailVariableInstanceUpdateEntityImpl.class);
    when(entity.getByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    historicDetailEntityManagerImpl.delete(entity, false);

    // Assert
    verify(byteArrayRef).delete();
    verify(entity, atLeast(1)).getByteArrayRef();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByProcessInstanceId("42");

    // Assert that nothing has changed
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.AUDIT));
    when(historicDetailDataManager.findHistoricDetailsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicDetailDataManager).findHistoricDetailsByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByProcessInstanceId3() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.AUDIT));

    ArrayList<HistoricDetailEntity> historicDetailEntityList = new ArrayList<>();
    historicDetailEntityList.add(new HistoricDetailAssignmentEntityImpl());
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.findHistoricDetailsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicDetailEntityList);

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).findHistoricDetailsByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByProcessInstanceId4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.AUDIT));

    ArrayList<HistoricDetailEntity> historicDetailEntityList = new ArrayList<>();
    historicDetailEntityList.add(new HistoricDetailAssignmentEntityImpl());
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.findHistoricDetailsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicDetailEntityList);

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByProcessInstanceId("42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).findHistoricDetailsByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByProcessInstanceId5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.AUDIT));

    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(JSONObject.NULL);
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(10.0d);
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(42L);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2("42");
    historicDetailVariableInstanceUpdateEntityImpl
        .setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());

    ArrayList<HistoricDetailEntity> historicDetailEntityList = new ArrayList<>();
    historicDetailEntityList.add(historicDetailVariableInstanceUpdateEntityImpl);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.findHistoricDetailsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicDetailEntityList);

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByProcessInstanceId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).findHistoricDetailsByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByProcessInstanceId6() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.AUDIT));
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = mock(
        HistoricDetailVariableInstanceUpdateEntityImpl.class);
    when(historicDetailVariableInstanceUpdateEntityImpl.getByteArrayRef()).thenReturn(new ByteArrayRef());

    ArrayList<HistoricDetailEntity> historicDetailEntityList = new ArrayList<>();
    historicDetailEntityList.add(historicDetailVariableInstanceUpdateEntityImpl);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.findHistoricDetailsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicDetailEntityList);

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByProcessInstanceId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicDetailVariableInstanceUpdateEntityImpl, atLeast(1)).getByteArrayRef();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).findHistoricDetailsByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then calls {@link ByteArrayRef#delete()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByProcessInstanceId_thenCallsDelete() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.AUDIT));
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = mock(
        HistoricDetailVariableInstanceUpdateEntityImpl.class);
    when(historicDetailVariableInstanceUpdateEntityImpl.getByteArrayRef()).thenReturn(byteArrayRef);

    ArrayList<HistoricDetailEntity> historicDetailEntityList = new ArrayList<>();
    historicDetailEntityList.add(historicDetailVariableInstanceUpdateEntityImpl);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.findHistoricDetailsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicDetailEntityList);

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByProcessInstanceId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(byteArrayRef).delete();
    verify(historicDetailVariableInstanceUpdateEntityImpl, atLeast(1)).getByteArrayRef();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).findHistoricDetailsByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByProcessInstanceId_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.AUDIT));

    ArrayList<HistoricDetailEntity> historicDetailEntityList = new ArrayList<>();
    historicDetailEntityList.add(new HistoricDetailAssignmentEntityImpl());
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.findHistoricDetailsByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(historicDetailEntityList);

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByProcessInstanceId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).findHistoricDetailsByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#findHistoricDetailCountByQueryCriteria(HistoricDetailQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#findHistoricDetailCountByQueryCriteria(HistoricDetailQueryImpl)}
   */
  @Test
  public void testFindHistoricDetailCountByQueryCriteria_thenReturnThree() {
    // Arrange
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    when(historicDetailDataManager.findHistoricDetailCountByQueryCriteria(Mockito.<HistoricDetailQueryImpl>any()))
        .thenReturn(3L);
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicDetailDataManager);

    // Act
    long actualFindHistoricDetailCountByQueryCriteriaResult = historicDetailEntityManagerImpl
        .findHistoricDetailCountByQueryCriteria(new HistoricDetailQueryImpl());

    // Assert
    verify(historicDetailDataManager).findHistoricDetailCountByQueryCriteria(isA(HistoricDetailQueryImpl.class));
    assertEquals(3L, actualFindHistoricDetailCountByQueryCriteriaResult);
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#findHistoricDetailsByQueryCriteria(HistoricDetailQueryImpl, Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#findHistoricDetailsByQueryCriteria(HistoricDetailQueryImpl, Page)}
   */
  @Test
  public void testFindHistoricDetailsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    when(historicDetailDataManager.findHistoricDetailsByQueryCriteria(Mockito.<HistoricDetailQueryImpl>any(),
        Mockito.<Page>any())).thenReturn(new ArrayList<>());
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicDetailDataManager);
    HistoricDetailQueryImpl historicVariableUpdateQuery = new HistoricDetailQueryImpl();

    // Act
    List<HistoricDetail> actualFindHistoricDetailsByQueryCriteriaResult = historicDetailEntityManagerImpl
        .findHistoricDetailsByQueryCriteria(historicVariableUpdateQuery, new Page(1, 3));

    // Assert
    verify(historicDetailDataManager).findHistoricDetailsByQueryCriteria(isA(HistoricDetailQueryImpl.class),
        isA(Page.class));
    assertTrue(actualFindHistoricDetailsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByTaskId("42");

    // Assert that nothing has changed
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByTaskId2() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.FULL));

    ArrayList<HistoricDetailEntity> historicDetailEntityList = new ArrayList<>();
    historicDetailEntityList.add(new HistoricDetailAssignmentEntityImpl());
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.findHistoricDetailsByTaskId(Mockito.<String>any()))
        .thenReturn(historicDetailEntityList);

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).findHistoricDetailsByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByTaskId3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.FULL));

    ArrayList<HistoricDetailEntity> historicDetailEntityList = new ArrayList<>();
    historicDetailEntityList.add(new HistoricDetailAssignmentEntityImpl());
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.findHistoricDetailsByTaskId(Mockito.<String>any()))
        .thenReturn(historicDetailEntityList);

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByTaskId("42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).findHistoricDetailsByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByTaskId4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.FULL));

    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = new HistoricDetailVariableInstanceUpdateEntityImpl();
    historicDetailVariableInstanceUpdateEntityImpl.setActivityInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setCachedValue(JSONObject.NULL);
    historicDetailVariableInstanceUpdateEntityImpl.setDeleted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setDetailType("Detail Type");
    historicDetailVariableInstanceUpdateEntityImpl.setDoubleValue(10.0d);
    historicDetailVariableInstanceUpdateEntityImpl.setExecutionId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setInserted(true);
    historicDetailVariableInstanceUpdateEntityImpl.setLongValue(42L);
    historicDetailVariableInstanceUpdateEntityImpl.setName("Name");
    historicDetailVariableInstanceUpdateEntityImpl.setProcessInstanceId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setRevision(1);
    historicDetailVariableInstanceUpdateEntityImpl.setTaskId("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue("42");
    historicDetailVariableInstanceUpdateEntityImpl.setTextValue2("42");
    historicDetailVariableInstanceUpdateEntityImpl
        .setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicDetailVariableInstanceUpdateEntityImpl.setUpdated(true);
    historicDetailVariableInstanceUpdateEntityImpl.setVariableType(new BigDecimalType());

    ArrayList<HistoricDetailEntity> historicDetailEntityList = new ArrayList<>();
    historicDetailEntityList.add(historicDetailVariableInstanceUpdateEntityImpl);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.findHistoricDetailsByTaskId(Mockito.<String>any()))
        .thenReturn(historicDetailEntityList);

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByTaskId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).findHistoricDetailsByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByTaskId5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.FULL));
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = mock(
        HistoricDetailVariableInstanceUpdateEntityImpl.class);
    when(historicDetailVariableInstanceUpdateEntityImpl.getByteArrayRef()).thenReturn(new ByteArrayRef());

    ArrayList<HistoricDetailEntity> historicDetailEntityList = new ArrayList<>();
    historicDetailEntityList.add(historicDetailVariableInstanceUpdateEntityImpl);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.findHistoricDetailsByTaskId(Mockito.<String>any()))
        .thenReturn(historicDetailEntityList);

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByTaskId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicDetailVariableInstanceUpdateEntityImpl, atLeast(1)).getByteArrayRef();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).findHistoricDetailsByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}.
   * <ul>
   *   <li>Then calls {@link ByteArrayRef#delete()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByTaskId_thenCallsDelete() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.FULL));
    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();
    HistoricDetailVariableInstanceUpdateEntityImpl historicDetailVariableInstanceUpdateEntityImpl = mock(
        HistoricDetailVariableInstanceUpdateEntityImpl.class);
    when(historicDetailVariableInstanceUpdateEntityImpl.getByteArrayRef()).thenReturn(byteArrayRef);

    ArrayList<HistoricDetailEntity> historicDetailEntityList = new ArrayList<>();
    historicDetailEntityList.add(historicDetailVariableInstanceUpdateEntityImpl);
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.findHistoricDetailsByTaskId(Mockito.<String>any()))
        .thenReturn(historicDetailEntityList);

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByTaskId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(byteArrayRef).delete();
    verify(historicDetailVariableInstanceUpdateEntityImpl, atLeast(1)).getByteArrayRef();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).findHistoricDetailsByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByTaskId_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.FULL));

    ArrayList<HistoricDetailEntity> historicDetailEntityList = new ArrayList<>();
    historicDetailEntityList.add(new HistoricDetailAssignmentEntityImpl());
    doNothing().when(historicDetailDataManager).delete(Mockito.<HistoricDetailEntity>any());
    when(historicDetailDataManager.findHistoricDetailsByTaskId(Mockito.<String>any()))
        .thenReturn(historicDetailEntityList);

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByTaskId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicDetailDataManager).delete(isA(HistoricDetailEntity.class));
    verify(historicDetailDataManager).findHistoricDetailsByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}.
   * <ul>
   *   <li>Then calls
   * {@link HistoricDetailDataManager#findHistoricDetailsByTaskId(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#deleteHistoricDetailsByTaskId(String)}
   */
  @Test
  public void testDeleteHistoricDetailsByTaskId_thenCallsFindHistoricDetailsByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.FULL));
    when(historicDetailDataManager.findHistoricDetailsByTaskId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    historicDetailEntityManagerImpl.deleteHistoricDetailsByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicDetailDataManager).findHistoricDetailsByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#findHistoricDetailsByNativeQuery(Map, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#findHistoricDetailsByNativeQuery(Map, int, int)}
   */
  @Test
  public void testFindHistoricDetailsByNativeQuery_thenReturnEmpty() {
    // Arrange
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    when(historicDetailDataManager.findHistoricDetailsByNativeQuery(Mockito.<Map<String, Object>>any(), anyInt(),
        anyInt())).thenReturn(new ArrayList<>());
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicDetailDataManager);

    // Act
    List<HistoricDetail> actualFindHistoricDetailsByNativeQueryResult = historicDetailEntityManagerImpl
        .findHistoricDetailsByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(historicDetailDataManager).findHistoricDetailsByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindHistoricDetailsByNativeQueryResult.isEmpty());
  }

  /**
   * Test
   * {@link HistoricDetailEntityManagerImpl#findHistoricDetailCountByNativeQuery(Map)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricDetailEntityManagerImpl#findHistoricDetailCountByNativeQuery(Map)}
   */
  @Test
  public void testFindHistoricDetailCountByNativeQuery_thenReturnThree() {
    // Arrange
    HistoricDetailDataManager historicDetailDataManager = mock(HistoricDetailDataManager.class);
    when(historicDetailDataManager.findHistoricDetailCountByNativeQuery(Mockito.<Map<String, Object>>any()))
        .thenReturn(3L);
    HistoricDetailEntityManagerImpl historicDetailEntityManagerImpl = new HistoricDetailEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicDetailDataManager);

    // Act
    long actualFindHistoricDetailCountByNativeQueryResult = historicDetailEntityManagerImpl
        .findHistoricDetailCountByNativeQuery(new HashMap<>());

    // Assert
    verify(historicDetailDataManager).findHistoricDetailCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindHistoricDetailCountByNativeQueryResult);
  }
}
