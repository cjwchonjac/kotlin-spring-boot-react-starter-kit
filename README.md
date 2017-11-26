# kotlin-spring-boot-react-starter-kit
Starter kit of Spring-React. This project includes base implementation of posting-feature app. it is sample application of isomorhpic rendering with nashorn script engine.

## How to build:
- Clone the repo: `git clone https://github.com/cjwchonjac/kotlin-spring-boot-react-starter-kit.git`
- Install [Node](https://nodejs.org) and [npm](https://www.npmjs.com)
- Install node moduels `npm install`
- Run npm script `npm run build`
- Run Spring-Boot server `./gradlew bootRun`

## Thread Local vs Object Pool
Most other sample projects have been implemented by using thread locals of Nashorn script engines to ensure thread safety. but this project uses pooling of Nashorn script engines to ensure thread safety. Because HTML rendering process is definetely CPU-bound task, Object Pooling has no performance problem and is more efficient in terms of memory consumption.
