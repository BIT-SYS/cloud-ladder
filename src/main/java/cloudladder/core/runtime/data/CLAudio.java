package cloudladder.core.runtime.data;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public class CLAudio extends CLData{
    private Path    path;

    private Player player;

    public CLAudio(){
        Path p = null;
    }

    public CLAudio(Path p){
        this.path = p;
        try {
            this.player = new Player(new BufferedInputStream(new FileInputStream(p.toFile())));
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void play(){
        new Thread(()->{
            try {
                this.player.play();
            } catch (JavaLayerException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void save(Path p){

    }

    public String toString(){
        if (this.path == null){
            return "Audio[" + this.path.toString() + "]";
        }
        else{
            return "Audio[" + this.player.toString() + "]";
        }
    }
}
