package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockStorePrimary;
  private TorpedoStore mockStoreSecondary;

  @BeforeEach
  public void init() {

    this.mockStorePrimary = mock(TorpedoStore.class);
    this.mockStoreSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockStorePrimary, mockStoreSecondary);
  }



  //Csak az első tárolóban van torpedó
  //Másodikban nincs
  @Test
  public void fireTorpedo_Single_Success_Only_Primary(){
    // Arrange
    when(mockStorePrimary.getTorpedoCount()).thenReturn(1);
    when(mockStorePrimary.isEmpty()).thenReturn(false);
    when(mockStorePrimary.fire(1)).thenReturn(true);

    when(mockStoreSecondary.getTorpedoCount()).thenReturn(0);
    when(mockStoreSecondary.isEmpty()).thenReturn(true);
    when(mockStoreSecondary.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockStorePrimary, times(1)).fire(1);
    verify(mockStoreSecondary, never()).fire(1);
    //Csak az számít, hogy van-e lőszer, a darabszámra nincs szükség ebben a függvényben.
    verify(mockStoreSecondary, never()).getTorpedoCount();
    verify(mockStorePrimary, never()).getTorpedoCount();

  }


  //Csak a második tárolóban van torpedó
  //Elsőben nincs
  @Test
  public void fireTorpedo_Single_Success_Only_Secondary(){
    // Arrange
    when(mockStorePrimary.getTorpedoCount()).thenReturn(0);
    when(mockStorePrimary.isEmpty()).thenReturn(true);
    when(mockStorePrimary.fire(1)).thenReturn(false);

    when(mockStoreSecondary.getTorpedoCount()).thenReturn(1);
    when(mockStoreSecondary.isEmpty()).thenReturn(false);
    when(mockStoreSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockStoreSecondary, times(1)).fire(1);
    verify(mockStorePrimary, never()).fire(1);

    //Csak az számít, hogy van-e lőszer, a darabszámra nincs szükség ebben a függvényben.
    verify(mockStoreSecondary, never()).getTorpedoCount();
    verify(mockStorePrimary, never()).getTorpedoCount();
  }

  //Mindkettő tárolóban van torpedó
  //Elvileg csak az elsőből fogy lőszer
  @Test
  public void fireTorpedo_Single_Success_notEmpty(){
    // Arrange
    when(mockStorePrimary.getTorpedoCount()).thenReturn(10);
    when(mockStorePrimary.isEmpty()).thenReturn(false);
    when(mockStorePrimary.fire(1)).thenReturn(true);

    when(mockStoreSecondary.getTorpedoCount()).thenReturn(10);
    when(mockStoreSecondary.isEmpty()).thenReturn(false);
    when(mockStoreSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockStorePrimary, times(1)).fire(1);
    verify(mockStoreSecondary, never()).fire(1);

    //Csak az számít, hogy van-e lőszer, a darabszámra nincs szükség ebben a függvényben.
    verify(mockStoreSecondary, never()).getTorpedoCount();
    verify(mockStorePrimary, never()).getTorpedoCount();
  }

  //Mindkét tárolóban van lőszer, de null a FiringMode
  @Test
  public void fireTorpedo_NULL_Fail(){
     // Arrange
     when(mockStorePrimary.getTorpedoCount()).thenReturn(10);
     when(mockStorePrimary.isEmpty()).thenReturn(false);
     when(mockStorePrimary.fire(1)).thenReturn(true);
 
     when(mockStoreSecondary.getTorpedoCount()).thenReturn(10);
     when(mockStoreSecondary.isEmpty()).thenReturn(false);
     when(mockStoreSecondary.fire(1)).thenReturn(true);
 
     // Act
     boolean result = ship.fireTorpedo(null);

     assertEquals(false, result);
     verify(mockStorePrimary, never()).fire(1);
     verify(mockStoreSecondary, never()).fire(1);
 
     //Csak az számít, hogy van-e lőszer, a darabszámra nincs szükség ebben a függvényben.
     verify(mockStoreSecondary, never()).getTorpedoCount();
     verify(mockStorePrimary, never()).getTorpedoCount();

  }

  //ALL mode, de csak az első tárolóban van lőszer
  @Test
  public void fireTorpedo_All_Success_OnlyPrimary(){
    // Arrange
    when(mockStorePrimary.getTorpedoCount()).thenReturn(1);
    when(mockStorePrimary.isEmpty()).thenReturn(false);
    when(mockStorePrimary.fire(1)).thenReturn(true);

    when(mockStoreSecondary.getTorpedoCount()).thenReturn(0);
    when(mockStoreSecondary.isEmpty()).thenReturn(true);
    when(mockStoreSecondary.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockStorePrimary, times(1)).fire(1);
    verify(mockStoreSecondary, never()).fire(anyInt());

    //Csak az számít, hogy van-e lőszer, a darabszámra nincs szükség ebben a függvényben.
    verify(mockStoreSecondary, never()).getTorpedoCount();
    verify(mockStorePrimary, never()).getTorpedoCount();
  }

    //ALL mode, mindkettő tárolóban van lőszer
    @Test
    public void fireTorpedo_All_Both(){
      // Arrange
      when(mockStorePrimary.getTorpedoCount()).thenReturn(1);
      when(mockStorePrimary.isEmpty()).thenReturn(false);
      when(mockStorePrimary.fire(1)).thenReturn(true);
  
      when(mockStoreSecondary.getTorpedoCount()).thenReturn(1);
      when(mockStoreSecondary.isEmpty()).thenReturn(false);
      when(mockStoreSecondary.fire(1)).thenReturn(true);
  
      // Act
      boolean result = ship.fireTorpedo(FiringMode.ALL);
  
      // Assert
      verify(mockStorePrimary, times(1)).fire(1);
      verify(mockStoreSecondary, times(1)).fire(1);
  
      //Csak az számít, hogy van-e lőszer, a darabszámra nincs szükség ebben a függvényben.
      verify(mockStoreSecondary, never()).getTorpedoCount();
      verify(mockStorePrimary, never()).getTorpedoCount();
    }

    @Test
    public void fireLaserFail(){
      boolean result = ship.fireLaser(FiringMode.ALL);
      
      assertEquals(false, result);
    }

  //ALL mode, de csak az első tárolóban van lőszer
  @Test
  public void fireTorpedo_All_Success_OnlySecondary(){
    // Arrange
    when(mockStoreSecondary.getTorpedoCount()).thenReturn(1);
    when(mockStoreSecondary.isEmpty()).thenReturn(false);
    when(mockStoreSecondary.fire(1)).thenReturn(true);

    when(mockStorePrimary.getTorpedoCount()).thenReturn(0);
    when(mockStorePrimary.isEmpty()).thenReturn(true);
    when(mockStorePrimary.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockStorePrimary, times(1)).fire(1);
    verify(mockStoreSecondary, never()).fire(anyInt());

    //Csak az számít, hogy van-e lőszer, a darabszámra nincs szükség ebben a függvényben.
    verify(mockStoreSecondary, never()).getTorpedoCount();
    verify(mockStorePrimary, never()).getTorpedoCount();
  }


}
