package six.team.backend.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.PageJsonGen;
import six.team.backend.model.Index;
import six.team.backend.store.IndexStore;
import six.team.backend.store.PageStore;

@Controller
@RequestMapping("/")
public class IndexContoller {
	@RequestMapping(method = RequestMethod.GET)
	public
	@ResponseBody
	ResponseEntity<IndexStore> index() {
			IndexStore index = Index.getIndex();
			return new ResponseEntity<IndexStore>(index, HttpStatus.OK);
		}
}
