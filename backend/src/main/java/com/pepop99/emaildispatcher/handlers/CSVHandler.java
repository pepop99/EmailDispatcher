package com.pepop99.emaildispatcher.handlers;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHandler extends BaseHandler {
    @Override
    public void process(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        final String uri = httpServletRequest.getRequestURI();
        switch (uri) {
            case APIHandlerConstants.URI_CSV_UPLOAD -> {
                boolean isMultipartContent = ServletFileUpload.isMultipartContent(httpServletRequest);
                if (!isMultipartContent) {
                    respond("Incorrect file format", httpServletResponse, 400);
                }
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                try {
                    List<FileItem> items = upload.parseRequest(httpServletRequest);
                    for (FileItem item : items) {
                        if (!item.isFormField()) {
                            String fileName = new File(item.getName()).getName();
                            String filePath = "/tmp" + File.separator + fileName;
                            File uploadedFile = new File(filePath);
                            System.out.println(filePath);
                            // saves the file to upload directory
                            item.write(uploadedFile);
                            List<List<String>> records = new ArrayList<>();
                            try (BufferedReader br = new BufferedReader(new FileReader(uploadedFile))) {
                                String line;
                                while ((line = br.readLine()) != null) {
                                    String[] values = line.split(",");
                                    records.add(Arrays.asList(values));
                                }
                            }
                            System.out.println(records);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                httpServletRequest.getInputStream();
                respond("Upload successful", httpServletResponse, 200);
            }
            case APIHandlerConstants.URI_CSV_FETCH -> {

            }
            default -> respond("Invalid request", httpServletResponse, 404);
        }
    }
}
