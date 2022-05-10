package com.example.starter;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.ReadStream;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

import java.time.LocalDateTime;

public class MainVerticle extends AbstractVerticle {

  private MongoClient mongoClient;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    // Create a Router
    Router router = Router.router(vertx);

    final JsonObject mongoConfig = new JsonObject()
      .put("connection_string", "mongodb://chartdb-service:27017/?readPreference=primary&ssl=false")
      .put("db_name", "shartDB");
    mongoClient = MongoClient.createShared(vertx, mongoConfig);

    mongoClient.getCollections();
    router.route().handler(CorsHandler.create("*")
      .allowedMethod(HttpMethod.GET)
      .allowedMethod(HttpMethod.POST)
      .allowedMethod(HttpMethod.OPTIONS)
      .allowCredentials(true)
      .allowedHeader("Access-Control-Allow-Method")
      .allowedHeader("Access-Control-Allow-Origin")
      .allowedHeader("Access-Control-Allow-Credentials")
      .allowedHeader("Content-Type"));

    router.route("/*").handler(BodyHandler.create());
    // Save shart
    router.post("/")
      .handler(context -> {
          var body = context.getBodyAsJson();
          mongoClient.save("sharts", body, res -> {
            if (res.succeeded()) {
              String id = res.result();
              System.out.println("Inserted shart with id " + id);
              context.json(body);
            } else {
              res.cause().printStackTrace();
              context.json(
                new JsonObject()
                  .put("Error ", res.cause().toString())
              );
            }
          });
        }
      );

    // Get all sharts
    router.get("/byId/")
      .handler(context -> {
          JsonArray objects = new JsonArray();
          var id = context.queryParam("id").get(0);
          System.out.println(id);
          mongoClient.findOne("sharts", new JsonObject().put("_id", id), null, ar -> {
            if (ar.succeeded()) {
              if (ar.result() == null) {
                context.end("404");
              } else {
                context.end(ar.result().toString());
              }
            } else {
              context.end(ar.cause().toString());
            }
          });
        }
      );

    // Get all sharts
    router.get("/")
      .handler(context -> {
          JsonArray objects = new JsonArray();
          ReadStream<JsonObject> stream = mongoClient.findBatch("sharts", new JsonObject());
          stream.exceptionHandler(throwable -> System.err.println(throwable.getMessage()))
            .handler(objects::add)
            .endHandler(v -> {
              context.end(objects.toString());
            });

//          context.json(
//            new JsonObject()
//              .put("This code block ", "should not be reached")
//          );
        }
      );

    // Mount the handler for all incoming requests at every path and HTTP method
    router.route().handler(context -> {
      // Get the address of the request
      String address = context.request().connection().remoteAddress().toString();
      // Get the query parameter "name"
      MultiMap queryParams = context.queryParams();
      String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
      // Write a json response
      context.json(
        new JsonObject()
          .put("404", "not found")
      );
    });

    // Create the HTTP server
    vertx.createHttpServer()
      // Handle every request using the router
      .requestHandler(router)
      // Start listening
      .listen(8888)
      // Print the port
      .onSuccess(server ->
        System.out.println(
          "HTTP server started on port " + server.actualPort()
        )
      );
  }
}
