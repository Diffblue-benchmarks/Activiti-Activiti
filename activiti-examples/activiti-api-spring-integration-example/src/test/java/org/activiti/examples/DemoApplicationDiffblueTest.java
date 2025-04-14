package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.File;
import java.util.ArrayList;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.runtime.model.impl.IntegrationContextImpl;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.DefaultDirectoryScanner;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

class DemoApplicationDiffblueTest {
  /**
   * Test {@link DemoApplication#DemoApplication(ProcessRuntime, SecurityUtil)}.
   * <p>
   * Method under test: {@link DemoApplication#DemoApplication(ProcessRuntime, SecurityUtil)}
   */
  @Test
  @DisplayName("Test new DemoApplication(ProcessRuntime, SecurityUtil)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DemoApplication.<init>(ProcessRuntime, SecurityUtil)"})
  void testNewDemoApplication() {
    // Arrange and Act
    DemoApplication actualDemoApplication = new DemoApplication(null, new SecurityUtil());

    // Assert
    assertTrue(actualDemoApplication.fileChannel() instanceof DirectChannel);
    assertTrue(actualDemoApplication.fileReadingMessageSource() instanceof FileReadingMessageSource);
  }

  /**
   * Test {@link DemoApplication#run(String[])}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ProcessDefinitionImpl} (default constructor).</li>
   *   <li>Then calls {@link ProcessRuntime#processDefinitions(Pageable)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#run(String[])}
   */
  @Test
  @DisplayName("Test run(String[]); given ArrayList() add ProcessDefinitionImpl (default constructor); then calls processDefinitions(Pageable)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DemoApplication.run(String[])"})
  void testRun_givenArrayListAddProcessDefinitionImpl_thenCallsProcessDefinitions() {
    // Arrange
    ArrayList<ProcessDefinition> content = new ArrayList<>();
    content.add(new ProcessDefinitionImpl());
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.processDefinitions(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(content, 1000));
    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    (new DemoApplication(processRuntime, securityUtil)).run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(securityUtil).logInAs(eq("system"));
  }

  /**
   * Test {@link DemoApplication#run(String[])}.
   * <ul>
   *   <li>Then calls {@link ProcessRuntime#processDefinitions(Pageable)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#run(String[])}
   */
  @Test
  @DisplayName("Test run(String[]); then calls processDefinitions(Pageable)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DemoApplication.run(String[])"})
  void testRun_thenCallsProcessDefinitions() {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    (new DemoApplication(processRuntime, securityUtil)).run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(securityUtil).logInAs(eq("system"));
  }

  /**
   * Test {@link DemoApplication#fileReadingMessageSource()}.
   * <p>
   * Method under test: {@link DemoApplication#fileReadingMessageSource()}
   */
  @Test
  @DisplayName("Test fileReadingMessageSource()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MessageSource DemoApplication.fileReadingMessageSource()"})
  void testFileReadingMessageSource() {
    // Arrange and Act
    MessageSource<File> actualFileReadingMessageSourceResult = (new DemoApplication(null, new SecurityUtil()))
        .fileReadingMessageSource();
    Message<File> actualReceiveResult = actualFileReadingMessageSourceResult.receive();

    // Assert
    assertTrue(((FileReadingMessageSource) actualFileReadingMessageSourceResult)
        .getScanner() instanceof DefaultDirectoryScanner);
    assertTrue(actualFileReadingMessageSourceResult instanceof FileReadingMessageSource);
    assertTrue(actualReceiveResult instanceof GenericMessage);
    assertEquals("file:inbound-channel-adapter",
        ((FileReadingMessageSource) actualFileReadingMessageSourceResult).getComponentType());
    assertNull(((FileReadingMessageSource) actualFileReadingMessageSourceResult).getBeanName());
    assertNull(((FileReadingMessageSource) actualFileReadingMessageSourceResult).getComponentName());
    assertNull(((FileReadingMessageSource) actualFileReadingMessageSourceResult).getManagedName());
    assertNull(((FileReadingMessageSource) actualFileReadingMessageSourceResult).getManagedType());
    MessageHeaders headers = actualReceiveResult.getHeaders();
    assertEquals(5, headers.size());
    assertEquals(IntegrationPatternType.inbound_channel_adapter,
        actualFileReadingMessageSourceResult.getIntegrationPatternType());
    assertFalse(((FileReadingMessageSource) actualFileReadingMessageSourceResult).isRunning());
    assertFalse(((FileReadingMessageSource) actualFileReadingMessageSourceResult).isUseWatchService());
    assertFalse(((FileReadingMessageSource) actualFileReadingMessageSourceResult).isObserved());
    assertTrue(((FileReadingMessageSource) actualFileReadingMessageSourceResult).isLoggingEnabled());
    assertTrue(headers.containsKey("file_name"));
    assertTrue(headers.containsKey("file_originalFile"));
    assertTrue(headers.containsKey("file_relativePath"));
    assertTrue(headers.containsKey(MessageHeaders.ID));
    assertTrue(headers.containsKey(MessageHeaders.TIMESTAMP));
  }

  /**
   * Test {@link DemoApplication#processTextConnector()}.
   * <p>
   * Method under test: {@link DemoApplication#processTextConnector()}
   */
  @Test
  @DisplayName("Test processTextConnector()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Connector DemoApplication.processTextConnector()"})
  void testProcessTextConnector() {
    // Arrange and Act
    Connector actualProcessTextConnectorResult = (new DemoApplication(null, new SecurityUtil())).processTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariable("content", "Value");

    // Assert
    assertSame(integrationContextImpl, actualProcessTextConnectorResult.apply(integrationContextImpl));
  }

  /**
   * Test {@link DemoApplication#tagTextConnector()}.
   * <p>
   * Method under test: {@link DemoApplication#tagTextConnector()}
   */
  @Test
  @DisplayName("Test tagTextConnector()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Connector DemoApplication.tagTextConnector()"})
  void testTagTextConnector() {
    // Arrange and Act
    Connector actualTagTextConnectorResult = (new DemoApplication(null, new SecurityUtil())).tagTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Assert
    assertSame(integrationContextImpl, actualTagTextConnectorResult.apply(integrationContextImpl));
  }

  /**
   * Test {@link DemoApplication#discardTextConnector()}.
   * <p>
   * Method under test: {@link DemoApplication#discardTextConnector()}
   */
  @Test
  @DisplayName("Test discardTextConnector()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Connector DemoApplication.discardTextConnector()"})
  void testDiscardTextConnector() {
    // Arrange and Act
    Connector actualDiscardTextConnectorResult = (new DemoApplication(null, new SecurityUtil())).discardTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Assert
    assertSame(integrationContextImpl, actualDiscardTextConnectorResult.apply(integrationContextImpl));
  }
}
