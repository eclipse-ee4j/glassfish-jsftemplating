# JSFTemplating

Jakarta Faces VDL/Templating library to create reusable UI components.

JSFTemplating is an alternative VDL (View Description Language) for Jakarta Faces. It replaces Facelets or the (now deprecated) Jakarta Server Pages.
Practically, JSTTemplating is only used by the GlassFish admin console. It's solely retained for keeping that working, and therefor JSFTemplating is 
absolutely not recommended to be used for new development of any kind.


## Build

```bash
$ mvn install
```

## Release Example Howto

Remember that this is just an example and it is your responsibility to choose the right values.

1. Create a release branch and push it to the GitHub repository.
```
git checkout -b RELEASE_123 eclipse/master
git push eclipse RELEASE_123
```
2. Open [CI Release Job](https://ci.eclipse.org/glassfish/view/Release/job/all-projects-release-healthy)
3. Click [Build with parameters](https://ci.eclipse.org/glassfish/view/Release/job/all-projects-release-healthy/build) in menu.
    - `PROJECT` is `jsftemplating`
    - `RELEASE_VERSION` = `1.2.3`
    - `NEXT_VERSION` = `2.0.0-SNAPSHOT`
    - `BRANCH` = `RELEASE_123`
    - `DRY_RUN` can be used to try the build without any push or publish event.
    - click [Build] button.
4. Wait for it to finish successfully
5. Verify that everything was done:
    1. Verify that the deployment is present in [Maven Central Deployments](https://central.sonatype.com/publishing/deployments)
       - It is possible that you will not have permissions to visit the namespace. Ask project leads or
         check if expected artifacts made it to Maven Central, then this was obviously successful.
    2. Verify that a new [1.2.3](https://github.com/eclipse-ee4j/glassfish-jsftemplating/tags) tag was created.
    3. Verify that the release branch changed the number to the `NEXT_VERSION` value.

6. Create a Draft PR based on this branch.
7. Create the release on GitHub: click ["Draft a new release"](https://github.com/eclipse-ee4j/glassfish-jsftemplating/releases)
8. Open [Maven Central Deployments](https://central.sonatype.com/publishing/deployments) and click the Publish button.
   Maven Central then distributes artifacts so they will become reachable to anyone referring Maven Central Repository.
9. Verify that it's present in [Maven Central](https://repo.maven.apache.org/maven2/org/glassfish/jsftemplating/jsftemplating/)
   (usually takes few minutes now)
10. Now you can refer this dependency in other projects. Not that it may take a while until websites notice
   the new version - until then it will not be available in search indexes.