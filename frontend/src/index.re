[%bs.raw {|require("tailwindcss/dist/tailwind.min.css")|}];

[%bs.raw {|require("antd/dist/antd.css")|}];

[%bs.raw {|require("./index.css")|}];
[%bs.raw {|require("./components/calendar.css")|}];

ReactDOMRe.renderToElementWithId(<App />, "app");