package com.dk.gym.util;

import com.dk.gym.controller.SessionRequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.dk.gym.service.ParamConstant.*;

public class FileLoader {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PATH_FORMAT = "%s%s%s";

    public void loadFile(String saveDirName, SessionRequestContent content) {

        long id = UUID.randomUUID().getLeastSignificantBits();
        String idHex = Long.toHexString(id);

        Part part = content.findPart(PARAM_ICONPATH);
        String fileName;

        if (part != null && !part.getSubmittedFileName().isEmpty()) {

            fileName = part.getSubmittedFileName();
            fileName = String.format(PATH_FORMAT, idHex, "_", fileName);

            String uploadDirPath = String.format(PATH_FORMAT, content.findParameter(PARAM_UPLOAD), File.separator, saveDirName);
            String uploadFilePath = String.format(PATH_FORMAT, uploadDirPath, File.separator, fileName);

            boolean dixExist = true;

            File fileSaveDir = new File(uploadDirPath);
            if (!fileSaveDir.exists()) {
                dixExist = fileSaveDir.mkdirs();
            }
            if (dixExist) {
                try {
                    part.write(uploadFilePath);

                    String filePath = String.format(PATH_FORMAT, saveDirName, File.separator, fileName);

                    content.insertAttribute(PARAM_ICONPATH, filePath);

                    LOGGER.log(Level.DEBUG, "filePath: " + filePath);
                } catch (IOException e) {
                    LOGGER.log(Level.ERROR, "Writing part into file", e);
                }
            }
        }
    }
}
