import http from 'http';
import ReactHTMLEmail from 'react-html-email';
import templates from './templates';

// set up React to support a few HTML attributes useful for legacy clients
ReactHTMLEmail.injectReactEmailAttributes();

ReactHTMLEmail.configStyleValidator({
  // When strict, incompatible style properties will result in an error.
  strict: true,

  // Whether to warn when compatibility notes for a style property exist.
  warn: true,

  // Platforms to consider for compatibility checks.
  platforms: [
    'gmail',
    'gmail-android',
    'apple-mail',
    'apple-ios',
    'yahoo-mail',
    'outlook',
    'outlook-legacy',
    'outlook-web',
  ],
});

const PORT = 8085;
var server = http.createServer((request, response) => {
    if (request.url === '/') {
        response.end('Enter template url (e.g. http://localhost:' + PORT + '/week-link)');
    }
    for (var key in templates) {
        if (request.url === '/' + key) {
            response.end(ReactHTMLEmail.renderEmail(templates[key]));
            return;
        }
    }
    response.end('Template does not exist');
});
server.listen(PORT, () => console.log("Server listening on: http://localhost:%s", PORT));
