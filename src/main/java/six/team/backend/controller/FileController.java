package six.team.backend.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import six.team.backend.model.FileModel;

import javax.servlet.http.HttpServletRequest;


@RestController
@ResponseBody
public class FileController {

    private final FileModel fileModel = new FileModel();

    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET,
            value ="/files")
    public @ResponseBody
    ResponseEntity<String> getFiles() {
        JSONObject json = fileModel.getAllFiles();
        return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
    }

    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            value ="/files")
    public @ResponseBody
    ResponseEntity<String> uploadFiles() {
        return new ResponseEntity<String>(HttpStatus.I_AM_A_TEAPOT);
    }

    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET,
            value ="/files/{type}/{id}")
    public @ResponseBody
    ResponseEntity<String> filesImages(@PathVariable("type") String type, @PathVariable("id") String id) {
        JSONObject json = new JSONObject();
        json.put("files", fileModel.getAllFilesOfType(type, id));
        return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
    }

    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            value ="/files/{type}/{id}")
    public @ResponseBody
    ResponseEntity<String> filesImageError(@PathVariable("type") String type, @PathVariable("id") String id) {
        return new ResponseEntity<String>(HttpStatus.I_AM_A_TEAPOT);
    }

    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET,
            value ="/file/{filename}")
    public @ResponseBody
    ResponseEntity<String> getImage(HttpServletRequest request, @PathVariable("filename") String filename) {
        JSONObject json = fileModel.createImageJSON(fileModel.getFile(filename));
           return new ResponseEntity<String> (json.toString(), HttpStatus.OK);
    }

    @RequestMapping(value="/file/upload", method=RequestMethod.POST)
     public @ResponseBody String handleFileUpload(HttpServletRequest req, @RequestParam("file") MultipartFile file, @RequestParam("event_id") int eventID, @RequestParam("news_id") int newsID, @RequestParam("page_id") int pageID){
        String filename = fileModel.generateName(8);
        fileModel.saveToDisk(file, filename);
        fileModel.addToDB(file, eventID, newsID, pageID, filename);
        return "Success";
    }

    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            value ="/file/{filename}/delete")
    public @ResponseBody
    ResponseEntity<String> deleteImage(HttpServletRequest request, @PathVariable("filename") String filename) {
        fileModel.removeFile(filename);
        return new ResponseEntity<String> ("deleted", HttpStatus.OK);
    }
}