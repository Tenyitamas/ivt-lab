package hu.bme.mit.spaceship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;

  private TorpedoStore primaryTorpedoStoreMock;
  private TorpedoStore secondaryTorpedoStoreMock;
  @BeforeEach
  public void init(){
    primaryTorpedoStoreMock = Mockito.mock(TorpedoStore.class);
    secondaryTorpedoStoreMock = Mockito.mock(TorpedoStore.class);

    this.ship = new GT4500(primaryTorpedoStoreMock, secondaryTorpedoStoreMock);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    Mockito.when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    Mockito.when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryTorpedoStoreMock).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    Mockito.when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    Mockito.when(primaryTorpedoStoreMock.getTorpedoCount()).thenReturn(10);
    Mockito.when(primaryTorpedoStoreMock.fire(10)).thenReturn(true);

    Mockito.when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    Mockito.when(secondaryTorpedoStoreMock.getTorpedoCount()).thenReturn(11);
    Mockito.when(secondaryTorpedoStoreMock.fire(11)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primaryTorpedoStoreMock).fire(10);
    verify(secondaryTorpedoStoreMock).fire(11);
  }

  @Test
  public void fireTorpedo_Single_WithEmptyTorpedoStores() {
    Mockito.when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    Mockito.when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);

    verify(primaryTorpedoStoreMock, never()).fire(anyInt());
    verify(secondaryTorpedoStoreMock, never()).fire(anyInt());

  }

  @Test
  public void fireTorpedo_Single_WithEmptySingleTorpedoStore() {
    Mockito.when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    Mockito.when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    Mockito.when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);

    verify(primaryTorpedoStoreMock, never()).fire(anyInt());
    verify(secondaryTorpedoStoreMock).fire(1);

  }

  @Test
  public void fireTorpedo_Single_WithNonEmptySingleTorpedoStores_FireTwice() {
    Mockito.when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    Mockito.when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    Mockito.when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);
    Mockito.when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    verify(primaryTorpedoStoreMock).fire(1);
    verify(secondaryTorpedoStoreMock).fire(1);
  }
  @Test
  public void fireTorpedo_Single_WithEmptySecondaryTorpedoStores_FireTwice() {
    Mockito.when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    Mockito.when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    Mockito.when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);
    Mockito.when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    verify(primaryTorpedoStoreMock, times(2)).fire(1);
    verify(secondaryTorpedoStoreMock, never()).fire(anyInt());
  }
  @Test
  public void fireTorpedo_Single_WithOnePrimaryTorpedoStores_FireTwice() {
    Mockito.when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    Mockito.when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    Mockito.when(secondaryTorpedoStoreMock.fire(1)).thenReturn(true);
    Mockito.when(primaryTorpedoStoreMock.fire(1)).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);
    Mockito.when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);

    verify(primaryTorpedoStoreMock, times(1)).fire(1);
    verify(secondaryTorpedoStoreMock, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_WithEmptyPrimaryTorpedoStore(){
    // Arrange
    Mockito.when(primaryTorpedoStoreMock.isEmpty()).thenReturn(true);

    Mockito.when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    Mockito.when(secondaryTorpedoStoreMock.getTorpedoCount()).thenReturn(11);
    Mockito.when(secondaryTorpedoStoreMock.fire(11)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primaryTorpedoStoreMock, never()).fire(anyInt());
    verify(secondaryTorpedoStoreMock).fire(11);
  }

  @Test
  public void fireTorpedo_All_WithEmptySecondaryTorpedoStore(){
    // Arrange
    Mockito.when(secondaryTorpedoStoreMock.isEmpty()).thenReturn(true);

    Mockito.when(primaryTorpedoStoreMock.isEmpty()).thenReturn(false);
    Mockito.when(primaryTorpedoStoreMock.getTorpedoCount()).thenReturn(11);
    Mockito.when(primaryTorpedoStoreMock.fire(11)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(secondaryTorpedoStoreMock, never()).fire(anyInt());
    verify(primaryTorpedoStoreMock).fire(11);
  }

  @Test
  public void fire_laser_should_return_false() {
    assertEquals(false, ship.fireLaser(FiringMode.SINGLE));
  }

  @Test
  public void firingMode_null_should_return_false() {
    assertEquals(false, ship.fireTorpedo(FiringMode.ALTERNATING));
  }
}
