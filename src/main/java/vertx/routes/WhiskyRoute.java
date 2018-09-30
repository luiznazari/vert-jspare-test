package vertx.routes;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.JsonParser;
import org.jspare.vertx.ext.jackson.datatype.JsonObjectBuilder;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Put;
import vertx.domain.Whisky;
import org.jspare.vertx.web.annotation.content.Produces;
import org.jspare.vertx.web.annotation.handling.Parameter;
import org.jspare.vertx.web.annotation.method.Delete;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.method.Post;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;
import org.jspare.vertx.web.handler.APIHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@SubRouter("/api/whiskies")
public class WhiskyRoute extends APIHandler {

	private static Map<Integer, Whisky> products = new LinkedHashMap<>();

	static {
		Whisky bowmore = new Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay");
		products.put(bowmore.getId(), bowmore);
		Whisky talisker = new Whisky("Talisker 57° North", "Scotland, Island");
		products.put(talisker.getId(), talisker);
	}

	@Get
	@Handler
	@Produces("application/json; charset=utf-8")
	public void getAll() {
		success(Json.encodePrettily(products.values()));
	}

	@Post
	@Handler
	@Produces("application/json; charset=utf-8")
	public void addOne(JsonObject jsonObject) throws IOException {
		Whisky whisky = new ObjectMapper().readValue(jsonObject.toString(), Whisky.class);
		products.put(whisky.getId(), whisky);

		jsonObject.put("id", whisky.getId());
		created(jsonObject);
	}

	@Put("/:id")
	@Handler
	@Produces("application/json; charset=utf-8")
	public void editOne(@Parameter("id") Integer id, JsonObject jsonObject) throws IOException {
		if (id == null) {
			badRequest();
		} else {
			Whisky newWhisky = new ObjectMapper().readValue(jsonObject.toString(), Whisky.class);
			Whisky whisky = products.get(id);
			whisky.setName(newWhisky.getName());
			whisky.setOrigin(newWhisky.getOrigin());

			success(Json.encodePrettily(whisky));
		}
	}


	@Delete("/:id")
	@Handler
	public void deleteOne(@Parameter("id") Integer id) {
		if (id == null) {
			badRequest();
		} else {
			products.remove(id);
			noContent();
		}
	}

}
