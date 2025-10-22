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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.zip.ZipInputStream;
import org.activiti.core.common.project.model.ProjectManifest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManager;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisResourceDataManager;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public class DeploymentBuilderImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DeploymentBuilderImpl#DeploymentBuilderImpl(RepositoryServiceImpl, DeploymentEntity, ResourceEntityManager)}
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DeploymentBuilderImpl.<init>(RepositoryServiceImpl, DeploymentEntity, ResourceEntityManager)",
      "DeploymentBuilder DeploymentBuilderImpl.activateProcessDefinitionsOn(Date)",
      "DeploymentBuilder DeploymentBuilderImpl.disableBpmnValidation()",
      "DeploymentBuilder DeploymentBuilderImpl.disableSchemaValidation()",
      "DeploymentBuilder DeploymentBuilderImpl.enableDuplicateFiltering()",
      "DeploymentEntity DeploymentBuilderImpl.getDeployment()", "Map DeploymentBuilderImpl.getDeploymentProperties()",
      "Integer DeploymentBuilderImpl.getEnforcedAppVersion()",
      "Date DeploymentBuilderImpl.getProcessDefinitionsActivationDate()",
      "ProjectManifest DeploymentBuilderImpl.getProjectManifest()",
      "boolean DeploymentBuilderImpl.isBpmn20XsdValidationEnabled()",
      "boolean DeploymentBuilderImpl.isDuplicateFilterEnabled()",
      "boolean DeploymentBuilderImpl.isProcessValidationEnabled()",
      "DeploymentBuilder DeploymentBuilderImpl.setEnforcedAppVersion(Integer)",
      "DeploymentBuilder DeploymentBuilderImpl.setProjectManifest(ProjectManifest)"})
  public void testGettersAndSetters() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    DeploymentBuilderImpl actualDeploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    DeploymentBuilder actualActivateProcessDefinitionsOnResult = actualDeploymentBuilderImpl
        .activateProcessDefinitionsOn(date);
    DeploymentBuilder actualSetEnforcedAppVersionResult = actualDeploymentBuilderImpl.setEnforcedAppVersion(1);
    ProjectManifest projectManifest = new ProjectManifest();
    projectManifest.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    projectManifest.setCreationDate("2020-03-01");
    projectManifest.setDescription("The characteristics of someone or something");
    projectManifest.setId("42");
    projectManifest.setLastModifiedBy("JaneDoe");
    projectManifest.setLastModifiedDate("2020-03-01");
    projectManifest.setName("Name");
    projectManifest.setVersion("1.0.2");
    DeploymentBuilder actualSetProjectManifestResult = actualDeploymentBuilderImpl.setProjectManifest(projectManifest);
    DeploymentBuilder actualDisableBpmnValidationResult = actualDeploymentBuilderImpl.disableBpmnValidation();
    DeploymentBuilder actualDisableSchemaValidationResult = actualDeploymentBuilderImpl.disableSchemaValidation();
    DeploymentBuilder actualEnableDuplicateFilteringResult = actualDeploymentBuilderImpl.enableDuplicateFiltering();
    DeploymentEntity actualDeployment = actualDeploymentBuilderImpl.getDeployment();
    Map<String, Object> actualDeploymentProperties = actualDeploymentBuilderImpl.getDeploymentProperties();
    Integer actualEnforcedAppVersion = actualDeploymentBuilderImpl.getEnforcedAppVersion();
    Date actualProcessDefinitionsActivationDate = actualDeploymentBuilderImpl.getProcessDefinitionsActivationDate();
    ProjectManifest actualProjectManifest = actualDeploymentBuilderImpl.getProjectManifest();
    boolean actualIsBpmn20XsdValidationEnabledResult = actualDeploymentBuilderImpl.isBpmn20XsdValidationEnabled();
    boolean actualIsDuplicateFilterEnabledResult = actualDeploymentBuilderImpl.isDuplicateFilterEnabled();
    boolean actualIsProcessValidationEnabledResult = actualDeploymentBuilderImpl.isProcessValidationEnabled();

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
   * Test {@link DeploymentBuilderImpl#addInputStream(String, InputStream)} with {@code resourceName}, {@code inputStream}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addInputStream(String, InputStream)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addInputStream(String, InputStream)"})
  public void testAddInputStreamWithResourceNameInputStream() throws IOException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));
    ByteArrayInputStream inputStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act
    DeploymentBuilder actualAddInputStreamResult = deploymentBuilderImpl.addInputStream("Resource Name", inputStream);

    // Assert
    assertEquals(-1, inputStream.read(new byte[]{}));
    assertSame(deploymentBuilderImpl, actualAddInputStreamResult);
  }

  /**
   * Test {@link DeploymentBuilderImpl#addInputStream(String, InputStream)} with {@code resourceName}, {@code inputStream}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addInputStream(String, InputStream)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addInputStream(String, InputStream)"})
  public void testAddInputStreamWithResourceNameInputStream2() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeploymentBuilderImpl(repositoryService, deployment,
            new ResourceEntityManagerImpl(processEngineConfiguration,
                new MybatisResourceDataManager(new JtaProcessEngineConfiguration()))))
            .addInputStream("Resource Name", (InputStream) null));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addInputStream(String, Resource)} with {@code resourceName}, {@code resource}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addInputStream(String, Resource)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addInputStream(String, Resource)"})
  public void testAddInputStreamWithResourceNameResource() throws UnsupportedEncodingException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));

    // Act
    DeploymentBuilder actualAddInputStreamResult = deploymentBuilderImpl.addInputStream("Resource Name",
        new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    DeploymentEntity deployment2 = ((DeploymentBuilderImpl) actualAddInputStreamResult).getDeployment();
    assertTrue(deployment2 instanceof DeploymentEntityImpl);
    Map<String, ResourceEntity> resources = deployment2.getResources();
    assertEquals(1, resources.size());
    ResourceEntity getResult = resources.get("Resource Name");
    assertTrue(getResult instanceof ResourceEntityImpl);
    assertTrue(actualAddInputStreamResult instanceof DeploymentBuilderImpl);
    assertEquals("Resource Name", getResult.getName());
    assertNull(getResult.getId());
    assertNull(getResult.getDeploymentId());
    assertFalse(getResult.isDeleted());
    assertFalse(getResult.isInserted());
    assertFalse(getResult.isUpdated());
    assertFalse(getResult.isGenerated());
    byte[] expectedBytes = "AXAXAXAX".getBytes("UTF-8");
    assertArrayEquals(expectedBytes, getResult.getBytes());
  }

  /**
   * Test {@link DeploymentBuilderImpl#addInputStream(String, Resource)} with {@code resourceName}, {@code resource}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addInputStream(String, Resource)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addInputStream(String, Resource)"})
  public void testAddInputStreamWithResourceNameResource2() throws UnsupportedEncodingException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));

    // Act and Assert
    assertSame(deploymentBuilderImpl,
        deploymentBuilderImpl.addInputStream(".bar", new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"))));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addInputStream(String, Resource)} with {@code resourceName}, {@code resource}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addInputStream(String, Resource)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addInputStream(String, Resource)"})
  public void testAddInputStreamWithResourceNameResource3() throws UnsupportedEncodingException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));

    // Act and Assert
    assertSame(deploymentBuilderImpl,
        deploymentBuilderImpl.addInputStream(".zip", new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"))));
  }

  /**
   * Test {@link DeploymentBuilderImpl#hasProjectManifestSet()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#hasProjectManifestSet()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DeploymentBuilderImpl.hasProjectManifestSet()"})
  public void testHasProjectManifestSet_thenReturnFalse() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertFalse((new DeploymentBuilderImpl(repositoryService, deployment, new ResourceEntityManagerImpl(
        processEngineConfiguration, new MybatisResourceDataManager(new JtaProcessEngineConfiguration()))))
        .hasProjectManifestSet());
  }

  /**
   * Test {@link DeploymentBuilderImpl#hasEnforcedAppVersion()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#hasEnforcedAppVersion()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DeploymentBuilderImpl.hasEnforcedAppVersion()"})
  public void testHasEnforcedAppVersion_thenReturnFalse() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertFalse((new DeploymentBuilderImpl(repositoryService, deployment, new ResourceEntityManagerImpl(
        processEngineConfiguration, new MybatisResourceDataManager(new JtaProcessEngineConfiguration()))))
        .hasEnforcedAppVersion());
  }

  /**
   * Test {@link DeploymentBuilderImpl#addClasspathResource(String)}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addClasspathResource(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addClasspathResource(String)"})
  public void testAddClasspathResource() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));
    deploymentBuilderImpl.addClasspathResource("");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentBuilderImpl.addClasspathResource("Resource"));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addClasspathResource(String)}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addClasspathResource(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addClasspathResource(String)"})
  public void testAddClasspathResource2() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));

    // Act and Assert
    assertSame(deploymentBuilderImpl, deploymentBuilderImpl.addClasspathResource(""));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addClasspathResource(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addClasspathResource(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addClasspathResource(String)"})
  public void testAddClasspathResource_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeploymentBuilderImpl(repositoryService, deployment,
            new ResourceEntityManagerImpl(processEngineConfiguration,
                new MybatisResourceDataManager(new JtaProcessEngineConfiguration()))))
            .addClasspathResource("Resource"));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addString(String, String)}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addString(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addString(String, String)"})
  public void testAddString() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));

    // Act and Assert
    assertSame(deploymentBuilderImpl, deploymentBuilderImpl.addString("Resource Name", "Text"));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addString(String, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addString(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addString(String, String)"})
  public void testAddString_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeploymentBuilderImpl(repositoryService, deployment,
            new ResourceEntityManagerImpl(processEngineConfiguration,
                new MybatisResourceDataManager(new JtaProcessEngineConfiguration()))))
            .addString("Resource Name", null));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBytes(String, byte[])}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addBytes(String, byte[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBytes(String, byte[])"})
  public void testAddBytes() throws UnsupportedEncodingException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));

    // Act and Assert
    assertSame(deploymentBuilderImpl, deploymentBuilderImpl.addBytes("Resource Name", "AXAXAXAX".getBytes("UTF-8")));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addBytes(String, byte[])}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addBytes(String, byte[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addBytes(String, byte[])"})
  public void testAddBytes_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeploymentBuilderImpl(repositoryService, deployment, new ResourceEntityManagerImpl(
            processEngineConfiguration, new MybatisResourceDataManager(new JtaProcessEngineConfiguration()))))
            .addBytes("Resource Name", null));
  }

  /**
   * Test {@link DeploymentBuilderImpl#addZipInputStream(ZipInputStream)}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#addZipInputStream(ZipInputStream)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.addZipInputStream(ZipInputStream)"})
  public void testAddZipInputStream() throws UnsupportedEncodingException {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertSame(deploymentBuilderImpl,
        deploymentBuilderImpl.addZipInputStream(new ZipInputStream(byteArrayInputStream, Charset.forName("UTF-8"))));
  }

  /**
   * Test {@link DeploymentBuilderImpl#name(String)}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#name(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.name(String)"})
  public void testName() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));

    // Act and Assert
    assertSame(deploymentBuilderImpl, deploymentBuilderImpl.name("Name"));
  }

  /**
   * Test {@link DeploymentBuilderImpl#category(String)}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#category(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.category(String)"})
  public void testCategory() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));

    // Act and Assert
    assertSame(deploymentBuilderImpl, deploymentBuilderImpl.category("Category"));
  }

  /**
   * Test {@link DeploymentBuilderImpl#key(String)}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#key(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.key(String)"})
  public void testKey() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));

    // Act and Assert
    assertSame(deploymentBuilderImpl, deploymentBuilderImpl.key("Key"));
  }

  /**
   * Test {@link DeploymentBuilderImpl#tenantId(String)}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#tenantId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.tenantId(String)"})
  public void testTenantId() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));

    // Act and Assert
    assertSame(deploymentBuilderImpl, deploymentBuilderImpl.tenantId("42"));
  }

  /**
   * Test {@link DeploymentBuilderImpl#deploymentProperty(String, Object)}.
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#deploymentProperty(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DeploymentBuilder DeploymentBuilderImpl.deploymentProperty(String, Object)"})
  public void testDeploymentProperty() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilderImpl = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));

    // Act and Assert
    assertSame(deploymentBuilderImpl, deploymentBuilderImpl.deploymentProperty("Property Key", JSONObject.NULL));
  }

  /**
   * Test {@link DeploymentBuilderImpl#deploy()}.
   * <ul>
   *   <li>Then return {@link DeploymentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentBuilderImpl#deploy()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Deployment DeploymentBuilderImpl.deploy()"})
  public void testDeploy_thenReturnDeploymentEntityImpl() {
    // Arrange
    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    when(repositoryService.deploy(Mockito.<DeploymentBuilderImpl>any())).thenReturn(deploymentEntityImpl);
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    Deployment actualDeployResult = (new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()))))
        .deploy();

    // Assert
    verify(repositoryService).deploy(isA(DeploymentBuilderImpl.class));
    assertSame(deploymentEntityImpl, actualDeployResult);
  }
}
