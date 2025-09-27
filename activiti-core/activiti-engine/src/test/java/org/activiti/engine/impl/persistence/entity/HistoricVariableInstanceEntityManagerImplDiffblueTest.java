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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.HistoricVariableInstanceQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricVariableInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricVariableInstanceDataManager;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricVariableInstanceEntityManagerImplDiffblueTest {
  @Mock private HistoricVariableInstanceDataManager historicVariableInstanceDataManager;

  @InjectMocks
  private HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl;

  @Mock private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       HistoricVariableInstanceEntityManagerImpl#HistoricVariableInstanceEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       HistoricVariableInstanceDataManager)}
   *   <li>{@link
   *       HistoricVariableInstanceEntityManagerImpl#setHistoricVariableInstanceDataManager(HistoricVariableInstanceDataManager)}
   *   <li>{@link HistoricVariableInstanceEntityManagerImpl#getDataManager()}
   *   <li>{@link
   *       HistoricVariableInstanceEntityManagerImpl#getHistoricVariableInstanceDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, HistoricVariableInstanceDataManager)",
    "DataManager HistoricVariableInstanceEntityManagerImpl.getDataManager()",
    "HistoricVariableInstanceDataManager HistoricVariableInstanceEntityManagerImpl.getHistoricVariableInstanceDataManager()",
    "void HistoricVariableInstanceEntityManagerImpl.setHistoricVariableInstanceDataManager(HistoricVariableInstanceDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    HistoricVariableInstanceEntityManagerImpl actualHistoricVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    MybatisHistoricVariableInstanceDataManager historicVariableInstanceDataManager =
        new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration());
    actualHistoricVariableInstanceEntityManagerImpl.setHistoricVariableInstanceDataManager(
        historicVariableInstanceDataManager);
    DataManager<HistoricVariableInstanceEntity> actualDataManager =
        actualHistoricVariableInstanceEntityManagerImpl.getDataManager();

    // Assert
    assertSame(historicVariableInstanceDataManager, actualDataManager);
    assertSame(
        historicVariableInstanceDataManager,
        actualHistoricVariableInstanceEntityManagerImpl.getHistoricVariableInstanceDataManager());
  }

  /**
   * Test {@link HistoricVariableInstanceEntityManagerImpl#copyAndInsert(VariableInstanceEntity)}.
   *
   * <ul>
   *   <li>Then return {@link HistoricVariableInstanceEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#copyAndInsert(VariableInstanceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceEntity HistoricVariableInstanceEntityManagerImpl.copyAndInsert(VariableInstanceEntity)"
  })
  public void testCopyAndInsert_thenReturnHistoricVariableInstanceEntityImpl() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());
    processEngineConfiguration.setClock(new DefaultClockImpl());

    HistoricVariableInstanceDataManager historicVariableInstanceDataManager =
        mock(HistoricVariableInstanceDataManager.class);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .insert(Mockito.<HistoricVariableInstanceEntity>any());
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl =
        new HistoricVariableInstanceEntityImpl();
    when(historicVariableInstanceDataManager.create())
        .thenReturn(historicVariableInstanceEntityImpl);

    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            processEngineConfiguration, historicVariableInstanceDataManager);

    // Act
    HistoricVariableInstanceEntity actualCopyAndInsertResult =
        historicVariableInstanceEntityManagerImpl.copyAndInsert(new VariableInstanceEntityImpl());

    // Assert
    verify(historicVariableInstanceDataManager).create();
    verify(historicVariableInstanceDataManager).insert(isA(HistoricVariableInstanceEntity.class));
    assertSame(historicVariableInstanceEntityImpl, actualCopyAndInsertResult);
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#copyVariableValue(HistoricVariableInstanceEntity,
   * VariableInstanceEntity)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#copyVariableValue(HistoricVariableInstanceEntity,
   * VariableInstanceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.copyVariableValue(HistoricVariableInstanceEntity, VariableInstanceEntity)"
  })
  public void testCopyVariableValue() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setClock(new DefaultClockImpl());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    HistoricVariableInstanceEntityImpl historicVariableInstance =
        new HistoricVariableInstanceEntityImpl();

    // Act
    historicVariableInstanceEntityManagerImpl.copyVariableValue(
        historicVariableInstance, new VariableInstanceEntityImpl());

    // Assert
    Object persistentState = historicVariableInstance.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(6, ((Map<String, Date>) persistentState).size());
    assertTrue(((Map<String, Date>) persistentState).containsKey("createTime"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("doubleValue"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("longValue"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("textValue"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("textValue2"));
    assertSame(
        historicVariableInstance.getLastUpdatedTime(),
        ((Map<String, Date>) persistentState).get("lastUpdatedTime"));
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#copyVariableValue(HistoricVariableInstanceEntity,
   * VariableInstanceEntity)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#copyVariableValue(HistoricVariableInstanceEntity,
   * VariableInstanceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.copyVariableValue(HistoricVariableInstanceEntity, VariableInstanceEntity)"
  })
  public void testCopyVariableValue2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setClock(new DefaultClockImpl());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    HistoricVariableInstanceEntityImpl historicVariableInstance =
        new HistoricVariableInstanceEntityImpl();

    VariableInstanceEntityImpl variableInstance = new VariableInstanceEntityImpl();
    variableInstance.setBytes(null);

    // Act
    historicVariableInstanceEntityManagerImpl.copyVariableValue(
        historicVariableInstance, variableInstance);

    // Assert
    Object persistentState = historicVariableInstance.getPersistentState();
    assertTrue(persistentState instanceof Map);
    ByteArrayRef byteArrayRef = historicVariableInstance.getByteArrayRef();
    assertEquals("hist.var-null", byteArrayRef.getName());
    assertNull(byteArrayRef.getBytes());
    assertNull(byteArrayRef.getId());
    assertEquals(7, ((Map<String, Date>) persistentState).size());
    assertNull(((Map<String, Date>) persistentState).get("byteArrayRef"));
    assertNull(byteArrayRef.getEntity());
    assertFalse(byteArrayRef.isDeleted());
    assertTrue(((Map<String, Date>) persistentState).containsKey("createTime"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("doubleValue"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("lastUpdatedTime"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("longValue"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("textValue"));
  }

  /**
   * Test {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity,
   * boolean)} with {@code HistoricVariableInstanceEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Given {@link ByteArrayRef#ByteArrayRef()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.delete(HistoricVariableInstanceEntity, boolean)"
  })
  public void testDeleteWithHistoricVariableInstanceEntityBoolean_givenByteArrayRef() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    HistoricVariableInstanceDataManager historicVariableInstanceDataManager =
        mock(HistoricVariableInstanceDataManager.class);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());

    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            processEngineConfiguration, historicVariableInstanceDataManager);

    HistoricVariableInstanceEntity entity = mock(HistoricVariableInstanceEntity.class);
    when(entity.getByteArrayRef()).thenReturn(new ByteArrayRef());

    // Act
    historicVariableInstanceEntityManagerImpl.delete(entity, true);

    // Assert
    verify(entity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
  }

  /**
   * Test {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity,
   * boolean)} with {@code HistoricVariableInstanceEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>Then calls {@link MybatisHistoricVariableInstanceDataManager#delete(Entity)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.delete(HistoricVariableInstanceEntity, boolean)"
  })
  public void testDeleteWithHistoricVariableInstanceEntityBoolean_givenNull_thenCallsDelete() {
    // Arrange
    MybatisHistoricVariableInstanceDataManager historicVariableInstanceDataManager =
        mock(MybatisHistoricVariableInstanceDataManager.class);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicVariableInstanceDataManager);

    HistoricVariableInstanceEntityImpl entity = new HistoricVariableInstanceEntityImpl();
    entity.setCachedValue(JSONObject.NULL);
    entity.setCreateTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    entity.setDeleted(true);
    entity.setDoubleValue(10.0d);
    entity.setExecutionId("42");
    entity.setId("42");
    entity.setInserted(true);
    entity.setLastUpdatedTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    entity.setLongValue(42L);
    entity.setName("Name");
    entity.setProcessInstanceId("42");
    entity.setRevision(1);
    entity.setTaskId("42");
    entity.setTextValue("42");
    entity.setTextValue2("42");
    entity.setUpdated(true);
    entity.setVariableType(new BigDecimalType());
    entity.setBytes(null);

    // Act
    historicVariableInstanceEntityManagerImpl.delete(entity, false);

    // Assert
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
  }

  /**
   * Test {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity,
   * boolean)} with {@code HistoricVariableInstanceEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricVariableInstanceDataManager#delete(Entity)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.delete(HistoricVariableInstanceEntity, boolean)"
  })
  public void testDeleteWithHistoricVariableInstanceEntityBoolean_thenCallsDelete() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    HistoricVariableInstanceDataManager historicVariableInstanceDataManager =
        mock(HistoricVariableInstanceDataManager.class);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());

    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            processEngineConfiguration, historicVariableInstanceDataManager);

    // Act
    historicVariableInstanceEntityManagerImpl.delete(
        new HistoricVariableInstanceEntityImpl(), true);

    // Assert
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
  }

  /**
   * Test {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity,
   * boolean)} with {@code HistoricVariableInstanceEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ByteArrayRef#delete()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.delete(HistoricVariableInstanceEntity, boolean)"
  })
  public void testDeleteWithHistoricVariableInstanceEntityBoolean_thenCallsDelete2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    HistoricVariableInstanceDataManager historicVariableInstanceDataManager =
        mock(HistoricVariableInstanceDataManager.class);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());

    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            processEngineConfiguration, historicVariableInstanceDataManager);

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    HistoricVariableInstanceEntity entity = mock(HistoricVariableInstanceEntity.class);
    when(entity.getByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    historicVariableInstanceEntityManagerImpl.delete(entity, true);

    // Assert
    verify(byteArrayRef).delete();
    verify(entity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
  }

  /**
   * Test {@link HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity,
   * boolean)} with {@code HistoricVariableInstanceEntity}, {@code boolean}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#delete(HistoricVariableInstanceEntity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.delete(HistoricVariableInstanceEntity, boolean)"
  })
  public void testDeleteWithHistoricVariableInstanceEntityBoolean_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    HistoricVariableInstanceDataManager historicVariableInstanceDataManager =
        mock(HistoricVariableInstanceDataManager.class);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());

    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            processEngineConfiguration, historicVariableInstanceDataManager);

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    HistoricVariableInstanceEntity entity = mock(HistoricVariableInstanceEntity.class);
    when(entity.getByteArrayRef()).thenReturn(byteArrayRef);

    // Act
    historicVariableInstanceEntityManagerImpl.delete(entity, true);

    // Assert
    verify(eventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(eventDispatcher).isEnabled();
    verify(byteArrayRef).delete();
    verify(entity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(String)"
  })
  public void testDeleteHistoricVariableInstanceByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(
        "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(String)"
  })
  public void testDeleteHistoricVariableInstanceByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(
            Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(
        "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstancesByProcessInstanceId("42");
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(String)"
  })
  public void testDeleteHistoricVariableInstanceByProcessInstanceId3() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList =
        new ArrayList<>();
    historicVariableInstanceEntityList.add(new HistoricVariableInstanceEntityImpl());
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(
            Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(
        "42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstancesByProcessInstanceId("42");
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(String)"
  })
  public void testDeleteHistoricVariableInstanceByProcessInstanceId4() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getByteArrayRef()).thenReturn(new ByteArrayRef());

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList =
        new ArrayList<>();
    historicVariableInstanceEntityList.add(historicVariableInstanceEntity);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(
            Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(
        "42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceEntity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstancesByProcessInstanceId("42");
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(String)"
  })
  public void testDeleteHistoricVariableInstanceByProcessInstanceId5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getByteArrayRef()).thenReturn(byteArrayRef);

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList =
        new ArrayList<>();
    historicVariableInstanceEntityList.add(historicVariableInstanceEntity);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(
            Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(
        "42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(byteArrayRef).delete();
    verify(historicVariableInstanceEntity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstancesByProcessInstanceId("42");
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ByteArrayRef#delete()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(String)"
  })
  public void testDeleteHistoricVariableInstanceByProcessInstanceId_thenCallsDelete() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getByteArrayRef()).thenReturn(byteArrayRef);

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList =
        new ArrayList<>();
    historicVariableInstanceEntityList.add(historicVariableInstanceEntity);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(
            Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(
        "42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(byteArrayRef).delete();
    verify(historicVariableInstanceEntity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstancesByProcessInstanceId("42");
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstanceByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(String)"
  })
  public void testDeleteHistoricVariableInstanceByProcessInstanceId_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getByteArrayRef()).thenReturn(byteArrayRef);

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList =
        new ArrayList<>();
    historicVariableInstanceEntityList.add(historicVariableInstanceEntity);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByProcessInstanceId(
            Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstanceByProcessInstanceId(
        "42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(byteArrayRef).delete();
    verify(historicVariableInstanceEntity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstancesByProcessInstanceId("42");
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstanceCountByQueryCriteria(HistoricVariableInstanceQueryImpl)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstanceCountByQueryCriteria(HistoricVariableInstanceQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long HistoricVariableInstanceEntityManagerImpl.findHistoricVariableInstanceCountByQueryCriteria(HistoricVariableInstanceQueryImpl)"
  })
  public void testFindHistoricVariableInstanceCountByQueryCriteria_thenReturnThree() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager =
        mock(HistoricVariableInstanceDataManager.class);
    when(historicVariableInstanceDataManager.findHistoricVariableInstanceCountByQueryCriteria(
            Mockito.<HistoricVariableInstanceQueryImpl>any()))
        .thenReturn(3L);
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicVariableInstanceDataManager);

    // Act
    long actualFindHistoricVariableInstanceCountByQueryCriteriaResult =
        historicVariableInstanceEntityManagerImpl.findHistoricVariableInstanceCountByQueryCriteria(
            new HistoricVariableInstanceQueryImpl());

    // Assert
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstanceCountByQueryCriteria(
            isA(HistoricVariableInstanceQueryImpl.class));
    assertEquals(3L, actualFindHistoricVariableInstanceCountByQueryCriteriaResult);
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstancesByQueryCriteria(HistoricVariableInstanceQueryImpl,
   * Page)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstancesByQueryCriteria(HistoricVariableInstanceQueryImpl,
   * Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HistoricVariableInstanceEntityManagerImpl.findHistoricVariableInstancesByQueryCriteria(HistoricVariableInstanceQueryImpl, Page)"
  })
  public void testFindHistoricVariableInstancesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager =
        mock(HistoricVariableInstanceDataManager.class);
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByQueryCriteria(
            Mockito.<HistoricVariableInstanceQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicVariableInstanceDataManager);
    HistoricVariableInstanceQueryImpl historicProcessVariableQuery =
        new HistoricVariableInstanceQueryImpl();

    // Act
    List<HistoricVariableInstance> actualFindHistoricVariableInstancesByQueryCriteriaResult =
        historicVariableInstanceEntityManagerImpl.findHistoricVariableInstancesByQueryCriteria(
            historicProcessVariableQuery, new Page(1, 3));

    // Assert
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstancesByQueryCriteria(
            isA(HistoricVariableInstanceQueryImpl.class), isA(Page.class));
    assertTrue(actualFindHistoricVariableInstancesByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstanceByVariableInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstanceByVariableInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceEntity HistoricVariableInstanceEntityManagerImpl.findHistoricVariableInstanceByVariableInstanceId(String)"
  })
  public void testFindHistoricVariableInstanceByVariableInstanceId() {
    // Arrange
    HistoricVariableInstanceEntityImpl historicVariableInstanceEntityImpl =
        new HistoricVariableInstanceEntityImpl();
    when(historicVariableInstanceDataManager.findHistoricVariableInstanceByVariableInstanceId(
            Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityImpl);

    // Act
    HistoricVariableInstanceEntity actualFindHistoricVariableInstanceByVariableInstanceIdResult =
        historicVariableInstanceEntityManagerImpl.findHistoricVariableInstanceByVariableInstanceId(
            "42");

    // Assert
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstanceByVariableInstanceId("42");
    assertSame(
        historicVariableInstanceEntityImpl,
        actualFindHistoricVariableInstanceByVariableInstanceIdResult);
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId(String)"
  })
  public void testDeleteHistoricVariableInstancesByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId(String)"
  })
  public void testDeleteHistoricVariableInstancesByTaskId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(
            Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId("42");
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId(String)"
  })
  public void testDeleteHistoricVariableInstancesByTaskId3() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList =
        new ArrayList<>();
    historicVariableInstanceEntityList.add(new HistoricVariableInstanceEntityImpl());
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(
            Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId("42");
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId(String)"
  })
  public void testDeleteHistoricVariableInstancesByTaskId4() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getByteArrayRef()).thenReturn(new ByteArrayRef());

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList =
        new ArrayList<>();
    historicVariableInstanceEntityList.add(historicVariableInstanceEntity);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(
            Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historicVariableInstanceEntity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId("42");
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId(String)"
  })
  public void testDeleteHistoricVariableInstancesByTaskId5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getByteArrayRef()).thenReturn(byteArrayRef);

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList =
        new ArrayList<>();
    historicVariableInstanceEntityList.add(historicVariableInstanceEntity);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(
            Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(byteArrayRef).delete();
    verify(historicVariableInstanceEntity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId("42");
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ByteArrayRef#delete()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId(String)"
  })
  public void testDeleteHistoricVariableInstancesByTaskId_thenCallsDelete() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getByteArrayRef()).thenReturn(byteArrayRef);

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList =
        new ArrayList<>();
    historicVariableInstanceEntityList.add(historicVariableInstanceEntity);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(
            Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(byteArrayRef).delete();
    verify(historicVariableInstanceEntity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId("42");
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#deleteHistoricVariableInstancesByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HistoricVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId(String)"
  })
  public void testDeleteHistoricVariableInstancesByTaskId_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));

    ByteArrayRef byteArrayRef = mock(ByteArrayRef.class);
    doNothing().when(byteArrayRef).delete();

    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getByteArrayRef()).thenReturn(byteArrayRef);

    ArrayList<HistoricVariableInstanceEntity> historicVariableInstanceEntityList =
        new ArrayList<>();
    historicVariableInstanceEntityList.add(historicVariableInstanceEntity);
    doNothing()
        .when(historicVariableInstanceDataManager)
        .delete(Mockito.<HistoricVariableInstanceEntity>any());
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByTaskId(
            Mockito.<String>any()))
        .thenReturn(historicVariableInstanceEntityList);

    // Act
    historicVariableInstanceEntityManagerImpl.deleteHistoricVariableInstancesByTaskId("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(byteArrayRef).delete();
    verify(historicVariableInstanceEntity, atLeast(1)).getByteArrayRef();
    verify(historicVariableInstanceDataManager).delete(isA(HistoricVariableInstanceEntity.class));
    verify(historicVariableInstanceDataManager).findHistoricVariableInstancesByTaskId("42");
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstancesByNativeQuery(Map, int,
   * int)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstancesByNativeQuery(Map, int,
   * int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List HistoricVariableInstanceEntityManagerImpl.findHistoricVariableInstancesByNativeQuery(Map, int, int)"
  })
  public void testFindHistoricVariableInstancesByNativeQuery_thenReturnEmpty() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager =
        mock(HistoricVariableInstanceDataManager.class);
    when(historicVariableInstanceDataManager.findHistoricVariableInstancesByNativeQuery(
            Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicVariableInstanceDataManager);

    // Act
    List<HistoricVariableInstance> actualFindHistoricVariableInstancesByNativeQueryResult =
        historicVariableInstanceEntityManagerImpl.findHistoricVariableInstancesByNativeQuery(
            new HashMap<>(), 1, 3);

    // Assert
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstancesByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindHistoricVariableInstancesByNativeQueryResult.isEmpty());
  }

  /**
   * Test {@link
   * HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstanceCountByNativeQuery(Map)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceEntityManagerImpl#findHistoricVariableInstanceCountByNativeQuery(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long HistoricVariableInstanceEntityManagerImpl.findHistoricVariableInstanceCountByNativeQuery(Map)"
  })
  public void testFindHistoricVariableInstanceCountByNativeQuery_thenReturnThree() {
    // Arrange
    HistoricVariableInstanceDataManager historicVariableInstanceDataManager =
        mock(HistoricVariableInstanceDataManager.class);
    when(historicVariableInstanceDataManager.findHistoricVariableInstanceCountByNativeQuery(
            Mockito.<Map<String, Object>>any()))
        .thenReturn(3L);
    HistoricVariableInstanceEntityManagerImpl historicVariableInstanceEntityManagerImpl =
        new HistoricVariableInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicVariableInstanceDataManager);

    // Act
    long actualFindHistoricVariableInstanceCountByNativeQueryResult =
        historicVariableInstanceEntityManagerImpl.findHistoricVariableInstanceCountByNativeQuery(
            new HashMap<>());

    // Assert
    verify(historicVariableInstanceDataManager)
        .findHistoricVariableInstanceCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindHistoricVariableInstanceCountByNativeQueryResult);
  }
}
