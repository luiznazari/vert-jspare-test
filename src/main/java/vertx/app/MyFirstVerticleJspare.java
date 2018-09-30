package vertx.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.jspare.vertx.annotation.Module;
import org.jspare.vertx.annotation.Modules;
import org.jspare.vertx.module.VerticleConfigModule;
import org.jspare.vertx.web.annotation.module.Routes;
import org.jspare.vertx.web.builder.HttpServerBuilder;
import org.jspare.vertx.web.builder.RouterBuilder;
import org.jspare.vertx.web.module.HttpServerModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Routes(scanPackages = "vertx.routes", scanClasspath = true)
@Modules({
	@Module(VerticleConfigModule.class),
	@Module(HttpServerModule.class)
})
public class MyFirstVerticleJspare extends AbstractVerticle {

	private static Logger log = LoggerFactory.getLogger(MyFirstVerticleJspare.class);

	@Override
	public void start(Future<Void> fut) {
		Router router = RouterBuilder
			.create(vertx)
			.addHandler(BodyHandler.create())
//			.routePackages(Arrays.asList("vertx.routes"))
			.route(r -> r.path("/assets/*").handler(StaticHandler.create("assets")))
			.build();

		HttpServerBuilder
			.create(vertx)
			.router(router)
			.build()
			.listen(8000, result -> log.debug("HttpServer listening at port {}", result.result().actualPort()));
	}

}