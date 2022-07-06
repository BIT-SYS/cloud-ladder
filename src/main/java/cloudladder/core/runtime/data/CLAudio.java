package cloudladder.core.runtime.data;
import lombok.Getter;
import okhttp3.*;

import java.io.*;
import java.nio.file.Path;
import javax.sound.sampled.*;

@Getter
public class CLAudio extends CLData {
    private AudioInputStream audio;
    private SourceDataLine sourceDataLine;
    private final Path path;
    private final boolean fromFile;
    private Thread mainThread;
    private volatile boolean running = true;

    public CLAudio(AudioInputStream audio){
        this.audio = audio;
        this.path = null;
        this.fromFile = false;
    }
    public CLAudio(Path path) throws UnsupportedAudioFileException, IOException{
        this.typeName = "audio";
        this.path = path;
        this.fromFile = true;
        this.audio=AudioSystem.getAudioInputStream(path.toFile());
    }
    private void playAudio() throws IOException, InterruptedException {
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
    private void stopAudio(){
        synchronized(this){
            running = false;
            notifyAll();
        }
    }
    private void continueAudio(){
        synchronized(this){
            running = true;
            notifyAll();
        }
    }
    public void play() throws LineUnavailableException {
        if(sourceDataLine == null)
        {
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audio.getFormat(),AudioSystem.NOT_SPECIFIED);
            sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
        }
        sourceDataLine.open(audio.getFormat());
        sourceDataLine.start();
        mainThread = new Thread(new Runnable(){
            public void run()
            {
                try {
                    playAudio();
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
                stopAudio();

            }
        }).start();
    }
    public void continues(){
        new Thread(new Runnable(){
            public void run(){
                continueAudio();
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

    public String postToUrl(String url,File file,String language) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        RequestBody reqBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("lang", language)
                .addFormDataPart("audio", "audio.wav",
                        RequestBody.create(file,MediaType.parse("application/octet-stream")))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(reqBody)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    //剪切出音频的start到end的部分
    public CLAudio cutAudio(int start,int end) throws UnsupportedAudioFileException, IOException{

        File file = new File(this.path.toString());
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
        AudioFormat format = fileFormat.getFormat();
        float bytesPerSecond = format.getFrameSize() *  format.getFrameRate()/1000;
        //跳过并丢弃该音频输入流中指定数量的字节。
        AudioInputStream sourceStream = audio;
        sourceStream.skip((long)(start * bytesPerSecond));
        long framesOfAudioToCopy =(long)( (end-start) *  format.getFrameRate()/1000);
        AudioInputStream cutStream = new AudioInputStream(sourceStream, format, framesOfAudioToCopy);
        return new CLAudio(cutStream);
    }
    //将音频audio1和audio2拼接在一起，audio2接在audio1后面
    public CLAudio mergeAudio(CLAudio audio1,CLAudio audio2) throws IOException {
        AudioInputStream mergeStream =
        new AudioInputStream(
                new SequenceInputStream(audio1.audio, audio2.audio),
                audio1.audio.getFormat(),
                audio1.audio.getFrameLength() + audio2.audio.getFrameLength());
        return new CLAudio(mergeStream);
    }
    //将音频audio1和audio2混音在一起
    public CLAudio mixAudio(CLAudio audio1,CLAudio audio2) throws IOException, UnsupportedAudioFileException{
        byte[] byte1 = audio1.audio.readAllBytes();
        byte[] byte2 = audio2.audio.readAllBytes();
        byte[] out = new byte[byte1.length];
        for (int i=0; i<byte1.length; i++)
        {
            out[i] = (byte) ((byte1[i] + byte2[i]) >> 1);
        }
        InputStream byteArray = new ByteArrayInputStream(out);
        AudioInputStream mixStream = AudioSystem.getAudioInputStream(byteArray);
        return new CLAudio(mixStream);
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
