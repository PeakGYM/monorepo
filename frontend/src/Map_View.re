type map;
[@bs.module "./map.js"] external loadMap: 'a => map = "loadMap";

[@react.component]
let make = () => {
  React.useEffect0(() => {
    let map = loadMap("");

    None;
  });

  <div
    id="map-container"
    style={ReactDOMRe.Style.make(~width="auto", ~height="800px", ~position="relative", ())}
  />;
};
