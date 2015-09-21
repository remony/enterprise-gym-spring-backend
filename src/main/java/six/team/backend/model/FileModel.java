package six.team.backend.model;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import six.team.backend.dao.FileDAO;
import six.team.backend.store.FileStore;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.LinkedList;

public class FileModel {
    private final FileDAO fileDao = new FileDAO();

    public LinkedList<FileStore> getFile(String filename, HttpServletRequest req) {
        return fileDao.getFileDB(filename, req);
    }

    //TODO check if the folder exists, if not then create it

    public Boolean saveToDisk(MultipartFile file, String filename) {
        File directory = new File(System.getProperty("user.home") + "images");
        // If there is no file directory available
        if (!directory.exists()) { // create it
            directory.mkdir();
        }
        if (!checkIfEmpty(file)) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(generateFilepath(file, filename))));
                stream.write(bytes);
                stream.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private Boolean checkIfEmpty(MultipartFile file) {
        return file.isEmpty();
    }

    private String generateFilepath(MultipartFile file, String filename) {
        return System.getProperty("user.home") + "/images/" + filename + "." + FilenameUtils.getExtension(file.getOriginalFilename());
    }

    public String generateName(int length) {
        return  RandomStringUtils.random(length, true, true);
    }

    public FileStore addToDB(MultipartFile file, int eventID, int newsID, int pageID, String filename) {
        FileStore fileStore = new FileStore();
        long time = System.currentTimeMillis();
        fileDao.addToDB(eventID, newsID, pageID, new Date(time), (filename + "." + FilenameUtils.getExtension(file.getOriginalFilename())), filename);
        return fileStore;
    }

    public JSONObject createImageJSON(FileStore file) {
        JSONObject json = new JSONObject();
        json.put("filename", file.getFilename());
        json.put("expanded_filename", file.getExpanded_filename());
        if (file.getEvent_id() != -1) {
            json.put("event_id", file.getEvent_id());
        }
        if (file.getNews_id() != -1) {
            json.put("news_id", file.getNews_id());
        }
        if (file.getPage_id() != -1) {
            json.put("page_id", file.getPage_id());
        }
        json.put("date_uploaded", file.getDate_uploaded());
        json.put("access_url", file.getAccess_url());
        return json;
    }

    public JSONObject getAllFiles(HttpServletRequest req) {
        LinkedList<FileStore> files = fileDao.getAllFiles(req);
        JSONObject json = new JSONObject();
        json.put("files", files);
        return json;
    }

    public LinkedList<FileStore> getAllFilesOfType(String type, String id, HttpServletRequest req) {
        if (type.equals("event")) {
            return fileDao.getAllEventFiles(id, req);
        } else if (type.equals("news")) {
            return fileDao.getAllNewsFiles(id, req);
        } else if (type.equals("page")) {
            return fileDao.getAllPageFiles(id, req);
        }
        return new LinkedList<FileStore>();
    }

    public boolean removeFile(String filename) {
        if (fileDao.removeFromDB(filename)) {
            return true;
        } else {
            return false;
        }
    }
}