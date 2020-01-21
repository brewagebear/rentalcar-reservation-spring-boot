package com.toyota.rentalcar.dev.controllers;

import com.toyota.rentalcar.dev.dto.UploadFileResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
public abstract class SessionController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    public void uploadFileSession(HttpSession session, List<UploadFileResponse> files){
        if(session.isNew()){
            logger.info("New Session : " + session.getId());
            session.setAttribute("files", files);
        } else {
            session.setAttribute("files", files);
            logger.info("Reused Session : " + session.getId() + " files is set to: " + session.getAttribute("files"));
        }
    }
}
