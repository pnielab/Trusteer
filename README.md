# Trusteer
Implementation of Trusteer task
I chose Spring boot to be the specific implementation of the task.
Cons: too heavy for the specific task, a lot of jars are imported and do not use them, we can Easy create this task with pure java and import only the specific jars that we need. Also using spring is less efficient since each invocation to a given method is actually a proxy call.
Pros: quick implementation, large community support, Easy to maintain, easy to configure via external configuration files, manage object life cycle, easy to deploy and wrap. And most importantly can be enhanced if needed to other solutions quickly. 
We can change the map that holds files state (hash) to different implementation very easy, we can configure to use implementation such as distributed map or use distributed caching or persist to disk or DB by changing data source from configuration file only, the signature is the same but different implementation can be change externally. (Dependency Injection- implementation at runtime),
We can add very quickly support to a rest api for other features if needed.
Using Spring we can easy create integration tests and it I very easy to unit test.
