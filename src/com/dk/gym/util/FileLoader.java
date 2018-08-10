package com.dk.gym.util;

import com.dk.gym.controller.RequestContent;
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

    public void loadFile(String saveDirName, RequestContent content) {


        long id = UUID.randomUUID().getLeastSignificantBits();
        String idHex = Long.toHexString(id);

        Part part = content.findPart(PARAM_ICONPATH);
        String fileName = null;

        if (part != null && !part.getSubmittedFileName().isEmpty()) {

            fileName = part.getSubmittedFileName();
            fileName = String.format(PATH_FORMAT, idHex, "_", fileName);

            String uploadDirPath = String.format(PATH_FORMAT, content.findParameter(PARAM_UPLOAD), File.separator, saveDirName);
            String uploadFilePath = String.format(PATH_FORMAT, uploadDirPath, File.separator, fileName);

            LOGGER.log(Level.DEBUG, "uploadDirPath: " + uploadDirPath);
            LOGGER.log(Level.DEBUG, "uploadFilePath: " + uploadFilePath);

            boolean dirCreated = true;

            File fileSaveDir = new File(uploadDirPath);
            if (!fileSaveDir.exists()) {
                dirCreated = fileSaveDir.mkdirs();
            }

            if (dirCreated) {

                try {
                    part.write(uploadFilePath);
                } catch (IOException e) {
                    LOGGER.log(Level.ERROR, e);
                    fileName = null;
                }
            }

            String filePath = String.format(PATH_FORMAT, saveDirName, File.separator, fileName);

            LOGGER.log(Level.DEBUG, "filePath: " + filePath);

            if (filePath != null) {
                content.insertAttribute(PARAM_ICONPATH, filePath);
            }
        }
    }
}
