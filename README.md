# ShareMyWeek
An friendly web app for picking events based on everyone's availability and budget

## Development

#### IDE

You'll need a version of [Maven] 3.1 for your IDE. You can install a IDE that comes with it, or install your own.

So far a good work environment consist of [Eclipse] EE for back-end and [Atom] for front-end. [Intellij] works well in both areas. 
Metadata directories have been added to .gitignore

> The Project relies on Lombok in order to automatically generate sources from Java class with getters and setters. For Eclipse, you'll need to download and run [lombok.jar] to install the plugin.



#### Front-end Guide

>It is required that all JavaScript libraries for development are imported using [NPM]. The only exception is if the library is not in NPM. In that case download and add it to js/lib/

1. Download and install latest [Node.js]
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

>Use the JavaScript libraries only in .js files. Do not add script tags into the HTML files. Each HTML file should have only one script tag for the corresponding bundle.
You can use a library by adding [require] statements in any .js file:

```javascript
$ var packageName = require('package-name');
```

> All JavaScript sources is bundled together into a single file with its dependencies. The Maven Project utilizes a [Frontend Maven plugin] and [Webpack] to build and bundle JavaScript sources.

For creating JavaScript files, make sure that the script has at least one parent script (required through another script). 
If the JavaScript file is the initial script for an HTML page, then you need to make sure it a entry part of webpack.config.js. See [Webpack's documentation on entry]



#### Building

1. Import the project into your preferred IDE
2. Run Maven install on project.

> Run Maven install when pom.xml changes

For JavaScript, right now only workflow is to refresh the js/ directory to ensure it is built and reloaded. 
If you are modifying front-end code in external editor you need to refresh the directories regardless to ensure auto-build. Intellij is always manual.

#### Running the application

The preferred way is setting up an launch configuration in the IDE, so you can enable debug mode. You'll need to install [MongoDB]. By default the application will access mongodb://localhost/test. 
Please follow the official documentation for an install guide. You can set it as a service, if you like.

#### Testing

Soon.

#### Deployment

Soon.

[lombok.jar]: https://projectlombok.org/download.html
[Maven]: https://maven.apache.org/
[Atom]: https://atom.io/
[Eclipse]: http://eclipse.org
[Intellij]: https://www.jetbrains.com/idea/
[NPM]: https://www.npmjs.com/
[Node.js]: https://nodejs.org/
[require]: http://requirejs.org/
[Frontend Maven plugin]: https://github.com/eirslett/frontend-maven-plugin
[Webpack]: https://webpack.github.io/
[Webpack's documentation on entry]: https://webpack.github.io/docs/configuration.html#entry
[MongoDB]: https://www.mongodb.org/