docker tag price.streamwise/configserver:0.0.1-SNAPSHOT warpedphantom/price.streamwise.configserver:0.0.1-SNAPSHOT;
docker tag price.streamwise/eurekaserver:0.0.1-SNAPSHOT warpedphantom/price.streamwise.eurekaserver:0.0.1-SNAPSHOT;
docker tag price.streamwise/gatewayserver:0.0.1-SNAPSHOT warpedphantom/price.streamwise.gatewayserver:0.0.1-SNAPSHOT;
docker tag price.streamwise/catalog-service:0.0.1-SNAPSHOT warpedphantom/price.streamwise.catalog-service:0.0.1-SNAPSHOT;
docker tag price.streamwise/consumer-service:0.0.1-SNAPSHOT warpedphantom/price.streamwise.consumer-service:0.0.1-SNAPSHOT;

docker push warpedphantom/price.streamwise.configserver:0.0.1-SNAPSHOT;
docker push warpedphantom/price.streamwise.eurekaserver:0.0.1-SNAPSHOT;
docker push warpedphantom/price.streamwise.gatewayserver:0.0.1-SNAPSHOT;
docker push warpedphantom/price.streamwise.catalog-service:0.0.1-SNAPSHOT;
docker push warpedphantom/price.streamwise.consumer-service:0.0.1-SNAPSHOT;