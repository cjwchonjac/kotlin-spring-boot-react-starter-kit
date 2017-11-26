var fs = require('fs-extra');

fs.readdir('./dist', (err, files) => {
  files.forEach(file => {
  var dest;
  if (file.endsWith('ejs') || file.endsWith('html')) {
    dest = './src/main/resources/templates/markup/' + file
  } else if (file.endsWith('js')) {
    dest = './src/main/resources/static/js/' + file
  }

  if (dest) {
    fs.copy('./dist/' + file, dest, function (err) {
      if (err) {
        return console.error(err);
      }

      console.log('Copied to ' + dest);
    });
  }
  });
});
