/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ascension;



import java.io.File;
 
 import javax.sound.sampled.AudioFormat;
 import javax.sound.sampled.AudioInputStream;
 import javax.sound.sampled.AudioSystem;
 import javax.sound.sampled.Clip;
 import javax.sound.sampled.DataLine;
 
 
 public class Musique {    
 
     private Clip clip = null;
     private AudioInputStream audioStream = null;
 
     public Musique(File f) throws Exception{
         
         audioStream = AudioSystem.getAudioInputStream(f);//recuperation d'un stream de type audo sur le fichier
         AudioFormat audioFormat = audioStream.getFormat();//recuperation du format de son
         //recuperation du son que l'on va stoquer dans un oblet de type clip
         DataLine.Info info = new DataLine.Info(
                 Clip.class, audioStream.getFormat(),
                 ((int) audioStream.getFrameLength() * audioFormat.getFrameSize()));
         //recuperation d'une instance de type Clip
         clip = (Clip) AudioSystem.getLine(info);
 
     }
 
     /**
      * Ouverture du flux audio
      * @return On retourne <code>false</code> si il y a eu une erreure
      */
     public boolean open(){
         if(clip != null && !clip.isOpen())//teste pour ne pas le faire dans le vent
             try {
                 clip.open(audioStream);
             } catch (Exception e) {
                 e.printStackTrace();//pour le debugage
                 return false;
             }
         return true;
     }
 
     /**
      * Fermeture du flux audio
      */
     public void close(){
         if(clip != null && clip.isOpen())//teste pour ne pas le faire dans le vent
             clip.close();
     }
 
     /**
      * On joue le son
      */
     public void play(){
         if(clip != null && clip.isOpen())
             clip.start();
     }
 
     /**
      * On arrete le son
      */
     public void stop(){
         if(clip != null && clip.isOpen())
             clip.stop();
     }
 
 
  
 }