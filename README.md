# Twoll
Interactive Polls application for the TWEB course

Our project page is available on Github Pages: https://galedric.github.io/HEIG-TWEB-Polls/

### Preview

![](https://galedric.github.io/HEIG-TWEB-Polls/images/feedback_trasn.png)
![](https://galedric.github.io/HEIG-TWEB-Polls/images/poll.png)
![](https://galedric.github.io/HEIG-TWEB-Polls/images/redaction.png)
![](https://galedric.github.io/HEIG-TWEB-Polls/images/list.png)

### Technologies

* **Backend** : Scala (2.11.8) + Play Framework (2.5.11)
* **Build** : sbt (0.13.13)
* **Protocol** : WebSockets
* **Frontend** : HTML5 + Angular2

### Deployment

Technically, the whole compilation and packaging process is handled by sbt.  You just need a recent version (>0.13.5) of sbt and every tools and dependencies will be automatically downloaded. You don't have to install anything, except a Java `jdk` and `sbt`.

You can enter the sbt shell by running `sbt` in the `server` folder. Once in sbt, you can launch the application by using `run` or build a docker using `docker:stage`. Alternatively, `docker:publishLocal` publishes the image to the local docker daemon.

For simplicity, we have included a pre-built package of our application, ready to be built into a docker image, in the `docker` folder. You can simply run `docker build -t twoll .` in that folder to build a local image for our application. Use `docker run -p 9000:9000 twoll` to run it.

### Developers

This webapp is made by [Bastien Clément](https://github.com/galedric) and [Matthieu Villard](https://github.com/matthieuVillard).
