# ShareMyWeek
An friendly web app for picking events based on everyone's availability and budget

## Development

#### IDE

You'll need a version of [Maven] 3.1 for your IDE. You can install a IDE that comes with it, or install your own.

So far a good work environment consist of [Eclipse] EE for back-end and [Atom] for front-end. [Intellij] works well in both areas.
Metadata directories have been added to .gitignore

> The Project relies on Lombok in order to automatically generate sources from Java class with getters and setters. For Eclipse, you'll need to download and run [lombok.jar] to install the plugin.

#### Email Design Guide

-Use a web app such as [mailchimp]. Then save the template in src: $project_dir/src/main/resources/templates/mail

>If you like to code in React instead, in /react-email-templates you can create your design in
templates/(e.g mydesign-template.js). See for tips: [react-html-email]. Then add your template to index.js.

>In /react-email-templates, preview template ```npm template-viewer``` and go to http://localhost:85/. Any images should go into templates/images to be copied to src. Then build into src using ```npm build```. Push the modified files from src into git.

#### View Design Guide

- View Design should be pushed in the $project_dir/design/ folder.
- Consider using [Bootstrap] look and feel as default.
- To start off designing, you may use the Bootstrap designer [pinguendo].
- All templates should share the same stylesheet to uphold an consistent design

> Pinguendo uses the .less file. On saving the template, the file is compiled into .css. Therefore modification of style should go into the less file. [less] extends the css body language.

After pushing changes of the template to git: Copy app.css to src/main/resources/static/js/src/client/assets/css

#### Creating React components for an View

To start off creating React components, you can use an UI React-builder tool called [Structor]. Structor can automatically create boiler plate code using frameworks such as [React] and [Redux]. However, you can create your components manually and linking them to /.structor/index.js. By modifying generated code structure you may understand the frameworks easier. Please refer to Structor's guide for more details.

To install Structor:

1. Make sure to install latest Node.js. See Front-end tips
2. You'll need to install [python 2.7.11] because some of the scripts need to be built using it
3. Set an environment variable PYTHON to the python binary file
4. Install npm package: ```npm install -g structor```

    >You can ignore ERROR on optional dependencies
    
5. To start Structor, cd into $project_folder/src/main/resources/static/js. Then, ```structor```
6. Go to http://localhost:2222/structor to launch the application. It will take a few seconds for the existing pages to be built
7. Modify or create a new page.

> If you click on the current route selection you can modify the Route script name located in js/src/client/routes. You should always leave its contents untouched. This is where the components of the page are linked and provide routing mechanism [React-router] for Single Page apps.

Please refer to Structor's Wiki to see how and where code is organized.

#### Adding your own Styles

Under resources/static/less directory, there is an app.less file for adding custom styles. This file gets appended last, so if you want to override an existing style you may.
Unfortunately, Structor won't recognize changes until you restart it.


#### Adding Front-end libraries

>It is required that all JavaScript libraries for development are imported using [NPM]. The only exception is if the library is not in NPM. In that case download and add it to static/js/src/client/js/

1. Download and install latest [Node.js]
2. To install an package, start an terminal and cd into (repository)/src/main/resources/static/js
3. Enter the following: ```npm install --save <package-name>```
4. To see current list of installed packages ```npm list```

Then commit and push package.json

>Use the JavaScript libraries only in .js or .jsx files. Do not add script tags into the HTML files. Each HTML file should have only one script tag for the corresponding bundle.
You can use a library by adding [require] or ES6 [import] statements in any .js file:

```javascript
var packageName = require('package-name');
var libName = require('$relativePath/lib/name');
```

```javascript
import {packageName} from 'library'
import {libName} from '$relativePath/lib/name'
```


> All JavaScript sources is bundled together into a single file with its dependencies. The Maven Project utilizes a [Frontend Maven plugin] and [Webpack] to build and bundle JavaScript sources.

For creating JavaScript files, make sure that the script has at least one parent script (required through another script).
If the JavaScript file is the initial script for an HTML page, then you need to make sure it a entry part of webpack.config.js. See [Webpack's documentation on entry]



#### Building Java

1. Import the project into your preferred IDE
2. Run Maven install on project.

> Run Maven install when pom.xml changes


#### Building LESS/JavaScript

If the .jsx files are modified (by you or Structor), you'll need to rebuild the scripts using package.json in resources/static/js/:

```sh
npm run build-client
```

Then refresh the js/ directory to ensure it is reloaded by the Spring instance.

> This build command can stop compiling issues it finds.


#### Running the application

The preferred way is setting up an launch configuration in the IDE, so you can enable debug mode. You'll need to install [MongoDB]. By default the application will access mongodb://localhost/test.
Please follow the official documentation for an install and run guide. You can set it as a service, if you like.

#### Debugging

Soon.

#### Testing Email

Start the test email server:
1. Install [smtpeshka]. $ ```npm install -g smtpeshka```
2. cd into $project_dir/smtp-test-server
3. $ ```smtpeshka```
4. Go to http://localhost:2580/

> You can add whitelist, blacklist configuration. Add config files to config/. For example:

1. $ ```echo .* > config/rcpt_to.access.blacklist_regex```
2. $ ```echo test1@sharemyweek.com > rcpt_to.access.whitelist```.
3. Restart smtpeshka.

As result, only test1@sharemyweek.com can recieve email from the server. See more tips: http://haraka.github.io/manual/plugins/access.html


#### Server-side Testing

Soon.

#### Deployment

Soon.

[mailchimp]: http://mailchimp.com
[react-html-email]: https://github.com/chromakode/react-html-email
[Bootstrap]: http://getbootstrap.com/
[React-router]: https://github.com/rackt/react-router
[less]: http://lesscss.org
[import]: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/import
[React]: http://facebook.github.io/react/
[Redux]: https://github.com/rackt/redux
[Structor]: https://github.com/ipselon/structor
[python 2.7.11]: https://www.python.org/downloads/
[lombok.jar]: https://projectlombok.org/download.html
[Maven]: https://maven.apache.org/
[Atom]: https://atom.io/
[Eclipse]: http://eclipse.org
[Intellij]: https://www.jetbrains.com/idea/
[pinguendo]: http://pingendo.com/
[NPM]: https://www.npmjs.com/
[Node.js]: https://nodejs.org/
[require]: http://requirejs.org/
[Frontend Maven plugin]: https://github.com/eirslett/frontend-maven-plugin
[Webpack]: https://webpack.github.io/
[Webpack's documentation on entry]: https://webpack.github.io/docs/configuration.html#entry
[MongoDB]: https://www.mongodb.org/
[smtpeshka]: https://www.npmjs.com/package/smtpeshka
