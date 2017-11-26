var serializer = (new Packages.com.fasterxml.jackson.databind.ObjectMapper()).writer();

function render(template, model, url) {
  var path = model.path;
  var model = JSON.parse(serializer.writeValueAsString(model));
  var result = ReactNashornApp.render(path, model);
  return template .replace('/#html#/', result.html)
      .replace('/#state#/', JSON.stringify(result.state).replace(/</g, '\\u003c'))
}
