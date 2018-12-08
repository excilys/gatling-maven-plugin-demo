gatling-maven-plugin-demo
=========================

Simple showcase of a maven project using the gatling-maven-plugin.

To test it out, simply execute the following command:

    $mvn gatling:test -Dgatling.simulationClass=computerdatabase.BasicSimulation
    $mvn gatling:test -Dgatling.simulationClass=computerdatabase.BasicSimulationSample2
    $mvn gatling:test -Dgatling.simulationClass=computerdatabase.BasicSimulationSample2 -Dusers=5 -DdurationInSecs=30

or simply:

    $mvn gatling:test
