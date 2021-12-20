echo %cd%
java -Djava.library.path=%cd%/lib/  -Xms64m -Xmx128m  -jar %cd%/monitor-agent-release.jar
