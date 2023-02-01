# trader-services-route-one-performance-tests
Performance test suite for `Trader Services`, using [performance-test-runner](https://github.com/hmrc/performance-test-runner) under the hood.


## Running the tests

Prior to executing the tests ensure you have:

* Docker - to start mongo container
* Installed/configured service manager

Run the following command to start the services locally:
```
sm --start TRADER_SERVICES_ALL -f

docker run --rm -d --name mongo -d -p 27017:27017 mongo:3.6
```

## Logging

The template uses [logback.xml](src/test/resources) to configure log levels. The default log level is *WARN*. This can be updated to use a lower level for example *TRACE* to view the requests sent and responses received during the test.

#### Smoke test

It might be useful to try the journey with one user to check that everything works fine before running the full performance test
```
sbt -Dperftest.runSmokeTest=true -DrunLocal=true gatling:test
```

#### Running the performance test
```
sbt -DrunLocal=true gatling:test
```
### Run the example test against staging environment

#### Smoke test
```
sbt -Dperftest.runSmokeTest=true -DrunLocal=false gatling:test
```

#### Run the performance test

To run a full performance test against staging environment, implement a job builder and run the test **only** from Jenkins.

#### Different journey flows 

Import 
Selection of [Cancellation] for [Request Type] 
OR 
[Route 3] for [Route] => Reason page 
All other options will go to Priority goods page
--
Selection of [Hold] for [Route] will lead to Mandatory Transport details page
All other options will not see Transport details page
--

Export 
Selection of [Cancellation]/[Withdrawal] for [Request Type] 
OR 
[Route 3] for [Route] => Reason page 
All other options will go to Priority goods page

Selection of [C1601]/[C1602] for [Request Type] OR [Hold] for [Route] will lead to Mandatory Transport details page
All other options will not see Transport details page

Selection of [C1601]/[C1603] for [Request Type] => Will ask for date of ARRIVAL instead of date of DEPARTURE 
on Transport details page

