package cloudladder.core.object;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import okhttp3.*;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@CLObjectAnnotation(typeIdentifier = "audio")
public class CLAudio extends CLObject {
    private AudioInputStream audio;
    private SourceDataLine sourceDataLine;
    private final Path path;
    private final boolean fromFile;
    private Thread mainThread;
    private volatile boolean running = true;

    public CLAudio(AudioInputStream audio) {
        this.audio = audio;
        this.path = null;
        this.fromFile = false;
    }

    public CLAudio(Path path) throws UnsupportedAudioFileException, IOException {
        this.fromFile = true;
        String tmpType = TypeChecking(path.toString());
        if (tmpType.equals(".wav")) {
            this.path = path;
            this.audio = AudioSystem.getAudioInputStream(path.toFile());
            if (audio == null) {
                System.out.println("文件不存在");
            }
        } else if (tmpType.equals(".mp3")) {
            String newPath = mp3ToWav(path.toString());
            File wavFile = new File(newPath);
            this.path = Paths.get(newPath);
            this.audio = AudioSystem.getAudioInputStream(wavFile);
            if (audio == null) {
                System.out.println("文件不存在");
            }
        } else {
            this.path = null;
            System.out.println("不支持的文件格式，请使用wav,pcm或mp3文件");
        }
    }

    private String TypeChecking(String path) {
        int p = path.lastIndexOf('.');
        return path.substring(p).toLowerCase();
    }

    private String mp3ToWav(String path) throws IOException, UnsupportedAudioFileException {
        int p = path.lastIndexOf('.');
        String newPath = path.substring(0, p) + ".mp3";
        String retPath = path.substring(0, p) + ".wav";
        File mp3File = new File(newPath);
        MpegAudioFileReader mp = new MpegAudioFileReader();
        AudioInputStream mp3Data = mp.getAudioInputStream(mp3File);
        AudioFormat baseFormat = mp3Data.getFormat();
        AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
        AudioInputStream pcmData = AudioSystem.getAudioInputStream(targetFormat, mp3Data);
        AudioSystem.write(pcmData, AudioFileFormat.Type.WAVE, new File(retPath));
        return retPath;
    }

    public void wavToMp3(String newPath) throws EncoderException, IOException {
        File mp3File = new File("cloudladder/" + newPath);
        File wavFile = new File(this.path.toString());
        AudioAttributes A_attr = new AudioAttributes();
        A_attr.setCodec("libmp3lame");
        A_attr.setBitRate(36000); //音频比率 MP3默认是1280000
        A_attr.setChannels(2);
        A_attr.setSamplingRate(44100);
        EncodingAttributes E_attr = new EncodingAttributes();
        E_attr.setFormat("mp3");
        E_attr.setAudioAttributes(A_attr);
        Encoder encoder = new Encoder();
        encoder.encode(wavFile, mp3File, E_attr);
        mp3File.getAbsoluteFile().createNewFile();
    }

    private void playAudio() throws IOException, InterruptedException {
        synchronized (this) {
            running = true;
        }
        int count;
        byte tempBuff[] = new byte[1024];

        while ((count = audio.read(tempBuff, 0, tempBuff.length)) != -1) {
            synchronized (this) {
                while (!running)
                    wait();
            }
            sourceDataLine.write(tempBuff, 0, count);

        }
        sourceDataLine.drain();
        sourceDataLine.close();
        audio.close();
    }

    private void stopAudio() {
        synchronized (this) {
            running = false;
            notifyAll();
        }
    }

    private void continueAudio() {
        synchronized (this) {
            running = true;
            notifyAll();
        }
    }

    public void play() throws LineUnavailableException {
        if (audio == null) {
            System.out.println("无音频可播放");
            return;
        }
        if (sourceDataLine == null) {
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audio.getFormat(), AudioSystem.NOT_SPECIFIED);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        }
        sourceDataLine.open(audio.getFormat());
        sourceDataLine.start();
        mainThread = new Thread(new Runnable() {
            public void run() {
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

    public void stop() {
        new Thread(new Runnable() {
            public void run() {
                stopAudio();

            }
        }).start();
    }

    public void continues() {
        new Thread(new Runnable() {
            public void run() {
                continueAudio();
            }
        }).start();
    }

    public void save(Path path) {
        File file = new File(path.toUri());
        try {
            AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
            AudioSystem.write(audio, fileType, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String binary() {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            AudioSystem.write(audio, AudioFileFormat.Type.WAVE, stream);
            return stream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public Response postToUrl(String url, File file, String aParam) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        RequestBody reqBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("aParam", aParam)
                .addFormDataPart("audio", "audio.wav",
                        RequestBody.create(file, MediaType.parse("application/octet-stream")))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(reqBody)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    //剪切出音频的start到end的部分
    public CLAudio cutAudio(int start, int end) throws UnsupportedAudioFileException, IOException {
        File file = new File(this.path.toString());
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
        AudioFormat format = fileFormat.getFormat();
        float bytesPerSecond = format.getFrameSize() * format.getFrameRate() / 1000;
        //跳过并丢弃该音频输入流中指定数量的字节。
        AudioInputStream sourceStream = audio;
        sourceStream.skip((long) (start * bytesPerSecond));
        long framesOfAudioToCopy = (long) ((end - start) * format.getFrameRate() / 1000);
        AudioInputStream cutStream = new AudioInputStream(sourceStream, format, framesOfAudioToCopy);
        return new CLAudio(cutStream);
    }

    //将音频audio1和audio2拼接在一起，audio2接在audio1后面
    public CLAudio mergeAudio(CLAudio audio1, CLAudio audio2) throws IOException {
        AudioInputStream mergeStream =
                new AudioInputStream(
                        new SequenceInputStream(audio1.audio, audio2.audio),
                        audio1.audio.getFormat(),
                        audio1.audio.getFrameLength() + audio2.audio.getFrameLength());
        return new CLAudio(mergeStream);
    }

    //将音频audio1和audio2混音在一起
    public CLAudio mixAudio(CLAudio audio1, CLAudio audio2) throws IOException, UnsupportedAudioFileException {
        byte[] byte1 = audio1.audio.readAllBytes();
        byte[] byte2 = audio2.audio.readAllBytes();
        byte[] out = new byte[byte1.length];
        for (int i = 0; i < byte1.length; i++) {
            out[i] = (byte) ((byte1[i] + byte2[i]) >> 1);
        }
        InputStream byteArray = new ByteArrayInputStream(out);
        AudioInputStream mixStream = AudioSystem.getAudioInputStream(byteArray);
        return new CLAudio(mixStream);
    }

    @Override
    public String getTypeIdentifier() {
        return "audio";
    }

    @Override
    public String defaultStringify() {
        return "audio@" + this.id;
    }
}
