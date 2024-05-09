package com.pepop99.emaildispatcher.handlers;

import com.opencsv.CSVReader;
import com.pepop99.emaildispatcher.metadata.AppMeta;
import com.pepop99.emaildispatcher.metadata.GrantMeta;
import com.pepop99.emaildispatcher.metadata.GrantType;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
                            final String fileName = new File(item.getName()).getName();
                            final String filePath = "/tmp" + File.separator + fileName;
                            final File uploadedFile = new File(filePath);
                            item.write(uploadedFile);
                            readCSV(uploadedFile);
                        }
                    }
                } catch (FileUploadException e) {
                    respond("Error uploading file", httpServletResponse, 500);
                    throw new RuntimeException(e);
                } catch (IllegalArgumentException e) {
                    respond(e.getMessage(), httpServletResponse, 500);
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    respond("Error parsing CSV", httpServletResponse, 500);
                    throw new RuntimeException(e);
                }
                respond("Upload successful", httpServletResponse, 200);
            }
            case APIHandlerConstants.URI_CSV_FETCH -> {
                respond(AppMeta.instance.csvData.fetchAllData().toString(), httpServletResponse, 200);
            }
            default -> respond("Invalid request", httpServletResponse, 404);
        }
    }

    private void readCSV(File uploadedFile) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(uploadedFile))) {
            final String[] headers = reader.readNext();
            AppMeta.instance.csvData = new CSVData(headers);
            String[] data;
            while ((data = reader.readNext()) != null) {
                final GrantMeta grantMeta = new GrantMeta();
                for (int i = 0; i < headers.length; i++) {
                    if (StringUtils.equalsIgnoreCase(headers[i], "Grant Type")) {
                        parseGrantType(grantMeta, data[i]);
                    } else if (StringUtils.equalsIgnoreCase(headers[i], "tags")) {
                        grantMeta.setTags(data[i].split(","));
                    } else {
                        grantMeta.getOtherData().put(headers[i], data[i]);
                    }
                }
                AppMeta.instance.csvData.addEntry(grantMeta);
            }
        }
    }

    private static void parseGrantType(GrantMeta grantMeta, String data) {
        try {
            grantMeta.setGrantType(GrantType.valueOf(data));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Illegal grant type " + data + " specified in CSV", e);
        }
    }
}
