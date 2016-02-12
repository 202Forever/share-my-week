import fs from 'fs-extra';
import ReactHTMLEmail from 'react-html-email';
import templates from './templates';

const outputDir = '../src/main/resources/templates/mail'

export default function build() {
    fs.ensureDirSync(outputDir);
    fs.ensureDirSync('./templates/images');
    fs.copySync('./templates/images', outputDir + '/images');
    console.log('Copying images...');

    console.log('Building template files...');

    for (var key in templates) {
        let filepath = outputDir + '/' + key + '.html';
        fs.writeFileSync(filepath, ReactHTMLEmail.renderEmail(templates[key]));
        console.log(filepath);
    }
}
