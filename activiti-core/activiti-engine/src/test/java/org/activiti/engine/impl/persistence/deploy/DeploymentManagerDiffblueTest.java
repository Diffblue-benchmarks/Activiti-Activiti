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
package org.activiti.engine.impl.persistence.deploy;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityManager;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.DeploymentDataManager;
import org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeploymentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionDataManager;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DeploymentManagerDiffblueTest {
  /**
   * Test {@link DeploymentManager#deploy(DeploymentEntity, Map)} with {@code deployment}, {@code deploymentSettings}.
   * <p>
   * Method under test: {@link DeploymentManager#deploy(DeploymentEntity, Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DeploymentManager.deploy(DeploymentEntity, Map)"})
  public void testDeployWithDeploymentDeploymentSettings() {
    // Arrange
    Deployer deployer = mock(Deployer.class);
    doThrow(new ActivitiIllegalArgumentException("An error occurred")).when(deployer)
        .deploy(Mockito.<DeploymentEntity>any(), Mockito.<Map<String, Object>>any());

    ArrayList<Deployer> deployers = new ArrayList<>();
    deployers.add(deployer);
    deployers.add(mock(Deployer.class));

    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setDeployers(deployers);
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentManager.deploy(deployment, new HashMap<>()));
    verify(deployer).deploy(isA(DeploymentEntity.class), isA(Map.class));
  }

  /**
   * Test {@link DeploymentManager#deploy(DeploymentEntity, Map)} with {@code deployment}, {@code deploymentSettings}.
   * <ul>
   *   <li>Given {@link Deployer} {@link Deployer#deploy(DeploymentEntity, Map)} does nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentManager#deploy(DeploymentEntity, Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DeploymentManager.deploy(DeploymentEntity, Map)"})
  public void testDeployWithDeploymentDeploymentSettings_givenDeployerDeployDoesNothing() {
    // Arrange
    Deployer deployer = mock(Deployer.class);
    doNothing().when(deployer).deploy(Mockito.<DeploymentEntity>any(), Mockito.<Map<String, Object>>any());

    ArrayList<Deployer> deployers = new ArrayList<>();
    deployers.add(deployer);

    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setDeployers(deployers);
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();

    // Act
    deploymentManager.deploy(deployment, new HashMap<>());

    // Assert
    verify(deployer).deploy(isA(DeploymentEntity.class), isA(Map.class));
  }

  /**
   * Test {@link DeploymentManager#deploy(DeploymentEntity)} with {@code deployment}.
   * <ul>
   *   <li>Given {@link Deployer} {@link Deployer#deploy(DeploymentEntity, Map)} does nothing.</li>
   *   <li>Then calls {@link Deployer#deploy(DeploymentEntity, Map)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentManager#deploy(DeploymentEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DeploymentManager.deploy(DeploymentEntity)"})
  public void testDeployWithDeployment_givenDeployerDeployDoesNothing_thenCallsDeploy() {
    // Arrange
    Deployer deployer = mock(Deployer.class);
    doNothing().when(deployer).deploy(Mockito.<DeploymentEntity>any(), Mockito.<Map<String, Object>>any());

    ArrayList<Deployer> deployers = new ArrayList<>();
    deployers.add(deployer);

    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setDeployers(deployers);

    // Act
    deploymentManager.deploy(new DeploymentEntityImpl());

    // Assert
    verify(deployer).deploy(isA(DeploymentEntity.class), isNull());
  }

  /**
   * Test {@link DeploymentManager#deploy(DeploymentEntity)} with {@code deployment}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentManager#deploy(DeploymentEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DeploymentManager.deploy(DeploymentEntity)"})
  public void testDeployWithDeployment_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    Deployer deployer = mock(Deployer.class);
    doThrow(new ActivitiIllegalArgumentException("An error occurred")).when(deployer)
        .deploy(Mockito.<DeploymentEntity>any(), Mockito.<Map<String, Object>>any());

    ArrayList<Deployer> deployers = new ArrayList<>();
    deployers.add(deployer);
    deployers.add(mock(Deployer.class));

    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setDeployers(deployers);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentManager.deploy(new DeploymentEntityImpl()));
    verify(deployer).deploy(isA(DeploymentEntity.class), isNull());
  }

  /**
   * Test {@link DeploymentManager#findDeployedProcessDefinitionById(String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Deployer}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentManager#findDeployedProcessDefinitionById(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinition DeploymentManager.findDeployedProcessDefinitionById(String)"})
  public void testFindDeployedProcessDefinitionById_givenArrayListAddDeployer_whenNull() {
    // Arrange
    ArrayList<Deployer> deployers = new ArrayList<>();
    deployers.add(mock(Deployer.class));

    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setDeployers(deployers);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> deploymentManager.findDeployedProcessDefinitionById(null));
  }

  /**
   * Test {@link DeploymentManager#findDeployedProcessDefinitionById(String)}.
   * <ul>
   *   <li>Then calls {@link ProcessDefinitionCacheEntry#getProcessDefinition()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentManager#findDeployedProcessDefinitionById(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinition DeploymentManager.findDeployedProcessDefinitionById(String)"})
  public void testFindDeployedProcessDefinitionById_thenCallsGetProcessDefinition() {
    // Arrange
    ProcessDefinitionCacheEntry processDefinitionCacheEntry = mock(ProcessDefinitionCacheEntry.class);
    when(processDefinitionCacheEntry.getProcessDefinition())
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    DefaultDeploymentCache<ProcessDefinitionCacheEntry> processDefinitionCache = new DefaultDeploymentCache<>();
    processDefinitionCache.add("42", processDefinitionCacheEntry);

    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setProcessDefinitionCache(processDefinitionCache);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> deploymentManager.findDeployedProcessDefinitionById("42"));
    verify(processDefinitionCacheEntry).getProcessDefinition();
  }

  /**
   * Test {@link DeploymentManager#findDeployedProcessDefinitionById(String)}.
   * <ul>
   *   <li>Then return {@link ProcessDefinitionEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentManager#findDeployedProcessDefinitionById(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinition DeploymentManager.findDeployedProcessDefinitionById(String)"})
  public void testFindDeployedProcessDefinitionById_thenReturnProcessDefinitionEntityImpl() {
    // Arrange
    DefaultDeploymentCache<ProcessDefinitionCacheEntry> processDefinitionCache = new DefaultDeploymentCache<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    BpmnModel bpmnModel = new BpmnModel();
    processDefinitionCache.add("42", new ProcessDefinitionCacheEntry(processDefinition, bpmnModel, new Process()));

    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setProcessDefinitionCache(processDefinitionCache);

    // Act and Assert
    assertSame(processDefinition, deploymentManager.findDeployedProcessDefinitionById("42"));
  }

  /**
   * Test {@link DeploymentManager#findDeployedLatestProcessDefinitionByKey(String)}.
   * <p>
   * Method under test: {@link DeploymentManager#findDeployedLatestProcessDefinitionByKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinition DeploymentManager.findDeployedLatestProcessDefinitionByKey(String)"})
  public void testFindDeployedLatestProcessDefinitionByKey() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findLatestProcessDefinitionByKey(Mockito.<String>any())).thenReturn(null);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManager = new ProcessDefinitionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setProcessDefinitionEntityManager(processDefinitionEntityManager);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> deploymentManager.findDeployedLatestProcessDefinitionByKey("Process Definition Key"));
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey(eq("Process Definition Key"));
  }

  /**
   * Test {@link DeploymentManager#findDeployedLatestProcessDefinitionByKeyAndTenantId(String, String)}.
   * <p>
   * Method under test: {@link DeploymentManager#findDeployedLatestProcessDefinitionByKeyAndTenantId(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinition DeploymentManager.findDeployedLatestProcessDefinitionByKeyAndTenantId(String, String)"})
  public void testFindDeployedLatestProcessDefinitionByKeyAndTenantId() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findLatestProcessDefinitionByKeyAndTenantId(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(null);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManager = new ProcessDefinitionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setProcessDefinitionEntityManager(processDefinitionEntityManager);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> deploymentManager.findDeployedLatestProcessDefinitionByKeyAndTenantId("Process Definition Key", "42"));
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKeyAndTenantId(eq("Process Definition Key"),
        eq("42"));
  }

  /**
   * Test {@link DeploymentManager#findDeployedProcessDefinitionByKeyAndVersionAndTenantId(String, Integer, String)}.
   * <p>
   * Method under test: {@link DeploymentManager#findDeployedProcessDefinitionByKeyAndVersionAndTenantId(String, Integer, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinition DeploymentManager.findDeployedProcessDefinitionByKeyAndVersionAndTenantId(String, Integer, String)"})
  public void testFindDeployedProcessDefinitionByKeyAndVersionAndTenantId() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionByKeyAndVersionAndTenantId(Mockito.<String>any(),
        Mockito.<Integer>any(), Mockito.<String>any())).thenReturn(null);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManager = new ProcessDefinitionEntityManagerImpl(
        new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setProcessDefinitionEntityManager(processDefinitionEntityManager);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> deploymentManager
        .findDeployedProcessDefinitionByKeyAndVersionAndTenantId("Process Definition Key", 1, "42"));
    verify(processDefinitionDataManager).findProcessDefinitionByKeyAndVersionAndTenantId(eq("Process Definition Key"),
        eq(1), eq("42"));
  }

  /**
   * Test {@link DeploymentManager#resolveProcessDefinition(ProcessDefinition)}.
   * <p>
   * Method under test: {@link DeploymentManager#resolveProcessDefinition(ProcessDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionCacheEntry DeploymentManager.resolveProcessDefinition(ProcessDefinition)"})
  public void testResolveProcessDefinition() {
    // Arrange
    DeploymentCache<ProcessDefinitionCacheEntry> processDefinitionCache = mock(DeploymentCache.class);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    BpmnModel bpmnModel = new BpmnModel();
    ProcessDefinitionCacheEntry processDefinitionCacheEntry = new ProcessDefinitionCacheEntry(processDefinition,
        bpmnModel, new Process());

    when(processDefinitionCache.get(Mockito.<String>any())).thenReturn(processDefinitionCacheEntry);

    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setProcessDefinitionCache(processDefinitionCache);

    // Act
    ProcessDefinitionCacheEntry actualResolveProcessDefinitionResult = deploymentManager
        .resolveProcessDefinition(new ProcessDefinitionEntityImpl());

    // Assert
    verify(processDefinitionCache).get(isNull());
    assertSame(processDefinitionCacheEntry, actualResolveProcessDefinitionResult);
  }

  /**
   * Test {@link DeploymentManager#removeDeployment(String, boolean)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentManager#removeDeployment(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DeploymentManager.removeDeployment(String, boolean)"})
  public void testRemoveDeployment_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    when(deploymentDataManager.findById(Mockito.<String>any())).thenReturn(null);
    DeploymentEntityManagerImpl deploymentEntityManager = new DeploymentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), deploymentDataManager);

    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setDeploymentEntityManager(deploymentEntityManager);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> deploymentManager.removeDeployment("42", true));
    verify(deploymentDataManager).findById(eq("42"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DeploymentManager}
   *   <li>{@link DeploymentManager#setDeployers(List)}
   *   <li>{@link DeploymentManager#setDeploymentEntityManager(DeploymentEntityManager)}
   *   <li>{@link DeploymentManager#setKnowledgeBaseCache(DeploymentCache)}
   *   <li>{@link DeploymentManager#setProcessDefinitionCache(DeploymentCache)}
   *   <li>{@link DeploymentManager#setProcessDefinitionEntityManager(ProcessDefinitionEntityManager)}
   *   <li>{@link DeploymentManager#setProcessDefinitionInfoCache(ProcessDefinitionInfoCache)}
   *   <li>{@link DeploymentManager#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   *   <li>{@link DeploymentManager#getDeployers()}
   *   <li>{@link DeploymentManager#getDeploymentEntityManager()}
   *   <li>{@link DeploymentManager#getKnowledgeBaseCache()}
   *   <li>{@link DeploymentManager#getProcessDefinitionCache()}
   *   <li>{@link DeploymentManager#getProcessDefinitionEntityManager()}
   *   <li>{@link DeploymentManager#getProcessDefinitionInfoCache()}
   *   <li>{@link DeploymentManager#getProcessEngineConfiguration()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DeploymentManager.<init>()", "List DeploymentManager.getDeployers()",
      "DeploymentEntityManager DeploymentManager.getDeploymentEntityManager()",
      "DeploymentCache DeploymentManager.getKnowledgeBaseCache()",
      "DeploymentCache DeploymentManager.getProcessDefinitionCache()",
      "ProcessDefinitionEntityManager DeploymentManager.getProcessDefinitionEntityManager()",
      "ProcessDefinitionInfoCache DeploymentManager.getProcessDefinitionInfoCache()",
      "ProcessEngineConfigurationImpl DeploymentManager.getProcessEngineConfiguration()",
      "void DeploymentManager.setDeployers(List)",
      "void DeploymentManager.setDeploymentEntityManager(DeploymentEntityManager)",
      "void DeploymentManager.setKnowledgeBaseCache(DeploymentCache)",
      "void DeploymentManager.setProcessDefinitionCache(DeploymentCache)",
      "void DeploymentManager.setProcessDefinitionEntityManager(ProcessDefinitionEntityManager)",
      "void DeploymentManager.setProcessDefinitionInfoCache(ProcessDefinitionInfoCache)",
      "void DeploymentManager.setProcessEngineConfiguration(ProcessEngineConfigurationImpl)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    DeploymentManager actualDeploymentManager = new DeploymentManager();
    ArrayList<Deployer> deployers = new ArrayList<>();
    actualDeploymentManager.setDeployers(deployers);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentEntityManagerImpl deploymentEntityManager = new DeploymentEntityManagerImpl(processEngineConfiguration,
        new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    actualDeploymentManager.setDeploymentEntityManager(deploymentEntityManager);
    DefaultDeploymentCache<Object> knowledgeBaseCache = new DefaultDeploymentCache<>();
    actualDeploymentManager.setKnowledgeBaseCache(knowledgeBaseCache);
    DefaultDeploymentCache<ProcessDefinitionCacheEntry> processDefinitionCache = new DefaultDeploymentCache<>();
    actualDeploymentManager.setProcessDefinitionCache(processDefinitionCache);
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManager = new ProcessDefinitionEntityManagerImpl(
        processEngineConfiguration2, new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration()));

    actualDeploymentManager.setProcessDefinitionEntityManager(processDefinitionEntityManager);
    CommandConfig defaultConfig = new CommandConfig();
    ProcessDefinitionInfoCache processDefinitionInfoCache = new ProcessDefinitionInfoCache(
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    actualDeploymentManager.setProcessDefinitionInfoCache(processDefinitionInfoCache);
    JtaProcessEngineConfiguration processEngineConfiguration3 = new JtaProcessEngineConfiguration();
    actualDeploymentManager.setProcessEngineConfiguration(processEngineConfiguration3);
    List<Deployer> actualDeployers = actualDeploymentManager.getDeployers();
    DeploymentEntityManager actualDeploymentEntityManager = actualDeploymentManager.getDeploymentEntityManager();
    DeploymentCache<Object> actualKnowledgeBaseCache = actualDeploymentManager.getKnowledgeBaseCache();
    DeploymentCache<ProcessDefinitionCacheEntry> actualProcessDefinitionCache = actualDeploymentManager
        .getProcessDefinitionCache();
    ProcessDefinitionEntityManager actualProcessDefinitionEntityManager = actualDeploymentManager
        .getProcessDefinitionEntityManager();
    ProcessDefinitionInfoCache actualProcessDefinitionInfoCache = actualDeploymentManager
        .getProcessDefinitionInfoCache();
    ProcessEngineConfigurationImpl actualProcessEngineConfiguration = actualDeploymentManager
        .getProcessEngineConfiguration();

    // Assert
    assertTrue(actualDeployers.isEmpty());
    assertSame(deployers, actualDeployers);
    assertSame(processEngineConfiguration3, actualProcessEngineConfiguration);
    assertSame(knowledgeBaseCache, actualKnowledgeBaseCache);
    assertSame(processDefinitionCache, actualProcessDefinitionCache);
    assertSame(processDefinitionInfoCache, actualProcessDefinitionInfoCache);
    assertSame(deploymentEntityManager, actualDeploymentEntityManager);
    assertSame(processDefinitionEntityManager, actualProcessDefinitionEntityManager);
  }
}
