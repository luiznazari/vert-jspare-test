package vertx.routes;

import org.jspare.vertx.web.annotation.content.Produces;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;
import org.jspare.vertx.web.handler.APIHandler;

@SubRouter("/")
public class IndexRoute extends APIHandler {

	@Get
	@Handler
	@Produces("text/html")
	public void index() {
		success("<h1>Hello from my first Vert.x 3 application</h1>");
	}

}
