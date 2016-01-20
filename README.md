# ShareMyWeek
An friendly web app for picking events based on everyone's availability and budget

## Development

#### IDE

You'll need a version of [Maven] 3.1 for your IDE. You can install a IDE that comes with it, or install your own.

So far a good work environment consist of [Eclipse] EE for back-end and [Atom] for front-end. [Intellij] works well in both areas. 
Metadata directories have been added to .gitignore

#### Front-end Guide

>It is requried that all JavaScript libraries for development are imported using [NPM]. The only exception is if the library is not in NPM. In that case download and add it to js/lib/

1. Download and install latest v5 [Node.js]
2. To install an package, start Node.js bash and cd into (repository)/src/main/resources/static/js

Enter the following:
```sh
$ npm install <package-name>
```
To see current list of installed packages
```sh
$ npm list
```

Then commit and push package.json

>Use the JavaScript libraries only in .js files. Do not add script tags into the HTML files. 
You can use a library by adding [require] statements in any .js file:

```javascript
$ var packageName = require('package-name');
```

> All JavaScript sources is bundled together into a single file with its dependencies. Project utilizes a [Frontend Maven plugin] and [Webpack] to build JavaScript sources.

#### Building

1. Import the project into your preferred IDE
2. Run Maven install on project.

> Run Maven install when pom.xml changes

For JavaScript, right now only workflow is to refresh the js/ directory to ensure it is built and reloaded. If you are modifying front-end code in external editor you need to refresh the directories regardless to ensure auto-build. Intellij is always manual.

#### Running the application

The preferred way is setting up an launch configuration in the IDE, so you can enable debug mode. You'll need to install [MongoDB]. By default the application will access mongodb://localhost/test. Please follow the official documentation for an install guide. You can set it as a service, if you like.

#### Testing

Soon.

#### Deployment

Soon.

[Maven]: https://maven.apache.org/
[Atom]: https://atom.io/
[Eclipse]: http://eclipse.org
[Intellij]: https://www.jetbrains.com/idea/
[NPM]: https://www.npmjs.com/
[Node.js]: http://nodejs.org/dist/latest-v5.x/win-x64/
[require]: http://requirejs.org/
[Frontend Maven plugin]: https://github.com/eirslett/frontend-maven-plugin
[Webpack]: https://webpack.github.io/
[MongoDB]: https://www.mongodb.org/