package com.wzh.util;

import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;

import java.io.IOException;

@Slf4j
public class MusicTimeUtil {
    /**
     * 音频文件获取文件时长
     * @param fileName
     * @return
     */
    public static String getAudioInfo(String fileName) {
        String strLen = "0";
        try {

            MP3File file = new MP3File(fileName);
            MP3AudioHeader audioHeader = (MP3AudioHeader)file.getAudioHeader();
            strLen = audioHeader.getTrackLengthAsString();
            log.info(strLen);
            int intLen = audioHeader.getTrackLength();
            log.info(String.valueOf(intLen));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }
        return strLen;
    }
}
