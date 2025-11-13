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
package org.activiti.engine.impl.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.AssociationDirection;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.ComplexGateway;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.MessageFlow;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.TextAnnotation;
import org.activiti.core.common.project.model.ProjectManifest;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManager;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.ResourceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisResourceDataManager;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class DeploymentBuilderImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link DeploymentBuilderImpl#DeploymentBuilderImpl(RepositoryServiceImpl,
   *       DeploymentEntity, ResourceEntityManager)}
   *   <li>{@link DeploymentBuilderImpl#activateProcessDefinitionsOn(Date)}
   *   <li>{@link DeploymentBuilderImpl#setEnforcedAppVersion(Integer)}
   *   <li>{@link DeploymentBuilderImpl#setProjectManifest(ProjectManifest)}
   *   <li>{@link DeploymentBuilderImpl#disableBpmnValidation()}
   *   <li>{@link DeploymentBuilderImpl#disableSchemaValidation()}
   *   <li>{@link DeploymentBuilderImpl#enableDuplicateFiltering()}
   *   <li>{@link DeploymentBuilderImpl#getDeployment()}
   *   <li>{@link DeploymentBuilderImpl#getDeploymentProperties()}
   *   <li>{@link DeploymentBuilderImpl#getEnforcedAppVersion()}
   *   <li>{@link DeploymentBuilderImpl#getProcessDefinitionsActivationDate()}
   *   <li>{@link DeploymentBuilderImpl#getProjectManifest()}
   *   <li>{@link DeploymentBuilderImpl#isBpmn20XsdValidationEnabled()}
   *   <li>{@link DeploymentBuilderImpl#isDuplicateFilterEnabled()}
   *   <li>{@link DeploymentBuilderImpl#isProcessValidationEnabled()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentBuilderImpl.<init>(RepositoryServiceImpl, DeploymentEntity, ResourceEntityManager)",
    "DeploymentBuilder DeploymentBuilderImpl.activateProcessDefinitionsOn(Date)",
    "DeploymentBuilder DeploymentBuilderImpl.disableBpmnValidation()",
    "DeploymentBuilder DeploymentBuilderImpl.disableSchemaValidation()",
    "DeploymentBuilder DeploymentBuilderImpl.enableDuplicateFiltering()",
    "DeploymentEntity DeploymentBuilderImpl.getDeployment()",
    "Map DeploymentBuilderImpl.getDeploymentProperties()",
    "Integer DeploymentBuilderImpl.getEnforcedAppVersion()",
    "Date DeploymentBuilderImpl.getProcessDefinitionsActivationDate()",
    "ProjectManifest DeploymentBuilderImpl.getProjectManifest()",
    "boolean DeploymentBuilderImpl.isBpmn20XsdValidationEnabled()",
    "boolean DeploymentBuilderImpl.isDuplicateFilterEnabled()",
    "boolean DeploymentBuilderImpl.isProcessValidationEnabled()",
    "DeploymentBuilder DeploymentBuilderImpl.setEnforcedAppVersion(Integer)",
    "DeploymentBuilder DeploymentBuilderImpl.setProjectManifest(ProjectManifest)"
  })
  public void testGettersAndSetters() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    // Act
    DeploymentBuilderImpl actualDeploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);
    Date date =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    DeploymentBuilder actualActivateProcessDefinitionsOnResult =
        actualDeploymentBuilderImpl.activateProcessDefinitionsOn(date);
    DeploymentBuilder actualSetEnforcedAppVersionResult =
        actualDeploymentBuilderImpl.setEnforcedAppVersion(1);
    ProjectManifest projectManifest = new ProjectManifest();
    projectManifest.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    projectManifest.setCreationDate("2020-03-01");
    projectManifest.setDescription("The characteristics of someone or something");
    projectManifest.setId("42");
    projectManifest.setLastModifiedBy("JaneDoe");
    projectManifest.setLastModifiedDate("2020-03-01");
    projectManifest.setName("Name");
    projectManifest.setVersion("1.0.2");
    DeploymentBuilder actualSetProjectManifestResult =
        actualDeploymentBuilderImpl.setProjectManifest(projectManifest);
    DeploymentBuilder actualDisableBpmnValidationResult =
        actualDeploymentBuilderImpl.disableBpmnValidation();
    DeploymentBuilder actualDisableSchemaValidationResult =
        actualDeploymentBuilderImpl.disableSchemaValidation();
    DeploymentBuilder actualEnableDuplicateFilteringResult =
        actualDeploymentBuilderImpl.enableDuplicateFiltering();
    DeploymentEntity actualDeployment = actualDeploymentBuilderImpl.getDeployment();
    Map<String, Object> actualDeploymentProperties =
        actualDeploymentBuilderImpl.getDeploymentProperties();
    Integer actualEnforcedAppVersion = actualDeploymentBuilderImpl.getEnforcedAppVersion();
    Date actualProcessDefinitionsActivationDate =
        actualDeploymentBuilderImpl.getProcessDefinitionsActivationDate();
    ProjectManifest actualProjectManifest = actualDeploymentBuilderImpl.getProjectManifest();
    boolean actualIsBpmn20XsdValidationEnabledResult =
        actualDeploymentBuilderImpl.isBpmn20XsdValidationEnabled();
    boolean actualIsDuplicateFilterEnabledResult =
        actualDeploymentBuilderImpl.isDuplicateFilterEnabled();
    boolean actualIsProcessValidationEnabledResult =
        actualDeploymentBuilderImpl.isProcessValidationEnabled();

    // Assert
    assertNull(actualDeploymentBuilderImpl.repositoryService.getCommandExecutor());
    assertEquals(1, actualEnforcedAppVersion.intValue());
    assertFalse(actualIsBpmn20XsdValidationEnabledResult);
    assertFalse(actualIsProcessValidationEnabledResult);
    assertTrue(actualDeploymentProperties.isEmpty());
    assertTrue(actualIsDuplicateFilterEnabledResult);
    assertSame(projectManifest, actualProjectManifest);
    assertSame(deployment, actualDeployment);
    assertSame(actualDeploymentBuilderImpl, actualActivateProcessDefinitionsOnResult);
    assertSame(actualDeploymentBuilderImpl, actualDisableBpmnValidationResult);
    assertSame(actualDeploymentBuilderImpl, actualDisableSchemaValidationResult);
    assertSame(actualDeploymentBuilderImpl, actualEnableDuplicateFilteringResult);
    assertSame(actualDeploymentBuilderImpl, actualSetEnforcedAppVersionResult);
    assertSame(actualDeploymentBuilderImpl, actualSetProjectManifestResult);
    assertSame(date, actualProcessDefinitionsActivationDate);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addInputStream(String, InputStream)} with {@code
   * resourceName}, {@code inputStream}.
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addInputStream(String, InputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addInputStream(String, InputStream)"})
  public void testAddInputStreamWithResourceNameInputStream() throws IOException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);
    ByteArrayInputStream inputStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act
    DeploymentBuilder actualAddInputStreamResult =
        deploymentBuilderImpl.addInputStream("Resource Name", inputStream);

    // Assert
    int actualReadResult = inputStream.read(new byte[] {});
    assertEquals(-1, actualReadResult);
    assertSame(deploymentBuilderImpl, actualAddInputStreamResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addInputStream(String, Resource)} with {@code resourceName},
   * {@code resource}.
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addInputStream(String, Resource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addInputStream(String, Resource)"})
  public void testAddInputStreamWithResourceNameResource() throws UnsupportedEncodingException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act
    DeploymentBuilder actualAddInputStreamResult =
        deploymentBuilderImpl.addInputStream(
            "Resource Name", new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    assertSame(deploymentBuilderImpl, actualAddInputStreamResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addInputStream(String, Resource)} with {@code resourceName},
   * {@code resource}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addInputStream(String, Resource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addInputStream(String, Resource)"})
  public void testAddInputStreamWithResourceNameResource_thenThrowActivitiException() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> deploymentBuilderImpl.addInputStream("Resource Name", new ClassPathResource(".bar")));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addInputStream(String, Resource)} with {@code resourceName},
   * {@code resource}.
   *
   * <ul>
   *   <li>When {@code .bar}.
   *   <li>Then calls {@link ByteArrayResource#getInputStream()}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addInputStream(String, Resource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addInputStream(String, Resource)"})
  public void testAddInputStreamWithResourceNameResource_whenBar_thenCallsGetInputStream()
      throws IOException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), mock(ResourceDataManager.class));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    ByteArrayResource resource = mock(ByteArrayResource.class);
    when(resource.getInputStream())
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Act
    DeploymentBuilder actualAddInputStreamResult =
        deploymentBuilderImpl.addInputStream(".bar", resource);

    // Assert
    verify(resource).getInputStream();
    assertSame(deploymentBuilderImpl, actualAddInputStreamResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addInputStream(String, Resource)} with {@code resourceName},
   * {@code resource}.
   *
   * <ul>
   *   <li>When {@code .jar}.
   *   <li>Then calls {@link ByteArrayResource#getInputStream()}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addInputStream(String, Resource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addInputStream(String, Resource)"})
  public void testAddInputStreamWithResourceNameResource_whenJar_thenCallsGetInputStream()
      throws IOException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), mock(ResourceDataManager.class));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    ByteArrayResource resource = mock(ByteArrayResource.class);
    when(resource.getInputStream())
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Act
    DeploymentBuilder actualAddInputStreamResult =
        deploymentBuilderImpl.addInputStream(".jar", resource);

    // Assert
    verify(resource).getInputStream();
    assertSame(deploymentBuilderImpl, actualAddInputStreamResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addInputStream(String, Resource)} with {@code resourceName},
   * {@code resource}.
   *
   * <ul>
   *   <li>When {@code .zip}.
   *   <li>Then calls {@link ByteArrayResource#getInputStream()}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addInputStream(String, Resource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addInputStream(String, Resource)"})
  public void testAddInputStreamWithResourceNameResource_whenZip_thenCallsGetInputStream()
      throws IOException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), mock(ResourceDataManager.class));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    ByteArrayResource resource = mock(ByteArrayResource.class);
    when(resource.getInputStream())
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Act
    DeploymentBuilder actualAddInputStreamResult =
        deploymentBuilderImpl.addInputStream(".zip", resource);

    // Assert
    verify(resource).getInputStream();
    assertSame(deploymentBuilderImpl, actualAddInputStreamResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#hasProjectManifestSet()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#hasProjectManifestSet()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DeploymentBuilderImpl.hasProjectManifestSet()"})
  public void testHasProjectManifestSet_thenReturnFalse() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act and Assert
    assertFalse(deploymentBuilderImpl.hasProjectManifestSet());
  }

  /**
   * Test {@link DeploymentBuilderImpl#hasProjectManifestSet()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#hasProjectManifestSet()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DeploymentBuilderImpl.hasProjectManifestSet()"})
  public void testHasProjectManifestSet_thenReturnTrue() {
    // Arrange
    ProjectManifest projectManifest = new ProjectManifest();
    projectManifest.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    projectManifest.setCreationDate("2020-03-01");
    projectManifest.setDescription("The characteristics of someone or something");
    projectManifest.setId("42");
    projectManifest.setLastModifiedBy("JaneDoe");
    projectManifest.setLastModifiedDate("2020-03-01");
    projectManifest.setName("Name");
    projectManifest.setVersion("1.0.2");
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);
    deploymentBuilderImpl.setProjectManifest(projectManifest);

    // Act and Assert
    assertTrue(deploymentBuilderImpl.hasProjectManifestSet());
  }

  /**
   * Test {@link DeploymentBuilderImpl#hasEnforcedAppVersion()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#hasEnforcedAppVersion()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DeploymentBuilderImpl.hasEnforcedAppVersion()"})
  public void testHasEnforcedAppVersion_thenReturnFalse() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act and Assert
    assertFalse(deploymentBuilderImpl.hasEnforcedAppVersion());
  }

  /**
   * Test {@link DeploymentBuilderImpl#hasEnforcedAppVersion()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#hasEnforcedAppVersion()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DeploymentBuilderImpl.hasEnforcedAppVersion()"})
  public void testHasEnforcedAppVersion_thenReturnTrue() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);
    deploymentBuilderImpl.setEnforcedAppVersion(1);

    // Act and Assert
    assertTrue(deploymentBuilderImpl.hasEnforcedAppVersion());
  }

  /**
   * Test {@link DeploymentBuilderImpl#addClasspathResource(String)}.
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addClasspathResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addClasspathResource(String)"})
  public void testAddClasspathResource() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act
    DeploymentBuilder actualAddClasspathResourceResult =
        deploymentBuilderImpl.addClasspathResource("");

    // Assert
    assertSame(deploymentBuilderImpl, actualAddClasspathResourceResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addClasspathResource(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addClasspathResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addClasspathResource(String)"})
  public void testAddClasspathResource_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deploymentBuilderImpl.addClasspathResource("Resource"));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addString(String, String)}.
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addString(String, String)"})
  public void testAddString() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act
    DeploymentBuilder actualAddStringResult =
        deploymentBuilderImpl.addString("Resource Name", "Text");

    // Assert
    assertSame(deploymentBuilderImpl, actualAddStringResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addString(String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addString(String, String)"})
  public void testAddString_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deploymentBuilderImpl.addString("Resource Name", null));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBytes(String, byte[])}.
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBytes(String, byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBytes(String, byte[])"})
  public void testAddBytes() throws UnsupportedEncodingException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act
    DeploymentBuilder actualAddBytesResult =
        deploymentBuilderImpl.addBytes("Resource Name", "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    assertSame(deploymentBuilderImpl, actualAddBytesResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBytes(String, byte[])}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBytes(String, byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBytes(String, byte[])"})
  public void testAddBytes_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deploymentBuilderImpl.addBytes("Resource Name", null));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addZipInputStream(ZipInputStream)}.
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addZipInputStream(ZipInputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addZipInputStream(ZipInputStream)"})
  public void testAddZipInputStream() throws UnsupportedEncodingException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);
    ByteArrayInputStream byteArrayInputStream =
        new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));
    ZipInputStream zipInputStream =
        new ZipInputStream(byteArrayInputStream, Charset.forName("UTF-8"));

    // Act
    DeploymentBuilder actualAddZipInputStreamResult =
        deploymentBuilderImpl.addZipInputStream(zipInputStream);

    // Assert
    assertSame(deploymentBuilderImpl, actualAddZipInputStreamResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addFlowGraphicInfoList("UTF-8", new ArrayList<>());

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1376, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   *   <li>Then return array length is {@code 1108}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenAdhocSubProcess_thenReturnArrayLengthIs1108() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(new HashMap<>());
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1108, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@code An error occurred}.
   *   <li>Then return array length is {@code 1472}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenAnErrorOccurred_thenReturnArrayLengthIs1472() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addError("An error occurred", "An error occurred", "An error occurred");

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1472, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ActivitiListener} (default constructor).
   *   <li>Then return array length is {@code 1498}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenArrayListAddActivitiListener_thenReturnArrayLengthIs1498() {
    // Arrange
    MybatisResourceDataManager resourceDataManager = mock(MybatisResourceDataManager.class);
    when(resourceDataManager.create()).thenReturn(new ResourceEntityImpl());
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(new JtaProcessEngineConfiguration(), resourceDataManager);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(
            repositoryService, new DeploymentEntityImpl(), resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(activitiListenerList);
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(resourceDataManager).create();
    DeploymentEntity deployment =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1498, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link GraphicInfo} (default constructor).
   *   <li>Then return array length is {@code 1173}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenArrayListAddGraphicInfo_thenReturnArrayLengthIs1173() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(new HashMap<>());
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1173, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link Association} (default constructor).
   *   <li>Then calls {@link BpmnModel#getArtifact(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenAssociation_thenCallsGetArtifact() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(bpmnModel.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(bpmnModel.getMessageFlow(Mockito.<String>any())).thenReturn(new MessageFlow());
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(new HashMap<>());
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getArtifact("UTF-8");
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessageFlow("UTF-8");
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1108, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Expanded is {@code null}.
   *   <li>Then return array length is {@code 1426}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenGraphicInfoExpandedIsNull_thenReturnArrayLengthIs1426() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(null);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1426, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} empty string is {@link ArrayList#ArrayList()}.
   *   <li>Then return array length is {@code 1498}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenHashMapEmptyStringIsArrayList_thenReturnArrayLengthIs1498() {
    // Arrange
    MybatisResourceDataManager resourceDataManager = mock(MybatisResourceDataManager.class);
    when(resourceDataManager.create()).thenReturn(new ResourceEntityImpl());
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(new JtaProcessEngineConfiguration(), resourceDataManager);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(
            repositoryService, new DeploymentEntityImpl(), resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("", new ArrayList<>());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(stringListMap);
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap2 = new HashMap<>();
    stringListMap2.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap2);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(resourceDataManager).create();
    DeploymentEntity deployment =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1498, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} empty string is {@link ArrayList#ArrayList()}.
   *   <li>Then return array length is {@code 1542}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenHashMapEmptyStringIsArrayList_thenReturnArrayLengthIs1542() {
    // Arrange
    MybatisResourceDataManager resourceDataManager = mock(MybatisResourceDataManager.class);
    when(resourceDataManager.create()).thenReturn(new ResourceEntityImpl());
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(new JtaProcessEngineConfiguration(), resourceDataManager);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(
            repositoryService, new DeploymentEntityImpl(), resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("", new ArrayList<>());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(stringListMap);
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap2 = new HashMap<>();
    stringListMap2.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap2);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process, atLeast(1)).getExtensionElements();
    verify(association).getId();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(resourceDataManager).create();
    DeploymentEntity deployment =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1542, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code Key} is {@link ArrayList#ArrayList()}.
   *   <li>Then return array length is {@code 1549}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenHashMapKeyIsArrayList_thenReturnArrayLengthIs1549() {
    // Arrange
    MybatisResourceDataManager resourceDataManager = mock(MybatisResourceDataManager.class);
    when(resourceDataManager.create()).thenReturn(new ResourceEntityImpl());
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(new JtaProcessEngineConfiguration(), resourceDataManager);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(
            repositoryService, new DeploymentEntityImpl(), resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("Key", new ArrayList<>());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(stringListMap);
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap2 = new HashMap<>();
    stringListMap2.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap2);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(association, atLeast(1)).getExtensionElements();
    verify(association).getId();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(resourceDataManager).create();
    DeploymentEntity deployment =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1549, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@link Association} (default
   *       constructor).
   *   <li>Then return array length is {@code 1021}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenLinkedHashSetAddAssociation_thenReturnArrayLengthIs1021() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getFlowLocationMap()).thenReturn(new HashMap<>());
    when(bpmnModel.getLocationMap()).thenReturn(new HashMap<>());
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1021, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@link Association} (default
   *       constructor).
   *   <li>Then return array length is {@code 1253}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenLinkedHashSetAddAssociation_thenReturnArrayLengthIs1253() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(new HashMap<>());
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1253, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@link Association} (default
   *       constructor).
   *   <li>Then return array length is {@code 1444}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenLinkedHashSetAddAssociation_thenReturnArrayLengthIs1444() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new Association());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1444, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@link CallActivity} (default
   *       constructor).
   *   <li>Then return array length is {@code 1502}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenLinkedHashSetAddCallActivity_thenReturnArrayLengthIs1502() {
    // Arrange
    MybatisResourceDataManager resourceDataManager = mock(MybatisResourceDataManager.class);
    when(resourceDataManager.create()).thenReturn(new ResourceEntityImpl());
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(new JtaProcessEngineConfiguration(), resourceDataManager);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(
            repositoryService, new DeploymentEntityImpl(), resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new CallActivity());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(resourceDataManager).create();
    DeploymentEntity deployment =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1502, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@link ComplexGateway} (default
   *       constructor).
   *   <li>Then return array length is {@code 1506}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenLinkedHashSetAddComplexGateway_thenReturnArrayLengthIs1506() {
    // Arrange
    MybatisResourceDataManager resourceDataManager = mock(MybatisResourceDataManager.class);
    when(resourceDataManager.create()).thenReturn(new ResourceEntityImpl());
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(new JtaProcessEngineConfiguration(), resourceDataManager);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(
            repositoryService, new DeploymentEntityImpl(), resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new ComplexGateway());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(resourceDataManager).create();
    DeploymentEntity deployment =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1506, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@link TextAnnotation} (default
   *       constructor).
   *   <li>Then return array length is {@code 1422}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenLinkedHashSetAddTextAnnotation_thenReturnArrayLengthIs1422() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(new TextAnnotation());

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1422, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link Pool} (default constructor) Id is {@code 42}.
   *   <li>Then return array length is {@code 1651}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenPoolIdIs42_thenReturnArrayLengthIs1651() {
    // Arrange
    MybatisResourceDataManager resourceDataManager = mock(MybatisResourceDataManager.class);
    when(resourceDataManager.create()).thenReturn(new ResourceEntityImpl());
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(new JtaProcessEngineConfiguration(), resourceDataManager);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(
            repositoryService, new DeploymentEntityImpl(), resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    Pool pool = new Pool();
    pool.setId("42");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getMessageFlows()).thenReturn(new HashMap<>());
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(poolList);
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMessageFlows();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(resourceDataManager).create();
    DeploymentEntity deployment =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1651, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Given space space.
   *   <li>Then return array length is {@code 1463}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_givenSpaceSpace_thenReturnArrayLengthIs1463() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addNamespace("  ", "  ");

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    bpmnModel.addMessage(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1463, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1353}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_thenReturnArrayLengthIs1353() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.setTargetNamespace("UTF-8");
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1353, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1376}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_thenReturnArrayLengthIs1376() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1376, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1396}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_thenReturnArrayLengthIs1396() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addNamespace("UTF-8", "UTF-8");

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1396, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1449}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_thenReturnArrayLengthIs1449() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    bpmnModel.addMessage(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1449, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1510}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_thenReturnArrayLengthIs1510() {
    // Arrange
    MybatisResourceDataManager resourceDataManager = mock(MybatisResourceDataManager.class);
    when(resourceDataManager.create()).thenReturn(new ResourceEntityImpl());
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(new JtaProcessEngineConfiguration(), resourceDataManager);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(
            repositoryService, new DeploymentEntityImpl(), resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BusinessRuleTask());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(resourceDataManager).create();
    DeploymentEntity deployment =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1510, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1571}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_thenReturnArrayLengthIs1571() {
    // Arrange
    MybatisResourceDataManager resourceDataManager = mock(MybatisResourceDataManager.class);
    when(resourceDataManager.create()).thenReturn(new ResourceEntityImpl());
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(new JtaProcessEngineConfiguration(), resourceDataManager);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(
            repositoryService, new DeploymentEntityImpl(), resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    messageList.add(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult5 = Message.builder();

    Builder attributesResult5 = builderResult5.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult5
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(messageList);
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(resourceDataManager).create();
    DeploymentEntity deployment =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1571, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1623}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_thenReturnArrayLengthIs1623() {
    // Arrange
    MybatisResourceDataManager resourceDataManager = mock(MybatisResourceDataManager.class);
    when(resourceDataManager.create()).thenReturn(new ResourceEntityImpl());
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(new JtaProcessEngineConfiguration(), resourceDataManager);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(
            repositoryService, new DeploymentEntityImpl(), resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    Pool pool = new Pool();
    pool.setId("42");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(new BooleanDataObject());

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(null);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getMessageFlows()).thenReturn(new HashMap<>());
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(poolList);
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(association).getId();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMessageFlows();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess).getName();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(resourceDataManager).create();
    DeploymentEntity deployment =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1623, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1835}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_thenReturnArrayLengthIs1835() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addProcess(TestProcessUtil.createOneTaskProcessWithId("42"));
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1835, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Then return array length is {@code 1965}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_thenReturnArrayLengthIs1965() {
    // Arrange
    MybatisResourceDataManager resourceDataManager = mock(MybatisResourceDataManager.class);
    when(resourceDataManager.create()).thenReturn(new ResourceEntityImpl());
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(new JtaProcessEngineConfiguration(), resourceDataManager);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(
            repositoryService, new DeploymentEntityImpl(), resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    Pool pool = new Pool();
    pool.setId("42");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.isNotExclusive()).thenReturn(true);
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getCompletionCondition()).thenReturn("Completion Condition");
    when(adhocSubProcess.getDocumentation()).thenReturn("Documentation");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.getArtifacts()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getExtensionElements()).thenReturn(new HashMap<>());
    when(adhocSubProcess.getLoopCharacteristics())
        .thenReturn(new MultiInstanceLoopCharacteristics());
    when(adhocSubProcess.getId()).thenReturn("42");

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(adhocSubProcess);

    Association association = mock(Association.class);
    when(association.getSourceRef()).thenReturn("Source Ref");
    when(association.getTargetRef()).thenReturn("Target Ref");
    when(association.getId()).thenReturn("42");
    when(association.getExtensionElements()).thenReturn(new HashMap<>());
    when(association.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    LinkedHashSet<Artifact> artifactSet = new LinkedHashSet<>();
    artifactSet.add(association);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifacts()).thenReturn(artifactSet);
    when(process.isExecutable()).thenReturn(true);
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElements()).thenReturn(flowElementSet);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("UTF-8", new ArrayList<>());

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("UTF-8", graphicInfo2);

    AdhocSubProcess adhocSubProcess2 = mock(AdhocSubProcess.class);
    when(adhocSubProcess2.getName()).thenReturn("Name");

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getMessageFlows()).thenReturn(new HashMap<>());
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess2);
    when(bpmnModel.getFlowLocationMap()).thenReturn(stringListMap);
    when(bpmnModel.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(poolList);
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getLoopCharacteristics();
    verify(adhocSubProcess, atLeast(1)).getCompletionCondition();
    verify(association).getAssociationDirection();
    verify(association).getSourceRef();
    verify(association).getTargetRef();
    verify(process).getAttributes();
    verify(adhocSubProcess).getExtensionElements();
    verify(association).getExtensionElements();
    verify(process).getExtensionElements();
    verify(adhocSubProcess).getId();
    verify(association).getId();
    verify(process).getId();
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel, atLeast(1)).getFlowElement("UTF-8");
    verify(bpmnModel).getFlowLocationGraphicInfo("UTF-8");
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo("UTF-8");
    verify(bpmnModel).getLabelGraphicInfo("UTF-8");
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMessageFlows();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(adhocSubProcess, atLeast(1)).getDocumentation();
    verify(adhocSubProcess).getExecutionListeners();
    verify(adhocSubProcess2).getName();
    verify(adhocSubProcess, atLeast(1)).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(adhocSubProcess).isNotExclusive();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifacts();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getFlowElements();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
    verify(adhocSubProcess).getArtifacts();
    verify(adhocSubProcess).getFlowElements();
    verify(resourceDataManager).create();
    DeploymentEntity deployment =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1965, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>Then return array length is seven hundred sixty-nine.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_thenReturnArrayLengthIsSevenHundredSixtyNine() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    Process process = mock(Process.class);
    when(process.getFlowElements()).thenReturn(new ArrayList<>());
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addFlowElement(new AdhocSubProcess());
    process.addArtifact(new Association());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getFlowLocationMap()).thenReturn(new HashMap<>());
    when(bpmnModel.getLocationMap()).thenReturn(new HashMap<>());
    when(bpmnModel.getProcesses()).thenReturn(processList);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");
    when(bpmnModel.getMessages()).thenReturn(new ArrayList<>());
    when(bpmnModel.getSignals()).thenReturn(new ArrayList<>());
    when(bpmnModel.getPools()).thenReturn(new ArrayList<>());
    when(bpmnModel.getDataStores()).thenReturn(new HashMap<>());
    when(bpmnModel.getDefinitionsAttributes()).thenReturn(new HashMap<>());
    when(bpmnModel.getErrors()).thenReturn(new HashMap<>());
    when(bpmnModel.getNamespaces()).thenReturn(new HashMap<>());
    when(bpmnModel.getMainProcess()).thenReturn(TestProcessUtil.createOneTaskProcessWithId("42"));
    doNothing().when(bpmnModel).addGraphicInfo(Mockito.<String>any(), Mockito.<GraphicInfo>any());
    bpmnModel.addGraphicInfo("UTF-8", graphicInfo);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel("Resource Name", bpmnModel);

    // Assert
    verify(bpmnModel).addGraphicInfo(eq("UTF-8"), isA(GraphicInfo.class));
    verify(bpmnModel).getDataStores();
    verify(bpmnModel).getDefinitionsAttributes();
    verify(bpmnModel).getErrors();
    verify(bpmnModel).getFlowLocationMap();
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).getMainProcess();
    verify(bpmnModel).getMessages();
    verify(bpmnModel, atLeast(1)).getNamespaces();
    verify(bpmnModel, atLeast(1)).getPools();
    verify(bpmnModel, atLeast(1)).getProcesses();
    verify(bpmnModel).getSignals();
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getFlowElements();
    verify(process).getLanes();
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(769, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}.
   *
   * <ul>
   *   <li>When createOneTaskBpmnModel.
   *   <li>Then return array length is {@code 1376}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#addBpmnModel(String, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBpmnModel(String, BpmnModel)"})
  public void testAddBpmnModel_whenCreateOneTaskBpmnModel_thenReturnArrayLengthIs1376() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act
    DeploymentBuilder actualAddBpmnModelResult =
        deploymentBuilderImpl.addBpmnModel(
            "Resource Name", TestProcessUtil.createOneTaskBpmnModel());

    // Assert
    DeploymentEntity deployment2 =
        ((DeploymentBuilderImpl) actualAddBpmnModelResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddBpmnModelResult instanceof DeploymentBuilderImpl);
    assertEquals(1376, getResult.getBytes().length);
  }

  /**
   * Test {@link DeploymentBuilderImpl#name(String)}.
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#name(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.name(String)"})
  public void testName() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act
    DeploymentBuilder actualNameResult = deploymentBuilderImpl.name("Name");

    // Assert
    assertSame(deploymentBuilderImpl, actualNameResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#category(String)}.
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#category(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.category(String)"})
  public void testCategory() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act
    DeploymentBuilder actualCategoryResult = deploymentBuilderImpl.category("Category");

    // Assert
    assertSame(deploymentBuilderImpl, actualCategoryResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#key(String)}.
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#key(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.key(String)"})
  public void testKey() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act
    DeploymentBuilder actualKeyResult = deploymentBuilderImpl.key("Key");

    // Assert
    assertSame(deploymentBuilderImpl, actualKeyResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#tenantId(String)}.
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#tenantId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.tenantId(String)"})
  public void testTenantId() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act
    DeploymentBuilder actualTenantIdResult = deploymentBuilderImpl.tenantId("42");

    // Assert
    assertSame(deploymentBuilderImpl, actualTenantIdResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#deploymentProperty(String, Object)}.
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#deploymentProperty(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.deploymentProperty(String, Object)"})
  public void testDeploymentProperty() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act
    DeploymentBuilder actualDeploymentPropertyResult =
        deploymentBuilderImpl.deploymentProperty("Property Key", JSONObject.NULL);

    // Assert
    assertSame(deploymentBuilderImpl, actualDeploymentPropertyResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#deploy()}.
   *
   * <ul>
   *   <li>Then return {@link DeploymentEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DeploymentBuilderImpl#deploy()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Deployment DeploymentBuilderImpl.deploy()"})
  public void testDeploy_thenReturnDeploymentEntityImpl() {
    // Arrange
    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    when(repositoryService.deploy(Mockito.<DeploymentBuilderImpl>any()))
        .thenReturn(deploymentEntityImpl);
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act
    Deployment actualDeployResult = deploymentBuilderImpl.deploy();

    // Assert
    verify(repositoryService).deploy(isA(DeploymentBuilderImpl.class));
    assertSame(deploymentEntityImpl, actualDeployResult);
  }
}
