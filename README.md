ADDService: Microservice to do add, SubService: Microservice to subtract, EurekaSserver: Spring Netflix Eureka instance, MicroREST: Give a facade pattern tp access microservice.

Steps:
Bring up the Eureka Server
Then bring up the REST Services
To access use : localhost:8081/ops?ops=add&num1=100&num2=700
