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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.ModelQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.ModelDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisModelDataManager;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.repository.Model;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ModelEntityManagerImplDiffblueTest {
  @Mock
  private ModelDataManager modelDataManager;

  @InjectMocks
  private ModelEntityManagerImpl modelEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ModelEntityManagerImpl#ModelEntityManagerImpl(ProcessEngineConfigurationImpl, ModelDataManager)}
   *   <li>{@link ModelEntityManagerImpl#setModelDataManager(ModelDataManager)}
   *   <li>{@link ModelEntityManagerImpl#getDataManager()}
   *   <li>{@link ModelEntityManagerImpl#getModelDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ModelEntityManagerImpl actualModelEntityManagerImpl = new ModelEntityManagerImpl(processEngineConfiguration,
        new MybatisModelDataManager(new JtaProcessEngineConfiguration()));
    MybatisModelDataManager modelDataManager = new MybatisModelDataManager(new JtaProcessEngineConfiguration());
    actualModelEntityManagerImpl.setModelDataManager(modelDataManager);
    DataManager<ModelEntity> actualDataManager = actualModelEntityManagerImpl.getDataManager();

    // Assert that nothing has changed
    assertSame(modelDataManager, actualDataManager);
    assertSame(modelDataManager, actualModelEntityManagerImpl.getModelDataManager());
  }

  /**
   * Test {@link ModelEntityManagerImpl#findById(String)}.
   * <p>
   * Method under test: {@link ModelEntityManagerImpl#findById(String)}
   */
  @Test
  public void testFindById() {
    // Arrange
    ModelEntityImpl modelEntityImpl = new ModelEntityImpl();
    when(modelDataManager.findById(Mockito.<String>any())).thenReturn(modelEntityImpl);

    // Act
    ModelEntity actualFindByIdResult = modelEntityManagerImpl.findById("42");

    // Assert
    verify(modelDataManager).findById(eq("42"));
    assertSame(modelEntityImpl, actualFindByIdResult);
  }

  /**
   * Test {@link ModelEntityManagerImpl#insert(ModelEntity)} with
   * {@code ModelEntity}.
   * <p>
   * Method under test: {@link ModelEntityManagerImpl#insert(ModelEntity)}
   */
  @Test
  public void testInsertWithModelEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ModelDataManager modelDataManager = mock(ModelDataManager.class);
    doNothing().when(modelDataManager).insert(Mockito.<ModelEntity>any());
    ModelEntityManagerImpl modelEntityManagerImpl = new ModelEntityManagerImpl(processEngineConfiguration,
        modelDataManager);
    ModelEntityImpl model = new ModelEntityImpl();

    // Act
    modelEntityManagerImpl.insert(model);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getClock();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(modelDataManager).insert(isA(ModelEntity.class));
    Object persistentState = model.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(10, ((Map<String, Object>) persistentState).size());
    assertTrue(((Map<String, Object>) persistentState).containsKey("category"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("deploymentId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("editorSourceValueId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("metaInfo"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("name"));
    Date expectedGetResult = model.getCreateTime();
    assertSame(expectedGetResult, ((Map<String, Object>) persistentState).get("createTime"));
  }

  /**
   * Test {@link ModelEntityManagerImpl#insert(ModelEntity)} with
   * {@code ModelEntity}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityManagerImpl#insert(ModelEntity)}
   */
  @Test
  public void testInsertWithModelEntity_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ModelDataManager modelDataManager = mock(ModelDataManager.class);
    doNothing().when(modelDataManager).insert(Mockito.<ModelEntity>any());
    ModelEntityManagerImpl modelEntityManagerImpl = new ModelEntityManagerImpl(processEngineConfiguration,
        modelDataManager);
    ModelEntityImpl model = new ModelEntityImpl();

    // Act
    modelEntityManagerImpl.insert(model);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(modelDataManager).insert(isA(ModelEntity.class));
    Object persistentState = model.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(10, ((Map<String, Object>) persistentState).size());
    assertTrue(((Map<String, Object>) persistentState).containsKey("category"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("deploymentId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("editorSourceValueId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("metaInfo"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("name"));
    Date expectedGetResult = model.getCreateTime();
    assertSame(expectedGetResult, ((Map<String, Object>) persistentState).get("createTime"));
  }

  /**
   * Test {@link ModelEntityManagerImpl#insert(ModelEntity)} with
   * {@code ModelEntity}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityManagerImpl#insert(ModelEntity)}
   */
  @Test
  public void testInsertWithModelEntity_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ModelDataManager modelDataManager = mock(ModelDataManager.class);
    doNothing().when(modelDataManager).insert(Mockito.<ModelEntity>any());
    ModelEntityManagerImpl modelEntityManagerImpl = new ModelEntityManagerImpl(processEngineConfiguration,
        modelDataManager);
    ModelEntityImpl model = new ModelEntityImpl();

    // Act
    modelEntityManagerImpl.insert(model);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getClock();
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(modelDataManager).insert(isA(ModelEntity.class));
    Object persistentState = model.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(10, ((Map<String, Object>) persistentState).size());
    assertTrue(((Map<String, Object>) persistentState).containsKey("category"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("deploymentId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("editorSourceValueId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("metaInfo"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("name"));
    Date expectedGetResult = model.getCreateTime();
    assertSame(expectedGetResult, ((Map<String, Object>) persistentState).get("createTime"));
  }

  /**
   * Test {@link ModelEntityManagerImpl#updateModel(ModelEntity)}.
   * <p>
   * Method under test: {@link ModelEntityManagerImpl#updateModel(ModelEntity)}
   */
  @Test
  public void testUpdateModel() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ModelDataManager modelDataManager = mock(ModelDataManager.class);
    when(modelDataManager.update(Mockito.<ModelEntity>any())).thenReturn(new ModelEntityImpl());
    ModelEntityManagerImpl modelEntityManagerImpl = new ModelEntityManagerImpl(processEngineConfiguration,
        modelDataManager);
    ModelEntityImpl updatedModel = new ModelEntityImpl();

    // Act
    modelEntityManagerImpl.updateModel(updatedModel);

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(modelDataManager).update(isA(ModelEntity.class));
    Object persistentState = updatedModel.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(10, ((Map<String, Object>) persistentState).size());
    assertTrue(((Map<String, Object>) persistentState).containsKey("category"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("createTime"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("deploymentId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("editorSourceValueId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("metaInfo"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("name"));
  }

  /**
   * Test {@link ModelEntityManagerImpl#updateModel(ModelEntity)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityManagerImpl#updateModel(ModelEntity)}
   */
  @Test
  public void testUpdateModel_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ModelDataManager modelDataManager = mock(ModelDataManager.class);
    when(modelDataManager.update(Mockito.<ModelEntity>any())).thenReturn(new ModelEntityImpl());
    ModelEntityManagerImpl modelEntityManagerImpl = new ModelEntityManagerImpl(processEngineConfiguration,
        modelDataManager);
    ModelEntityImpl updatedModel = new ModelEntityImpl();

    // Act
    modelEntityManagerImpl.updateModel(updatedModel);

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(modelDataManager).update(isA(ModelEntity.class));
    Object persistentState = updatedModel.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(10, ((Map<String, Object>) persistentState).size());
    assertTrue(((Map<String, Object>) persistentState).containsKey("category"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("createTime"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("deploymentId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("editorSourceValueId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("metaInfo"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("name"));
  }

  /**
   * Test {@link ModelEntityManagerImpl#updateModel(ModelEntity)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityManagerImpl#updateModel(ModelEntity)}
   */
  @Test
  public void testUpdateModel_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    ModelDataManager modelDataManager = mock(ModelDataManager.class);
    when(modelDataManager.update(Mockito.<ModelEntity>any())).thenReturn(new ModelEntityImpl());
    ModelEntityManagerImpl modelEntityManagerImpl = new ModelEntityManagerImpl(processEngineConfiguration,
        modelDataManager);
    ModelEntityImpl updatedModel = new ModelEntityImpl();

    // Act
    modelEntityManagerImpl.updateModel(updatedModel);

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(modelDataManager).update(isA(ModelEntity.class));
    Object persistentState = updatedModel.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(10, ((Map<String, Object>) persistentState).size());
    assertTrue(((Map<String, Object>) persistentState).containsKey("category"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("createTime"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("deploymentId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("editorSourceValueId"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("metaInfo"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("name"));
  }

  /**
   * Test {@link ModelEntityManagerImpl#delete(String)} with {@code modelId}.
   * <p>
   * Method under test: {@link ModelEntityManagerImpl#delete(String)}
   */
  @Test
  public void testDeleteWithModelId() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(modelDataManager.findById(Mockito.<String>any())).thenReturn(new ModelEntityImpl());
    doNothing().when(modelDataManager).delete(Mockito.<ModelEntity>any());

    // Act
    modelEntityManagerImpl.delete("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(modelDataManager).delete(isA(ModelEntity.class));
    verify(modelDataManager).findById(eq("42"));
  }

  /**
   * Test {@link ModelEntityManagerImpl#delete(String)} with {@code modelId}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityManagerImpl#delete(String)}
   */
  @Test
  public void testDeleteWithModelId_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(modelDataManager.findById(Mockito.<String>any())).thenReturn(new ModelEntityImpl());
    doNothing().when(modelDataManager).delete(Mockito.<ModelEntity>any());

    // Act
    modelEntityManagerImpl.delete("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(modelDataManager).delete(isA(ModelEntity.class));
    verify(modelDataManager).findById(eq("42"));
  }

  /**
   * Test {@link ModelEntityManagerImpl#delete(String)} with {@code modelId}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityManagerImpl#delete(String)}
   */
  @Test
  public void testDeleteWithModelId_thenCallsIsEnabled() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(modelDataManager.findById(Mockito.<String>any())).thenReturn(new ModelEntityImpl());
    doNothing().when(modelDataManager).delete(Mockito.<ModelEntity>any());

    // Act
    modelEntityManagerImpl.delete("42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(modelDataManager).delete(isA(ModelEntity.class));
    verify(modelDataManager).findById(eq("42"));
  }

  /**
   * Test
   * {@link ModelEntityManagerImpl#insertEditorSourceForModel(String, byte[])}.
   * <ul>
   *   <li>Then calls {@link DataManager#findById(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ModelEntityManagerImpl#insertEditorSourceForModel(String, byte[])}
   */
  @Test
  public void testInsertEditorSourceForModel_thenCallsFindById() throws UnsupportedEncodingException {
    // Arrange
    when(modelDataManager.findById(Mockito.<String>any())).thenReturn(null);

    // Act
    modelEntityManagerImpl.insertEditorSourceForModel("42", "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    verify(modelDataManager).findById(eq("42"));
  }

  /**
   * Test
   * {@link ModelEntityManagerImpl#insertEditorSourceExtraForModel(String, byte[])}.
   * <ul>
   *   <li>Then calls {@link DataManager#findById(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ModelEntityManagerImpl#insertEditorSourceExtraForModel(String, byte[])}
   */
  @Test
  public void testInsertEditorSourceExtraForModel_thenCallsFindById() throws UnsupportedEncodingException {
    // Arrange
    when(modelDataManager.findById(Mockito.<String>any())).thenReturn(null);

    // Act
    modelEntityManagerImpl.insertEditorSourceExtraForModel("42", "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    verify(modelDataManager).findById(eq("42"));
  }

  /**
   * Test
   * {@link ModelEntityManagerImpl#findModelsByQueryCriteria(ModelQueryImpl, Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ModelEntityManagerImpl#findModelsByQueryCriteria(ModelQueryImpl, Page)}
   */
  @Test
  public void testFindModelsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    ModelDataManager modelDataManager = mock(ModelDataManager.class);
    when(modelDataManager.findModelsByQueryCriteria(Mockito.<ModelQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    ModelEntityManagerImpl modelEntityManagerImpl = new ModelEntityManagerImpl(new JtaProcessEngineConfiguration(),
        modelDataManager);
    ModelQueryImpl query = new ModelQueryImpl();

    // Act
    List<Model> actualFindModelsByQueryCriteriaResult = modelEntityManagerImpl.findModelsByQueryCriteria(query,
        new Page(1, 3));

    // Assert
    verify(modelDataManager).findModelsByQueryCriteria(isA(ModelQueryImpl.class), isA(Page.class));
    assertTrue(actualFindModelsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test
   * {@link ModelEntityManagerImpl#findModelCountByQueryCriteria(ModelQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ModelEntityManagerImpl#findModelCountByQueryCriteria(ModelQueryImpl)}
   */
  @Test
  public void testFindModelCountByQueryCriteria_thenReturnThree() {
    // Arrange
    ModelDataManager modelDataManager = mock(ModelDataManager.class);
    when(modelDataManager.findModelCountByQueryCriteria(Mockito.<ModelQueryImpl>any())).thenReturn(3L);
    ModelEntityManagerImpl modelEntityManagerImpl = new ModelEntityManagerImpl(new JtaProcessEngineConfiguration(),
        modelDataManager);

    // Act
    long actualFindModelCountByQueryCriteriaResult = modelEntityManagerImpl
        .findModelCountByQueryCriteria(new ModelQueryImpl());

    // Assert
    verify(modelDataManager).findModelCountByQueryCriteria(isA(ModelQueryImpl.class));
    assertEquals(3L, actualFindModelCountByQueryCriteriaResult);
  }

  /**
   * Test {@link ModelEntityManagerImpl#findEditorSourceByModelId(String)}.
   * <ul>
   *   <li>Given {@link ModelDataManager} {@link DataManager#findById(String)}
   * return {@link ModelEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ModelEntityManagerImpl#findEditorSourceByModelId(String)}
   */
  @Test
  public void testFindEditorSourceByModelId_givenModelDataManagerFindByIdReturnModelEntityImpl() {
    // Arrange
    when(modelDataManager.findById(Mockito.<String>any())).thenReturn(new ModelEntityImpl());

    // Act
    byte[] actualFindEditorSourceByModelIdResult = modelEntityManagerImpl.findEditorSourceByModelId("42");

    // Assert
    verify(modelDataManager).findById(eq("42"));
    assertNull(actualFindEditorSourceByModelIdResult);
  }

  /**
   * Test {@link ModelEntityManagerImpl#findEditorSourceByModelId(String)}.
   * <ul>
   *   <li>Given {@link ModelDataManager} {@link DataManager#findById(String)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ModelEntityManagerImpl#findEditorSourceByModelId(String)}
   */
  @Test
  public void testFindEditorSourceByModelId_givenModelDataManagerFindByIdReturnNull() {
    // Arrange
    when(modelDataManager.findById(Mockito.<String>any())).thenReturn(null);

    // Act
    byte[] actualFindEditorSourceByModelIdResult = modelEntityManagerImpl.findEditorSourceByModelId("42");

    // Assert
    verify(modelDataManager).findById(eq("42"));
    assertNull(actualFindEditorSourceByModelIdResult);
  }

  /**
   * Test {@link ModelEntityManagerImpl#findEditorSourceExtraByModelId(String)}.
   * <p>
   * Method under test:
   * {@link ModelEntityManagerImpl#findEditorSourceExtraByModelId(String)}
   */
  @Test
  public void testFindEditorSourceExtraByModelId() {
    // Arrange
    when(modelDataManager.findById(Mockito.<String>any())).thenReturn(new ModelEntityImpl());

    // Act
    byte[] actualFindEditorSourceExtraByModelIdResult = modelEntityManagerImpl.findEditorSourceExtraByModelId("42");

    // Assert
    verify(modelDataManager).findById(eq("42"));
    assertNull(actualFindEditorSourceExtraByModelIdResult);
  }

  /**
   * Test {@link ModelEntityManagerImpl#findEditorSourceExtraByModelId(String)}.
   * <ul>
   *   <li>Given {@link ModelDataManager} {@link DataManager#findById(String)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ModelEntityManagerImpl#findEditorSourceExtraByModelId(String)}
   */
  @Test
  public void testFindEditorSourceExtraByModelId_givenModelDataManagerFindByIdReturnNull() {
    // Arrange
    when(modelDataManager.findById(Mockito.<String>any())).thenReturn(null);

    // Act
    byte[] actualFindEditorSourceExtraByModelIdResult = modelEntityManagerImpl.findEditorSourceExtraByModelId("42");

    // Assert
    verify(modelDataManager).findById(eq("42"));
    assertNull(actualFindEditorSourceExtraByModelIdResult);
  }

  /**
   * Test {@link ModelEntityManagerImpl#findModelsByNativeQuery(Map, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ModelEntityManagerImpl#findModelsByNativeQuery(Map, int, int)}
   */
  @Test
  public void testFindModelsByNativeQuery_thenReturnEmpty() {
    // Arrange
    ModelDataManager modelDataManager = mock(ModelDataManager.class);
    when(modelDataManager.findModelsByNativeQuery(Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    ModelEntityManagerImpl modelEntityManagerImpl = new ModelEntityManagerImpl(new JtaProcessEngineConfiguration(),
        modelDataManager);

    // Act
    List<Model> actualFindModelsByNativeQueryResult = modelEntityManagerImpl.findModelsByNativeQuery(new HashMap<>(), 1,
        3);

    // Assert
    verify(modelDataManager).findModelsByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindModelsByNativeQueryResult.isEmpty());
  }

  /**
   * Test {@link ModelEntityManagerImpl#findModelCountByNativeQuery(Map)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ModelEntityManagerImpl#findModelCountByNativeQuery(Map)}
   */
  @Test
  public void testFindModelCountByNativeQuery_thenReturnThree() {
    // Arrange
    ModelDataManager modelDataManager = mock(ModelDataManager.class);
    when(modelDataManager.findModelCountByNativeQuery(Mockito.<Map<String, Object>>any())).thenReturn(3L);
    ModelEntityManagerImpl modelEntityManagerImpl = new ModelEntityManagerImpl(new JtaProcessEngineConfiguration(),
        modelDataManager);

    // Act
    long actualFindModelCountByNativeQueryResult = modelEntityManagerImpl.findModelCountByNativeQuery(new HashMap<>());

    // Assert
    verify(modelDataManager).findModelCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindModelCountByNativeQueryResult);
  }
}
