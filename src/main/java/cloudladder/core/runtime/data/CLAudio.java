package cloudladder.core.runtime.data;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

@Getter
public class CLAudio extends CLData {
    private final AudioInputStream audio;
    private SourceDataLine sourceDataLine;
    private final Path path;
    private final boolean fromFile;
    private Thread mainThread;
    private volatile boolean running = true;

    public CLAudio(AudioInputStream audio)
    {
        this.audio = audio;
        this.path = null;
        this.fromFile = false;
    }
    public CLAudio(Path path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.typeName = "audio";
        this.path = path;
        this.fromFile = true;
        this.audio=AudioSystem.getAudioInputStream(path.toFile());
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audio.getFormat(),AudioSystem.NOT_SPECIFIED);
        sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(audio.getFormat());
        sourceDataLine.start();
    }
    private void playMusic() throws IOException, InterruptedException {
        synchronized(this){
            running = true;
        }
        int count;
        byte tempBuff[] = new byte[1024];

        while((count = audio.read(tempBuff,0,tempBuff.length)) != -1){
            synchronized(this){
                while(!running)
                    wait();
            }
            sourceDataLine.write(tempBuff,0,count);

        }
        sourceDataLine.drain();
        sourceDataLine.close();
        audio.close();
    }
    private void stopMusic(){
        synchronized(this){
            running = false;
            notifyAll();
        }
    }
    private void continueMusic(){
        synchronized(this){
            running = true;
            notifyAll();
        }
    }
    public void play() throws IOException, InterruptedException {
        mainThread = new Thread(new Runnable(){
            public void run()
            {
                try {
                    playMusic();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mainThread.start();
    }
    public void stop(){
        new Thread(new Runnable(){
            public void run(){
                stopMusic();

            }
        }).start();
    }
    public void continues(){
        new Thread(new Runnable(){
            public void run(){
                continueMusic();
            }
        }).start();
    }
    public void save(Path path) {
        File file = new File(path.toUri());
        try {
            AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
            AudioSystem.write(audio, fileType,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String binary() {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            AudioSystem.write(audio,AudioFileFormat.Type.WAVE,stream);
            return stream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    @Override
    public String toString() {
        if (this.fromFile) {
            return "Audio[" + this.path.toString() + "]";
        } else {
            return "Audio[" + this.audio.toString() + "]";
        }
    }
}
