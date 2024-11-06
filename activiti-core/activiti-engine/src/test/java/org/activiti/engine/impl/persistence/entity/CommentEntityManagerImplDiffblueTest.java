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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.CommentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisCommentDataManager;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommentEntityManagerImplDiffblueTest {
  @Mock
  private CommentDataManager commentDataManager;

  @InjectMocks
  private CommentEntityManagerImpl commentEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link CommentEntityManagerImpl#CommentEntityManagerImpl(ProcessEngineConfigurationImpl, CommentDataManager)}
   *   <li>
   * {@link CommentEntityManagerImpl#setCommentDataManager(CommentDataManager)}
   *   <li>{@link CommentEntityManagerImpl#getCommentDataManager()}
   *   <li>{@link CommentEntityManagerImpl#getDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    CommentEntityManagerImpl actualCommentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfiguration,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration()));
    MybatisCommentDataManager commentDataManager = new MybatisCommentDataManager(new JtaProcessEngineConfiguration());
    actualCommentEntityManagerImpl.setCommentDataManager(commentDataManager);
    CommentDataManager actualCommentDataManager = actualCommentEntityManagerImpl.getCommentDataManager();

    // Assert that nothing has changed
    assertSame(commentDataManager, actualCommentDataManager);
    assertSame(commentDataManager, actualCommentEntityManagerImpl.getDataManager());
  }

  /**
   * Test {@link CommentEntityManagerImpl#insert(CommentEntity)} with
   * {@code CommentEntity}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityManagerImpl#insert(CommentEntity)}
   */
  @Test
  public void testInsertWithCommentEntity_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfiguration,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.insert(new CommentEntityImpl()));
  }

  /**
   * Test {@link CommentEntityManagerImpl#findCommentsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByTaskId(String)}
   */
  @Test
  public void testFindCommentsByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link CommentEntityManagerImpl#findCommentsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByTaskId(String)}
   */
  @Test
  public void testFindCommentsByTaskId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByTaskId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByTaskId(eq("42"));
  }

  /**
   * Test {@link CommentEntityManagerImpl#findCommentsByTaskId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByTaskId(String)}
   */
  @Test
  public void testFindCommentsByTaskId_thenReturnEmpty() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByTaskId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<Comment> actualFindCommentsByTaskIdResult = commentEntityManagerImpl.findCommentsByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByTaskId(eq("42"));
    assertTrue(actualFindCommentsByTaskIdResult.isEmpty());
  }

  /**
   * Test
   * {@link CommentEntityManagerImpl#findCommentsByTaskIdAndType(String, String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByTaskIdAndType(String, String)}
   */
  @Test
  public void testFindCommentsByTaskIdAndType() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByTaskIdAndType("42", "Type"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test
   * {@link CommentEntityManagerImpl#findCommentsByTaskIdAndType(String, String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByTaskIdAndType(String, String)}
   */
  @Test
  public void testFindCommentsByTaskIdAndType2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByTaskIdAndType(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByTaskIdAndType("42", "Type"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByTaskIdAndType(eq("42"), eq("Type"));
  }

  /**
   * Test
   * {@link CommentEntityManagerImpl#findCommentsByTaskIdAndType(String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByTaskIdAndType(String, String)}
   */
  @Test
  public void testFindCommentsByTaskIdAndType_thenReturnEmpty() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByTaskIdAndType(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<Comment> actualFindCommentsByTaskIdAndTypeResult = commentEntityManagerImpl.findCommentsByTaskIdAndType("42",
        "Type");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByTaskIdAndType(eq("42"), eq("Type"));
    assertTrue(actualFindCommentsByTaskIdAndTypeResult.isEmpty());
  }

  /**
   * Test {@link CommentEntityManagerImpl#findCommentsByType(String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByType(String)}
   */
  @Test
  public void testFindCommentsByType() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByType("Type"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link CommentEntityManagerImpl#findCommentsByType(String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByType(String)}
   */
  @Test
  public void testFindCommentsByType2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByType(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByType("Type"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByType(eq("Type"));
  }

  /**
   * Test {@link CommentEntityManagerImpl#findCommentsByType(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByType(String)}
   */
  @Test
  public void testFindCommentsByType_thenReturnEmpty() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByType(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<Comment> actualFindCommentsByTypeResult = commentEntityManagerImpl.findCommentsByType("Type");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByType(eq("Type"));
    assertTrue(actualFindCommentsByTypeResult.isEmpty());
  }

  /**
   * Test {@link CommentEntityManagerImpl#findEventsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findEventsByTaskId(String)}
   */
  @Test
  public void testFindEventsByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findEventsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link CommentEntityManagerImpl#findEventsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findEventsByTaskId(String)}
   */
  @Test
  public void testFindEventsByTaskId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findEventsByTaskId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findEventsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findEventsByTaskId(eq("42"));
  }

  /**
   * Test {@link CommentEntityManagerImpl#findEventsByTaskId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findEventsByTaskId(String)}
   */
  @Test
  public void testFindEventsByTaskId_thenReturnEmpty() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findEventsByTaskId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<Event> actualFindEventsByTaskIdResult = commentEntityManagerImpl.findEventsByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findEventsByTaskId(eq("42"));
    assertTrue(actualFindEventsByTaskIdResult.isEmpty());
  }

  /**
   * Test {@link CommentEntityManagerImpl#findEventsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findEventsByProcessInstanceId(String)}
   */
  @Test
  public void testFindEventsByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findEventsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link CommentEntityManagerImpl#findEventsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findEventsByProcessInstanceId(String)}
   */
  @Test
  public void testFindEventsByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findEventsByProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findEventsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findEventsByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link CommentEntityManagerImpl#findEventsByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findEventsByProcessInstanceId(String)}
   */
  @Test
  public void testFindEventsByProcessInstanceId_thenReturnEmpty() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findEventsByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<Event> actualFindEventsByProcessInstanceIdResult = commentEntityManagerImpl
        .findEventsByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findEventsByProcessInstanceId(eq("42"));
    assertTrue(actualFindEventsByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link CommentEntityManagerImpl#deleteCommentsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#deleteCommentsByTaskId(String)}
   */
  @Test
  public void testDeleteCommentsByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.deleteCommentsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link CommentEntityManagerImpl#deleteCommentsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#deleteCommentsByTaskId(String)}
   */
  @Test
  public void testDeleteCommentsByTaskId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    doThrow(new ActivitiException("An error occurred")).when(commentDataManager)
        .deleteCommentsByTaskId(Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.deleteCommentsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).deleteCommentsByTaskId(eq("42"));
  }

  /**
   * Test {@link CommentEntityManagerImpl#deleteCommentsByTaskId(String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommentDataManager#deleteCommentsByTaskId(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#deleteCommentsByTaskId(String)}
   */
  @Test
  public void testDeleteCommentsByTaskId_thenCallsDeleteCommentsByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    doNothing().when(commentDataManager).deleteCommentsByTaskId(Mockito.<String>any());

    // Act
    commentEntityManagerImpl.deleteCommentsByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).deleteCommentsByTaskId(eq("42"));
  }

  /**
   * Test
   * {@link CommentEntityManagerImpl#deleteCommentsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#deleteCommentsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteCommentsByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.deleteCommentsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test
   * {@link CommentEntityManagerImpl#deleteCommentsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#deleteCommentsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteCommentsByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    doThrow(new ActivitiException("An error occurred")).when(commentDataManager)
        .deleteCommentsByProcessInstanceId(Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.deleteCommentsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).deleteCommentsByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link CommentEntityManagerImpl#deleteCommentsByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then calls
   * {@link CommentDataManager#deleteCommentsByProcessInstanceId(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#deleteCommentsByProcessInstanceId(String)}
   */
  @Test
  public void testDeleteCommentsByProcessInstanceId_thenCallsDeleteCommentsByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    doNothing().when(commentDataManager).deleteCommentsByProcessInstanceId(Mockito.<String>any());

    // Act
    commentEntityManagerImpl.deleteCommentsByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).deleteCommentsByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String)}
   * with {@code processInstanceId}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String)}
   */
  @Test
  public void testFindCommentsByProcessInstanceIdWithProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String)}
   * with {@code processInstanceId}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String)}
   */
  @Test
  public void testFindCommentsByProcessInstanceIdWithProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String, String)}
   * with {@code processInstanceId}, {@code type}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String, String)}
   */
  @Test
  public void testFindCommentsByProcessInstanceIdWithProcessInstanceIdType() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByProcessInstanceId("42", "Type"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String, String)}
   * with {@code processInstanceId}, {@code type}.
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String, String)}
   */
  @Test
  public void testFindCommentsByProcessInstanceIdWithProcessInstanceIdType2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByProcessInstanceId(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findCommentsByProcessInstanceId("42", "Type"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByProcessInstanceId(eq("42"), eq("Type"));
  }

  /**
   * Test
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String, String)}
   * with {@code processInstanceId}, {@code type}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String, String)}
   */
  @Test
  public void testFindCommentsByProcessInstanceIdWithProcessInstanceIdType_thenReturnEmpty() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByProcessInstanceId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<Comment> actualFindCommentsByProcessInstanceIdResult = commentEntityManagerImpl
        .findCommentsByProcessInstanceId("42", "Type");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByProcessInstanceId(eq("42"), eq("Type"));
    assertTrue(actualFindCommentsByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String)}
   * with {@code processInstanceId}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CommentEntityManagerImpl#findCommentsByProcessInstanceId(String)}
   */
  @Test
  public void testFindCommentsByProcessInstanceIdWithProcessInstanceId_thenReturnEmpty() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(commentDataManager.findCommentsByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<Comment> actualFindCommentsByProcessInstanceIdResult = commentEntityManagerImpl
        .findCommentsByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(commentDataManager).findCommentsByProcessInstanceId(eq("42"));
    assertTrue(actualFindCommentsByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link CommentEntityManagerImpl#findComment(String)}.
   * <ul>
   *   <li>Then return {@link CommentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityManagerImpl#findComment(String)}
   */
  @Test
  public void testFindComment_thenReturnCommentEntityImpl() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    when(commentDataManager.findComment(Mockito.<String>any())).thenReturn(commentEntityImpl);

    // Act
    Comment actualFindCommentResult = commentEntityManagerImpl.findComment("42");

    // Assert
    verify(commentDataManager).findComment(eq("42"));
    assertSame(commentEntityImpl, actualFindCommentResult);
  }

  /**
   * Test {@link CommentEntityManagerImpl#findComment(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityManagerImpl#findComment(String)}
   */
  @Test
  public void testFindComment_thenThrowActivitiException() {
    // Arrange
    when(commentDataManager.findComment(Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findComment("42"));
    verify(commentDataManager).findComment(eq("42"));
  }

  /**
   * Test {@link CommentEntityManagerImpl#findEvent(String)}.
   * <ul>
   *   <li>Then return {@link CommentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityManagerImpl#findEvent(String)}
   */
  @Test
  public void testFindEvent_thenReturnCommentEntityImpl() {
    // Arrange
    CommentEntityImpl commentEntityImpl = new CommentEntityImpl();
    when(commentDataManager.findEvent(Mockito.<String>any())).thenReturn(commentEntityImpl);

    // Act
    Event actualFindEventResult = commentEntityManagerImpl.findEvent("42");

    // Assert
    verify(commentDataManager).findEvent(eq("42"));
    assertSame(commentEntityImpl, actualFindEventResult);
  }

  /**
   * Test {@link CommentEntityManagerImpl#findEvent(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityManagerImpl#findEvent(String)}
   */
  @Test
  public void testFindEvent_thenThrowActivitiException() {
    // Arrange
    when(commentDataManager.findEvent(Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.findEvent("42"));
    verify(commentDataManager).findEvent(eq("42"));
  }

  /**
   * Test {@link CommentEntityManagerImpl#delete(CommentEntity)} with
   * {@code CommentEntity}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityManagerImpl#delete(CommentEntity)}
   */
  @Test
  public void testDeleteWithCommentEntity_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfiguration,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> commentEntityManagerImpl.delete(new CommentEntityImpl()));
  }

  /**
   * Test {@link CommentEntityManagerImpl#checkHistoryEnabled()}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommentEntityManagerImpl#checkHistoryEnabled()}
   */
  @Test
  public void testCheckHistoryEnabled_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> (new CommentEntityManagerImpl(processEngineConfiguration,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration()))).checkHistoryEnabled());
  }
}
